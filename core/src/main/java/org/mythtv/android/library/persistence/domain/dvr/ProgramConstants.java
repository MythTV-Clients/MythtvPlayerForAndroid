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

/*
 * Created by dmfrey on 1/25/15.
 */
public class ProgramConstants extends AbstractBaseDatabase {

    public static enum ProgramType { RECORDED, UPCOMING }

    public static final String TABLE_NAME = "programs";

    public static final Uri CONTENT_URI = Uri.parse( "content://" + MythtvProvider.AUTHORITY + "/" + TABLE_NAME );
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final int ALL		    			= 210;
    public static final int SINGLE    				= 211;
    public static final int ALL_FTS		    		= 212;
    public static final int ALL_RECORDING_GROUPS	= 250;
    public static final int ALL_TITLES	            = 251;

    public static final String CREATE_TABLE, DROP_TABLE;


    public static final String FIELD_PROGRAM_TYPE = "program_type";             // RECORDED or UPCOMING
    public static final String FIELD_PROGRAM_TYPE_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_START_TIME = "program_start_time";
    public static final String FIELD_PROGRAM_START_TIME_DATA_TYPE = "INTEGER";

    public static final String FIELD_PROGRAM_END_TIME = "program_end_time";
    public static final String FIELD_PROGRAM_END_TIME_DATA_TYPE = "INTEGER";

    public static final String FIELD_PROGRAM_TITLE_SORT = "program_title_sort";
    public static final String FIELD_PROGRAM_TITLE_SORT_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_TITLE = "program_title";
    public static final String FIELD_PROGRAM_TITLE_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_SUB_TITLE = "program_sub_title";
    public static final String FIELD_PROGRAM_SUB_TITLE_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_CATEGORY = "program_category";
    public static final String FIELD_PROGRAM_CATEGORY_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_CAT_TYPE = "program_cat_type";
    public static final String FIELD_PROGRAM_CAT_TYPE_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_REPEAT = "program_repeat";
    public static final String FIELD_PROGRAM_REPEAT_DATA_TYPE = "INTEGER";

    public static final String FIELD_PROGRAM_VIDEO_PROPS = "program_video_props";
    public static final String FIELD_PROGRAM_VIDEO_PROPS_DATA_TYPE = "INTEGER";

    public static final String FIELD_PROGRAM_AUDIO_PROPS = "program_audio_props";
    public static final String FIELD_PROGRAM_AUDIO_PROPS_DATA_TYPE = "INTEGER";

    public static final String FIELD_PROGRAM_SUB_PROPS = "program_sub_props";
    public static final String FIELD_PROGRAM_SUB_PROPS_DATA_TYPE = "INTEGER";

    public static final String FIELD_PROGRAM_SERIES_ID = "program_series_id";
    public static final String FIELD_PROGRAM_SERIES_ID_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_PROGRAM_ID = "program_program_id";
    public static final String FIELD_PROGRAM_PROGRAM_ID_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_STARS = "program_stars";
    public static final String FIELD_PROGRAM_STARS_DATA_TYPE = "REAL";

    public static final String FIELD_PROGRAM_FILE_SIZE = "program_file_size";
    public static final String FIELD_PROGRAM_FILE_SIZE_DATA_TYPE = "INTEGER";

    public static final String FIELD_PROGRAM_LAST_MODIFIED = "program_last_modified";
    public static final String FIELD_PROGRAM_LAST_MODIFIED_DATA_TYPE = "INTEGER";

    public static final String FIELD_PROGRAM_PROGRAM_FLAGS = "program_program_flags";
    public static final String FIELD_PROGRAM_PROGRAM_FLAGS_DATA_TYPE = "INTEGER";

    public static final String FIELD_PROGRAM_FILE_NAME = "program_file_name";
    public static final String FIELD_PROGRAM_FILE_NAME_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_HOSTNAME = "program_hostname";
    public static final String FIELD_PROGRAM_HOSTNAME_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_AIR_DATE = "program_air_date";
    public static final String FIELD_PROGRAM_AIR_DATE_DATA_TYPE = "long";

    public static final String FIELD_PROGRAM_DESCRIPTION = "program_description";
    public static final String FIELD_PROGRAM_DESCRIPTION_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_INETREF = "program_inetref";
    public static final String FIELD_PROGRAM_INETREF_DATA_TYPE = "TEXT";

    public static final String FIELD_PROGRAM_SEASON = "program_season";
    public static final String FIELD_PROGRAM_SEASON_DATA_TYPE = "INTEGER";

    public static final String FIELD_PROGRAM_EPISODE = "program_episode";
    public static final String FIELD_PROGRAM_EPISODE_DATA_TYPE = "INTEGER";

