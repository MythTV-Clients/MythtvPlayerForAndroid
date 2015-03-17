package org.mythtv.android.library.persistence.domain.dvr;

import android.net.Uri;

import org.mythtv.android.library.persistence.domain.AbstractBaseDatabase;
import org.mythtv.android.library.persistence.repository.MythtvProvider;

/**
 * Created by dmfrey on 1/25/15.
 */
public class ProgramFtsConstants extends AbstractBaseDatabase {

    public static final String TABLE_NAME = "programs_fts";

    public static final Uri CONTENT_URI = Uri.parse( "content://" + MythtvProvider.AUTHORITY + "/" + TABLE_NAME );
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final int ALL		    			= 310;
    public static final int SINGLE    				= 311;

    public static final String CREATE_TABLE, DROP_TABLE;

    public static final String SELECTION =
            ProgramConstants.FIELD_PROGRAM_TITLE + " MATCH ?";


    static {

        StringBuilder createTable = new StringBuilder();

        createTable.append( "CREATE VIRTUAL TABLE " ).append( TABLE_NAME ).append( " using fts3 (" );
        createTable.append( _ID ).append( " " ).append( FIELD_ID_DATA_TYPE ).append( " " ).append( FIELD_ID_PRIMARY_KEY ).append(", ");
        createTable.append( ProgramConstants.FIELD_PROGRAM_TYPE ).append( " " ).append( ProgramConstants.FIELD_PROGRAM_TYPE_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_PROGRAM_START_TIME ).append( " " ).append( ProgramConstants.FIELD_PROGRAM_START_TIME_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_PROGRAM_END_TIME ).append( " " ).append( ProgramConstants.FIELD_PROGRAM_END_TIME_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_PROGRAM_TITLE_SORT ).append( " " ).append( ProgramConstants.FIELD_PROGRAM_TITLE_SORT_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_PROGRAM_TITLE ).append( " " ).append( ProgramConstants.FIELD_PROGRAM_TITLE_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_PROGRAM_SUB_TITLE ).append( " " ).append( ProgramConstants.FIELD_PROGRAM_SUB_TITLE_DATA_TYPE ).append(", ");
        createTable.append( ProgramConstants.FIELD_PROGRAM_CATEGORY ).append( " " ).append( ProgramConstants.FIELD_PROGRAM_CATEGORY_DATA_TYPE ).append(", ");
        createTable.append( ProgramConstants.FIELD_PROGRAM_FILE_NAME ).append( " " ).append( ProgramConstants.FIELD_PROGRAM_FILE_NAME_DATA_TYPE ).append(", ");
        createTable.append( ProgramConstants.FIELD_PROGRAM_HOSTNAME ).append(" ").append( ProgramConstants.FIELD_PROGRAM_HOSTNAME_DATA_TYPE ).append(", ");
        createTable.append( ProgramConstants.FIELD_PROGRAM_DESCRIPTION ).append( " " ).append( ProgramConstants.FIELD_PROGRAM_DESCRIPTION_DATA_TYPE ).append(", ");
        createTable.append( ProgramConstants.FIELD_PROGRAM_INETREF ).append( " " ).append( ProgramConstants.FIELD_PROGRAM_INETREF_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_CHANNEL_CHAN_ID ).append(" ").append( ProgramConstants.FIELD_CHANNEL_CHAN_ID_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_CHANNEL_CHAN_NUM ).append( " " ).append( ProgramConstants.FIELD_CHANNEL_CHAN_NUM_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_CHANNEL_CALLSIGN ).append( " " ).append( ProgramConstants.FIELD_CHANNEL_CALLSIGN_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_CHANNEL_CHANNEL_NAME ).append( " " ).append( ProgramConstants.FIELD_CHANNEL_CHANNEL_NAME_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_RECORDING_RECORDED_ID ).append( " " ).append( ProgramConstants.FIELD_RECORDING_RECORDED_ID_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_RECORDING_STATUS ).append(" ").append( ProgramConstants.FIELD_RECORDING_STATUS_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_RECORDING_PRIORITY ).append( " " ).append( ProgramConstants.FIELD_RECORDING_PRIORITY_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_RECORDING_START_TS ).append( " " ).append( ProgramConstants.FIELD_RECORDING_START_TS_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_RECORDING_END_TS ).append( " " ).append( ProgramConstants.FIELD_RECORDING_END_TS_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_RECORDING_RECORD_ID ).append( " " ).append( ProgramConstants.FIELD_RECORDING_RECORD_ID_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_RECORDING_REC_GROUP ).append( " " ).append( ProgramConstants.FIELD_RECORDING_REC_GROUP_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_RECORDING_PLAY_GROUP ).append( " " ).append( ProgramConstants.FIELD_RECORDING_PLAY_GROUP_DATA_TYPE ).append( ", " );
        createTable.append( ProgramConstants.FIELD_RECORDING_STORAGE_GROUP ).append( " " ).append( ProgramConstants.FIELD_RECORDING_STORAGE_GROUP_DATA_TYPE ).append( ", " );
        createTable.append( CastMemberConstants.FIELD_NAME ).append( " " ).append( CastMemberConstants.FIELD_NAME_DATA_TYPE ).append( ", " );
        createTable.append( CastMemberConstants.FIELD_CHARACTER_NAME ).append( " " ).append( CastMemberConstants.FIELD_CHARACTER_NAME_DATA_TYPE );
        createTable.append( ")" );

        CREATE_TABLE = createTable.toString();

        StringBuilder dropTable = new StringBuilder();

        dropTable.append( "DROP TABLE IF EXISTS " ).append( TABLE_NAME );

        DROP_TABLE = dropTable.toString();

    }

}
