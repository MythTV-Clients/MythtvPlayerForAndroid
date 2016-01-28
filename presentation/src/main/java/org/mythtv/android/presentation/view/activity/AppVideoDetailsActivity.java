package org.mythtv.android.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerVideoComponent;
import org.mythtv.android.presentation.internal.di.components.VideoComponent;
import org.mythtv.android.presentation.internal.di.modules.LiveStreamModule;
import org.mythtv.android.presentation.internal.di.modules.VideoModule;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.view.fragment.AppVideoDetailsFragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.ButterKnife;

/**
 * Created by dmfrey on 11/25/15.
 */
public class AppVideoDetailsActivity extends AppAbstractBaseActivity implements HasComponent<VideoComponent>, AppVideoDetailsFragment.VideoDetailsListener {

    private static final String TAG = AppVideoDetailsActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_VIDEO_ID = "org.mythtv.android.INTENT_PARAM_VIDEO_ID";
    private static final String INTENT_EXTRA_PARAM_STORAGE_GROUP = "org.mythtv.android.INTENT_PARAM_STORAGE_GROUP";
    private static final String INTENT_EXTRA_PARAM_FILENAME = "org.mythtv.android.INTENT_PARAM_FILENAME";
    private static final String INTENT_EXTRA_PARAM_HOSTNAME = "org.mythtv.android.INTENT_PARAM_HOSTNAME";
    private static final String INSTANCE_STATE_PARAM_VIDEO_ID = "org.mythtv.android.STATE_PARAM_VIDEO_ID";
    private static final String INSTANCE_STATE_PARAM_STORAGE_GROUP = "org.mythtv.android.STATE_PARAM_STORAGE_GROUP";
    private static final String INSTANCE_STATE_PARAM_FILENAME = "org.mythtv.android.STATE_PARAM_FILENAME";
    private static final String INSTANCE_STATE_PARAM_HOSTNAME = "org.mythtv.android.STATE_PARAM_HOSTNAME";

    private int id;
    private String storageGroup, filename, hostname;
    private VideoComponent videoComponent;

    public static Intent getCallingIntent( Context context, int id, String storageGroup, String filename, String hostname ) {

        Intent callingIntent = new Intent( context, AppVideoDetailsActivity.class );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_VIDEO_ID, id );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_STORAGE_GROUP, storageGroup );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_FILENAME, filename );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_HOSTNAME, hostname );

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
            outState.putString( INSTANCE_STATE_PARAM_STORAGE_GROUP, this.storageGroup );
            outState.putString( INSTANCE_STATE_PARAM_FILENAME, this.filename );
            outState.putString( INSTANCE_STATE_PARAM_HOSTNAME, this.hostname );

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
            this.storageGroup = savedInstanceState.getString( INSTANCE_STATE_PARAM_STORAGE_GROUP );
            this.filename = savedInstanceState.getString( INSTANCE_STATE_PARAM_FILENAME );
            this.hostname = savedInstanceState.getString( INSTANCE_STATE_PARAM_HOSTNAME );

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
        Log.d(TAG, "initializeActivity : enter");

        if( null == savedInstanceState  ) {
            Log.d( TAG, "initializeActivity : savedInstanceState is null" );

            Bundle extras = getIntent().getExtras();
            if( null != extras ) {
                Log.d( TAG, "initializeActivity : extras != null" );

                if( extras.containsKey( INTENT_EXTRA_PARAM_VIDEO_ID ) ) {

                    this.id = getIntent().getIntExtra( INTENT_EXTRA_PARAM_VIDEO_ID, -1 );

                }

                if( extras.containsKey( INTENT_EXTRA_PARAM_STORAGE_GROUP ) ) {

                    this.storageGroup = getIntent().getStringExtra(INTENT_EXTRA_PARAM_STORAGE_GROUP);

                }

                if( extras.containsKey( INTENT_EXTRA_PARAM_FILENAME ) ) {

                    this.filename = getIntent().getStringExtra( INTENT_EXTRA_PARAM_FILENAME );

                }

                if( extras.containsKey( INTENT_EXTRA_PARAM_HOSTNAME ) ) {

                    this.hostname = getIntent().getStringExtra( INTENT_EXTRA_PARAM_HOSTNAME );

                }

            }

            addFragment( R.id.fl_fragment, AppVideoDetailsFragment.newInstance( this.id ) );

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState is not null" );

            this.id = savedInstanceState.getInt( INSTANCE_STATE_PARAM_VIDEO_ID );
            this.storageGroup = savedInstanceState.getString( INSTANCE_STATE_PARAM_STORAGE_GROUP );
            this.filename = savedInstanceState.getString( INSTANCE_STATE_PARAM_FILENAME );
            this.hostname = savedInstanceState.getString( INSTANCE_STATE_PARAM_HOSTNAME );

        }

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.videoComponent = DaggerVideoComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .videoModule( new VideoModule( this.id ) )
                .liveStreamModule( new LiveStreamModule( this.storageGroup, this.filename, this.hostname ) )
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
    public void onPlayVideo( VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "onPlayVideo : enter" );

        if( !getInternalPlayerPreferenceFromPreferences() || getExternalPlayerPreferenceFromPreferences() ) {

            String filename = "";
            try {

                filename = URLEncoder.encode( videoMetadataInfoModel.getFileName(), "UTF-8" );

            } catch( UnsupportedEncodingException e ) { }

            String videoUrl = getMasterBackendUrl()  + "/Content/GetFile?FileName=" + filename;
            Log.d( TAG, "onPlayVideo : videoUrl=" + videoUrl );

            navigator.navigateToExternalPlayer( this, videoUrl );

        } else if( null != videoMetadataInfoModel.getLiveStreamInfo() ) {

            try {
                String videoUrl = getMasterBackendUrl() + URLEncoder.encode( videoMetadataInfoModel.getLiveStreamInfo().getRelativeUrl(), "UTF-8" );
                videoUrl = videoUrl.replaceAll( "%2F", "/" );
                videoUrl = videoUrl.replaceAll( "\\+", "%20" );

//                navigator.navigateToInternalPlayer( this, videoUrl, null, AppPlayerActivity.TYPE_HLS );
                navigator.navigateToVideoPlayer( this, videoUrl );

            } catch( UnsupportedEncodingException e ) { }

        }

        Log.d( TAG, "onPlayRecording : exit" );
    }

    public boolean getInternalPlayerPreferenceFromPreferences() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );

        return sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_INTERNAL_PLAYER, false );
    }

    public boolean getExternalPlayerPreferenceFromPreferences() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );

        return sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_EXTERNAL_PLAYER_OVERRIDE_VIDEO, false );
    }

}
