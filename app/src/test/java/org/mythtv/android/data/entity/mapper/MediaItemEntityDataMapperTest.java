package org.mythtv.android.data.entity.mapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.Media;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

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

public class MediaItemEntityDataMapperTest extends ApplicationTestCase {

    private static final String JSON_RESPONSE_DVR_GET_RECORDED = "{\"Program\": {\"StartTime\": \"2015-09-08T11:30:00Z\", \"EndTime\": \"2015-09-08T12:00:00Z\", \"Title\": \"Star Wars: Droid Tales\", \"SubTitle\": \"Mission to Mos Eisley\", \"Category\": \"Fantasy\", \"CatType\": \"series\", \"Repeat\": \"false\", \"VideoProps\": \"11\", \"AudioProps\": \"0\", \"SubProps\": \"1\", \"SeriesId\": \"EP02206828\", \"ProgramId\": \"EP022068280003\", \"Stars\": \"0\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"ProgramFlags\": \"5\", \"Airdate\": \"2015-09-07\", \"Description\": \"C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped.\", \"Inetref\": \"ttvdb.py_298171\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2289\", \"ChanNum\": \"1289\", \"CallSign\": \"DXDHD\", \"IconURL\": \"\", \"ChannelName\": \"Disney XD HD\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1289\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1289\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"60006\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3120\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-08T11:30:00Z\", \"EndTs\": \"2015-09-08T12:00:00Z\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"RecordId\": \"112\", \"RecGroup\": \"Default\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"Default\", \"RecType\": \"0\", \"DupInType\": \"15\", \"DupMethod\": \"6\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}}";
    private static final String JSON_RESPONSE_DVR_GET_RECORDED_NULL_STARTTS = "{\"Program\": {\"StartTime\": \"2015-09-08T11:30:00Z\", \"EndTime\": \"2015-09-08T12:00:00Z\", \"Title\": \"Star Wars: Droid Tales\", \"SubTitle\": \"Mission to Mos Eisley\", \"Category\": \"Fantasy\", \"CatType\": \"series\", \"Repeat\": \"false\", \"VideoProps\": \"11\", \"AudioProps\": \"0\", \"SubProps\": \"1\", \"SeriesId\": \"EP02206828\", \"ProgramId\": \"EP022068280003\", \"Stars\": \"0\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"ProgramFlags\": \"5\", \"Airdate\": \"2015-09-07\", \"Description\": \"C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped.\", \"Inetref\": \"ttvdb.py_298171\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2289\", \"ChanNum\": \"1289\", \"CallSign\": \"DXDHD\", \"IconURL\": \"\", \"ChannelName\": \"Disney XD HD\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1289\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1289\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"60006\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3120\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"\", \"EndTs\": \"2015-09-08T12:00:00Z\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"RecordId\": \"112\", \"RecGroup\": \"Default\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"Default\", \"RecType\": \"0\", \"DupInType\": \"15\", \"DupMethod\": \"6\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}}";
    private static final String JSON_RESPONSE_DVR_GET_RECORDED_NULL_RECORDEDID = "{\"Program\": {\"StartTime\": \"2015-09-08T11:30:00Z\", \"EndTime\": \"2015-09-08T12:00:00Z\", \"Title\": \"Star Wars: Droid Tales\", \"SubTitle\": \"Mission to Mos Eisley\", \"Category\": \"Fantasy\", \"CatType\": \"series\", \"Repeat\": \"false\", \"VideoProps\": \"11\", \"AudioProps\": \"0\", \"SubProps\": \"1\", \"SeriesId\": \"EP02206828\", \"ProgramId\": \"EP022068280003\", \"Stars\": \"0\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"ProgramFlags\": \"5\", \"Airdate\": \"2015-09-07\", \"Description\": \"C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped.\", \"Inetref\": \"ttvdb.py_298171\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2289\", \"ChanNum\": \"1289\", \"CallSign\": \"DXDHD\", \"IconURL\": \"\", \"ChannelName\": \"Disney XD HD\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1289\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1289\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"60006\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-08T11:30:00Z\", \"EndTs\": \"2015-09-08T12:00:00Z\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"RecordId\": \"112\", \"RecGroup\": \"Default\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"Default\", \"RecType\": \"0\", \"DupInType\": \"15\", \"DupMethod\": \"6\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}}";
    private static final String JSON_RESPONSE_VIDEO_GET_VIDEO = "{\"VideoMetadataInfo\":{\"Id\": \"1\", \"Title\": \"big buck bunny 1080p stereo\", \"SubTitle\": \"\", \"Tagline\": \"\", \"Director\": \"Unknown\", \"Studio\": \"\", \"Description\": \"None\", \"Certification\": \"NR\", \"Inetref\": \"00000000\", \"Collectionref\": \"-1\", \"HomePage\": \"\", \"ReleaseDate\": \"\", \"AddDate\": \"2015-01-05T05:00:00Z\", \"UserRating\": \"0\", \"Length\": \"0\", \"PlayCount\": \"0\", \"Season\": \"0\", \"Episode\": \"0\", \"ParentalLevel\": \"1\", \"Visible\": \"true\", \"Watched\": \"false\", \"Processed\": \"true\", \"ContentType\": \"MOVIE\", \"FileName\": \"library\\/Blender\\/big_buck_bunny_1080p_stereo.ogg\", \"Hash\": \"63ad35476c29214c\", \"HostName\": \"mythcenter\", \"Coverart\": \"\", \"Fanart\": \"\", \"Banner\": \"\", \"Screenshot\": \"\", \"Trailer\": \"\", \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}}";

