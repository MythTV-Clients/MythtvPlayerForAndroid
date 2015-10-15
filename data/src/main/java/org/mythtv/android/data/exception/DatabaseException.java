package org.mythtv.android.data.exception;

/**
 * Created by dmfrey on 8/26/15.
 */
public class DatabaseException extends Exception {

    public DatabaseException() {
        super();
    }

    public DatabaseException( final String message) {
        super( message );
    }

    public DatabaseException( final String message, final Throwable cause ) {
        super( message, cause );
    }

    public DatabaseException( final Throwable cause ) {
        super( cause );
    }

}
