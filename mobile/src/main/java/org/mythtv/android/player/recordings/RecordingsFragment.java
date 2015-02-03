package org.mythtv.android.player.recordings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.ui.adapters.ProgramItemAdapter;
import org.mythtv.android.library.ui.data.RecordingDataConsumer;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.R;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class RecordingsFragment extends Fragment implements RecordingDataConsumer, ProgramItemAdapter.ProgramItemClickListener {

    private static final String TAG = RecordingsFragment.class.getSimpleName();
    private static final String RECORDINGS_DATA_FRAGMENT_TAG = RecordingsDataFragment.class.getCanonicalName();

    RecyclerView mRecyclerView;
    ProgramItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    OnRecordingClickedListener mListener;

    public interface OnRecordingClickedListener {

        public void onSetProgram( Program program );

    }

    public static RecordingsFragment newInstance( String title ) {

        Bundle args = new Bundle();
        args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, title );

        RecordingsFragment f = new RecordingsFragment();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d(TAG, "onCreateView : enter");

        RecordingsDataFragment recordingsDataFragment = (RecordingsDataFragment) getChildFragmentManager().findFragmentByTag( RECORDINGS_DATA_FRAGMENT_TAG );
        if( null == recordingsDataFragment ) {
            Log.d( TAG, "selectItem : creating new RecordingsDataFragment");

            recordingsDataFragment = (RecordingsDataFragment) Fragment.instantiate( getActivity(), RecordingsDataFragment.class.getName(), getArguments() );
            recordingsDataFragment.setRetainInstance( true );
            recordingsDataFragment.setConsumer( this );

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.add( recordingsDataFragment, RECORDINGS_DATA_FRAGMENT_TAG );
            transaction.commit();

        }

        Log.d( TAG, "onCreateView : exit" );
        return inflater.inflate( R.layout.program_list, container, false );
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated(savedInstanceState);
        Log.d( TAG, "onActivityCreated : enter" );

        String title = getArguments().getString( RecordingsDataFragment.TITLE_INFO_TITLE );
        if( null == title || "".equals( title ) ) {
            title = getResources().getString( R.string.all_recordings );
        }

        ( (ActionBarActivity) getActivity() ).getSupportActionBar().setTitle( title );

        mRecyclerView = (RecyclerView) getView().findViewById( R.id.list );

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach(activity);
        Log.d( TAG, "onAttach : enter" );

        try {

            mListener = (OnRecordingClickedListener) activity;

        } catch( ClassCastException e ) {
            throw new ClassCastException( activity.toString() + " must implement OnRecordingClickedListener" );
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public void onSetPrograms( List<Program> programs ) {
        Log.d( TAG, "onSetPrograms : enter" );

        mAdapter = new ProgramItemAdapter( programs, this, !getArguments().containsKey( RecordingsDataFragment.TITLE_INFO_TITLE ) );
        mRecyclerView.setAdapter( mAdapter );

        Log.d( TAG, "onSetPrograms : exit" );
    }

    @Override
    public void onHandleError(String message) {
        Log.d( TAG, "onHandleError : enter" );

        Toast.makeText( getActivity(), message, Toast.LENGTH_LONG ).show();

        Log.d( TAG, "onHandleError : exit" );
    }

    public void onProgramItemClicked( Program program ) {
        Log.d( TAG, "onProgramItemClicked : enter" );

        mListener.onSetProgram( program );

        Log.d( TAG, "onProgramItemClicked : exit" );
    }

}