    private static final String JSON_RESPONSE_DVR_GET_RECORDED_BILL_BAD = "{\"Program\": {\"StartTime\": \"2017-01-25T01:29:38Z\", \"EndTime\": \"2017-01-25T01:29:38Z\", \"Title\": \"\", \"SubTitle\": \"\", \"Category\": \"\", \"CatType\": \"\", \"Repeat\": \"false\", \"VideoProps\": \"0\", \"AudioProps\": \"0\", \"SubProps\": \"0\", \"SeriesId\": \"\", \"ProgramId\": \"\", \"Stars\": \"0\", \"LastModified\": \"2017-01-25T01:29:38Z\", \"ProgramFlags\": \"0\", \"Airdate\": \"\", \"Description\": \"\", \"Inetref\": \"\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"0\", \"FileName\": \"\", \"HostName\": \"\", \"Channel\": {\"ChanId\": \"0\", \"ChanNum\": \"\", \"CallSign\": \"\", \"IconURL\": \"\", \"ChannelName\": \"\", \"MplexId\": \"0\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"0\", \"ATSCMinorChan\": \"0\", \"Format\": \"\", \"FrequencyId\": \"\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"0\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"0\", \"Status\": \"0\", \"Priority\": \"0\", \"StartTs\": \"\", \"EndTs\": \"\", \"FileSize\": \"0\", \"FileName\": \"\", \"HostName\": \"\", \"LastModified\": \"\", \"RecordId\": \"0\", \"RecGroup\": \"\", \"PlayGroup\": \"\", \"StorageGroup\": \"\", \"RecType\": \"0\", \"DupInType\": \"1\", \"DupMethod\": \"1\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}}";

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

        ProgramEntity programEntity = programEntityJsonMapper.transformProgramEntity( JSON_RESPONSE_DVR_GET_RECORDED );
        assertThat( programEntity, not( nullValue() ) );

