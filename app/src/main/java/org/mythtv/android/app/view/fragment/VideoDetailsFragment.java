package org.mythtv.android.app.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mythtv.android.R;
import org.mythtv.android.app.internal.di.components.VideoComponent;
import org.mythtv.android.presentation.VideoDetailsView;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.presenter.VideoDetailsPresenter;
import org.mythtv.android.presentation.view.component.AutoLoadImageView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dmfrey on 8/31/15.
 */
public class VideoDetailsFragment extends AbstractBaseFragment implements VideoDetailsView, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = VideoDetailsFragment.class.getSimpleName();

    public interface VideoDetailsListener {

        void onVideoLoaded( final VideoMetadataInfoModel videoMetadataInfoModel );

    }

    private VideoDetailsListener listener;

    @Inject
    VideoDetailsPresenter presenter;

    @Bind( R.id.video_coverart )
    AutoLoadImageView iv_coverart;

    @Bind( R.id.video_show_name )
    TextView tv_showname;

    @Bind( R.id.video_episode_name )
    TextView tv_episodename;

    @Bind( R.id.video_progress )
    ProgressBar pb_progress;

    @Bind( R.id.video_description )
    TextView tv_description;

    @Bind( R.id.rl_progress )
    RelativeLayout rl_progress;

    @Bind( R.id.watched_switch )
    SwitchCompat watched;

    @Bind( R.id.hsl_stream_switch )
    SwitchCompat hls_stream;

    public VideoDetailsFragment() { super(); }

    public static VideoDetailsFragment newInstance() {

        return new VideoDetailsFragment();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_video_details, container, false );
        ButterKnife.bind( this, fragmentView );

        Log.d( TAG, "onCreateView : exit" );
        return fragmentView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        this.initialize();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        Log.d( TAG, "onAttach : enter" );

        if( activity instanceof VideoDetailsListener ) {

            this.listener = (VideoDetailsListener) activity;

        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.presenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.presenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroyView() {
        Log.d( TAG, "onDestroyView : enter" );
        super.onDestroyView();

        ButterKnife.unbind( this );

        Log.d( TAG, "onDestroyView : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.presenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    @Override
    public void onCheckedChanged( CompoundButton buttonView, boolean isChecked ) {
        Log.d( TAG, "onCheckedChanged : enter" );

        switch( buttonView.getId() ) {

            case R.id.watched_switch :

                this.presenter.updateWatchedStatus();

                break;

            case R.id.hsl_stream_switch :

                this.presenter.updateHlsStream();

                break;

        }

        Log.d( TAG, "onCheckedChanged : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( VideoComponent.class ).inject( this );
        this.presenter.setView( this );

        loadVideoDetails();

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public void renderVideo( VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "renderVideo : enter" );

        if( null != videoMetadataInfoModel ) {
            Log.d( TAG, "renderVideo : video is not null" );

            ActionBar actionBar = ( (AppCompatActivity) getActivity() ).getSupportActionBar();
            actionBar.setTitle( videoMetadataInfoModel.getTitle() );

            this.iv_coverart.setImageUrl( getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + videoMetadataInfoModel.getId() + "&Type=coverart&Width=150" );
            this.tv_showname.setText( videoMetadataInfoModel.getTitle() );
            this.tv_episodename.setText( videoMetadataInfoModel.getSubTitle() );
            this.tv_description.setText( videoMetadataInfoModel.getDescription() );

            updateWatchedStatus( videoMetadataInfoModel );
            updateLiveStream( videoMetadataInfoModel );

        }

        Log.d( TAG, "renderProgram : exit" );
    }

    @Override
    public void updateLiveStream( VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "updateLiveStream : enter" );

        updateLiveStreamControls( videoMetadataInfoModel.getLiveStreamInfo() );

        this.listener.onVideoLoaded( videoMetadataInfoModel );

        Log.d( TAG, "updateLiveStream : exit" );
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
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

        this.showToastMessage( message, getResources().getString( R.string.retry ), new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                VideoDetailsFragment.this.loadVideoDetails();

            }

        });

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public void showMessage( String message ) {
        Log.d( TAG, "showMessage : enter" );

        this.showToastMessage( message, null, null );

        Log.d( TAG, "showMessage : exit" );
    }

    @Override
    public Context getContext() {

        return getActivity().getApplicationContext();
    }

    /**
     * Loads video details.
     */
    private void loadVideoDetails() {
        Log.d( TAG, "loadVideoDetails : enter" );

        if( null != this.presenter) {
            Log.d( TAG, "loadVideoDetails : presenter is not null" );

            this.presenter.initialize();

        }

        Log.d( TAG, "loadProgramDetails : exit" );
    }

    private void updateWatchedStatus( final VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "updateWatchedStatus : enter" );

        watched.setOnCheckedChangeListener( null );

        if( null != videoMetadataInfoModel ) {
            Log.d( TAG, "updateWatchedStatus : videoMetadataInfoModel is not null" );

            boolean watchedStatus = videoMetadataInfoModel.isWatched();
            Log.d( TAG, "updateWatchedStatus : watchedStatus=" + watchedStatus );
            watched.setChecked( watchedStatus );

        }

        watched.setOnCheckedChangeListener( this );

        Log.d( TAG, "updateWatchedStatus : exit" );
    }

    private void updateLiveStreamControls( LiveStreamInfoModel liveStreamInfoModel ) {
        Log.d( TAG, "updateLiveStreamControls : enter" );

        hls_stream.setOnCheckedChangeListener( null );

        if( null != liveStreamInfoModel ) {
            Log.d( TAG, "updateLiveStreamControls : hls exists" );

            hls_stream.setChecked( true );

            pb_progress.setVisibility( View.VISIBLE );
            pb_progress.setIndeterminate( false );
            pb_progress.setProgress( liveStreamInfoModel.getPercentComplete() );

            if( liveStreamInfoModel.getPercentComplete() < 2 ) {

                pb_progress.getProgressDrawable().setColorFilter( Color.RED, android.graphics.PorterDuff.Mode.SRC_IN );

            } else {

                pb_progress.getProgressDrawable().setColorFilter( getResources().getColor( R.color.accent ), android.graphics.PorterDuff.Mode.SRC_IN );

            }

        } else {
            Log.d( TAG, "updateLiveStreamControls : hls does not exist" );

            hls_stream.setChecked( false );

            pb_progress.setVisibility( View.GONE );

        }

        hls_stream.setOnCheckedChangeListener( this );

        Log.d( TAG, "updateLiveStreamControls : exit" );
    }

}
