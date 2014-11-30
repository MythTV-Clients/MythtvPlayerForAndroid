package org.mythtv.android.player.ui.recordings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.mythtv.android.library.R;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.ui.adapters.ProgramAdapter;
import org.mythtv.android.library.ui.data.RecordingDataConsumer;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;

import java.util.List;
import java.util.Map;

/**
 * Created by dmfrey on 11/29/14.
 */
public class RecordingListActivity extends ActionBarActivity implements RecordingDataConsumer, ProgramAdapter.ProgramClickListener {

    private static final String TAG = RecordingListActivity.class.getSimpleName();
    private static final String RECORDING_DATA_FRAGMENT_TAG = RecordingsDataFragment.class.getCanonicalName();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        Log.v( TAG, "onCreate : enter" );

        setContentView( R.layout.program_list );

        recyclerView = (RecyclerView) findViewById( R.id.list );

        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        recyclerView.setLayoutManager( layoutManager );

        RecordingsDataFragment recordingsDataFragment = (RecordingsDataFragment) getFragmentManager().findFragmentByTag( RECORDING_DATA_FRAGMENT_TAG );
        if( null == recordingsDataFragment ) {
            Log.d( TAG, "onCreate : creating new RecordinsDataFragment");

            recordingsDataFragment = (RecordingsDataFragment) Fragment.instantiate( this, RecordingsDataFragment.class.getName() );
            recordingsDataFragment.setRetainInstance( true );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add( recordingsDataFragment, RECORDING_DATA_FRAGMENT_TAG );
            transaction.commit();

        }

        Log.v( TAG, "onCreate : exit" );
    }

    public void setPrograms( List<Program> programs ) {

        ProgramAdapter adapter = new ProgramAdapter( programs, this );
        recyclerView.setAdapter( adapter );

    }

    public void setPrograms( Map<String, String> mCategories, Map<String, List<Program>> mPrograms ) {

    }

    public void handleError( String message ) {

        Toast.makeText( this, message, Toast.LENGTH_LONG ).show();

    }

    public void programClicked( Program program ) {

    }

}
