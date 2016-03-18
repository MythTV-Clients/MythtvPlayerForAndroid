package org.mythtv.android.data.entity.mapper;

import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
public class VideoMetadataInfoEntityJsonDataMapperTest extends ApplicationTestCase {

    private static final String JSON_RESPONSE_VIDEO_GET_VIDEO = "{\"VideoMetadataInfo\":{\"Id\": \"1\", \"Title\": \"big buck bunny 1080p stereo\", \"SubTitle\": \"\", \"Tagline\": \"\", \"Director\": \"Unknown\", \"Studio\": \"\", \"Description\": \"None\", \"Certification\": \"NR\", \"Inetref\": \"00000000\", \"Collectionref\": \"-1\", \"HomePage\": \"\", \"ReleaseDate\": \"\", \"AddDate\": \"2015-01-05T05:00:00Z\", \"UserRating\": \"0\", \"Length\": \"0\", \"PlayCount\": \"0\", \"Season\": \"0\", \"Episode\": \"0\", \"ParentalLevel\": \"1\", \"Visible\": \"true\", \"Watched\": \"false\", \"Processed\": \"true\", \"ContentType\": \"MOVIE\", \"FileName\": \"library\\/Blender\\/big_buck_bunny_1080p_stereo.ogg\", \"Hash\": \"63ad35476c29214c\", \"HostName\": \"mythcenter\", \"Coverart\": \"\", \"Fanart\": \"\", \"Banner\": \"\", \"Screenshot\": \"\", \"Trailer\": \"\", \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}}";
    private static final String JSON_RESPONSE_VIDEO_GET_VIDEO_BAD = "{\"Id\": \"1\", \"Title\": \"big buck bunny 1080p stereo\", \"SubTitle\": \"\", \"Tagline\": \"\", \"Director\": \"Unknown\", \"Studio\": \"\", \"Description\": \"None\", \"Certification\": \"NR\", \"Inetref\": \"00000000\", \"Collectionref\": \"-1\", \"HomePage\": \"\", \"ReleaseDate\": \"\", \"AddDate\": \"2015-01-05T05:00:00Z\", \"UserRating\": \"0\", \"Length\": \"0\", \"PlayCount\": \"0\", \"Season\": \"0\", \"Episode\": \"0\", \"ParentalLevel\": \"1\", \"Visible\": \"true\", \"Watched\": \"false\", \"Processed\": \"true\", \"ContentType\": \"MOVIE\", \"FileName\": \"library\\/Blender\\/big_buck_bunny_1080p_stereo.ogg\", \"Hash\": \"63ad35476c29214c\", \"HostName\": \"mythcenter\", \"Coverart\": \"\", \"Fanart\": \"\", \"Banner\": \"\", \"Screenshot\": \"\", \"Trailer\": \"\", \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []";
    private static final String JSON_RESPONSE_VIDEO_GET_VIDEO_LIST = "{\"VideoMetadataInfoList\": {\"StartIndex\": \"0\", \"Count\": \"2\", \"CurrentPage\": \"1\", \"TotalPages\": \"1\", \"TotalAvailable\": \"2\", \"AsOf\": \"2015-11-01T21:22:22Z\", \"Version\": \"0.28.20150906-1\", \"ProtoVer\": \"87\", \"VideoMetadataInfos\": [{\"Id\": \"1\", \"Title\": \"big buck bunny 1080p stereo\", \"SubTitle\": \"\", \"Tagline\": \"\", \"Director\": \"Unknown\", \"Studio\": \"\", \"Description\": \"None\", \"Certification\": \"NR\", \"Inetref\": \"00000000\", \"Collectionref\": \"-1\", \"HomePage\": \"\", \"ReleaseDate\": \"\", \"AddDate\": \"2015-01-05T05:00:00Z\", \"UserRating\": \"0\", \"Length\": \"0\", \"PlayCount\": \"0\", \"Season\": \"0\", \"Episode\": \"0\", \"ParentalLevel\": \"1\", \"Visible\": \"true\", \"Watched\": \"false\", \"Processed\": \"true\", \"ContentType\": \"MOVIE\", \"FileName\": \"library\\/Blender\\/big_buck_bunny_1080p_stereo.ogg\", \"Hash\": \"63ad35476c29214c\", \"HostName\": \"mythcenter\", \"Coverart\": \"\", \"Fanart\": \"\", \"Banner\": \"\", \"Screenshot\": \"\", \"Trailer\": \"\", \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}},{\"Id\": \"2\", \"Title\": \"tears of steel 1080p\", \"SubTitle\": \"\", \"Tagline\": \"\", \"Director\": \"Unknown\", \"Studio\": \"\", \"Description\": \"None\", \"Certification\": \"NR\", \"Inetref\": \"00000000\", \"Collectionref\": \"-1\", \"HomePage\": \"\", \"ReleaseDate\": \"\", \"AddDate\": \"2015-01-05T05:00:00Z\", \"UserRating\": \"0\", \"Length\": \"0\", \"PlayCount\": \"0\", \"Season\": \"0\", \"Episode\": \"0\", \"ParentalLevel\": \"1\", \"Visible\": \"true\", \"Watched\": \"false\", \"Processed\": \"true\", \"ContentType\": \"MOVIE\", \"FileName\": \"library\\/Blender\\/tears_of_steel_1080p.mkv\", \"Hash\": \"54220ffda5954294\", \"HostName\": \"mythcenter\", \"Coverart\": \"\", \"Fanart\": \"\", \"Banner\": \"\", \"Screenshot\": \"\", \"Trailer\": \"\", \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}}]}}";
    private static final String JSON_RESPONSE_VIDEO_GET_VIDEO_LIST_BAD = "{\"VideoMetadataInfoList\": {\"StartIndex\": \"0\", \"Count\": \"2\", \"CurrentPage\": \"1\", \"TotalPages\": \"1\", \"TotalAvailable\": \"2\", \"AsOf\": \"2015-11-01T21:22:22Z\", \"Version\": \"0.28.20150906-1\", \"ProtoVer\": \"87\", \"VideoMetadataInfos\": [{\"Id\": \"1\", \"Title\": \"big buck bunny 1080p stereo\", \"SubTitle\": \"\", \"Tagline\": \"\", \"Director\": \"Unknown\", \"Studio\": \"\", \"Description\": \"None\", \"Certification\": \"NR\", \"Inetref\": \"00000000\", \"Collectionref\": \"-1\", \"HomePage\": \"\", \"ReleaseDate\": \"\", \"AddDate\": \"2015-01-05T05:00:00Z\", \"UserRating\": \"0\", \"Length\": \"0\", \"PlayCount\": \"0\", \"Season\": \"0\", \"Episode\": \"0\", \"ParentalLevel\": \"1\", \"Visible\": \"true\", \"Watched\": \"false\", \"Processed\": \"true\", \"ContentType\": \"MOVIE\", \"FileName\": \"library\\/Blender\\/big_buck_bunny_1080p_stereo.ogg\", \"Hash\": \"63ad35476c29214c\", \"HostName\": \"mythcenter\", \"Coverart\": \"\", \"Fanart\": \"\", \"Banner\": \"\", \"Screenshot\": \"\", \"Trailer\": \"\", \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []}},{\"Id\": \"2\", \"Title\": \"tears of steel 1080p\", \"SubTitle\": \"\", \"Tagline\": \"\", \"Director\": \"Unknown\", \"Studio\": \"\", \"Description\": \"None\", \"Certification\": \"NR\", \"Inetref\": \"00000000\", \"Collectionref\": \"-1\", \"HomePage\": \"\", \"ReleaseDate\": \"\", \"AddDate\": \"2015-01-05T05:00:00Z\", \"UserRating\": \"0\", \"Length\": \"0\", \"PlayCount\": \"0\", \"Season\": \"0\", \"Episode\": \"0\", \"ParentalLevel\": \"1\", \"Visible\": \"true\", \"Watched\": \"false\", \"Processed\": \"true\", \"ContentType\": \"MOVIE\", \"FileName\": \"library\\/Blender\\/tears_of_steel_1080p.mkv\", \"Hash\": \"54220ffda5954294\", \"HostName\": \"mythcenter\", \"Coverart\": \"\", \"Fanart\": \"\", \"Banner\": \"\", \"Screenshot\": \"\", \"Trailer\": \"\", \"Artwork\": {\"ArtworkInfos\": []}, \"Cast\": {\"CastMembers\": []";

