package org.mythtv.android.presentation.exception;

import android.content.Context;

import org.mythtv.android.R;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.data.exception.ProgramNotFoundException;

/**
 * Created by dmfrey on 8/30/15.
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
    }

    public static String create( Context context, Exception exception ) {

        String message = context.getString( R.string.exception_message_generic );

        if( exception instanceof NetworkConnectionException ) {

            message = context.getString(R.string.exception_message_no_connection);

        } else if( exception instanceof ProgramNotFoundException ) {

            message = context.getString(R.string.exception_message_program_not_found);

        }

        return message;
    }

}
