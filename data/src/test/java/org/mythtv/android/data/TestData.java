package org.mythtv.android.data;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.ArtworkEntity;
import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.data.entity.CastEntity;
import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.data.entity.ChannelInfoEntity;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.InputEntity;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.RecordingInfoEntity;
import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.Media;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 5/10/17.
 */

public abstract class TestData {

    protected static final int FAKE_RECORDED_ID = 999;

    protected static final DateTime FAKE_START_TIME = new DateTime();
    protected static final DateTime FAKE_END_TIME = new DateTime();
    protected static final String FAKE_TITLE = "fake title";
    protected static final String FAKE_SUB_TITLE = "fake sub title";
    protected static final String FAKE_CATEGORY = "fake category";
    protected static final String FAKE_CATTYPE = "fake catType";
    protected static final boolean FAKE_REPEAT = false;
    protected static final int FAKE_VIDEOPROPS = 1;
    protected static final int FAKE_AUDIOPROPS = 1;
    protected static final int FAKE_SUBPROPS = 1;
    protected static final String FAKE_SERIESID = "fake seriesId";
    protected static final String FAKE_PROGRAMID = "fake programId";
    protected static final double FAKE_STARS = 1.0;
    protected static final long FAKE_FILESIZE = 1L;
    protected static final DateTime FAKE_LASTMODIFIED = new DateTime();
    protected static final int FAKE_PROGRAMFLAGS = 1;
    protected static final String FAKE_FILENAME = "fake fileName";
    protected static final String FAKE_HOSTNAME = "fake hostName";
    protected static final String FAKE_AIRDATE = "fake airdate";
    protected static final String FAKE_DESCRIPTION = "fake description";
    protected static final String FAKE_INETREF = "fake inetref";
    protected static final int FAKE_SEASON = 1;
    protected static final int FAKE_EPISODE = 1;
    protected static final int FAKE_TOTALEPISODES = 1;

    protected static final int FAKE_ID = 1;
    protected static final int FAKE_WIDTH = 1;
    protected static final int FAKE_HEIGHT = 1;
    protected static final int FAKE_BITRATE = 1;
    protected static final int FAKE_AUDIO_BITRATE = 1;
    protected static final int FAKE_SEGMENT_SIZE = 1;
    protected static final int FAKE_MAX_SEGMENTS = 1;
    protected static final int FAKE_START_SEGMENT = 1;
    protected static final int FAKE_CURRENT_SEGMENT = 1;
    protected static final int FAKE_SEGMENT_COUNT = 1;
    protected static final int FAKE_PERCENT_COMPLETE = 1;
    protected static final DateTime FAKE_CREATED = new DateTime();
    protected static final DateTime FAKE_LAST_MODIFIED = new DateTime();
    protected static final String FAKE_RELATIVE_URL = "fake relative url";
    protected static final String FAKE_FULL_URL = "fake full url";
    protected static final String FAKE_STATUS_STR = "fake status str";
    protected static final int FAKE_STATUS_INT = 1;
    protected static final String FAKE_STATUS_MESSAGE = "fake status message";
    protected static final String FAKE_SOURCE_FILE = "fake source file";
    protected static final String FAKE_SOURCE_HOST = "fake source host";
    protected static final int FAKE_SOURCE_WIDTH = 1;
    protected static final int FAKE_SOURCE_HEIGHT = 1;
    protected static final int FAKE_AUDIO_ONLY_BITRATE = 1;

    protected static final boolean FAKE_LOCAL = false;
    protected static final boolean FAKE_CONNECTED = false;
    protected static final int FAKE_STATE = -1;
    protected static final int FAKE_SLEEP_STATUS = -1;
    protected static final boolean FAKE_LOW_ON_FREE_SPACE = false;

    protected static final long FAKE_BOOKMARK = 0;

    protected static final String FAKE_URL = "fake url";
    protected static final String FAKE_STORAGE_GROUP = "fake storage group";
    protected static final String FAKE_TYPE = "fake type";

    protected static final String FAKE_NAME = "fake name";
    protected static final String FAKE_CHARACTER_NAME = "fake character name";
    protected static final String FAKE_ROLE = "fake role";
    protected static final String FAKE_TRANSLATED_ROLE = "fake translated role";

