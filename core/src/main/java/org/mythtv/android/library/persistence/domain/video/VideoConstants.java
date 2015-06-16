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

package org.mythtv.android.library.persistence.domain.video;

import android.net.Uri;

import org.mythtv.android.library.persistence.domain.AbstractBaseDatabase;
import org.mythtv.android.library.persistence.repository.MythtvProvider;

/*
 * Created by dmfrey on 4/15/15.
 */
public class VideoConstants extends AbstractBaseDatabase {

    public static final String TABLE_NAME = "videos";

    public static final Uri CONTENT_URI = Uri.parse( "content://" + MythtvProvider.AUTHORITY + "/" + TABLE_NAME );
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + MythtvProvider.AUTHORITY + "." + TABLE_NAME;
    public static final int ALL		    			= 300;
    public static final int SINGLE    				= 301;
    public static final int ALL_FTS		    		= 302;
    public static final int SINGLE_ID  				= 303;
    public static final int ALL_TV_TITLES  			= 304;
    public static final int ALL_TV_SEASON  			= 305;

    public static final String CREATE_TABLE, DROP_TABLE;

    public static final String FIELD_VIDEO_ID = "id";
    public static final String FIELD_VIDEO_ID_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_TITLE_SORT = "title_sort";
    public static final String FIELD_VIDEO_TITLE_SORT_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_TITLE = "title";
    public static final String FIELD_VIDEO_TITLE_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_SUB_TITLE = "sub_title";
    public static final String FIELD_VIDEO_SUB_TITLE_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_TAGLINE = "tagline";
    public static final String FIELD_VIDEO_TAGLINE_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_DIRECTOR = "director";
    public static final String FIELD_VIDEO_DIRECTOR_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_STUDIO = "studio";
    public static final String FIELD_VIDEO_STUDIO_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_DESCRIPTION = "description";
    public static final String FIELD_VIDEO_DESCRIPTION_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_CERTIFICATION = "certification";
    public static final String FIELD_VIDEO_CERTIFICATION_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_INETREF = "inetref";
    public static final String FIELD_VIDEO_INETREF_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_COLLECTIONREF = "collectionref";
    public static final String FIELD_VIDEO_COLLECTIONREF_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_HOMEPAGE = "homepage";
    public static final String FIELD_VIDEO_HOMEPAGE_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_RELEASE_DATE = "release_date";
    public static final String FIELD_VIDEO_RELEASE_DATE_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_ADD_DATE = "add_date";
    public static final String FIELD_VIDEO_ADD_DATE_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_USER_RATING = "user_rating";
    public static final String FIELD_VIDEO_USER_RATING_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_LENGTH = "length";
    public static final String FIELD_VIDEO_LENGTH_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_PLAY_COUNT = "play_count";
    public static final String FIELD_VIDEO_PLAY_COUNT_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_SEASON = "season";
    public static final String FIELD_VIDEO_SEASON_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_EPISODE = "episode";
    public static final String FIELD_VIDEO_EPISODE_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_PARENTAL_LEVEL = "parental_level";
    public static final String FIELD_VIDEO_PARENTAL_LEVEL_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_VISIBLE = "visible";
    public static final String FIELD_VIDEO_VISIBLE_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_WATCHED = "watched";
    public static final String FIELD_VIDEO_WATCHED_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_PROCESSED = "processed";
    public static final String FIELD_VIDEO_PROCESSED_DATA_TYPE = "INTEGER";

    public static final String FIELD_VIDEO_CONTENT_TYPE = "content_type";
    public static final String FIELD_VIDEO_CONTENT_TYPE_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_FILEPATH = "filepath";
    public static final String FIELD_VIDEO_FILEPATH_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_FILENAME = "filename";
    public static final String FIELD_VIDEO_FILENAME_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_HASH = "hash";
    public static final String FIELD_VIDEO_HASH_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_HOSTNAME = "hostname";
    public static final String FIELD_VIDEO_HOSTNAME_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_COVERART = "coverart";
    public static final String FIELD_VIDEO_COVERART_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_FANART = "fanart";
    public static final String FIELD_VIDEO_FANART_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_BANNER = "banner";
    public static final String FIELD_VIDEO_BANNER_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_SCREENSHOT = "screenshot";
    public static final String FIELD_VIDEO_SCREENSHOT_DATA_TYPE = "TEXT";

