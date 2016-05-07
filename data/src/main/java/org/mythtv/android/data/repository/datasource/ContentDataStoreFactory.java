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
import android.util.Log;

import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityJsonMapper;
import org.mythtv.android.data.net.ContentApi;
import org.mythtv.android.data.net.ContentApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class ContentDataStoreFactory {

    private static final String TAG = ContentDataStoreFactory.class.getSimpleName();

    private final Context context;

    @Inject
    public ContentDataStoreFactory( Context context ) {
        Log.d( TAG, "initialize : enter" );

        if( null == context ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.context = context.getApplicationContext();

        Log.d( TAG, "initialize : exit" );
    }

    public ContentDataStore create() {
        Log.d( TAG, "create : enter" );

        Log.d( TAG, "create : exit" );
        return createMasterBackendDataStore();
    }

    public ContentDataStore createMasterBackendDataStore() {
        Log.d( TAG, "createMasterBackendDataStore : enter" );

        LiveStreamInfoEntityJsonMapper liveStreamInfoEntityJsonMapper = new LiveStreamInfoEntityJsonMapper();
        BooleanJsonMapper booleanJsonMapper = new BooleanJsonMapper();
        ContentApi api = new ContentApiImpl( this.context, liveStreamInfoEntityJsonMapper, booleanJsonMapper );

        Log.d( TAG, "createMasterBackendDataStore : exit" );
        return new MasterBackendContentDataStore( api );
    }

}
