package org.mythtv.android.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.mythtv.android.R;
import org.mythtv.android.app.internal.di.components.DaggerVideoComponent;
import org.mythtv.android.app.internal.di.components.VideoComponent;
import org.mythtv.android.app.internal.di.modules.LiveStreamModule;
import org.mythtv.android.app.internal.di.modules.VideoModule;
import org.mythtv.android.app.view.fragment.VideoDetailsFragment;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dmfrey on 11/25/15.
 */
public class VideoDetailsActivity extends AbstractBaseActivity implements HasComponent<VideoComponent>, VideoDetailsFragment.VideoDetailsListener {

    private static final String TAG = VideoDetailsActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_VIDEO_ID = "org.mythtv.android.INTENT_PARAM_VIDEO_ID";
    private static final String INSTANCE_STATE_PARAM_VIDEO_ID = "org.mythtv.android.STATE_PARAM_VIDEO_ID";

    private int id;
    private VideoComponent videoComponent;

    private VideoDetailsFragment videoDetailsFragment;

    @Bind( R.id.backdrop )
    ImageView backdrop;

//    @Bind( R.id.watched )
//    ImageView watched;
//
//    @Bind( R.id.hsl_stream )
//    ImageView hls_stream;

    public static Intent getCallingIntent( Context context, int id ) {

        Intent callingIntent = new Intent( context, VideoDetailsActivity.class );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_VIDEO_ID, id );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_app_video_details;
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
    public boolean onNavigateUp() {
        Log.d( TAG, "onNavigateUp : enter" );

        onBackPressed();

        Log.d( TAG, "onNavigateUp : exit" );
        return true;
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        if( null != outState ) {
            Log.d( TAG, "onSaveInstanceState : outState is not null" );

            outState.putInt( INSTANCE_STATE_PARAM_VIDEO_ID, this.id );

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

            this.id = savedInstanceState.getInt( INSTANCE_STATE_PARAM_VIDEO_ID );

        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_details, menu );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case R.id.menu_settings :

                navigator.navigateToVideoSettings( this );

                return true;

        }

        return super.onOptionsItemSelected( item );
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d( TAG, "initializeActivity : enter" );

        if( null == savedInstanceState  ) {
            Log.d( TAG, "initializeActivity : savedInstanceState is null" );

            Bundle extras = getIntent().getExtras();
            if( null != extras ) {
                Log.d( TAG, "initializeActivity : extras != null" );

                if( extras.containsKey( INTENT_EXTRA_PARAM_VIDEO_ID ) ) {

                    this.id = getIntent().getIntExtra( INTENT_EXTRA_PARAM_VIDEO_ID, -1 );

                }

            }

            videoDetailsFragment = VideoDetailsFragment.newInstance();
            addFragment( R.id.fl_fragment, VideoDetailsFragment.newInstance() );

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState is not null" );

            this.id = savedInstanceState.getInt( INSTANCE_STATE_PARAM_VIDEO_ID );

        }

        loadBackdrop();

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.videoComponent = DaggerVideoComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .videoModule( new VideoModule( this.id ) )
                .liveStreamModule( new LiveStreamModule() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public VideoComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return videoComponent;
    }

    @Override
    public void onVideoLoaded( VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "onVideoLoaded : enter" );

        updateWatchedStatus( videoMetadataInfoModel );
        updateHlsStream( videoMetadataInfoModel.getLiveStreamInfo() );

        Log.d( TAG, "onVideoLoaded : exit" );
    }

    @Override
    public void onPlayVideo( VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "onPlayVideo : enter" );

        if( !getSharedPreferencesModule().getInternalPlayerPreferenceFromPreferences() || getSharedPreferencesModule().getExternalPlayerPreferenceFromPreferences() ) {

            String filename = "";
            try {

                filename = URLEncoder.encode( videoMetadataInfoModel.getFileName(), "UTF-8" );

            } catch( UnsupportedEncodingException e ) { }

            String videoUrl = getSharedPreferencesModule().getMasterBackendUrl()  + "/Content/GetFile?FileName=" + filename;
            Log.d( TAG, "onPlayVideo : videoUrl=" + videoUrl );

            navigator.navigateToExternalPlayer( this, videoUrl );

        } else if( null != videoMetadataInfoModel.getLiveStreamInfo() ) {

            try {

                String videoUrl = getSharedPreferencesModule().getMasterBackendUrl() + URLEncoder.encode( videoMetadataInfoModel.getLiveStreamInfo().getRelativeUrl(), "UTF-8" );
                videoUrl = videoUrl.replaceAll( "%2F", "/" );
                videoUrl = videoUrl.replaceAll( "\\+", "%20" );

//                navigator.navigateToInternalPlayer( this, videoUrl, null, PlayerActivity.TYPE_HLS );
                navigator.navigateToVideoPlayer( this, videoUrl );

            } catch( UnsupportedEncodingException e ) { }

        }

        Log.d( TAG, "onPlayRecording : exit" );
    }

    private void loadBackdrop() {
        Log.d( TAG, "loadBackdrop : enter" );

        String previewUrl = getSharedPreferencesModule().getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + this.id + "&Type=banner";
        Log.i( TAG, "loadBackdrop : previewUrl=" + previewUrl );
        final ImageView imageView = (ImageView) findViewById( R.id.backdrop );
        Picasso.with( this )
                .load( previewUrl )
                .fit().centerCrop()
                .into(imageView);

        Log.d( TAG, "loadBackdrop : exit" );
    }

    private void updateWatchedStatus( final VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "updateWatchedStatus : enter" );

//        if( null != programModel ) {
//            //Log.d( TAG, "updateWatchedStatus : programModel is not null" );
//
//            if( null != programModel.getProgramFlags() ) {
//                Log.d( TAG, "updateWatchedStatus : programFlags=0x" + Integer.toHexString( programModel.getProgramFlags() ) );
//
//                boolean watchedStatus = ( programModel.getProgramFlags() & 0x00000200 ) > 0;
//                Log.d( TAG, "updateWatchedStatus : watchedStatus=" + watchedStatus );
//                if( watchedStatus ) {
//                    Log.d( TAG, "updateWatchedStatus : setting to watched" );
//
//                    watched.setImageDrawable( getResources().getDrawable( R.drawable.ic_watched_24dp ) );
//
//                } else {
//                    Log.d( TAG, "updateWatchedStatus : setting to unwatched" );
//
//                    watched.setImageDrawable( getResources().getDrawable( R.drawable.ic_unwatched_24dp ) );
//
//                }
//
//            }
//
//        }

        Log.d( TAG, "updateWatchedStatus : exit" );
    }

    private void updateHlsStream( final LiveStreamInfoModel liveStreamInfoModel ) {
        Log.d( TAG, "updateHlsStream : enter" );

//        if( null != liveStreamInfoModel ) {
//            Log.d( TAG, "updateHlsStream : liveStreamInfo" + liveStreamInfoModel );
//
//            setTint( hls_stream.getDrawable(), Color.WHITE );
//
//        } else {
//            Log.d( TAG, "updateHlsStream : setting hls_stream to delete" );
//
//            setTint( hls_stream.getDrawable(), Color.LTGRAY );
//
//        }

        Log.d( TAG, "updateHlsStream : exit" );
    }

}
