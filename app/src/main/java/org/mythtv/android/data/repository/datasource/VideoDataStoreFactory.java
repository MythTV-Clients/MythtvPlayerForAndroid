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

import org.mythtv.android.data.net.VideoApi;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/3/15.
 */
@Singleton
public class VideoDataStoreFactory {

    private static final String TAG = VideoDataStoreFactory.class.getSimpleName();

    private final VideoApi api;

    @Inject
    public VideoDataStoreFactory( final VideoApi api ) {
        Log.d( TAG, "initialize : enter" );

        if( null == api ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.api = api;

        Log.d( TAG, "initialize : exit" );
    }

    public VideoDataStore createMasterBackendDataStore() {
        Log.d( TAG, "createMasterBackendDataStore : enter" );

        Log.d( TAG, "createMasterBackendDataStore : exit" );
        return new MasterBackendVideoDataStore( this.api );
    }

}
