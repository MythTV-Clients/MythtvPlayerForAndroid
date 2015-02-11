package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.ui.data.RecordingDataConsumer;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.player.BaseActionBarActivity;
import org.mythtv.android.R;

import java.util.List;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingsActivity extends BaseActionBarActivity  implements RecordingDataConsumer {

    private static final String TAG = RecordingsActivity.class.getSimpleName();

    private static final String RECORDINGS_DATA_FRAGMENT_TAG = RecordingsDataFragment.class.getCanonicalName();

    private RecordingsFragment mRecordingsFragment;
    String mTitle = null;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_recordings;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate : enter");

        mRecordingsFragment = (RecordingsFragment) getFragmentManager().findFragmentById( R.id.fragment_recordings );

        if( null != savedInstanceState && savedInstanceState.containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) ) {
            Log.d( TAG, "onCreate : retrieved title from savedInstanceState" );

            mTitle = savedInstanceState.getString( RecordingsDataFragment.TITLE_INFO_TITLE );
        }

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) ) {
            Log.d( TAG, "onCreate : retrieved title from intent extras" );

            mTitle = getIntent().getStringExtra( RecordingsDataFragment.TITLE_INFO_TITLE );
        }

        if( null != mTitle && !"".equals( mTitle ) ) {
            getSupportActionBar().setTitle( mTitle );
        } else {
            getSupportActionBar().setTitle( getResources().getString( R.string.all_recordings ) );
        }

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        Log.d( TAG, "onSaveInstanceState : mTitle=" + mTitle );
        outState.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitle );

        Log.d( TAG, "onSaveInstanceState : exit" );
        super.onSaveInstanceState( outState );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        Log.d( TAG, "onRestoreInstanceState : enter" );
        super.onRestoreInstanceState( savedInstanceState );

        if( savedInstanceState.containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) ) {
            mTitle = savedInstanceState.getString( RecordingsDataFragment.TITLE_INFO_TITLE );
            Log.d( TAG, "onRestoreInstanceState : mTitle=" + mTitle );
        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    @Override
    public void onSetPrograms( List<Program> programs ) {

        mRecordingsFragment.setPrograms( mTitle, programs );

    }

    @Override
    public void onHandleError( String message ) {

        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();

    }

    @Override
    protected void updateData() {

        RecordingsDataFragment recordingsDataFragment = (RecordingsDataFragment) getFragmentManager().findFragmentByTag( RECORDINGS_DATA_FRAGMENT_TAG );
        if( null == recordingsDataFragment ) {

            Bundle args = new Bundle();
            if( null != mTitle ) {
                args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitle );
            }

            recordingsDataFragment = (RecordingsDataFragment) Fragment.instantiate( this, RecordingsDataFragment.class.getName(), args );
            recordingsDataFragment.setRetainInstance( true );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add( recordingsDataFragment, RECORDINGS_DATA_FRAGMENT_TAG );
            transaction.commit();

        }

    }

    public void onSetProgram( Program program ) {
        Log.d( TAG, "onSetProgram : enter" );

        Bundle args = new Bundle();
        args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, program );

        Intent recordingDetails = new Intent( RecordingsActivity.this, RecordingDetailsActivity.class );
        recordingDetails.putExtras( args );
        startActivity( recordingDetails );

        Log.d( TAG, "onSetProgram : exit" );
    }

}
