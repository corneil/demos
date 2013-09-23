package org.springframework.di;

public class ResourceException extends Exception {
    private static final long serialVersionUID = -3867859731861431903L;

    public ResourceException() {
    }

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(Throwable cause) {
        super(cause);
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
