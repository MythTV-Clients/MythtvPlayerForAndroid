package org.mythtv.android.data.entity.mapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;

import java.io.StringReader;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 9/5/16.
 */

public class MediaItemDataMapperTest extends ApplicationTestCase {

    private static final String JSON_RESPONSE_DVR_GET_RECORDED = "{\"Program\": {\"StartTime\": \"2015-09-08T11:30:00Z\", \"EndTime\": \"2015-09-08T12:00:00Z\", \"Title\": \"Star Wars: Droid Tales\", \"SubTitle\": \"Mission to Mos Eisley\", \"Category\": \"Fantasy\", \"CatType\": \"series\", \"Repeat\": \"false\", \"VideoProps\": \"11\", \"AudioProps\": \"0\", \"SubProps\": \"1\", \"SeriesId\": \"EP02206828\", \"ProgramId\": \"EP022068280003\", \"Stars\": \"0\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"ProgramFlags\": \"5\", \"Airdate\": \"2015-09-07\", \"Description\": \"C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped.\", \"Inetref\": \"ttvdb.py_298171\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2289\", \"ChanNum\": \"1289\", \"CallSign\": \"DXDHD\", \"IconURL\": \"\", \"ChannelName\": \"Disney XD HD\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1289\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1289\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"60006\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3120\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-08T11:30:00Z\", \"EndTs\": \"2015-09-08T12:00:00Z\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"RecordId\": \"112\", \"RecGroup\": \"Default\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"Default\", \"RecType\": \"0\", \"DupInType\": \"15\", \"DupMethod\": \"6\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}}";
    private static final String JSON_RESPONSE_DVR_GET_RECORDED_NULL_STARTTS = "{\"Program\": {\"StartTime\": \"2015-09-08T11:30:00Z\", \"EndTime\": \"2015-09-08T12:00:00Z\", \"Title\": \"Star Wars: Droid Tales\", \"SubTitle\": \"Mission to Mos Eisley\", \"Category\": \"Fantasy\", \"CatType\": \"series\", \"Repeat\": \"false\", \"VideoProps\": \"11\", \"AudioProps\": \"0\", \"SubProps\": \"1\", \"SeriesId\": \"EP02206828\", \"ProgramId\": \"EP022068280003\", \"Stars\": \"0\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"ProgramFlags\": \"5\", \"Airdate\": \"2015-09-07\", \"Description\": \"C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped.\", \"Inetref\": \"ttvdb.py_298171\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2289\", \"ChanNum\": \"1289\", \"CallSign\": \"DXDHD\", \"IconURL\": \"\", \"ChannelName\": \"Disney XD HD\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1289\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1289\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"60006\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3120\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"\", \"EndTs\": \"2015-09-08T12:00:00Z\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"RecordId\": \"112\", \"RecGroup\": \"Default\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"Default\", \"RecType\": \"0\", \"DupInType\": \"15\", \"DupMethod\": \"6\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}}";
    private static final String JSON_RESPONSE_DVR_GET_RECORDED_NULL_RECORDEDID = "{\"Program\": {\"StartTime\": \"2015-09-08T11:30:00Z\", \"EndTime\": \"2015-09-08T12:00:00Z\", \"Title\": \"Star Wars: Droid Tales\", \"SubTitle\": \"Mission to Mos Eisley\", \"Category\": \"Fantasy\", \"CatType\": \"series\", \"Repeat\": \"false\", \"VideoProps\": \"11\", \"AudioProps\": \"0\", \"SubProps\": \"1\", \"SeriesId\": \"EP02206828\", \"ProgramId\": \"EP022068280003\", \"Stars\": \"0\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"ProgramFlags\": \"5\", \"Airdate\": \"2015-09-07\", \"Description\": \"C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped.\", \"Inetref\": \"ttvdb.py_298171\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2289\", \"ChanNum\": \"1289\", \"CallSign\": \"DXDHD\", \"IconURL\": \"\", \"ChannelName\": \"Disney XD HD\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1289\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1289\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"60006\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-08T11:30:00Z\", \"EndTs\": \"2015-09-08T12:00:00Z\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"RecordId\": \"112\", \"RecGroup\": \"Default\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"Default\", \"RecType\": \"0\", \"DupInType\": \"15\", \"DupMethod\": \"6\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}}";
    private static final String JSON_RESPONSE_VIDEO_GET_VIDEO = "{\"VideoMetadataInfo\":{\"Id\": \"1\", \"Title\": \"big buck bunny 1080p stereo\", \"SubTitle\": \"\", \"Tagline\": \"\", \"Director\": \"Unknown\", \"Studio\": \"\", \"Description\": \"None\", \"Certification\": \"NR\", \"Inetref\": \"00000000\", \"Collectionref\": \"-1\", \"HomePage\": \"\", \"ReleaseDate\": \"\", \"AddDate\": \"2015-01-05T05:00:00Z\", \"UserRating\": \"0\", \"Length\": \"0\", \"PlayCount\": \"0\", \"Season\": \"0\", \"Episode\": \"0\", \"ParentalLevel\": \"1\", \"Visible\": \"true\", \"Watched\": \"false\", \"Processed\": \"true\", \"ContentType\": \"MOVIE\", \"FileName\": \"library\\/Blender\\/big_buck_bunny_1080p_stereo.ogg\", \"Hash\": \"63ad35476c29214c\", \"HostName\": \"mythcenter\", \"Coverart\": \"\", \"Fanart\": \"\", \"Banner\": \"\", \"Screenshot\": \"\", \"Trailer\": \"\", \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}}";

