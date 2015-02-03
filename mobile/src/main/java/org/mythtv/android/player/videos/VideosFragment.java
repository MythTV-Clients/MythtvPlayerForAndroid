package org.mythtv.android.player.videos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
public class VideosFragment extends Fragment implements VideoDataConsumer, VideoItemAdapter.VideoItemClickListener {

    private static final String TAG = VideosFragment.class.getSimpleName();
    private static final String VIDEOS_DATA_FRAGMENT_TAG = VideosDataFragment.class.getCanonicalName();

    RecyclerView mRecyclerView;
    VideoItemAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        VideosDataFragment videosDataFragment = (VideosDataFragment) getChildFragmentManager().findFragmentByTag(VIDEOS_DATA_FRAGMENT_TAG);
        if( null == videosDataFragment ) {
            Log.d( TAG, "selectItem : creating new VideosDataFragment");

            videosDataFragment = (VideosDataFragment) Fragment.instantiate( getActivity(), VideosDataFragment.class.getName() );
            videosDataFragment.setRetainInstance( true );
            videosDataFragment.setConsumer( this );

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.add( videosDataFragment, VIDEOS_DATA_FRAGMENT_TAG );
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
    public void setVideos( List<Video> videos ) {
        Log.d( TAG, "setVideos : enter" );

        mAdapter = new VideoItemAdapter( videos, this );
        mRecyclerView.setAdapter( mAdapter );

        Log.d( TAG, "setVideos : exit" );
    }

    @Override
    public void handleError( String message ) {
        Log.d( TAG, "handleError : enter" );

        Toast.makeText( getActivity(), message, Toast.LENGTH_LONG ).show();

        Log.d( TAG, "handleError : exit" );
    }

    public void videoItemClicked(Video video) {
        Log.d( TAG, "videoItemClicked : enter" );

        Bundle args = new Bundle();
        args.putSerializable( VideoDetailsFragment.VIDEO_KEY, video );

        Intent videoDetails = new Intent( getActivity(), VideoDetailsActivity.class );
        videoDetails.putExtras( args );
        startActivity( videoDetails );

        Log.d( TAG, "videoItemClicked : exit" );
    }

}
