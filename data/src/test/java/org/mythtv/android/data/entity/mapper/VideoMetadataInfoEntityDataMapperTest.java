package org.mythtv.android.data.entity.mapper;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.ArtworkEntity;
import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.data.entity.CastEntity;
import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.Artwork;
import org.mythtv.android.domain.ArtworkInfo;
import org.mythtv.android.domain.Cast;
import org.mythtv.android.domain.CastMember;
import org.mythtv.android.domain.VideoMetadataInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by dmfrey on 9/18/15.
 */
public class VideoMetadataInfoEntityDataMapperTest extends ApplicationTestCase {

    private static final int FAKE_ID = 1;
    private static final String FAKE_TITLE = "fake title";
    private static final String FAKE_SUB_TITLE = "fake sub title";
    private static final String FAKE_TAGLINE = "fake tagline";
    private static final String FAKE_DIRECTOR = "fake director";
    private static final String FAKE_STUDIO = "fake studio";
    private static final String FAKE_DESCRIPTION = "fake description";
    private static final String FAKE_CERTIFICATION = "fake certification";
    private static final String FAKE_INETREF = "fake inetref";
    private static final int FAKE_COLLECTIONREF = 1;
    private static final String FAKE_HOMEPAGE = "fake homepage";
    private static final DateTime FAKE_RELEASE_DATE = new DateTime();
    private static final DateTime FAKE_ADD_DATE = new DateTime();
    private static final float FAKE_USER_RATING = 1.0f;
    private static final int FAKE_LENGTH = 1;
    private static final int FAKE_PLAY_COUNT = 1;
    private static final int FAKE_SEASON = 1;
    private static final int FAKE_EPISODE = 1;
    private static final int FAKE_PARENTAL_LEVEL = 1;
    private static final boolean FAKE_VISIBLE = true;
    private static final boolean FAKE_WATCHED = true;
    private static final boolean FAKE_PROCESSED = true;
    private static final String FAKE_CONTENT_TYPE = "fake content type";
    private static final String FAKE_FILENAME = "fake filename";
    private static final String FAKE_HASH = "fake hash";
    private static final String FAKE_HOSTNAME = "fake hostname";
    private static final String FAKE_COVERART = "fake coverart";
    private static final String FAKE_FANART = "fake fanart";
    private static final String FAKE_BANNER = "fake banner";
    private static final String FAKE_SCREENSHOT = "fake screenshot";
    private static final String FAKE_TRAILER = "fake trailer";

