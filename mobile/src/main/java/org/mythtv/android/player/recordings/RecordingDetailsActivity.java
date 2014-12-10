package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.player.R;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingDetailsActivity extends ActionBarActivity {

    private static final String TAG = RecordingDetailsActivity.class.getSimpleName();

    private static final String RECORDING_DETAILS_FRAGMENT_TAG = RecordingDetailsFragment.class.getCanonicalName();

    Program mProgram;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.v( TAG, "onCreate : enter" );

        setContentView( R.layout.activity_recording_details );

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( RecordingDetailsFragment.PROGRAM_KEY ) ) {
            mProgram = (Program) getIntent().getSerializableExtra( RecordingDetailsFragment.PROGRAM_KEY );
        }

        getSupportActionBar().setTitle( mProgram.getTitle() );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        RecordingDetailsFragment recordingDetailsFragment = (RecordingDetailsFragment) getFragmentManager().findFragmentByTag( RECORDING_DETAILS_FRAGMENT_TAG );
        if( null == recordingDetailsFragment ) {
            Log.d( TAG, "onCreate : creating new RecordingDetailsFragment" );

            Bundle args = new Bundle();
            args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, mProgram );

            recordingDetailsFragment = (RecordingDetailsFragment) Fragment.instantiate( this, RecordingDetailsFragment.class.getName(), args );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace( R.id.content_frame, recordingDetailsFragment, RECORDING_DETAILS_FRAGMENT_TAG );
            transaction.addToBackStack( null );
            transaction.commit();
        }

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );
        Log.v( TAG, "onSaveInstanceState : enter" );

        outState.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, mProgram );

        Log.v( TAG, "onSaveInstanceState : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );
        Log.v( TAG, "onRestoreInstanceState : enter" );

        if( savedInstanceState.containsKey( RecordingDetailsFragment.PROGRAM_KEY ) ) {
            mProgram = (Program) savedInstanceState.getSerializable( RecordingDetailsFragment.PROGRAM_KEY );
        }

        Log.v( TAG, "onRestoreInstanceState : exit" );
    }

}
