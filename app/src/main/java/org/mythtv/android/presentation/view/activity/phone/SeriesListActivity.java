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
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerMediaComponent;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.view.fragment.phone.MediaItemListFragment;
import org.mythtv.android.presentation.view.listeners.MediaItemListListener;

/**
 * Activity that shows a list of programs.
 *
 * @author dmfrey
 *
 * Created on 9/1/15.
 */
public class SeriesListActivity extends AbstractBasePhoneActivity implements HasComponent<MediaComponent>, View.OnClickListener, MediaItemListListener /*, NotifyListener*/ {

    private static final String TAG = SeriesListActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_MEDIA = "org.mythtv.android.INTENT_PARAM_MEDIA";
    private static final String INSTANCE_STATE_PARAM_MEDIA = "org.mythtv.android.STATE_PARAM_MEDIA";

    private static final String INTENT_EXTRA_PARAM_TITLE_REG_EX = "org.mythtv.android.INTENT_PARAM_TITLE_REG_EX";
    private static final String INSTANCE_STATE_PARAM_TITLE_REG_EX = "org.mythtv.android.STATE_PARAM_TITLE_REG_EX";

    private static final String INTENT_EXTRA_PARAM_INETREF = "org.mythtv.android.INTENT_PARAM_INETREF";
    private static final String INSTANCE_STATE_PARAM_INETREF = "org.mythtv.android.STATE_PARAM_INETREF";

    private Media media;
    private String titleRegEx = null, inetref = null;

    private MediaItemListFragment fragment;

    private MediaComponent mediaComponent;

    public static Intent getCallingIntent( Context context, Media media, String titleRegEx, String inetref ) {

        Intent callingIntent = new Intent( context, SeriesListActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );

        if( null == media ) {

            throw new IllegalArgumentException( "Key [" + INTENT_EXTRA_PARAM_MEDIA + "] is required!" );
        }

        callingIntent.putExtra( INTENT_EXTRA_PARAM_MEDIA, media.name() );

        if( null != titleRegEx ) {

            callingIntent.putExtra( INTENT_EXTRA_PARAM_TITLE_REG_EX, titleRegEx );

        }

        if( null != inetref ) {

            callingIntent.putExtra( INTENT_EXTRA_PARAM_INETREF, inetref );

        }

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_program_list;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        super.onCreate( savedInstanceState );

        this.initializeActivity( savedInstanceState );
        this.initializeInjector();

//        mFab.setOnClickListener( this );

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        Log.d( TAG, "onSaveInstanceState : enter" );

        if( null != outState ) {
            Log.d( TAG, "onSaveInstanceState : outState is not null" );

            outState.putString( INSTANCE_STATE_PARAM_MEDIA, this.media.name() );

            if( null != this.titleRegEx && !"".equals( titleRegEx ) ) {
                Log.d( TAG, "onSaveInstanceState : saving titleRegEx" );

                outState.putString( INSTANCE_STATE_PARAM_TITLE_REG_EX, this.titleRegEx );

            }

            if( null != this.inetref && !"".equals( inetref ) ) {
                Log.d( TAG, "onSaveInstanceState : saving inetref" );

                outState.putString( INSTANCE_STATE_PARAM_INETREF, this.inetref );

            }

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

            this.media = Media.valueOf( savedInstanceState.getString( INSTANCE_STATE_PARAM_MEDIA ) );

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_TITLE_REG_EX ) ) {
                Log.d( TAG, "onRestoreInstanceState : restoring titleRegEx" );

                this.titleRegEx = savedInstanceState.getString( INSTANCE_STATE_PARAM_TITLE_REG_EX );
                Log.d( TAG, "onRestoreInstanceState : restored titleRegEx=" + this.titleRegEx );

            }

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_INETREF ) ) {
                Log.d( TAG, "onRestoreInstanceState : restoring inetref" );

                this.inetref = savedInstanceState.getString( INSTANCE_STATE_PARAM_INETREF );
                Log.d( TAG, "onRestoreInstanceState : restored inetref=" + this.inetref );

            }

        }

        Log.d( TAG, "onRestoreInstanceState : exit" );
    }

    @Override
    public void onClick( View v ) {

        fragment.reload();

    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d( TAG, "initializeActivity : enter" );

        if( null == savedInstanceState ) {
            Log.d( TAG, "initializeActivity : savedInstanceState == null" );

            Bundle extras = getIntent().getExtras();
            if( null != extras ) {

                this.media = Media.valueOf( getIntent().getStringExtra( INTENT_EXTRA_PARAM_MEDIA ) );

                if( extras.containsKey( INTENT_EXTRA_PARAM_TITLE_REG_EX ) ) {

                    this.titleRegEx = extras.getString( INTENT_EXTRA_PARAM_TITLE_REG_EX );
                    Log.d( TAG, "initializeActivity : restored titleRegEx=" + this.titleRegEx );

                }

                if( extras.containsKey( INTENT_EXTRA_PARAM_INETREF ) ) {

                    this.inetref = extras.getString( INTENT_EXTRA_PARAM_INETREF );
                    Log.d( TAG, "initializeActivity : restored inetref=" + this.inetref );

                }

            }

            fragment = MediaItemListFragment.newInstance( media, titleRegEx, inetref );

            addFragment( R.id.fl_fragment, fragment );

        } else {
            Log.d( TAG, "initializeActivity : savedInstanceState != null" );

            this.media = Media.valueOf( savedInstanceState.getString( INSTANCE_STATE_PARAM_MEDIA ) );

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_TITLE_REG_EX ) ) {

                this.titleRegEx = savedInstanceState.getString( INSTANCE_STATE_PARAM_TITLE_REG_EX );
                Log.d( TAG, "initializeActivity : restored titleRegEx=" + this.titleRegEx );

            }

            if( savedInstanceState.containsKey( INSTANCE_STATE_PARAM_INETREF ) ) {

                this.inetref = savedInstanceState.getString( INSTANCE_STATE_PARAM_INETREF );
                Log.d( TAG, "initializeActivity : restored inetref=" + this.inetref );

            }

        }

        if( null != titleRegEx && !"".equals( titleRegEx ) ) {
            Log.d( TAG, "initializeActivity : setting toolbar title to '" + titleRegEx + "'" );

            getSupportActionBar().setTitle( titleRegEx );

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
    public void onMediaItemClicked( final MediaItemModel mediaItemModel, final View sharedElement, final String sharedElementName ) {
        Log.d( TAG, "onMediaItemClicked : enter" );

        Log.d( TAG, "onMediaItemClicked : mediaItemModel=" + mediaItemModel.toString() );

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation( this, sharedElement, sharedElementName );
        navigator.navigateToMediaItem( this, mediaItemModel.id(), mediaItemModel.media(), options );

        Log.d( TAG, "onMediaItemClicked : exit" );
    }

}
