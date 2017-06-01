package org.mythtv.android.data.entity.mapper;

import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
@RunWith( MockitoJUnitRunner.class )
public class LiveStreamInfoEntityJsonDataMapperTest {

    private static final String JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO = "{\"LiveStreamInfo\": {\"Id\": \"128\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"2000000\", \"AudioBitrate\": \"128000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"434\", \"SegmentCount\": \"434\", \"PercentComplete\": \"100\", \"Created\": \"2015-10-17T03:03:30Z\", \"LastModified\": \"2015-10-17T03:03:30Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/recordings\\/2006_20151017003100.ts\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1280\", \"SourceHeight\": \"720\", \"AudioOnlyBitrate\": \"64000\"}}";
    private static final String JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_BAD = "{\"LiveStreamInfo\": {\"Id\": \"128\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"2000000\", \"AudioBitrate\": \"128000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"434\", \"SegmentCount\": \"434\", \"PercentComplete\": \"100\", \"Created\": \"2015-10-17T03:03:30Z\", \"LastModified\": \"2015-10-17T03:03:30Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/recordings\\/2006_20151017003100.ts\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1280\", \"SourceHeight\": \"720\", \"AudioOnlyBitrate\":";
    private static final String JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_LIST = "{\"LiveStreamInfoList\": {\"LiveStreamInfos\": [{\"Id\": \"128\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"2000000\", \"AudioBitrate\": \"128000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"434\", \"SegmentCount\": \"434\", \"PercentComplete\": \"100\", \"Created\": \"2015-10-17T03:03:30Z\", \"LastModified\": \"2015-10-17T03:03:30Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/recordings\\/2006_20151017003100.ts\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1280\", \"SourceHeight\": \"720\", \"AudioOnlyBitrate\": \"64000\"},{\"Id\": \"110\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"800000\", \"AudioBitrate\": \"64000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"1304\", \"SegmentCount\": \"1304\", \"PercentComplete\": \"100\", \"Created\": \"2015-04-27T03:56:34Z\", \"LastModified\": \"2015-04-27T03:56:34Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/Animal House.mkv.1280x720_800kV_64kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/Animal House.mkv.1280x720_800kV_64kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/videos\\/library\\/Movies\\/Animal House\\/Animal House.mkv\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1920\", \"SourceHeight\": \"1088\", \"AudioOnlyBitrate\": \"64000\"}]}}";
    private static final String JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_LIST_BAD = "{\"LiveStreamInfoList\": {\"LiveStreamInfos\": [{\"Id\": \"128\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"2000000\", \"AudioBitrate\": \"128000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"434\", \"SegmentCount\": \"434\", \"PercentComplete\": \"100\", \"Created\": \"2015-10-17T03:03:30Z\", \"LastModified\": \"2015-10-17T03:03:30Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/recordings\\/2006_20151017003100.ts\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1280\", \"SourceHeight\": \"720\", \"AudioOnlyBitrate\": \"64000\"},{\"Id\": \"110\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"800000\", \"AudioBitrate\": \"64000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"1304\", \"SegmentCount\": \"1304\", \"PercentComplete\": \"100\", \"Created\": \"2015-04-27T03:56:34Z\", \"LastModified\": \"2015-04-27T03:56:34Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/Animal House.mkv.1280x720_800kV_64kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/Animal House.mkv.1280x720_800kV_64kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/videos\\/library\\/Movies\\/Animal House\\/Animal House.mkv\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1920\", \"SourceHeight\": \"1088\", \"AudioOnlyBitrate\": ";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private LiveStreamInfoEntityJsonMapper LiveStreamInfoEntityJsonMapper;

    @Before
    public void setUp() {

        LiveStreamInfoEntityJsonMapper = new LiveStreamInfoEntityJsonMapper();

    }

    @Test
    public void testTransformLiveStreamInfoEntityHappyCase() {

        LiveStreamInfoEntity liveStreamInfoEntity = LiveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntity( JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO );

        assertThat( liveStreamInfoEntity.id() ).isEqualTo( 128 );
        assertThat( liveStreamInfoEntity.width() ).isEqualTo( 1280 );
        assertThat( liveStreamInfoEntity.height() ).isEqualTo( 720 );
        assertThat( liveStreamInfoEntity.bitrate() ).isEqualTo( 2000000 );
        assertThat( liveStreamInfoEntity.audioBitrate() ).isEqualTo( 128000 );
        assertThat( liveStreamInfoEntity.segmentSize() ).isEqualTo( 4 );
        assertThat( liveStreamInfoEntity.maxSegments() ).isEqualTo( 0 );
        assertThat( liveStreamInfoEntity.startSegment() ).isEqualTo( 1 );
        assertThat( liveStreamInfoEntity.currentSegment() ).isEqualTo( 434 );
        assertThat( liveStreamInfoEntity.segmentCount() ).isEqualTo( 434 );
        assertThat( liveStreamInfoEntity.percentComplete() ).isEqualTo( 100 );
        assertThat( liveStreamInfoEntity.created() ).isNotNull();
        assertThat( liveStreamInfoEntity.lastModified() ).isNotNull();
        assertThat( liveStreamInfoEntity.relativeUrl() ).isEqualTo( "/StorageGroup/Streaming/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8" );
        assertThat( liveStreamInfoEntity.fullUrl() ).isEqualTo( "http://192.168.10.200:6544/StorageGroup/Streaming/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8" );
        assertThat( liveStreamInfoEntity.statusString() ).isEqualTo( "Completed" );
        assertThat( liveStreamInfoEntity.statusInt() ).isEqualTo( 3 );
        assertThat( liveStreamInfoEntity.statusMessage() ).isEqualTo( "Transcoding Completed" );
        assertThat( liveStreamInfoEntity.sourceFile() ).isEqualTo( "/var/lib/mythtv/recordings/2006_20151017003100.ts" );
        assertThat( liveStreamInfoEntity.sourceHost() ).isEqualTo( "mythcenter" );
        assertThat( liveStreamInfoEntity.sourceWidth() ).isEqualTo( 1280 );
        assertThat( liveStreamInfoEntity.sourceHeight() ).isEqualTo( 720 );
        assertThat( liveStreamInfoEntity.audioOnlyBitrate() ).isEqualTo( 64000 );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformLiveStreamInfoEntityBadJson() {

        LiveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntity( JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_BAD );

    }

    @Test
    public void testTransformLiveStreamInfoEntityCollectionHappyCase() {

        Collection<LiveStreamInfoEntity> LiveStreamInfoEntityCollection = LiveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntityCollection( JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_LIST );

        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 0 ] ).id() ).isEqualTo( 128 );
        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 0 ] ).width() ).isEqualTo( 1280 );
        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 0 ] ).height() ).isEqualTo( 720 );
        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 1 ] ).id() ).isEqualTo( 110 );
        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 1 ] ).width() ).isEqualTo( 1280 );
        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 1 ] ).height() ).isEqualTo( 720 );
        assertThat( LiveStreamInfoEntityCollection.size() ).isEqualTo( 2 );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformLiveStreamInfoEntityCollectionBadJson() {

        LiveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntityCollection( JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_LIST_BAD );

    }

}
