package org.mythtv.android.presentation.internal.di.components;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.internal.di.modules.ActivityModule;
import org.mythtv.android.presentation.internal.di.modules.LiveStreamModule;
import org.mythtv.android.presentation.internal.di.modules.VideoModule;
import org.mythtv.android.presentation.internal.di.modules.VideoSeriesModule;
import org.mythtv.android.presentation.internal.di.modules.VideosModule;
import org.mythtv.android.presentation.view.fragment.AppAdultListFragment;
import org.mythtv.android.presentation.view.fragment.AppHomeVideoListFragment;
import org.mythtv.android.presentation.view.fragment.AppMovieListFragment;
import org.mythtv.android.presentation.view.fragment.AppMusicVideoListFragment;
import org.mythtv.android.presentation.view.fragment.AppTelevisionListFragment;
import org.mythtv.android.presentation.view.fragment.AppTelevisionSeriesListFragment;
import org.mythtv.android.presentation.view.fragment.AppVideoDetailsFragment;

import dagger.Component;

/**
 * Created by dmfrey on 11/9/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, VideosModule.class, VideoModule.class, VideoSeriesModule.class, LiveStreamModule.class } )
public interface VideoComponent {

    void inject( AppMovieListFragment movieListFragment);
    void inject( AppTelevisionListFragment televisionListFragment );
    void inject( AppTelevisionSeriesListFragment televisionListFragment );
    void inject( AppHomeVideoListFragment homeVideoListFragment );
    void inject( AppMusicVideoListFragment musicVideoListFragment );
    void inject( AppAdultListFragment adultListFragment );

    void inject( AppVideoDetailsFragment videoDetailsFragment );

    void plus( VideosModule videosModule );
    void plus( VideoSeriesModule videoSeriesModule );

}
