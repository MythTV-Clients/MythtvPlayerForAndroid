package org.mythtv.android.mapper;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.mythtv.android.domain.LiveStreamInfo;
import org.mythtv.android.mapper.LiveStreamInfoModelDataMapper;
import org.mythtv.android.model.LiveStreamInfoModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by dmfrey on 10/17/15.
 */
public class LiveStreamInfoModelDataMapperTest extends TestCase {

    private static final int FAKE_ID = 1;
    private static final int FAKE_WIDTH = 1;
    private static final int FAKE_HEIGHT = 1;
    private static final int FAKE_BITRATE = 1;
    private static final int FAKE_AUDIO_BITRATE = 1;
    private static final int FAKE_SEGMENT_SIZE = 1;
    private static final int FAKE_MAX_SEGMENTS = 1;
    private static final int FAKE_START_SEGMENT = 1;
    private static final int FAKE_CURRENT_SEGMENT = 1;
    private static final int FAKE_SEGMENT_COUNT = 1;
    private static final int FAKE_PERCENT_COMPLETE = 1;
    private static final DateTime FAKE_CREATED = new DateTime();
    private static final DateTime FAKE_LAST_MODIFIED = new DateTime();
    private static final String FAKE_RELATIVE_URL = "fake relative url";
    private static final String FAKE_FULL_URL = "fake full url";
    private static final String FAKE_STATUS_STR = "fake status str";
    private static final int FAKE_STATUS_INT = 1;
    private static final String FAKE_STATUS_MESSAGE = "fake status message";
    private static final String FAKE_SOURCE_FILE = "fake source file";
    private static final String FAKE_SOURCE_HOST = "fake source host";
    private static final int FAKE_SOURCE_WIDTH = 1;
    private static final int FAKE_SOURCE_HEIGHT = 1;
    private static final int FAKE_AUDIO_ONLY_BITRATE = 1;

    private LiveStreamInfoModelDataMapper liveStreamInfoModelDataMapper;

    protected void setUp() throws Exception {
        super.setUp();

        liveStreamInfoModelDataMapper = new LiveStreamInfoModelDataMapper();

    }

    public void testTransformLiveStreamInfoModel() {

        LiveStreamInfo liveStreamInfo = createFakeLiveStreamInfo();
        LiveStreamInfoModel liveStreamInfoModel = liveStreamInfoModelDataMapper.transform( liveStreamInfo );

        assertThat( liveStreamInfo, is( instanceOf( LiveStreamInfo.class ) ) );
        assertThat( liveStreamInfo.getId(), is( FAKE_ID ) );
        assertThat( liveStreamInfo.getWidth(), is( FAKE_WIDTH ) );
        assertThat( liveStreamInfo.getHeight(), is( FAKE_HEIGHT ) );
        assertThat( liveStreamInfo.getBitrate(), is( FAKE_BITRATE ) );
        assertThat( liveStreamInfo.getAudioBitrate(), is( FAKE_AUDIO_BITRATE ) );
        assertThat( liveStreamInfo.getSegmentSize(), is( FAKE_SEGMENT_SIZE ) );
        assertThat( liveStreamInfo.getMaxSegments(), is( FAKE_MAX_SEGMENTS ) );
        assertThat( liveStreamInfo.getStartSegment(), is( FAKE_START_SEGMENT ) );
        assertThat( liveStreamInfo.getCurrentSegment(), is( FAKE_CURRENT_SEGMENT ) );
        assertThat( liveStreamInfo.getSegmentCount(), is( FAKE_SEGMENT_COUNT ) );
        assertThat( liveStreamInfo.getPercentComplete(), is( FAKE_PERCENT_COMPLETE ) );
        assertThat( liveStreamInfo.getCreated(), is( FAKE_CREATED ) );
        assertThat( liveStreamInfo.getLastModified(), is( FAKE_LAST_MODIFIED ) );
        assertThat( liveStreamInfo.getRelativeUrl(), is( FAKE_RELATIVE_URL ) );
        assertThat( liveStreamInfo.getFullUrl(), is( FAKE_FULL_URL ) );
        assertThat( liveStreamInfo.getStatusString(), is( FAKE_STATUS_STR ) );
        assertThat( liveStreamInfo.getStatusInt(), is( FAKE_STATUS_INT ) );
        assertThat( liveStreamInfo.getStatusMessage(), is( FAKE_STATUS_MESSAGE ) );
        assertThat( liveStreamInfo.getSourceFile(), is( FAKE_SOURCE_FILE ) );
        assertThat( liveStreamInfo.getSourceHost(), is( FAKE_SOURCE_HOST ) );
        assertThat( liveStreamInfo.getSourceWidth(), is( FAKE_SOURCE_WIDTH ) );
        assertThat( liveStreamInfo.getSourceHeight(), is( FAKE_SOURCE_HEIGHT ) );
        assertThat( liveStreamInfo.getAudioOnlyBitrate(), is( FAKE_AUDIO_ONLY_BITRATE ) );

    }

