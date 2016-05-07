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
