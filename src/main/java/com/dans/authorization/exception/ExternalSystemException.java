package com.dans.authorization.exception;

import lombok.Getter;

@Getter
public class ExternalSystemException extends RuntimeException {

    private int statusCode;

    public ExternalSystemException() {
        super();
    }

    public ExternalSystemException(String message) {
        super(message);
    }

    public ExternalSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalSystemException(Throwable cause) {
        super(cause);
    }

    public ExternalSystemException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    protected ExternalSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
