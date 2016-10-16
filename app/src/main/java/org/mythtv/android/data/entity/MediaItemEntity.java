package org.mythtv.android.data.entity;

import org.joda.time.DateTime;
import org.mythtv.android.domain.Media;

/**
 * Created by dmfrey on 7/10/16.
 */

public class MediaItemEntity {

    public static final String TABLE_NAME = "media_item";
    public static final String CREATE_TABLE;
    public static final String DROP_TABLE;
    public static final String SQL_SELECT_MATCH = TABLE_NAME + " MATCH ?";
    public static final String SQL_DELETE_ALL = "delete from " + TABLE_NAME + " where type = ?";
    public static final String SQL_INSERT = "insert into " + TABLE_NAME + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String FIELD_ID = "id";
    public static final String FIELD_MEDIA = "media";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_SUBTITLE = "subtitle";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_START_DATE = "start_date";
    public static final String FIELD_PROGRAM_FLAGS = "program_flags";
    public static final String FIELD_SEASON = "season";
    public static final String FIELD_EPISODE = "episode";
    public static final String FIELD_STUDIO = "studio";
    public static final String FIELD_CAST_MEMBERS = "cast_members";
    public static final String FIELD_CHARACTERS = "characters";
    public static final String FIELD_URL = "url";
    public static final String FIELD_FANART_URL = "fanart_url";
    public static final String FIELD_COVERART_URL = "coverart_url";
    public static final String FIELD_BANNER_URL = "banner_url";
    public static final String FIELD_PREVIEW_URL = "preview_url";
    public static final String FIELD_CONTENT_TYPE = "contentType";
    public static final String FIELD_DURATION = "duration";
    public static final String FIELD_RECORDING = "recording";
    public static final String FIELD_LIVE_STREAM_PERCENT_COMPLETE = "percent_complete";
    public static final String FIELD_LIVE_STREAM_ID = "live_stream_id";
    public static final String FIELD_CREATE_LIVE_STREAM_URL = "create_live_stream_url";
    public static final String FIELD_REMOVE_LIVE_STREAM_URL = "remove_live_stream_url";
    public static final String FIELD_WATCHED_STATUS = "watched_status";
    public static final String FIELD_MARK_WATCHED_URL = "mark_watched_url";

