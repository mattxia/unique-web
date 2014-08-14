package org.unique.plugin.i18n;

public class I18NException extends RuntimeException {

    private static final long serialVersionUID = -959639555662757838L;

    public I18NException() {
    }

    public I18NException(String message) {
        super(message);
    }

    public I18NException(Throwable cause) {
        super(cause);
    }

    public I18NException(String message, Throwable cause) {
        super(message, cause);
    }

}
