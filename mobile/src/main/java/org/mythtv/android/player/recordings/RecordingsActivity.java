package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.player.R;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingsActivity extends ActionBarActivity {

    private static final String TAG = RecordingsActivity.class.getSimpleName();

    private static final String RECORDINGS_FRAGMENT_TAG = RecordingsFragment.class.getCanonicalName();

    String mTitle = null;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate : enter");

        setContentView( R.layout.activity_recordings );

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey(RecordingsDataFragment.TITLE_INFO_TITLE ) ) {
            mTitle = getIntent().getStringExtra( RecordingsDataFragment.TITLE_INFO_TITLE );
        }

        if( null != mTitle && !"".equals( mTitle ) ) {
            getSupportActionBar().setTitle( mTitle );
        } else {
            getSupportActionBar().setTitle( getResources().getString( R.string.all_recordings ) );
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        RecordingsFragment recordingsFragment = (RecordingsFragment) getFragmentManager().findFragmentByTag( RECORDINGS_FRAGMENT_TAG );
        if( null == recordingsFragment ) {
            Log.d( TAG, "onCreate : creating new RecordingsFragment" );

            Bundle args = new Bundle();
            if( null != mTitle ) {
                args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitle );
            }

            recordingsFragment = (RecordingsFragment) Fragment.instantiate( this, RecordingsFragment.class.getName(), args );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace( R.id.content_frame, recordingsFragment, RECORDINGS_FRAGMENT_TAG );
            transaction.addToBackStack( null );
            transaction.commit();
        }

        Log.v( TAG, "onCreate : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );
        Log.v( TAG, "onSaveInstanceState : enter" );

        outState.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitle );

        Log.v(TAG, "onSaveInstanceState : exit");
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState( savedInstanceState );
        Log.v( TAG, "onRestoreInstanceState : enter" );

        if( savedInstanceState.containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) ) {
            mTitle = savedInstanceState.getString( RecordingsDataFragment.TITLE_INFO_TITLE );
        }

        Log.v( TAG, "onRestoreInstanceState : exit" );
    }

}
