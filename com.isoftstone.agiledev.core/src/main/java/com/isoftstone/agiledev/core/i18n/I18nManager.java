package com.isoftstone.agiledev.core.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;

import org.osgi.framework.Bundle;

public abstract class I18nManager {
	private static Map<Long, I18n> i18ns = new HashMap<Long, I18n>();
	private static Object lock = new Object();

	public static I18n getI18n(Bundle bundle) {
		I18n i18n = i18ns.get(bundle.getBundleId());
		if (i18n != null)
			return i18n;

		synchronized (lock) {
			i18n = i18ns.get(bundle.getBundleId());
			if (i18n != null)
				return i18n;

			i18n = new I18nImpl(bundle);
			i18ns.put(bundle.getBundleId(), i18n);

			return i18n;
		}
	}

	private static class I18nImpl implements I18n {
		private Bundle bundle;
		private Map<Locale, ResourceBundle> rbCache;
		private Map<String, Map<Locale, MessageFormat>> mfCache;

		private static final String DEFAULT_I18N_DIR = "/META-INF/agiledev/i18n";
		private static final String DEFAULT_BASE_NAME = "/messages";

		public I18nImpl(Bundle bundle) {
			this.bundle = bundle;
			rbCache = new HashMap<Locale, ResourceBundle>();
			mfCache = new HashMap<String, Map<Locale, MessageFormat>>();
		}

		@Override
		public String getMessage(String code) throws NoSuchMessageException {
			return getMessage(code, Locale.getDefault());
		}

		@Override
		public String getMessage(String code, Locale locale)
				throws NoSuchMessageException {
			return getMessage(code, (Object[]) null, locale);
		}

		@Override
		public synchronized String getMessage(String code, Object[] args, Locale locale)
				throws NoSuchMessageException {
			Map<Locale, MessageFormat> mfMap = mfCache.get(code);
			if (mfMap == null) {
				mfMap = new HashMap<Locale, MessageFormat>();
				mfCache.put(code, mfMap);
			}

			MessageFormat mf = mfMap.get(locale);
			if (mf == null) {
				ResourceBundle rb = rbCache.get(locale);
				if (rb == null) {
					rb = ResourceBundle.getBundle(DEFAULT_I18N_DIR + DEFAULT_BASE_NAME,
						locale, BundleClassLoaderGetter.getClassLoader(bundle), new OsgiControl(bundle));
					rbCache.put(locale, rb);
				}
					
				mf = new MessageFormat(rb.getString(code));
				mfMap.put(locale, mf);
			}
				
			return mf.format(args);
		}
		
		private static class BundleClassLoaderGetter {
			private static Map<Bundle, ClassLoader> cache = new HashMap<Bundle, ClassLoader>();
			
			public synchronized static ClassLoader getClassLoader(Bundle bundle) {
				ClassLoader classLoader = null;
				classLoader = cache.get(bundle);
				if (classLoader != null)
					return classLoader;
				
				classLoader = new ClassLoader() {};
				cache.put(bundle, classLoader);
				
				return classLoader;
			}
		}

		private static class OsgiControl extends ResourceBundle.Control {
			private Bundle osgiBundle;

			public OsgiControl(Bundle bundle) {
				this.osgiBundle = bundle;
			}

			@Override
			public ResourceBundle newBundle(String baseName, Locale locale, String format,
						ClassLoader loader, boolean reload) throws IllegalAccessException,
							InstantiationException, IOException {
				String bundleName = toBundleName(baseName, locale);
				ResourceBundle bundle = null;
				if (format.equals("java.class")) {
					try {
						@SuppressWarnings("unchecked")
						Class<? extends ResourceBundle> bundleClass = (Class<? extends ResourceBundle>) loader
								.loadClass(bundleName);

						// If the class isn't a ResourceBundle subclass, throw a
						// ClassCastException.
						if (ResourceBundle.class.isAssignableFrom(bundleClass)) {
							bundle = bundleClass.newInstance();
						} else {
							throw new ClassCastException(bundleClass.getName()
									+ " cannot be cast to ResourceBundle");
						}
					} catch (ClassNotFoundException e) {
					}
				} else if (format.equals("java.properties")) {
					final String resourceName = toResourceName(bundleName,
							"properties");
					final boolean reloadFlag = reload;
					InputStream stream = null;
					try {
						stream = AccessController
								.doPrivileged(new PrivilegedExceptionAction<InputStream>() {
									public InputStream run() throws IOException {
										InputStream is = null;
										if (reloadFlag) {
											URL url = osgiBundle.getEntry(resourceName);
											if (url != null) {
												URLConnection connection = url
														.openConnection();
												if (connection != null) {
													// Disable caches to get
													// fresh data for
													// reloading.
													connection
															.setUseCaches(false);
													is = connection
															.getInputStream();
												}
											}
										} else {
											is = osgiBundle.getEntry(resourceName).openStream();
										}
										
										return is;
									}
								});
					} catch (PrivilegedActionException e) {
						throw (IOException) e.getException();
					}
					if (stream != null) {
						try {
							bundle = new PropertyResourceBundle(stream);
						} finally {
							stream.close();
						}
					}
				} else {
					throw new IllegalArgumentException("unknown format: "
							+ format);
				}
				return bundle;
			}

			public boolean needsReload(String baseName, Locale locale, String format,
						ClassLoader loader, ResourceBundle bundle, long loadTime) {
				if (bundle == null) {
					throw new NullPointerException();
				}
				if (format.equals("java.class")
						|| format.equals("java.properties")) {
					format = format.substring(5);
				}
				boolean result = false;
				try {
					String resourceName = toResourceName(toBundleName(baseName, locale), format);
					URL url = osgiBundle.getEntry(resourceName);
					if (url != null) {
						long lastModified = 0;
						URLConnection connection = url.openConnection();
						if (connection != null) {
							// disable caches to get the correct data
							connection.setUseCaches(false);
							if (connection instanceof JarURLConnection) {
								JarEntry ent = ((JarURLConnection) connection)
										.getJarEntry();
								if (ent != null) {
									lastModified = ent.getTime();
									if (lastModified == -1) {
										lastModified = 0;
									}
								}
							} else {
								lastModified = connection.getLastModified();
							}
						}
						result = lastModified >= loadTime;
					}
				} catch (NullPointerException npe) {
					throw npe;
				} catch (Exception e) {
					// ignore other exceptions
				}
				return result;
			}
		}

		@Override
		public String getMessage(String code, String defaultMessage) {
			return getMessage(code, defaultMessage, Locale.getDefault());
		}

		@Override
		public String getMessage(String code, String defaultMessage,
				Locale locale) {
			return getMessage(code, null, defaultMessage, Locale.getDefault());
		}

		@Override
		public String getMessage(String code, Object[] args,
				String defaultMessage, Locale locale) {
			try {
				return getMessage(code, args, locale);
			} catch (Exception e) {
				return defaultMessage;
			}
		}
	}
}