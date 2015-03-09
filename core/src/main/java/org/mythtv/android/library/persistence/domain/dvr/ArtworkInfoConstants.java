package org.mythtv.android.library.persistence.domain.dvr;

import android.net.Uri;

import org.mythtv.android.library.persistence.domain.AbstractBaseDatabase;
import org.mythtv.android.library.persistence.repository.MythtvProvider;

/**
 * Created by dmfrey on 1/25/15.
 */
public class ArtworkInfoConstants extends AbstractBaseDatabase {

    public static final String TABLE_NAME = "artwork_infos";

    public static final Uri CONTENT_URI = Uri.parse( "content://" + MythtvProvider.AUTHORITY + "/" + TABLE_NAME );
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final int ALL		    			= 240;
    public static final int SINGLE    				= 241;

    public static final String CREATE_TABLE, DROP_TABLE;
    public static final String INSERT_ROW, UPDATE_ROW, DELETE_ROW;

    public static final String FIELD_URL = "url";
    public static final String FIELD_URL_DATA_TYPE = "TEXT";

    public static final String FIELD_FILENAME = "filename";
    public static final String FIELD_FILENAME_DATA_TYPE = "TEXT";

    public static final String FIELD_STORAGE_GROUP = "storage_group";
    public static final String FIELD_STORAGE_GROUP_DATA_TYPE = "TEXT";

    public static final String FIELD_TYPE = "type";
    public static final String FIELD_TYPE_DATA_TYPE = "TEXT";

    public static final String FIELD_CHAN_ID = "chan_id";
    public static final String FIELD_CHAN_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_START_TS = "start_ts";
    public static final String FIELD_START_TS_DATA_TYPE = "INTEGER";


    static {

        StringBuilder createTable = new StringBuilder();

        createTable.append( "CREATE TABLE " + TABLE_NAME + " (" );
        createTable.append( _ID ).append( " " ).append( FIELD_ID_DATA_TYPE ).append( " " ).append( FIELD_ID_PRIMARY_KEY_AUTOINCREMENT ).append(", ");
        createTable.append( FIELD_URL ).append( " " ).append( FIELD_URL_DATA_TYPE ).append(", ");
        createTable.append( FIELD_FILENAME ).append( " " ).append( FIELD_FILENAME_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_STORAGE_GROUP ).append(" ").append( FIELD_STORAGE_GROUP_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_TYPE ).append( " " ).append( FIELD_TYPE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHAN_ID ).append(" ").append( FIELD_CHAN_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_START_TS ).append( " " ).append( FIELD_START_TS_DATA_TYPE ).append( ", " );
        createTable.append( ");" );

        CREATE_TABLE = createTable.toString();

        StringBuilder dropTable = new StringBuilder();

        dropTable.append( "DROP TABLE IF EXISTS " ).append( TABLE_NAME );

        DROP_TABLE = dropTable.toString();

        StringBuilder insert = new StringBuilder();

        insert.append( "INSERT INTO " ).append( TABLE_NAME ).append( " ( " );
        insert.append( FIELD_URL ).append( "," );
        insert.append( FIELD_FILENAME ).append( "," );
        insert.append( FIELD_STORAGE_GROUP ).append( "," );
        insert.append( FIELD_TYPE ).append( ", " );
        insert.append( FIELD_CHAN_ID ).append( "," );
        insert.append( FIELD_START_TS );
        insert.append( " ) " );
        insert.append( "VALUES( ?,?,?,?,?,? )" );

        INSERT_ROW = insert.toString();

        StringBuilder update = new StringBuilder();

        update.append( "UPDATE " ).append( TABLE_NAME ).append( " SET " );
        update.append( FIELD_URL ).append( "=?, " );
        update.append( FIELD_FILENAME ).append( "=?, " );
        update.append( FIELD_STORAGE_GROUP ).append( "=?, " );
        update.append( FIELD_TYPE ).append( "=?, " );
        update.append( FIELD_CHAN_ID ).append( "=?, " );
        update.append( FIELD_START_TS ).append( "=? " );
        update.append( "WHERE " ).append( _ID ).append( " = ? " );

        UPDATE_ROW = update.toString();

        StringBuilder delete = new StringBuilder();

        delete.append( "DELETE FROM " ).append( TABLE_NAME ).append( " " );
        delete.append( "WHERE " ).append( _ID ).append( " = ?" );

        DELETE_ROW = delete.toString();

    }

}
