package org.mythtv.android.data.entity.mapper;

import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.ProgramEntity;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
public class ProgramEntityJsonDataMapperTest extends ApplicationTestCase {

    private static final String JSON_RESPONSE_DVR_GET_RECORDED = "{\"Program\": {\"StartTime\": \"2015-09-08T11:30:00Z\", \"EndTime\": \"2015-09-08T12:00:00Z\", \"Title\": \"Star Wars: Droid Tales\", \"SubTitle\": \"Mission to Mos Eisley\", \"Category\": \"Fantasy\", \"CatType\": \"series\", \"Repeat\": \"false\", \"VideoProps\": \"11\", \"AudioProps\": \"0\", \"SubProps\": \"1\", \"SeriesId\": \"EP02206828\", \"ProgramId\": \"EP022068280003\", \"Stars\": \"0\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"ProgramFlags\": \"5\", \"Airdate\": \"2015-09-07\", \"Description\": \"C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped.\", \"Inetref\": \"ttvdb.py_298171\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2289\", \"ChanNum\": \"1289\", \"CallSign\": \"DXDHD\", \"IconURL\": \"\", \"ChannelName\": \"Disney XD HD\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1289\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1289\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"60006\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3120\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-08T11:30:00Z\", \"EndTs\": \"2015-09-08T12:00:00Z\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"RecordId\": \"112\", \"RecGroup\": \"Default\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"Default\", \"RecType\": \"0\", \"DupInType\": \"15\", \"DupMethod\": \"6\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}}";
    private static final String JSON_RESPONSE_DVR_GET_RECORDED_BAD = "{\"Program\": {\"StartTime\": \"2015-09-08T11:30:00Z\", \"EndTime\": \"2015-09-08T12:00:00Z\", \"Title\": \"Star Wars: Droid Tales\", \"SubTitle\": \"Mission to Mos Eisley\", \"Category\": \"Fantasy\", \"CatType\": \"series\", \"Repeat\": \"false\", \"VideoProps\": \"11\", \"AudioProps\": \"0\", \"SubProps\": \"1\", \"SeriesId\": \"EP02206828\", \"ProgramId\": \"EP022068280003\", \"Stars\": \"0\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"ProgramFlags\": \"5\", \"Airdate\": \"2015-09-07\", \"Description\": \"C-3PO recounts his adventure with the ``Star Wars Rebels'' crew in an effort to find R2-D2, who has been kidnapped.\", \"Inetref\": \"ttvdb.py_298171\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2289\", \"ChanNum\": \"1289\", \"CallSign\": \"DXDHD\", \"IconURL\": \"\", \"ChannelName\": \"Disney XD HD\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1289\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1289\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"60006\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3120\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-08T11:30:00Z\", \"EndTs\": \"2015-09-08T12:00:00Z\", \"FileSize\": \"2780544064\", \"FileName\": \"2289_20150908113000.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-08T12:00:01Z\", \"RecordId\": \"112\", \"RecGroup\": \"Default\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"Default\", \"RecType\": \"0\", \"DupInType\": \"15\", \"DupMethod\": \"6\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []";
    private static final String JSON_RESPONSE_DVR_GET_RECORDED_LIST = "{\"ProgramList\": {\"StartIndex\": \"1\", \"Count\": \"2\", \"TotalAvailable\": \"39\", \"AsOf\": \"2015-09-19T03:16:59Z\", \"Version\": \"0.28.20150817-1\", \"ProtoVer\": \"87\", \"Programs\": [{\"StartTime\": \"2015-09-18T22:00:00Z\", \"EndTime\": \"2015-09-18T22:30:00Z\", \"Title\": \"Action News 6PM\", \"SubTitle\": \"\", \"Category\": \"News\", \"CatType\": \"series\", \"Repeat\": \"true\", \"VideoProps\": \"11\", \"AudioProps\": \"1\", \"SubProps\": \"1\", \"SeriesId\": \"\", \"ProgramId\": \"SH004527160000\", \"Stars\": \"0\", \"LastModified\": \"2015-09-18T22:30:01Z\", \"ProgramFlags\": \"4100\", \"Airdate\": \"2001-07-02\", \"Description\": \"Gardner, Tynan, Rodgers.\", \"Inetref\": \"\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"2325270856\", \"FileName\": \"2006_20150918220000.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2006\", \"ChanNum\": \"1006\", \"CallSign\": \"WPVIDT\", \"IconURL\": \"\", \"ChannelName\": \"WPVIDT (WPVI-DT)\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1006\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1006\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"19612\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3287\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-18T22:00:00Z\", \"EndTs\": \"2015-09-18T22:30:01Z\", \"FileSize\": \"2325270856\", \"FileName\": \"2006_20150918220000.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-18T22:30:01Z\", \"RecordId\": \"0\", \"RecGroup\": \"LiveTV\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"LiveTV\", \"RecType\": \"0\", \"DupInType\": \"0\", \"DupMethod\": \"0\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}},{\"StartTime\": \"2015-09-18T21:00:00Z\", \"EndTime\": \"2015-09-18T22:00:00Z\", \"Title\": \"Action News 5PM\", \"SubTitle\": \"\", \"Category\": \"News\", \"CatType\": \"series\", \"Repeat\": \"true\", \"VideoProps\": \"11\", \"AudioProps\": \"1\", \"SubProps\": \"1\", \"SeriesId\": \"\", \"ProgramId\": \"SH006990820000\", \"Stars\": \"0\", \"LastModified\": \"2015-09-18T22:00:01Z\", \"ProgramFlags\": \"4100\", \"Airdate\": \"2004-10-18\", \"Description\": \"Malpass, Williams, Tynan, Apody.\", \"Inetref\": \"\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"3975316212\", \"FileName\": \"2006_20150918210001.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2006\", \"ChanNum\": \"1006\", \"CallSign\": \"WPVIDT\", \"IconURL\": \"\", \"ChannelName\": \"WPVIDT (WPVI-DT)\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1006\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1006\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"19612\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3286\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-18T21:00:01Z\", \"EndTs\": \"2015-09-18T22:00:01Z\", \"FileSize\": \"3975316212\", \"FileName\": \"2006_20150918210001.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-18T22:00:01Z\", \"RecordId\": \"0\", \"RecGroup\": \"LiveTV\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"LiveTV\", \"RecType\": \"0\", \"DupInType\": \"0\", \"DupMethod\": \"0\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}]}}";
    private static final String JSON_RESPONSE_DVR_GET_RECORDED_LIST_BAD = "{\"ProgramList\": {\"StartIndex\": \"1\", \"Count\": \"2\", \"TotalAvailable\": \"39\", \"AsOf\": \"2015-09-19T03:16:59Z\", \"Version\": \"0.28.20150817-1\", \"ProtoVer\": \"87\", \"Programs\": [{\"StartTime\": \"2015-09-18T22:00:00Z\", \"EndTime\": \"2015-09-18T22:30:00Z\", \"Title\": \"Action News 6PM\", \"SubTitle\": \"\", \"Category\": \"News\", \"CatType\": \"series\", \"Repeat\": \"true\", \"VideoProps\": \"11\", \"AudioProps\": \"1\", \"SubProps\": \"1\", \"SeriesId\": \"\", \"ProgramId\": \"SH004527160000\", \"Stars\": \"0\", \"LastModified\": \"2015-09-18T22:30:01Z\", \"ProgramFlags\": \"4100\", \"Airdate\": \"2001-07-02\", \"Description\": \"Gardner, Tynan, Rodgers.\", \"Inetref\": \"\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"2325270856\", \"FileName\": \"2006_20150918220000.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2006\", \"ChanNum\": \"1006\", \"CallSign\": \"WPVIDT\", \"IconURL\": \"\", \"ChannelName\": \"WPVIDT (WPVI-DT)\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1006\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1006\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"19612\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3287\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-18T22:00:00Z\", \"EndTs\": \"2015-09-18T22:30:01Z\", \"FileSize\": \"2325270856\", \"FileName\": \"2006_20150918220000.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-18T22:30:01Z\", \"RecordId\": \"0\", \"RecGroup\": \"LiveTV\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"LiveTV\", \"RecType\": \"0\", \"DupInType\": \"0\", \"DupMethod\": \"0\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}},{\"StartTime\": \"2015-09-18T21:00:00Z\", \"EndTime\": \"2015-09-18T22:00:00Z\", \"Title\": \"Action News 5PM\", \"SubTitle\": \"\", \"Category\": \"News\", \"CatType\": \"series\", \"Repeat\": \"true\", \"VideoProps\": \"11\", \"AudioProps\": \"1\", \"SubProps\": \"1\", \"SeriesId\": \"\", \"ProgramId\": \"SH006990820000\", \"Stars\": \"0\", \"LastModified\": \"2015-09-18T22:00:01Z\", \"ProgramFlags\": \"4100\", \"Airdate\": \"2004-10-18\", \"Description\": \"Malpass, Williams, Tynan, Apody.\", \"Inetref\": \"\", \"Season\": \"0\", \"Episode\": \"0\", \"TotalEpisodes\": \"0\", \"FileSize\": \"3975316212\", \"FileName\": \"2006_20150918210001.ts\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"2006\", \"ChanNum\": \"1006\", \"CallSign\": \"WPVIDT\", \"IconURL\": \"\", \"ChannelName\": \"WPVIDT (WPVI-DT)\", \"MplexId\": \"32767\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"1006\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"1006\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"1\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"19612\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3286\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-18T21:00:01Z\", \"EndTs\": \"2015-09-18T22:00:01Z\", \"FileSize\": \"3975316212\", \"FileName\": \"2006_20150918210001.ts\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-18T22:00:01Z\", \"RecordId\": \"0\", \"RecGroup\": \"LiveTV\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"LiveTV\", \"RecType\": \"0\", \"DupInType\": \"0\", \"DupMethod\": \"0\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []";

