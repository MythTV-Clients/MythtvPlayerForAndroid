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

package org.mythtv.android.presentation.internal.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.vincentbrison.openlibraries.android.dualcache.Builder;
import com.vincentbrison.openlibraries.android.dualcache.CacheSerializer;
import com.vincentbrison.openlibraries.android.dualcache.DualCache;
import com.vincentbrison.openlibraries.android.dualcache.JsonSerializer;

import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.EncoderEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.LongJsonMapper;
import org.mythtv.android.data.entity.mapper.ProgramEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.VideoMetadataInfoEntityJsonMapper;
import org.mythtv.android.data.executor.JobExecutor;
import org.mythtv.android.data.net.ContentApi;
import org.mythtv.android.data.net.ContentApiImpl;
import org.mythtv.android.data.net.DvrApi;
import org.mythtv.android.data.net.DvrApiImpl;
import org.mythtv.android.data.net.VideoApi;
import org.mythtv.android.data.net.VideoApiImpl;
import org.mythtv.android.data.repository.DvrDataRepository;
import org.mythtv.android.data.repository.MediaItemDataRepository;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;
import org.mythtv.android.domain.repository.MediaItemRepository;
import org.mythtv.android.presentation.AndroidApplication;
import org.mythtv.android.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
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
    DvrRepository provideDvrRepository( DvrDataRepository dvrDataRepository) {

        return dvrDataRepository;
    }

    @Provides
    @Singleton
    MediaItemRepository provideMediaItemRepository( MediaItemDataRepository mediaItemDataRepository ) {

        return mediaItemDataRepository;
    }

    @Provides
    @Singleton
    BooleanJsonMapper provideBooleanJsonMapper() {

        return new BooleanJsonMapper();
    }

    @Provides
    @Singleton
    LongJsonMapper provideLongJsonMapper() {

        return new LongJsonMapper();
    }

    @Provides
    @Singleton
    TitleInfoEntityJsonMapper provideTitleInfoEntityJsonMapper( final Gson gson ) {

        return new TitleInfoEntityJsonMapper( gson );
    }

    @Provides
    @Singleton
    ProgramEntityJsonMapper provideProgramEntityJsonMapper( final Gson gson ) {

        return new ProgramEntityJsonMapper( gson );
    }

    @Provides
    @Singleton
    EncoderEntityJsonMapper provideEncoderEntityJsonMapper( final Gson gson ) {

        return new EncoderEntityJsonMapper( gson );
    }

    @Provides
    @Singleton
    VideoMetadataInfoEntityJsonMapper provideVideoMetadataInfoEntityJsonMapper( final Gson gson ) {

        return new VideoMetadataInfoEntityJsonMapper( gson );
    }

    @Provides
    @Singleton
    ContentApi provideContentApi( final Context context, final SharedPreferences sharedPreferences, final OkHttpClient okHttpClient, final LiveStreamInfoEntityJsonMapper liveStreamInfoEntityJsonMapper, final BooleanJsonMapper booleanJsonMapper ) {

        return new ContentApiImpl( context, sharedPreferences, okHttpClient, liveStreamInfoEntityJsonMapper, booleanJsonMapper );
    }

    @Provides
    @Singleton
    DvrApi provideDvrApi( final Context context, final SharedPreferences sharedPreferences, final OkHttpClient okHttpClient, final TitleInfoEntityJsonMapper titleInfoEntityJsonMapper, final ProgramEntityJsonMapper programEntityJsonMapper, final EncoderEntityJsonMapper encoderEntityJsonMapper, final LongJsonMapper longJsonMapper, final BooleanJsonMapper booleanJsonMapper ) {

        return new DvrApiImpl( context, sharedPreferences, okHttpClient, titleInfoEntityJsonMapper, programEntityJsonMapper, encoderEntityJsonMapper, booleanJsonMapper, longJsonMapper );
    }

    @Provides
    @Singleton
    VideoApi provideVideoApi( final Context context, final SharedPreferences sharedPreferences, final OkHttpClient okHttpClient, final VideoMetadataInfoEntityJsonMapper videoMetadataInfoEntityJsonMapper, final BooleanJsonMapper booleanJsonMapper ) {

        return new VideoApiImpl( context, sharedPreferences, okHttpClient, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );
    }

    @Provides
    @Singleton
    DualCache<MediaItemEntity> provideCache( /* final Context context */ final Gson gson ) {

//        CacheSerializer<MediaItemEntity> jsonSerializer = new JsonSerializer<>( MediaItemEntity.class );

        return new Builder<MediaItemEntity>( "MythtvPlayerCache", 1 )
                .enableLog()
                .useReferenceInRam( 10485760, mediaItemEntity -> gson.toJson( mediaItemEntity ).length() )
//                .useSerializerInDisk( 10485760, true, jsonSerializer, context )
                .noDisk()
                .build();

    }

}
