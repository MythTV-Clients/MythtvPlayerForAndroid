package org.mythtv.android.app.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.mythtv.android.app.R;
import org.mythtv.android.domain.ContentType;
import org.mythtv.android.app.internal.di.components.VideoComponent;
import org.mythtv.android.app.internal.di.modules.VideosModule;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.presenter.MusicVideoListPresenter;
import org.mythtv.android.presentation.view.VideoListView;
import org.mythtv.android.app.view.adapter.VideosAdapter;
import org.mythtv.android.app.view.adapter.VideosLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dmfrey on 8/31/15.
 */
public class MusicVideoListFragment extends AbstractBaseVideoPagerFragment implements VideoListView {

    private static final String TAG = MusicVideoListFragment.class.getSimpleName();

    @Inject
    MusicVideoListPresenter musicVideoListPresenter;

    @Bind( R.id.rv_videoMetadataInfos )
    RecyclerView rv_videoMetadataInfos;

    @Bind( R.id.rl_progress )
    RelativeLayout rl_progress;

    private VideosAdapter videoMetadataInfosAdapter;

    private VideoListListener videoListListener;

    public MusicVideoListFragment() { super(); }

    public static MusicVideoListFragment newInstance() {

        return new MusicVideoListFragment();
    }

    @Override public void onAttach( Activity activity ) {
        super.onAttach( activity );
        Log.d( TAG, "onAttach : enter" );

        if( activity instanceof VideoListListener ) {
            this.videoListListener = (VideoListListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_app_video_list, container, false );
        ButterKnife.bind( this, fragmentView );
        setupUI();

        Log.d( TAG, "onCreateView : exit" );
        return fragmentView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        this.initialize();
        this.loadMovieList();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.musicVideoListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.musicVideoListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.musicVideoListPresenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    @Override
    public void onDestroyView() {
        Log.d( TAG, "onDestroyView : enter" );
        super.onDestroyView();

        ButterKnife.unbind( this );

        Log.d( TAG, "onDestroyView : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( VideoComponent.class ).inject( this );
        this.musicVideoListPresenter.setView( this );
        this.getComponent( VideoComponent.class ).plus( new VideosModule() );
//        this.musicVideoListPresenter.initialize( contentType );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        this.rv_videoMetadataInfos.setLayoutManager( new VideosLayoutManager( getActivity() ) );

        this.videoMetadataInfosAdapter = new VideosAdapter( getActivity(), new ArrayList<VideoMetadataInfoModel>() );
        this.videoMetadataInfosAdapter.setOnItemClickListener( onItemClickListener );
        this.rv_videoMetadataInfos.setAdapter( videoMetadataInfosAdapter );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

        this.rl_progress.setVisibility( View.VISIBLE );
        this.getActivity().setProgressBarIndeterminateVisibility( true );

        Log.d( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

        this.rl_progress.setVisibility( View.GONE );
        this.getActivity().setProgressBarIndeterminateVisibility( false );

        Log.d( TAG, "hideLoading : exit" );
    }

    @Override
    public void showRetry() {
        Log.d( TAG, "showRetry : enter" );

        Log.d( TAG, "showRetry : exit" );
    }

    @Override
    public void hideRetry() {
        Log.d( TAG, "hideRetry : enter" );

        Log.d( TAG, "hideRetry : exit" );
    }

    @Override
    public void renderVideoList(Collection<VideoMetadataInfoModel> videoMetadataInfoModelCollection ) {
        Log.d( TAG, "renderVideoList : enter" );

        if( null != videoMetadataInfoModelCollection ) {
            Log.d( TAG, "renderVideoList : videoMetadataInfoModelCollection is not null, videoMetadataInfoModelCollection=" + videoMetadataInfoModelCollection );

            this.videoMetadataInfosAdapter.setVideoMetadataInfosCollection( videoMetadataInfoModelCollection );

        }

        Log.d( TAG, "renderVideoList : exit" );
    }

    @Override
    public void viewVideo(VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "viewVideo : enter" );

        if( null != this.videoListListener ) {

            this.videoListListener.onVideoClicked( videoMetadataInfoModel, ContentType.MUSICVIDEO );

        }

        Log.d( TAG, "viewVideo : exit" );
    }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

        this.showToastMessage( message, getResources().getString( R.string.retry ), new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                MusicVideoListFragment.this.loadMovieList();

            }

        });


        Log.d( TAG, "showError : exit" );
    }

    @Override
    public Context getContext() {
        Log.d( TAG, "getContext : enter" );

        Log.d( TAG, "getContext : exit" );
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all musicVideos.
     */
    private void loadMovieList() {
        Log.d( TAG, "loadMovieList : enter" );

        this.musicVideoListPresenter.initialize();

        Log.d( TAG, "loadMovieList : exit" );
    }

    private VideosAdapter.OnItemClickListener onItemClickListener = new VideosAdapter.OnItemClickListener() {

                @Override
                public void onVideoMetadataInfoItemClicked( VideoMetadataInfoModel videoMetadataInfoModel ) {

                    if( null != MusicVideoListFragment.this.musicVideoListPresenter && null != videoMetadataInfoModel ) {

                        MusicVideoListFragment.this.musicVideoListPresenter.onVideoClicked(videoMetadataInfoModel);

                    }

                }

    };

}
