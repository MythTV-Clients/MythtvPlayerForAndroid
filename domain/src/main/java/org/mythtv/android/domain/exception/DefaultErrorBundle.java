package org.mythtv.android.domain.exception;

/**
 * Created by dmfrey on 8/26/15.
 */
public class DefaultErrorBundle implements ErrorBundle {

    private static final String DEFAULT_ERROR_MSG = "Unknown error";

    private final Exception exception;

    public DefaultErrorBundle( Exception exception ) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        return ( exception != null ) ? this.exception.getMessage() : DEFAULT_ERROR_MSG;
    }

}
