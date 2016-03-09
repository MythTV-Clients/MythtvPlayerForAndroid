package org.mythtv.android.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerVideoComponent;
import org.mythtv.android.presentation.internal.di.components.VideoComponent;
import org.mythtv.android.presentation.view.fragment.TvVideosFragment;

public class TvVideosActivity extends TvAbstractBaseActivity implements HasComponent<VideoComponent>, TvVideosFragment.CategoryListener {

    private static final String TAG = TvVideosActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        return new Intent( context, TvVideosActivity.class );
    }

    private VideoComponent videoComponent;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_tv_videos;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.initializeInjector();

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

    @Override
    public void onCategoryClicked( String category ) {

        tvNavigator.navigateToVideoCategory( this, category );

    }

}
