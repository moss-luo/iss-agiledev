package com.isoftstone.agiledev.hrdemo.workbench.app.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.isoftstone.agiledev.core.i18n.I18nManager;
import com.isoftstone.agiledev.hrdemo.workbench.app.IMenuManager;
import com.isoftstone.agiledev.hrdemo.workbench.app.Menu;

@Service
public class MenuManager implements BundleContextAware, BundleListener, IMenuManager {
	private List<Menu> menus;
	private Map<String, List<Menu>> cache;
	private Map<Long, List<Menu>> bundleContributions;
	private Locale locale;
	
	private BundleContext bundleContext;
	private static final Logger logger = LoggerFactory.getLogger(MenuManager.class);

	private Menu generateMenu(String menuKey, String menuString, Bundle bundle) {
		String menuId = menuKey;
		if (menuId.length() == 0) {
			return null;
		}
		
		StringTokenizer tokenizer = new StringTokenizer(menuString, ",");
		Menu menu = new Menu(menuId);
		
		while (tokenizer.hasMoreElements()) {
			String token = tokenizer.nextToken();
			int index = token.indexOf("=");
			if (index == -1 || index == 0 || index == token.length() - 1)
				continue;
			
			String key = token.substring(0, index);
			String value = token.substring(index + 1, token.length());
			value = i18n(value, bundle);
			
			if ("text".equals(key)) {
				menu.setText(value);
			} else if ("icon".equals(key)) {
				menu.setIcon(value);
			} else if ("action".equals(key)) {
				menu.setAction(value);
			} else if ("parent".equals(key)) {
				menu.setParentId(value);
			} else if ("order".equals(key)) {
				try {
					int order = Integer.parseInt(value);
					menu.setOrder(order);
				} catch (NumberFormatException e) {
					// do nothing
				}				
			} else {
				logger.warn(String.format("Illegal menu configuration[%s, %s]", menuKey, menuString));
				return null;
			}
		}
		
		return menu;
	}
	
	private String i18n(String value, Bundle bundle) {
		if (!value.startsWith("${") || !value.endsWith("}"))
			return value;
		
		value = value.substring(2, value.length() - 1);
		return I18nManager.getI18n(bundle).getMessage(value, locale);
	}

	public List<Menu> getMenus(String parentId) {
		String parentIdKey = parentId;
		if (parentIdKey == null) {
			parentIdKey = "";
		}
		
		List<Menu> children = cache.get(parentIdKey);
		if (children != null)
			return children;
		
		children = new ArrayList<Menu>();
		for (Menu menu : menus) {
			if (isChild(parentId, menu)) {
				children.add(menu);
			}
		}
		
		Collections.sort(menus);		
		cache.put(parentIdKey, children);
		
		return children;
	}

	private boolean isChild(String parentId, Menu menu) {
		if (parentId == null)
			return menu.getParentId() == null;
		
		return parentId.equals(menu.getParentId());
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		if (event.getType() == BundleEvent.STARTED) {
			readMenusIfExisting(event.getBundle());
		} else if (event.getType() == BundleEvent.STOPPED) {
			removeMenusIfExisting(event.getBundle());
		} else {
			// do nothing
		}
	}
	
	private synchronized void removeMenusIfExisting(Bundle bundle) {
		List<Menu> bundleMenus = bundleContributions.get(bundle.getBundleId());
		if (bundleMenus == null)
			return;
		
		synchronized (this) {
			for (Menu menu : bundleMenus) {
				menus.remove(menu);
			}
			cache.clear();
		}
	}

	@PostConstruct
	public void start() {
		menus = new ArrayList<Menu>();
		cache = new HashMap<String, List<Menu>>();
		bundleContributions = new HashMap<Long, List<Menu>>();
		locale = new Locale("");
		
		bundleContext.addBundleListener(this);
		searchExistingMenus();
	}
	
	private void searchExistingMenus() {
		for (Bundle bundle : bundleContext.getBundles()) {
			if (bundle.getState() == Bundle.ACTIVE) {
				readMenusIfExisting(bundle);
			}
		}
	}

	private void readMenusIfExisting(Bundle bundle) {
		Enumeration<URL> menuResources = bundle.findEntries("/META-INF/agiledev", "menu.properties", false);
		if (menuResources == null)
			return;
		
		if (!menuResources.hasMoreElements())
			return;
		
		URL menuResource = menuResources.nextElement();
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = menuResource.openStream();
			properties.load(is);
		} catch (IOException e) {
			logger.warn(String.format("Can't read menu properties from bundle %", bundle.getSymbolicName()));
			return;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		
		List<Menu> bundleMenus = new ArrayList<Menu>();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			Menu menu = generateMenu((String)entry.getKey(), (String)entry.getValue(), bundle);
			
			if (menu == null)
				continue;
			
			if (menu.getText() == null || menu.getText().equals("")) {
				logger.warn("Null text menu");
			} else {
				if (isMenuExisted(menu.getId(), bundleMenus)) {
					logger.warn(String.format("Duplicate menu id from bundle %s", bundle.getSymbolicName()));
				}
				bundleMenus.add(menu);
			}
		}
		
		if (bundleMenus.size() == 0)
			return;
		
		synchronized (this) {
			bundleContributions.put(bundle.getBundleId(), bundleMenus);
			Iterator<Menu> iter = bundleMenus.iterator();
			while (iter.hasNext()) {
				Menu toAdd = iter.next();
				
				for (Menu menu : bundleMenus) {
					if (toAdd.getId().equals(menu.getParentId())) {
						toAdd.setHasChild(true);
						break;
					}
				}
				
				menus.add(toAdd);
			}
			
			cache.clear();
		}
	}

	private boolean isMenuExisted(String id, List<Menu> bundleMenus) {
		for (Menu menu : menus) {
			if (menu.getId().equals(id))
				return true;
		}
		
		for (Menu menu : bundleMenus) {
			if (menu.getId().equals(id))
				return true;
		}
		
		return false;
	}

	@PreDestroy
	public void destroy() {
		menus = null;
		cache = null;
		bundleContributions = null;
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	@Override
	public void setLocale(Locale locale) {
		start();
		this.locale = locale;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}
}
