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

    private static final String RECORDINGS_FRAGMENT_TAG = RecordingsDataFragment.class.getCanonicalName();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.v( TAG, "onCreate : enter" );

        setContentView( R.layout.activity_recordings );

        if( getIntent().getExtras().containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) ) {
            getSupportActionBar().setTitle( getIntent().getStringExtra( RecordingsDataFragment.TITLE_INFO_TITLE ) );
        } else {
            getSupportActionBar().setTitle( getResources().getString( R.string.all_recordings ) );
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        RecordingsFragment recordingsFragment = (RecordingsFragment) getFragmentManager().findFragmentByTag( RECORDINGS_FRAGMENT_TAG );
        if( null == recordingsFragment ) {
            Log.d( TAG, "onCreate : creating new RecordingsFragment" );

            recordingsFragment = (RecordingsFragment) Fragment.instantiate( this, RecordingsFragment.class.getName(), getIntent().getExtras() );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace( R.id.content_frame, recordingsFragment, RECORDINGS_FRAGMENT_TAG );
            transaction.addToBackStack( null );
            transaction.commit();
        }

        Log.v( TAG, "onCreate : exit" );
    }

}