    public void testTransformLiveStreamInfoModelCollection() {

        LiveStreamInfo mockLiveStreamInfoOne = mock( LiveStreamInfo.class );
        LiveStreamInfo mockLiveStreamInfoTwo = mock( LiveStreamInfo.class );

        List<LiveStreamInfo> liveStreamInfoList = new ArrayList<>( 5 );
        liveStreamInfoList.add( mockLiveStreamInfoOne );
        liveStreamInfoList.add( mockLiveStreamInfoTwo );

        Collection<LiveStreamInfoModel> liveStreamInfoModelCollection = liveStreamInfoModelDataMapper.transform( liveStreamInfoList );

        assertThat( liveStreamInfoModelCollection.toArray()[ 0 ], is( instanceOf( LiveStreamInfoModel.class ) ) );
        assertThat( liveStreamInfoModelCollection.toArray()[ 1 ], is( instanceOf( LiveStreamInfoModel.class ) ) );
        assertThat( liveStreamInfoModelCollection.size(), is( 2 ) );

    }

    private LiveStreamInfo createFakeLiveStreamInfo() {

        LiveStreamInfo liveStreamInfo = new LiveStreamInfo();
        liveStreamInfo.setId( FAKE_ID );
        liveStreamInfo.setWidth( FAKE_WIDTH );
        liveStreamInfo.setHeight( FAKE_HEIGHT );
        liveStreamInfo.setBitrate( FAKE_BITRATE );
        liveStreamInfo.setAudioBitrate( FAKE_AUDIO_BITRATE );
        liveStreamInfo.setSegmentSize( FAKE_SEGMENT_SIZE );
        liveStreamInfo.setMaxSegments( FAKE_MAX_SEGMENTS );
        liveStreamInfo.setStartSegment( FAKE_START_SEGMENT );
        liveStreamInfo.setCurrentSegment( FAKE_CURRENT_SEGMENT );
        liveStreamInfo.setSegmentCount( FAKE_SEGMENT_COUNT );
        liveStreamInfo.setPercentComplete( FAKE_PERCENT_COMPLETE );
        liveStreamInfo.setCreated( FAKE_CREATED );
        liveStreamInfo.setLastModified( FAKE_LAST_MODIFIED );
        liveStreamInfo.setRelativeUrl( FAKE_RELATIVE_URL );
        liveStreamInfo.setFullUrl( FAKE_FULL_URL );
        liveStreamInfo.setStatusString( FAKE_STATUS_STR );
        liveStreamInfo.setStatusInt( FAKE_STATUS_INT );
        liveStreamInfo.setStatusMessage( FAKE_STATUS_MESSAGE );
        liveStreamInfo.setSourceFile( FAKE_SOURCE_FILE );
        liveStreamInfo.setSourceHost( FAKE_SOURCE_HOST );
        liveStreamInfo.setSourceWidth( FAKE_SOURCE_WIDTH );
        liveStreamInfo.setSourceHeight( FAKE_SOURCE_HEIGHT );
        liveStreamInfo.setAudioOnlyBitrate( FAKE_AUDIO_ONLY_BITRATE );

        return liveStreamInfo;
    }

}
