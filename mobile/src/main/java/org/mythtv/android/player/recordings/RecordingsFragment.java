package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.ui.adapters.ProgramAdapter;
import org.mythtv.android.player.R;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class RecordingsFragment extends Fragment implements ProgramAdapter.ProgramClickListener {

    private static final String TAG = RecordingsFragment.class.getSimpleName();

    RecyclerView mRecyclerView;
    ProgramAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.v( TAG, "onCreateView : enter" );

        Log.v( TAG, "onCreateView : exit" );
        return inflater.inflate( R.layout.program_list, container, false );
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        Log.v( TAG, "onActivityCreated : enter" );

        mRecyclerView = (RecyclerView) getView().findViewById( R.id.list );

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );

        mAdapter = new ProgramAdapter( ( (MainApplication) getActivity().getApplicationContext() ).getPrograms(), this );
        Log.v( TAG, "onCreateView : mAdapter count=" + mAdapter.getItemCount() );
        mRecyclerView.setAdapter( mAdapter );

        Log.v( TAG, "onActivityCreated : exit" );
    }

    public void programClicked( Program program ) {
        Log.v( TAG, "programClicked : enter" );

        Log.v( TAG, "programClicked : exit" );
    }

}