    @Test
    public void testTransformVideoMetadataInfoEntity() {

        VideoMetadataInfoEntity videoMetadataInfoEntity = createFakeVideoMetadataInfoEntity();
        VideoMetadataInfo videoMetadataInfo = VideoMetadataInfoEntityDataMapper.transform( videoMetadataInfoEntity );

        assertThat( videoMetadataInfo, is( instanceOf( VideoMetadataInfo.class ) ) );
        assertThat( videoMetadataInfo.getId(), is( FAKE_ID ) );
        assertThat( videoMetadataInfo.getTitle(), is( FAKE_TITLE ) );
        assertThat( videoMetadataInfo.getSubTitle(), is( FAKE_SUB_TITLE ) );
        assertThat( videoMetadataInfo.getTagline(), is( FAKE_TAGLINE ) );
        assertThat( videoMetadataInfo.getDirector(), is( FAKE_DIRECTOR ) );
        assertThat( videoMetadataInfo.getStudio(), is( FAKE_STUDIO ) );
        assertThat( videoMetadataInfo.getDescription(), is( FAKE_DESCRIPTION ) );
        assertThat( videoMetadataInfo.getCertification(), is( FAKE_CERTIFICATION ) );
        assertThat( videoMetadataInfo.getInetref(), is( FAKE_INETREF ) );
        assertThat( videoMetadataInfo.getCollectionref(), is( FAKE_COLLECTIONREF ) );
        assertThat( videoMetadataInfo.getHomePage(), is( FAKE_HOMEPAGE ) );
        assertThat( videoMetadataInfo.getReleaseDate(), is( FAKE_RELEASE_DATE ) );
        assertThat( videoMetadataInfo.getAddDate(), is( FAKE_ADD_DATE ) );
        assertThat( videoMetadataInfo.getUserRating(), is( FAKE_USER_RATING ) );
        assertThat( videoMetadataInfo.getLength(), is( FAKE_LENGTH ) );
        assertThat( videoMetadataInfo.getPlayCount(), is( FAKE_PLAY_COUNT ) );
        assertThat( videoMetadataInfo.getSeason(), is( FAKE_SEASON ) );
        assertThat( videoMetadataInfo.getEpisode(), is( FAKE_EPISODE ) );
        assertThat( videoMetadataInfo.getParentalLevel(), is( FAKE_PARENTAL_LEVEL ) );
        assertThat( videoMetadataInfo.isVisible(), is( FAKE_VISIBLE ) );
        assertThat( videoMetadataInfo.isWatched(), is( FAKE_WATCHED ) );
        assertThat( videoMetadataInfo.isProcessed(), is( FAKE_PROCESSED ) );
        assertThat( videoMetadataInfo.getContentType(), is( FAKE_CONTENT_TYPE ) );
        assertThat( videoMetadataInfo.getFileName(), is( FAKE_FILENAME ) );
        assertThat( videoMetadataInfo.getHash(), is( FAKE_HASH ) );
        assertThat( videoMetadataInfo.getHostName(), is( FAKE_HOSTNAME ) );
        assertThat( videoMetadataInfo.getCoverart(), is( FAKE_COVERART ) );
        assertThat( videoMetadataInfo.getFanart(), is( FAKE_FANART ) );
        assertThat( videoMetadataInfo.getBanner(), is( FAKE_BANNER ) );
        assertThat( videoMetadataInfo.getScreenshot(), is( FAKE_SCREENSHOT ) );
        assertThat( videoMetadataInfo.getTrailer(), is( FAKE_TRAILER ) );
        assertThat( videoMetadataInfo.getArtwork(), is( createFakeArtwork() ) );
        assertThat( videoMetadataInfo.getCast(), is( createFakeCast() ) );

    }

    @Test
    public void testTransformVideoMetadataInfoEntityCollection() {

        VideoMetadataInfoEntity mockVideoMetadataInfoEntityOne = mock( VideoMetadataInfoEntity.class );
        VideoMetadataInfoEntity mockVideoMetadataInfoEntityTwo = mock( VideoMetadataInfoEntity.class );

        List<VideoMetadataInfoEntity> videoMetadataInfoEntityList = new ArrayList<>( 5 );
        videoMetadataInfoEntityList.add(mockVideoMetadataInfoEntityOne);
        videoMetadataInfoEntityList.add( mockVideoMetadataInfoEntityTwo );

        Collection<VideoMetadataInfo> videoMetadataInfoCollection = VideoMetadataInfoEntityDataMapper.transform( videoMetadataInfoEntityList );

        assertThat( videoMetadataInfoCollection.toArray()[ 0 ], is( instanceOf( VideoMetadataInfo.class ) ) );
        assertThat( videoMetadataInfoCollection.toArray()[ 1 ], is( instanceOf( VideoMetadataInfo.class ) ) );
        assertThat( videoMetadataInfoCollection.size(), is( 2 ) );

    }

