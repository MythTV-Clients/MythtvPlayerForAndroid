package org.mythtv.android.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.domain.ContentType;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerVideoComponent;
import org.mythtv.android.presentation.internal.di.components.VideoComponent;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.view.fragment.TvAbstractBaseVideoFragment;
import org.mythtv.android.presentation.view.fragment.TvVideoAdultFragment;
import org.mythtv.android.presentation.view.fragment.TvVideoHomeMovieFragment;
import org.mythtv.android.presentation.view.fragment.TvVideoMovieFragment;
import org.mythtv.android.presentation.view.fragment.TvVideoMusicVideoFragment;
import org.mythtv.android.presentation.view.fragment.TvVideoTelevisionFragment;

public class TvVideoCategoryActivity extends TvAbstractBaseActivity implements HasComponent<VideoComponent> {

    private static final String TAG = TvVideoCategoryActivity.class.getSimpleName();

    private static final String INTENT_EXTRA_PARAM_CATEGORY = "org.mythtv.android.INTENT_PARAM_CATEGORY";
    private static final String INSTANCE_STATE_PARAM_CATEGORY = "org.mythtv.android.STATE_PARAM_CATEGORY";

    private String category;
    private VideoComponent videoComponent;

    public static Intent getCallingIntent( Context context, String category ) {

        Intent callingIntent = new Intent( context, TvVideoCategoryActivity.class );
        callingIntent.putExtra( INTENT_EXTRA_PARAM_CATEGORY, category );

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

                    addFragment( R.id.fl_fragment, TvVideoMovieFragment.newInstance() );

                    break;

                case ContentType.TELEVISION :

                    addFragment( R.id.fl_fragment, TvVideoTelevisionFragment.newInstance() );

                    break;

                case ContentType.HOMEVIDEO :

                    addFragment( R.id.fl_fragment, TvVideoHomeMovieFragment.newInstance() );

                    break;

                case ContentType.MUSICVIDEO :

                    addFragment( R.id.fl_fragment, TvVideoMusicVideoFragment.newInstance() );

                    break;

                case ContentType.ADULT :

                    addFragment( R.id.fl_fragment, TvVideoAdultFragment.newInstance() );

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
