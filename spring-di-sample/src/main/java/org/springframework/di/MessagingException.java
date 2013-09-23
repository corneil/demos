package org.springframework.di;

public class MessagingException extends ResourceException {
    private static final long serialVersionUID = 2695734294198043303L;

    public MessagingException() {
    }

    public MessagingException(String message) {
        super(message);
    }

    public MessagingException(Throwable cause) {
        super(cause);
    }

    public MessagingException(String message, Throwable cause) {
        super(message, cause);
    }

}