    public static final String FIELD_VIDEO_TRAILER = "trailer";
    public static final String FIELD_VIDEO_TRAILER_DATA_TYPE = "TEXT";

    public static final String FIELD_CAST_MEMBER_NAMES = "cast_member_names";
    public static final String FIELD_CAST_MEMBER_NAMES_DATA_TYPE = "TEXT";

    public static final String FIELD_CAST_MEMBER_CHARACTERS = "cast_member_characters";
    public static final String FIELD_CAST_MEMBER_CHARACTERS_DATA_TYPE = "TEXT";

    public static final String FIELD_CAST_MEMBER_ROLES = "cast_member_roles";
    public static final String FIELD_CAST_MEMBER_ROLES_DATA_TYPE = "TEXT";


    static {

        StringBuilder createTable = new StringBuilder();

        createTable.append( "CREATE VIRTUAL TABLE " + TABLE_NAME + " using fts3 (" );
        createTable.append( FIELD_VIDEO_ID ).append( " " ).append( FIELD_VIDEO_ID_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_TITLE_SORT ).append( " " ).append( FIELD_VIDEO_TITLE_SORT_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_TITLE ).append( " " ).append( FIELD_VIDEO_TITLE_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_SUB_TITLE ).append( " " ).append( FIELD_VIDEO_SUB_TITLE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_TAGLINE ).append( " " ).append( FIELD_VIDEO_TAGLINE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_DIRECTOR ).append( " " ).append( FIELD_VIDEO_DIRECTOR_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_STUDIO ).append( " " ).append( FIELD_VIDEO_STUDIO_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_DESCRIPTION ).append( " " ).append( FIELD_VIDEO_DESCRIPTION_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_CERTIFICATION ).append( " " ).append( FIELD_VIDEO_CERTIFICATION_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_INETREF ).append( " " ).append( FIELD_VIDEO_INETREF_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_COLLECTIONREF ).append( " " ).append( FIELD_VIDEO_COLLECTIONREF_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_HOMEPAGE ).append( " " ).append( FIELD_VIDEO_HOMEPAGE_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_RELEASE_DATE ).append( " " ).append( FIELD_VIDEO_RELEASE_DATE_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_ADD_DATE ).append( " " ).append( FIELD_VIDEO_ADD_DATE_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_USER_RATING ).append( " " ).append( FIELD_VIDEO_USER_RATING_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_LENGTH ).append( " " ).append( FIELD_VIDEO_LENGTH_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_PLAY_COUNT ).append( " " ).append( FIELD_VIDEO_PLAY_COUNT_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_SEASON ).append( " " ).append( FIELD_VIDEO_SEASON_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_EPISODE ).append( " " ).append( FIELD_VIDEO_EPISODE_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_PARENTAL_LEVEL ).append( " " ).append( FIELD_VIDEO_PARENTAL_LEVEL_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_VISIBLE ).append( " " ).append( FIELD_VIDEO_VISIBLE_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_WATCHED ).append( " " ).append( FIELD_VIDEO_WATCHED_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_PROCESSED ).append( " " ).append( FIELD_VIDEO_PROCESSED_DATA_TYPE ).append(", ");
        createTable.append( FIELD_VIDEO_CONTENT_TYPE ).append( " " ).append( FIELD_VIDEO_CONTENT_TYPE_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_FILEPATH ).append( " " ).append( FIELD_VIDEO_FILEPATH_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_FILENAME ).append( " " ).append( FIELD_VIDEO_FILENAME_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_HASH ).append( " " ).append( FIELD_VIDEO_HASH_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_HOSTNAME ).append( " " ).append( FIELD_VIDEO_HOSTNAME_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_COVERART ).append( " " ).append( FIELD_VIDEO_COVERART_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_FANART ).append( " " ).append( FIELD_VIDEO_FANART_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_BANNER ).append( " " ).append( FIELD_VIDEO_BANNER_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_SCREENSHOT ).append( " " ).append( FIELD_VIDEO_SCREENSHOT_DATA_TYPE ).append( ", " );
        createTable.append( FIELD_VIDEO_TRAILER ).append( " " ).append( FIELD_VIDEO_TRAILER_DATA_TYPE ).append( ", " );

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
