/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.presentation.exception;

import android.content.Context;

import org.mythtv.android.R;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.data.exception.ProgramNotFoundException;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/30/15.
 */
public final class ErrorMessageFactory {

    private ErrorMessageFactory() {
    }

    public static String create( Context context, Exception exception ) {

        String message = context.getString( R.string.exception_message_generic );

        if( exception instanceof NetworkConnectionException ) {

            message = context.getString( R.string.exception_message_no_connection );

        } else if( exception instanceof ProgramNotFoundException ) {

            message = context.getString( R.string.exception_message_program_not_found );

        }

        return message;
    }

}
