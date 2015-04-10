package org.mythtv.android.player.tv.recordings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.player.common.ui.data.RecordingsDataFragment;
import org.mythtv.android.player.tv.search.SearchableActivity;

public class RecordingsActivity extends Activity {

    private static final String TAG = RecordingsActivity.class.getSimpleName();
    private static final String RECORDINGS_DATA_FRAGMENT_TAG = RecordingsDataFragment.class.getCanonicalName();

    private RecordingsFragment mRecordingsFragment;
    String mTitle = null;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.i( TAG, "onCreate : enter" );

        setContentView( R.layout.activity_tv_recordings );

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

    public void update() {

        mRecordingsFragment.reload();

    }

}
