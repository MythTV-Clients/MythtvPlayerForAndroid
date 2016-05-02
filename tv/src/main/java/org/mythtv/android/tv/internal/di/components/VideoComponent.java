package org.mythtv.android.tv.internal.di.components;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.internal.di.modules.LiveStreamModule;
import org.mythtv.android.presentation.internal.di.modules.VideoModule;
import org.mythtv.android.presentation.internal.di.modules.VideoSeriesModule;
import org.mythtv.android.presentation.internal.di.modules.VideosModule;
import org.mythtv.android.tv.internal.di.modules.ActivityModule;
import org.mythtv.android.tv.view.fragment.VideoAdultFragment;
import org.mythtv.android.tv.view.fragment.VideoHomeMovieFragment;
import org.mythtv.android.tv.view.fragment.VideoMovieFragment;
import org.mythtv.android.tv.view.fragment.VideoMusicVideoFragment;
import org.mythtv.android.tv.view.fragment.VideoTelevisionFragment;

import dagger.Component;

/**
 * Created by dmfrey on 11/9/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, VideosModule.class, VideoModule.class, VideoSeriesModule.class, LiveStreamModule.class } )
public interface VideoComponent {

    void inject( VideoMovieFragment videoMovieFragment );
    void inject( VideoTelevisionFragment videoMovieFragment );
    void inject( VideoHomeMovieFragment videoMovieFragment );
    void inject( VideoMusicVideoFragment videoMovieFragment );
    void inject( VideoAdultFragment videoMovieFragment );

    void plus(VideosModule videosModule);
    void plus(VideoSeriesModule videoSeriesModule);

}
