package org.mythtv.android.player.videos;

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

import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.ui.adapters.VideoItemAdapter;
import org.mythtv.android.library.ui.data.VideoDataConsumer;
import org.mythtv.android.library.ui.data.VideosDataFragment;
import org.mythtv.android.R;

import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class VideosFragment extends Fragment implements VideoItemAdapter.VideoItemClickListener {

    RecyclerView mRecyclerView;
    VideoItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {

        View view = inflater.inflate( R.layout.video_list, container, false );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.list );
        mLayoutManager = new LinearLayoutManager( getActivity() );
        mRecyclerView.setLayoutManager( mLayoutManager );

        return view;
    }

    public void setVideos( List<Video> videos ) {

        mAdapter = new VideoItemAdapter( videos, this );
        mRecyclerView.setAdapter( mAdapter );

    }

    public void videoItemClicked( Video video ) {

        Bundle args = new Bundle();
        args.putSerializable( VideoDetailsFragment.VIDEO_KEY, video );

        Intent videoDetails = new Intent( getActivity(), VideoDetailsActivity.class );
        videoDetails.putExtras( args );
        startActivity( videoDetails );

    }

}