    protected static final int FAKE_CHAN_ID = 1;
    protected static final String FAKE_CHAN_NAME = "fake chan name";
    protected static final String FAKE_CALL_SIGN = "fake call sign";
    protected static final String FAKE_ICON_URL = "fake icon url";
    protected static final String FAKE_CHANNEL_NAME = "fake channel name";
    protected static final int FAKE_MPLEX_ID = 1;
    protected static final int FAKE_SERVICE_ID = 1;
    protected static final int FAKE_ATSC_MAJOR_CHAN = 1;
    protected static final int FAKE_ATSC_MINOR_CHAN = 1;
    protected static final String FAKE_FORMAT = "fake format";
    protected static final String FAKE_FREQUENCY_ID = "fake frequency id";
    protected static final int FAKE_FINE_TUNE = 1;
    protected static final String FAKE_CHAN_FILTERS = "fake chan filters";
    protected static final int FAKE_SOURCE_ID = 1;
    protected static final int FAKE_INPUT_ID = 1;
    protected static final boolean FAKE_COMM_FREE = false;
    protected static final boolean FAKE_USE_EIT = false;
    protected static final boolean FAKE_VISIBLE = false;
    protected static final String FAKE_XMLTVID = "fake xmltvid";
    protected static final String FAKE_DEFAULT_AUTH = "fake default auth";

    protected static final int FAKE_STATUS = 1;
    protected static final int FAKE_PRIORITY = 1;
    protected static final DateTime FAKE_START_TS = DateTime.now();
    protected static final DateTime FAKE_END_TS = DateTime.now();
    protected static final int FAKE_RECORD_ID = 1;
    protected static final String FAKE_REC_GROUP = "fake rec group";
    protected static final String FAKE_PLAY_GROUP = "fake play group";
    protected static final int FAKE_REC_TYPE = 1;
    protected static final int FAKE_DUP_IN_TYPE = 1;
    protected static final int FAKE_DUP_METHOD = 1;
    protected static final int FAKE_ENCODER_ID = 1;
    protected static final String FAKE_ENCODER_NAME = "fake encoder name";
    protected static final String FAKE_PROFILE = "fake profile";

    protected static final int FAKE_CARD_ID = 1;
    protected static final String FAKE_INPUT_NAME = "fake input name";
    protected static final String FAKE_DISPLAY_NAME = "fake display name";
    protected static final boolean FAKE_QUICK_TUNE = false;
    protected static final int FAKE_RECORD_PRIORITY = 1;
    protected static final int FAKE_SCHEDULE_ORDER = 1;
    protected static final int FAKE_LIVE_TV_ORDER = 1;

    protected static final int FAKE_COUNT = 1;

    protected static final String FAKE_TAG_LINE = "fake tag line";
    protected static final String FAKE_DIRECTOR = "fake director";
    protected static final String FAKE_STUDIO = "fake studio";
    protected static final String FAKE_CERTIFICATION = "fake certification";
    protected static final int FAKE_COLLECTION_REF = 1;
    protected static final String FAKE_HOMEPAGE = "fake homepage";
    protected static final DateTime FAKE_RELEASE_DATE = DateTime.now();
    protected static final DateTime FAKE_ADD_DATE = DateTime.now();
    protected static final float FAKE_USER_RATING = 0.0f;
    protected static final int FAKE_LENGTH = 1;
    protected static final int FAKE_PLAY_COUNT = 1;
    protected static final int FAKE_PARENTAL_LEVEL = 1;
    protected static final boolean FAKE_WATCHED = false;
    protected static final boolean FAKE_PROCESSED = false;
    protected static final String FAKE_CONTENT_TYPE = "fake content type";
    protected static final String FAKE_HASH = "fake hash";
    protected static final String FAKE_COVERART = "fake coverart";
    protected static final String FAKE_FANART = "fake fanart";
    protected static final String FAKE_BANNER = "fake banner";
    protected static final String FAKE_SCREENSHOT = "fake screenshot";
    protected static final String FAKE_TRAILER = "fake trailer";

    protected static final Media FAKE_MEDIA = Media.PROGRAM;
    protected static final String FAKE_ARTWORK = "fake artwork";
    protected static final String FAKE_CAST_MEMBERS = "fake cast members";
    protected static final String FAKE_CHARACTERS = "fake characters";
    protected static final String FAKE_FANART_URL = "fake fanart url";
    protected static final String FAKE_COVERART_URL = "fake coverart url";
    protected static final String FAKE_BANNER_URL = "fake banner url";
    protected static final String FAKE_PREVIEW_URL = "fake preview url";
    protected static final long FAKE_DURATION = 1;
    protected static final boolean FAKE_RECORDING = false;
    protected static final int FAKE_LIVESTREAM_ID = 1;
    protected static final String FAKE_UPDATE_SAVED_BOOKMARK_URL = "fake update saved bookmark url";
    protected static final String FAKE_RECORDING_GROUP = "fake recording group";

