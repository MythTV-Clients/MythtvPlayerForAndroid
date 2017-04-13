package org.mythtv.android.presentation.model;

import android.os.Bundle;

import com.google.auto.value.AutoValue;

import org.joda.time.DateTime;
import org.mythtv.android.domain.Media;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 7/10/16.
 */
@AutoValue
@SuppressWarnings( "PMD.GodClass" )
public abstract class MediaItemModel implements Serializable {

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
    public static final String KEY_WATCHED_STATUS = "watched_status";
    public static final String KEY_MARK_WATCHED_URL = "mark_watched_url";
    public static final String KEY_UPDATE_SAVED_BOOKMARK_URL = "update_saved_bookmark_url";
    public static final String KEY_BOOKMARK = "bookmark";
    public static final String KEY_INETREF = "inetref";
    public static final String KEY_VALIDATION_ERRORS = "validation_errors";
    public static final String KEY_CERTIFICATION = "certification";
    public static final String KEY_PARENTAL_LEVEL = "parental_level";
    public static final String KEY_RECORDING_GROUP = "recording_group";

    public abstract int id();

    @Nullable
    public abstract Media media();

    @Nullable
    public abstract String title();

    @Nullable
    public abstract String subTitle();

    @Nullable
    public abstract String description();

    @Nullable
    public abstract DateTime startDate();

    public abstract int programFlags();

    public abstract int season();

    public abstract int episode();

    @Nullable
    public abstract String studio(); // video = studio, recording = channel

    @Nullable
    public abstract String castMembers();

    @Nullable
    public abstract String characters();

    @Nullable
    public abstract String url();

    @Nullable
    public abstract String fanartUrl();

    @Nullable
    public abstract String coverartUrl();

    @Nullable
    public abstract String bannerUrl();

    @Nullable
    public abstract String previewUrl();

    @Nullable
    public abstract String contentType();

    public abstract long duration();

    public abstract int percentComplete();

    public abstract boolean recording();

    public abstract int liveStreamId();

    public abstract boolean watched();

    @Nullable
    public abstract String markWatchedUrl();

    @Nullable
    public abstract String updateSavedBookmarkUrl();

    public abstract long bookmark();

    @Nullable
    public abstract String inetref();

    @Nullable
    public abstract String certification();

    public abstract int parentalLevel();

    @Nullable
    public abstract String recordingGroup();

    @Nullable
    public abstract List<ErrorModel> validationErrors();

    public static MediaItemModel create(int id, Media media, String title, String subTitle, String description, DateTime startDate, int programFlags, int season, int episode, String studio,
                                   String castMembers, String characters, String url, String fanartUrl, String coverartUrl, String bannerUrl, String previewUrl, String contentType,
                                   long duration, int percentComplete, boolean recording, int liveStreamId,
                                   boolean watched, String markWatchedUrl, String updateSavedBookmarkUrl, long bookmark, String inetref,
                                   String certification, int parentalLevel, String recordingGroup, List<ErrorModel> validationErrors ) {

        return new AutoValue_MediaItemModel( id, media, title, subTitle, description, startDate, programFlags, season, episode, studio,
                castMembers, characters, url, fanartUrl, coverartUrl, bannerUrl, previewUrl, contentType,
                duration, percentComplete, recording, liveStreamId,
                watched, markWatchedUrl, updateSavedBookmarkUrl, bookmark, inetref,
                certification, parentalLevel, recordingGroup, validationErrors );
    }

    public boolean isValid() {

        return null != media() && validationErrors().isEmpty();
    }

