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
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.mythtv.android.data.repository.DatabaseHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 10/10/15.
 */
@Singleton
public class SearchDataStoreFactory {

    private static final String TAG = SearchDataStoreFactory.class.getSimpleName();

    private DatabaseHelper mOpenHelper;

    @Inject
    public SearchDataStoreFactory( Context context ) {
        Log.d( TAG, "initialize : enter" );

        if( null == context ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.mOpenHelper = new DatabaseHelper( context.getApplicationContext() );

        Log.d( TAG, "initialize : exit" );
    }

    public SearchDataStore createReadSearchDataStore() {
        Log.d( TAG, "createReadSearchDataStore : enter" );

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        SearchDataStore searchDataStore = new DbSearchDataStore( db );

        Log.d( TAG, "createReadSearchDataStore : exit" );
        return  searchDataStore;
    }

    public SearchDataStore createWriteSearchDataStore() {
        Log.d( TAG, "createWriteSearchDataStore : enter" );

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        SearchDataStore searchDataStore = new DbSearchDataStore( db );

        Log.d( TAG, "createWriteSearchDataStore : exit" );
        return  searchDataStore;
    }

}