    private ProgramEntityJsonMapper programEntityJsonMapper;
    private VideoMetadataInfoEntityJsonMapper videoMetadataInfoEntityJsonMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        programEntityJsonMapper = new ProgramEntityJsonMapper( gson );
        videoMetadataInfoEntityJsonMapper = new VideoMetadataInfoEntityJsonMapper( gson );

    }

    @Test
    public void testTransformProgram() throws Exception {

        ProgramEntity programEntity = programEntityJsonMapper.transformProgramEntity( new StringReader( JSON_RESPONSE_DVR_GET_RECORDED ) );
        assertThat( programEntity, not( nullValue() ) );

        MediaItem mediaItem = MediaItemDataMapper.transform( programEntity );
        assertThat( mediaItem, not( nullValue() ) );
        assertThat( mediaItem.getId(), is( equalTo( 3120 ) ) );
        assertThat( mediaItem.getMedia(), is( equalTo( Media.PROGRAM ) ) );
        assertThat( mediaItem.getTitle(), is( equalTo( "Star Wars: Droid Tales" ) ) );
        assertThat( mediaItem.getSubTitle(), is( equalTo( "Mission to Mos Eisley" ) ) );
        assertThat( mediaItem.getDescription(), is( equalTo( "C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped." ) ) );
        assertThat( mediaItem.getStartDate(), not( nullValue() ) );
        assertThat( mediaItem.getSeason(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.getEpisode(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.getStudio(), is( equalTo( "DXDHD" ) ) );
        assertThat( mediaItem.getUrl(), is( equalTo( "/Content/GetFile?FileName=2289_20150908113000.ts" ) ) );
        assertThat( mediaItem.getFanartUrl(), is( nullValue() ) );
        assertThat( mediaItem.getCoverartUrl(), is( nullValue() ) );
        assertThat( mediaItem.getBannerUrl(), is( nullValue() ) );
        assertThat( mediaItem.getPreviewUrl(), is( "/Content/GetPreviewImage?RecordedId=3120" ) );

        assertThat( mediaItem.getDuration(), greaterThan( 0l ) );
        assertThat( mediaItem.getDuration(), is( equalTo( 30L ) ) );
        assertThat( mediaItem.isValid(), is( true ) );
        assertThat( mediaItem.getValidationErrors(), hasSize( 0 ) );

    }

    @Test
    public void testTransformProgramNullStartTs() throws Exception {

        ProgramEntity programEntity = programEntityJsonMapper.transformProgramEntity( new StringReader( JSON_RESPONSE_DVR_GET_RECORDED_NULL_STARTTS ) );
        assertThat( programEntity, not( nullValue() ) );

        MediaItem mediaItem = MediaItemDataMapper.transform( programEntity );
        assertThat( mediaItem, not( nullValue() ) );
        assertThat( mediaItem.getId(), is( equalTo( 3120 ) ) );
        assertThat( mediaItem.getMedia(), is( equalTo( Media.PROGRAM ) ) );
        assertThat( mediaItem.getTitle(), is( equalTo( "Star Wars: Droid Tales" ) ) );
        assertThat( mediaItem.getSubTitle(), is( equalTo( "Mission to Mos Eisley" ) ) );
        assertThat( mediaItem.getDescription(), is( equalTo( "C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped." ) ) );
        assertThat( mediaItem.getStartDate(), not( nullValue() ) );
        assertThat( mediaItem.getSeason(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.getEpisode(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.getStudio(), is( equalTo( "DXDHD" ) ) );
        assertThat( mediaItem.getUrl(), is( equalTo( "/Content/GetFile?FileName=2289_20150908113000.ts" ) ) );
        assertThat( mediaItem.getFanartUrl(), is( nullValue() ) );
        assertThat( mediaItem.getCoverartUrl(), is( nullValue() ) );
        assertThat( mediaItem.getBannerUrl(), is( nullValue() ) );
        assertThat( mediaItem.getPreviewUrl(), is( "/Content/GetPreviewImage?RecordedId=3120" ) );

        assertThat( mediaItem.getDuration(), lessThanOrEqualTo( 0l ) );
        assertThat( mediaItem.isValid(), is( false ) );
        assertThat( mediaItem.getValidationErrors(), hasSize( 1 ) );

    }

    @Test
    public void testTransformProgramNullRecordedId() throws Exception {

        ProgramEntity programEntity = programEntityJsonMapper.transformProgramEntity( new StringReader( JSON_RESPONSE_DVR_GET_RECORDED_NULL_RECORDEDID ) );
        assertThat( programEntity, not( nullValue() ) );

        MediaItem mediaItem = MediaItemDataMapper.transform( programEntity );
        assertThat( mediaItem, not( nullValue() ) );
        assertThat( mediaItem.getId(), is( 0 ) );
        assertThat( mediaItem.getMedia(), is( equalTo( Media.PROGRAM ) ) );
        assertThat( mediaItem.getTitle(), is( equalTo( "Star Wars: Droid Tales" ) ) );
        assertThat( mediaItem.getSubTitle(), is( equalTo( "Mission to Mos Eisley" ) ) );
        assertThat( mediaItem.getDescription(), is( equalTo( "C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped." ) ) );
        assertThat( mediaItem.getStartDate(), not( nullValue() ) );
        assertThat( mediaItem.getSeason(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.getEpisode(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.getStudio(), is( equalTo( "DXDHD" ) ) );
        assertThat( mediaItem.getUrl(), is( equalTo( "/Content/GetFile?FileName=2289_20150908113000.ts" ) ) );
        assertThat( mediaItem.getFanartUrl(), is( nullValue() ) );
        assertThat( mediaItem.getCoverartUrl(), is( nullValue() ) );
        assertThat( mediaItem.getBannerUrl(), is( nullValue() ) );
        assertThat( mediaItem.getPreviewUrl(), is( nullValue() ) );

        assertThat( mediaItem.getDuration(), greaterThan( 0l ) );
        assertThat( mediaItem.getDuration(), is( equalTo( 30L ) ) );
        assertThat( mediaItem.isValid(), is( false ) );
        assertThat( mediaItem.getValidationErrors(), hasSize( 1 ) );

    }

    @Test
    public void testTransformVideo() throws Exception {

        VideoMetadataInfoEntity videoMetadataInfoEntity = videoMetadataInfoEntityJsonMapper.transformVideoMetadataInfoEntity( new StringReader( JSON_RESPONSE_VIDEO_GET_VIDEO ) );
        assertThat( videoMetadataInfoEntity, not( nullValue() ) );

        MediaItem mediaItem = MediaItemDataMapper.transform( videoMetadataInfoEntity );
        assertThat( mediaItem, not( nullValue() ) );
        assertThat( mediaItem.getId(), is( equalTo( 1 ) ) );
        assertThat( mediaItem.getMedia(), is( equalTo( Media.VIDEO ) ) );
        assertThat( mediaItem.getTitle(), is( equalTo( "big buck bunny 1080p stereo" ) ) );
        assertThat( mediaItem.getSubTitle(), is( equalTo( "" ) ) );
        assertThat( mediaItem.getDescription(), is( equalTo( "None" ) ) );
        assertThat( mediaItem.getStartDate(), is( nullValue() ) );
        assertThat( mediaItem.getSeason(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.getEpisode(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.getStudio(), is( equalTo( "" ) ) );
        assertThat( mediaItem.getUrl(), is( equalTo( "/Content/GetFile?FileName=library%2FBlender%2Fbig_buck_bunny_1080p_stereo.ogg" ) ) );
        assertThat( mediaItem.getFanartUrl(), is( nullValue() ) );
        assertThat( mediaItem.getCoverartUrl(), is( nullValue() ) );
        assertThat( mediaItem.getBannerUrl(), is( nullValue() ) );
        assertThat( mediaItem.getPreviewUrl(), is( nullValue() ) );

        assertThat( mediaItem.getDuration(), is( equalTo( 0L ) ) );
        assertThat( mediaItem.getValidationErrors(), hasSize( 0 ) );

    }

}
