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

package org.mythtv.android.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mythtv.android.app.R;
import org.mythtv.android.app.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.app.internal.di.components.DvrComponent;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.modules.LiveStreamModule;
import org.mythtv.android.presentation.internal.di.modules.ProgramModule;
import org.mythtv.android.app.view.fragment.ProgramDetailsFragment;
import org.mythtv.android.presentation.internal.di.HasComponent;
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

    private ProgramModel programModel;

    @Bind( R.id.backdrop )
    ImageView backdrop;

    @Bind( R.id.fab )
    FloatingActionButton fab;

    public static Intent getCallingIntent( Context context, int chanId, DateTime startTime ) {

        Intent callingIntent = new Intent( context, ProgramDetailsActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_CHAN_ID, chanId );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_START_TIME, startTime.getMillis() );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_program_details;
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

            case android.R.id.home:

                NavUtils.navigateUpFromSameTask( this );

                return true;

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

                addFragment( R.id.fl_fragment, ProgramDetailsFragment.newInstance() );

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

        this.programModel = programModel;

        Log.d( TAG, "onRecordingLoaded : exit" );
    }

    private void loadBackdrop() {
        Log.d( TAG, "loadBackdrop : enter" );

        String previewUrl = getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + this.chanId + "&StartTime=" + this.startTime.withZone( DateTimeZone.UTC ).toString( "yyyy-MM-dd'T'HH:mm:ss" );
        Log.i( TAG, "loadBackdrop : previewUrl=" + previewUrl );
        final ImageView imageView = (ImageView) findViewById( R.id.backdrop );
        getNetComponent().picasso()
                .load( previewUrl )
                .fit().centerCrop()
                .into( imageView );

        Log.d( TAG, "loadBackdrop : exit" );
    }

    @OnClick( R.id.fab )
    void onButtonFabPlay() {
        Log.d( TAG, "onButtonFabPlay : enter" );

        if( null == this.programModel.getLiveStreamInfo() || this.programModel.getLiveStreamInfo().getPercentComplete() < 2 ) {
            Log.d( TAG, "onButtonFabPlay : stream does not exist or is not ready, send to external player" );

            String recordingUrl = getMasterBackendUrl()  + "/Content/GetFile?FileName=" + programModel.getFileName();

            navigator.navigateToExternalPlayer( this, recordingUrl );

        } else {
            Log.d( TAG, "onButtonFabPlay : stream exists and is ready" );

            try {

                String recordingUrl = getMasterBackendUrl() + URLEncoder.encode( programModel.getLiveStreamInfo().getRelativeUrl(), "UTF-8");
                recordingUrl = recordingUrl.replaceAll( "%2F", "/" );
                recordingUrl = recordingUrl.replaceAll( "\\+", "%20" );

                if( getSharedPreferencesComponent().sharedPreferences().getBoolean( SettingsKeys.KEY_PREF_INTERNAL_PLAYER, true ) ) {
                    Log.d( TAG, "onButtonFabPlay : sending steam to internal player" );

                    navigator.navigateToVideoPlayer( this, recordingUrl );

                } else {
                    Log.d( TAG, "onButtonFabPlay : sending stream to external player" );

                    navigator.navigateToExternalPlayer( this, recordingUrl );

                }

            } catch( UnsupportedEncodingException e ) { }

        }

        Log.d( TAG, "onButtonFabPlay : exit" );
    }

}