    public static final String FIELD_PROGRAM_TOTAL_EPISODES = "program_total_episodes";
    public static final String FIELD_PROGRAM_TOTAL_EPISODES_DATA_TYPE = "INTEGER";


    public static final String FIELD_CHANNEL_CHAN_ID = "channel_chan_id";
    public static final String FIELD_CHANNEL_CHAN_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_CHANNEL_CHAN_NUM = "channel_chan_num";
    public static final String FIELD_CHANNEL_CHAN_NUM_DATA_TYPE = "TEXT";

    public static final String FIELD_CHANNEL_CALLSIGN = "channel_callsign";
    public static final String FIELD_CHANNEL_CALLSIGN_DATA_TYPE = "TEXT";

    public static final String FIELD_CHANNEL_ICON_URL = "channel_icon_url";
    public static final String FIELD_CHANNEL_ICON_URL_DATA_TYPE = "TEXT";

    public static final String FIELD_CHANNEL_CHANNEL_NAME = "channel_channel_name";
    public static final String FIELD_CHANNEL_CHANNEL_NAME_DATA_TYPE = "TEXT";

    public static final String FIELD_CHANNEL_MPLEX_ID = "channel_mplex_id";
    public static final String FIELD_CHANNEL_MPLEX_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_CHANNEL_SERVICE_ID = "channel_service_id";
    public static final String FIELD_CHANNEL_SERVICE_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_CHANNEL_ATSC_MAJOR_CHAN = "channel_atsc_major_chan";
    public static final String FIELD_CHANNEL_ATSC_MAJOR_CHAN_DATA_TYPE = "INTEGER";

    public static final String FIELD_CHANNEL_ATSC_MINOR_CHAN = "channel_atsc_minor_chan";
    public static final String FIELD_CHANNEL_ATSC_MINOR_CHAN_DATA_TYPE = "INTEGER";

    public static final String FIELD_CHANNEL_FORMAT = "channel_format";
    public static final String FIELD_CHANNEL_FORMAT_DATA_TYPE = "TEXT";

    public static final String FIELD_CHANNEL_FREQUENCY_ID = "channel_frequence_id";
    public static final String FIELD_CHANNEL_FREQUENCY_ID_DATA_TYPE = "TEXT";

    public static final String FIELD_CHANNEL_FINE_TUNE = "channel_fine_tune";
    public static final String FIELD_CHANNEL_FINE_TUNE_DATA_TYPE = "INTEGER";

    public static final String FIELD_CHANNEL_CHAN_FILTERS = "channel_chan_filters";
    public static final String FIELD_CHANNEL_CHAN_FILTERS_DATA_TYPE = "TEXT";

    public static final String FIELD_CHANNEL_SOURCE_ID = "channel_source_id";
    public static final String FIELD_CHANNEL_SOURCE_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_CHANNEL_INPUT_ID = "channel_input_id";
    public static final String FIELD_CHANNEL_INPUT_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_CHANNEL_COMM_FREE = "channel_comm_free";
    public static final String FIELD_CHANNEL_COMM_FREE_DATA_TYPE = "INTEGER";

    public static final String FIELD_CHANNEL_USE_EIT = "channel_use_eit";
    public static final String FIELD_CHANNEL_USE_EIT_DATA_TYPE = "INTEGER";

    public static final String FIELD_CHANNEL_VISIBLE = "channel_visible";
    public static final String FIELD_CHANNEL_VISIBLE_DATA_TYPE = "INTEGER";

    public static final String FIELD_CHANNEL_XMLTVID = "channel_xmltvid";
    public static final String FIELD_CHANNEL_XMLTVID_DATA_TYPE = "TEXT";

    public static final String FIELD_CHANNEL_DEFAULT_AUTH = "channel_default_auth";
    public static final String FIELD_CHANNEL_DEFAULT_AUTH_DATA_TYPE = "TEXT";


    public static final String FIELD_RECORDING_RECORDED_ID = "recording_recorded_id";
    public static final String FIELD_RECORDING_RECORDED_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_RECORDING_STATUS = "recording_status";
    public static final String FIELD_RECORDING_STATUS_DATA_TYPE = "INTEGER";

    public static final String FIELD_RECORDING_PRIORITY = "recording_priority";
    public static final String FIELD_RECORDING_PRIORITY_DATA_TYPE = "INTEGER";

    public static final String FIELD_RECORDING_START_TS = "recording_start_ts";
    public static final String FIELD_RECORDING_START_TS_DATA_TYPE = "INTEGER";

    public static final String FIELD_RECORDING_END_TS = "recording_end_ts";
    public static final String FIELD_RECORDING_END_TS_DATA_TYPE = "INTEGER";

    public static final String FIELD_RECORDING_RECORD_ID = "recording_record_id";
    public static final String FIELD_RECORDING_RECORD_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_RECORDING_REC_GROUP = "recording_rec_group";
    public static final String FIELD_RECORDING_REC_GROUP_DATA_TYPE = "TEXT";

