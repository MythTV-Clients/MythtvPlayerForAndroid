package org.mythtv.android.player.recordings;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.ui.adapters.ProgramItemAdapter;
import org.mythtv.android.R;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class RecordingsFragment extends Fragment implements ProgramItemAdapter.ProgramItemClickListener {

    RecyclerView mRecyclerView;
    ProgramItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.program_list, container, false );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );

        return view;
    }

    public void setPrograms( String title, List<Program> programs ) {

        mAdapter = new ProgramItemAdapter( programs, this, null == title );
        mRecyclerView.setAdapter( mAdapter );

        if( null == title || "".equals( title ) ) {
            title = getResources().getString( R.string.all_recordings );
        }

        ( (ActionBarActivity) getActivity() ).getSupportActionBar().setTitle( title );

    }

    public void onProgramItemClicked( Program program ) {

        Bundle args = new Bundle();
        args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, program );

        Intent recordingDetails = new Intent( getActivity(), RecordingDetailsActivity.class );
        recordingDetails.putExtras( args );
        startActivity(recordingDetails);

    }

}
