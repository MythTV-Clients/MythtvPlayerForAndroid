package org.mythtv.android.data.cache.serializer;

import org.mythtv.android.data.ApplicationTestCase;
import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VideoEntityJsonSerializerTest extends ApplicationTestCase {

    private VideoEntityJsonSerializer jsonSerializer;

    @Before
    public void setUp() {

        jsonSerializer = new VideoEntityJsonSerializer( this.gson );

    }

    @Test
    public void testSerializeHappyCase() {

        VideoMetadataInfoEntity videoEntityOne = jsonSerializer.deserialize( JSON_RESPONSE );
        String jsonString = jsonSerializer.serialize( videoEntityOne );
        VideoMetadataInfoEntity videoEntityTwo = jsonSerializer.deserialize( jsonString );

        assertThat( videoEntityOne.id(), is( videoEntityTwo.id() ) );
        assertThat( videoEntityOne.title(), is( videoEntityTwo.title() ) );
        assertThat( videoEntityOne.subTitle(), is( videoEntityTwo.subTitle() ) );
        assertThat( videoEntityOne.fileName(), is( videoEntityTwo.fileName() ) );

    }

    private static final String JSON_RESPONSE = "{\n" +
            "  \"VideoMetadataInfo\": {\n" +
            "    \"Id\": \"1\",\n" +
            "    \"Title\": \"big buck bunny 1080p stereo\",\n" +
            "    \"SubTitle\": \"\",\n" +
            "    \"Tagline\": \"\",\n" +
            "    \"Director\": \"Unknown\",\n" +
            "    \"Studio\": \"\",\n" +
            "    \"Description\": \"None\",\n" +
            "    \"Certification\": \"NR\",\n" +
            "    \"Inetref\": \"00000000\",\n" +
            "    \"Collectionref\": \"-1\",\n" +
            "    \"HomePage\": \"\",\n" +
            "    \"ReleaseDate\": \"\",\n" +
            "    \"AddDate\": \"2015-01-05T05:00:00Z\",\n" +
            "    \"UserRating\": \"0\",\n" +
            "    \"Length\": \"0\",\n" +
            "    \"PlayCount\": \"0\",\n" +
            "    \"Season\": \"0\",\n" +
            "    \"Episode\": \"0\",\n" +
            "    \"ParentalLevel\": \"1\",\n" +
            "    \"Visible\": \"true\",\n" +
            "    \"Watched\": \"false\",\n" +
            "    \"Processed\": \"true\",\n" +
            "    \"ContentType\": \"MOVIE\",\n" +
            "    \"FileName\": \"library/Blender/big_buck_bunny_1080p_stereo.ogg\",\n" +
            "    \"Hash\": \"63ad35476c29214c\",\n" +
            "    \"HostName\": \"mythcenter\",\n" +
            "    \"Coverart\": \"\",\n" +
            "    \"Fanart\": \"\",\n" +
            "    \"Banner\": \"\",\n" +
            "    \"Screenshot\": \"\",\n" +
            "    \"Trailer\": \"\",\n" +
            "    \"Artwork\": {\n" +
            "      \"ArtworkInfos\": []\n" +
            "    },\n" +
            "    \"Cast\": {\n" +
            "      \"CastMembers\": []\n" +
            "    }\n" +
            "  }\n" +
            "}\n";

    }