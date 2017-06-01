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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.mythtv.android.data.entity.MediaItemEntity;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 10/8/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mythtvdb";
    private static final int DATABASE_VERSION = 30;

    public DatabaseHelper( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onOpen( SQLiteDatabase db ) {
        super.onOpen( db );

        if( !db.isReadOnly() ) {

            db.execSQL( "PRAGMA encoding = \"UTF-8\";" );

        }

    }

    @Override
    public void onCreate( SQLiteDatabase db ) {

        dropTables( db );

        createMediaItemTable( db );

    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {

        if( oldVersion < DATABASE_VERSION ) {

            onCreate( db );

        }

    }

    private void dropTables( SQLiteDatabase db ) {

        db.execSQL( "DROP TABLE IF EXISTS search_result;" );

        db.execSQL( MediaItemEntity.DROP_TABLE );

    }

    private void createMediaItemTable( SQLiteDatabase db) {

        db.execSQL( MediaItemEntity.CREATE_TABLE );

    }

}
