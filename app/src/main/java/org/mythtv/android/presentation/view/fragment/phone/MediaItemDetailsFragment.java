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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.R;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityJsonMapper;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.presenter.phone.MediaItemDetailsPresenter;
import org.mythtv.android.presentation.utils.SeasonEpisodeFormatter;
import org.mythtv.android.presentation.view.MediaItemDetailsView;
import org.mythtv.android.presentation.view.component.AutoLoadImageView;

import java.io.IOException;
import java.io.Reader;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.FormBody;
import okhttp3.Request;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dmfrey on 8/31/15.
 */
public class MediaItemDetailsFragment extends AbstractBaseFragment implements MediaItemDetailsView {

    private static final String TAG = MediaItemDetailsFragment.class.getSimpleName();
    private static final int ADD_LIVE_STREAM_DIALOG_RESULT = 0;
    private static final int REMOVE_LIVE_STREAM_DIALOG_RESULT = 1;

    public interface MediaItemDetailsListener {

        void onMediaItemLoaded( final MediaItemModel mediaItemModel );

    }

    private MediaItemModel mediaItemModel;
    private MediaItemDetailsListener listener;

    MenuItem menuHlsEnable;
    MenuItem menuHlsDisable;
    MenuItem menuMarkWatched;
    MenuItem menuMarkUnwatched;

    @Inject
    MediaItemDetailsPresenter presenter;

    @BindView( R.id.media_item_image )
    AutoLoadImageView iv_image;

    @BindView( R.id.media_item_bookmark )
    ImageView iv_bookmark;

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

        setHasOptionsMenu( true );

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

        this.timer.cancel();

        if( null != this.getLiveStreamTask && this.getLiveStreamTask.getStatus() == AsyncTask.Status.RUNNING ) {

            getLiveStreamTask.cancel( true );

        }

        Log.d( TAG, "onDestroy : exit" );
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        Log.d( TAG, "onActivityResult : enter" );
        super.onActivityResult( requestCode, resultCode, data );

        switch( requestCode ) {

            case ADD_LIVE_STREAM_DIALOG_RESULT :
                Log.d( TAG, "onActivityResult : add live stream result returned " + resultCode );

                if( resultCode == RESULT_OK ) {
                    Log.d( TAG, "onActivityResult : positive button pressed" );

                    new AddLiveStreamTask().execute();

                } else {
                    Log.d( TAG, "onActivityResult : negative button pressed" );


                }

                break;

            case REMOVE_LIVE_STREAM_DIALOG_RESULT :
                Log.d( TAG, "onActivityResult : remove live stream result returned " + resultCode );

                if( resultCode == RESULT_OK ) {
                    Log.d( TAG, "onActivityResult : positive button pressed" );

                    new RemoveLiveStreamTask().execute();

                } else {
                    Log.d( TAG, "onActivityResult : negative button pressed" );


                }

                break;

        }

