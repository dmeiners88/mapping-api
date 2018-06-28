package de.dmeiners.mapping.api;

public class ResultTypeException extends RuntimeException {

    public ResultTypeException() {
        super();
    }

    public ResultTypeException(String message) {
        super(message);
    }

    public ResultTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultTypeException(Throwable cause) {
        super(cause);
    }

    protected ResultTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
