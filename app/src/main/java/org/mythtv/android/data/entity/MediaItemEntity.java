package org.mythtv.android.data.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.joda.time.DateTime;
import org.mythtv.android.domain.Media;

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
public abstract class MediaItemEntity {

    private static final String INTEGER_KEY = "INTEGER";
    private static final String TEXT_KEY = "TEXT";
    
    public static final String TABLE_NAME = "media_item";
    public static final String CREATE_TABLE;
    public static final String DROP_TABLE;
    public static final String SQL_SELECT_MATCH = TABLE_NAME + " MATCH ?";
    public static final String SQL_DELETE_ALL = "delete from " + TABLE_NAME + " where type = ?";
    public static final String SQL_INSERT = "insert into " + TABLE_NAME + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
    public static final String FIELD_WATCHED_STATUS = "watched_status";
    public static final String FIELD_MARK_WATCHED_URL = "mark_watched_url";
    public static final String FIELD_UPDATE_SAVED_BOOKMARK_URL = "update_saved_bookmark_url";
    public static final String FIELD_BOOKMARK = "bookmark";
    public static final String FIELD_INETREF = "inetref";
    public static final String FIELD_CERTIFICATION = "certification";
    public static final String FIELD_PARENTAL_LEVEL = "parental_level";
    public static final String FIELD_RECORDING_GROUP = "recording_group";

    static {

        String createTable = ("CREATE VIRTUAL TABLE " + TABLE_NAME + " using fts3 (") +
                FIELD_ID + " " + INTEGER_KEY + ", " +
                FIELD_MEDIA + " " + TEXT_KEY + ", " +
                FIELD_TITLE + " " + TEXT_KEY + ", " +
                FIELD_SUBTITLE + " " + TEXT_KEY + ", " +
                FIELD_DESCRIPTION + " " + TEXT_KEY + ", " +
                FIELD_START_DATE + " " + INTEGER_KEY + ", " +
                FIELD_PROGRAM_FLAGS + " " + INTEGER_KEY + ", " +
                FIELD_SEASON + " " + INTEGER_KEY + ", " +
                FIELD_EPISODE + " " + INTEGER_KEY + ", " +
                FIELD_STUDIO + " " + TEXT_KEY + ", " +
                FIELD_CAST_MEMBERS + " " + TEXT_KEY + ", " +
                FIELD_CHARACTERS + " " + TEXT_KEY + ", " +
                FIELD_URL + " " + TEXT_KEY + ", " +
                FIELD_FANART_URL + " " + TEXT_KEY + ", " +
                FIELD_COVERART_URL + " " + TEXT_KEY + ", " +
                FIELD_BANNER_URL + " " + TEXT_KEY + ", " +
                FIELD_PREVIEW_URL + " " + TEXT_KEY + ", " +
                FIELD_CONTENT_TYPE + " " + TEXT_KEY + ", " +
                FIELD_DURATION + " " + INTEGER_KEY + ", " +
                FIELD_RECORDING + " " + INTEGER_KEY + ", " +
                FIELD_LIVE_STREAM_PERCENT_COMPLETE + " " + INTEGER_KEY + ", " +
                FIELD_LIVE_STREAM_ID + " " + INTEGER_KEY + ", " +
                FIELD_WATCHED_STATUS + " " + INTEGER_KEY + ", " +
                FIELD_MARK_WATCHED_URL + " " + TEXT_KEY + ", " +
                FIELD_UPDATE_SAVED_BOOKMARK_URL + " " + TEXT_KEY + ", " +
                FIELD_BOOKMARK + " " + INTEGER_KEY + ", " +
                FIELD_INETREF + " " + TEXT_KEY + ", " +
                FIELD_CERTIFICATION + " " + TEXT_KEY + ", " +
                FIELD_PARENTAL_LEVEL + " " + INTEGER_KEY + ", " +
                FIELD_RECORDING_GROUP + " " + TEXT_KEY +
                ");";

        CREATE_TABLE = createTable;

        DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

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

    public static MediaItemEntity create( int id, Media media, String title, String subTitle, String description, DateTime startDate, int programFlags, int season, int episode, String studio,
                                          String castMembers, String characters, String url, String fanartUrl, String coverartUrl, String bannerUrl, String previewUrl, String contentType,
                                          long duration, int percentComplete, boolean recording, int liveStreamId,
                                          boolean watched, String markWatchedUrl, String updateSavedBookmarkUrl, long bookmark, String inetref,
                                          String certification, int parentalLevel, String recordingGroup ) {

        return new AutoValue_MediaItemEntity( id, media, title, subTitle, description, startDate, programFlags, season, episode, studio,
                castMembers, characters, url, fanartUrl, coverartUrl, bannerUrl, previewUrl, contentType,
                duration, percentComplete, recording, liveStreamId,
                watched, markWatchedUrl, updateSavedBookmarkUrl, bookmark, inetref,
                certification, parentalLevel, recordingGroup );
    }

    public static TypeAdapter<MediaItemEntity> typeAdapter( Gson gson ) {

        return new AutoValue_MediaItemEntity.GsonTypeAdapter( gson );
    }

}