        MediaItemEntity mediaItem = MediaItemEntityDataMapper.transform( programEntity );
        assertThat( mediaItem, not( nullValue() ) );
        assertThat( mediaItem.id(), is( equalTo( 3120 ) ) );
        assertThat( mediaItem.media(), is( equalTo( Media.PROGRAM ) ) );
        assertThat( mediaItem.title(), is( equalTo( "Star Wars: Droid Tales" ) ) );
        assertThat( mediaItem.subTitle(), is( equalTo( "Mission to Mos Eisley" ) ) );
        assertThat( mediaItem.description(), is( equalTo( "C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped." ) ) );
        assertThat( mediaItem.startDate(), not( nullValue() ) );
        assertThat( mediaItem.season(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.episode(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.studio(), is( equalTo( "DXDHD" ) ) );
        assertThat( mediaItem.url(), is( equalTo( "/Content/GetFile?FileName=2289_20150908113000.ts" ) ) );
        assertThat( mediaItem.fanartUrl(), is( nullValue() ) );
        assertThat( mediaItem.coverartUrl(), is( nullValue() ) );
        assertThat( mediaItem.bannerUrl(), is( nullValue() ) );
        assertThat( mediaItem.previewUrl(), is( "/Content/GetPreviewImage?RecordedId=3120" ) );

        assertThat( mediaItem.duration(), greaterThan(0L) );
        assertThat( mediaItem.duration(), is( equalTo( 30L ) ) );
        assertThat( mediaItem.isValid(), is( true ) );
        assertThat( mediaItem.validationErrors(), hasSize( 0 ) );

    }

    @Test
    public void testTransformProgramNullStartTs() throws Exception {

        ProgramEntity programEntity = programEntityJsonMapper.transformProgramEntity( JSON_RESPONSE_DVR_GET_RECORDED_NULL_STARTTS );
        assertThat( programEntity, not( nullValue() ) );

        MediaItemEntity mediaItem = MediaItemEntityDataMapper.transform( programEntity );
        assertThat( mediaItem, not( nullValue() ) );
        assertThat( mediaItem.id(), is( equalTo( 3120 ) ) );
        assertThat( mediaItem.media(), is( equalTo( Media.PROGRAM ) ) );
        assertThat( mediaItem.title(), is( equalTo( "Star Wars: Droid Tales" ) ) );
        assertThat( mediaItem.subTitle(), is( equalTo( "Mission to Mos Eisley" ) ) );
        assertThat( mediaItem.description(), is( equalTo( "C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped." ) ) );
        assertThat( mediaItem.startDate(), not( nullValue() ) );
        assertThat( mediaItem.season(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.episode(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.studio(), is( equalTo( "DXDHD" ) ) );
        assertThat( mediaItem.url(), is( equalTo( "/Content/GetFile?FileName=2289_20150908113000.ts" ) ) );
        assertThat( mediaItem.fanartUrl(), is( nullValue() ) );
        assertThat( mediaItem.coverartUrl(), is( nullValue() ) );
        assertThat( mediaItem.bannerUrl(), is( nullValue() ) );
        assertThat( mediaItem.previewUrl(), is( "/Content/GetPreviewImage?RecordedId=3120" ) );

        assertThat( mediaItem.duration(), lessThanOrEqualTo(0L) );
        assertThat( mediaItem.isValid(), is( false ) );
        assertThat( mediaItem.validationErrors(), hasSize( 1 ) );

    }

    @Test
    public void testTransformProgramNullRecordedId() throws Exception {

        ProgramEntity programEntity = programEntityJsonMapper.transformProgramEntity( JSON_RESPONSE_DVR_GET_RECORDED_NULL_RECORDEDID );
        assertThat( programEntity, not( nullValue() ) );

        MediaItemEntity mediaItem = MediaItemEntityDataMapper.transform( programEntity );
        assertThat( mediaItem, not( nullValue() ) );
        assertThat( mediaItem.id(), is( 0 ) );
        assertThat( mediaItem.media(), is( equalTo( Media.PROGRAM ) ) );
        assertThat( mediaItem.title(), is( equalTo( "Star Wars: Droid Tales" ) ) );
        assertThat( mediaItem.subTitle(), is( equalTo( "Mission to Mos Eisley" ) ) );
        assertThat( mediaItem.description(), is( equalTo( "C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped." ) ) );
        assertThat( mediaItem.startDate(), not( nullValue() ) );
        assertThat( mediaItem.season(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.episode(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.studio(), is( equalTo( "DXDHD" ) ) );
        assertThat( mediaItem.url(), is( equalTo( "/Content/GetFile?FileName=2289_20150908113000.ts" ) ) );
        assertThat( mediaItem.fanartUrl(), is( nullValue() ) );
        assertThat( mediaItem.coverartUrl(), is( nullValue() ) );
        assertThat( mediaItem.bannerUrl(), is( nullValue() ) );
        assertThat( mediaItem.previewUrl(), is( nullValue() ) );

        assertThat( mediaItem.duration(), greaterThan( 0L) );
        assertThat( mediaItem.duration(), is( equalTo( 30L ) ) );
        assertThat( mediaItem.isValid(), is( false ) );
        assertThat( mediaItem.validationErrors(), hasSize( 1 ) );

    }

    @Test
    public void testTransformUpcoming() throws Exception {

        try( BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( getClass().getClassLoader().getResourceAsStream( "org/mythtv/android/data/entity/mapper/Dvr_GetUpcomingList.json" ) ) ) ) {

            String line = null;
            String message = new String();
            final StringBuffer buffer = new StringBuffer( 2048 );
            while( ( line = bufferedReader.readLine()) != null ) {
                // buffer.append(line);
                message += line;
            }

            List<ProgramEntity> programEntities = programEntityJsonMapper.transformProgramEntityCollection( message );
            assertThat( programEntities, not(nullValue() ) );

            List<MediaItemEntity> mediaItems = MediaItemEntityDataMapper.transformPrograms( programEntities );
            assertThat( mediaItems, not( nullValue()) );
            assertThat( mediaItems, hasSize(equalTo( 40 ) ) );

            for( MediaItemEntity mediaItem : mediaItems ) {

                assertThat( mediaItem.isValid(), is( true ) );
                assertThat( mediaItem.validationErrors(), hasSize( 0 ) );

            }

        }

    }

    @Test
    public void testTransformVideo() throws Exception {

        VideoMetadataInfoEntity videoMetadataInfoEntity = videoMetadataInfoEntityJsonMapper.transformVideoMetadataInfoEntity( JSON_RESPONSE_VIDEO_GET_VIDEO );
        assertThat( videoMetadataInfoEntity, not( nullValue() ) );

        MediaItemEntity mediaItem = MediaItemEntityDataMapper.transform( videoMetadataInfoEntity );
        assertThat( mediaItem, not( nullValue() ) );
        assertThat( mediaItem.id(), is( equalTo( 1 ) ) );
        assertThat( mediaItem.media(), is( equalTo( Media.MOVIE ) ) );
        assertThat( mediaItem.title(), is( equalTo( "big buck bunny 1080p stereo" ) ) );
        assertThat( mediaItem.subTitle(), is( equalTo( "" ) ) );
        assertThat( mediaItem.description(), is( equalTo( "None" ) ) );
        assertThat( mediaItem.startDate(), is( nullValue() ) );
        assertThat( mediaItem.season(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.episode(), is( equalTo( 0 ) ) );
        assertThat( mediaItem.studio(), is( equalTo( "" ) ) );
        assertThat( mediaItem.url(), is( equalTo( "/Content/GetFile?FileName=library%2FBlender%2Fbig_buck_bunny_1080p_stereo.ogg" ) ) );
        assertThat( mediaItem.fanartUrl(), is( nullValue() ) );
        assertThat( mediaItem.coverartUrl(), is( nullValue() ) );
        assertThat( mediaItem.bannerUrl(), is( nullValue() ) );
        assertThat( mediaItem.previewUrl(), is( nullValue() ) );

        assertThat( mediaItem.duration(), is( equalTo( 0L ) ) );
        assertThat( mediaItem.validationErrors(), hasSize( 0 ) );

    }

    @Test
    public void testTransformProgramBillBad() throws Exception {

        ProgramEntity programEntity = programEntityJsonMapper.transformProgramEntity( JSON_RESPONSE_DVR_GET_RECORDED_BILL_BAD );
        assertThat( programEntity, not( nullValue() ) );

        MediaItemEntity mediaItem = MediaItemEntityDataMapper.transform( programEntity );
        assertThat( mediaItem, not( nullValue() ) );
        assertThat( mediaItem.id(), is( 0 ) );
        assertThat( mediaItem.media(), is( nullValue() ) );

        assertThat( mediaItem.duration(), lessThanOrEqualTo(0L) );
        assertThat( mediaItem.isValid(), is( false ) );
        assertThat( mediaItem.validationErrors(), hasSize( 3 ) );

    }

}