    private VideoMetadataInfoEntityJsonMapper videoMetadataInfoEntityJsonMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        videoMetadataInfoEntityJsonMapper = new VideoMetadataInfoEntityJsonMapper();

    }

    @Test
    public void testTransformVideoMetadataInfoEntityHappyCase() {

        VideoMetadataInfoEntity videoMetadataInfoEntity = videoMetadataInfoEntityJsonMapper.transformVideoMetadataInfoEntity( JSON_RESPONSE_VIDEO_GET_VIDEO );

        assertThat( videoMetadataInfoEntity.getId(), is( 1 ) );
        assertThat( videoMetadataInfoEntity.getTitle(), is( "big buck bunny 1080p stereo" ) );
        assertThat( videoMetadataInfoEntity.getSubTitle(), is( "" ) );
        assertThat( videoMetadataInfoEntity.getInetref(), is( "00000000" ) );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformVideoMetadataInfoEntityBadJson() {

        videoMetadataInfoEntityJsonMapper.transformVideoMetadataInfoEntity( JSON_RESPONSE_VIDEO_GET_VIDEO_BAD );

    }

    @Test
    public void testTransformVideoMetadataInfoEntityCollectionHappyCase() {

        Collection<VideoMetadataInfoEntity> videoMetadataInfoEntityCollection = videoMetadataInfoEntityJsonMapper.transformVideoMetadataInfoEntityCollection( JSON_RESPONSE_VIDEO_GET_VIDEO_LIST );

        assertThat( ( (VideoMetadataInfoEntity) videoMetadataInfoEntityCollection.toArray() [ 0 ] ).getId(), is( 1 ) );
        assertThat( ( (VideoMetadataInfoEntity) videoMetadataInfoEntityCollection.toArray() [ 0 ] ).getTitle(), is( "big buck bunny 1080p stereo" ) );
        assertThat( ( (VideoMetadataInfoEntity) videoMetadataInfoEntityCollection.toArray() [ 0 ] ).getInetref(), is( "00000000" ) );
        assertThat( ( (VideoMetadataInfoEntity) videoMetadataInfoEntityCollection.toArray() [ 1 ] ).getId(), is( 2 ) );
        assertThat( ( (VideoMetadataInfoEntity) videoMetadataInfoEntityCollection.toArray() [ 1 ] ).getTitle(), is( "tears of steel 1080p" ) );
        assertThat( ( (VideoMetadataInfoEntity) videoMetadataInfoEntityCollection.toArray() [ 1 ] ).getInetref(), is( "00000000" ) );
        assertThat( videoMetadataInfoEntityCollection.size(), is( 2 ) );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformVideoMetadataInfoEntityCollectionBadJson() {

        videoMetadataInfoEntityJsonMapper.transformVideoMetadataInfoEntityCollection( JSON_RESPONSE_VIDEO_GET_VIDEO_LIST_BAD );

    }

}
