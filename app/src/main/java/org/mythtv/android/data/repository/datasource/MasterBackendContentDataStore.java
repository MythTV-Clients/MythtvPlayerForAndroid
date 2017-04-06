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

import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.net.ContentApi;
import org.mythtv.android.domain.Media;

import java.util.List;

import rx.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/27/15.
 */
public class MasterBackendContentDataStore implements ContentDataStore {

    private static final String TAG = MasterBackendContentDataStore.class.getSimpleName();

    private final ContentApi api;

    public MasterBackendContentDataStore( ContentApi api ) {

        this.api = api;

    }

    @Override
    public Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntityList( final String filename ) {
        Log.d( TAG, "liveStreamInfoEntityList : enter" );

        return this.api.liveStreamInfoEntityList( filename );
    }

    @Override
    public Observable<LiveStreamInfoEntity> addLiveStream( final int id, final Media media ) {
        Log.d( TAG, "addLiveStream : enter" );

        return this.api.addLiveStream( id, media );
    }

    @Override
    public Observable<Boolean> removeLiveStream( final int id ) {
        Log.d( TAG, "removeLiveStream : enter" );

        return this.api.removeLiveStream( id );
    }

}
