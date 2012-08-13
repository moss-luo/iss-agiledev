package com.isoftstone.agiledev.core.i18n;

import java.util.Locale;

public interface I18n {
	String getMessage(String code) throws NoSuchMessageException;
	String getMessage(String code, Locale locale) throws NoSuchMessageException;
	String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException;
	String getMessage(String code, String defaultMessage);
	String getMessage(String code, String defaultMessage, Locale locale);
	String getMessage(String code, Object[] args, String defaultMessage, Locale locale);
}
