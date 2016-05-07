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

package org.mythtv.android.tv.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.tv.R;
import org.mythtv.android.domain.ContentType;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.tv.internal.di.components.DaggerVideoComponent;
import org.mythtv.android.tv.internal.di.components.VideoComponent;
import org.mythtv.android.tv.view.fragment.VideoAdultFragment;
import org.mythtv.android.tv.view.fragment.VideoHomeMovieFragment;
import org.mythtv.android.tv.view.fragment.VideoMovieFragment;
import org.mythtv.android.tv.view.fragment.VideoMusicVideoFragment;
import org.mythtv.android.tv.view.fragment.VideoTelevisionFragment;

public class VideoCategoryActivity extends AbstractBaseActivity implements HasComponent<VideoComponent> {

    private static final String TAG = VideoCategoryActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_CATEGORY = "org.mythtv.android.INTENT_PARAM_CATEGORY";
    private static final String INSTANCE_STATE_PARAM_CATEGORY = "org.mythtv.android.STATE_PARAM_CATEGORY";

    private String category;
    private VideoComponent videoComponent;

    public static Intent getCallingIntent( Context context, String category ) {

        Intent callingIntent = new Intent( context, VideoCategoryActivity.class );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_CATEGORY, category );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_video_category;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.initializeActivity( savedInstanceState );
        this.initializeInjector();

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        if( null != outState ) {
            Log.d( TAG, "onSaveInstanceState : outState is not null" );

            outState.putString( INSTANCE_STATE_PARAM_CATEGORY, this.category );

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

            this.category = savedInstanceState.getString( INSTANCE_STATE_PARAM_CATEGORY );

        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
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

                if( extras.containsKey( INTENT_EXTRA_PARAM_CATEGORY ) ) {

                    this.category = getIntent().getStringExtra( INTENT_EXTRA_PARAM_CATEGORY );

                }

            }

            switch( this.category ) {

                case ContentType.MOVIE :

                    addFragment( R.id.fl_fragment, VideoMovieFragment.newInstance() );

                    break;

                case ContentType.TELEVISION :

                    addFragment( R.id.fl_fragment, VideoTelevisionFragment.newInstance() );

                    break;

                case ContentType.HOMEVIDEO :

                    addFragment( R.id.fl_fragment, VideoHomeMovieFragment.newInstance() );

                    break;

                case ContentType.MUSICVIDEO :

                    addFragment( R.id.fl_fragment, VideoMusicVideoFragment.newInstance() );

                    break;

                case ContentType.ADULT :

                    addFragment( R.id.fl_fragment, VideoAdultFragment.newInstance() );

                    break;

            }

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState is not null" );

            this.category = savedInstanceState.getString( INSTANCE_STATE_PARAM_CATEGORY );

        }

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.videoComponent = DaggerVideoComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public VideoComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return videoComponent;
    }

}
