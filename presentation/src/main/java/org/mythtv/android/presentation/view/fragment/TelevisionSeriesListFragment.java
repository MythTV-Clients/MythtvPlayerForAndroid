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
import org.mythtv.android.presentation.internal.di.components.VideoComponent;
import org.mythtv.android.presentation.internal.di.modules.VideosModule;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.presenter.TelevisionListPresenter;
import org.mythtv.android.presentation.presenter.TelevisionSeriesListPresenter;
import org.mythtv.android.presentation.view.VideoMetadataInfoListView;
import org.mythtv.android.presentation.view.adapter.VideoMetadataInfosAdapter;
import org.mythtv.android.presentation.view.adapter.VideoMetadataInfosLayoutManager;
import org.mythtv.android.presentation.view.adapter.VideoSeriesAdapter;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dmfrey on 8/31/15.
 */
public class TelevisionSeriesListFragment extends BaseFragment implements VideoMetadataInfoListView {

    private static final String TAG = TelevisionSeriesListFragment.class.getSimpleName();

    private static final String ARGUMENT_KEY_SERIES = "org.mythtv.android.ARGUMENT_SERIES";

    private String series;

    /**
     * Interface for listening videoMetadataInfo list events.
     */
    public interface VideoMetadataInfoListListener {

        void onVideoMetadataInfoClicked( final VideoMetadataInfoModel videoMetadataInfoModel );

    }

    @Inject
    TelevisionSeriesListPresenter televisionSeriesListPresenter;

    @Bind( R.id.rv_videoMetadataInfos )
    RecyclerView rv_videoMetadataInfos;

    @Bind( R.id.rl_progress )
    RelativeLayout rl_progress;

    @Bind( R.id.rl_retry )
    RelativeLayout rl_retry;

    @Bind( R.id.bt_retry )
    Button bt_retry;

    private VideoSeriesAdapter videoSeriesAdapter;

    private VideoMetadataInfoListListener videoMetadataInfoListListener;

    public TelevisionSeriesListFragment() { super(); }

    public static TelevisionSeriesListFragment newInstance( String series ) {

        TelevisionSeriesListFragment fragment = new TelevisionSeriesListFragment();

        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putString( ARGUMENT_KEY_SERIES, series );
        fragment.setArguments( argumentsBundle );

        return fragment;
    }

    @Override public void onAttach( Activity activity ) {
        super.onAttach( activity );
        Log.d( TAG, "onAttach : enter" );

        if( activity instanceof VideoMetadataInfoListListener ) {
            this.videoMetadataInfoListListener = (VideoMetadataInfoListListener) activity;
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
        this.loadTelevisionSeriesList();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.televisionSeriesListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.televisionSeriesListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.televisionSeriesListPresenter.destroy();

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
        this.televisionSeriesListPresenter.setView( this );
        this.series = getArguments().getString( ARGUMENT_KEY_SERIES );
        this.getComponent( VideoComponent.class ).plus( new VideosModule() );

        this.televisionSeriesListPresenter.initialize( this.series );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        this.rv_videoMetadataInfos.setLayoutManager( new VideoMetadataInfosLayoutManager( getActivity() ) );

        this.videoSeriesAdapter = new VideoSeriesAdapter( getActivity(), new ArrayList<VideoMetadataInfoModel>() );
        this.videoSeriesAdapter.setOnItemClickListener( onItemClickListener );
        this.rv_videoMetadataInfos.setAdapter( videoSeriesAdapter );

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

            this.videoSeriesAdapter.setVideoSeriesCollection( videoMetadataInfoModelCollection );

        }

        Log.d( TAG, "renderVideoMetadataInfoList : exit" );
    }

    @Override
    public void viewVideoMetadataInfo( VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "viewVideoMetadataInfo : enter" );

        if( null != this.videoMetadataInfoListListener ) {

            this.videoMetadataInfoListListener.onVideoMetadataInfoClicked( videoMetadataInfoModel );

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
     * Loads all tv series.
     */
    private void loadTelevisionSeriesList() {
        Log.d( TAG, "loadTelevisionSeriesList : enter" );

        if( null != this.televisionSeriesListPresenter ) {

            this.televisionSeriesListPresenter.initialize( series );

        }

        Log.d( TAG, "loadTelevisionSeriesList : exit" );
    }

    @OnClick( R.id.bt_retry )
    void onButtonRetryClick() {
        Log.d( TAG, "onButtonRetryClick : enter" );

        TelevisionSeriesListFragment.this.loadTelevisionSeriesList();

        Log.d( TAG, "onButtonRetryClick : exit" );
    }

    private VideoSeriesAdapter.OnItemClickListener onItemClickListener = new VideoSeriesAdapter.OnItemClickListener() {

                @Override
                public void onVideoMetadataInfoItemClicked( VideoMetadataInfoModel videoMetadataInfoModel ) {

                    if( null != TelevisionSeriesListFragment.this.televisionSeriesListPresenter && null != videoMetadataInfoModel ) {

                        TelevisionSeriesListFragment.this.televisionSeriesListPresenter.onVideoClicked( videoMetadataInfoModel );

                    }

                }

    };

}
