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

//import lombok.Data;

/**
 * Created by dmfrey on 10/8/15.
 */
//@Data
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

    public SearchResultEntity() {
    }

    public SearchResultEntity(int chanId, long startTime, String title, String subTitle, String category, String callsign, String channelNumber, String description, String inetref, int season, int episode, String castMembers, String characters, int videoId, String contentType, String rating, String storageGroup, String filename, String hostname, String type ) {

        this.chanId = chanId;
        this.startTime = startTime;
        this.title = title;
        this.subTitle = subTitle;
        this.category = category;
        this.callsign = callsign;
        this.channelNumber = channelNumber;
        this.description = description;
        this.inetref = inetref;
        this.season = season;
        this.episode = episode;
        this.castMembers = castMembers;
        this.characters = characters;
        this.videoId = videoId;
        this.contentType = contentType;
        this.rating = rating;
        this.storageGroup = storageGroup;
        this.filename = filename;
        this.hostname = hostname;
        this.type = type;

    }

    public int getChanId() {
        return chanId;
    }

    public void setChanId(int chanId) {
        this.chanId = chanId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInetref() {
        return inetref;
    }

    public void setInetref(String inetref) {
        this.inetref = inetref;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getCastMembers() {
        return castMembers;
    }

    public void setCastMembers(String castMembers) {
        this.castMembers = castMembers;
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStorageGroup() {
        return storageGroup;
    }

    public void setStorageGroup(String storageGroup) {
        this.storageGroup = storageGroup;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SearchResultEntity{" +
                "chanId=" + chanId +
                ", startTime=" + startTime +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", category='" + category + '\'' +
                ", callsign='" + callsign + '\'' +
                ", channelNumber='" + channelNumber + '\'' +
                ", description='" + description + '\'' +
                ", inetref='" + inetref + '\'' +
                ", season=" + season +
                ", episode=" + episode +
                ", castMembers='" + castMembers + '\'' +
                ", characters='" + characters + '\'' +
                ", videoId=" + videoId +
                ", contentType='" + contentType + '\'' +
                ", rating='" + rating + '\'' +
                ", storageGroup='" + storageGroup + '\'' +
                ", filename='" + filename + '\'' +
                ", hostname='" + hostname + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
