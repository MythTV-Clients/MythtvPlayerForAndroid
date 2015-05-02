/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.library.core.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.events.dvr.UpdateRecordedProgramsEvent;

/**
 * Created by dmfrey on 3/14/15.
 */
public class RefreshRecordedProgramsTask extends AsyncTask<String, Void, Void> {

    private final String TAG = RefreshRecordedProgramsTask.class.getSimpleName();

    public interface OnRefreshRecordedProgramTaskListener {

        public void onRefreshComplete();

    }

    OnRefreshRecordedProgramTaskListener mListener;

    public RefreshRecordedProgramsTask( OnRefreshRecordedProgramTaskListener listener ) {

        if( null != listener ) {

            mListener = listener;

        }

    }

    @Override
    protected Void doInBackground( String... params ) {
        Log.v(TAG, "doInBackground : enter");

        String title = null;
        if( null != params && params.length > 0 ) {
            title = params[ 0 ];
        }

        MainApplication.getInstance().getDvrApiService().updateRecordedPrograms( new UpdateRecordedProgramsEvent( true, 0, null, title, null, null ) );

        Log.v( TAG, "doInBackground : exit" );
        return null;
    }

    @Override
    protected void onPostExecute( Void aVoid ) {

        if( null != mListener ) {

            mListener.onRefreshComplete();

        }

        super.onPostExecute( aVoid );

    }

}
