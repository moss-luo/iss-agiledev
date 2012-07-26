package com.isoftstone.agiledev.osgi.web.internal;

import org.osgi.service.http.NamespaceException;

import com.isoftstone.agiledev.osgi.core.action.DefaultWebActivator;
import com.isoftstone.agiledev.osgi.core.action.Register;

/**
 * Extension of the default OSGi bundle activator
 */
public final class Activator extends DefaultWebActivator {
	
	private	String[][] res = new String[][]{
			{"/resources","resources"},
			{"/resources/agiledev","resources/agiledev"},
			{"/resources/agiledev/i18n","resources/agiledev/i18n"},
			{"/resources/agiledev/style","resources/agiledev/style"},
			{"/resources/ligerui/js","resources/ligerui/js"},
			{"/resources/ligerui/js/core","resources/ligerui/js/core"},
			{"/resources/ligerui/skins","resources/ligerui/skins"},
			{"/resources/ligerui/skins/Aqua","resources/ligerui/skins/Aqua"},
			{"/resources/ligerui/skins/Aqua/css","resources/ligerui/skins/Aqua/css"},
			{"/resources/ligerui/skins/Aqua/images","resources/ligerui/skins/Aqua/images"},
			{"/resources/ligerui/skins/Aqua/images/common","resources/ligerui/skins/Aqua/images/common"},
			{"/resources/ligerui/skins/Aqua/images/controls","resources/ligerui/skins/Aqua/images/controls"},
			{"/resources/ligerui/skins/Aqua/images/dateeditor","resources/ligerui/skins/Aqua/images/dateeditor"},
			{"/resources/ligerui/skins/Aqua/images/form","resources/ligerui/skins/Aqua/images/form"},
			{"/resources/ligerui/skins/Aqua/images/grid","resources/ligerui/skins/Aqua/images/grid"},
			{"/resources/ligerui/skins/Aqua/images/icon","resources/ligerui/skins/Aqua/images/icon"},
			{"/resources/ligerui/skins/Aqua/images/layout","resources/ligerui/skins/Aqua/images/layout"},
			{"/resources/ligerui/skins/Aqua/images/menu","resources/ligerui/skins/Aqua/images/menu"},
			{"/resources/ligerui/skins/Aqua/images/panel","resources/ligerui/skins/Aqua/images/panel"},
			{"/resources/ligerui/skins/Aqua/images/tree","resources/ligerui/skins/Aqua/images/tree"},
			{"/resources/ligerui/skins/Aqua/images/win","resources/ligerui/skins/Aqua/images/win"}
	}; 
	
	protected void registerResources(Register register) throws NamespaceException {
		for (String[] s : res) {
			register.registerResources(s[0], s[1]);
		}
	}
	
}
