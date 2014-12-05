package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.ui.adapters.ProgramAdapter;
import org.mythtv.android.library.ui.data.RecordingDataConsumer;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.player.R;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class RecordingsFragment extends Fragment implements RecordingDataConsumer, ProgramAdapter.ProgramClickListener {

    private static final String TAG = RecordingsFragment.class.getSimpleName();
    private static final String RECORDING_DATA_FRAGMENT_TAG = RecordingsDataFragment.class.getCanonicalName();

    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.v( TAG, "onCreateView : enter" );

        View rootView = inflater.inflate( R.layout.program_list, container, false );
        rootView.setTag( TAG );

        mRecyclerView = (RecyclerView) rootView.findViewById( R.id.list );

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );

        RecordingsDataFragment recordingsDataFragment = (RecordingsDataFragment) getFragmentManager().findFragmentByTag( RECORDING_DATA_FRAGMENT_TAG );
        if( null == recordingsDataFragment ) {
            Log.d( TAG, "onCreateView : creating new RecordingsDataFragment");

            recordingsDataFragment = (RecordingsDataFragment) Fragment.instantiate( getActivity(), RecordingsDataFragment.class.getName() );
            recordingsDataFragment.setRetainInstance( true );
            recordingsDataFragment.setRecordingDataConsumer( this );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add( recordingsDataFragment, RECORDING_DATA_FRAGMENT_TAG );
            transaction.commit();

        }

        Log.v( TAG, "onCreateView : exit" );
        return rootView;
    }

    public void setPrograms( List<Program> programs ) {
        Log.v( TAG, "setPrograms : enter" );

        Log.v( TAG, "setPrograms : programs=" + programs );
        mAdapter = new ProgramAdapter( programs, this );
        mRecyclerView.setAdapter( mAdapter );

        Log.v( TAG, "setPrograms : exit" );
    }

    public void handleError( String message ) {
        Log.v( TAG, "handleError : enter" );

        Toast.makeText( getActivity(), message, Toast.LENGTH_LONG ).show();

        Log.v( TAG, "handleError : exit" );
    }

    public void programClicked( Program program ) {
        Log.v( TAG, "programClicked : enter" );

        Log.v( TAG, "programClicked : exit" );
    }

}
