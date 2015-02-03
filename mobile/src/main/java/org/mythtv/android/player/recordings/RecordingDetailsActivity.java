package org.mythtv.android.player.recordings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.player.BaseActionBarActivity;
import org.mythtv.android.R;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingDetailsActivity extends BaseActionBarActivity {

    private static final String TAG = RecordingDetailsActivity.class.getSimpleName();

    private static final String RECORDING_DETAILS_FRAGMENT_TAG = RecordingDetailsFragment.class.getCanonicalName();

    Program mProgram;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recording_details;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.d( TAG, "onCreate : enter" );

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( RecordingDetailsFragment.PROGRAM_KEY ) ) {
            mProgram = (Program) getIntent().getSerializableExtra( RecordingDetailsFragment.PROGRAM_KEY );
        }

        getSupportActionBar().setTitle( ( null != mProgram.getSubTitle() && !"".equals( mProgram.getSubTitle() ) ) ? mProgram.getSubTitle() : mProgram.getTitle() );

        RecordingDetailsFragment recordingDetailsFragment = (RecordingDetailsFragment) getSupportFragmentManager().findFragmentByTag( RECORDING_DETAILS_FRAGMENT_TAG );
        if( null == recordingDetailsFragment ) {
            Log.d( TAG, "onCreate : creating new RecordingDetailsFragment" );

            Bundle args = new Bundle();
            args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, mProgram );

            recordingDetailsFragment = (RecordingDetailsFragment) Fragment.instantiate( this, RecordingDetailsFragment.class.getName(), args );

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace( R.id.content_frame, recordingDetailsFragment, RECORDING_DETAILS_FRAGMENT_TAG );
            transaction.addToBackStack( null );
            transaction.commit();
        }

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );
        Log.d( TAG, "onSaveInstanceState : enter" );

        outState.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, mProgram );

        Log.d( TAG, "onSaveInstanceState : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );
        Log.d( TAG, "onRestoreInstanceState : enter" );

        if( savedInstanceState.containsKey( RecordingDetailsFragment.PROGRAM_KEY ) ) {
            mProgram = (Program) savedInstanceState.getSerializable( RecordingDetailsFragment.PROGRAM_KEY );
        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

}
