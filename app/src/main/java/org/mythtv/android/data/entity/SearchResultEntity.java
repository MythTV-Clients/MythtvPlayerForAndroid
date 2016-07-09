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

package org.mythtv.android.data.entity;

import lombok.Data;

/**
 * Created by dmfrey on 10/8/15.
 */
@Data
public class SearchResultEntity {

    public static final String TABLE_NAME = "search_result";
    public static final String CREATE_TABLE;
    public static final String DROP_TABLE;
    public static final String SQL_SELECT_MATCH = TABLE_NAME + " MATCH ?";
    public static final String SQL_DELETE_ALL = "delete from " + TABLE_NAME + " where type = ?";
    public static final String SQL_INSERT = "insert into " + TABLE_NAME + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    static {

        StringBuilder createTable = new StringBuilder();

        createTable.append( "CREATE VIRTUAL TABLE " + TABLE_NAME + " using fts3 (" );
        createTable.append( "START_TIME" ).append( " " ).append( "INTEGER" ).append( ", ");
        createTable.append( "TITLE" ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( "SUB_TITLE" ).append( " " ).append( "TEXT" ).append( ", ");
        createTable.append( "CATEGORY" ).append( " " ).append( "TEXT" ).append( ", ");
        createTable.append( "DESCRIPTION" ).append( " " ).append( "TEXT" ).append( ", ");
        createTable.append( "INETREF" ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( "SEASON" ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( "EPISODE" ).append( " " ).append( "TEXT" ).append( ", " );

        createTable.append( "CHAN_ID" ).append( " " ).append( "INTEGER" ).append( ", " );
        createTable.append( "CHAN_NUM" ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( "CALLSIGN" ).append( " " ).append( "TEXT" ).append( ", " );

        createTable.append( "CAST_MEMBER_NAMES" ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( "CAST_MEMBER_CHARACTERS" ).append( " ").append( "TEXT" ).append( ", " );

        createTable.append( "VIDEO_ID" ).append( " " ).append( "INTEGER" ).append( ", ");
        createTable.append( "RATING" ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( "CONTENT_TYPE" ).append( " " ).append( "TEXT" ).append( ", " );

        createTable.append( "STORAGE_GROUP" ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( "FILENAME" ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( "HOSTNAME" ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( "TYPE" ).append(" " ).append( "TEXT" );

        createTable.append( ");" );

        CREATE_TABLE = createTable.toString();

        DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    private int chanId;
    private long startTime;
    private String title;
    private String subTitle;
    private String category;
    private String callsign;
    private String channelNumber;
    private String description;
    private String inetref;
    private int season;
    private int episode;
    private String castMembers;
    private String characters;
    private int videoId;
    private String contentType;
    private String rating;
    private String storageGroup;
    private String filename;
    private String hostname;
    private String type;

}
