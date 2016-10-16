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

package org.mythtv.android.presentation.view.activity.phone;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.gms.cast.framework.CastSession;

import org.mythtv.android.R;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityJsonMapper;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerMediaComponent;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.internal.di.modules.MediaItemModule;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.view.fragment.phone.AddLiveStreamDialogFragment;
import org.mythtv.android.presentation.view.fragment.phone.CastErrorDialogFragment;
import org.mythtv.android.presentation.view.fragment.phone.CastNotReadyDialogFragment;
import org.mythtv.android.presentation.view.fragment.phone.MediaItemDetailsFragment;
import org.mythtv.android.presentation.view.fragment.phone.RemoveLiveStreamDialogFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by dmfrey on 9/30/15.
 */
public class MediaItemDetailsActivity extends AbstractBasePhoneActivity implements HasComponent<MediaComponent>, MediaItemDetailsFragment.MediaItemDetailsListener {

    private static final String TAG = MediaItemDetailsActivity.class.getSimpleName();
    private static final int ADD_LIVE_STREAM_DIALOG_RESULT = 0;
    private static final int REMOVE_LIVE_STREAM_DIALOG_RESULT = 1;

    private static final String INTENT_EXTRA_PARAM_ID = "org.mythtv.android.INTENT_PARAM_ID";
    private static final String INTENT_EXTRA_PARAM_MEDIA = "org.mythtv.android.INTENT_PARAM_MEDIA";
    private static final String INSTANCE_STATE_PARAM_ID = "org.mythtv.android.STATE_PARAM_ID";
    private static final String INSTANCE_STATE_PARAM_MEDIA = "org.mythtv.android.STATE_PARAM_MEDIA";

    private int id;
    private Media media;
    private MediaComponent mediaComponent;

    private MediaItemModel mediaItemModel;

    @BindView( R.id.backdrop )
    ImageView backdrop;

    @BindView( R.id.fab )
    FloatingActionButton fab;

    MediaItemDetailsFragment fragment;

    MenuItem menuHlsEnable;
    MenuItem menuHlsDisable;
    MenuItem menuMarkWatched;
    MenuItem menuMarkUnwatched;

