package org.mythtv.android.player.tv.recordings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.player.tv.search.SearchableActivity;

public class RecordingsActivity extends Activity {

    private static final String TAG = RecordingsActivity.class.getSimpleName();

    private RecordingsFragment mRecordingsFragment;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.i( TAG, "onCreate : enter" );

        setContentView( R.layout.activity_tv_recordings );

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
