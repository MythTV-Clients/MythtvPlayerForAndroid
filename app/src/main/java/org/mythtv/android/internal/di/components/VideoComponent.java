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

package org.mythtv.android.internal.di.components;

import org.mythtv.android.internal.di.PerActivity;
import org.mythtv.android.internal.di.modules.ActivityModule;
import org.mythtv.android.internal.di.modules.LiveStreamModule;
import org.mythtv.android.internal.di.modules.NetModule;
import org.mythtv.android.internal.di.modules.SharedPreferencesModule;
import org.mythtv.android.internal.di.modules.VideoModule;
import org.mythtv.android.internal.di.modules.VideoSeriesModule;
import org.mythtv.android.internal.di.modules.VideoWatchedStatusModule;
import org.mythtv.android.internal.di.modules.VideosModule;
import org.mythtv.android.view.fragment.phone.HomeVideoListFragment;
import org.mythtv.android.view.fragment.phone.MusicVideoListFragment;
import org.mythtv.android.view.fragment.phone.TelevisionSeriesListFragment;
import org.mythtv.android.view.fragment.phone.VideoDetailsFragment;
import org.mythtv.android.view.fragment.phone.AdultListFragment;
import org.mythtv.android.view.fragment.phone.MovieListFragment;
import org.mythtv.android.view.fragment.phone.TelevisionListFragment;
import org.mythtv.android.view.fragment.tv.VideoAdultFragment;
import org.mythtv.android.view.fragment.tv.VideoHomeMovieFragment;
import org.mythtv.android.view.fragment.tv.VideoMovieFragment;
import org.mythtv.android.view.fragment.tv.VideoMusicVideoFragment;
import org.mythtv.android.view.fragment.tv.VideoTelevisionFragment;

import dagger.Component;

/**
 * Created by dmfrey on 11/9/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, SharedPreferencesModule.class, NetModule.class, VideosModule.class, VideoModule.class, VideoSeriesModule.class, LiveStreamModule.class, VideoWatchedStatusModule.class } )
public interface VideoComponent {

    // Phone/Tablet fragments
    void inject( AdultListFragment videoMovieFragment );
    void inject( HomeVideoListFragment videoMovieFragment );
    void inject( MovieListFragment videoMovieFragment );
    void inject( MusicVideoListFragment videoMovieFragment );
    void inject( TelevisionListFragment videoMovieFragment );
    void inject( TelevisionSeriesListFragment videoMovieFragment );
    void inject( VideoDetailsFragment videoMovieDetailsFragment );

    // TV fragments
    void inject( VideoMovieFragment videoMovieFragment );
    void inject( VideoTelevisionFragment videoMovieFragment );
    void inject( VideoHomeMovieFragment videoMovieFragment );
    void inject( VideoMusicVideoFragment videoMovieFragment );
    void inject( VideoAdultFragment videoMovieFragment );

    // Extra modules
    void plus( VideosModule videosModule );
    void plus( VideoSeriesModule videoSeriesModule );

}