    public String getFanartUrl( String width ) {

        String url = fanartUrl();
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public String getCoverartUrl( String width ) {

        String url = coverartUrl();
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public String getBannerUrl( String width ) {

        String url = bannerUrl();
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public String getPreviewUrl( String width ) {

        String url = previewUrl();
        if( null != width && !"".equals( width ) ) {

            url = url + String.format( WIDTH_QS, width );
        }

        return url;
    }

    public Bundle toBundle() {

        Bundle wrapper = new Bundle();
        wrapper.putInt( KEY_ID, id() );
        wrapper.putString( KEY_MEDIA, media().name() );
        if( null != title() && !"".equals( title() ) ) {
            wrapper.putString( KEY_TITLE, title() );
        }
        if( null != subTitle() && !"".equals( subTitle() ) ) {
            wrapper.putString( KEY_SUBTITLE, subTitle() );
        }
        if( null != description() && !"".equals( description() ) ) {
            wrapper.putString( KEY_DESCRIPTION, description() );
        }
        if( null != startDate() ) {
            wrapper.putLong( KEY_START_DATE, startDate().getMillis() );
        }
        if( programFlags() != -1 ) {
            wrapper.putInt( KEY_PROGRAM_FLAGS, programFlags() );
        }
        if( season() != -1 ) {
            wrapper.putInt( KEY_SEASON, season() );
        }
        if( episode() != -1 ) {
            wrapper.putInt( KEY_EPISODE, episode() );
        }
        if( null != studio() && !"".equals( studio() ) ) {
            wrapper.putString( KEY_STUDIO, studio() );
        }
        if( null != castMembers() && !"".equals( castMembers() ) ) {
            wrapper.putString( KEY_CAST_MEMBERS, castMembers() );
        }
        if( null != characters() && !"".equals( characters() ) ) {
            wrapper.putString( KEY_CHARACTERS, characters() );
        }
        wrapper.putString( KEY_URL, url() );
        if( null != fanartUrl() && !"".equals( fanartUrl() ) ) {
            wrapper.putString( KEY_FANART_URL, fanartUrl() );
        }
        if( null != contentType() && !"".equals( coverartUrl() ) ) {
            wrapper.putString( KEY_COVERART_URL, coverartUrl() );
        }
        if( null != bannerUrl() && !"".equals( bannerUrl() ) ) {
            wrapper.putString( KEY_BANNER_URL, bannerUrl() );
        }
        if( null != previewUrl() && !"".equals( previewUrl() ) ) {
            wrapper.putString( KEY_PREVIEW_URL, previewUrl() );
        }
        wrapper.putString( KEY_CONTENT_TYPE, contentType() );

        wrapper.putLong( KEY_DURATION, duration() );

        wrapper.putInt( KEY_LIVE_STREAM_PERCENT_COMPLETE, percentComplete() );

        wrapper.putBoolean( KEY_RECORDING, recording() );

        if( liveStreamId() != -1 ) {
            wrapper.putInt( KEY_LIVE_STREAM_ID, liveStreamId() );
        }

        wrapper.putBoolean( KEY_WATCHED_STATUS, watched() );

        if( null != markWatchedUrl() && !"".equals( markWatchedUrl() ) ) {
            wrapper.putString( KEY_MARK_WATCHED_URL, markWatchedUrl() );
        }

        if( null != updateSavedBookmarkUrl() && !"".equals( updateSavedBookmarkUrl() ) ) {
            wrapper.putString( KEY_UPDATE_SAVED_BOOKMARK_URL, updateSavedBookmarkUrl() );
        }

        wrapper.putLong( KEY_BOOKMARK, bookmark() );

        if( null != inetref() && !"".equals( inetref() ) ) {
            wrapper.putString( KEY_INETREF, inetref() );
        }

        // TODO: fix this
        //wrapper.putParcelableArrayList( KEY_VALIDATION_ERRORS, validationErrors() );

        if( null != certification() && !"".equals( certification() ) ) {
            wrapper.putString( KEY_CERTIFICATION, certification() );
        }

        wrapper.putInt( KEY_PARENTAL_LEVEL, parentalLevel() );

        if( null != recordingGroup() && !"".equals( recordingGroup() ) ) {
            wrapper.putString( KEY_RECORDING_GROUP, recordingGroup() );
        }

        return wrapper;
    }

    public static MediaItemModel fromBundle( Bundle wrapper ) {

        if( null == wrapper ) {

            return null;
        }

        MediaItemModel media = MediaItemModel.create(
                wrapper.getInt( KEY_ID ),
                Media.valueOf( wrapper.getString( KEY_MEDIA ) ),
                ( wrapper.containsKey( KEY_TITLE ) ) ? wrapper.getString( KEY_TITLE ) : "",
                ( wrapper.containsKey( KEY_SUBTITLE ) ) ? wrapper.getString( KEY_SUBTITLE ) : "",
                ( wrapper.containsKey( KEY_DESCRIPTION ) ) ? wrapper.getString( KEY_DESCRIPTION ) : "",
                ( wrapper.containsKey( KEY_START_DATE ) ) ? new DateTime( wrapper.getLong( KEY_START_DATE ) ) : DateTime.now(),
                ( wrapper.containsKey( KEY_PROGRAM_FLAGS ) ) ? wrapper.getInt( KEY_PROGRAM_FLAGS ) : -1,
                ( wrapper.containsKey( KEY_SEASON ) ) ? wrapper.getInt( KEY_SEASON ) : -1,
                ( wrapper.containsKey( KEY_EPISODE ) ) ? wrapper.getInt( KEY_EPISODE ) : -1,
                ( wrapper.containsKey( KEY_STUDIO ) ) ? wrapper.getString( KEY_STUDIO ) : "",
                ( wrapper.containsKey( KEY_CAST_MEMBERS ) ) ? wrapper.getString( KEY_CAST_MEMBERS ) : "",
                ( wrapper.containsKey( KEY_CHARACTERS ) ) ? wrapper.getString( KEY_CHARACTERS ) : "",
                wrapper.getString( KEY_URL ),
                ( wrapper.containsKey( KEY_FANART_URL ) ) ? wrapper.getString( KEY_FANART_URL ) : "",
                ( wrapper.containsKey( KEY_COVERART_URL ) ) ?  wrapper.getString( KEY_COVERART_URL ) : "",
                ( wrapper.containsKey( KEY_BANNER_URL ) ) ? wrapper.getString( KEY_BANNER_URL ) : "",
                ( wrapper.containsKey( KEY_PREVIEW_URL ) ) ? wrapper.getString( KEY_PREVIEW_URL ) : "",
                wrapper.getString( KEY_CONTENT_TYPE ),
                wrapper.getLong( KEY_DURATION ),
                wrapper.getInt( KEY_LIVE_STREAM_PERCENT_COMPLETE ),
                wrapper.getBoolean( KEY_RECORDING ),
                ( wrapper.containsKey( KEY_LIVE_STREAM_ID ) ) ? wrapper.getInt( KEY_LIVE_STREAM_ID ) : -1,
                wrapper.getBoolean( KEY_WATCHED_STATUS ),
                ( wrapper.containsKey( KEY_MARK_WATCHED_URL ) ) ? wrapper.getString( KEY_MARK_WATCHED_URL ) : "",
                ( wrapper.containsKey( KEY_UPDATE_SAVED_BOOKMARK_URL ) ) ? wrapper.getString( KEY_UPDATE_SAVED_BOOKMARK_URL ) : "",
                wrapper.getLong( KEY_BOOKMARK ),
                ( wrapper.containsKey( KEY_INETREF ) ) ? wrapper.getString( KEY_INETREF ) : "",
                ( wrapper.containsKey( KEY_CERTIFICATION ) ) ? wrapper.getString( KEY_CERTIFICATION ) : "",
                wrapper.getInt( KEY_PARENTAL_LEVEL ),
                ( wrapper.containsKey( KEY_RECORDING_GROUP ) ) ? wrapper.getString( KEY_RECORDING_GROUP ) : "",
                Collections.emptyList()
        );

        return media;
    }

}