    private ProgramEntityJsonMapper programEntityJsonMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        programEntityJsonMapper = new ProgramEntityJsonMapper( gson );

    }

    @Test
    public void testTransformProgramEntityHappyCase() {

        ProgramEntity programEntity = programEntityJsonMapper.transformProgramEntity( JSON_RESPONSE_DVR_GET_RECORDED );

        assertThat( programEntity.title(), is( equalTo( "Star Wars: Droid Tales" ) ) );
        assertThat( programEntity.subTitle(), is( equalTo( "Mission to Mos Eisley" ) ) );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformProgramEntityBadJson() {

        programEntityJsonMapper.transformProgramEntity( JSON_RESPONSE_DVR_GET_RECORDED_BAD );

    }

    @Test
    public void testTransformProgramEntityCollectionHappyCase() {

        Collection<ProgramEntity> programEntityCollection = programEntityJsonMapper.transformProgramEntityCollection( JSON_RESPONSE_DVR_GET_RECORDED_LIST );

        assertThat( ( (ProgramEntity) programEntityCollection.toArray() [ 0 ] ).title(), is( "Action News 6PM" ) );
        assertThat( ( (ProgramEntity) programEntityCollection.toArray() [ 1 ] ).title(), is( "Action News 5PM" ) );
        assertThat( programEntityCollection.size(), is( 2 ) );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformProgramEntityCollectionBadJson() {

        programEntityJsonMapper.transformProgramEntityCollection( JSON_RESPONSE_DVR_GET_RECORDED_LIST_BAD );

    }

}
