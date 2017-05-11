package org.mythtv.android.data;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.entity.InputEntity;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.data.entity.ProgramEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

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
    protected static final List<InputEntity> FAKE_INPUTS = Collections.emptyList();

    protected ProgramEntity createFakeProgramEntity() {

        return ProgramEntity.create(
                FAKE_START_TIME, FAKE_END_TIME, FAKE_TITLE, FAKE_SUB_TITLE, FAKE_CATEGORY, FAKE_CATTYPE,
                FAKE_REPEAT, FAKE_VIDEOPROPS, FAKE_AUDIOPROPS, FAKE_SUBPROPS, FAKE_SERIESID, FAKE_PROGRAMID,
                FAKE_STARS, FAKE_FILESIZE, FAKE_LASTMODIFIED, FAKE_PROGRAMFLAGS, FAKE_FILENAME,
                FAKE_HOSTNAME, FAKE_AIRDATE, FAKE_DESCRIPTION, FAKE_INETREF, FAKE_SEASON, FAKE_EPISODE,
                FAKE_TOTALEPISODES, null, null, null, null );
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

        return EncoderEntity.create( FAKE_ID, FAKE_HOSTNAME, FAKE_LOCAL, FAKE_CONNECTED, FAKE_STATE, FAKE_SLEEP_STATUS, FAKE_LOW_ON_FREE_SPACE, FAKE_INPUTS, createFakeProgramEntity() );
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