    protected TitleInfoEntity createFakeTitleInfoEntity() {

        return TitleInfoEntity.create( FAKE_TITLE, FAKE_INETREF, FAKE_COUNT );
    }

    protected ProgramEntity createFakeProgramEntity() {

        ProgramEntity entity = ProgramEntity.create(
                FAKE_START_TIME, FAKE_END_TIME, FAKE_TITLE, FAKE_SUB_TITLE, FAKE_CATEGORY, FAKE_CATTYPE,
                FAKE_REPEAT, FAKE_VIDEOPROPS, FAKE_AUDIOPROPS, FAKE_SUBPROPS, FAKE_SERIESID, FAKE_PROGRAMID,
                FAKE_STARS, FAKE_FILESIZE, FAKE_LASTMODIFIED, FAKE_PROGRAMFLAGS, FAKE_FILENAME,
                FAKE_HOSTNAME, FAKE_AIRDATE, FAKE_DESCRIPTION, FAKE_INETREF, FAKE_SEASON, FAKE_EPISODE,
                FAKE_TOTALEPISODES, createFakeChannelInfoEntity(), createFakeRecordingInfoEntity(),
                createFakeArtworkEntity(), createFakeCastEntity() );

        entity.setBookmark( FAKE_BOOKMARK );
        entity.setLiveStreamInfoEntity( createFakeLiveStreamInfoEntity() );

        return entity;
    }

    protected ChannelInfoEntity createFakeChannelInfoEntity() {

        return ChannelInfoEntity.create(
                FAKE_CHAN_ID, FAKE_CHAN_NAME, FAKE_CALL_SIGN, FAKE_ICON_URL, FAKE_CHANNEL_NAME,
                FAKE_MPLEX_ID, FAKE_SERVICE_ID, FAKE_ATSC_MAJOR_CHAN, FAKE_ATSC_MINOR_CHAN,
                FAKE_FORMAT, FAKE_FREQUENCY_ID, FAKE_FINE_TUNE, FAKE_CHAN_FILTERS, FAKE_SOURCE_ID,
                FAKE_INPUT_ID, FAKE_COMM_FREE, FAKE_USE_EIT, FAKE_VISIBLE, FAKE_XMLTVID,
                FAKE_DEFAULT_AUTH, null
        );
    }

    protected RecordingInfoEntity createFakeRecordingInfoEntity() {

        return RecordingInfoEntity.create(
                String.valueOf( FAKE_RECORDED_ID ), FAKE_STATUS, FAKE_PRIORITY, FAKE_START_TS, FAKE_END_TS,
                FAKE_RECORD_ID, FAKE_REC_GROUP, FAKE_PLAY_GROUP, FAKE_STORAGE_GROUP, FAKE_REC_TYPE,
                FAKE_DUP_IN_TYPE, FAKE_DUP_METHOD, FAKE_ENCODER_ID, FAKE_ENCODER_NAME, FAKE_PROFILE
        );
    }

    protected ArtworkEntity createFakeArtworkEntity() {

        return ArtworkEntity.create( Collections.singletonList( createFakeArtworkInfoEntity() ) );
    }

    protected ArtworkInfoEntity createFakeArtworkInfoEntity() {

        return ArtworkInfoEntity.create( FAKE_URL, FAKE_FILENAME, FAKE_STORAGE_GROUP, FAKE_TYPE );
    }

    protected CastEntity createFakeCastEntity() {

        return CastEntity.create( Collections.singletonList( createFakeCastMemberEntity() ) );
    }

    protected CastMemberEntity createFakeCastMemberEntity() {

        return CastMemberEntity.create( FAKE_NAME, FAKE_CHARACTER_NAME, FAKE_ROLE, FAKE_TRANSLATED_ROLE );
    }

    protected LiveStreamInfoEntity createFakeLiveStreamInfoEntity() {

        return LiveStreamInfoEntity.create(
                FAKE_ID, FAKE_WIDTH, FAKE_HEIGHT, FAKE_BITRATE, FAKE_AUDIO_BITRATE,
                FAKE_SEGMENT_SIZE, FAKE_MAX_SEGMENTS, FAKE_START_SEGMENT,  FAKE_CURRENT_SEGMENT,
                FAKE_SEGMENT_COUNT, FAKE_PERCENT_COMPLETE, FAKE_CREATED, FAKE_LAST_MODIFIED,
                FAKE_RELATIVE_URL, FAKE_FULL_URL, FAKE_STATUS_STR, FAKE_STATUS_INT,
                FAKE_STATUS_MESSAGE, FAKE_SOURCE_FILE, FAKE_SOURCE_HOST, FAKE_SOURCE_WIDTH,
                FAKE_SOURCE_HEIGHT, FAKE_AUDIO_ONLY_BITRATE );
    }

