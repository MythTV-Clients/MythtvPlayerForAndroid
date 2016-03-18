package org.mythtv.android.data.exception;

/**
 * Created by dmfrey on 8/26/15.
 */
public class VideoNotFoundException extends Exception {

    public VideoNotFoundException() {
        super();
    }

    public VideoNotFoundException(final String message) {
        super(message);
    }

    public VideoNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public VideoNotFoundException(final Throwable cause) {
        super(cause);
    }

}
