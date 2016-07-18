package org.mythtv.android.data.cache.serializer;

import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.EncoderListEntity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EncoderEntityListJsonSerializerTest extends ApplicationTestCase {

    private EncoderListEntityJsonSerializer jsonSerializer;

    @Before
    public void setUp() {

        jsonSerializer = new EncoderListEntityJsonSerializer( this.gson );

    }

    @Test
    public void testSerializeHappyCase() {

        EncoderListEntity encoderListEntityOne = jsonSerializer.deserialize( JSON_RESPONSE );
        String jsonString = jsonSerializer.serialize( encoderListEntityOne );
        EncoderListEntity encoderListEntityTwo = jsonSerializer.deserialize( jsonString );

        assertThat( encoderListEntityOne, is( encoderListEntityTwo ) );
        assertThat( encoderListEntityOne.getEncoders(), is( equalTo( encoderListEntityTwo.getEncoders() ) ) );
        assertThat( encoderListEntityOne.getEncoders().getEncoders(), is( encoderListEntityTwo.getEncoders().getEncoders() ) );
        assertThat( encoderListEntityOne.getEncoders().getEncoders().length, is( encoderListEntityTwo.getEncoders().getEncoders().length ) );

    }

    private static final String JSON_RESPONSE = "{\n" +
            "  \"EncoderList\": {\n" +
            "    \"Encoders\": [\n" +
            "      {\n" +
            "        \"Id\": \"1\",\n" +
            "        \"HostName\": \"mythcenter\",\n" +
            "        \"Local\": \"true\",\n" +
            "        \"Connected\": \"true\",\n" +
            "        \"State\": \"1\",\n" +
            "        \"SleepStatus\": \"8\",\n" +
            "        \"LowOnFreeSpace\": \"false\",\n" +
            "        \"Inputs\": [\n" +
            "          {\n" +
            "            \"Id\": \"1\",\n" +
            "            \"CardId\": \"1\",\n" +
            "            \"SourceId\": \"1\",\n" +
            "            \"InputName\": \"MPEG2TS\",\n" +
            "            \"DisplayName\": \"HDHRP-1\",\n" +
            "            \"QuickTune\": \"false\",\n" +
            "            \"RecPriority\": \"0\",\n" +
            "            \"ScheduleOrder\": \"1\",\n" +
            "            \"LiveTVOrder\": \"1\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"Recording\": {\n" +
            "          \"StartTime\": \"2016-01-18T14:00:00Z\",\n" +
            "          \"EndTime\": \"2016-01-18T15:00:00Z\",\n" +
            "          \"Title\": \"Live! With Kelly and Michael\",\n" +
            "          \"SubTitle\": \"\",\n" +
            "          \"Category\": \"Talk\",\n" +
            "          \"CatType\": \"series\",\n" +
            "          \"Repeat\": \"false\",\n" +
            "          \"VideoProps\": \"1\",\n" +
            "          \"AudioProps\": \"1\",\n" +
            "          \"SubProps\": \"1\",\n" +
            "          \"SeriesId\": \"EP01611848\",\n" +
            "          \"ProgramId\": \"EP016118480884\",\n" +
            "          \"Stars\": \"0\",\n" +
            "          \"LastModified\": \"2016-01-18T14:00:00Z\",\n" +
            "          \"ProgramFlags\": \"16777220\",\n" +
            "          \"Airdate\": \"2016-01-18\",\n" +
            "          \"Description\": \"\",\n" +
            "          \"Inetref\": \"\",\n" +
            "          \"Season\": \"0\",\n" +
            "          \"Episode\": \"0\",\n" +
            "          \"TotalEpisodes\": \"0\",\n" +
            "          \"FileSize\": \"0\",\n" +
            "          \"FileName\": \"/var/lib/mythtv/livetv/2006_20160118140000.ts\",\n" +
            "          \"HostName\": \"mythcenter\",\n" +
            "          \"Channel\": {\n" +
            "            \"ChanId\": \"2006\",\n" +
            "            \"ChanNum\": \"1006\",\n" +
            "            \"CallSign\": \"WPVIDT\",\n" +
            "            \"IconURL\": \"\",\n" +
            "            \"ChannelName\": \"WPVIDT (WPVI-DT)\",\n" +
            "            \"MplexId\": \"32767\",\n" +
            "            \"ServiceId\": \"0\",\n" +
            "            \"ATSCMajorChan\": \"1006\",\n" +
            "            \"ATSCMinorChan\": \"0\",\n" +
            "            \"Format\": \"Default\",\n" +
            "            \"FrequencyId\": \"1006\",\n" +
            "            \"FineTune\": \"0\",\n" +
            "            \"ChanFilters\": \"\",\n" +
            "            \"SourceId\": \"1\",\n" +
            "            \"InputId\": \"0\",\n" +
            "            \"CommFree\": \"false\",\n" +
            "            \"UseEIT\": \"false\",\n" +
            "            \"Visible\": \"true\",\n" +
            "            \"XMLTVID\": \"19612\",\n" +
            "            \"DefaultAuth\": \"\",\n" +
            "            \"Programs\": []\n" +
            "          },\n" +
            "          \"Recording\": {\n" +
            "            \"RecordedId\": \"5118\",\n" +
            "            \"Status\": \"-2\",\n" +
            "            \"Priority\": \"0\",\n" +
            "            \"StartTs\": \"2016-01-18T14:00:00Z\",\n" +
            "            \"EndTs\": \"2016-01-18T15:00:00Z\",\n" +
            "            \"FileSize\": \"3371762516\",\n" +
            "            \"FileName\": \"/var/lib/mythtv/livetv/2006_20160118140000.ts\",\n" +
            "            \"HostName\": \"mythcenter\",\n" +
            "            \"LastModified\": \"2016-01-18T14:00:00Z\",\n" +
            "            \"RecordId\": \"0\",\n" +
            "            \"RecGroup\": \"LiveTV\",\n" +
            "            \"PlayGroup\": \"Default\",\n" +
            "            \"StorageGroup\": \"LiveTV\",\n" +
            "            \"RecType\": \"0\",\n" +
            "            \"DupInType\": \"15\",\n" +
            "            \"DupMethod\": \"8\",\n" +
            "            \"EncoderId\": \"1\",\n" +
            "            \"EncoderName\": \"HDHRP-1\",\n" +
            "            \"Profile\": \"Default\"\n" +
            "          },\n" +
            "          \"Artwork\": {\n" +
            "            \"ArtworkInfos\": []\n" +
            "          },\n" +
            "          \"Cast\": {\n" +
            "            \"CastMembers\": [\n" +
            "              {\n" +
            "                \"Name\": \"Kelly Ripa\",\n" +
            "                \"CharacterName\": \"\",\n" +
            "                \"Role\": \"host\",\n" +
            "                \"TranslatedRole\": \"Host\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"Name\": \"Michael Strahan\",\n" +
            "                \"CharacterName\": \"\",\n" +
            "                \"Role\": \"host\",\n" +
            "                \"TranslatedRole\": \"Host\"\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"Id\": \"3\",\n" +
            "        \"HostName\": \"mythcenter\",\n" +
            "        \"Local\": \"true\",\n" +
            "        \"Connected\": \"true\",\n" +
            "        \"State\": \"0\",\n" +
            "        \"SleepStatus\": \"8\",\n" +
            "        \"LowOnFreeSpace\": \"false\",\n" +
            "        \"Inputs\": [\n" +
            "          {\n" +
            "            \"Id\": \"3\",\n" +
            "            \"CardId\": \"3\",\n" +
            "            \"SourceId\": \"1\",\n" +
            "            \"InputName\": \"MPEG2TS\",\n" +
            "            \"DisplayName\": \"HDHR-2\",\n" +
            "            \"QuickTune\": \"false\",\n" +
            "            \"RecPriority\": \"0\",\n" +
            "            \"ScheduleOrder\": \"3\",\n" +
            "            \"LiveTVOrder\": \"3\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"Recording\": {\n" +
            "          \"StartTime\": \"\",\n" +
            "          \"EndTime\": \"\",\n" +
            "          \"Title\": \"\",\n" +
            "          \"SubTitle\": \"\",\n" +
            "          \"Category\": \"\",\n" +
            "          \"CatType\": \"\",\n" +
            "          \"Repeat\": \"false\",\n" +
            "          \"VideoProps\": \"0\",\n" +
            "          \"AudioProps\": \"0\",\n" +
            "          \"SubProps\": \"0\",\n" +
            "          \"SeriesId\": \"\",\n" +
            "          \"ProgramId\": \"\",\n" +
            "          \"Stars\": \"0\",\n" +
            "          \"LastModified\": \"\",\n" +
            "          \"ProgramFlags\": \"0\",\n" +
            "          \"Airdate\": \"\",\n" +
            "          \"Description\": \"\",\n" +
            "          \"Inetref\": \"\",\n" +
            "          \"Season\": \"0\",\n" +
            "          \"Episode\": \"0\",\n" +
            "          \"TotalEpisodes\": \"0\",\n" +
            "          \"FileSize\": \"0\",\n" +
            "          \"FileName\": \"\",\n" +
            "          \"HostName\": \"\",\n" +
            "          \"Channel\": {\n" +
            "            \"ChanId\": \"0\",\n" +
            "            \"ChanNum\": \"\",\n" +
            "            \"CallSign\": \"\",\n" +
            "            \"IconURL\": \"\",\n" +
            "            \"ChannelName\": \"\",\n" +
            "            \"MplexId\": \"0\",\n" +
            "            \"ServiceId\": \"0\",\n" +
            "            \"ATSCMajorChan\": \"0\",\n" +
            "            \"ATSCMinorChan\": \"0\",\n" +
            "            \"Format\": \"\",\n" +
            "            \"FrequencyId\": \"\",\n" +
            "            \"FineTune\": \"0\",\n" +
            "            \"ChanFilters\": \"\",\n" +
            "            \"SourceId\": \"0\",\n" +
            "            \"InputId\": \"0\",\n" +
            "            \"CommFree\": \"false\",\n" +
            "            \"UseEIT\": \"false\",\n" +
            "            \"Visible\": \"true\",\n" +
            "            \"XMLTVID\": \"\",\n" +
            "            \"DefaultAuth\": \"\",\n" +
            "            \"Programs\": []\n" +
            "          },\n" +
            "          \"Recording\": {\n" +
            "            \"RecordedId\": \"0\",\n" +
            "            \"Status\": \"0\",\n" +
            "            \"Priority\": \"0\",\n" +
            "            \"StartTs\": \"\",\n" +
            "            \"EndTs\": \"\",\n" +
            "            \"FileSize\": \"0\",\n" +
            "            \"FileName\": \"\",\n" +
            "            \"HostName\": \"\",\n" +
            "            \"LastModified\": \"\",\n" +
            "            \"RecordId\": \"0\",\n" +
            "            \"RecGroup\": \"\",\n" +
            "            \"PlayGroup\": \"\",\n" +
            "            \"StorageGroup\": \"\",\n" +
            "            \"RecType\": \"0\",\n" +
            "            \"DupInType\": \"1\",\n" +
            "            \"DupMethod\": \"1\",\n" +
            "            \"EncoderId\": \"0\",\n" +
            "            \"EncoderName\": \"\",\n" +
            "            \"Profile\": \"\"\n" +
            "          },\n" +
            "          \"Artwork\": {\n" +
            "            \"ArtworkInfos\": []\n" +
            "          },\n" +
            "          \"Cast\": {\n" +
            "            \"CastMembers\": []\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"Id\": \"5\",\n" +
            "        \"HostName\": \"mythcenter\",\n" +
            "        \"Local\": \"true\",\n" +
            "        \"Connected\": \"true\",\n" +
            "        \"State\": \"0\",\n" +
            "        \"SleepStatus\": \"8\",\n" +
            "        \"LowOnFreeSpace\": \"false\",\n" +
            "        \"Inputs\": [\n" +
            "          {\n" +
            "            \"Id\": \"5\",\n" +
            "            \"CardId\": \"5\",\n" +
            "            \"SourceId\": \"1\",\n" +
            "            \"InputName\": \"MPEG2TS\",\n" +
            "            \"DisplayName\": \"HDHR-3\",\n" +
            "            \"QuickTune\": \"false\",\n" +
            "            \"RecPriority\": \"0\",\n" +
            "            \"ScheduleOrder\": \"5\",\n" +
            "            \"LiveTVOrder\": \"5\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"Recording\": {\n" +
            "          \"StartTime\": \"\",\n" +
            "          \"EndTime\": \"\",\n" +
            "          \"Title\": \"\",\n" +
            "          \"SubTitle\": \"\",\n" +
            "          \"Category\": \"\",\n" +
            "          \"CatType\": \"\",\n" +
            "          \"Repeat\": \"false\",\n" +
            "          \"VideoProps\": \"0\",\n" +
            "          \"AudioProps\": \"0\",\n" +
            "          \"SubProps\": \"0\",\n" +
            "          \"SeriesId\": \"\",\n" +
            "          \"ProgramId\": \"\",\n" +
            "          \"Stars\": \"0\",\n" +
            "          \"LastModified\": \"\",\n" +
            "          \"ProgramFlags\": \"0\",\n" +
            "          \"Airdate\": \"\",\n" +
            "          \"Description\": \"\",\n" +
            "          \"Inetref\": \"\",\n" +
            "          \"Season\": \"0\",\n" +
            "          \"Episode\": \"0\",\n" +
            "          \"TotalEpisodes\": \"0\",\n" +
            "          \"FileSize\": \"0\",\n" +
            "          \"FileName\": \"\",\n" +
            "          \"HostName\": \"\",\n" +
            "          \"Channel\": {\n" +
            "            \"ChanId\": \"0\",\n" +
            "            \"ChanNum\": \"\",\n" +
            "            \"CallSign\": \"\",\n" +
            "            \"IconURL\": \"\",\n" +
            "            \"ChannelName\": \"\",\n" +
            "            \"MplexId\": \"0\",\n" +
            "            \"ServiceId\": \"0\",\n" +
            "            \"ATSCMajorChan\": \"0\",\n" +
            "            \"ATSCMinorChan\": \"0\",\n" +
            "            \"Format\": \"\",\n" +
            "            \"FrequencyId\": \"\",\n" +
            "            \"FineTune\": \"0\",\n" +
            "            \"ChanFilters\": \"\",\n" +
            "            \"SourceId\": \"0\",\n" +
            "            \"InputId\": \"0\",\n" +
            "            \"CommFree\": \"false\",\n" +
            "            \"UseEIT\": \"false\",\n" +
            "            \"Visible\": \"true\",\n" +
            "            \"XMLTVID\": \"\",\n" +
            "            \"DefaultAuth\": \"\",\n" +
            "            \"Programs\": []\n" +
            "          },\n" +
            "          \"Recording\": {\n" +
            "            \"RecordedId\": \"0\",\n" +
            "            \"Status\": \"0\",\n" +
            "            \"Priority\": \"0\",\n" +
            "            \"StartTs\": \"\",\n" +
            "            \"EndTs\": \"\",\n" +
            "            \"FileSize\": \"0\",\n" +
            "            \"FileName\": \"\",\n" +
            "            \"HostName\": \"\",\n" +
            "            \"LastModified\": \"\",\n" +
            "            \"RecordId\": \"0\",\n" +
            "            \"RecGroup\": \"\",\n" +
            "            \"PlayGroup\": \"\",\n" +
            "            \"StorageGroup\": \"\",\n" +
            "            \"RecType\": \"0\",\n" +
            "            \"DupInType\": \"1\",\n" +
            "            \"DupMethod\": \"1\",\n" +
            "            \"EncoderId\": \"0\",\n" +
            "            \"EncoderName\": \"\",\n" +
            "            \"Profile\": \"\"\n" +
            "          },\n" +
            "          \"Artwork\": {\n" +
            "            \"ArtworkInfos\": []\n" +
            "          },\n" +
            "          \"Cast\": {\n" +
            "            \"CastMembers\": []\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"Id\": \"7\",\n" +
            "        \"HostName\": \"mythcenter\",\n" +
            "        \"Local\": \"true\",\n" +
            "        \"Connected\": \"true\",\n" +
            "        \"State\": \"0\",\n" +
            "        \"SleepStatus\": \"8\",\n" +
            "        \"LowOnFreeSpace\": \"false\",\n" +
            "        \"Inputs\": [\n" +
            "          {\n" +
            "            \"Id\": \"7\",\n" +
            "            \"CardId\": \"7\",\n" +
            "            \"SourceId\": \"1\",\n" +
            "            \"InputName\": \"MPEG2TS\",\n" +
            "            \"DisplayName\": \"HDHR-4\",\n" +
            "            \"QuickTune\": \"false\",\n" +
            "            \"RecPriority\": \"0\",\n" +
            "            \"ScheduleOrder\": \"7\",\n" +
            "            \"LiveTVOrder\": \"7\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"Recording\": {\n" +
            "          \"StartTime\": \"\",\n" +
            "          \"EndTime\": \"\",\n" +
            "          \"Title\": \"\",\n" +
            "          \"SubTitle\": \"\",\n" +
            "          \"Category\": \"\",\n" +
            "          \"CatType\": \"\",\n" +
            "          \"Repeat\": \"false\",\n" +
            "          \"VideoProps\": \"0\",\n" +
            "          \"AudioProps\": \"0\",\n" +
            "          \"SubProps\": \"0\",\n" +
            "          \"SeriesId\": \"\",\n" +
            "          \"ProgramId\": \"\",\n" +
            "          \"Stars\": \"0\",\n" +
            "          \"LastModified\": \"\",\n" +
            "          \"ProgramFlags\": \"0\",\n" +
            "          \"Airdate\": \"\",\n" +
            "          \"Description\": \"\",\n" +
            "          \"Inetref\": \"\",\n" +
            "          \"Season\": \"0\",\n" +
            "          \"Episode\": \"0\",\n" +
            "          \"TotalEpisodes\": \"0\",\n" +
            "          \"FileSize\": \"0\",\n" +
            "          \"FileName\": \"\",\n" +
            "          \"HostName\": \"\",\n" +
            "          \"Channel\": {\n" +
            "            \"ChanId\": \"0\",\n" +
            "            \"ChanNum\": \"\",\n" +
            "            \"CallSign\": \"\",\n" +
            "            \"IconURL\": \"\",\n" +
            "            \"ChannelName\": \"\",\n" +
            "            \"MplexId\": \"0\",\n" +
            "            \"ServiceId\": \"0\",\n" +
            "            \"ATSCMajorChan\": \"0\",\n" +
            "            \"ATSCMinorChan\": \"0\",\n" +
            "            \"Format\": \"\",\n" +
            "            \"FrequencyId\": \"\",\n" +
            "            \"FineTune\": \"0\",\n" +
            "            \"ChanFilters\": \"\",\n" +
            "            \"SourceId\": \"0\",\n" +
            "            \"InputId\": \"0\",\n" +
            "            \"CommFree\": \"false\",\n" +
            "            \"UseEIT\": \"false\",\n" +
            "            \"Visible\": \"true\",\n" +
            "            \"XMLTVID\": \"\",\n" +
            "            \"DefaultAuth\": \"\",\n" +
            "            \"Programs\": []\n" +
            "          },\n" +
            "          \"Recording\": {\n" +
            "            \"RecordedId\": \"0\",\n" +
            "            \"Status\": \"0\",\n" +
            "            \"Priority\": \"0\",\n" +
            "            \"StartTs\": \"\",\n" +
            "            \"EndTs\": \"\",\n" +
            "            \"FileSize\": \"0\",\n" +
            "            \"FileName\": \"\",\n" +
            "            \"HostName\": \"\",\n" +
            "            \"LastModified\": \"\",\n" +
            "            \"RecordId\": \"0\",\n" +
            "            \"RecGroup\": \"\",\n" +
            "            \"PlayGroup\": \"\",\n" +
            "            \"StorageGroup\": \"\",\n" +
            "            \"RecType\": \"0\",\n" +
            "            \"DupInType\": \"1\",\n" +
            "            \"DupMethod\": \"1\",\n" +
            "            \"EncoderId\": \"0\",\n" +
            "            \"EncoderName\": \"\",\n" +
            "            \"Profile\": \"\"\n" +
            "          },\n" +
            "          \"Artwork\": {\n" +
            "            \"ArtworkInfos\": []\n" +
            "          },\n" +
            "          \"Cast\": {\n" +
            "            \"CastMembers\": []\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"Id\": \"9\",\n" +
            "        \"HostName\": \"mythcenter\",\n" +
            "        \"Local\": \"true\",\n" +
            "        \"Connected\": \"true\",\n" +
            "        \"State\": \"0\",\n" +
            "        \"SleepStatus\": \"8\",\n" +
            "        \"LowOnFreeSpace\": \"false\",\n" +
            "        \"Inputs\": [\n" +
            "          {\n" +
            "            \"Id\": \"9\",\n" +
            "            \"CardId\": \"9\",\n" +
            "            \"SourceId\": \"1\",\n" +
            "            \"InputName\": \"MPEG2TS\",\n" +
            "            \"DisplayName\": \"HDHR-5\",\n" +
            "            \"QuickTune\": \"false\",\n" +
            "            \"RecPriority\": \"0\",\n" +
            "            \"ScheduleOrder\": \"9\",\n" +
            "            \"LiveTVOrder\": \"9\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"Recording\": {\n" +
            "          \"StartTime\": \"\",\n" +
            "          \"EndTime\": \"\",\n" +
            "          \"Title\": \"\",\n" +
            "          \"SubTitle\": \"\",\n" +
            "          \"Category\": \"\",\n" +
            "          \"CatType\": \"\",\n" +
            "          \"Repeat\": \"false\",\n" +
            "          \"VideoProps\": \"0\",\n" +
            "          \"AudioProps\": \"0\",\n" +
            "          \"SubProps\": \"0\",\n" +
            "          \"SeriesId\": \"\",\n" +
            "          \"ProgramId\": \"\",\n" +
            "          \"Stars\": \"0\",\n" +
            "          \"LastModified\": \"\",\n" +
            "          \"ProgramFlags\": \"0\",\n" +
            "          \"Airdate\": \"\",\n" +
            "          \"Description\": \"\",\n" +
            "          \"Inetref\": \"\",\n" +
            "          \"Season\": \"0\",\n" +
            "          \"Episode\": \"0\",\n" +
            "          \"TotalEpisodes\": \"0\",\n" +
            "          \"FileSize\": \"0\",\n" +
            "          \"FileName\": \"\",\n" +
            "          \"HostName\": \"\",\n" +
            "          \"Channel\": {\n" +
            "            \"ChanId\": \"0\",\n" +
            "            \"ChanNum\": \"\",\n" +
            "            \"CallSign\": \"\",\n" +
            "            \"IconURL\": \"\",\n" +
            "            \"ChannelName\": \"\",\n" +
            "            \"MplexId\": \"0\",\n" +
            "            \"ServiceId\": \"0\",\n" +
            "            \"ATSCMajorChan\": \"0\",\n" +
            "            \"ATSCMinorChan\": \"0\",\n" +
            "            \"Format\": \"\",\n" +
            "            \"FrequencyId\": \"\",\n" +
            "            \"FineTune\": \"0\",\n" +
            "            \"ChanFilters\": \"\",\n" +
            "            \"SourceId\": \"0\",\n" +
            "            \"InputId\": \"0\",\n" +
            "            \"CommFree\": \"false\",\n" +
            "            \"UseEIT\": \"false\",\n" +
            "            \"Visible\": \"true\",\n" +
            "            \"XMLTVID\": \"\",\n" +
            "            \"DefaultAuth\": \"\",\n" +
            "            \"Programs\": []\n" +
            "          },\n" +
            "          \"Recording\": {\n" +
            "            \"RecordedId\": \"0\",\n" +
            "            \"Status\": \"0\",\n" +
            "            \"Priority\": \"0\",\n" +
            "            \"StartTs\": \"\",\n" +
            "            \"EndTs\": \"\",\n" +
            "            \"FileSize\": \"0\",\n" +
            "            \"FileName\": \"\",\n" +
            "            \"HostName\": \"\",\n" +
            "            \"LastModified\": \"\",\n" +
            "            \"RecordId\": \"0\",\n" +
            "            \"RecGroup\": \"\",\n" +
            "            \"PlayGroup\": \"\",\n" +
            "            \"StorageGroup\": \"\",\n" +
            "            \"RecType\": \"0\",\n" +
            "            \"DupInType\": \"1\",\n" +
            "            \"DupMethod\": \"1\",\n" +
            "            \"EncoderId\": \"0\",\n" +
            "            \"EncoderName\": \"\",\n" +
            "            \"Profile\": \"\"\n" +
            "          },\n" +
            "          \"Artwork\": {\n" +
            "            \"ArtworkInfos\": []\n" +
            "          },\n" +
            "          \"Cast\": {\n" +
            "            \"CastMembers\": []\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      {\n" +
            "        \"Id\": \"11\",\n" +
            "        \"HostName\": \"mythcenter\",\n" +
            "        \"Local\": \"true\",\n" +
            "        \"Connected\": \"true\",\n" +
            "        \"State\": \"0\",\n" +
            "        \"SleepStatus\": \"8\",\n" +
            "        \"LowOnFreeSpace\": \"false\",\n" +
            "        \"Inputs\": [\n" +
            "          {\n" +
            "            \"Id\": \"11\",\n" +
            "            \"CardId\": \"11\",\n" +
            "            \"SourceId\": \"1\",\n" +
            "            \"InputName\": \"MPEG2TS\",\n" +
            "            \"DisplayName\": \"HDHR-6\",\n" +
            "            \"QuickTune\": \"false\",\n" +
            "            \"RecPriority\": \"0\",\n" +
            "            \"ScheduleOrder\": \"11\",\n" +
            "            \"LiveTVOrder\": \"11\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"Recording\": {\n" +
            "          \"StartTime\": \"\",\n" +
            "          \"EndTime\": \"\",\n" +
            "          \"Title\": \"\",\n" +
            "          \"SubTitle\": \"\",\n" +
            "          \"Category\": \"\",\n" +
            "          \"CatType\": \"\",\n" +
            "          \"Repeat\": \"false\",\n" +
            "          \"VideoProps\": \"0\",\n" +
            "          \"AudioProps\": \"0\",\n" +
            "          \"SubProps\": \"0\",\n" +
            "          \"SeriesId\": \"\",\n" +
            "          \"ProgramId\": \"\",\n" +
            "          \"Stars\": \"0\",\n" +
            "          \"LastModified\": \"\",\n" +
            "          \"ProgramFlags\": \"0\",\n" +
            "          \"Airdate\": \"\",\n" +
            "          \"Description\": \"\",\n" +
            "          \"Inetref\": \"\",\n" +
            "          \"Season\": \"0\",\n" +
            "          \"Episode\": \"0\",\n" +
            "          \"TotalEpisodes\": \"0\",\n" +
            "          \"FileSize\": \"0\",\n" +
            "          \"FileName\": \"\",\n" +
            "          \"HostName\": \"\",\n" +
            "          \"Channel\": {\n" +
            "            \"ChanId\": \"0\",\n" +
            "            \"ChanNum\": \"\",\n" +
            "            \"CallSign\": \"\",\n" +
            "            \"IconURL\": \"\",\n" +
            "            \"ChannelName\": \"\",\n" +
            "            \"MplexId\": \"0\",\n" +
            "            \"ServiceId\": \"0\",\n" +
            "            \"ATSCMajorChan\": \"0\",\n" +
            "            \"ATSCMinorChan\": \"0\",\n" +
            "            \"Format\": \"\",\n" +
            "            \"FrequencyId\": \"\",\n" +
            "            \"FineTune\": \"0\",\n" +
            "            \"ChanFilters\": \"\",\n" +
            "            \"SourceId\": \"0\",\n" +
            "            \"InputId\": \"0\",\n" +
            "            \"CommFree\": \"false\",\n" +
            "            \"UseEIT\": \"false\",\n" +
            "            \"Visible\": \"true\",\n" +
            "            \"XMLTVID\": \"\",\n" +
            "            \"DefaultAuth\": \"\",\n" +
            "            \"Programs\": []\n" +
            "          },\n" +
            "          \"Recording\": {\n" +
            "            \"RecordedId\": \"0\",\n" +
            "            \"Status\": \"0\",\n" +
            "            \"Priority\": \"0\",\n" +
            "            \"StartTs\": \"\",\n" +
            "            \"EndTs\": \"\",\n" +
            "            \"FileSize\": \"0\",\n" +
            "            \"FileName\": \"\",\n" +
            "            \"HostName\": \"\",\n" +
            "            \"LastModified\": \"\",\n" +
            "            \"RecordId\": \"0\",\n" +
            "            \"RecGroup\": \"\",\n" +
            "            \"PlayGroup\": \"\",\n" +
            "            \"StorageGroup\": \"\",\n" +
            "            \"RecType\": \"0\",\n" +
            "            \"DupInType\": \"1\",\n" +
            "            \"DupMethod\": \"1\",\n" +
            "            \"EncoderId\": \"0\",\n" +
            "            \"EncoderName\": \"\",\n" +
            "            \"Profile\": \"\"\n" +
            "          },\n" +
            "          \"Artwork\": {\n" +
            "            \"ArtworkInfos\": []\n" +
            "          },\n" +
            "          \"Cast\": {\n" +
            "            \"CastMembers\": []\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}\n";

}