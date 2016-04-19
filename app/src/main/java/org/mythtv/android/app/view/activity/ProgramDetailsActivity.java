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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mythtv.android.R;
import org.mythtv.android.app.view.fragment.ProgramDetailsFragment;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.app.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.app.internal.di.components.DvrComponent;
import org.mythtv.android.app.internal.di.modules.LiveStreamModule;
import org.mythtv.android.app.internal.di.modules.ProgramModule;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.model.ProgramModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dmfrey on 9/30/15.
 */
public class ProgramDetailsActivity extends AbstractBaseActivity implements HasComponent<DvrComponent>, ProgramDetailsFragment.ProgramDetailsListener {

    private static final String TAG = ProgramDetailsActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_CHAN_ID = "org.mythtv.android.INTENT_PARAM_CHAN_ID";
    private static final String INTENT_EXTRA_PARAM_START_TIME = "org.mythtv.android.INTENT_PARAM_START_TIME";
    private static final String INSTANCE_STATE_PARAM_CHAN_ID = "org.mythtv.android.STATE_PARAM_CHAN_ID";
    private static final String INSTANCE_STATE_PARAM_START_TIME = "org.mythtv.android.STATE_PARAM_START_TIME";

    private int chanId;
    private DateTime startTime;
    private DvrComponent dvrComponent;

    private ProgramDetailsFragment programDetailsFragment;

    @Bind( R.id.backdrop )
    ImageView backdrop;

    @Bind( R.id.watched )
    ImageView watched;

    @Bind( R.id.hsl_stream )
    ImageView hls_stream;

    public static Intent getCallingIntent( Context context, int chanId, DateTime startTime ) {

        Intent callingIntent = new Intent( context, ProgramDetailsActivity.class );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_CHAN_ID, chanId );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_START_TIME, startTime.getMillis() );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_app_program_details;
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

            outState.putInt( INSTANCE_STATE_PARAM_CHAN_ID, this.chanId );
            outState.putLong( INSTANCE_STATE_PARAM_START_TIME, this.startTime.getMillis() );

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

            this.chanId = savedInstanceState.getInt( INSTANCE_STATE_PARAM_CHAN_ID );
            this.startTime = new DateTime( savedInstanceState.getLong( INSTANCE_STATE_PARAM_START_TIME ) );

            Log.d( TAG, "onRestoreInstanceState : chanId=" + chanId + ", startTime=" + startTime );
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

                programDetailsFragment = ProgramDetailsFragment.newInstance();
                addFragment( R.id.fl_fragment, programDetailsFragment );

            }

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState is not null" );

            this.chanId = savedInstanceState.getInt( INSTANCE_STATE_PARAM_CHAN_ID );
            this.startTime = new DateTime( savedInstanceState.getLong( INSTANCE_STATE_PARAM_START_TIME, -1 ) );

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
            .liveStreamModule( new LiveStreamModule() )
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
    public void onRecordingLoaded( final ProgramModel programModel ) {
        Log.d( TAG, "onRecordingLoaded : enter" );

        updateWatchedStatus( programModel );
        updateHlsStream( programModel.getLiveStreamInfo() );

        Log.d( TAG, "onRecordingLoaded : exit" );
    }

    @Override
    public void onPlayRecording( ProgramModel programModel ) {
        Log.d( TAG, "onPlayRecording : enter" );

        if( !getSharedPreferencesModule().getInternalPlayerPreferenceFromPreferences() ) {

            String recordingUrl = getSharedPreferencesModule().getMasterBackendUrl()  + "/Content/GetFile?FileName=" + programModel.getFileName();

            navigator.navigateToExternalPlayer( this, recordingUrl );

        } else if( null != programModel.getLiveStreamInfo() ) {

            try {

                String recordingUrl = getSharedPreferencesModule().getMasterBackendUrl() + URLEncoder.encode( programModel.getLiveStreamInfo().getRelativeUrl(), "UTF-8" );
                recordingUrl = recordingUrl.replaceAll( "%2F", "/" );
                recordingUrl = recordingUrl.replaceAll( "\\+", "%20" );

                navigator.navigateToVideoPlayer( this, recordingUrl );

            } catch( UnsupportedEncodingException e ) { }

        }

        Log.d( TAG, "onPlayRecording : exit" );
    }

    private void loadBackdrop() {
        Log.d( TAG, "loadBackdrop : enter" );

        String previewUrl = getSharedPreferencesModule().getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + this.chanId + "&StartTime=" + this.startTime.withZone( DateTimeZone.UTC ).toString( "yyyy-MM-dd'T'HH:mm:ss" );
        Log.i( TAG, "loadBackdrop : previewUrl=" + previewUrl );
        final ImageView imageView = (ImageView) findViewById( R.id.backdrop );
        Picasso.with( this )
                .load( previewUrl )
                .fit().centerCrop()
                .into(imageView);

        Log.d( TAG, "loadBackdrop : exit" );
    }

    private void updateWatchedStatus( final ProgramModel programModel ) {
        Log.d( TAG, "updateWatchedStatus : enter" );

        if( null != programModel ) {
            //Log.d( TAG, "updateWatchedStatus : programModel is not null" );

            if( null != programModel.getProgramFlags() ) {
                Log.d( TAG, "updateWatchedStatus : programFlags=0x" + Integer.toHexString( programModel.getProgramFlags() ) );

                boolean watchedStatus = ( programModel.getProgramFlags() & 0x00000200 ) > 0;
                Log.d( TAG, "updateWatchedStatus : watchedStatus=" + watchedStatus );
                if( watchedStatus ) {
                    Log.d( TAG, "updateWatchedStatus : setting to watched" );

                    watched.setImageDrawable( getResources().getDrawable( R.drawable.ic_watched_24dp ) );
                    setTint( watched.getDrawable(), Color.WHITE );

                } else {
                    Log.d( TAG, "updateWatchedStatus : setting to unwatched" );

                    watched.setImageDrawable( getResources().getDrawable( R.drawable.ic_unwatched_24dp ) );
                    setTint( watched.getDrawable(), Color.LTGRAY );

                }

            }

        }

        Log.d( TAG, "updateWatchedStatus : exit" );
    }

    private void updateHlsStream( final LiveStreamInfoModel liveStreamInfoModel ) {
        Log.d( TAG, "updateHlsStream : enter" );

        if( null != liveStreamInfoModel ) {
            Log.d( TAG, "updateHlsStream : liveStreamInfo" + liveStreamInfoModel );

            setTint( hls_stream.getDrawable(), Color.WHITE );

        } else {
            Log.d( TAG, "updateHlsStream : setting hls_stream to delete" );

            setTint( hls_stream.getDrawable(), Color.LTGRAY );

        }

        Log.d( TAG, "updateHlsStream : exit" );
    }

    @OnClick( R.id.watched )
    void onButtonWatched() {
        Log.d( TAG, "onButtonWatched : enter" );

        programDetailsFragment.requestUpdateWatchedStatus();

        Log.d( TAG, "onButtonWatched : exit" );
    }

    @OnClick( R.id.hsl_stream )
    void onButtonHlsStream() {
        Log.d( TAG, "onButtonHlsStream : enter" );

        programDetailsFragment.requestUpdateHlsStream();

        Log.d( TAG, "onButtonHlsStream : exit" );
    }

}
