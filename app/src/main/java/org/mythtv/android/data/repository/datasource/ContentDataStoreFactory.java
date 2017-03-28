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

import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityJsonMapper;
import org.mythtv.android.data.net.ContentApi;
import org.mythtv.android.data.net.ContentApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/27/15.
 */
@Singleton
public class ContentDataStoreFactory {

    private static final String TAG = ContentDataStoreFactory.class.getSimpleName();

    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final Gson gson;
    private final OkHttpClient okHttpClient;

    @Inject
    public ContentDataStoreFactory( Context context, SharedPreferences sharedPreferences, Gson gson, OkHttpClient okHttpClient ) {
        Log.d( TAG, "initialize : enter" );

        if( null == context || null == sharedPreferences || null == gson || null == okHttpClient ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.context = context;
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
        this.okHttpClient = okHttpClient;

        Log.d( TAG, "initialize : exit" );
    }

    public ContentDataStore createMasterBackendDataStore() {
        Log.d( TAG, "createMasterBackendDataStore : enter" );

        LiveStreamInfoEntityJsonMapper liveStreamInfoEntityJsonMapper = new LiveStreamInfoEntityJsonMapper( gson );
        ContentApi api = new ContentApiImpl( this.context, this.sharedPreferences, this.okHttpClient, liveStreamInfoEntityJsonMapper );

        Log.d( TAG, "createMasterBackendDataStore : exit" );
        return new MasterBackendContentDataStore( api );
    }

}
