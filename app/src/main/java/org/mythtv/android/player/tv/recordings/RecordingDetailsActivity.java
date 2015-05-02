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

package org.mythtv.android.player.tv.recordings;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.library.core.domain.dvr.Program;

public class RecordingDetailsActivity extends Activity {

    private static final String TAG = RecordingDetailsActivity.class.getSimpleName();

    private static final String RECORDING_DETAILS_FRAGMENT_TAG = RecordingDetailsFragment.class.getCanonicalName();

    Program mProgram;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_tv_recording_details );

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( RecordingDetailsFragment.PROGRAM_KEY ) ) {
            mProgram = (Program) getIntent().getSerializableExtra( RecordingDetailsFragment.PROGRAM_KEY );
        }

        RecordingDetailsFragment recordingDetailsFragment = (RecordingDetailsFragment) getFragmentManager().findFragmentByTag( RECORDING_DETAILS_FRAGMENT_TAG );
        if( null == recordingDetailsFragment ) {
            Log.d( TAG, "onCreate : creating new RecordingDetailsFragment" );

            Bundle args = new Bundle();
            args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, mProgram );

            recordingDetailsFragment = (RecordingDetailsFragment) Fragment.instantiate( this, RecordingDetailsFragment.class.getName(), args );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace( R.id.content_frame, recordingDetailsFragment, RECORDING_DETAILS_FRAGMENT_TAG );
            transaction.commit();
        }

        Log.d( TAG, "onCreate : exit" );
    }

//    @Override
//    protected void onResume() {
//        Log.d( TAG, "onResume : enter" );
//        super.onResume();
//
//        MainApplication.getInstance().scheduleAlarms();
//
//        Log.d( TAG, "onResume : exit" );
//    }
//
//    @Override
//    protected void onPause() {
//        Log.d( TAG, "onPause : enter" );
//        super.onPause();
//
//        MainApplication.getInstance().cancelAlarms();
//
//        Log.d( TAG, "onPause : exit" );
//    }

}
