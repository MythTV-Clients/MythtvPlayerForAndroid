package org.mythtv.android.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.VideoDetailsView;
import org.mythtv.android.presentation.internal.di.components.VideoComponent;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.presenter.VideoDetailsPresenter;
import org.mythtv.android.presentation.view.component.AutoLoadImageView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dmfrey on 8/31/15.
 */
public class VideoDetailsFragment extends BaseFragment implements VideoDetailsView {

    private static final String TAG = VideoDetailsFragment.class.getSimpleName();

    private static final String ARGUMENT_KEY_VIDEO_ID = "org.mythtv.android.ARGUMENT_VIDEO_ID";

    public interface VideoDetailsListener {

        void onPlayVideo( final VideoMetadataInfoModel videoMetadataInfoModel );

    }

    private VideoDetailsListener videoDetailsListener;

    private int id;

    @Inject
    VideoDetailsPresenter videoDetailsPresenter;

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

    @Bind( R.id.video_queue_hls )
    Button bt_queue;

    @Bind( R.id.video_play )
    Button bt_play;

    @Bind( R.id.rl_progress )
    RelativeLayout rl_progress;

    private VideoMetadataInfoModel videoMetadataInfoModel;

    public VideoDetailsFragment() { super(); }

    public static VideoDetailsFragment newInstance( int id ) {

        VideoDetailsFragment programDetailsFragment = new VideoDetailsFragment();

        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putInt( ARGUMENT_KEY_VIDEO_ID, id );
        programDetailsFragment.setArguments( argumentsBundle );

        return programDetailsFragment;
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
            this.videoDetailsListener = (VideoDetailsListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.videoDetailsPresenter.resume();
        updateLiveStreamControls();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.videoDetailsPresenter.pause();

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

        this.videoDetailsPresenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( VideoComponent.class ).inject( this );
        this.videoDetailsPresenter.setView( this );
        this.id = getArguments().getInt( ARGUMENT_KEY_VIDEO_ID );
        Log.d( TAG, "initialize : id=" + this.id );

        this.videoDetailsPresenter.initialize( this.id );

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public void renderVideo( VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d(TAG, "renderVideo : enter");

        if( null != videoMetadataInfoModel ) {
            Log.d( TAG, "renderVideo : video is not null" );

            this.videoMetadataInfoModel = videoMetadataInfoModel;

            ActionBar actionBar = ( (AppCompatActivity) getActivity() ).getSupportActionBar();
            actionBar.setTitle( videoMetadataInfoModel.getTitle() );

            this.iv_coverart.setImageUrl( getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + videoMetadataInfoModel.getId() + "&Type=coverart&Width=150" );
            this.tv_showname.setText( videoMetadataInfoModel.getTitle() );
            this.tv_episodename.setText( videoMetadataInfoModel.getSubTitle() );
            this.tv_description.setText( videoMetadataInfoModel.getDescription() );

            updateLiveStreamControls();

        }

        Log.d(TAG, "renderProgram : exit");
    }

    @Override
    public void updateLiveStream( LiveStreamInfoModel liveStreamInfo ) {

        this.videoMetadataInfoModel.setLiveStreamInfo( liveStreamInfo );

        updateLiveStreamControls();

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
    public Context getContext()
    {
        return getActivity().getApplicationContext();
    }

    /**
     * Loads video details.
     */
    private void loadVideoDetails() {
        Log.d( TAG, "loadVideoDetails : enter" );

        if( null != this.videoDetailsPresenter ) {
            Log.d( TAG, "loadVideoDetails : videoDetailsPresenter is not null" );

            this.videoDetailsPresenter.initialize( this.id );

        }

        Log.d( TAG, "loadProgramDetails : exit" );
    }

    private void updateLiveStreamControls() {
        Log.d( TAG, "updateLiveStreamControls : enter" );

        boolean useInternalPlayer = getInternalPlayerPreferenceFromPreferences( getActivity() );
        boolean useExternalPlayerOverride = getExternalPlayerPreferenceFromPreferences( getActivity() );
        if( !useInternalPlayer || useExternalPlayerOverride ) {
            Log.d( TAG, "updateLiveStreamControls : using external player" );

            pb_progress.setVisibility( View.GONE );
            bt_play.setVisibility( View.VISIBLE );
            bt_queue.setVisibility( View.GONE );

            return;
        }

        if( null != videoMetadataInfoModel && null != videoMetadataInfoModel.getLiveStreamInfo() ) {
            Log.d( TAG, "updateLiveStreamControls : hls exists" );

            pb_progress.setVisibility( View.VISIBLE );
            pb_progress.setIndeterminate( false );
            pb_progress.setProgress( videoMetadataInfoModel.getLiveStreamInfo().getPercentComplete() );

            if( videoMetadataInfoModel.getLiveStreamInfo().getPercentComplete() > 2 ) {
                Log.d( TAG, "updateLiveStreamControls : hls can be played" );

                bt_play.setVisibility( View.VISIBLE );
                bt_queue.setVisibility( View.GONE );

            } else {
                Log.d( TAG, "updateLiveStreamControls : hls processing..." );

                bt_play.setVisibility( View.GONE );
                bt_queue.setVisibility( View.GONE );

            }

        } else {
            Log.d( TAG, "updateLiveStreamControls : hls does not exist" );

            pb_progress.setVisibility( View.GONE );
            bt_play.setVisibility( View.GONE );
            bt_queue.setVisibility( View.VISIBLE );
        }

        Log.d( TAG, "updateLiveStreamControls : exit" );
    }

    @OnClick( R.id.video_play )
    void onButtonPlayClick() {
        Log.d( TAG, "onButtonPlayClick : enter" );

        if( null != videoMetadataInfoModel ) {

            this.videoDetailsListener.onPlayVideo( videoMetadataInfoModel );

        }

        Log.d( TAG, "onButtonPlayClick : exit" );
    }

    @OnClick( R.id.video_queue_hls )
    void onButtonQueueClick() {
        Log.d( TAG, "onButtonQueueClick : enter" );

        if( null != videoMetadataInfoModel ) {

            this.videoDetailsPresenter.addLiveStream();

        }

        Log.d( TAG, "onButtonQueueClick : exit" );
    }

    public boolean getInternalPlayerPreferenceFromPreferences( Context context ) {

        if( null == context ) {

            return false;
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_INTERNAL_PLAYER, false );
    }

    public boolean getExternalPlayerPreferenceFromPreferences( Context context ) {

        if( null == context ) {

            return false;
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO, false );
    }

}
