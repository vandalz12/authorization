package com.dans.authorization.exception;

public class KeyGeneratorException extends RuntimeException {
    public KeyGeneratorException() {
    }

    public KeyGeneratorException(String message) {
        super(message);
    }

    public KeyGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyGeneratorException(Throwable cause) {
        super(cause);
    }

    public KeyGeneratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
