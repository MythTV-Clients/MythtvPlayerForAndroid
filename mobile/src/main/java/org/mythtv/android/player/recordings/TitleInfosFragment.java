package org.mythtv.android.player.recordings;

import android.app.Activity;
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

import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.ui.adapters.TitleInfoItemAdapter;
import org.mythtv.android.library.ui.data.RecordingsDataFragment;
import org.mythtv.android.library.ui.data.TitleInfoDataConsumer;
import org.mythtv.android.library.ui.data.TitleInfosDataFragment;
import org.mythtv.android.R;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class TitleInfosFragment extends Fragment implements TitleInfoItemAdapter.TitleInfoItemClickListener {

    private static final String TAG = TitleInfosFragment.class.getSimpleName();

    RecyclerView mRecyclerView;
    TitleInfoItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View view = inflater.inflate( R.layout.program_list, container, false );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );

        Log.d( TAG, "onCreateView : exit" );
        return view;
    }

    public void setTitleInfos( List<TitleInfo> titleInfos ) {
        Log.d( TAG, "setTitleInfos : enter" );

        mAdapter = new TitleInfoItemAdapter( titleInfos, this );
        mRecyclerView.setAdapter(mAdapter);

        Log.d( TAG, "onSetPrograms : exit" );
    }

    public void titleInfoItemClicked( TitleInfo titleInfo ) {
        Log.d(TAG, "titleInfoItemClicked : enter");

        Bundle args = new Bundle();
        if( !getActivity().getResources().getString( R.string.all_recordings ).equals( titleInfo.getTitle() ) ) {
            args.putString( RecordingsDataFragment.TITLE_INFO_TITLE, titleInfo.getTitle() );
        }

        Intent recordings = new Intent( getActivity(), RecordingsActivity.class );
        recordings.putExtras( args );
        startActivity( recordings );

        Log.d( TAG, "titleInfoItemClicked : exit" );
    }

}
