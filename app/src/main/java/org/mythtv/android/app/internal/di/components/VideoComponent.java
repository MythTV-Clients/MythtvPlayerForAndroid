package org.mythtv.android.app.internal.di.components;

import org.mythtv.android.presentation.internal.di.modules.VideoWatchedStatusModule;
import org.mythtv.android.app.view.fragment.HomeVideoListFragment;
import org.mythtv.android.app.view.fragment.MusicVideoListFragment;
import org.mythtv.android.app.view.fragment.TelevisionSeriesListFragment;
import org.mythtv.android.app.view.fragment.VideoDetailsFragment;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.app.internal.di.modules.ActivityModule;
import org.mythtv.android.presentation.internal.di.modules.LiveStreamModule;
import org.mythtv.android.presentation.internal.di.modules.VideoModule;
import org.mythtv.android.presentation.internal.di.modules.VideoSeriesModule;
import org.mythtv.android.presentation.internal.di.modules.VideosModule;
import org.mythtv.android.app.view.fragment.AdultListFragment;
import org.mythtv.android.app.view.fragment.MovieListFragment;
import org.mythtv.android.app.view.fragment.TelevisionListFragment;

import dagger.Component;

/**
 * Created by dmfrey on 11/9/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, VideosModule.class, VideoModule.class, VideoSeriesModule.class, LiveStreamModule.class, VideoWatchedStatusModule.class } )
public interface VideoComponent {

    void inject( AdultListFragment videoMovieFragment );
    void inject( HomeVideoListFragment videoMovieFragment );
    void inject( MovieListFragment videoMovieFragment );
    void inject( MusicVideoListFragment videoMovieFragment );
    void inject( TelevisionListFragment videoMovieFragment );
    void inject( TelevisionSeriesListFragment videoMovieFragment );
    void inject( VideoDetailsFragment videoMovieDetailsFragment );

    void plus( VideosModule videosModule );
    void plus( VideoSeriesModule videoSeriesModule );

}