    public static final String FIELD_RECORDING_PLAY_GROUP = "recording_play_group";
    public static final String FIELD_RECORDING_PLAY_GROUP_DATA_TYPE = "TEXT";

    public static final String FIELD_RECORDING_STORAGE_GROUP = "recording_storage_group";
    public static final String FIELD_RECORDING_STORAGE_GROUP_DATA_TYPE = "TEXT";

    public static final String FIELD_RECORDING_REC_TYPE = "recording_rec_type";
    public static final String FIELD_RECORDING_REC_TYPE_DATA_TYPE = "INTEGER";

    public static final String FIELD_RECORDING_DUP_IN_TYPE = "recording_dup_in_type";
    public static final String FIELD_RECORDING_DUP_IN_TYPE_DATA_TYPE = "INTEGER";

    public static final String FIELD_RECORDING_DUP_METHOD = "recording_dup_method";
    public static final String FIELD_RECORDING_DUP_METHOD_DATA_TYPE = "INTEGER";

    public static final String FIELD_RECORDING_ENCODER_ID = "recording_encoder_id";
    public static final String FIELD_RECORDING_ENCODER_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_RECORDING_ENCODER_NAME = "recording_encoder_name";
    public static final String FIELD_RECORDING_ENCODER_NAME_DATA_TYPE = "TEXT";

    public static final String FIELD_RECORDING_PROFILE = "recording_profile";
    public static final String FIELD_RECORDING_PROFILE_DATA_TYPE = "TEXT";

    public static final String FIELD_CAST_MEMBER_NAMES = "cast_member_names";
    public static final String FIELD_CAST_MEMBER_NAMES_DATA_TYPE = "TEXT";

    public static final String FIELD_CAST_MEMBER_CHARACTERS = "cast_member_characters";
    public static final String FIELD_CAST_MEMBER_CHARACTERS_DATA_TYPE = "TEXT";

    public static final String FIELD_CAST_MEMBER_ROLES = "cast_member_roles";
    public static final String FIELD_CAST_MEMBER_ROLES_DATA_TYPE = "TEXT";