    protected EncoderEntity createFakeEncoderEntity() {

        return EncoderEntity.create(
                FAKE_ID, FAKE_HOSTNAME, FAKE_LOCAL, FAKE_CONNECTED, FAKE_STATE, FAKE_SLEEP_STATUS,
                FAKE_LOW_ON_FREE_SPACE, Collections.singletonList( createFakeInputEntity() ),
                createFakeProgramEntity() );
    }

    protected InputEntity createFakeInputEntity() {

        return InputEntity.create(
                FAKE_ID, FAKE_CARD_ID, FAKE_SOURCE_ID, FAKE_INPUT_NAME, FAKE_DISPLAY_NAME,
                FAKE_QUICK_TUNE, FAKE_RECORD_PRIORITY, FAKE_SCHEDULE_ORDER, FAKE_LIVE_TV_ORDER );
    }

    protected VideoMetadataInfoEntity createFakeVideoMetadataInfoEntity() {

        VideoMetadataInfoEntity entity = VideoMetadataInfoEntity.create(
                FAKE_ID, FAKE_TITLE, FAKE_SUB_TITLE, FAKE_TAG_LINE, FAKE_DIRECTOR, FAKE_STUDIO,
                FAKE_DESCRIPTION, FAKE_CERTIFICATION, FAKE_INETREF, FAKE_COLLECTION_REF,
                FAKE_HOMEPAGE, FAKE_RELEASE_DATE, FAKE_ADD_DATE, FAKE_USER_RATING, FAKE_LENGTH,
                FAKE_PLAY_COUNT, FAKE_SEASON, FAKE_EPISODE, FAKE_PARENTAL_LEVEL, FAKE_VISIBLE,
                FAKE_WATCHED, FAKE_PROCESSED, FAKE_CONTENT_TYPE, FAKE_FILENAME, FAKE_HASH,
                FAKE_HOSTNAME, FAKE_COVERART, FAKE_FANART, FAKE_BANNER, FAKE_SCREENSHOT,
                FAKE_TRAILER, createFakeArtworkEntity(), createFakeCastEntity()
        );

        entity.setLiveStreamInfoEntity( createFakeLiveStreamInfoEntity() );

        return entity;
    };

    protected SeriesEntity createFakeSeriesEntity() {

        return SeriesEntity.create( FAKE_TITLE, FAKE_MEDIA, FAKE_ARTWORK, FAKE_COUNT, FAKE_INETREF );
    }

    protected MediaItemEntity createFakeMediaItemEntity() {

        return MediaItemEntity.create(
                FAKE_ID, FAKE_MEDIA, FAKE_TITLE, FAKE_SUB_TITLE, FAKE_DESCRIPTION, FAKE_START_TIME,
                FAKE_PROGRAMFLAGS, FAKE_SEASON, FAKE_EPISODE, FAKE_STUDIO, FAKE_CAST_MEMBERS,
                FAKE_CHARACTERS, FAKE_URL, FAKE_FANART_URL, FAKE_COVERART_URL, FAKE_BANNER_URL,
                FAKE_PREVIEW_URL, FAKE_CONTENT_TYPE, FAKE_DURATION, FAKE_PERCENT_COMPLETE,
                FAKE_RECORDING, FAKE_LIVESTREAM_ID, FAKE_WATCHED, FAKE_UPDATE_SAVED_BOOKMARK_URL,
                FAKE_BOOKMARK, FAKE_INETREF, FAKE_CERTIFICATION, FAKE_PARENTAL_LEVEL,
                FAKE_RECORDING_GROUP, Collections.emptyList()
        );
    }

    protected String getUrlContents( String theUrl ) {

        StringBuilder content = new StringBuilder();

        try {

            // create a url object
            URL url = getClass().getClassLoader().getResource( theUrl );;

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( urlConnection.getInputStream() ) );

            String line;

            // read from the urlconnection via the bufferedreader
            while( ( line = bufferedReader.readLine() ) != null ) {

                content.append( line + "\n" );
            }

            bufferedReader.close();

        } catch( Exception e ) {
            e.printStackTrace();
        }

        return content.toString();
    }

}
