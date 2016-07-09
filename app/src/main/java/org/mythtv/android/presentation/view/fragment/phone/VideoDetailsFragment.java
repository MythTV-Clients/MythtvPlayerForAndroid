/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.presentation.view.fragment.phone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import org.mythtv.android.presentation.R;
import org.mythtv.android.presentation.internal.di.components.VideoComponent;
import org.mythtv.android.presentation.VideoDetailsView;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.presenter.phone.VideoDetailsPresenter;
import org.mythtv.android.presentation.view.component.AutoLoadImageView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dmfrey on 8/31/15.
 */
public class VideoDetailsFragment extends AbstractBaseFragment implements VideoDetailsView, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = VideoDetailsFragment.class.getSimpleName();
    private static final int ADD_LIVE_STREAM_DIALOG_RESULT = 0;
    private static final int REMOVE_LIVE_STREAM_DIALOG_RESULT = 1;

    public interface VideoDetailsListener {

        void onVideoLoaded( final VideoMetadataInfoModel videoMetadataInfoModel );

    }

    private VideoMetadataInfoModel videoMetadataInfoModel;
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

        View fragmentView = inflater.inflate( R.layout.fragment_phone_video_details, container, false );
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
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        Log.d( TAG, "onActivityResult : enter" );
        super.onActivityResult( requestCode, resultCode, data );

        switch( requestCode ) {

            case ADD_LIVE_STREAM_DIALOG_RESULT :
                Log.d( TAG, "onActivityResult : add live stream result returned " + resultCode );

                if( resultCode == Activity.RESULT_OK ) {
                    Log.d( TAG, "onActivityResult : positive button pressed" );

                    this.presenter.updateHlsStream();

                } else {
                    Log.d( TAG, "onActivityResult : negative button pressed" );

                    updateLiveStreamControls( null );

                }

                break;

            case REMOVE_LIVE_STREAM_DIALOG_RESULT :
                Log.d( TAG, "onActivityResult : remove live stream result returned " + resultCode );

                if( resultCode == Activity.RESULT_OK ) {
                    Log.d( TAG, "onActivityResult : positive button pressed" );

                    this.presenter.updateHlsStream();

                } else {
                    Log.d( TAG, "onActivityResult : negative button pressed" );

                    updateLiveStreamControls( videoMetadataInfoModel.getLiveStreamInfo() );

                }

                break;

        }

        Log.d( TAG, "onActivityResult : exit" );
    }

    @Override
    public void onCheckedChanged( CompoundButton buttonView, boolean isChecked ) {
        Log.d( TAG, "onCheckedChanged : enter" );

        switch( buttonView.getId() ) {

            case R.id.watched_switch :
                Log.d( TAG, "onCheckedChanged : watched switched" );

                this.presenter.updateWatchedStatus();

                break;

            case R.id.hsl_stream_switch :
                Log.d( TAG, "onCheckedChanged : hls_stream switched" );

                if( null != videoMetadataInfoModel.getLiveStreamInfo() ) {
                    Log.d( TAG, "onCheckedChanged : prompt removing live stream" );

                    RemoveLiveStreamDialogFragment fragment = new RemoveLiveStreamDialogFragment();
                    fragment.setTargetFragment( this, REMOVE_LIVE_STREAM_DIALOG_RESULT );
                    fragment.show( getFragmentManager(), "RemoveLiveStreamDialogFragment" );

                } else {
                    Log.d( TAG, "onCheckedChanged : prompt adding live stream" );

                    AddLiveStreamDialogFragment fragment = new AddLiveStreamDialogFragment();
                    fragment.setTargetFragment( this, ADD_LIVE_STREAM_DIALOG_RESULT );
                    fragment.show( getFragmentManager(), "AddLiveStreamDialogFragment" );

                }

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

            this.videoMetadataInfoModel = videoMetadataInfoModel;

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

        this.videoMetadataInfoModel = videoMetadataInfoModel;

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
            watched.setText( watchedStatus ? getResources().getString( R.string.watched ) : getResources().getString( R.string.unwatched ) );

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

                hls_stream.setText( getResources().getString( R.string.http_live_stream_unavailable ) );
                pb_progress.getProgressDrawable().setColorFilter( Color.RED, android.graphics.PorterDuff.Mode.SRC_IN );

            } else {
                Log.d( TAG, "updateLiveStreamControls : hls processing..." );

                String playerType = getResources().getString( R.string.pref_internal_player );
                if( !getSharedPreferencesModule().getInternalPlayer() ) {

                    playerType = getResources().getString( R.string.pref_external_player );

                }

                hls_stream.setText( getResources().getString( R.string.http_live_stream_available, playerType ) );
                pb_progress.getProgressDrawable().setColorFilter( getResources().getColor( R.color.accent ), android.graphics.PorterDuff.Mode.SRC_IN );

            }

        } else {
            Log.d( TAG, "updateLiveStreamControls : hls does not exist" );

            hls_stream.setChecked( false );
            hls_stream.setText( getResources().getString( R.string.http_live_stream, getResources().getString( R.string.unavailable ) ) );

            pb_progress.setVisibility( View.GONE );

        }

        hls_stream.setOnCheckedChangeListener( this );

        Log.d( TAG, "updateLiveStreamControls : exit" );
    }

}
