package org.mythtv.android.player.recordings;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.mythtv.android.library.core.domain.dvr.ChannelInfo;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.domain.dvr.RecordingInfo;
import org.mythtv.android.library.persistence.domain.dvr.ProgramConstants;
import org.mythtv.android.library.persistence.domain.dvr.TitleInfoConstants;
import org.mythtv.android.library.ui.adapters.ProgramItemAdapter;
import org.mythtv.android.R;
import org.mythtv.android.player.AbstractBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class RecordingsFragment extends AbstractBaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, ProgramItemAdapter.ProgramItemClickListener {

    private static final String PROGRAM_TITLE_KEY = "program_title";

    RecyclerView mRecyclerView;
    ProgramItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    TextView mEmpty;
    boolean mShowTitle = false;

    @Override
    public Loader<Cursor> onCreateLoader( int id, Bundle args ) {

        String[] projection = new String[]{ProgramConstants._ID, ProgramConstants.FIELD_PROGRAM_START_TIME, ProgramConstants.FIELD_PROGRAM_TITLE, ProgramConstants.FIELD_PROGRAM_SUB_TITLE, ProgramConstants.FIELD_PROGRAM_INETREF, ProgramConstants.FIELD_PROGRAM_DESCRIPTION, ProgramConstants.FIELD_CHANNEL_CHAN_ID, ProgramConstants.FIELD_RECORDING_START_TS };
        String selection = ProgramConstants.FIELD_PROGRAM_TYPE + " = ? AND " + ProgramConstants.FIELD_RECORDING_REC_GROUP + " != ?";
        String[] selectionArgs = new String[] { ProgramConstants.ProgramType.RECORDED.name(), "LiveTV" };
        String sort = ProgramConstants.FIELD_PROGRAM_END_TIME + " desc";

        if( args.containsKey( PROGRAM_TITLE_KEY ) ) {
            selection = ProgramConstants.FIELD_PROGRAM_TITLE + " = ?";
            selectionArgs = new String[] { args.getString( PROGRAM_TITLE_KEY ) };
        }

        return new CursorLoader( getActivity(), ProgramConstants.CONTENT_URI, projection, selection, selectionArgs, sort );
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data ) {

        List<Program> programs = new ArrayList<Program>();
        while( data.moveToNext() ) {

            Program program = new Program();
            program.setStartTime( new DateTime( data.getLong( data.getColumnIndex( ProgramConstants.FIELD_PROGRAM_START_TIME ) ) ) );
            program.setTitle( data.getString( data.getColumnIndex( ProgramConstants.FIELD_PROGRAM_TITLE ) ) );
            program.setSubTitle( data.getString( data.getColumnIndex( ProgramConstants.FIELD_PROGRAM_SUB_TITLE ) ) );
            program.setInetref( data.getString( data.getColumnIndex( ProgramConstants.FIELD_PROGRAM_INETREF ) ) );
            program.setDescription( data.getString( data.getColumnIndex( ProgramConstants.FIELD_PROGRAM_DESCRIPTION ) ) );

            ChannelInfo channel = new ChannelInfo();
            channel.setChanId( data.getInt( data.getColumnIndex( ProgramConstants.FIELD_CHANNEL_CHAN_ID ) ) );
            program.setChannel( channel );

            RecordingInfo recording = new RecordingInfo();
            program.setStartTime( new DateTime( data.getLong( data.getColumnIndex( ProgramConstants.FIELD_RECORDING_START_TS ) ) ) );
            program.setRecording( recording );

            programs.add( program );

        }
        data.close();

        if( !programs.isEmpty() ) {

            mAdapter = new ProgramItemAdapter( programs, this, mShowTitle );
            mRecyclerView.setAdapter( mAdapter );

        }

    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader ) {

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.program_list, container, false );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );
        mEmpty = (TextView) view.findViewById( R.id.empty );

        return view;
    }

    public void setPrograms( String title, List<Program> programs ) {

        mShowTitle = ( null == title );

        Bundle args = new Bundle();
        if( null != title ) {
            args.putString( PROGRAM_TITLE_KEY, title );
        }

        getLoaderManager().initLoader( 0, args, this );

    }

    public void onProgramItemClicked( View v, Program program ) {

        Bundle args = new Bundle();
        args.putSerializable( RecordingDetailsFragment.PROGRAM_KEY, program );

        Intent recordingDetails = new Intent( getActivity(), RecordingDetailsActivity.class );
        recordingDetails.putExtras(args);

        String transitionName = getString( R.string.recording_transition );
        ActivityOptionsCompat options =
            ActivityOptionsCompat.makeSceneTransitionAnimation( getActivity(),
                v,   // The view which starts the transition
                transitionName    // The transitionName of the view we’re transitioning to
            );
        ActivityCompat.startActivity( getActivity(), recordingDetails, options.toBundle() );

    }

    @Override
    public void connected() {

        mRecyclerView.setVisibility( View.VISIBLE );
        mEmpty.setVisibility(View.GONE);

    }

    @Override
    public void notConnected() {

        mRecyclerView.setVisibility(View.GONE);
        mEmpty.setVisibility(View.VISIBLE);

    }

}
