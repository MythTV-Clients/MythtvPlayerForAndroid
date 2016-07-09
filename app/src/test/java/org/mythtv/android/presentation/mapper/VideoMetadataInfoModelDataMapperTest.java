package org.mythtv.android.presentation.mapper;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.mythtv.android.domain.Artwork;
import org.mythtv.android.domain.ArtworkInfo;
import org.mythtv.android.domain.Cast;
import org.mythtv.android.domain.CastMember;
import org.mythtv.android.domain.VideoMetadataInfo;
import org.mythtv.android.presentation.mapper.VideoMetadataInfoModelDataMapper;
import org.mythtv.android.presentation.model.ArtworkInfoModel;
import org.mythtv.android.presentation.model.ArtworkModel;
import org.mythtv.android.presentation.model.CastMemberModel;
import org.mythtv.android.presentation.model.CastModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by dmfrey on 9/18/15.
 */
public class VideoMetadataInfoModelDataMapperTest extends TestCase {

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

    private VideoMetadataInfoModelDataMapper videoMetadataInfoModelDataMapper;

    protected void setUp() throws Exception {
        super.setUp();

        videoMetadataInfoModelDataMapper = new VideoMetadataInfoModelDataMapper();

    }

    public void testTransformVideoMetadataInfo() {

        VideoMetadataInfo videoMetadataInfo = createFakeVideoMetadataInfo();
        VideoMetadataInfoModel videoMetadataInfoModel = videoMetadataInfoModelDataMapper.transform( videoMetadataInfo );

        assertThat( videoMetadataInfoModel, is( instanceOf( VideoMetadataInfoModel.class ) ) );
        assertThat( videoMetadataInfoModel.getId(), is( FAKE_ID ) );
        assertThat( videoMetadataInfoModel.getTitle(), is( FAKE_TITLE ) );
        assertThat( videoMetadataInfoModel.getSubTitle(), is( FAKE_SUB_TITLE ) );
        assertThat( videoMetadataInfoModel.getTagline(), is( FAKE_TAGLINE ) );
        assertThat( videoMetadataInfoModel.getDirector(), is( FAKE_DIRECTOR ) );
        assertThat( videoMetadataInfoModel.getStudio(), is( FAKE_STUDIO ) );
        assertThat( videoMetadataInfoModel.getDescription(), is( FAKE_DESCRIPTION ) );
        assertThat( videoMetadataInfoModel.getCertification(), is( FAKE_CERTIFICATION ) );
        assertThat( videoMetadataInfoModel.getInetref(), is( FAKE_INETREF ) );
        assertThat( videoMetadataInfoModel.getCollectionref(), is( FAKE_COLLECTIONREF ) );
        assertThat( videoMetadataInfoModel.getHomePage(), is( FAKE_HOMEPAGE ) );
        assertThat( videoMetadataInfoModel.getReleaseDate(), is( FAKE_RELEASE_DATE ) );
        assertThat( videoMetadataInfoModel.getAddDate(), is( FAKE_ADD_DATE ) );
        assertThat( videoMetadataInfoModel.getUserRating(), is( FAKE_USER_RATING ) );
        assertThat( videoMetadataInfoModel.getLength(), is( FAKE_LENGTH ) );
        assertThat( videoMetadataInfoModel.getPlayCount(), is( FAKE_PLAY_COUNT ) );
        assertThat( videoMetadataInfoModel.getSeason(), is( FAKE_SEASON ) );
        assertThat( videoMetadataInfoModel.getEpisode(), is( FAKE_EPISODE ) );
        assertThat( videoMetadataInfoModel.getParentalLevel(), is( FAKE_PARENTAL_LEVEL ) );
        assertThat( videoMetadataInfoModel.isVisible(), is( FAKE_VISIBLE ) );
        assertThat( videoMetadataInfoModel.isWatched(), is( FAKE_WATCHED ) );
        assertThat( videoMetadataInfoModel.isProcessed(), is( FAKE_PROCESSED ) );
        assertThat( videoMetadataInfoModel.getContentType(), is( FAKE_CONTENT_TYPE ) );
        assertThat( videoMetadataInfoModel.getFileName(), is( FAKE_FILENAME ) );
        assertThat( videoMetadataInfoModel.getHash(), is( FAKE_HASH ) );
        assertThat( videoMetadataInfoModel.getHostName(), is( FAKE_HOSTNAME ) );
        assertThat( videoMetadataInfoModel.getCoverart(), is( FAKE_COVERART ) );
        assertThat( videoMetadataInfoModel.getFanart(), is( FAKE_FANART ) );
        assertThat( videoMetadataInfoModel.getBanner(), is( FAKE_BANNER ) );
        assertThat( videoMetadataInfoModel.getScreenshot(), is( FAKE_SCREENSHOT ) );
        assertThat( videoMetadataInfoModel.getTrailer(), is( FAKE_TRAILER ) );
        assertThat( videoMetadataInfoModel.getArtwork(), is( createFakeArtworkModel() ) );
        assertThat( videoMetadataInfoModel.getCast(), is( createFakeCastModel() ) );

    }

