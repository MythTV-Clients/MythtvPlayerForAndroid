package org.mythtv.android.player.recordings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.player.BaseActionBarActivity;
import org.mythtv.android.R;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingsActivity extends BaseActionBarActivity implements RecordingsFragment.OnRecordingClickedListener {

    private static final String TAG = RecordingsActivity.class.getSimpleName();

    private static final String RECORDINGS_FRAGMENT_TAG = RecordingsFragment.class.getCanonicalName();
    private static final String RECORDING_DETAILS_FRAGMENT_TAG = RecordingDetailsFragment.class.getCanonicalName();

    String mTitle = null;
    Program mProgram = null;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recordings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.d( TAG, "onCreate : enter" );

        if( null != savedInstanceState && savedInstanceState.containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) ) {
            Log.d( TAG, "onCreate : retrieved title from savedInstanceState" );

            mTitle = savedInstanceState.getString( RecordingsDataFragment.TITLE_INFO_TITLE );
        }

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) ) {
            Log.d( TAG, "onCreate : retrieved title from intent extras" );

            mTitle = getIntent().getStringExtra( RecordingsDataFragment.TITLE_INFO_TITLE );
        }

        RecordingsFragment recordingsFragment = (RecordingsFragment) getSupportFragmentManager().findFragmentByTag( RECORDINGS_FRAGMENT_TAG );
        if( null == recordingsFragment ) {
            Log.d( TAG, "onCreate : creating new RecordingsFragment" );

            Bundle args = new Bundle();
            if( null != mTitle ) {
                args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitle );
            }

            recordingsFragment = (RecordingsFragment) Fragment.instantiate( this, RecordingsFragment.class.getName(), args );
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace( R.id.content_frame, recordingsFragment, RECORDINGS_FRAGMENT_TAG );
//            transaction.addToBackStack( null );
            transaction.commit();

        }

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        if( null != mTitle && !"".equals( mTitle ) ) {
            getSupportActionBar().setTitle( mTitle );
        } else {
            getSupportActionBar().setTitle( getResources().getString( R.string.all_recordings ) );
        }

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        Log.d( TAG, "onSaveInstanceState : mTitle=" + mTitle );
        outState.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitle );

        if( null != mProgram ) {
            outState.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, mProgram );
        }

        Log.d( TAG, "onSaveInstanceState : exit" );
        super.onSaveInstanceState( outState );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );
        Log.d( TAG, "onRestoreInstanceState : enter" );

        if( savedInstanceState.containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) ) {
            mTitle = savedInstanceState.getString( RecordingsDataFragment.TITLE_INFO_TITLE );
            Log.d( TAG, "onRestoreInstanceState : mTitle=" + mTitle );
        }

        if( savedInstanceState.containsKey( RecordingDetailsFragment.PROGRAM_KEY ) ) {
            mProgram = (Program) savedInstanceState.getSerializable( RecordingDetailsFragment.PROGRAM_KEY );
        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d( TAG, "onSupportNavigateUp : enter" );

        Log.d( TAG, "onSupportNavigateUp : backstack count=" + getSupportFragmentManager().getBackStackEntryCount() );
        if( getSupportFragmentManager().getBackStackEntryCount() > 0 ) {
            getSupportFragmentManager().popBackStack();

            if( null != mTitle && !"".equals( mTitle ) ) {
                getSupportActionBar().setTitle( mTitle );
            } else {
                getSupportActionBar().setTitle( getResources().getString( R.string.all_recordings ) );
            }

            Log.d( TAG, "onSupportNavigateUp : exit, removing recordingDetailsFragment" );
            return true;
        }

        Log.d( TAG, "onSupportNavigateUp : exit" );
        return super.onSupportNavigateUp();
    }

    @Override
    public void onSetProgram( Program program ) {
        Log.d( TAG, "onSetProgram : enter" );

        Bundle args = new Bundle();
        args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, program );

        RecordingDetailsFragment recordingDetailsFragment = (RecordingDetailsFragment) getSupportFragmentManager().findFragmentByTag( RECORDING_DETAILS_FRAGMENT_TAG );
        if( null == recordingDetailsFragment ) {

            recordingDetailsFragment = (RecordingDetailsFragment) Fragment.instantiate( this, RecordingDetailsFragment.class.getName(), args );

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace( R.id.content_frame, recordingDetailsFragment, RECORDING_DETAILS_FRAGMENT_TAG );
            transaction.addToBackStack( null );
            transaction.commit();

        }

        Log.d( TAG, "onSetProgram : exit" );
    }

}
