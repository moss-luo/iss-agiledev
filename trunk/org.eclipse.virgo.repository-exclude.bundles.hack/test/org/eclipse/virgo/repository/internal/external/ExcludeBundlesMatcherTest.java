package org.eclipse.virgo.repository.internal.external;

import junit.framework.Assert;

import org.junit.Test;

public class ExcludeBundlesMatcherTest {
	private ExcludeBundlesMatcher matcher;
	
	@Test
    public void testEnvVariableSet() {
		matcher = new ExcludeBundlesMatcher() {
			@Override
			protected String getEnvVarible() {
				return "${user.home}/.m2/repository/org/springframework/**/*;${user.home}/.m2/repository/org/slf4j/**/*";
			}
		};
		
		Assert.assertTrue(matcher.match(System.getProperty("user.home") +
			"/.m2/repository/org/springframework/org.springframework.beans/3.1.0.RELEASE$/org.springframework.beans-3.1.0.RELEASE.jar"));
		Assert.assertTrue(matcher.match(System.getProperty("user.home") +
				"/.m2/repository/org/slf4j/slf4j-api/1.6.4/slf4j-api-1.6.4.jar"));
	}
	
	public void testEnvVariableNotSet() {
		matcher = new ExcludeBundlesMatcher() {
			@Override
			protected String getEnvVarible() {
				return null;
			}
		};
		
		Assert.assertTrue(matcher.match(System.getProperty("user.home") +
				"/.m2/repository/org/springframework/org.springframework.beans/3.1.0.RELEASE$/org.springframework.beans-3.1.0.RELEASE.jar"));
		Assert.assertFalse(matcher.match(System.getProperty("user.home") +
				"/.m2/repository/org/slf4j/slf4j-api/1.6.4/slf4j-api-1.6.4.jar"));
	}
}
