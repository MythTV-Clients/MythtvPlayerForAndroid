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

import org.mythtv.android.data.net.ContentApi;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    private final ContentApi contentApi;

    @Inject
    public ContentDataStoreFactory( final ContentApi contentApi ) {
        Log.d( TAG, "initialize : enter" );

        if( null == contentApi  ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.contentApi = contentApi;

        Log.d( TAG, "initialize : exit" );
    }

    public ContentDataStore createMasterBackendDataStore() {
        Log.d( TAG, "createMasterBackendDataStore : enter" );

        Log.d( TAG, "createMasterBackendDataStore : exit" );
        return new MasterBackendContentDataStore( this.contentApi );
    }

}
