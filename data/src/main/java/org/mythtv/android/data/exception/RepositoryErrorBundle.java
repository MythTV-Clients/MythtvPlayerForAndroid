package org.mythtv.android.data.exception;

import org.mythtv.android.domain.exception.ErrorBundle;

/**
 * Created by dmfrey on 8/26/15.
 */
public class RepositoryErrorBundle implements ErrorBundle {

    private final Exception exception;

    public RepositoryErrorBundle( Exception exception ) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {

        String message = "";

        if( null != this.exception ) {

            this.exception.getMessage();

        }

        return message;
    }

}
