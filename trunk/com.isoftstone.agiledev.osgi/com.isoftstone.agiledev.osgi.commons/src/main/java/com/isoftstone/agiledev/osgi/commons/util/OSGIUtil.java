package com.isoftstone.agiledev.osgi.commons.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class OSGIUtil {
	/**
	 * 加载jar包中的所有类.
	 * @param jarPath
	 * @return
	 * @throws IOException
	 */
	public static String[] readJarFileClass(String jarPath) throws IOException{
		List<JarEntry> list = Collections.list(new JarFile(jarPath).entries());
		List<String> fullNames = new ArrayList<String>();
		for (JarEntry e : list) {
			String n = e.getName();
		 if (n.endsWith(".class")) {
			 n=n.substring(0,n.length()-6);
			 fullNames.add(n.replace('/', '.'));
		 }
		}
		int classCount = fullNames.size();
		String[] classNames = fullNames.toArray(new String[classCount]);
		return classNames;
	}
}
