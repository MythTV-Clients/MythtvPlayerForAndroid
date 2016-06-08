package org.mythtv.android.data.cache.serializer;

import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.VideoMetadataInfoListEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VideoEntityListJsonSerializerTest extends ApplicationTestCase {

    private VideoListEntityJsonSerializer jsonSerializer;

    @Before
    public void setUp() {

        jsonSerializer = new VideoListEntityJsonSerializer( this.gson );

    }

    @Test
    public void testSerializeHappyCase() {

        VideoMetadataInfoListEntity videoListEntityOne = jsonSerializer.deserialize( JSON_RESPONSE );
        String jsonString = jsonSerializer.serialize( videoListEntityOne );
        VideoMetadataInfoListEntity videoListEntityTwo = jsonSerializer.deserialize( jsonString );

        assertThat( videoListEntityOne, is( videoListEntityTwo ) );
        assertThat( videoListEntityOne.getVideoMetadataInfosEntity(), is( videoListEntityTwo.getVideoMetadataInfosEntity() ) );
        assertThat( videoListEntityOne.getStartIndex(), is( videoListEntityTwo.getStartIndex() ) );
        assertThat( videoListEntityOne.getCount(), is( videoListEntityTwo.getCount() ) );
        assertThat( videoListEntityOne.getAsOf(), is( videoListEntityTwo.getAsOf() ) );
        assertThat( videoListEntityOne.getVersion(), is( videoListEntityTwo.getVersion() ) );
        assertThat( videoListEntityOne.getProtoVer(), is( videoListEntityTwo.getProtoVer() ) );

    }

    private static final String JSON_RESPONSE = "{\"VideoMetadataInfoList\": {\"StartIndex\": \"0\", \"Count\": \"2\", \"CurrentPage\": \"1\", \"TotalPages\": \"1\", \"TotalAvailable\": \"2\", \"AsOf\": \"2015-12-01T03:13:28Z\", \"Version\": \"0.28.20151024-1\", \"ProtoVer\": \"87\", \"VideoMetadataInfos\": [{\"Id\": \"1\", \"Title\": \"big buck bunny 1080p stereo\", \"SubTitle\": \"\", \"Tagline\": \"\", \"Director\": \"Unknown\", \"Studio\": \"\", \"Description\": \"None\", \"Certification\": \"NR\", \"Inetref\": \"00000000\", \"Collectionref\": \"-1\", \"HomePage\": \"\", \"ReleaseDate\": \"\", \"AddDate\": \"2015-01-05T05:00:00Z\", \"UserRating\": \"0\", \"Length\": \"0\", \"PlayCount\": \"0\", \"Season\": \"0\", \"Episode\": \"0\", \"ParentalLevel\": \"1\", \"Visible\": \"true\", \"Watched\": \"false\", \"Processed\": \"true\", \"ContentType\": \"MOVIE\", \"FileName\": \"library\\/Blender\\/big_buck_bunny_1080p_stereo.ogg\", \"Hash\": \"63ad35476c29214c\", \"HostName\": \"mythcenter\", \"Coverart\": \"\", \"Fanart\": \"\", \"Banner\": \"\", \"Screenshot\": \"\", \"Trailer\": \"\", \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}},{\"Id\": \"2\", \"Title\": \"tears of steel 1080p\", \"SubTitle\": \"\", \"Tagline\": \"\", \"Director\": \"Unknown\", \"Studio\": \"\", \"Description\": \"None\", \"Certification\": \"NR\", \"Inetref\": \"00000000\", \"Collectionref\": \"-1\", \"HomePage\": \"\", \"ReleaseDate\": \"\", \"AddDate\": \"2015-01-05T05:00:00Z\", \"UserRating\": \"0\", \"Length\": \"0\", \"PlayCount\": \"0\", \"Season\": \"0\", \"Episode\": \"0\", \"ParentalLevel\": \"1\", \"Visible\": \"true\", \"Watched\": \"false\", \"Processed\": \"true\", \"ContentType\": \"MOVIE\", \"FileName\": \"library\\/Blender\\/tears_of_steel_1080p.mkv\", \"Hash\": \"54220ffda5954294\", \"HostName\": \"mythcenter\", \"Coverart\": \"\", \"Fanart\": \"\", \"Banner\": \"\", \"Screenshot\": \"\", \"Trailer\": \"\", \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}]}}";

}