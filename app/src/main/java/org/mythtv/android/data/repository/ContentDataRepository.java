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

package org.mythtv.android.data.repository;

import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityDataMapper;
import org.mythtv.android.data.repository.datasource.ContentDataStore;
import org.mythtv.android.data.repository.datasource.ContentDataStoreFactory;
import org.mythtv.android.domain.LiveStreamInfo;
import org.mythtv.android.domain.repository.ContentRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class ContentDataRepository implements ContentRepository {

    private static final String TAG = ContentDataRepository.class.getSimpleName();

    private final ContentDataStoreFactory contentDataStoreFactory;

    @Inject
    public ContentDataRepository( ContentDataStoreFactory contentDataStoreFactory ) {

        this.contentDataStoreFactory = contentDataStoreFactory;

    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<LiveStreamInfo> addliveStream( String storageGroup, String filename, String hostname ) {
        Log.d( TAG, "addliveStreamInfos : enter" );

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        return contentDataStore.addliveStream( storageGroup, filename, hostname )
                .map( liveStreamInfoEntity -> LiveStreamInfoEntityDataMapper.transform( liveStreamInfoEntity ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<LiveStreamInfo> addRecordingliveStream( int recordedId, int chanId, DateTime startTime ) {
        Log.d(TAG, "addRecordingliveStream : enter");

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        return contentDataStore.addRecordingliveStream( recordedId, chanId, startTime )
                .map( liveStreamInfoEntity -> LiveStreamInfoEntityDataMapper.transform( liveStreamInfoEntity ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<LiveStreamInfo> addVideoliveStream( int id ) {
        Log.d(TAG, "addVideoliveStream : enter");

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        return contentDataStore.addVideoliveStream( id )
                .map( liveStreamInfoEntity -> LiveStreamInfoEntityDataMapper.transform( liveStreamInfoEntity ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<List<LiveStreamInfo>> liveStreamInfos( String filename ) {
        Log.d( TAG, "liveStreamInfos : enter" );

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.createMasterBackendDataStore();

        return contentDataStore.liveStreamInfoEntityList( filename )
                .map( model -> LiveStreamInfoEntityDataMapper.transform( model ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<LiveStreamInfo> liveStreamInfo( int id ) {
        Log.d( TAG, "liveStreamInfo : enter" );
        Log.d( TAG, "liveStreamInfo : id=" + id );

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.create();

        return contentDataStore.liveStreamInfoEntityDetails( id)
                .map(liveStreamInfoEntity -> LiveStreamInfoEntityDataMapper.transform( liveStreamInfoEntity ) );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<Boolean> removeLiveStream( int id ) {
        Log.d( TAG, "removeStreamInfo : enter" );
        Log.d( TAG, "removeStreamInfo : id=" + id );

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.create();

        return contentDataStore.removeLiveStream( id );
    }

    @SuppressWarnings( "Convert2MethodRef" )
    @Override
    public Observable<Boolean> stopLiveStream( int id ) {
        Log.d( TAG, "stopStreamInfo : enter" );
        Log.d( TAG, "stopStreamInfo : id=" + id );

        final ContentDataStore contentDataStore = this.contentDataStoreFactory.create();

        return contentDataStore.stopLiveStream( id );
    }

}
