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

package org.mythtv.android.app.internal.di.modules;

import android.content.Context;

import org.mythtv.android.app.AndroidApplication;
import org.mythtv.android.data.cache.VideoCacheImpl;
import org.mythtv.android.data.cache.ProgramCache;
import org.mythtv.android.data.cache.ProgramCacheImpl;
import org.mythtv.android.data.cache.VideoCache;
import org.mythtv.android.data.executor.JobExecutor;
import org.mythtv.android.data.repository.ContentDataRepository;
import org.mythtv.android.data.repository.DvrDataRepository;
import org.mythtv.android.data.repository.SearchDataRepository;
import org.mythtv.android.data.repository.VideoDataRepository;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;
import org.mythtv.android.domain.repository.DvrRepository;
import org.mythtv.android.domain.repository.SearchRepository;
import org.mythtv.android.domain.repository.VideoRepository;
import org.mythtv.android.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 *
 * Created by dmfrey on 8/30/15.
 */
@Module
public class ApplicationModule {

    private final AndroidApplication application;

    public ApplicationModule( AndroidApplication application ) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor( JobExecutor jobExecutor ) {

        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread( UIThread uiThread ) {

        return uiThread;
    }

    @Provides
    @Singleton
    ProgramCache provideRecordedProgramCache( ProgramCacheImpl recordedProgramCache ) {

        return recordedProgramCache;
    }

    @Provides
    @Singleton
    DvrRepository provideDvrRepository( DvrDataRepository dvrDataRepository) {

        return dvrDataRepository;
    }

    @Provides
    @Singleton
    SearchRepository provideSearchRepository( SearchDataRepository searchDataRepository ) {

        return searchDataRepository;
    }

    @Provides
    @Singleton
    ContentRepository provideContentRepository( ContentDataRepository contentDataRepository ) {

        return contentDataRepository;
    }

    @Provides
    @Singleton
    VideoCache provideVideoCache( VideoCacheImpl videoCacheImpl ) {

        return videoCacheImpl;
    }

    @Provides
    @Singleton
    VideoRepository provideVideoRepository( VideoDataRepository videoDataRepository ) {

        return videoDataRepository;
    }

}
