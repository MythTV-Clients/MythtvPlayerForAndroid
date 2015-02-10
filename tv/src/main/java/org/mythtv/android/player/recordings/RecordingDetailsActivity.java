package org.mythtv.android.player.recordings;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
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

        setContentView( R.layout.activity_recording_details );

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
