package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private static final String RECORDINGS_DATA_FRAGMENT_TAG = RecordingsDataFragment.class.getCanonicalName();

    RecyclerView mRecyclerView;
    ProgramAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    public static RecordingsFragment newInstance( String title ) {

        Bundle args = new Bundle();
        args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, title );

        RecordingsFragment f = new RecordingsFragment();
        f.setArguments( args );

        return f;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        RecordingsDataFragment recordingsDataFragment = (RecordingsDataFragment) getChildFragmentManager().findFragmentByTag( RECORDINGS_DATA_FRAGMENT_TAG );
        if( null == recordingsDataFragment ) {
            Log.d( TAG, "selectItem : creating new RecordingsDataFragment");

            recordingsDataFragment = (RecordingsDataFragment) Fragment.instantiate( getActivity(), RecordingsDataFragment.class.getName(), getArguments() );
            recordingsDataFragment.setRetainInstance( true );
            recordingsDataFragment.setConsumer( this );

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add( recordingsDataFragment, RECORDINGS_DATA_FRAGMENT_TAG );
            transaction.commit();

        }

        Log.d( TAG, "onCreateView : exit" );
        return inflater.inflate( R.layout.program_list, container, false );
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        Log.d( TAG, "onActivityCreated : enter" );

        mRecyclerView = (RecyclerView) getView().findViewById( R.id.list );

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void setPrograms( List<Program> programs ) {
        Log.d( TAG, "setPrograms : enter" );

        mAdapter = new ProgramAdapter( programs, this, !getArguments().containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) );
        mRecyclerView.setAdapter( mAdapter );

        Log.d( TAG, "setPrograms : exit" );
    }

    @Override
    public void handleError( String message ) {
        Log.d( TAG, "handleError : enter" );

        Toast.makeText( getActivity(), message, Toast.LENGTH_LONG ).show();

        Log.d( TAG, "handleError : exit" );
    }

    public void programClicked( Program program ) {
        Log.d( TAG, "programClicked : enter" );

        Bundle args = new Bundle();
        args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, program );

        Intent intent = new Intent( getActivity(), RecordingDetailsActivity.class );
        intent.putExtras( args );
        startActivity( intent );

        Log.d( TAG, "programClicked : exit" );
    }

}