    private VideoMetadataInfoEntity createFakeVideoMetadataInfoEntity() {

        VideoMetadataInfoEntity videoMetadataInfoEntity = new VideoMetadataInfoEntity();
        videoMetadataInfoEntity.setId( FAKE_ID );
        videoMetadataInfoEntity.setTitle(FAKE_TITLE );
        videoMetadataInfoEntity.setSubTitle(FAKE_SUB_TITLE );
        videoMetadataInfoEntity.setTagline(FAKE_TAGLINE );
        videoMetadataInfoEntity.setDirector(FAKE_DIRECTOR );
        videoMetadataInfoEntity.setStudio(FAKE_STUDIO );
        videoMetadataInfoEntity.setDescription(FAKE_DESCRIPTION );
        videoMetadataInfoEntity.setCertification(FAKE_CERTIFICATION );
        videoMetadataInfoEntity.setInetref(FAKE_INETREF );
        videoMetadataInfoEntity.setCollectionref(FAKE_COLLECTIONREF );
        videoMetadataInfoEntity.setHomePage(FAKE_HOMEPAGE );
        videoMetadataInfoEntity.setReleaseDate(FAKE_RELEASE_DATE );
        videoMetadataInfoEntity.setAddDate(FAKE_ADD_DATE );
        videoMetadataInfoEntity.setUserRating(FAKE_USER_RATING );
        videoMetadataInfoEntity.setLength(FAKE_LENGTH );
        videoMetadataInfoEntity.setPlayCount(FAKE_PLAY_COUNT );
        videoMetadataInfoEntity.setSeason(FAKE_SEASON );
        videoMetadataInfoEntity.setEpisode(FAKE_EPISODE );
        videoMetadataInfoEntity.setParentalLevel(FAKE_PARENTAL_LEVEL );
        videoMetadataInfoEntity.setVisible(FAKE_VISIBLE );
        videoMetadataInfoEntity.setWatched(FAKE_WATCHED );
        videoMetadataInfoEntity.setProcessed(FAKE_PROCESSED );
        videoMetadataInfoEntity.setContentType(FAKE_CONTENT_TYPE );
        videoMetadataInfoEntity.setFileName(FAKE_FILENAME );
        videoMetadataInfoEntity.setHash(FAKE_HASH );
        videoMetadataInfoEntity.setHostName(FAKE_HOSTNAME );
        videoMetadataInfoEntity.setCoverart(FAKE_COVERART );
        videoMetadataInfoEntity.setFanart(FAKE_FANART );
        videoMetadataInfoEntity.setBanner(FAKE_BANNER );
        videoMetadataInfoEntity.setScreenshot(FAKE_SCREENSHOT );
        videoMetadataInfoEntity.setTrailer(FAKE_TRAILER );
        videoMetadataInfoEntity.setArtwork( createFakeArtworkEntity() );
        videoMetadataInfoEntity.setCast( createFakeCastEntity() );

        return videoMetadataInfoEntity;
    }

    private ArtworkEntity createFakeArtworkEntity() {

        ArtworkEntity artworkEntity = new ArtworkEntity();

        ArtworkInfoEntity[] artworkInfoEntities = new ArtworkInfoEntity[ 1 ];
        artworkInfoEntities[ 0 ] = new ArtworkInfoEntity();
        artworkInfoEntities[ 0 ].setStorageGroup("fake storage group");
        artworkInfoEntities[ 0 ].setType("fake type");
        artworkInfoEntities[ 0 ].setUrl( "fake url" );
        artworkInfoEntities[ 0 ].setFileName( "fake filename" );
        artworkEntity.setArtworkInfos( artworkInfoEntities );

        return artworkEntity;
    }

    private Artwork createFakeArtwork() {

        Artwork artwork = new Artwork();

        ArtworkInfo[] artworkInfos = new ArtworkInfo[ 1 ];
        artworkInfos[ 0 ] = new ArtworkInfo();
        artworkInfos[ 0 ].setStorageGroup("fake storage group");
        artworkInfos[ 0 ].setType("fake type");
        artworkInfos[ 0 ].setUrl("fake url");
        artworkInfos[ 0 ].setFileName( "fake filename" );
        artwork.setArtworkInfos( artworkInfos );

        return artwork;
    }

    private CastEntity createFakeCastEntity() {

        CastEntity castEntity = new CastEntity();

        CastMemberEntity[] castMemberEntities = new CastMemberEntity[ 1 ];
        castMemberEntities[ 0 ] = new CastMemberEntity();
        castMemberEntities[ 0 ].setCharacterName("fake character name");
        castMemberEntities[0].setRole("fake role");
        castMemberEntities[0].setName("fake name");
        castMemberEntities[ 0 ].setTranslatedRole("fake translated role");
        castEntity.setCastMembers(castMemberEntities);

        return castEntity;
    }

    private Cast createFakeCast() {

        Cast cast = new Cast();

        CastMember[] castMembers = new CastMember[ 1 ];
        castMembers[ 0 ] = new CastMember();
        castMembers[ 0 ].setCharacterName( "fake character name" );
        castMembers[0].setRole( "fake role" );
        castMembers[0].setName( "fake name" );
        castMembers[ 0 ].setTranslatedRole( "fake translated role" );
        cast.setCastMembers( castMembers );

        return cast;
    }

}
