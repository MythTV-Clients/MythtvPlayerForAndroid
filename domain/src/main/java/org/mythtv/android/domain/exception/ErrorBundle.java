package org.mythtv.android.domain.exception;

/**
 * Created by dmfrey on 8/26/15.
 */
public interface ErrorBundle {

    Exception getException();

    String getErrorMessage();

}