    public static Intent getCallingIntent( Context context, int id, Media media ) {

        Intent callingIntent = new Intent( context, MediaItemDetailsActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_ID, id );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_MEDIA, media.name() );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_media_item_details;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );

        super.onCreate( savedInstanceState );

        ButterKnife.bind( this );

        this.initializeActivity( savedInstanceState );
        this.initializeInjector();

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        if( null != outState ) {
            Log.d( TAG, "onSaveInstanceState : outState is not null" );

            outState.putInt( INSTANCE_STATE_PARAM_ID, this.id );
            outState.putString( INSTANCE_STATE_PARAM_MEDIA, this.media.name() );

        }

        super.onSaveInstanceState( outState );

        Log.d( TAG, "onSaveInstanceState : exit" );
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        Log.d( TAG, "onRestoreInstanceState : enter" );
        super.onRestoreInstanceState( savedInstanceState );

        if( null != savedInstanceState ) {
            Log.d( TAG, "onRestoreInstanceState : savedInstanceState != null" );

            this.id = savedInstanceState.getInt( INSTANCE_STATE_PARAM_ID );
            this.media = Media.valueOf( savedInstanceState.getString( INSTANCE_STATE_PARAM_MEDIA ) );

            Log.d( TAG, "onRestoreInstanceState : id=" + id + ", media=" + media.name() );
        }

        Log.d(TAG, "onRestoreInstanceState : exit");
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
    public boolean onCreateOptionsMenu( Menu menu ) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_details, menu );

        menuHlsEnable = menu.findItem( R.id.menu_hls_enable );
        menuHlsDisable = menu.findItem( R.id.menu_hls_disable );
        menuMarkWatched = menu.findItem( R.id.menu_mark_watched );
        menuMarkUnwatched = menu.findItem( R.id.menu_mark_unwatched );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onMenuOpened( int featureId, Menu menu ) {

        updateMenu();

        return super.onMenuOpened( featureId, menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case android.R.id.home:

                NavUtils.navigateUpFromSameTask( this );

                return true;

            case R.id.menu_hls_enable:

                AddLiveStreamDialogFragment addLiveStreamfragment = new AddLiveStreamDialogFragment();
                addLiveStreamfragment.setTargetFragment( fragment, ADD_LIVE_STREAM_DIALOG_RESULT );
                addLiveStreamfragment.show( getSupportFragmentManager(), "AddLiveStreamDialogFragment" );

                return true;

            case R.id.menu_hls_disable:

                RemoveLiveStreamDialogFragment removeLiveStreamFragment = new RemoveLiveStreamDialogFragment();
                removeLiveStreamFragment.setTargetFragment( fragment, REMOVE_LIVE_STREAM_DIALOG_RESULT );
                removeLiveStreamFragment.show( getSupportFragmentManager(), "RemoveLiveStreamDialogFragment" );

                return true;

            case R.id.menu_mark_watched:

                new MarkWatchedTask().execute( true );

                return true;

            case R.id.menu_mark_unwatched:

                new MarkWatchedTask().execute( false );

                return true;

            case R.id.menu_settings:

                navigator.navigateToSettings( this );

                return true;

        }

        return super.onOptionsItemSelected( item );
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d( TAG, "initializeActivity : enter" );

        if( null == savedInstanceState ) {
            Log.d( TAG, "initializeActivity : savedInstanceState is null" );

            Bundle extras = getIntent().getExtras();
            if( null != extras ) {
                Log.d( TAG, "initializeActivity : extras != null" );

                if( extras.containsKey( INTENT_EXTRA_PARAM_ID ) ) {

                    this.id = getIntent().getIntExtra( INTENT_EXTRA_PARAM_ID, -1 );

                }

                if( extras.containsKey( INTENT_EXTRA_PARAM_MEDIA ) ) {

                    this.media = Media.valueOf( getIntent().getStringExtra( INTENT_EXTRA_PARAM_MEDIA ) );

                }

                fragment = MediaItemDetailsFragment.newInstance();
                addFragment( R.id.fl_fragment, fragment );

            }

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState is not null" );

            this.id = savedInstanceState.getInt( INSTANCE_STATE_PARAM_ID );
            this.media = Media.valueOf( savedInstanceState.getString( INSTANCE_STATE_PARAM_MEDIA ) );

        }

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.mediaComponent = DaggerMediaComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .mediaItemModule( new MediaItemModule( this.id, this.media ) )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public MediaComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return mediaComponent;
    }

    @Override
    public void onMediaItemLoaded( final MediaItemModel mediaItemModel ) {
        Log.d( TAG, "onMediaItemLoaded : enter" );

        this.mediaItemModel = mediaItemModel;
        loadBackdrop();

        Log.d( TAG, "onMediaItemLoaded : exit" );
    }

    private void loadBackdrop() {
        Log.d( TAG, "loadBackdrop : enter" );

        String backdropUrl = null;
        switch( mediaItemModel.getMedia() ) {

            case PROGRAM:

                if( null != mediaItemModel.getPreviewUrl() && !"".equals( mediaItemModel.getPreviewUrl() ) ) {

                    backdropUrl = getMasterBackendUrl() + mediaItemModel.getPreviewUrl();

                }

                break;

            default:

                if( null != mediaItemModel.getFanartUrl() && !"".equals( mediaItemModel.getFanartUrl() ) ) {

                    backdropUrl = getMasterBackendUrl() + mediaItemModel.getFanartUrl();

                }

                break;
        }

        if( null != backdropUrl && !"".equals( backdropUrl ) ) {

            Log.i( TAG, "loadBackdrop : backdropUrl=" + backdropUrl );
            final ImageView imageView = (ImageView) findViewById( R.id.backdrop );
            getNetComponent().picasso()
                    .load(backdropUrl)
                    .fit().centerCrop()
                    .into( imageView );

        }

        Log.d( TAG, "loadBackdrop : exit" );
    }

    private void updateMenu() {

        if( !mediaItemModel.isRecording() ) {

            if( mediaItemModel.getLiveStreamId() > -1 ) {

                menuHlsEnable.setVisible( true );
                menuHlsDisable.setVisible( false );

            } else {

                menuHlsEnable.setVisible( false );
                menuHlsDisable.setVisible( true );

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

    @OnClick( R.id.fab )
    void onButtonFabPlay() {
        Log.d( TAG, "onButtonFabPlay : enter" );

        if( getSharedPreferencesComponent().sharedPreferences().getBoolean( SettingsKeys.KEY_PREF_INTERNAL_PLAYER, true ) ) {
            Log.d( TAG, "onButtonFabPlay : sending stream to internal player" );

            switch( mediaItemModel.getMedia() ) {

                case PROGRAM:

                    if( mediaItemModel.getUrl().endsWith( "mp4" ) ) {

                        navigator.navigateToLocalPlayer( this, mediaItemModel );

                    } else if( mediaItemModel.getUrl().endsWith( "m3u8" ) ) {

                        if( mediaItemModel.getPercentComplete() > 2 ) {

                            navigator.navigateToLocalPlayer( this, mediaItemModel );

                        } else {

                            FragmentManager fm = getSupportFragmentManager();
                            CastNotReadyDialogFragment fragment = new CastNotReadyDialogFragment();
                            fragment.show( fm, "Cast Not Ready Dialog Fragment" );

                        }

                    } else {

                        CastSession castSession = mCastContext.getSessionManager().getCurrentCastSession();
                        if( null != castSession && castSession.isConnected() ) {

                            FragmentManager fm = getSupportFragmentManager();
                            CastErrorDialogFragment fragment = new CastErrorDialogFragment();
                            fragment.show( fm, "Cast Error Dialog Fragment" );

                        } else {

                            navigator.navigateToExternalPlayer( this, ( getMasterBackendUrl() + mediaItemModel.getUrl() ), mediaItemModel.getContentType() );

                        }

                    }

                    break;

                default:

                    navigator.navigateToLocalPlayer( this, mediaItemModel );

                    break;

            }

        } else {
            Log.d( TAG, "onButtonFabPlay : sending stream to external player" );

            navigator.navigateToExternalPlayer( this, (getMasterBackendUrl() + mediaItemModel.getUrl() ), mediaItemModel.getContentType() );

        }

        Log.d( TAG, "onButtonFabPlay : exit" );
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

            OkHttpClient okHttpClient = getNetComponent().okHttpClient();

            final Request request = new Request.Builder()
                    .url( getMasterBackendUrl() + mediaItemModel.getMarkWatchedUrl() )
                    .addHeader( "Accept", "application/json" )
                    .post( builder.build() )
                    .build();

            try {

                BooleanJsonMapper mapper = new BooleanJsonMapper();
                String result = okHttpClient.newCall( request ).execute().body().string();
                Log.d( TAG, "doInBackground : result=" + result );

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
                    fragment.reload();

                }

            }

        }

    }

    private class AddLiveStreamTask extends AsyncTask<Void, Void, LiveStreamInfoEntity> {

        @Override
        protected LiveStreamInfoEntity doInBackground( Void... params ) {
            Log.d( TAG, "doInBackground : mediaItemModel=" + mediaItemModel.toString() );

            OkHttpClient okHttpClient = getNetComponent().okHttpClient();

            final Request request = new Request.Builder()
                    .url( getMasterBackendUrl() + mediaItemModel.getCreateHttpLiveStreamUrl() )
                    .addHeader( "Accept", "application/json" )
                    .get()
                    .build();

            try {

                LiveStreamInfoEntityJsonMapper mapper = new LiveStreamInfoEntityJsonMapper( getNetComponent().gson() );
                String result = okHttpClient.newCall( request ).execute().body().string();
                Log.d( TAG, "doInBackground : result=" + result );

                return mapper.transformLiveStreamInfoEntity( result );

            } catch( IOException e ) {

                Log.e( TAG, "doInBackground : error", e );

            }

            return null;
        }

        @Override
        protected void onPostExecute( LiveStreamInfoEntity liveStreamInfoEntity ) {

            if( null != liveStreamInfoModel ) {

                mediaItemModel.setLiveStreamId( liveStreamInfoEntity.getId() );
                mediaItemModel.setRemoveHttpLiveStreamUrl( String.format( "/Content/RemoveLiveStream?Id=%s", String.valueOf( liveStreamInfoEntity.getId() ) ) );

                switch( mediaItemModel.getMedia() ) {

                    case PROGRAM:

                        mediaItemModel.setCreateHttpLiveStreamUrl( String.format( "/Content/AddRecordingLiveStream?RecordedId=%s&Width=1280", String.valueOf( mediaItemModel.getId() ) ) );

                        break;

                    case VIDEO:

                        mediaItemModel.setCreateHttpLiveStreamUrl( String.format( "/Content/AddVideoLiveStream?Id=%s&Width=1280", String.valueOf( mediaItemModel.getId() ) ) );

                        break;

                }

                updateMenu();
                fragment.reload();

            }

        }

    }

    private class RemoveLiveStreamTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground( Void... params ) {
            Log.d( TAG, "doInBackground : mediaItemModel=" + mediaItemModel.toString() );

            OkHttpClient okHttpClient = getNetComponent().okHttpClient();

            final Request request = new Request.Builder()
                    .url( getMasterBackendUrl() + mediaItemModel.getRemoveHttpLiveStreamUrl() )
                    .addHeader( "Accept", "application/json" )
                    .get()
                    .build();

            try {

                BooleanJsonMapper mapper = new BooleanJsonMapper();
                String result = okHttpClient.newCall( request ).execute().body().string();
                Log.d( TAG, "doInBackground : result=" + result );

                return mapper.transformBoolean( result );

            } catch( IOException e ) {

                Log.e( TAG, "doInBackground : error", e );

            }

            return null;

        }

        @Override
        protected void onPostExecute( Boolean result ) {

            if( null != result ) {

                updateMenu();
                fragment.reload();

            }

        }

    }

    private class GetLiveStreamTask extends AsyncTask<Void, Void, LiveStreamInfoModel> {

        @Override
        protected LiveStreamInfoModel doInBackground( Void... params ) {

            return null;
        }

        @Override
        protected void onPostExecute( LiveStreamInfoModel liveStreamInfoModel ) {

            if( null != liveStreamInfoModel ) {

                fragment.reload();

            }

        }

    }

}