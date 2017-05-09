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

package org.mythtv.android.presentation.view.activity.tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerMediaComponent;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.view.fragment.tv.MediaItemListFragment;

/**
 *
 *
 *
 * @author dmfrey
 */
public class VideoCategoryActivity extends AbstractBaseTvActivity implements HasComponent<MediaComponent>, MediaItemListFragment.MediaItemListListener {

    private static final String TAG = VideoCategoryActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_MEDIA = "org.mythtv.android.INTENT_PARAM_MEDIA";
    private static final String INSTANCE_STATE_PARAM_MEDIA = "org.mythtv.android.STATE_PARAM_MEDIA";

    private Media media;
    private MediaComponent mediaComponent;

    public static Intent getCallingIntent( Context context, Media media ) {

        Intent callingIntent = new Intent( context, VideoCategoryActivity.class );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_MEDIA, media.name() );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_tv_video_category;
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

            outState.putString( INSTANCE_STATE_PARAM_MEDIA, this.media.name() );

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

            this.media = Media.valueOf( savedInstanceState.getString( INSTANCE_STATE_PARAM_MEDIA ) );

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

                if( extras.containsKey( INTENT_EXTRA_PARAM_MEDIA ) ) {

                    this.media = Media.valueOf( getIntent().getStringExtra( INTENT_EXTRA_PARAM_MEDIA ) );

                }

            }

            switch( this.media ) {

                case MOVIE :

                    addFragment( R.id.fl_fragment, MediaItemListFragment.newInstance( new MediaItemListFragment.Builder( Media.MOVIE ).toBundle() ) );

                    break;

                case TELEVISION :

                    addFragment( R.id.fl_fragment, MediaItemListFragment.newInstance( new MediaItemListFragment.Builder( Media.TELEVISION ).tv( true ).toBundle() ) );

                    break;

                case HOMEVIDEO :

                    addFragment( R.id.fl_fragment, MediaItemListFragment.newInstance( new MediaItemListFragment.Builder( Media.HOMEVIDEO ).toBundle() ) );

                    break;

                case MUSICVIDEO :

                    addFragment( R.id.fl_fragment, MediaItemListFragment.newInstance( new MediaItemListFragment.Builder( Media.MUSICVIDEO ).toBundle() ) );

                    break;

                case ADULT :

                    addFragment( R.id.fl_fragment, MediaItemListFragment.newInstance( new MediaItemListFragment.Builder( Media.ADULT ).toBundle() ) );

                    break;

                default :

                    break;

            }

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState is not null" );

            this.media = Media.valueOf( savedInstanceState.getString( INSTANCE_STATE_PARAM_MEDIA ) );

        }

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.mediaComponent = DaggerMediaComponent.builder()
                .applicationComponent( getApplicationComponent() )
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
    public void onSearchClicked() {
        Log.d( TAG, "onSearchClicked : enter" );

        navigator.navigateToSearch( this );

        Log.d( TAG, "onSearchClicked : exit" );
    }

}
