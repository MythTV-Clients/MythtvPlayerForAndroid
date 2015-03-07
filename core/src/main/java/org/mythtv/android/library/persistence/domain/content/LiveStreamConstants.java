package org.mythtv.android.library.persistence.domain.content;

import android.net.Uri;

import org.mythtv.android.library.persistence.domain.AbstractBaseDatabase;
import org.mythtv.android.library.persistence.repository.MythtvProvider;

/**
 * Created by dmfrey on 1/25/15.
 */
public class LiveStreamConstants extends AbstractBaseDatabase {

    public static final String TABLE_NAME = "live_streams";

    public static final Uri CONTENT_URI = Uri.parse( "content://" + MythtvProvider.AUTHORITY + "/" + TABLE_NAME );
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final int ALL		    			= 100;
    public static final int SINGLE    				= 101;
    public static final int ALL_RECORDINGS 			= 102;
    public static final int SINGLE_RECORDING		= 103;
    public static final int ALL_VIDEOS    			= 104;
    public static final int SINGLE_VIDEO			= 105;

    public static final String CREATE_TABLE, DROP_TABLE;
    public static final String INSERT_ROW, UPDATE_ROW, DELETE_ROW;


    public static final String FIELD_LIVE_STREAM_ID = "live_stream_id";
    public static final String FIELD_LIVE_STREAM_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_WIDTH = "width";
    public static final String FIELD_WIDTH_DATA_TYPE = "INTEGER";

    public static final String FIELD_HEIGHT = "height";
    public static final String FIELD_HEIGHT_DATA_TYPE = "INTEGER";

    public static final String FIELD_BITRATE = "bitrate";
    public static final String FIELD_BITRATE_DATA_TYPE = "INTEGER";

    public static final String FIELD_AUDIO_BITRATE = "audio_bitrate";
    public static final String FIELD_AUDIO_BITRATE_DATA_TYPE = "INTEGER";

    public static final String FIELD_SEGMENT_SIZE = "segment_size";
    public static final String FIELD_SEGMENT_SIZE_DATA_TYPE = "INTEGER";

    public static final String FIELD_MAX_SEGMENTS = "max_segments";
    public static final String FIELD_MAX_SEGMENTS_DATA_TYPE = "INTEGER";

    public static final String FIELD_START_SEGMENT = "start_segment";
    public static final String FIELD_START_SEGMENT_DATA_TYPE = "INTEGER";

    public static final String FIELD_CURRENT_SEGMENT = "current_segment";
    public static final String FIELD_CURRENT_SEGMENT_DATA_TYPE = "INTEGER";

    public static final String FIELD_SEGMENT_COUNT = "segment_count";
    public static final String FIELD_SEGMENT_COUNT_DATA_TYPE = "INTEGER";

    public static final String FIELD_PERCENT_COMPLETE = "percent_complete";
    public static final String FIELD_PERCENT_COMPLETE_DATA_TYPE = "INTEGER";

    public static final String FIELD_RELATIVE_URL = "relative_url";
    public static final String FIELD_RELATIVE_URL_DATA_TYPE = "TEXT";

    public static final String FIELD_FULL_URL = "full_url";
    public static final String FIELD_FULL_URL_DATA_TYPE = "TEXT";

    public static final String FIELD_STATUS_STR = "status_str";
    public static final String FIELD_STATUS_STR_DATA_TYPE = "TEXT";

    public static final String FIELD_STATUS_INT = "status_int";
    public static final String FIELD_STATUS_INT_DATA_TYPE = "INTEGER";

    public static final String FIELD_STATUS_MESSAGE = "status_message";
    public static final String FIELD_STATUS_MESSAGE_DATA_TYPE = "TEXT";

    public static final String FIELD_SOURCE_FILE = "source_file";
    public static final String FIELD_SOURCE_FILE_DATA_TYPE = "TEXT";

    public static final String FIELD_SOURCE_HOST = "source_host";
    public static final String FIELD_SOURCE_HOST_DATA_TYPE = "TEXT";

    public static final String FIELD_SOURCE_WIDTH = "source_width";
    public static final String FIELD_SOURCE_WIDTH_DATA_TYPE = "INTEGER";

    public static final String FIELD_SOURCE_HEIGHT = "source_height";
    public static final String FIELD_SOURCE_HEIGHT_DATA_TYPE = "INTEGER";

