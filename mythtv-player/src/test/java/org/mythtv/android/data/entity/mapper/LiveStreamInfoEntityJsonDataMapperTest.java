package org.mythtv.android.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
public class LiveStreamInfoEntityJsonDataMapperTest extends ApplicationTestCase {

    private static final String JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO = "{\"LiveStreamInfo\": {\"Id\": \"128\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"2000000\", \"AudioBitrate\": \"128000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"434\", \"SegmentCount\": \"434\", \"PercentComplete\": \"100\", \"Created\": \"2015-10-17T03:03:30Z\", \"LastModified\": \"2015-10-17T03:03:30Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/recordings\\/2006_20151017003100.ts\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1280\", \"SourceHeight\": \"720\", \"AudioOnlyBitrate\": \"64000\"}}";
    private static final String JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_BAD = "{\"LiveStreamInfo\": {\"Id\": \"128\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"2000000\", \"AudioBitrate\": \"128000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"434\", \"SegmentCount\": \"434\", \"PercentComplete\": \"100\", \"Created\": \"2015-10-17T03:03:30Z\", \"LastModified\": \"2015-10-17T03:03:30Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/recordings\\/2006_20151017003100.ts\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1280\", \"SourceHeight\": \"720\", \"AudioOnlyBitrate\":";
    private static final String JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_LIST = "{\"LiveStreamInfoList\": {\"LiveStreamInfos\": [{\"Id\": \"128\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"2000000\", \"AudioBitrate\": \"128000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"434\", \"SegmentCount\": \"434\", \"PercentComplete\": \"100\", \"Created\": \"2015-10-17T03:03:30Z\", \"LastModified\": \"2015-10-17T03:03:30Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/recordings\\/2006_20151017003100.ts\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1280\", \"SourceHeight\": \"720\", \"AudioOnlyBitrate\": \"64000\"},{\"Id\": \"110\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"800000\", \"AudioBitrate\": \"64000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"1304\", \"SegmentCount\": \"1304\", \"PercentComplete\": \"100\", \"Created\": \"2015-04-27T03:56:34Z\", \"LastModified\": \"2015-04-27T03:56:34Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/Animal House.mkv.1280x720_800kV_64kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/Animal House.mkv.1280x720_800kV_64kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/videos\\/library\\/Movies\\/Animal House\\/Animal House.mkv\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1920\", \"SourceHeight\": \"1088\", \"AudioOnlyBitrate\": \"64000\"}]}}";
    private static final String JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_LIST_BAD = "{\"LiveStreamInfoList\": {\"LiveStreamInfos\": [{\"Id\": \"128\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"2000000\", \"AudioBitrate\": \"128000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"434\", \"SegmentCount\": \"434\", \"PercentComplete\": \"100\", \"Created\": \"2015-10-17T03:03:30Z\", \"LastModified\": \"2015-10-17T03:03:30Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/recordings\\/2006_20151017003100.ts\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1280\", \"SourceHeight\": \"720\", \"AudioOnlyBitrate\": \"64000\"},{\"Id\": \"110\", \"Width\": \"1280\", \"Height\": \"720\", \"Bitrate\": \"800000\", \"AudioBitrate\": \"64000\", \"SegmentSize\": \"4\", \"MaxSegments\": \"0\", \"StartSegment\": \"1\", \"CurrentSegment\": \"1304\", \"SegmentCount\": \"1304\", \"PercentComplete\": \"100\", \"Created\": \"2015-04-27T03:56:34Z\", \"LastModified\": \"2015-04-27T03:56:34Z\", \"RelativeURL\": \"\\/StorageGroup\\/Streaming\\/Animal House.mkv.1280x720_800kV_64kA.m3u8\", \"FullURL\": \"http:\\/\\/192.168.10.200:6544\\/StorageGroup\\/Streaming\\/Animal House.mkv.1280x720_800kV_64kA.m3u8\", \"StatusStr\": \"Completed\", \"StatusInt\": \"3\", \"StatusMessage\": \"Transcoding Completed\", \"SourceFile\": \"\\/var\\/lib\\/mythtv\\/videos\\/library\\/Movies\\/Animal House\\/Animal House.mkv\", \"SourceHost\": \"mythcenter\", \"SourceWidth\": \"1920\", \"SourceHeight\": \"1088\", \"AudioOnlyBitrate\": ";

