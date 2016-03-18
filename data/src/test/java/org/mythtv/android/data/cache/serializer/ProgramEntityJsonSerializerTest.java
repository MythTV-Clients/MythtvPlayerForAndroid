package org.mythtv.android.data.cache.serializer;

import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.ProgramEntity;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProgramEntityJsonSerializerTest extends ApplicationTestCase {

    private ProgramEntityJsonSerializer jsonSerializer;

    @Before
    public void setUp() {
        jsonSerializer = new ProgramEntityJsonSerializer();
    }

    @Test
    public void testSerializeHappyCase() {

        ProgramEntity programEntityOne = jsonSerializer.deserialize( JSON_RESPONSE );
        String jsonString = jsonSerializer.serialize( programEntityOne );
        ProgramEntity programEntityTwo = jsonSerializer.deserialize( jsonString );

        assertThat( programEntityOne.getTitle(), is( programEntityTwo.getTitle() ) );
        assertThat( programEntityOne.getStartTime(), is( programEntityTwo.getStartTime() ) );
        assertThat( programEntityOne.getFileName(), is( programEntityTwo.getFileName() ) );

    }

    private static final String JSON_RESPONSE = "{\n"
            + "         \"Program\": {\n"
            + "                 \"StartTime\": \"2015-06-11T00:00:00Z\",\n"
            + "                 \"EndTime\": \"2015-06-11T01:01:00Z\",\n"
            + "                 \"Title\": \"MasterChef\",\n"
            + "                 \"SubTitle\": \"Clawing to Victory\",\n"
            + "                 \"Category\": \"Reality\",\n"
            + "                 \"CatType\": \"series\",\n"
            + "                 \"Repeat\": \"false\",\n"
            + "                 \"VideoProps\": \"11\",\n"
            + "                 \"AudioProps\": \"9\",\n"
            + "                 \"SubProps\": \"1\",\n"
            + "                 \"SeriesId\": \"EP01251347\",\n"
            + "                 \"ProgramId\": \"EP012513470146\",\n"
            + "                 \"Stars\": \"0\",\n"
            + "                 \"LastModified\": \"2015-06-11T01:01:19Z\",\n"
            + "                 \"ProgramFlags\": \"5\",\n"
            + "                 \"Airdate\": \"2015-06-10\",\n"
            + "                 \"Description\": \"The cooks compete to prepare the best crab dish, with the winner gaining a significant advantage before the elimination; the cooks create versions of dishes using corn.\",\n"
            + "                 \"Inetref\": \"ttvdb.py_167751\",\n"
            + "                 \"Season\": \"6\",\n"
            + "                 \"Episode\": \"5\",\n"
            + "                 \"TotalEpisodes\": \"0\",\n"
            + "                 \"FileSize\": \"5375108752\",\n"
            + "                 \"FileName\": \"2009_20150611000000.ts\",\n"
            + "                 \"HostName\": \"mythcenter\",\n"
            + "                 \"Channel\": {\n"
            + "                     \"ChanId\": \"2009\",\n"
            + "                     \"ChanNum\": \"1009\",\n"
            + "                     \"CallSign\": \"WTXFDT\",\n"
            + "                     \"IconURL\": \"\",\n"
            + "                     \"ChannelName\": \"WTXFDT (WTXF-DT)\",\n"
            + "                     \"MplexId\": \"32767\",\n"
            + "                     \"ServiceId\": \"0\",\n"
            + "                     \"ATSCMajorChan\": \"1009\",\n"
            + "                     \"ATSCMinorChan\": \"0\",\n"
            + "                     \"Format\": \"Default\",\n"
            + "                     \"FrequencyId\": \"1009\",\n"
            + "                     \"FineTune\": \"0\",\n"
            + "                     \"ChanFilters\": \"\",\n"
            + "                     \"SourceId\": \"1\",\n"
            + "                     \"InputId\": \"0\",\n"
            + "                     \"CommFree\": \"false\",\n"
            + "                     \"UseEIT\": \"false\",\n"
            + "                     \"Visible\": \"true\",\n"
            + "                     \"XMLTVID\": \"19614\",\n"
            + "                     \"DefaultAuth\": \"\",\n"
            + "                     \"Programs\": []\n"
            + "                 },\n"
            + "                 \"Recording\": {\n"
            + "                     \"RecordedId\": \"2063\",\n"
            + "                     \"Status\": \"-3\",\n"
            + "                     \"Priority\": \"0\",\n"
            + "                     \"StartTs\": \"2015-06-11T00:00:00Z\",\n"
            + "                     \"EndTs\": \"2015-06-11T01:01:00Z\",\n"
            + "                     \"FileSize\": \"5375108752\",\n"
            + "                     \"FileName\": \"2009_20150611000000.ts\",\n"
            + "                     \"HostName\": \"mythcenter\",\n"
            + "                     \"LastModified\": \"2015-06-11T01:01:19Z\",\n"
            + "                     \"RecordId\": \"54\",\n"
            + "                     \"RecGroup\": \"Default\",\n"
            + "                     \"PlayGroup\": \"Default\",\n"
            + "                     \"StorageGroup\": \"Default\",\n"
            + "                     \"RecType\": \"0\",\n"
            + "                     \"DupInType\": \"15\",\n"
            + "                     \"DupMethod\": \"6\",\n"
            + "                     \"EncoderId\": \"0\",\n"
            + "                     \"EncoderName\": \"\",\n"
            + "                     \"Profile\": \"Default\"\n"
            + "                 },\n"
            + "                 \"Artwork\": {\n"
            + "                     \"ArtworkInfos\": [\n"
            + "                         {\n"
            + "                             \"URL\": \"/Content/GetImageFile?StorageGroup=Coverart&FileName=/MasterChef (US) Season 6_coverart.jpg\",\n"
            + "                             \"FileName\": \"myth://Coverart@mythcenter/MasterChef (US) Season 6_coverart.jpg\",\n"
            + "                             \"StorageGroup\": \"Coverart\",\n"
            + "                             \"Type\": \"coverart\"\n"
            + "                         },\n"
            + "                         {\n"
            + "                             \"URL\": \"/Content/GetImageFile?StorageGroup=Fanart&FileName=/MasterChef (US) Season 6_fanart.jpg\",\n"
            + "                             \"FileName\": \"myth://Fanart@mythcenter/MasterChef (US) Season 6_fanart.jpg\",\n"
            + "                             \"StorageGroup\": \"Fanart\",\n"
            + "                             \"Type\": \"fanart\"\n"
            + "                         },\n"
            + "                         {\n"
            + "                             \"URL\": \"/Content/GetImageFile?StorageGroup=Banners&FileName=/MasterChef (US) Season 6_banner.jpg\",\n"
            + "                             \"FileName\": \"myth://Banners@mythcenter/MasterChef (US) Season 6_banner.jpg\",\n"
            + "                             \"StorageGroup\": \"Banners\",\n"
            + "                             \"Type\": \"banner\"\n"
            + "                         }\n"
            + "                     ]\n"
            + "                 },\n"
            + "                 \"Cast\": {\n"
            + "                     \"CastMembers\": [\n"
            + "                         {\n"
            + "                             \"Name\": \"Gordon Ramsay\",\n"
            + "                             \"CharacterName\": \"\",\n"
            + "                             \"Role\": \"\",\n"
            + "                             \"TranslatedRole\": \"\"\n"
            + "                         },\n"
            + "                         {\n"
            + "                             \"Name\": \"Jesse Romero\",\n"
            + "                             \"CharacterName\": \"\",\n"
            + "                             \"Role\": \"\",\n"
            + "                             \"TranslatedRole\": \"\"\n"
            + "                         },\n"
            + "                         {\n"
            + "                             \"Name\": \"Justin Banister\",\n"
            + "                             \"CharacterName\": \"\",\n"
            + "                             \"Role\": \"\",\n"
            + "                             \"TranslatedRole\": \"\"\n"
            + "                         },\n"
            + "                         {\n"
            + "                             \"Name\": \"Katrina Kozar\",\n"
            + "                             \"CharacterName\": \"\",\n"
            + "                             \"Role\": \"\",\n"
            + "                             \"TranslatedRole\": \"\"\n"
            + "                         }\n"
            + "                     ]\n"
            + "                 }\n"
            + "             }\n"
            + "         }\n";

    }