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

package org.mythtv.android.data.repository.datasource;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.mythtv.android.data.cache.VideoCache;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.VideoMetadataInfoEntityJsonMapper;
import org.mythtv.android.data.net.VideoApi;
import org.mythtv.android.data.net.VideoApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;

/**
 * Created by dmfrey on 11/3/15.
 */
@Singleton
public class VideoDataStoreFactory {

    private static final String TAG = VideoDataStoreFactory.class.getSimpleName();

    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final OkHttpClient okHttpClient;
    private final Gson gson;
    private final VideoCache videoCache;
    private final SearchDataStoreFactory searchDataStoreFactory;

    @Inject
    public VideoDataStoreFactory( Context context, SharedPreferences sharedPreferences, OkHttpClient okHttpClient, Gson gson, VideoCache videoCache, SearchDataStoreFactory searchDataStoreFactory ) {
        Log.d( TAG, "initialize : enter" );

        if( null == context || null == sharedPreferences || null == okHttpClient || null == gson  || null == videoCache || null == searchDataStoreFactory ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.context = context.getApplicationContext();
        this.sharedPreferences = sharedPreferences;
        this.okHttpClient = okHttpClient;
        this.gson = gson;
        this.videoCache = videoCache;
        this.searchDataStoreFactory = searchDataStoreFactory;

        Log.d( TAG, "initialize : exit" );
    }

    public VideoDataStore create() {
        Log.d( TAG, "create : enter" );

        VideoDataStore videoDataStore;

        if( !this.videoCache.isExpired() && this.videoCache.isCached() ) {
            Log.d( TAG, "create : cache is not expired and videos exists in cache" );

            videoDataStore = new DiskVideoDataStore( this.videoCache );

        } else {
            Log.d( TAG, "create : query backend for data" );

            videoDataStore = createMasterBackendDataStore();

        }

        Log.d( TAG, "create : exit" );
        return videoDataStore;
    }

    public VideoDataStore createMasterBackendDataStore() {
        Log.d( TAG, "createMasterBackendDataStore : enter" );

        VideoMetadataInfoEntityJsonMapper videoMetadataInfoEntityJsonMapper = new VideoMetadataInfoEntityJsonMapper( this.gson );
        BooleanJsonMapper booleanJsonMapper = new BooleanJsonMapper();
        VideoApi api = new VideoApiImpl( this.context, this.sharedPreferences, this.okHttpClient, videoMetadataInfoEntityJsonMapper, booleanJsonMapper );

        Log.d( TAG, "createMasterBackendDataStore : exit" );
        return new MasterBackendVideoDataStore( api, this.videoCache, this.searchDataStoreFactory );
    }

    public VideoDataStore createCategoryDataStore( String category ) {
        Log.d( TAG, "createCategoryDataStore : enter" );

        VideoDataStore videoDataStore;

        if( this.videoCache.isExpired() && this.videoCache.isCached() ) {
            Log.d( TAG, "createCategoryCategoryDataStore : cache is not expired" );

            videoDataStore = new DiskVideoDataStore( this.videoCache );

        } else {
            Log.d( TAG, "createCategoryCategoryDataStore : query backend for data" );

            videoDataStore = createMasterBackendDataStore();

        }

        Log.d( TAG, "createCategoryDataStore : exit" );
        return videoDataStore;
    }

}