    public static final String FIELD_AUDIO_ONLY_BITRATE = "audio_only_bitrate";
    public static final String FIELD_AUDIO_ONLY_BITRATE_DATA_TYPE = "INTEGER";


    // For recordings v0.28+
    public static final String FIELD_RECORDED_ID = "recorded id";
    public static final String FIELD_RECORDED_ID_DATA_TYPE = "INTEGER";

    // For recordings v0.27-
    public static final String FIELD_CHAN_ID = "chan_id";
    public static final String FIELD_CHAN_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_START_TIME = "start_time";
    public static final String FIELD_START_TIME_DATA_TYPE = "INTEGER";


    // For videos
    public static final String FIELD_VIDEO_ID = "video_id";
    public static final String FIELD_VIDEO_ID_DATA_TYPE = "INTEGER";

    static {

        StringBuilder createTable = new StringBuilder();

        createTable.append( "CREATE TABLE " + TABLE_NAME + " (" );
        createTable.append( _ID ).append( " " ).append( FIELD_ID_DATA_TYPE ).append( " " ).append( FIELD_ID_PRIMARY_KEY_AUTOINCREMENT ).append( ", " );
        createTable.append( FIELD_LIVE_STREAM_ID ).append( " " ).append( FIELD_LIVE_STREAM_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_WIDTH ).append( " " ).append( FIELD_WIDTH_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_HEIGHT ).append( " " ).append( FIELD_HEIGHT_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_BITRATE ).append( " " ).append( FIELD_BITRATE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_AUDIO_BITRATE ).append( " " ).append( FIELD_AUDIO_BITRATE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_SEGMENT_SIZE ).append( " " ).append( FIELD_SEGMENT_SIZE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_MAX_SEGMENTS ).append( " " ).append( FIELD_MAX_SEGMENTS_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_START_SEGMENT ).append( " " ).append( FIELD_START_SEGMENT_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CURRENT_SEGMENT ).append( " " ).append( FIELD_CURRENT_SEGMENT_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_SEGMENT_COUNT ).append( " " ).append( FIELD_SEGMENT_COUNT_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_PERCENT_COMPLETE ).append( " " ).append( FIELD_PERCENT_COMPLETE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RELATIVE_URL ).append( " " ).append( FIELD_RELATIVE_URL_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_FULL_URL ).append( " " ).append( FIELD_FULL_URL_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_STATUS_STR ).append( " " ).append( FIELD_STATUS_STR_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_STATUS_INT ).append( " " ).append( FIELD_STATUS_INT_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_STATUS_MESSAGE ).append( " " ).append( FIELD_STATUS_MESSAGE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_SOURCE_FILE ).append( " " ).append( FIELD_SOURCE_FILE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_SOURCE_HOST ).append( " " ).append( FIELD_SOURCE_HOST_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_SOURCE_WIDTH ).append( " " ).append( FIELD_SOURCE_WIDTH_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_SOURCE_HEIGHT ).append( " " ).append( FIELD_SOURCE_HEIGHT_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_AUDIO_ONLY_BITRATE ).append( " " ).append( FIELD_AUDIO_ONLY_BITRATE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CREATED_DATE ).append( " " ).append( FIELD_CREATED_DATE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_LAST_MODIFIED_DATE ).append( " " ).append( FIELD_LAST_MODIFIED_DATE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDED_ID ).append( " " ).append( FIELD_RECORDED_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHAN_ID ).append( " " ).append( FIELD_CHAN_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_START_TIME ).append( " " ).append( FIELD_START_TIME_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_ID ).append( " " ).append( FIELD_VIDEO_ID_DATA_TYPE );
        createTable.append( ");" );

        CREATE_TABLE = createTable.toString();

        StringBuilder dropTable = new StringBuilder();

        dropTable.append( "DROP TABLE IF EXISTS " ).append( TABLE_NAME );

        DROP_TABLE = dropTable.toString();

        StringBuilder insert = new StringBuilder();

        insert.append( "INSERT INTO " ).append( TABLE_NAME ).append( " ( " );
        insert.append( FIELD_LIVE_STREAM_ID ).append( ", " );
        insert.append( FIELD_WIDTH ).append( ", " );
        insert.append( FIELD_HEIGHT ).append( ", " );
        insert.append( FIELD_BITRATE ).append( ", " );
        insert.append( FIELD_AUDIO_BITRATE ).append( ", " );
        insert.append( FIELD_SEGMENT_SIZE ).append( ", " );
        insert.append( FIELD_MAX_SEGMENTS ).append( ", " );
        insert.append( FIELD_START_SEGMENT ).append( ", " );
        insert.append( FIELD_CURRENT_SEGMENT ).append( ", " );
        insert.append( FIELD_SEGMENT_COUNT ).append( ", " );
        insert.append( FIELD_PERCENT_COMPLETE ).append( ", " );
        insert.append( FIELD_RELATIVE_URL ).append( ", " );
        insert.append( FIELD_FULL_URL ).append( ", " );
        insert.append( FIELD_STATUS_STR ).append( ", " );
        insert.append( FIELD_STATUS_INT ).append( ", " );
        insert.append( FIELD_STATUS_MESSAGE ).append( ", " );
        insert.append( FIELD_SOURCE_FILE ).append( ", " );
        insert.append( FIELD_SOURCE_HOST ).append( ", " );
        insert.append( FIELD_SOURCE_WIDTH ).append( ", " );
        insert.append( FIELD_SOURCE_HEIGHT ).append( ", " );
        insert.append( FIELD_AUDIO_ONLY_BITRATE ).append( ", " );
        insert.append( FIELD_CREATED_DATE ).append( ", " );
        insert.append( FIELD_LAST_MODIFIED_DATE ).append( ", " );
        insert.append( FIELD_RECORDED_ID ).append( "," );
        insert.append( FIELD_CHAN_ID ).append( ", " );
        insert.append( FIELD_START_TIME ).append( ", " );
        insert.append( FIELD_VIDEO_ID ).append( " " );
        insert.append( " ) " );
        insert.append( "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )" );

        INSERT_ROW = insert.toString();

        StringBuilder update = new StringBuilder();

        update.append( "UPDATE " ).append( TABLE_NAME ).append( " SET " );
        update.append( FIELD_LIVE_STREAM_ID ).append( "=?, " );
        update.append( FIELD_WIDTH ).append( "=?, " );
        update.append( FIELD_HEIGHT ).append( "=?, " );
        update.append( FIELD_BITRATE ).append( "=?, " );
        update.append( FIELD_AUDIO_BITRATE ).append( "=?, " );
        update.append( FIELD_SEGMENT_SIZE ).append( "=?, " );
        update.append( FIELD_MAX_SEGMENTS ).append( "=?, " );
        update.append( FIELD_START_SEGMENT ).append( "=?, " );
        update.append( FIELD_CURRENT_SEGMENT ).append( "=?, " );
        update.append( FIELD_SEGMENT_COUNT ).append( "=?, " );
        update.append( FIELD_PERCENT_COMPLETE ).append( "=?, " );
        update.append( FIELD_RELATIVE_URL ).append( "=?, " );
        update.append( FIELD_FULL_URL ).append( "=?, " );
        update.append( FIELD_STATUS_STR ).append( "=?, " );
        update.append( FIELD_STATUS_INT ).append( "=?, " );
        update.append( FIELD_STATUS_MESSAGE ).append( "=?, " );
        update.append( FIELD_SOURCE_FILE ).append( "=?, " );
        update.append( FIELD_SOURCE_HOST ).append( "=?, " );
        update.append( FIELD_SOURCE_WIDTH ).append( "=?, " );
        update.append( FIELD_SOURCE_HEIGHT ).append( "=?, " );
        update.append( FIELD_AUDIO_ONLY_BITRATE ).append( "=?, " );
        update.append( FIELD_CREATED_DATE ).append( "=?, " );
        update.append( FIELD_LAST_MODIFIED_DATE ).append( "=?, " );
        update.append( FIELD_RECORDED_ID ).append( "=?, " );
        update.append( FIELD_CHAN_ID ).append( "=?, " );
        update.append( FIELD_START_TIME ).append( "=?, " );
        update.append( FIELD_VIDEO_ID ).append( "=? " );
        update.append( "WHERE " ).append( _ID ).append( " = ? " );

        UPDATE_ROW = update.toString();

        StringBuilder delete = new StringBuilder();

        delete.append( "DELETE FROM " ).append( TABLE_NAME ).append( " " );
        delete.append( "WHERE " ).append( _ID ).append( " = ?" );

        DELETE_ROW = delete.toString();

    }

}
