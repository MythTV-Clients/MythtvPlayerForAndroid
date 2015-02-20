package org.mythtv.android.player.recordings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.ui.adapters.TitleInfoItemAdapter;
import org.mythtv.android.R;
import org.mythtv.android.player.AbstractBaseFragment;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class TitleInfosFragment extends AbstractBaseFragment implements TitleInfoItemAdapter.TitleInfoItemClickListener {

    RecyclerView mRecyclerView;
    TitleInfoItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    TextView mEmpty;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.title_info_list, container, false );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager(mLayoutManager);
        mEmpty = (TextView) view.findViewById( R.id.empty );

        return view;
    }

    public void setTitleInfos( List<TitleInfo> titleInfos ) {

        mAdapter = new TitleInfoItemAdapter( titleInfos, this );
        mRecyclerView.setAdapter(mAdapter);

    }

    public void titleInfoItemClicked( TitleInfo titleInfo ) {

        Bundle args = new Bundle();
        if( !getActivity().getResources().getString( R.string.all_recordings ).equals( titleInfo.getTitle() ) ) {

            args.putSerializable( RecordingsActivity.TITLE_INFO, titleInfo );

        }

        Intent recordings = new Intent( getActivity(), RecordingsActivity.class );
        recordings.putExtras(args);
        startActivity(recordings);

    }

    @Override
    public void connected() {

        mRecyclerView.setVisibility( View.VISIBLE );
        mEmpty.setVisibility( View.GONE );

    }

    @Override
    public void notConnected() {

        mRecyclerView.setVisibility( View.GONE );
        mEmpty.setVisibility( View.VISIBLE );

    }

}
