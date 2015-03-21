package org.mythtv.android.player.recordings;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.mythtv.android.R;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.ui.data.RecordingDataConsumer;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.player.search.SearchableActivity;

import java.util.List;

public class RecordingsActivity extends Activity implements RecordingDataConsumer {

    private static final String TAG = RecordingsActivity.class.getSimpleName();
    private static final String RECORDINGS_DATA_FRAGMENT_TAG = RecordingsDataFragment.class.getCanonicalName();

    private RecordingsFragment mRecordingsFragment;
    String mTitle = null;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.i( TAG, "onCreate : enter" );

        setContentView( R.layout.activity_recordings );

        if( null != getIntent().getExtras() && getIntent().getExtras().containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) ) {
            Log.d( TAG, "onCreate : retrieved title from intent extras" );

            mTitle = getIntent().getStringExtra( RecordingsDataFragment.TITLE_INFO_TITLE );
        }

        mRecordingsFragment = (RecordingsFragment) getFragmentManager().findFragmentById( R.id.fragment_recordings );

        update();

    }

    @Override
    public boolean onSearchRequested() {

        startActivity( new Intent( this, SearchableActivity.class ) );

        return true;
    }

    @Override
    public void onSetPrograms( List<Program> programs ) {

        mRecordingsFragment.setPrograms( programs );

    }

    @Override
    public void onHandleError( String message ) {

        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();

    }

    public void update() {

        RecordingsDataFragment recordingsDataFragment = (RecordingsDataFragment) getFragmentManager().findFragmentByTag( RECORDINGS_DATA_FRAGMENT_TAG );
        if( null == recordingsDataFragment ) {
            Log.d( TAG, "selectItem : creating new RecordingsDataFragment");

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

}
