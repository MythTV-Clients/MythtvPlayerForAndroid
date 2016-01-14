package org.mythtv.android.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.mythtv.android.R;
import org.mythtv.android.domain.ContentType;
import org.mythtv.android.presentation.internal.di.components.VideoComponent;
import org.mythtv.android.presentation.internal.di.modules.VideosModule;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.presenter.MovieListPresenter;
import org.mythtv.android.presentation.view.VideoMetadataInfoListView;
import org.mythtv.android.presentation.view.adapter.VideoMetadataInfosAdapter;
import org.mythtv.android.presentation.view.adapter.VideoMetadataInfosLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dmfrey on 8/31/15.
 */
public class MovieListFragment extends BaseVideoPagerFragment implements VideoMetadataInfoListView {

    private static final String TAG = MovieListFragment.class.getSimpleName();

    @Inject
    MovieListPresenter movieListPresenter;

    @Bind( R.id.rv_videoMetadataInfos )
    RecyclerView rv_videoMetadataInfos;

    @Bind( R.id.rl_progress )
    RelativeLayout rl_progress;

    @Bind( R.id.rl_retry )
    RelativeLayout rl_retry;

    @Bind( R.id.bt_retry )
    Button bt_retry;

    private VideoMetadataInfosAdapter videoMetadataInfosAdapter;

    private VideoListListener videoListListener;

    public MovieListFragment() { super(); }

    public static MovieListFragment newInstance() {

        return new MovieListFragment();
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

        View fragmentView = inflater.inflate( R.layout.fragment_video_metadata_info_list, container, false );
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

        this.movieListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.movieListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.movieListPresenter.destroy();

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
        this.movieListPresenter.setView( this );
        this.getComponent( VideoComponent.class ).plus( new VideosModule() );
//        this.movieListPresenter.initialize( contentType );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        this.rv_videoMetadataInfos.setLayoutManager( new VideoMetadataInfosLayoutManager( getActivity() ) );

        this.videoMetadataInfosAdapter = new VideoMetadataInfosAdapter( getActivity(), new ArrayList<VideoMetadataInfoModel>() );
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

        this.rl_retry.setVisibility( View.VISIBLE );

        Log.d( TAG, "showRetry : exit" );
    }

    @Override
    public void hideRetry() {
        Log.d( TAG, "hideRetry : enter" );

        this.rl_retry.setVisibility( View.GONE );

        Log.d( TAG, "hideRetry : exit" );
    }

    @Override
    public void renderVideoMetadataInfoList( Collection<VideoMetadataInfoModel> videoMetadataInfoModelCollection ) {
        Log.d( TAG, "renderVideoMetadataInfoList : enter" );

        if( null != videoMetadataInfoModelCollection ) {
            Log.d( TAG, "renderVideoMetadataInfoList : videoMetadataInfoModelCollection is not null, videoMetadataInfoModelCollection=" + videoMetadataInfoModelCollection );

            this.videoMetadataInfosAdapter.setVideoMetadataInfosCollection( videoMetadataInfoModelCollection );

        }

        Log.d( TAG, "renderVideoMetadataInfoList : exit" );
    }

    @Override
    public void viewVideoMetadataInfo( VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "viewVideoMetadataInfo : enter" );

        if( null != this.videoListListener ) {

            this.videoListListener.onVideoClicked( videoMetadataInfoModel, ContentType.MOVIE );

        }

        Log.d( TAG, "viewVideoMetadataInfo : exit" );
    }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

        this.showToastMessage( message );

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public Context getContext() {
        Log.d( TAG, "getContext : enter" );

        Log.d( TAG, "getContext : exit" );
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all movies.
     */
    private void loadMovieList() {
        Log.d( TAG, "loadMovieList : enter" );

        this.movieListPresenter.initialize();

        Log.d( TAG, "loadMovieList : exit" );
    }

    @OnClick( R.id.bt_retry )
    void onButtonRetryClick() {
        Log.d( TAG, "onButtonRetryClick : enter" );

        MovieListFragment.this.loadMovieList();

        Log.d( TAG, "onButtonRetryClick : exit" );
    }

    private VideoMetadataInfosAdapter.OnItemClickListener onItemClickListener = new VideoMetadataInfosAdapter.OnItemClickListener() {

                @Override
                public void onVideoMetadataInfoItemClicked( VideoMetadataInfoModel videoMetadataInfoModel ) {

                    if( null != MovieListFragment.this.movieListPresenter && null != videoMetadataInfoModel ) {

                        MovieListFragment.this.movieListPresenter.onVideoClicked( videoMetadataInfoModel );

                    }

                }

    };

}
