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

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.net.ContentApi;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/27/15.
 */
public class MasterBackendContentDataStore implements ContentDataStore {

    private static final String TAG = MasterBackendContentDataStore.class.getSimpleName();

    private final ContentApi api;

    public MasterBackendContentDataStore( ContentApi api ) {

        this.api = api;

    }

    @Override
    public Observable<LiveStreamInfoEntity> addliveStream( String storageGroup, String filename, String hostname ) {
        Log.d( TAG, "addliveStream : enter" );

        Log.d( TAG, "addliveStream : exit" );
        return this.api.addliveStream( storageGroup, filename, hostname );
    }

    @Override
    public Observable<LiveStreamInfoEntity> addRecordingliveStream( int recordedId, int chanId, DateTime startTime ) {
        Log.d( TAG, "addRecordingliveStream : enter" );

        Log.d( TAG, "addRecordingliveStream : exit" );
        return this.api.addRecordingliveStream( recordedId, chanId, startTime );
    }

    @Override
    public Observable<LiveStreamInfoEntity> addVideoliveStream( int id ) {
        Log.d( TAG, "addVideoliveStream : enter" );

        Log.d( TAG, "addVideoliveStream : exit" );
        return this.api.addVideoliveStream( id );
    }

    @Override
    public Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntityList( String filename ) {
        Log.d( TAG, "liveStreamInfoEntityList : enter" );

        Log.d( TAG, "liveStreamInfoEntityList : exit" );
        return this.api.liveStreamInfoEntityList( filename );
    }

    @Override
    public Observable<LiveStreamInfoEntity> liveStreamInfoEntityDetails( int id ) {
        Log.d( TAG, "liveStreamInfoEntityDetails : enter" );

        Log.d( TAG, "liveStreamInfoEntityDetails : exit" );
        return this.api.liveStreamInfoById( id );
    }

    @Override
    public Observable<Boolean> removeLiveStream( int id ) {
        Log.d( TAG, "removeLiveStream : enter" );

        Log.d( TAG, "removeLiveStream : exit" );
        return this.api.removeLiveStream( id );
    }

    @Override
    public Observable<Boolean> stopLiveStream( int id ) {
        Log.d( TAG, "stopLiveStream : enter" );

        Log.d( TAG, "stopLiveStream : exit" );
        return this.api.stopLiveStream( id );
    }
}