        Log.d( TAG, "onActivityResult : exit" );
    }

    @Override
    public void onPrepareOptionsMenu( Menu menu ) {
        super.onPrepareOptionsMenu( menu );

        menuHlsEnable = menu.findItem( R.id.menu_hls_enable );
        menuHlsDisable = menu.findItem( R.id.menu_hls_disable );
        menuMarkWatched = menu.findItem( R.id.menu_mark_watched );
        menuMarkUnwatched = menu.findItem( R.id.menu_mark_unwatched );

    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {

        inflater.inflate( R.menu.menu_item_details, menu );

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case R.id.menu_hls_enable:

                AddLiveStreamDialogFragment addLiveStreamfragment = new AddLiveStreamDialogFragment();
                addLiveStreamfragment.setTargetFragment( this, ADD_LIVE_STREAM_DIALOG_RESULT );
                addLiveStreamfragment.show( getFragmentManager(), "AddLiveStreamDialogFragment" );

                return true;

            case R.id.menu_hls_disable:

                RemoveLiveStreamDialogFragment removeLiveStreamFragment = new RemoveLiveStreamDialogFragment();
                removeLiveStreamFragment.setTargetFragment( this, REMOVE_LIVE_STREAM_DIALOG_RESULT );
                removeLiveStreamFragment.show( getFragmentManager(), "RemoveLiveStreamDialogFragment" );

                return true;

            case R.id.menu_mark_watched:

                new MarkWatchedTask().execute( true );

                return true;

            case R.id.menu_mark_unwatched:

                new MarkWatchedTask().execute( false );

                return true;

        }

        return super.onOptionsItemSelected( item );
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
            Log.d( TAG, "renderMediaItem : mediaItem is not null, mediaItemModel=" + mediaItemModel.toString() );

            this.mediaItemModel = mediaItemModel;
            listener.onMediaItemLoaded( this.mediaItemModel );
            updateMenu();

            ActionBar actionBar = ( (AppCompatActivity) getActivity() ).getSupportActionBar();
            actionBar.setTitle( this.mediaItemModel.getSubTitle() );
            actionBar.setSubtitle( this.mediaItemModel.getTitle() );

            if( null == mediaItemModel.getSubTitle() || "".equals( mediaItemModel.getSubTitle() ) ) {

                actionBar.setTitle( this.mediaItemModel.getTitle() );
                actionBar.setSubtitle( "" );

            }

            this.iv_image.setImageUrl( getMasterBackendUrl() + this.mediaItemModel.getCoverartUrl() );
            if( mediaItemModel.getBookmark() > 0 ) {

                this.iv_bookmark.setVisibility( View.VISIBLE );

            } else {

                this.iv_bookmark.setVisibility( View.GONE );

            }

            this.tv_title.setText( this.mediaItemModel.getTitle() );
            this.tv_sub_title.setText( this.mediaItemModel.getSubTitle() );
            this.tv_studio.setText( this.mediaItemModel.getStudio() );
            this.tv_date.setText( null != mediaItemModel.getStartDate() ? this.mediaItemModel.getStartDate().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "MS", Locale.getDefault() ) ) : "" );
            this.tv_episode.setText( SeasonEpisodeFormatter.format( mediaItemModel.getSeason(), mediaItemModel.getEpisode() ) );
            this.tv_duration.setText( getContext().getResources().getString( R.string.minutes, String.valueOf( this.mediaItemModel.getDuration() ) ) );
            this.tv_description.setText( this.mediaItemModel.getDescription() );

            updateProgress();

            if( mediaItemModel.getLiveStreamId() > 0 && mediaItemModel.getPercentComplete() < 100 ) {

                timer.start();

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

    private void updateMenu() {

        if( null == menuHlsDisable || null == menuHlsEnable || null == menuMarkUnwatched || null == menuMarkWatched ) {

            return;
        }

        if( !mediaItemModel.isRecording() ) {

            if( mediaItemModel.getLiveStreamId() != 0 ) {

                menuHlsEnable.setVisible( false );
                menuHlsDisable.setVisible( true );

            } else {

                menuHlsEnable.setVisible( true );
                menuHlsDisable.setVisible( false );

            }

        }

        if( mediaItemModel.isWatched() ) {

            menuMarkUnwatched.setVisible( true );
            menuMarkWatched.setVisible( false );

        } else {

            menuMarkUnwatched.setVisible( false );
            menuMarkWatched.setVisible( true );

        }

    }

    private void updateProgress() {

        if( mediaItemModel.getLiveStreamId() != 0 ) {

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

        } else {

            this.pb_progress.getProgressDrawable().setColorFilter( Color.RED, android.graphics.PorterDuff.Mode.SRC_IN );
            this.pb_progress.setVisibility( View.GONE );
            this.pb_progress.setIndeterminate( true );
            this.pb_progress.setProgress( 0 );

        }

    }

    private class MarkWatchedTask extends AsyncTask<Boolean, Void, Boolean> {

        @Override
        protected Boolean doInBackground( Boolean... params ) {

            String idParam;
            switch( mediaItemModel.getMedia() ) {

                case PROGRAM:

                    idParam = "RecordedId";

                    break;

                default:

                    idParam = "Id";

                    break;
            }

            FormBody.Builder builder = new FormBody.Builder();
            builder.add(idParam, String.valueOf( mediaItemModel.getId() ) );
            builder.add( "Watched", String.valueOf( params[ 0 ] ) );

            final Request request = new Request.Builder()
                    .url( getMasterBackendUrl() + mediaItemModel.getMarkWatchedUrl() )
                    .addHeader( "Accept", "application/json" )
                    .post( builder.build() )
                    .build();

            try {

                BooleanJsonMapper mapper = new BooleanJsonMapper();
                Reader result = okHttpClient.newCall( request ).execute().body().charStream();
//                Log.d( TAG, "doInBackground : result=" + result );

                return mapper.transformBoolean( result );

            } catch( IOException e ) {

                Log.e( TAG, "doInBackground : error", e );

            }

            return null;
        }

        @Override
        protected void onPostExecute( Boolean result ) {

            if( null != result ) {

                if (result) {

                    mediaItemModel.setWatched( true );
                    updateMenu();

                }

            }

        }

    }

    private class AddLiveStreamTask extends AsyncTask<Void, Void, LiveStreamInfoEntity> {

        @Override
        protected LiveStreamInfoEntity doInBackground( Void... params ) {
            Log.d( TAG, "doInBackground : mediaItemModel=" + mediaItemModel.toString() );

            final Request request = new Request.Builder()
                    .url( getMasterBackendUrl() + mediaItemModel.getCreateHttpLiveStreamUrl() )
                    .addHeader( "Accept", "application/json" )
                    .get()
                    .build();

            try {

                LiveStreamInfoEntityJsonMapper mapper = new LiveStreamInfoEntityJsonMapper( gson );
                Reader result = okHttpClient.newCall( request ).execute().body().charStream();
//                Log.d( TAG, "doInBackground : result=" + result );

                return mapper.transformLiveStreamInfoEntity( result );

            } catch( IOException e ) {

                Log.e( TAG, "doInBackground : error", e );

            }

            return null;
        }

        @Override
        protected void onPostExecute( LiveStreamInfoEntity liveStreamInfoEntity ) {

            if( null != liveStreamInfoEntity ) {
                Log.d( TAG, "onPostExecute : liveStreamInfoEntity=" + liveStreamInfoEntity.toString() );

                mediaItemModel.setLiveStreamId( liveStreamInfoEntity.getId() );
                mediaItemModel.setPercentComplete( liveStreamInfoEntity.getPercentComplete() );
                mediaItemModel.setRemoveHttpLiveStreamUrl( String.format( "/Content/RemoveLiveStream?Id=%s", String.valueOf( liveStreamInfoEntity.getId() ) ) );
                mediaItemModel.setGetHttpLiveStreamUrl( String.format( "/Content/GetLiveStream?Id=%s", String.valueOf( liveStreamInfoEntity.getId() ) ) );

                switch( mediaItemModel.getMedia() ) {

                    case PROGRAM:

                        mediaItemModel.setCreateHttpLiveStreamUrl( String.format( "/Content/AddRecordingLiveStream?RecordedId=%s&Width=960", String.valueOf( mediaItemModel.getId() ) ) );

                        break;

                    case VIDEO:

                        mediaItemModel.setCreateHttpLiveStreamUrl( String.format( "/Content/AddVideoLiveStream?Id=%s&Width=960", String.valueOf( mediaItemModel.getId() ) ) );

                        break;

                }

                updateMenu();
                updateProgress();
                timer.start();

            }

        }

    }

    private class RemoveLiveStreamTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground( Void... params ) {
            Log.d( TAG, "doInBackground : mediaItemModel=" + mediaItemModel.toString() );

            final Request request = new Request.Builder()
                    .url( getMasterBackendUrl() + mediaItemModel.getRemoveHttpLiveStreamUrl() )
                    .addHeader( "Accept", "application/json" )
                    .get()
                    .build();

            try {

                BooleanJsonMapper mapper = new BooleanJsonMapper();
                Reader result = okHttpClient.newCall( request ).execute().body().charStream();
//                Log.d( TAG, "doInBackground : result=" + result );

                return mapper.transformBoolean( result );

            } catch( IOException e ) {

                Log.e( TAG, "doInBackground : error", e );

            }

            return null;

        }

        @Override
        protected void onPostExecute( Boolean result ) {

            if( null != result ) {

                mediaItemModel.setLiveStreamId( 0 );
                mediaItemModel.setPercentComplete( 0 );
                mediaItemModel.setRemoveHttpLiveStreamUrl( "" );
                mediaItemModel.setGetHttpLiveStreamUrl( "" );

                updateMenu();
                updateProgress();

            }

        }

    }

    private class GetLiveStreamTask extends AsyncTask<Void, Void, LiveStreamInfoEntity> {

        @Override
        protected LiveStreamInfoEntity doInBackground( Void... params ) {
            Log.d( TAG, "doInBackground : mediaItemModel=" + mediaItemModel.toString() );

            final Request request = new Request.Builder()
                    .url( getMasterBackendUrl() + mediaItemModel.getGetHttpLiveStreamUrl() )
                    .addHeader( "Accept", "application/json" )
                    .get()
                    .build();

            try {

                LiveStreamInfoEntityJsonMapper mapper = new LiveStreamInfoEntityJsonMapper( gson );
                Reader result = okHttpClient.newCall( request ).execute().body().charStream();
//                Log.d( TAG, "doInBackground : result=" + result );

                return mapper.transformLiveStreamInfoEntity( result );

            } catch( IOException e ) {

                Log.e( TAG, "doInBackground : error", e );

            }

            return null;
        }

        @Override
        protected void onPostExecute( LiveStreamInfoEntity liveStreamInfoEntity ) {

            if( null != liveStreamInfoEntity ) {

                mediaItemModel.setPercentComplete( liveStreamInfoEntity.getPercentComplete() );

                if( liveStreamInfoEntity.getPercentComplete() == 100 ) {

                    timer.cancel();

                }

                updateProgress();

            }

        }

    }

    private GetLiveStreamTask getLiveStreamTask = null;
    private int fifteenMin = 60 * 30000;
    private CountDownTimer timer = new CountDownTimer( fifteenMin, 5000 ) {

        @Override
        public void onTick( long millisUntilFinished ) {
            Log.v( TAG, "onTick : enter" );

            getLiveStreamTask = new GetLiveStreamTask();
            getLiveStreamTask.execute();

            Log.v( TAG, "onTick : enter" );
        }

        @Override
        public void onFinish() {

        }

    };

}
