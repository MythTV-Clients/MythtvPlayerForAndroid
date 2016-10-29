package org.mythtv.android.presentation.model;

import android.os.Bundle;

import org.joda.time.DateTime;
import org.mythtv.android.domain.Media;

import java.io.Serializable;

/**
 * Created by dmfrey on 7/10/16.
 */

public class MediaItemModel implements Serializable {

    private static final String WIDTH_QS = "&Width=%s";

    public static final String KEY_ID = "id";
    public static final String KEY_MEDIA = "media";
    public static final String KEY_TITLE = "title";
    public static final String KEY_SUBTITLE = "subtitle";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_START_DATE = "start_date";
    public static final String KEY_PROGRAM_FLAGS = "program_flags";
    public static final String KEY_SEASON = "season";
    public static final String KEY_EPISODE = "episode";
    public static final String KEY_STUDIO = "studio";
    public static final String KEY_CAST_MEMBERS = "cast_members";
    public static final String KEY_CHARACTERS = "characters";
    public static final String KEY_URL = "url";
    public static final String KEY_FANART_URL = "fanart_url";
    public static final String KEY_COVERART_URL = "coverart_url";
    public static final String KEY_BANNER_URL = "banner_url";
    public static final String KEY_PREVIEW_URL = "preview_url";
    public static final String KEY_CONTENT_TYPE = "contentType";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_RECORDING = "recording";
    public static final String KEY_LIVE_STREAM_PERCENT_COMPLETE = "percent_complete";
    public static final String KEY_LIVE_STREAM_ID = "live_stream_id";
    public static final String KEY_CREATE_LIVE_STREAM_URL = "create_live_stream_url";
    public static final String KEY_REMOVE_LIVE_STREAM_URL = "remove_live_stream_url";
    public static final String KEY_GET_LIVE_STREAM_URL = "get_live_stream_url";
    public static final String KEY_WATCHED_STATUS = "watched_status";
    public static final String KEY_MARK_WATCHED_URL = "mark_watched_url";
    public static final String KEY_UPDATE_SAVED_BOOKMARK_URL = "update_saved_bookmark_url";
    public static final String KEY_BOOKMARK = "bookmark";

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
    private String getHttpLiveStreamUrl;
    private boolean watched;
    private String markWatchedUrl;
    private String updateSavedBookmarkUrl;
    private long bookmark;

    public MediaItemModel() { }

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

        String url = fanartUrl;
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public void setFanartUrl( String fanartUrl ) {

        this.fanartUrl = fanartUrl;

    }

    public String getCoverartUrl() {

        return coverartUrl;
    }

