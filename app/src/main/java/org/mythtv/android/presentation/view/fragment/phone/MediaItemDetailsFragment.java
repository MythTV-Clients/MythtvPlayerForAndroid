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
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.presenter.phone.MediaItemDetailsPresenter;
import org.mythtv.android.presentation.utils.SeasonEpisodeFormatter;
import org.mythtv.android.presentation.view.MediaItemDetailsView;
import org.mythtv.android.presentation.view.component.AutoLoadImageView;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dmfrey on 8/31/15.
 */
public class MediaItemDetailsFragment extends AbstractBaseFragment implements MediaItemDetailsView {

    private static final String TAG = MediaItemDetailsFragment.class.getSimpleName();

    public interface MediaItemDetailsListener {

        void onMediaItemLoaded( final MediaItemModel mediaItemModel );

    }

    private MediaItemModel mediaItemModel;
    private MediaItemDetailsListener listener;

    @Inject
    MediaItemDetailsPresenter presenter;

    @BindView( R.id.media_item_image )
    AutoLoadImageView iv_image;

    @BindView( R.id.media_item_title )
    TextView tv_title;

    @BindView( R.id.media_item_sub_title )
    TextView tv_sub_title;

    @BindView( R.id.media_item_studio )
    TextView tv_studio;

    @BindView( R.id.media_item_date )
    TextView tv_date;

    @BindView( R.id.media_item_duration )
    TextView tv_duration;

    @BindView( R.id.media_item_episode )
    TextView tv_episode;

    @BindView( R.id.media_item_progress )
    ProgressBar pb_progress;

    @BindView( R.id.media_item_description )
    TextView tv_description;

    @BindView( R.id.rl_progress )
    RelativeLayout rl_progress;

    private Unbinder unbinder;

    public MediaItemDetailsFragment() { super(); }

    public static MediaItemDetailsFragment newInstance() {

        return new MediaItemDetailsFragment();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_media_item_details, container, false );
        ButterKnife.bind( this, fragmentView );
        unbinder = ButterKnife.bind( this, fragmentView );

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

        if( activity instanceof MediaItemDetailsListener ) {

            this.listener = (MediaItemDetailsListener) activity;

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

        unbinder.unbind();

        Log.d( TAG, "onDestroyView : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.presenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( MediaComponent.class ).inject( this );
        this.presenter.setView( this );

        loadMediaItemDetails();

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public void renderMediaItem( MediaItemModel mediaItemModel ) {
        Log.d( TAG, "renderMediaItem : enter" );

        if( null != mediaItemModel ) {
            Log.d( TAG, "renderMediaItem : mediaItem is not null" );

            this.mediaItemModel = mediaItemModel;
            listener.onMediaItemLoaded( this.mediaItemModel );

            ActionBar actionBar = ( (AppCompatActivity) getActivity() ).getSupportActionBar();
            actionBar.setTitle( this.mediaItemModel.getSubTitle() );
            actionBar.setSubtitle( this.mediaItemModel.getTitle() );

            if( null == mediaItemModel.getSubTitle() || "".equals( mediaItemModel.getSubTitle() ) ) {

                actionBar.setTitle( this.mediaItemModel.getTitle() );
                actionBar.setSubtitle( "" );

            }

            this.iv_image.setImageUrl( getMasterBackendUrl() + this.mediaItemModel.getCoverartUrl() );
            this.tv_title.setText( this.mediaItemModel.getTitle() );
            this.tv_sub_title.setText( this.mediaItemModel.getSubTitle() );
            this.tv_studio.setText( this.mediaItemModel.getStudio() );
            this.tv_date.setText( null != mediaItemModel.getStartDate() ? this.mediaItemModel.getStartDate().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "MS", Locale.getDefault() ) ) : "" );
            this.tv_episode.setText( SeasonEpisodeFormatter.format( mediaItemModel.getSeason(), mediaItemModel.getEpisode() ) );
            this.tv_duration.setText( getContext().getResources().getString( R.string.minutes, String.valueOf( this.mediaItemModel.getDuration() ) ) );
            this.tv_description.setText( this.mediaItemModel.getDescription() );

            if( mediaItemModel.getPercentComplete() > 0 ) {

                this.pb_progress.setVisibility( View.VISIBLE );
                this.pb_progress.setIndeterminate( false );
                this.pb_progress.setProgress( mediaItemModel.getPercentComplete() );

                if( mediaItemModel.getPercentComplete() < 2 ) {

                    this.pb_progress.getProgressDrawable().setColorFilter( Color.RED, android.graphics.PorterDuff.Mode.SRC_IN );

                } else {

                    this.pb_progress.getProgressDrawable().setColorFilter( getResources().getColor( R.color.accent ), android.graphics.PorterDuff.Mode.SRC_IN );

                }

            }

        }

        Log.d( TAG, "renderMediaItem : exit" );
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

        this.showToastMessage( message, getResources().getString( R.string.retry ), v -> MediaItemDetailsFragment.this.loadMediaItemDetails());


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

    public void reload() {

        loadMediaItemDetails();
        
    }

    /**
     * Loads media item details.
     */
    private void loadMediaItemDetails() {
        Log.d( TAG, "loadMediaItemDetails : enter" );

        if( null != this.presenter) {
            Log.d( TAG, "loadMediaItemDetails : presenter is not null" );

            this.presenter.initialize();

        }

        Log.d( TAG, "loadMediaItemDetails : exit" );
    }

    @Override
    public void updateLiveStream( MediaItemModel mediaItem ) {
        Log.d( TAG, "updateLiveStream : enter" );

        if( null != mediaItem ) {

        }

        Log.d( TAG, "updateLiveStream : exit" );
    }

}