    public void testTransformVideoMetadataInfoCollection() {

        VideoMetadataInfo mockVideoMetadataInfoOne = mock( VideoMetadataInfo.class );
        VideoMetadataInfo mockVideoMetadataInfoTwo = mock( VideoMetadataInfo.class );

        List<VideoMetadataInfo> videoMetadataInfoList = new ArrayList<>( 5 );
        videoMetadataInfoList.add( mockVideoMetadataInfoOne );
        videoMetadataInfoList.add( mockVideoMetadataInfoTwo );

        Collection<VideoMetadataInfoModel> videoMetadataInfoModelCollection = videoMetadataInfoModelDataMapper.transform( videoMetadataInfoList );

        assertThat( videoMetadataInfoModelCollection.toArray()[ 0 ], is( instanceOf( VideoMetadataInfoModel.class ) ) );
        assertThat( videoMetadataInfoModelCollection.toArray()[ 1 ], is( instanceOf( VideoMetadataInfoModel.class ) ) );
        assertThat( videoMetadataInfoModelCollection.size(), is( 2 ) );

    }

    private VideoMetadataInfo createFakeVideoMetadataInfo() {

        VideoMetadataInfo videoMetadataInfo = new VideoMetadataInfo();
        videoMetadataInfo.setId( FAKE_ID );
        videoMetadataInfo.setTitle(FAKE_TITLE );
        videoMetadataInfo.setSubTitle(FAKE_SUB_TITLE );
        videoMetadataInfo.setTagline(FAKE_TAGLINE );
        videoMetadataInfo.setDirector(FAKE_DIRECTOR );
        videoMetadataInfo.setStudio(FAKE_STUDIO );
        videoMetadataInfo.setDescription(FAKE_DESCRIPTION );
        videoMetadataInfo.setCertification(FAKE_CERTIFICATION );
        videoMetadataInfo.setInetref(FAKE_INETREF );
        videoMetadataInfo.setCollectionref(FAKE_COLLECTIONREF );
        videoMetadataInfo.setHomePage(FAKE_HOMEPAGE );
        videoMetadataInfo.setReleaseDate(FAKE_RELEASE_DATE );
        videoMetadataInfo.setAddDate(FAKE_ADD_DATE );
        videoMetadataInfo.setUserRating(FAKE_USER_RATING );
        videoMetadataInfo.setLength(FAKE_LENGTH );
        videoMetadataInfo.setPlayCount(FAKE_PLAY_COUNT );
        videoMetadataInfo.setSeason(FAKE_SEASON );
        videoMetadataInfo.setEpisode(FAKE_EPISODE );
        videoMetadataInfo.setParentalLevel(FAKE_PARENTAL_LEVEL );
        videoMetadataInfo.setVisible(FAKE_VISIBLE );
        videoMetadataInfo.setWatched(FAKE_WATCHED );
        videoMetadataInfo.setProcessed(FAKE_PROCESSED );
        videoMetadataInfo.setContentType(FAKE_CONTENT_TYPE );
        videoMetadataInfo.setFileName(FAKE_FILENAME );
        videoMetadataInfo.setHash(FAKE_HASH );
        videoMetadataInfo.setHostName(FAKE_HOSTNAME );
        videoMetadataInfo.setCoverart(FAKE_COVERART );
        videoMetadataInfo.setFanart(FAKE_FANART );
        videoMetadataInfo.setBanner(FAKE_BANNER );
        videoMetadataInfo.setScreenshot(FAKE_SCREENSHOT );
        videoMetadataInfo.setTrailer(FAKE_TRAILER );
        videoMetadataInfo.setArtwork( createFakeArtwork() );
        videoMetadataInfo.setCast( createFakeCast() );

        return videoMetadataInfo;
    }

    private Artwork createFakeArtwork() {

        Artwork artwork = new Artwork();

        ArtworkInfo[] artworkInfos = new ArtworkInfo[ 1 ];
        artworkInfos[ 0 ] = new ArtworkInfo();
        artworkInfos[ 0 ].setStorageGroup("fake storage group");
        artworkInfos[ 0 ].setType("fake type");
        artworkInfos[ 0 ].setUrl( "fake url" );
        artworkInfos[ 0 ].setFileName( "fake filename" );
        artwork.setArtworkInfos( artworkInfos );

        return artwork;
    }

    private ArtworkModel createFakeArtworkModel() {

        ArtworkModel artworkModel = new ArtworkModel();

        ArtworkInfoModel[] artworkInfoModels = new ArtworkInfoModel[ 1 ];
        artworkInfoModels[ 0 ] = new ArtworkInfoModel();
        artworkInfoModels[ 0 ].setStorageGroup("fake storage group");
        artworkInfoModels[ 0 ].setType("fake type");
        artworkInfoModels[ 0 ].setUrl("fake url");
        artworkInfoModels[ 0 ].setFileName( "fake filename" );
        artworkModel.setArtworkInfos( artworkInfoModels );

        return artworkModel;
    }

    private Cast createFakeCast() {

        Cast cast = new Cast();

        CastMember[] castMembers = new CastMember[ 1 ];
        castMembers[ 0 ] = new CastMember();
        castMembers[ 0 ].setCharacterName( "fake character name" );
        castMembers[ 0 ].setRole( "fake role" );
        castMembers[ 0 ].setName( "fake name" );
        castMembers[ 0 ].setTranslatedRole( "fake translated role" );
        cast.setCastMembers( castMembers );

        return cast;
    }

    private CastModel createFakeCastModel() {

        CastModel castModel = new CastModel();

        CastMemberModel[] castMemberModels = new CastMemberModel[ 1 ];
        castMemberModels[ 0 ] = new CastMemberModel();
        castMemberModels[ 0 ].setCharacterName( "fake character name" );
        castMemberModels[ 0 ].setRole( "fake role" );
        castMemberModels[ 0 ].setName( "fake name" );
        castMemberModels[ 0 ].setTranslatedRole( "fake translated role" );
        castModel.setCastMembers( castMemberModels );

        return castModel;
    }

}