    public String getCoverartUrl( String width ) {

        String url = coverartUrl;
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public void setCoverartUrl( String coverartUrl ) {

        this.coverartUrl = coverartUrl;

    }

    public String getBannerUrl() {

        return bannerUrl;
    }

    public String getBannerUrl( String width ) {

        String url = bannerUrl;
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public void setBannerUrl( String bannerUrl ) {

        this.bannerUrl = bannerUrl;

    }

    public String getPreviewUrl() {

        return previewUrl;
    }

    public String getPreviewUrl( String width ) {

        String url = previewUrl;
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
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

    public String getGetHttpLiveStreamUrl() {

        return getHttpLiveStreamUrl;
    }

    public void setGetHttpLiveStreamUrl( String getHttpLiveStreamUrl ) {

        this.getHttpLiveStreamUrl = getHttpLiveStreamUrl;

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

    public String getUpdateSavedBookmarkUrl() {

        return updateSavedBookmarkUrl;
    }

    public void setUpdateSavedBookmarkUrl( String updateSavedBookmarkUrl ) {

        this.updateSavedBookmarkUrl = updateSavedBookmarkUrl;

    }

    public long getBookmark() {

        return bookmark;
    }

    public void setBookmark( long bookmark ) {

        this.bookmark = bookmark;

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
                ", getHttpLiveStreamUrl='" + getHttpLiveStreamUrl + '\'' +
                ", watched=" + watched +
                ", markWatchedUrl='" + markWatchedUrl + '\'' +
                ", updateSavedBookmarkUrl='" + updateSavedBookmarkUrl + '\'' +
                ", bookmark=" + bookmark +
                '}';
    }

    public Bundle toBundle() {

        Bundle wrapper = new Bundle();
        wrapper.putInt( KEY_ID, id );
        wrapper.putString( KEY_MEDIA, media.name() );
        if( null != title && !"".equals( title ) ) {
            wrapper.putString( KEY_TITLE, title );
        }
        if( null != subTitle && !"".equals( subTitle ) ) {
            wrapper.putString( KEY_SUBTITLE, subTitle );
        }
        if( null != description && !"".equals( description ) ) {
            wrapper.putString( KEY_DESCRIPTION, description );
        }
        if( null != startDate ) {
            wrapper.putLong( KEY_START_DATE, startDate.getMillis() );
        }
        if( programFlags != -1 ) {
            wrapper.putInt( KEY_PROGRAM_FLAGS, programFlags );
        }
        if( season != -1 ) {
            wrapper.putInt( KEY_SEASON, season );
        }
        if( episode != -1 ) {
            wrapper.putInt( KEY_EPISODE, episode );
        }
        if( null != studio && !"".equals( studio ) ) {
            wrapper.putString( KEY_STUDIO, studio );
        }
        if( null != castMembers && !"".equals( castMembers ) ) {
            wrapper.putString( KEY_CAST_MEMBERS, castMembers );
        }
        if( null != characters && !"".equals( characters ) ) {
            wrapper.putString( KEY_CHARACTERS, characters );
        }
        wrapper.putString( KEY_URL, url );
        if( null != fanartUrl && !"".equals( fanartUrl ) ) {
            wrapper.putString( KEY_FANART_URL, fanartUrl );
        }
        if( null != contentType && !"".equals( coverartUrl ) ) {
            wrapper.putString( KEY_COVERART_URL, coverartUrl );
        }
        if( null != bannerUrl && !"".equals( bannerUrl ) ) {
            wrapper.putString( KEY_BANNER_URL, bannerUrl );
        }
        if( null != previewUrl && !"".equals( previewUrl ) ) {
            wrapper.putString( KEY_PREVIEW_URL, previewUrl );
        }
        wrapper.putString( KEY_CONTENT_TYPE, contentType );

        wrapper.putLong( KEY_DURATION, duration );

        wrapper.putInt( KEY_LIVE_STREAM_PERCENT_COMPLETE, percentComplete );

        wrapper.putBoolean( KEY_RECORDING, recording );

        if( liveStreamId != -1 ) {
            wrapper.putInt( KEY_LIVE_STREAM_ID, liveStreamId );
        }

        if( null != createHttpLiveStreamUrl && !"".equals( createHttpLiveStreamUrl ) ) {
            wrapper.putString( KEY_CREATE_LIVE_STREAM_URL, createHttpLiveStreamUrl );
        }

        if( null != removeHttpLiveStreamUrl && !"".equals( removeHttpLiveStreamUrl ) ) {
            wrapper.putString( KEY_REMOVE_LIVE_STREAM_URL, removeHttpLiveStreamUrl );
        }

        wrapper.putBoolean( KEY_WATCHED_STATUS, watched );

        if( null != markWatchedUrl && !"".equals( markWatchedUrl ) ) {
            wrapper.putString( KEY_MARK_WATCHED_URL, markWatchedUrl );
        }

        if( null != updateSavedBookmarkUrl && !"".equals( updateSavedBookmarkUrl ) ) {
            wrapper.putString( KEY_UPDATE_SAVED_BOOKMARK_URL, updateSavedBookmarkUrl );
        }

        wrapper.putLong( KEY_BOOKMARK, bookmark );

        return wrapper;
    }

    public static MediaItemModel fromBundle( Bundle wrapper ) {

        if( null == wrapper ) {

            return null;
        }

        MediaItemModel media = new MediaItemModel();
        media.setId( wrapper.getInt( KEY_ID ) );
        media.setMedia( Media.valueOf( wrapper.getString( KEY_MEDIA ) ) );
        if( wrapper.containsKey( KEY_TITLE ) ) {
            media.setTitle( wrapper.getString( KEY_TITLE ) );
        }
        if( wrapper.containsKey( KEY_SUBTITLE ) ) {
            media.setSubTitle( wrapper.getString( KEY_SUBTITLE ) );
        }
        if( wrapper.containsKey( KEY_DESCRIPTION ) ) {
            media.setDescription( wrapper.getString( KEY_DESCRIPTION ) );
        }
        if( wrapper.containsKey( KEY_START_DATE ) ) {
            media.setStartDate( new DateTime( wrapper.getLong( KEY_START_DATE ) ) );
        }
        if( wrapper.containsKey( KEY_PROGRAM_FLAGS ) ) {
            media.setProgramFlags( wrapper.getInt( KEY_PROGRAM_FLAGS ) );
        }
        if( wrapper.containsKey( KEY_SEASON ) ) {
            media.setSeason( wrapper.getInt( KEY_SEASON ) );
        }
        if( wrapper.containsKey( KEY_EPISODE ) ) {
            media.setEpisode( wrapper.getInt( KEY_EPISODE ) );
        }
        if( wrapper.containsKey( KEY_STUDIO ) ) {
            media.setStudio( wrapper.getString( KEY_STUDIO ) );
        }
        if( wrapper.containsKey( KEY_CAST_MEMBERS ) ) {
            media.setCastMembers( wrapper.getString( KEY_CAST_MEMBERS ) );
        }
        if( wrapper.containsKey( KEY_CHARACTERS ) ) {
            media.setCharacters( wrapper.getString( KEY_CHARACTERS ) );
        }
        media.setUrl( wrapper.getString( KEY_URL ) );
        if( wrapper.containsKey( KEY_CONTENT_TYPE ) ) {
            media.setContentType( wrapper.getString( KEY_CONTENT_TYPE ) );
        }
        if( wrapper.containsKey( KEY_FANART_URL ) ) {
            media.setFanartUrl( wrapper.getString( KEY_FANART_URL ) );
        }
        if( wrapper.containsKey( KEY_COVERART_URL ) ) {
            media.setCoverartUrl( wrapper.getString( KEY_COVERART_URL ) );
        }
        if( wrapper.containsKey( KEY_BANNER_URL ) ) {
            media.setBannerUrl( wrapper.getString( KEY_BANNER_URL ) );
        }
        if( wrapper.containsKey( KEY_PREVIEW_URL ) ) {
            media.setPreviewUrl( wrapper.getString( KEY_PREVIEW_URL ) );
        }
        media.setContentType( wrapper.getString( KEY_CONTENT_TYPE ) );
        media.setDuration( wrapper.getLong( KEY_DURATION ) );
        media.setPercentComplete( wrapper.getInt( KEY_LIVE_STREAM_PERCENT_COMPLETE ) );
        media.setRecording( wrapper.getBoolean( KEY_RECORDING ) );
        if( wrapper.containsKey( KEY_LIVE_STREAM_ID ) ) {
            media.setLiveStreamId( wrapper.getInt( KEY_LIVE_STREAM_ID ) );
        }
        if( wrapper.containsKey( KEY_CREATE_LIVE_STREAM_URL ) ) {
            media.setCreateHttpLiveStreamUrl( wrapper.getString( KEY_CREATE_LIVE_STREAM_URL ) );
        }
        if( wrapper.containsKey( KEY_REMOVE_LIVE_STREAM_URL ) ) {
            media.setRemoveHttpLiveStreamUrl( wrapper.getString( KEY_REMOVE_LIVE_STREAM_URL ) );
        }
        media.setWatched( wrapper.getBoolean( KEY_WATCHED_STATUS ) );
        if( wrapper.containsKey( KEY_MARK_WATCHED_URL ) ) {
            media.setMarkWatchedUrl( wrapper.getString( KEY_MARK_WATCHED_URL ) );
        }
        if( wrapper.containsKey( KEY_UPDATE_SAVED_BOOKMARK_URL ) ) {
            media.setUpdateSavedBookmarkUrl( wrapper.getString( KEY_UPDATE_SAVED_BOOKMARK_URL ) );
        }
        media.setBookmark( wrapper.getLong( KEY_BOOKMARK ) );

        return media;
    }

}
