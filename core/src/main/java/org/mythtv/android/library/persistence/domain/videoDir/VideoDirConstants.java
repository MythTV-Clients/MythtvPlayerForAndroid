package org.mythtv.android.library.persistence.domain.videoDir;

import android.net.Uri;

import org.mythtv.android.library.persistence.domain.AbstractBaseDatabase;
import org.mythtv.android.library.persistence.repository.MythtvProvider;

/**
 * Created by dmfrey on 7/25/15.
 */
public class VideoDirConstants extends AbstractBaseDatabase {

    public static final String TABLE_NAME = "video_dirs";

    public static final Uri CONTENT_URI = Uri.parse( "content://" + MythtvProvider.AUTHORITY + "/" + TABLE_NAME );
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final int ALL		    			= 400;
    public static final int SINGLE    				= 401;

    public static final String CREATE_TABLE, DROP_TABLE;

    public static final String FIELD_VIDEO_DIR_PATH = "path";
    public static final String FIELD_VIDEO_DIR_PATH_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_DIR_NAME = "name";
    public static final String FIELD_VIDEO_DIR_NAME_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_DIR_PARENT = "parent";
    public static final String FIELD_VIDEO_DIR_PARENT_DATA_TYPE = "TEXT";


    static {

        StringBuilder createTable = new StringBuilder();

        createTable.append( "CREATE TABLE " + TABLE_NAME + " (" );
        createTable.append( _ID ).append( " " ).append( FIELD_ID_DATA_TYPE ).append( " " ).append( FIELD_ID_PRIMARY_KEY_AUTOINCREMENT ).append( ", " );
        createTable.append( FIELD_VIDEO_DIR_PATH ).append( " " ).append( FIELD_VIDEO_DIR_PATH_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_DIR_NAME ).append( " " ).append( FIELD_VIDEO_DIR_NAME_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_DIR_PARENT ).append( " " ).append( FIELD_VIDEO_DIR_PARENT_DATA_TYPE ).append( " " );
        createTable.append( ");" );

        CREATE_TABLE = createTable.toString();

        StringBuilder dropTable = new StringBuilder();

        dropTable.append( "DROP TABLE IF EXISTS " ).append( TABLE_NAME );

        DROP_TABLE = dropTable.toString();

    }

}
