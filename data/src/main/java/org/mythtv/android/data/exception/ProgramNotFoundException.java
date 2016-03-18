package org.mythtv.android.data.exception;

/**
 * Created by dmfrey on 8/26/15.
 */
public class ProgramNotFoundException extends Exception {

    public ProgramNotFoundException() {
        super();
    }

    public ProgramNotFoundException(final String message) {
        super(message);
    }

    public ProgramNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ProgramNotFoundException(final Throwable cause) {
        super(cause);
    }

}
