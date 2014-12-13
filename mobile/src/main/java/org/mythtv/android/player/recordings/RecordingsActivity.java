package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.player.BaseActionBarActivity;
import org.mythtv.android.player.R;

/**
 * Created by dmfrey on 12/8/14.
 */
public class RecordingsActivity extends BaseActionBarActivity {

    private static final String TAG = RecordingsActivity.class.getSimpleName();

    private static final String RECORDINGS_FRAGMENT_TAG = RecordingsFragment.class.getCanonicalName();

    String mTitle = null;

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
            Log.d( TAG, "onResume : retrieved title from intent extras" );

            mTitle = getIntent().getStringExtra( RecordingsDataFragment.TITLE_INFO_TITLE );
        }

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d( TAG, "onResume : enter" );

        if( null != mTitle && !"".equals( mTitle ) ) {
            getSupportActionBar().setTitle( mTitle );
        } else {
            getSupportActionBar().setTitle( getResources().getString( R.string.all_recordings ) );
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        RecordingsFragment recordingsFragment = (RecordingsFragment) getFragmentManager().findFragmentByTag( RECORDINGS_FRAGMENT_TAG );
        if( null == recordingsFragment ) {
            Log.d( TAG, "onResume : creating new RecordingsFragment" );

            Bundle args = new Bundle();
            if( null != mTitle ) {
                args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitle );
            }

            recordingsFragment = (RecordingsFragment) Fragment.instantiate( this, RecordingsFragment.class.getName(), args );

        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace( R.id.content_frame, recordingsFragment, RECORDINGS_FRAGMENT_TAG );
        transaction.addToBackStack( null );
        transaction.commit();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        Log.d( TAG, "onSaveInstanceState : mTitle=" + mTitle );
        outState.putString( RecordingsDataFragment.TITLE_INFO_TITLE, mTitle );

        Log.d(TAG, "onSaveInstanceState : exit");
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

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

}
