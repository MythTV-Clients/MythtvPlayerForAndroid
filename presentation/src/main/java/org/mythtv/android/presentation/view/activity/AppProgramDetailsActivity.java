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
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.presentation.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.internal.di.modules.LiveStreamModule;
import org.mythtv.android.presentation.internal.di.modules.ProgramModule;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.view.fragment.AppProgramDetailsFragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dmfrey on 9/30/15.
 */
public class AppProgramDetailsActivity extends AppAbstractBaseActivity implements HasComponent<DvrComponent>, AppProgramDetailsFragment.ProgramDetailsListener {

    private static final String TAG = AppProgramDetailsActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_CHAN_ID = "org.mythtv.android.INTENT_PARAM_CHAN_ID";
    private static final String INTENT_EXTRA_PARAM_START_TIME = "org.mythtv.android.INTENT_PARAM_START_TIME";
    private static final String INTENT_EXTRA_PARAM_STORAGE_GROUP = "org.mythtv.android.INTENT_PARAM_STORAGE_GROUP";
    private static final String INTENT_EXTRA_PARAM_FILENAME = "org.mythtv.android.INTENT_PARAM_FILENAME";
    private static final String INTENT_EXTRA_PARAM_HOSTNAME = "org.mythtv.android.INTENT_PARAM_HOSTNAME";
    private static final String INSTANCE_STATE_PARAM_CHAN_ID = "org.mythtv.android.STATE_PARAM_CHAN_ID";
    private static final String INSTANCE_STATE_PARAM_START_TIME = "org.mythtv.android.STATE_PARAM_START_TIME";
    private static final String INSTANCE_STATE_PARAM_STORAGE_GROUP = "org.mythtv.android.STATE_PARAM_STORAGE_GROUP";
    private static final String INSTANCE_STATE_PARAM_FILENAME = "org.mythtv.android.STATE_PARAM_FILENAME";
    private static final String INSTANCE_STATE_PARAM_HOSTNAME = "org.mythtv.android.STATE_PARAM_HOSTNAME";

    private int chanId;
    private DateTime startTime;
    private String storageGroup, filename, hostname;
    private DvrComponent dvrComponent;

    @Bind( R.id.backdrop )
    ImageView backdrop;

    public static Intent getCallingIntent( Context context, int chanId, DateTime startTime, String storageGroup, String filename, String hostname ) {

        Intent callingIntent = new Intent( context, AppProgramDetailsActivity.class );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_CHAN_ID, chanId );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_START_TIME, startTime.getMillis() );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_STORAGE_GROUP, storageGroup );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_FILENAME, filename );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_HOSTNAME, hostname );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_app_program_details;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d(TAG, "onCreate : enter");

        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );

        super.onCreate( savedInstanceState );

        ButterKnife.bind( this );

        this.initializeActivity( savedInstanceState );
        this.initializeInjector();

        Log.d(TAG, "onCreate : exit");
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

            outState.putInt( INSTANCE_STATE_PARAM_CHAN_ID, this.chanId );
            outState.putLong( INSTANCE_STATE_PARAM_START_TIME, this.startTime.getMillis() );
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
        super.onRestoreInstanceState(savedInstanceState);

        if( null != savedInstanceState ) {
            Log.d( TAG, "onRestoreInstanceState : savedInstanceState != null" );

            this.chanId = savedInstanceState.getInt( INSTANCE_STATE_PARAM_CHAN_ID );
            this.startTime = new DateTime( savedInstanceState.getLong( INSTANCE_STATE_PARAM_START_TIME ) );
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

                navigator.navigateToProgramSettings( this );

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

                if( extras.containsKey( INTENT_EXTRA_PARAM_CHAN_ID ) ) {

                    this.chanId = getIntent().getIntExtra( INTENT_EXTRA_PARAM_CHAN_ID, -1 );

                }

                if( extras.containsKey( INTENT_EXTRA_PARAM_START_TIME ) ) {

                    this.startTime = new DateTime( getIntent().getLongExtra( INTENT_EXTRA_PARAM_START_TIME, -1 ) );

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

            addFragment( R.id.fl_fragment, AppProgramDetailsFragment.newInstance( this.chanId, this.startTime ) );

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState is not null" );

            this.chanId = savedInstanceState.getInt( INSTANCE_STATE_PARAM_CHAN_ID );
            this.startTime = new DateTime( savedInstanceState.getLong( INSTANCE_STATE_PARAM_START_TIME, -1 ) );
            this.storageGroup = savedInstanceState.getString( INSTANCE_STATE_PARAM_STORAGE_GROUP );
            this.filename = savedInstanceState.getString( INSTANCE_STATE_PARAM_FILENAME );
            this.hostname = savedInstanceState.getString( INSTANCE_STATE_PARAM_HOSTNAME );

        }

        loadBackdrop();

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.dvrComponent = DaggerDvrComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .programModule( new ProgramModule( this.chanId, this.startTime ) )
                .liveStreamModule( new LiveStreamModule( this.storageGroup, this.filename, this.hostname ) )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public DvrComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return dvrComponent;
    }

    @Override
    public void onPlayRecording( ProgramModel programModel ) {
        Log.d( TAG, "onPlayRecording : enter" );

        if( !getInternalPlayerPreferenceFromPreferences() ) {

            String recordingUrl = getSharedPreferencesModule().getMasterBackendUrl()  + "/Content/GetFile?FileName=" + programModel.getFileName();

            navigator.navigateToExternalPlayer( this, recordingUrl );

        } else if( null != programModel.getLiveStreamInfo() ) {

            try {

                String recordingUrl = getSharedPreferencesModule().getMasterBackendUrl() + URLEncoder.encode( programModel.getLiveStreamInfo().getRelativeUrl(), "UTF-8" );
                recordingUrl = recordingUrl.replaceAll( "%2F", "/" );
                recordingUrl = recordingUrl.replaceAll( "\\+", "%20" );

//                navigator.navigateToInternalPlayer( this, recordingUrl, null, AppPlayerActivity.TYPE_HLS );
                navigator.navigateToVideoPlayer( this, recordingUrl );

            } catch( UnsupportedEncodingException e ) { }

        }

        Log.d( TAG, "onPlayRecording : exit" );
    }

    private void loadBackdrop() {
        Log.d( TAG, "loadBackdrop : enter" );

        String previewUrl = getSharedPreferencesModule().getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + this.chanId + "&StartTime=" + this.startTime.withZone(DateTimeZone.UTC).toString( "yyyy-MM-dd'T'HH:mm:ss" );
        Log.i( TAG, "loadBackdrop : previewUrl=" + previewUrl );
        final ImageView imageView = (ImageView) findViewById( R.id.backdrop );
        Picasso.with( this )
                .load( previewUrl )
                .fit().centerCrop()
                .into(imageView);

        Log.d( TAG, "loadBackdrop : exit" );
    }

    public boolean getInternalPlayerPreferenceFromPreferences() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );

        return sharedPreferences.getBoolean( SettingsKeys.KEY_PREF_INTERNAL_PLAYER, false );
    }

}
