package org.mythtv.android.data.cache.serializer;

import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.ProgramListEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProgramEntityListJsonSerializerTest extends ApplicationTestCase {

    private ProgramListEntityJsonSerializer jsonSerializer;

    @Before
    public void setUp() {
        jsonSerializer = new ProgramListEntityJsonSerializer();
    }

    @Test
    public void testSerializeHappyCase() {

        ProgramListEntity programListEntityOne = jsonSerializer.deserialize( JSON_RESPONSE );
        String jsonString = jsonSerializer.serialize( programListEntityOne );
        ProgramListEntity programListEntityTwo = jsonSerializer.deserialize( jsonString );

        assertThat( programListEntityOne, is( programListEntityTwo ) );
        assertThat( programListEntityOne.getPrograms(), is( programListEntityTwo.getPrograms() ) );
        assertThat( programListEntityOne.getPrograms().getStartIndex(), is( programListEntityTwo.getPrograms().getStartIndex() ) );
        assertThat( programListEntityOne.getPrograms().getCount(), is( programListEntityTwo.getPrograms().getCount() ) );
        assertThat( programListEntityOne.getPrograms().getAsOf(), is( programListEntityTwo.getPrograms().getAsOf() ) );
        assertThat( programListEntityOne.getPrograms().getVersion(), is( programListEntityTwo.getPrograms().getVersion() ) );
        assertThat( programListEntityOne.getPrograms().getProtoVer(), is( programListEntityTwo.getPrograms().getProtoVer() ) );
        assertThat( programListEntityOne.getPrograms().getPrograms(), is( programListEntityTwo.getPrograms().getPrograms() ) );
        assertThat( programListEntityOne.getPrograms().getPrograms().length, is( programListEntityTwo.getPrograms().getPrograms().length ) );

    }

    private static final String JSON_RESPONSE = "{\"ProgramList\": {\"StartIndex\": \"1\", \"Count\": \"2\", \"TotalAvailable\": \"59\", \"AsOf\": \"2015-09-26T02:17:53Z\", \"Version\": \"0.28.20150906-1\", \"ProtoVer\": \"87\", \"Programs\": [{\"StartTime\": \"2015-09-26T00:15:22Z\", \"EndTime\": \"2015-09-26T00:15:22Z\", \"Title\": \"Hak5\", \"SubTitle\": \"Easy File Encryption with Keybase - Hak5 1904\", \"Category\": \"Miro\", \"CatType\": \"series\", \"Repeat\": \"false\", \"VideoProps\": \"0\", \"AudioProps\": \"0\", \"SubProps\": \"0\", \"SeriesId\": \"\", \"ProgramId\": \"\", \"Stars\": \"0\", \"LastModified\": \"2015-09-26T04:15:22Z\", \"ProgramFlags\": \"0\", \"Airdate\": \"2015-09-25\", \"Description\": \"What could be easier than file encryption with GPG? File encryption with keybase.io! Darren Kitchen and Shannon Morse demo the basics of the\", \"Inetref\": \"79008\", \"Season\": \"1\", \"Episode\": \"1\", \"TotalEpisodes\": \"0\", \"FileSize\": \"273614596\", \"FileName\": \"9999_20150925201522.mp4\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"9999\", \"ChanNum\": \"999\", \"CallSign\": \"Miro\", \"IconURL\": \"\\/Guide\\/GetChannelIcon?ChanId=9999\", \"ChannelName\": \"Miro\", \"MplexId\": \"0\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"999\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"999\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"0\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3392\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-26T00:15:22Z\", \"EndTs\": \"2015-09-26T00:15:22Z\", \"FileSize\": \"273614596\", \"FileName\": \"9999_20150925201522.mp4\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-26T04:15:22Z\", \"RecordId\": \"0\", \"RecGroup\": \"Default\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"Default\", \"RecType\": \"0\", \"DupInType\": \"0\", \"DupMethod\": \"0\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}},{\"StartTime\": \"2015-09-26T00:15:09Z\", \"EndTime\": \"2015-09-26T00:15:09Z\", \"Title\": \"Hak5\", \"SubTitle\": \"Refuse to decrypt? Prison for you! - Hak5 1905\", \"Category\": \"Miro\", \"CatType\": \"series\", \"Repeat\": \"false\", \"VideoProps\": \"0\", \"AudioProps\": \"0\", \"SubProps\": \"0\", \"SeriesId\": \"\", \"ProgramId\": \"\", \"Stars\": \"0\", \"LastModified\": \"2015-09-26T04:15:09Z\", \"ProgramFlags\": \"0\", \"Airdate\": \"2015-09-25\", \"Description\": \"GPG Encryption for Windows, what happens if Keybase disappears, Free SSL certificates and how you could end up in prison for withholding you\", \"Inetref\": \"79008\", \"Season\": \"1\", \"Episode\": \"1\", \"TotalEpisodes\": \"0\", \"FileSize\": \"221425083\", \"FileName\": \"9999_20150925201509.mp4\", \"HostName\": \"mythcenter\", \"Channel\": {\"ChanId\": \"9999\", \"ChanNum\": \"999\", \"CallSign\": \"Miro\", \"IconURL\": \"\\/Guide\\/GetChannelIcon?ChanId=9999\", \"ChannelName\": \"Miro\", \"MplexId\": \"0\", \"ServiceId\": \"0\", \"ATSCMajorChan\": \"999\", \"ATSCMinorChan\": \"0\", \"Format\": \"Default\", \"FrequencyId\": \"999\", \"FineTune\": \"0\", \"ChanFilters\": \"\", \"SourceId\": \"0\", \"InputId\": \"0\", \"CommFree\": \"false\", \"UseEIT\": \"false\", \"Visible\": \"true\", \"XMLTVID\": \"\", \"DefaultAuth\": \"\", \"Programs\": []}, \"Recording\": {\"RecordedId\": \"3391\", \"Status\": \"-3\", \"Priority\": \"0\", \"StartTs\": \"2015-09-26T00:15:09Z\", \"EndTs\": \"2015-09-26T00:15:09Z\", \"FileSize\": \"221425083\", \"FileName\": \"9999_20150925201509.mp4\", \"HostName\": \"mythcenter\", \"LastModified\": \"2015-09-26T04:15:09Z\", \"RecordId\": \"0\", \"RecGroup\": \"Default\", \"PlayGroup\": \"Default\", \"StorageGroup\": \"Default\", \"RecType\": \"0\", \"DupInType\": \"0\", \"DupMethod\": \"0\", \"EncoderId\": \"0\", \"EncoderName\": \"\", \"Profile\": \"Default\"}, \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}]}}";

}