    private LiveStreamInfoEntityJsonMapper LiveStreamInfoEntityJsonMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private Gson mockGson;


    @Before
    public void setUp() {

        LiveStreamInfoEntityJsonMapper = new LiveStreamInfoEntityJsonMapper( gson );

    }

    @Test
    public void testTransformLiveStreamInfoEntityHappyCase() {

        LiveStreamInfoEntity liveStreamInfoEntity = LiveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntity( JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO );

        assertThat( liveStreamInfoEntity.id(), is( 128 ) );
        assertThat( liveStreamInfoEntity.width(), is( 1280 ) );
        assertThat( liveStreamInfoEntity.height(), is( 720 ) );
        assertThat( liveStreamInfoEntity.bitrate(), is( 2000000 ) );
        assertThat( liveStreamInfoEntity.audioBitrate(), is( 128000 ) );
        assertThat( liveStreamInfoEntity.segmentSize(), is( 4 ) );
        assertThat( liveStreamInfoEntity.maxSegments(), is( 0 ) );
        assertThat( liveStreamInfoEntity.startSegment(), is( 1 ) );
        assertThat( liveStreamInfoEntity.currentSegment(), is( 434 ) );
        assertThat( liveStreamInfoEntity.segmentCount(), is( 434 ) );
        assertThat( liveStreamInfoEntity.percentComplete(), is( 100 ) );
        assertThat( liveStreamInfoEntity.created(), is( notNullValue() ) );
        assertThat( liveStreamInfoEntity.lastModified(), is( notNullValue() ) );
        assertThat( liveStreamInfoEntity.relativeUrl(), is( "/StorageGroup/Streaming/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8" ) );
        assertThat( liveStreamInfoEntity.fullUrl(), is( "http://192.168.10.200:6544/StorageGroup/Streaming/2006_20151017003100.ts.1280x720_2000kV_128kA.m3u8" ) );
        assertThat( liveStreamInfoEntity.statusString(), is( "Completed" ) );
        assertThat( liveStreamInfoEntity.statusInt(), is( 3 ) );
        assertThat( liveStreamInfoEntity.statusMessage(), is( "Transcoding Completed" ) );
        assertThat( liveStreamInfoEntity.sourceFile(), is( "/var/lib/mythtv/recordings/2006_20151017003100.ts" ) );
        assertThat( liveStreamInfoEntity.sourceHost(), is( "mythcenter" ) );
        assertThat( liveStreamInfoEntity.sourceWidth(), is( 1280 ) );
        assertThat( liveStreamInfoEntity.sourceHeight(), is( 720 ) );
        assertThat( liveStreamInfoEntity.audioOnlyBitrate(), is( 64000 ) );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformLiveStreamInfoEntityBadJson() {

        LiveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntity( JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_BAD );

    }

    @Test
    public void testTransformLiveStreamInfoEntityCollectionHappyCase() {

        Collection<LiveStreamInfoEntity> LiveStreamInfoEntityCollection = LiveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntityCollection( JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_LIST );

        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 0 ] ).id(), is( 128 ) );
        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 0 ] ).width(), is( 1280 ) );
        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 0 ] ).height(), is( 720 ) );
        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 1 ] ).id(), is( 110 ) );
        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 1 ] ).width(), is( 1280 ) );
        assertThat( ( (LiveStreamInfoEntity) LiveStreamInfoEntityCollection.toArray() [ 1 ] ).height(), is( 720 ) );
        assertThat( LiveStreamInfoEntityCollection.size(), is( 2 ) );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformLiveStreamInfoEntityCollectionBadJson() {

        LiveStreamInfoEntityJsonMapper.transformLiveStreamInfoEntityCollection( JSON_RESPONSE_DVR_GET_LIVE_STREAM_INFO_LIST_BAD );

    }

}
