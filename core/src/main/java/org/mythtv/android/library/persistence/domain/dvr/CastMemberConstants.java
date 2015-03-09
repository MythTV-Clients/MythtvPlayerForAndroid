package org.mythtv.android.library.persistence.domain.dvr;

import android.net.Uri;

import org.mythtv.android.library.persistence.domain.AbstractBaseDatabase;
import org.mythtv.android.library.persistence.repository.MythtvProvider;

/**
 * Created by dmfrey on 1/25/15.
 */
public class CastMemberConstants extends AbstractBaseDatabase {

    public static final String TABLE_NAME = "cast_members";

    public static final Uri CONTENT_URI = Uri.parse( "content://" + MythtvProvider.AUTHORITY + "/" + TABLE_NAME );
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final int ALL		    			= 250;
    public static final int SINGLE    				= 251;

    public static final String CREATE_TABLE, DROP_TABLE;
    public static final String INSERT_ROW, UPDATE_ROW, DELETE_ROW;

    public static final String FIELD_NAME = "name";
    public static final String FIELD_NAME_DATA_TYPE = "TEXT";

    public static final String FIELD_CHARACTER_NAME = "character_name";
    public static final String FIELD_CHARACTER_NAME_DATA_TYPE = "TEXT";

    public static final String FIELD_ROLE = "role";
    public static final String FIELD_ROLE_DATA_TYPE = "TEXT";

    public static final String FIELD_TRANSLATED_ROLE = "translated_role";
    public static final String FIELD_TRANSLATED_ROLE_DATA_TYPE = "TEXT";

    public static final String FIELD_CHAN_ID = "chan_id";
    public static final String FIELD_CHAN_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_START_TS = "start_ts";
    public static final String FIELD_START_TS_DATA_TYPE = "INTEGER";


    static {

        StringBuilder createTable = new StringBuilder();

        createTable.append( "CREATE TABLE " + TABLE_NAME + " (" );
        createTable.append( _ID ).append( " " ).append( FIELD_ID_DATA_TYPE ).append( " " ).append( FIELD_ID_PRIMARY_KEY_AUTOINCREMENT ).append(", ");
        createTable.append( FIELD_NAME ).append( " " ).append( FIELD_NAME_DATA_TYPE ).append(", ");
        createTable.append( FIELD_CHARACTER_NAME ).append( " " ).append( FIELD_CHARACTER_NAME_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_ROLE ).append(" ").append( FIELD_ROLE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_TRANSLATED_ROLE ).append( " " ).append( FIELD_TRANSLATED_ROLE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHAN_ID ).append(" ").append( FIELD_CHAN_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_START_TS ).append( " " ).append( FIELD_START_TS_DATA_TYPE ).append( ", " );
        createTable.append( ");" );

        CREATE_TABLE = createTable.toString();

        StringBuilder dropTable = new StringBuilder();

        dropTable.append( "DROP TABLE IF EXISTS " ).append( TABLE_NAME );

        DROP_TABLE = dropTable.toString();

        StringBuilder insert = new StringBuilder();

        insert.append( "INSERT INTO " ).append( TABLE_NAME ).append( " ( " );
        insert.append( FIELD_NAME ).append( "," );
        insert.append( FIELD_CHARACTER_NAME ).append( "," );
        insert.append( FIELD_ROLE ).append(",");
        insert.append( FIELD_TRANSLATED_ROLE ).append(",");
        insert.append( FIELD_CHAN_ID ).append( "," );
        insert.append( FIELD_START_TS );
        insert.append( " ) " );
        insert.append( "VALUES( ?,?,?,?,?,? )" );

        INSERT_ROW = insert.toString();

        StringBuilder update = new StringBuilder();

        update.append( "UPDATE " ).append( TABLE_NAME ).append( " SET " );
        update.append( FIELD_NAME ).append( "=?, " );
        update.append( FIELD_CHARACTER_NAME ).append( "=?, " );
        update.append( FIELD_ROLE ).append( "=?, " );
        update.append( FIELD_TRANSLATED_ROLE ).append( "=?, " );
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
