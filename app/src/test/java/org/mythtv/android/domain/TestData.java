package org.mythtv.android.domain;

import org.joda.time.DateTime;

import java.util.Collections;

/**
 *
 * @author dmfrey
 *
 * Created on 5/15/17.
 */

public class TestData {

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

    protected MediaItem createFakeMediaItem() {

        return MediaItem.create(
                FAKE_ID, FAKE_MEDIA, FAKE_TITLE, FAKE_SUB_TITLE, FAKE_DESCRIPTION, FAKE_START_TIME,
                FAKE_PROGRAMFLAGS, FAKE_SEASON, FAKE_EPISODE, FAKE_STUDIO, FAKE_CAST_MEMBERS,
                FAKE_CHARACTERS, FAKE_URL, FAKE_FANART_URL, FAKE_COVERART_URL, FAKE_BANNER_URL,
                FAKE_PREVIEW_URL, FAKE_CONTENT_TYPE, FAKE_DURATION, FAKE_PERCENT_COMPLETE,
                FAKE_RECORDING, FAKE_LIVESTREAM_ID, FAKE_WATCHED, FAKE_UPDATE_SAVED_BOOKMARK_URL,
                FAKE_BOOKMARK, FAKE_INETREF, FAKE_CERTIFICATION, FAKE_PARENTAL_LEVEL,
                FAKE_RECORDING_GROUP, Collections.emptyList()
        );
    }

    protected Series createFakeSeries() {

        return Series.create( FAKE_TITLE, Media.PROGRAM, FAKE_ARTWORK, FAKE_COUNT, FAKE_INETREF );
    }

    protected Encoder createFakeEncoder() {

        return Encoder.create(
                FAKE_ID, FAKE_INPUT_NAME, FAKE_TITLE, FAKE_DESCRIPTION, FAKE_STATE );
    }

}
