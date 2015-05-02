/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.library.persistence.domain.dvr;

import android.net.Uri;

import org.mythtv.android.library.persistence.domain.AbstractBaseDatabase;
import org.mythtv.android.library.persistence.repository.MythtvProvider;

/**
 * Created by dmfrey on 1/25/15.
 */
public class RecordingInfoConstants extends AbstractBaseDatabase {

    public static final String TABLE_NAME = "recording_infos";

    public static final Uri CONTENT_URI = Uri.parse( "content://" + MythtvProvider.AUTHORITY + "/" + TABLE_NAME );
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final int ALL		    			= 220;
    public static final int SINGLE    				= 221;

    public static final String CREATE_TABLE, DROP_TABLE;
    public static final String INSERT_ROW, UPDATE_ROW, DELETE_ROW;

    public static final String FIELD_TITLE = "title";
    public static final String FIELD_TITLE_DATA_TYPE = "TEXT";

    public static final String FIELD_INETREF = "inetref";
    public static final String FIELD_INETREF_DATA_TYPE = "TEXT";

    public static final String FIELD_TITLE_SORT = "title_sort";
    public static final String FIELD_TITLE_SORT_DATA_TYPE = "TEXT";

    public static final String FIELD_SORT = "sort";
    public static final String FIELD_SORT_DATA_TYPE = "INTEGER";


    static {

        StringBuilder createTable = new StringBuilder();

        createTable.append( "CREATE TABLE " + TABLE_NAME + " (" );
        createTable.append( _ID ).append( " " ).append( FIELD_ID_DATA_TYPE ).append( " " ).append( FIELD_ID_PRIMARY_KEY_AUTOINCREMENT ).append(", ");
        createTable.append(FIELD_TITLE).append( " " ).append( FIELD_TITLE_DATA_TYPE ).append(", ");
        createTable.append(FIELD_INETREF).append( " " ).append( FIELD_INETREF_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_TITLE_SORT ).append(" ").append( FIELD_TITLE_SORT_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_SORT ).append( " " ).append( FIELD_SORT_DATA_TYPE );
        createTable.append( ");" );

        CREATE_TABLE = createTable.toString();

        StringBuilder dropTable = new StringBuilder();

        dropTable.append( "DROP TABLE IF EXISTS " ).append( TABLE_NAME );

        DROP_TABLE = dropTable.toString();

        StringBuilder insert = new StringBuilder();

        insert.append( "INSERT INTO " ).append( TABLE_NAME ).append( " ( " );
        insert.append(FIELD_TITLE).append( "," );
        insert.append(FIELD_INETREF).append( "," );
        insert.append( FIELD_TITLE_SORT ).append(",");
        insert.append( FIELD_SORT );
        insert.append( " ) " );
        insert.append( "VALUES( ?,?,?,? )" );

        INSERT_ROW = insert.toString();

        StringBuilder update = new StringBuilder();

        update.append( "UPDATE " ).append( TABLE_NAME ).append( " SET " );
        update.append(FIELD_TITLE).append( "=?, " );
        update.append(FIELD_INETREF).append( "=?, " );
        update.append( FIELD_TITLE_SORT ).append( "=?, " );
        update.append( FIELD_SORT ).append( "=? " );
        update.append( "WHERE " ).append( _ID ).append( " = ? " );

        UPDATE_ROW = update.toString();

        StringBuilder delete = new StringBuilder();

        delete.append( "DELETE FROM " ).append( TABLE_NAME ).append( " " );
        delete.append( "WHERE " ).append( _ID ).append( " = ?" );

        DELETE_ROW = delete.toString();

    }

}
