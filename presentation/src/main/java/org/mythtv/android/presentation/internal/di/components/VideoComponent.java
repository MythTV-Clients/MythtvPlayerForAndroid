package org.mythtv.android.presentation.internal.di.components;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.internal.di.modules.ActivityModule;
import org.mythtv.android.presentation.internal.di.modules.LiveStreamModule;
import org.mythtv.android.presentation.internal.di.modules.VideoModule;
import org.mythtv.android.presentation.internal.di.modules.VideoSeriesModule;
import org.mythtv.android.presentation.internal.di.modules.VideosModule;
import org.mythtv.android.presentation.view.fragment.AdultListFragment;
import org.mythtv.android.presentation.view.fragment.HomeVideoListFragment;
import org.mythtv.android.presentation.view.fragment.MovieListFragment;
import org.mythtv.android.presentation.view.fragment.MusicVideoListFragment;
import org.mythtv.android.presentation.view.fragment.TelevisionListFragment;
import org.mythtv.android.presentation.view.fragment.TelevisionSeriesListFragment;
import org.mythtv.android.presentation.view.fragment.VideoDetailsFragment;

import dagger.Component;

/**
 * Created by dmfrey on 11/9/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, VideosModule.class, VideoModule.class, VideoSeriesModule.class, LiveStreamModule.class } )
public interface VideoComponent {

    void inject( MovieListFragment movieListFragment);
    void inject( TelevisionListFragment televisionListFragment );
    void inject( TelevisionSeriesListFragment televisionListFragment );
    void inject( HomeVideoListFragment homeVideoListFragment );
    void inject( MusicVideoListFragment musicVideoListFragment );
    void inject( AdultListFragment adultListFragment );

    void inject( VideoDetailsFragment videoDetailsFragment );

    void plus( VideosModule videosModule );
    void plus( VideoSeriesModule videoSeriesModule );

}
