package com.isoftstone.agiledev.config.exception;

/**
 *
 * @author hilbert.xu.wang@gmail.com
 *
 */
public class ConfigException extends RuntimeException {

    private static final long serialVersionUID = -4715630270519461582L;

    public ConfigException() {
        super();
    }

    public ConfigException(String message) {
        super(message);
    }
    
    public ConfigException(Throwable e) {
        super(e);
    }
    
}
