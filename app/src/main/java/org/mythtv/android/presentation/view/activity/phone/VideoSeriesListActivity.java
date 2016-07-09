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
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import org.mythtv.android.presentation.R;
import org.mythtv.android.presentation.view.fragment.phone.TelevisionSeriesListFragment;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerVideoComponent;
import org.mythtv.android.presentation.internal.di.components.VideoComponent;
import org.mythtv.android.presentation.internal.di.modules.VideoSeriesModule;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

/**
 * Activity that shows a list of programs.
 *
 * Created by dmfrey on 9/1/15.
 */
public class VideoSeriesListActivity extends AbstractBasePhoneActivity implements HasComponent<VideoComponent>, TelevisionSeriesListFragment.VideoMetadataInfoListListener {

    private static final String TAG = VideoSeriesListActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_SERIES = "org.mythtv.android.INTENT_PARAM_SERIES";
    private static final String INSTANCE_STATE_PARAM_SERIES = "org.mythtv.android.STATE_PARAM_SERIES";

    public static Intent getCallingIntent( Context context, String series ) {

        Intent callingIntent = new Intent( context, VideoSeriesListActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_SERIES, series );

        return callingIntent;
    }

    private String series;
    private VideoComponent videoComponent;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_video_series_list;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
        super.onCreate( savedInstanceState );

        this.initializeActivity( savedInstanceState );
        this.initializeInjector();

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        if( null != outState ) {
            Log.d( TAG, "onSaveInstanceState : outState is not null" );

            outState.putString( INSTANCE_STATE_PARAM_SERIES, this.series );

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

            this.series = savedInstanceState.getString( INSTANCE_STATE_PARAM_SERIES );

        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {

        switch( item.getItemId() ) {

            case android.R.id.home:

                onBackPressed();

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

                if( extras.containsKey( INTENT_EXTRA_PARAM_SERIES ) ) {

                    this.series = getIntent().getStringExtra( INTENT_EXTRA_PARAM_SERIES );

                }

            }

            addFragment( R.id.fl_fragment, TelevisionSeriesListFragment.newInstance( this.series ) );

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState is not null" );

            this.series = savedInstanceState.getString( INSTANCE_STATE_PARAM_SERIES );

        }

        setTitle( this.series );

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.videoComponent = DaggerVideoComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .videoSeriesModule( new VideoSeriesModule( this.series ) )
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
    public void onVideoMetadataInfoClicked( VideoMetadataInfoModel videoMetadataInfoModel ) {
        Log.d( TAG, "onVideoMetadataInfoClicked : enter" );

        Log.d( TAG, "onVideoMetadataInfoClicked : videoMetadataInfoModel=" + videoMetadataInfoModel );
        navigator.navigateToVideo( this, videoMetadataInfoModel.getId(), null, videoMetadataInfoModel.getFileName(), videoMetadataInfoModel.getHostName() );

        Log.d( TAG, "onVideoMetadataInfoClicked : exit" );
    }

}
