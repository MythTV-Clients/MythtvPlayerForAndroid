package org.mythtv.android.data.entity.mapper;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.domain.LiveStreamInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by dmfrey on 10/17/15.
 */
public class LiveStreamInfoEntityDataMapperTest extends ApplicationTestCase {

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

    @Test
    public void testTransformLiveStreamInfoEntity() {

        LiveStreamInfoEntity liveStreamInfoEntity = createFakeLiveStreamInfoEntity();
        LiveStreamInfo liveStreamInfo = LiveStreamInfoEntityDataMapper.transform( liveStreamInfoEntity );

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

    @Test
    public void testTransformLiveStreamInfoEntityCollection() {

        LiveStreamInfoEntity mockLiveStreamInfoEntityOne = mock( LiveStreamInfoEntity.class );
        LiveStreamInfoEntity mockLiveStreamInfoEntityTwo = mock( LiveStreamInfoEntity.class );

        List<LiveStreamInfoEntity> liveStreamInfoEntityList = new ArrayList<>( 5 );
        liveStreamInfoEntityList.add( mockLiveStreamInfoEntityOne );
        liveStreamInfoEntityList.add( mockLiveStreamInfoEntityTwo );

        Collection<LiveStreamInfo> liveStreamInfoCollection = LiveStreamInfoEntityDataMapper.transform( liveStreamInfoEntityList );

        assertThat( liveStreamInfoCollection.toArray()[ 0 ], is( instanceOf( LiveStreamInfo.class ) ) );
        assertThat( liveStreamInfoCollection.toArray()[ 1 ], is( instanceOf( LiveStreamInfo.class ) ) );
        assertThat( liveStreamInfoCollection.size(), is( 2 ) );

    }

    private LiveStreamInfoEntity createFakeLiveStreamInfoEntity() {

        LiveStreamInfoEntity liveStreamInfoEntity = new LiveStreamInfoEntity();
        liveStreamInfoEntity.setId( FAKE_ID );
        liveStreamInfoEntity.setWidth( FAKE_WIDTH );
        liveStreamInfoEntity.setHeight(FAKE_HEIGHT );
        liveStreamInfoEntity.setBitrate( FAKE_BITRATE );
        liveStreamInfoEntity.setAudioBitrate( FAKE_AUDIO_BITRATE );
        liveStreamInfoEntity.setSegmentSize( FAKE_SEGMENT_SIZE );
        liveStreamInfoEntity.setMaxSegments( FAKE_MAX_SEGMENTS );
        liveStreamInfoEntity.setStartSegment( FAKE_START_SEGMENT );
        liveStreamInfoEntity.setCurrentSegment( FAKE_CURRENT_SEGMENT );
        liveStreamInfoEntity.setSegmentCount( FAKE_SEGMENT_COUNT );
        liveStreamInfoEntity.setPercentComplete( FAKE_PERCENT_COMPLETE );
        liveStreamInfoEntity.setCreated( FAKE_CREATED );
        liveStreamInfoEntity.setLastModified( FAKE_LAST_MODIFIED );
        liveStreamInfoEntity.setRelativeUrl( FAKE_RELATIVE_URL );
        liveStreamInfoEntity.setFullUrl( FAKE_FULL_URL );
        liveStreamInfoEntity.setStatusString( FAKE_STATUS_STR );
        liveStreamInfoEntity.setStatusInt( FAKE_STATUS_INT );
        liveStreamInfoEntity.setStatusMessage( FAKE_STATUS_MESSAGE );
        liveStreamInfoEntity.setSourceFile( FAKE_SOURCE_FILE );
        liveStreamInfoEntity.setSourceHost( FAKE_SOURCE_HOST );
        liveStreamInfoEntity.setSourceWidth( FAKE_SOURCE_WIDTH );
        liveStreamInfoEntity.setSourceHeight( FAKE_SOURCE_HEIGHT );
        liveStreamInfoEntity.setAudioOnlyBitrate( FAKE_AUDIO_ONLY_BITRATE );

        return liveStreamInfoEntity;
    }

}
