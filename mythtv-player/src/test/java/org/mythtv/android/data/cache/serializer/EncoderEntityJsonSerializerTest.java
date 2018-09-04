package org.mythtv.android.data.cache.serializer;

import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.EncoderEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class EncoderEntityJsonSerializerTest extends ApplicationTestCase {

    private EncoderEntityJsonSerializer jsonSerializer;

    @Before
    public void setUp() {

        jsonSerializer = new EncoderEntityJsonSerializer( this.gson );

    }

    @Test
    public void testSerializeHappyCase() {

        EncoderEntity encoderEntityOne = jsonSerializer.deserialize( JSON_RESPONSE );
        String jsonString = jsonSerializer.serialize( encoderEntityOne );
        EncoderEntity encoderEntityTwo = jsonSerializer.deserialize( jsonString );

        assertThat( encoderEntityOne.id(), is( equalTo( encoderEntityTwo.id() ) ) );
        assertThat( encoderEntityOne.hostname(), is( equalTo( encoderEntityTwo.hostname() ) ) );
        assertThat( encoderEntityOne.local(), is( equalTo( encoderEntityTwo.local() ) ) );
        assertThat( encoderEntityOne.connected(), is( equalTo( encoderEntityTwo.connected() ) ) );
        assertThat( encoderEntityOne.state(), is( equalTo( encoderEntityTwo.state() ) ) );
        assertThat( encoderEntityOne.sleepStatus(), is( equalTo( encoderEntityTwo.sleepStatus() ) ) );
        assertThat( encoderEntityOne.lowOnFreeSpace(), is( equalTo( encoderEntityTwo.lowOnFreeSpace() ) ) );

    }

    private static final String JSON_RESPONSE =
            "{\n" +
            "  \"Id\": \"1\",\n" +
            "  \"HostName\": \"mythcenter\",\n" +
            "  \"Local\": \"true\",\n" +
            "  \"Connected\": \"true\",\n" +
            "  \"State\": \"1\",\n" +
            "  \"SleepStatus\": \"8\",\n" +
            "  \"LowOnFreeSpace\": \"false\",\n" +
            "  \"Inputs\": [\n" +
            "    {\n" +
            "      \"Id\": \"1\",\n" +
            "      \"CardId\": \"1\",\n" +
            "      \"SourceId\": \"1\",\n" +
            "      \"InputName\": \"MPEG2TS\",\n" +
            "      \"DisplayName\": \"HDHRP-1\",\n" +
            "      \"QuickTune\": \"false\",\n" +
            "      \"RecPriority\": \"0\",\n" +
            "      \"ScheduleOrder\": \"1\",\n" +
            "      \"LiveTVOrder\": \"1\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"Recording\": {\n" +
            "    \"StartTime\": \"2016-01-18T14:00:00Z\",\n" +
            "    \"EndTime\": \"2016-01-18T15:00:00Z\",\n" +
            "    \"Title\": \"Live! With Kelly and Michael\",\n" +
            "    \"SubTitle\": \"\",\n" +
            "    \"Category\": \"Talk\",\n" +
            "    \"CatType\": \"series\",\n" +
            "    \"Repeat\": \"false\",\n" +
            "    \"VideoProps\": \"1\",\n" +
            "    \"AudioProps\": \"1\",\n" +
            "    \"SubProps\": \"1\",\n" +
            "    \"SeriesId\": \"EP01611848\",\n" +
            "    \"ProgramId\": \"EP016118480884\",\n" +
            "    \"Stars\": \"0\",\n" +
            "    \"LastModified\": \"2016-01-18T14:00:00Z\",\n" +
            "    \"ProgramFlags\": \"16777220\",\n" +
            "    \"Airdate\": \"2016-01-18\",\n" +
            "    \"Description\": \"\",\n" +
            "    \"Inetref\": \"\",\n" +
            "    \"Season\": \"0\",\n" +
            "    \"Episode\": \"0\",\n" +
            "    \"TotalEpisodes\": \"0\",\n" +
            "    \"FileSize\": \"0\",\n" +
            "    \"FileName\": \"/var/lib/mythtv/livetv/2006_20160118140000.ts\",\n" +
            "    \"HostName\": \"mythcenter\",\n" +
            "    \"Channel\": {\n" +
            "      \"ChanId\": \"2006\",\n" +
            "      \"ChanNum\": \"1006\",\n" +
            "      \"CallSign\": \"WPVIDT\",\n" +
            "      \"IconURL\": \"\",\n" +
            "      \"ChannelName\": \"WPVIDT (WPVI-DT)\",\n" +
            "      \"MplexId\": \"32767\",\n" +
            "      \"ServiceId\": \"0\",\n" +
            "      \"ATSCMajorChan\": \"1006\",\n" +
            "      \"ATSCMinorChan\": \"0\",\n" +
            "      \"Format\": \"Default\",\n" +
            "      \"FrequencyId\": \"1006\",\n" +
            "      \"FineTune\": \"0\",\n" +
            "      \"ChanFilters\": \"\",\n" +
            "      \"SourceId\": \"1\",\n" +
            "      \"InputId\": \"0\",\n" +
            "      \"CommFree\": \"false\",\n" +
            "      \"UseEIT\": \"false\",\n" +
            "      \"Visible\": \"true\",\n" +
            "      \"XMLTVID\": \"19612\",\n" +
            "      \"DefaultAuth\": \"\",\n" +
            "      \"Programs\": []\n" +
            "    },\n" +
            "    \"Recording\": {\n" +
            "      \"RecordedId\": \"5118\",\n" +
            "      \"Status\": \"-2\",\n" +
            "      \"Priority\": \"0\",\n" +
            "      \"StartTs\": \"2016-01-18T14:00:00Z\",\n" +
            "      \"EndTs\": \"2016-01-18T15:00:00Z\",\n" +
            "      \"FileSize\": \"3371762516\",\n" +
            "      \"FileName\": \"/var/lib/mythtv/livetv/2006_20160118140000.ts\",\n" +
            "      \"HostName\": \"mythcenter\",\n" +
            "      \"LastModified\": \"2016-01-18T14:00:00Z\",\n" +
            "      \"RecordId\": \"0\",\n" +
            "      \"RecGroup\": \"LiveTV\",\n" +
            "      \"PlayGroup\": \"Default\",\n" +
            "      \"StorageGroup\": \"LiveTV\",\n" +
            "      \"RecType\": \"0\",\n" +
            "      \"DupInType\": \"15\",\n" +
            "      \"DupMethod\": \"8\",\n" +
            "      \"EncoderId\": \"1\",\n" +
            "      \"EncoderName\": \"HDHRP-1\",\n" +
            "      \"Profile\": \"Default\"\n" +
            "    },\n" +
            "    \"Artwork\": {\n" +
            "       \"ArtworkInfos\": []\n" +
            "    },\n" +
            "    \"Cast\": {\n" +
            "       \"CastMembers\": [\n" +
            "         {\n" +
            "           \"Name\": \"Kelly Ripa\",\n" +
            "           \"CharacterName\": \"\",\n" +
            "           \"Role\": \"host\",\n" +
            "           \"TranslatedRole\": \"Host\"\n" +
            "         },\n" +
            "         {\n" +
            "           \"Name\": \"Michael Strahan\",\n" +
            "           \"CharacterName\": \"\",\n" +
            "           \"Role\": \"host\",\n" +
            "           \"TranslatedRole\": \"Host\"\n" +
            "         }\n" +
            "       ]\n" +
            "    }\n" +
            "  }\n" +
            "}\n";

    }