    static {

        StringBuilder createTable = new StringBuilder();

        createTable.append( "CREATE VIRTUAL TABLE " + TABLE_NAME + " using fts3 (" );
        createTable.append( FIELD_ID ).append( " " ).append( "INTEGER" ).append( ", ");
        createTable.append( FIELD_MEDIA ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( FIELD_TITLE ).append( " " ).append( "TEXT" ).append( ", ");
        createTable.append( FIELD_SUBTITLE ).append( " " ).append( "TEXT" ).append( ", ");
        createTable.append( FIELD_DESCRIPTION ).append( " " ).append( "TEXT" ).append( ", ");
        createTable.append( FIELD_START_DATE ).append( " " ).append( "INTEGER" ).append( ", " );
        createTable.append( FIELD_PROGRAM_FLAGS ).append( " " ).append( "INTEGER" ).append( ", " );
        createTable.append( FIELD_SEASON ).append( " " ).append( "INTEGER" ).append( ", " );
        createTable.append( FIELD_EPISODE ).append( " " ).append( "INTEGER" ).append( ", " );
        createTable.append( FIELD_STUDIO ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( FIELD_CAST_MEMBERS ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( FIELD_CHARACTERS ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( FIELD_URL ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( FIELD_FANART_URL ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( FIELD_COVERART_URL ).append( " ").append( "TEXT" ).append( ", " );
        createTable.append( FIELD_BANNER_URL ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( FIELD_PREVIEW_URL ).append( " " ).append( "TEXT" ).append( ", ");
        createTable.append( FIELD_CONTENT_TYPE ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( FIELD_DURATION ).append( " " ).append( "INTEGER" ).append( ", " );
        createTable.append( FIELD_RECORDING ).append( " " ).append( "INTEGER" ).append( ", " );
        createTable.append( FIELD_LIVE_STREAM_PERCENT_COMPLETE ).append( " " ).append( "INTEGER" ).append( ", " );
        createTable.append( FIELD_LIVE_STREAM_ID ).append( " " ).append( "INTEGER" ).append( ", " );
        createTable.append( FIELD_CREATE_LIVE_STREAM_URL ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( FIELD_REMOVE_LIVE_STREAM_URL ).append( " " ).append( "TEXT" ).append( ", " );
        createTable.append( FIELD_WATCHED_STATUS ).append( " " ).append( "INTEGER" ).append( ", " );
        createTable.append( FIELD_MARK_WATCHED_URL ).append(" " ).append( "TEXT" );

        createTable.append( ");" );

        CREATE_TABLE = createTable.toString();

        DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    private int id;
    private Media media;
    private String title;
    private String subTitle;
    private String description;
    private DateTime startDate;
    private int programFlags;
    private int season;
    private int episode;
    private String studio; // video = studio, recording = channel
    private String castMembers;
    private String characters;
    private String url;
    private String fanartUrl;
    private String coverartUrl;
    private String bannerUrl;
    private String previewUrl;
    private String contentType;
    private long duration;
    private int percentComplete;
    private boolean recording;
    private int liveStreamId;
    private String createHttpLiveStreamUrl;
    private String removeHttpLiveStreamUrl;
    private boolean watched;
    private String markWatchedUrl;

    public MediaItemEntity() { }

    public int getId() {

        return id;
    }

    public void setId( int id ) {

        this.id = id;

    }

    public Media getMedia() {

        return media;
    }

    public void setMedia( Media media ) {

        this.media = media;

    }

    public String getTitle() {

        return title;
    }

    public void setTitle( String title ) {

        this.title = title;

    }

    public String getSubTitle() {

        return subTitle;
    }

    public void setSubTitle( String subTitle ) {

        this.subTitle = subTitle;

    }

    public String getDescription() {

        return description;
    }

    public void setDescription( String description ) {

        this.description = description;

    }

    public DateTime getStartDate() {

        return startDate;
    }

    public void setStartDate( DateTime startDate ) {

        this.startDate = startDate;

    }

    public int getProgramFlags() {

        return programFlags;
    }

    public void setProgramFlags( int programFlags ) {

        this.programFlags = programFlags;

    }

    public int getSeason() {

        return season;
    }

    public void setSeason( int season ) {

        this.season = season;

    }

    public int getEpisode() {

        return episode;
    }

    public void setEpisode( int episode ) {

        this.episode = episode;

    }

    public String getStudio() {

        return studio;
    }

    public void setStudio( String studio ) {

        this.studio = studio;

    }

    public String getCastMembers() {

        return castMembers;
    }

    public void setCastMembers( String castMembers ) {

        this.castMembers = castMembers;

    }

    public String getCharacters() {

        return characters;
    }

    public void setCharacters( String characters ) {

        this.characters = characters;

    }

    public String getUrl() {

        return url;
    }

    public void setUrl( String url ) {

        this.url = url;

    }

    public String getFanartUrl() {

        return fanartUrl;
    }

    public String getFanartUrl( String width ) {

        return fanartUrl;
    }

    public void setFanartUrl( String fanartUrl ) {

        this.fanartUrl = fanartUrl;

    }

    public String getCoverartUrl() {

        return coverartUrl;
    }

    public String getCoverartUrl( String width ) {

        return coverartUrl;
    }

    public void setCoverartUrl( String coverartUrl ) {

        this.coverartUrl = coverartUrl;

    }

    public String getBannerUrl() {

        return bannerUrl;
    }

    public String getBannerUrl( String width ) {

        return bannerUrl;
    }

    public void setBannerUrl( String bannerUrl ) {

        this.bannerUrl = bannerUrl;

    }

    public String getPreviewUrl() {

        return previewUrl;
    }

    public String getPreviewUrl( String width ) {

        return previewUrl;
    }

    public void setPreviewUrl( String previewUrl ) {

        this.previewUrl = previewUrl;

    }

    public String getContentType() {

        return contentType;
    }

    public void setContentType( String contentType ) {

        this.contentType = contentType;
    }

    public long getDuration() {

        return duration;
    }

    public void setDuration( long duration ) {

        this.duration = duration;

    }

    public int getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete( int percentComplete ) {
        this.percentComplete = percentComplete;
    }

    public boolean isRecording() {

        return recording;
    }

    public void setRecording( boolean recording ) {

        this.recording = recording;

    }

    public int getLiveStreamId() {

        return liveStreamId;
    }

    public void setLiveStreamId( int liveStreamId ) {

        this.liveStreamId = liveStreamId;

    }

    public String getCreateHttpLiveStreamUrl() {

        return createHttpLiveStreamUrl;
    }

    public void setCreateHttpLiveStreamUrl( String createHttpLiveStreamUrl ) {

        this.createHttpLiveStreamUrl = createHttpLiveStreamUrl;

    }

    public String getRemoveHttpLiveStreamUrl() {

        return removeHttpLiveStreamUrl;
    }

    public void setRemoveHttpLiveStreamUrl( String removeHttpLiveStreamUrl ) {

        this.removeHttpLiveStreamUrl = removeHttpLiveStreamUrl;

    }

    public boolean isWatched() {

        return watched;
    }

    public void setWatched( boolean watched ) {

        this.watched = watched;

    }

    public String getMarkWatchedUrl() {

        return markWatchedUrl;
    }

    public void setMarkWatchedUrl( String markWatchedUrl ) {

        this.markWatchedUrl = markWatchedUrl;

    }

    @Override
    public String toString() {
        return "MediaItemModel{" +
                "id=" + id +
                ", media=" + media +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", programFlags=" + programFlags +
                ", season=" + season +
                ", episode=" + episode +
                ", studio='" + studio + '\'' +
                ", castMembers='" + castMembers + '\'' +
                ", characters='" + characters + '\'' +
                ", url='" + url + '\'' +
                ", fanartUrl='" + fanartUrl + '\'' +
                ", coverartUrl='" + coverartUrl + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", previewUrl='" + previewUrl + '\'' +
                ", contentType='" + contentType + '\'' +
                ", duration=" + duration +
                ", percentComplete=" + percentComplete +
                ", recording=" + recording +
                ", liveStreamId=" + liveStreamId +
                ", createHttpLiveStreamUrl='" + createHttpLiveStreamUrl + '\'' +
                ", removeHttpLiveStreamUrl='" + removeHttpLiveStreamUrl + '\'' +
                ", watched=" + watched +
                ", markWatchedUrl='" + markWatchedUrl + '\'' +
                '}';
    }

}
