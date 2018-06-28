package de.dmeiners.mapping.api;

public class ScriptNameResolutionException extends RuntimeException {

    public ScriptNameResolutionException() {
        super();
    }

    public ScriptNameResolutionException(String message) {
        super(message);
    }

    public ScriptNameResolutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptNameResolutionException(Throwable cause) {
        super(cause);
    }

    protected ScriptNameResolutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