    static {

        StringBuilder createTable = new StringBuilder();

        createTable.append( "CREATE VIRTUAL TABLE " + TABLE_NAME + " using fts3 (" );
        createTable.append( FIELD_PROGRAM_TYPE ).append( " " ).append( FIELD_PROGRAM_TYPE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_PROGRAM_START_TIME ).append( " " ).append( FIELD_PROGRAM_START_TIME_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_PROGRAM_END_TIME ).append( " " ).append( FIELD_PROGRAM_END_TIME_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_PROGRAM_TITLE_SORT ).append( " " ).append( FIELD_PROGRAM_TITLE_SORT_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_PROGRAM_TITLE ).append( " " ).append( FIELD_PROGRAM_TITLE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_PROGRAM_SUB_TITLE ).append( " " ).append( FIELD_PROGRAM_SUB_TITLE_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_CATEGORY ).append( " " ).append( FIELD_PROGRAM_CATEGORY_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_CAT_TYPE ).append( " " ).append( FIELD_PROGRAM_CAT_TYPE_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_REPEAT ).append( " " ).append( FIELD_PROGRAM_REPEAT_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_VIDEO_PROPS ).append( " " ).append( FIELD_PROGRAM_VIDEO_PROPS_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_AUDIO_PROPS ).append( " " ).append( FIELD_PROGRAM_AUDIO_PROPS_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_SUB_PROPS ).append( " " ).append( FIELD_PROGRAM_SUB_PROPS_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_SERIES_ID ).append( " " ).append( FIELD_PROGRAM_SERIES_ID_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_PROGRAM_ID ).append( " " ).append( FIELD_PROGRAM_PROGRAM_ID_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_STARS ).append( " " ).append( FIELD_PROGRAM_STARS_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_FILE_SIZE ).append( " " ).append( FIELD_PROGRAM_FILE_SIZE_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_LAST_MODIFIED ).append( " " ).append( FIELD_PROGRAM_LAST_MODIFIED_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_PROGRAM_FLAGS ).append( " " ).append( FIELD_PROGRAM_PROGRAM_FLAGS_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_FILE_NAME ).append( " " ).append( FIELD_PROGRAM_FILE_NAME_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_HOSTNAME ).append( " " ).append( FIELD_PROGRAM_HOSTNAME_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_AIR_DATE ).append( " " ).append( FIELD_PROGRAM_AIR_DATE_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_DESCRIPTION ).append( " " ).append( FIELD_PROGRAM_DESCRIPTION_DATA_TYPE ).append(", ");
        createTable.append( FIELD_PROGRAM_INETREF ).append( " " ).append( FIELD_PROGRAM_INETREF_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_PROGRAM_SEASON ).append( " " ).append( FIELD_PROGRAM_SEASON_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_PROGRAM_EPISODE ).append( " " ).append( FIELD_PROGRAM_EPISODE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_PROGRAM_TOTAL_EPISODES ).append( " " ).append( FIELD_PROGRAM_TOTAL_EPISODES_DATA_TYPE ).append( ", " );

        createTable.append( FIELD_CHANNEL_CHAN_ID ).append( " " ).append( FIELD_CHANNEL_CHAN_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_CHAN_NUM ).append( " " ).append( FIELD_CHANNEL_CHAN_NUM_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_CALLSIGN ).append( " " ).append( FIELD_CHANNEL_CALLSIGN_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_ICON_URL ).append( " " ).append( FIELD_CHANNEL_ICON_URL_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_CHANNEL_NAME ).append( " " ).append( FIELD_CHANNEL_CHANNEL_NAME_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_MPLEX_ID ).append( " " ).append( FIELD_CHANNEL_MPLEX_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_SERVICE_ID ).append( " " ).append( FIELD_CHANNEL_SERVICE_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_ATSC_MAJOR_CHAN ).append( " " ).append( FIELD_CHANNEL_ATSC_MAJOR_CHAN_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_ATSC_MINOR_CHAN ).append( " " ).append( FIELD_CHANNEL_ATSC_MINOR_CHAN_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_FORMAT ).append( " " ).append( FIELD_CHANNEL_FORMAT_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_FREQUENCY_ID ).append( " " ).append( FIELD_CHANNEL_FREQUENCY_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_FINE_TUNE ).append( " " ).append( FIELD_CHANNEL_FINE_TUNE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_CHAN_FILTERS ).append( " " ).append( FIELD_CHANNEL_CHAN_FILTERS_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_SOURCE_ID ).append( " " ).append( FIELD_CHANNEL_SOURCE_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_INPUT_ID ).append( " " ).append( FIELD_CHANNEL_INPUT_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_COMM_FREE ).append( " " ).append( FIELD_CHANNEL_COMM_FREE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_USE_EIT ).append( " " ).append( FIELD_CHANNEL_USE_EIT_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_VISIBLE ).append( " " ).append( FIELD_CHANNEL_VISIBLE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_XMLTVID ).append( " " ).append( FIELD_CHANNEL_XMLTVID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CHANNEL_DEFAULT_AUTH ).append( " " ).append( FIELD_CHANNEL_DEFAULT_AUTH_DATA_TYPE ).append( ", " );

        createTable.append( FIELD_RECORDING_RECORDED_ID ).append( " " ).append( FIELD_RECORDING_RECORDED_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_STATUS ).append( " " ).append( FIELD_RECORDING_STATUS_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_PRIORITY ).append( " " ).append( FIELD_RECORDING_PRIORITY_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_START_TS ).append( " " ).append( FIELD_RECORDING_START_TS_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_END_TS ).append( " " ).append( FIELD_RECORDING_END_TS_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_RECORD_ID ).append( " " ).append( FIELD_RECORDING_RECORD_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_REC_GROUP ).append( " " ).append( FIELD_RECORDING_REC_GROUP_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_PLAY_GROUP ).append( " " ).append( FIELD_RECORDING_PLAY_GROUP_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_STORAGE_GROUP ).append( " " ).append( FIELD_RECORDING_STORAGE_GROUP_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_REC_TYPE ).append( " " ).append( FIELD_RECORDING_REC_TYPE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_DUP_IN_TYPE ).append( " " ).append( FIELD_RECORDING_DUP_IN_TYPE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_DUP_METHOD ).append( " " ).append( FIELD_RECORDING_DUP_METHOD_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_ENCODER_ID ).append( " " ).append( FIELD_RECORDING_ENCODER_ID_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_ENCODER_NAME ).append( " " ).append( FIELD_RECORDING_ENCODER_NAME_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_RECORDING_PROFILE ).append( " " ).append( FIELD_RECORDING_PROFILE_DATA_TYPE ).append( ", " );

        createTable.append( FIELD_CAST_MEMBER_NAMES ).append( " " ).append( FIELD_CAST_MEMBER_NAMES_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CAST_MEMBER_CHARACTERS ).append( " " ).append( FIELD_CAST_MEMBER_CHARACTERS_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_CAST_MEMBER_ROLES ).append( " " ).append( FIELD_CAST_MEMBER_ROLES_DATA_TYPE );

        createTable.append( ");" );

        CREATE_TABLE = createTable.toString();

        StringBuilder dropTable = new StringBuilder();

        dropTable.append( "DROP TABLE IF EXISTS " ).append( TABLE_NAME );

        DROP_TABLE = dropTable.toString();

    }

}
