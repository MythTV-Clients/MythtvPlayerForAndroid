package org.mythtv.android.data.entity.mapper;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.entity.ArtworkEntity;
import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.data.entity.CastEntity;
import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.data.entity.ChannelInfoEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.RecordingInfoEntity;
import org.mythtv.android.data.entity.SearchResultEntity;
import org.mythtv.android.domain.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 10/10/15.
 */
public class SearchResultEntityDataMapperTest {

    private static final int FAKE_CHAN_ID = 1;
    private static final long FAKE_START_TIME = new DateTime().getMillis();
    private static final String FAKE_TITLE = "fake title";
    private static final String FAKE_SUB_TITLE = "fake sub title";
    private static final String FAKE_CATEGORY = "fake category";
    private static final String FAKE_CALLSIGN = "fake callsign";
    private static final String FAKE_CHANNEL_NUMBER = "fake channel number";
    private static final String FAKE_DESCRIPTION = "fake description";
    private static final String FAKE_INETREF = "fake inetref";
    private static final int FAKE_SEASON = 1;
    private static final int FAKE_EPISODE = 1;
    private static final String FAKE_CAST_MEMBERS = "fake cast members";
    private static final String FAKE_CHARACTERS = "fake characters";
    private static final String FAKE_RATING = "fake rating";
    private static final SearchResult.Type FAKE_TYPE = SearchResult.Type.RECORDING;

    private static final DateTime FAKE_PROGRAM_START_TIME = new DateTime();
    private static final DateTime FAKE_PROGRAM_END_TIME = new DateTime();
    private static final String FAKE_PROGRAM_TITLE = "fake title";
    private static final String FAKE_PROGRAM_SUB_TITLE = "fake sub title";
    private static final String FAKE_PROGRAM_CATEGORY = "fake category";
    private static final String FAKE_PROGRAM_CATTYPE = "fake catType";
    private static final boolean FAKE_PROGRAM_REPEAT = false;
    private static final int FAKE_PROGRAM_VIDEOPROPS = 1;
    private static final int FAKE_PROGRAM_AUDIOPROPS = 1;
    private static final int FAKE_PROGRAM_SUBPROPS = 1;
    private static final String FAKE_PROGRAM_SERIESID = "fake seriesId";
    private static final String FAKE_PROGRAM_PROGRAMID = "fake programId";
    private static final double FAKE_PROGRAM_STARS = 1.0;
    private static final long FAKE_PROGRAM_FILESIZE = 1l;
    private static final DateTime FAKE_PROGRAM_LASTMODIFIED = new DateTime();
    private static final int FAKE_PROGRAM_PROGRAMFLAGS = 1;
    private static final String FAKE_PROGRAM_FILENAME = "fake fileName";
    private static final String FAKE_PROGRAM_HOSTNAME = "fake hostName";
    private static final LocalDate FAKE_PROGRAM_AIRDATE = new LocalDate();
    private static final String FAKE_PROGRAM_DESCRIPTION = "fake description";
    private static final String FAKE_PROGRAM_INETREF = "fake inetref";
    private static final int FAKE_PROGRAM_SEASON = 1;
    private static final int FAKE_PROGRAM_EPISODE = 1;
    private static final int FAKE_PROGRAM_TOTALEPISODES = 1;

    // Channel Info Fake Values
    private static final int FAKE_CHANNEL_INFO_CHAN_ID = 1;
    private static final String FAKE_CHANNEL_INFO_CHAN_NUM = "fake channel info chan num";
    private static final String FAKE_CHANNEL_INFO_CALLSIGN = "fake channel info callsign";
    private static final String FAKE_CHANNEL_INFO_ICON_URL = "fake channel info iconUrl";
    private static final String FAKE_CHANNEL_INFO_CHANNEL_NAME = "fake channel info channel name";
    private static final int FAKE_CHANNEL_INFO_MPLEXID = 1;
    private static final int FAKE_CHANNEL_INFO_SERVICE_ID = 1;
    private static final int FAKE_CHANNEL_INFO_ATSC_MAJOR_CHAN = 1;
    private static final int FAKE_CHANNEL_INFO_ATSC_MINOR_CHAN = 1;
    private static final String FAKE_CHANNEL_INFO_FORMAT = "fake channel info format";
    private static final String FAKE_CHANNEL_INFO_FREQUENCY_ID = "fake channel info frequence id";
    private static final int FAKE_CHANNEL_INFO_FINETUNE = 1;
    private static final String FAKE_CHANNEL_INFO_CHAN_FILTERS = "fake channel info chan filters";
    private static final int FAKE_CHANNEL_INFO_SOURCE_ID = 1;
    private static final int FAKE_CHANNEL_INFO_INPUT_ID = 1;
    private static final boolean FAKE_CHANNEL_INFO_COMM_FREE = false;
    private static final boolean FAKE_CHANNEL_INFO_USE_EIT = false;
    private static final boolean FAKE_CHANNEL_INFO_VISIBLE = false;
    private static final String FAKE_CHANNEL_INFO_XMLTVID = "fake channel info xmltvid";
    private static final String FAKE_CHANNEL_INFO_DEFAULT_AUTH = "fake channel info default auth";

    // Recording Info Fake Values
    private static final int FAKE_RECORDING_INFO_RECORDED_ID = 1;
    private static final int FAKE_RECORDING_INFO_STATUS = 1;
    private static final int FAKE_RECORDING_INFO_PRIORITY = 1;
    private static final DateTime FAKE_RECORDING_INFO_START_TS = new DateTime();
    private static final DateTime FAKE_RECORDING_INFO_END_TS = new DateTime();
    private static final int FAKE_RECORDING_INFO_RECORD_ID = 1;
    private static final String FAKE_RECORDING_INFO_REC_GROUP = "fake recording info rec group";
    private static final String FAKE_RECORDING_INFO_PLAY_GROUP = "fake recording info play group";
    private static final String FAKE_RECORDING_INFO_STORAGE_GROUP = "fake recording info storage group";
    private static final int FAKE_RECORDING_INFO_REC_TYPE = 1;
    private static final int FAKE_RECORDING_INFO_DUP_IN_TYPE = 1;
    private static final int FAKE_RECORDING_INFO_DUP_METHOD = 1;
    private static final int FAKE_RECORDING_INFO_ENCODER_ID = 1;
    private static final String FAKE_RECORDING_INFO_ENCODER_NAME = "fake recording info encoder name";
    private static final String FAKE_RECORDING_INFO_PROFILE = "fake recording info profile";

    // Artwork Info Fake Values
    private static final String FAKE_ARTWORK_INFO_URL = "fake artwork info url";
    private static final String FAKE_ARTWORK_INFO_FILENAME = "fake artwork info filename";
    private static final String FAKE_ARTWORK_INFO_STORAGE_GROUP = "fake artwork info storage group";
    private static final String FAKE_ARTWORK_INFO_TYPE = "fake artwork info type";

    // Cast Member Fake Values
    private static final String FAKE_CAST_MEMBER_NAME = "fake cast member name";
    private static final String FAKE_CAST_MEMBER_CHARACTER_NAME = "fake cast member character name";
    private static final String FAKE_CAST_MEMBER_ROLE = "fake cast member role";
    private static final String FAKE_CAST_MEMBER_TRANSLATED_ROLE = "fake cast member translated role";

    @Test
    public void testTransformSearchResultEntity() throws Exception {

        SearchResultEntity searchResultEntity = createFakeSearchResultEntity();
        SearchResult searchResult = SearchResultEntityDataMapper.transform( searchResultEntity );

        assertThat( searchResult, is( instanceOf( SearchResult.class ) ) );
        assertThat( searchResult.getChanId(), is( FAKE_CHAN_ID ) );
        assertThat( searchResult.getStartTime(), is( new DateTime( FAKE_START_TIME ) ) );
        assertThat( searchResult.getTitle(), is( FAKE_TITLE ) );
        assertThat( searchResult.getSubTitle(), is( FAKE_SUB_TITLE ) );
        assertThat( searchResult.getCategory(), is( FAKE_CATEGORY ) );
        assertThat( searchResult.getCallsign(), is( FAKE_CALLSIGN ) );
        assertThat( searchResult.getChannelNumber(), is( FAKE_CHANNEL_NUMBER ) );
        assertThat( searchResult.getDescription(), is( FAKE_DESCRIPTION ) );
        assertThat( searchResult.getInetref(), is( FAKE_INETREF ) );
        assertThat( searchResult.getSeason(), is( FAKE_SEASON ) );
        assertThat( searchResult.getEpisode(), is( FAKE_EPISODE ) );
        assertThat( searchResult.getCastMembers(), is( FAKE_CAST_MEMBERS ) );
        assertThat( searchResult.getCharacters(), is( FAKE_CHARACTERS ) );
        assertThat( searchResult.getRating(), is( FAKE_RATING ) );
        assertThat( searchResult.getType(), is( FAKE_TYPE ) );

    }

    @Test
    public void testTransformSearchResultEntityCollection() throws Exception {

        SearchResultEntity mockSearchResultEntityOne = createFakeSearchResultEntity();
        SearchResultEntity mockSearchResultEntityTwo = createFakeSearchResultEntity();

        List<SearchResultEntity> searchResultEntityList = new ArrayList<>( 5 );
        searchResultEntityList.add( mockSearchResultEntityOne );
        searchResultEntityList.add( mockSearchResultEntityTwo );

        Collection<SearchResult> searchResultCollection = SearchResultEntityDataMapper.transform( searchResultEntityList );

        assertThat( searchResultCollection.toArray()[ 0 ], is( instanceOf( SearchResult.class ) ) );
        assertThat( searchResultCollection.toArray()[ 1 ], is( instanceOf( SearchResult.class ) ) );
        assertThat( searchResultCollection.size(), is( 2 ) );

    }

    @Test
    public void testTransformProgram() throws Exception {

        ProgramEntity programEntity = createFakeProgramEntity();
        SearchResultEntity searchResultEntity = SearchResultEntityDataMapper.transformProgram( programEntity );

        assertThat( searchResultEntity, is( instanceOf( SearchResultEntity.class ) ) );
        assertThat( searchResultEntity.getChanId(), is( FAKE_CHANNEL_INFO_CHAN_ID ) );
        assertThat( searchResultEntity.getStartTime(), is( FAKE_RECORDING_INFO_START_TS.getMillis() ) );
        assertThat( searchResultEntity.getTitle(), is( FAKE_PROGRAM_TITLE ) );
        assertThat( searchResultEntity.getSubTitle(), is( FAKE_PROGRAM_SUB_TITLE ) );
        assertThat( searchResultEntity.getCategory(), is( FAKE_PROGRAM_CATEGORY ) );
        assertThat( searchResultEntity.getCallsign(), is( FAKE_CHANNEL_INFO_CALLSIGN ) );
        assertThat( searchResultEntity.getChannelNumber(), is( FAKE_CHANNEL_INFO_CHAN_NUM ) );
        assertThat( searchResultEntity.getDescription(), is( FAKE_PROGRAM_DESCRIPTION ) );
        assertThat( searchResultEntity.getInetref(), is( FAKE_PROGRAM_INETREF ) );
        assertThat( searchResultEntity.getSeason(), is( FAKE_PROGRAM_SEASON ) );
        assertThat( searchResultEntity.getEpisode(), is( FAKE_PROGRAM_EPISODE ) );
        assertThat( searchResultEntity.getCastMembers(), is( FAKE_CAST_MEMBER_NAME ) );
        assertThat( searchResultEntity.getCharacters(), is( FAKE_CAST_MEMBER_CHARACTER_NAME ) );
        assertThat( searchResultEntity.getRating(), nullValue() );
        assertThat( searchResultEntity.getType(), is( FAKE_TYPE.name() ) );

    }

    @Test
    public void testTransformProgramLiveTv() throws Exception {

        ProgramEntity programEntity = createFakeProgramEntity();
        programEntity.getRecording().setRecGroup( "LiveTV" );
        SearchResultEntity searchResultEntity = SearchResultEntityDataMapper.transformProgram( programEntity );

        assertThat( searchResultEntity, nullValue() );

    }

    @Test
    public void testTransformProgramEntityCollection() throws Exception {

        ProgramEntity mockProgramEntityOne = createFakeProgramEntity();
        ProgramEntity mockProgramEntityTwo = createFakeProgramEntity();

        List<ProgramEntity> programEntityList = new ArrayList<>( 5 );
        programEntityList.add( mockProgramEntityOne );
        programEntityList.add( mockProgramEntityTwo );

        Collection<SearchResultEntity> searchResultCollection = SearchResultEntityDataMapper.transformPrograms( programEntityList );

        assertThat( searchResultCollection.toArray()[ 0 ], is( instanceOf( SearchResultEntity.class ) ) );
        assertThat( searchResultCollection.toArray()[ 1 ], is( instanceOf( SearchResultEntity.class ) ) );
        assertThat( searchResultCollection.size(), is( 2 ) );

    }

    private SearchResultEntity createFakeSearchResultEntity() {

        SearchResultEntity searchResultEntity = new SearchResultEntity();
        searchResultEntity.setChanId( FAKE_CHAN_ID );
        searchResultEntity.setStartTime( FAKE_START_TIME );
        searchResultEntity.setTitle( FAKE_TITLE );
        searchResultEntity.setSubTitle( FAKE_SUB_TITLE );
        searchResultEntity.setCategory( FAKE_CATEGORY );
        searchResultEntity.setCallsign( FAKE_CALLSIGN );
        searchResultEntity.setChannelNumber( FAKE_CHANNEL_NUMBER );
        searchResultEntity.setDescription( FAKE_DESCRIPTION );
        searchResultEntity.setInetref( FAKE_INETREF );
        searchResultEntity.setSeason( FAKE_SEASON );
        searchResultEntity.setEpisode( FAKE_EPISODE );
        searchResultEntity.setCastMembers( FAKE_CAST_MEMBERS );
        searchResultEntity.setCharacters( FAKE_CHARACTERS );
        searchResultEntity.setRating( FAKE_RATING );
        searchResultEntity.setType( FAKE_TYPE.name() );

        return searchResultEntity;
    }

    private ProgramEntity createFakeProgramEntity() {

        ProgramEntity programEntity = new ProgramEntity();
        programEntity.setStartTime( FAKE_PROGRAM_START_TIME );
        programEntity.setEndTime( FAKE_PROGRAM_END_TIME );
        programEntity.setTitle( FAKE_PROGRAM_TITLE );
        programEntity.setSubTitle( FAKE_PROGRAM_SUB_TITLE );
        programEntity.setCategory( FAKE_PROGRAM_CATEGORY );
        programEntity.setCatType( FAKE_PROGRAM_CATTYPE );
        programEntity.setRepeat( FAKE_PROGRAM_REPEAT );
        programEntity.setVideoProps( FAKE_PROGRAM_VIDEOPROPS );
        programEntity.setAudioProps( FAKE_PROGRAM_AUDIOPROPS );
        programEntity.setSubProps( FAKE_PROGRAM_SUBPROPS );
        programEntity.setSeriesId( FAKE_PROGRAM_SERIESID );
        programEntity.setProgramId( FAKE_PROGRAM_PROGRAMID );
        programEntity.setStars( FAKE_PROGRAM_STARS );
        programEntity.setFileSize( FAKE_PROGRAM_FILESIZE );
        programEntity.setLastModified( FAKE_PROGRAM_LASTMODIFIED );
        programEntity.setProgramFlags( FAKE_PROGRAM_PROGRAMFLAGS );
        programEntity.setFileName( FAKE_PROGRAM_FILENAME );
        programEntity.setHostName( FAKE_PROGRAM_HOSTNAME );
        programEntity.setAirdate( FAKE_PROGRAM_AIRDATE );
        programEntity.setDescription( FAKE_PROGRAM_DESCRIPTION );
        programEntity.setInetref( FAKE_PROGRAM_INETREF );
        programEntity.setSeason( FAKE_PROGRAM_SEASON );
        programEntity.setEpisode( FAKE_PROGRAM_EPISODE );
        programEntity.setTotalEpisodes( FAKE_PROGRAM_TOTALEPISODES );

        programEntity.setChannel( createFakeChannelInfoEntity() );
        programEntity.setRecording( createFakeRecordingInfoEntity() );

        programEntity.setArtwork( createFakeArtworkEntity() );
        programEntity.setCast( createFakeCastEntity() );

        return programEntity;
    }

    private ChannelInfoEntity createFakeChannelInfoEntity() {

        ChannelInfoEntity channelInfoEntity = new ChannelInfoEntity();
        channelInfoEntity.setChanId(FAKE_CHANNEL_INFO_CHAN_ID);
        channelInfoEntity.setChanNum(FAKE_CHANNEL_INFO_CHAN_NUM);
        channelInfoEntity.setCallSign(FAKE_CHANNEL_INFO_CALLSIGN);
        channelInfoEntity.setIconURL(FAKE_CHANNEL_INFO_ICON_URL);
        channelInfoEntity.setChannelName(FAKE_CHANNEL_INFO_CHANNEL_NAME);
        channelInfoEntity.setMplexId(FAKE_CHANNEL_INFO_MPLEXID);
        channelInfoEntity.setServiceId(FAKE_CHANNEL_INFO_SERVICE_ID);
        channelInfoEntity.setATSCMajorChan(FAKE_CHANNEL_INFO_ATSC_MAJOR_CHAN);
        channelInfoEntity.setATSCMinorChan(FAKE_CHANNEL_INFO_ATSC_MINOR_CHAN);
        channelInfoEntity.setFormat(FAKE_CHANNEL_INFO_FORMAT);
        channelInfoEntity.setFrequencyId(FAKE_CHANNEL_INFO_FREQUENCY_ID);
        channelInfoEntity.setFineTune(FAKE_CHANNEL_INFO_FINETUNE);
        channelInfoEntity.setChanFilters(FAKE_CHANNEL_INFO_CHAN_FILTERS);
        channelInfoEntity.setSourceId(FAKE_CHANNEL_INFO_SOURCE_ID);
        channelInfoEntity.setInputId(FAKE_CHANNEL_INFO_INPUT_ID);
        channelInfoEntity.setCommFree(FAKE_CHANNEL_INFO_COMM_FREE);
        channelInfoEntity.setUseEIT(FAKE_CHANNEL_INFO_USE_EIT);
        channelInfoEntity.setVisible(FAKE_CHANNEL_INFO_VISIBLE);
        channelInfoEntity.setXMLTVID(FAKE_CHANNEL_INFO_XMLTVID);
        channelInfoEntity.setDefaultAuth(FAKE_CHANNEL_INFO_DEFAULT_AUTH);

        return channelInfoEntity;
    }

    private RecordingInfoEntity createFakeRecordingInfoEntity() {

        RecordingInfoEntity recordingInfoEntity = new RecordingInfoEntity();
        recordingInfoEntity.setRecordedId(FAKE_RECORDING_INFO_RECORDED_ID);
        recordingInfoEntity.setStatus(FAKE_RECORDING_INFO_STATUS);
        recordingInfoEntity.setPriority(FAKE_RECORDING_INFO_PRIORITY);
        recordingInfoEntity.setStartTs(FAKE_RECORDING_INFO_START_TS);
        recordingInfoEntity.setEndTs(FAKE_RECORDING_INFO_END_TS);
        recordingInfoEntity.setRecordId(FAKE_RECORDING_INFO_RECORD_ID);
        recordingInfoEntity.setRecGroup(FAKE_RECORDING_INFO_REC_GROUP);
        recordingInfoEntity.setPlayGroup(FAKE_RECORDING_INFO_PLAY_GROUP);
        recordingInfoEntity.setStorageGroup(FAKE_RECORDING_INFO_STORAGE_GROUP);
        recordingInfoEntity.setRecType(FAKE_RECORDING_INFO_REC_TYPE);
        recordingInfoEntity.setDupInType(FAKE_RECORDING_INFO_DUP_IN_TYPE);
        recordingInfoEntity.setDupMethod(FAKE_RECORDING_INFO_DUP_METHOD);
        recordingInfoEntity.setEncoderId(FAKE_RECORDING_INFO_ENCODER_ID);
        recordingInfoEntity.setEncoderName(FAKE_RECORDING_INFO_ENCODER_NAME);
        recordingInfoEntity.setProfile(FAKE_RECORDING_INFO_PROFILE);

        return recordingInfoEntity;
    }

    private ArtworkEntity createFakeArtworkEntity() {

        ArtworkEntity artworkEntity = new ArtworkEntity();

        ArtworkInfoEntity[] artworkInfoEntities = new ArtworkInfoEntity[ 1 ];
        artworkInfoEntities[ 0 ] = createFakeArtworkInfoEntity();
        artworkEntity.setArtworkInfos( artworkInfoEntities );

        return artworkEntity;
    }

    private ArtworkInfoEntity createFakeArtworkInfoEntity() {

        ArtworkInfoEntity artworkInfoEntity = new ArtworkInfoEntity();
        artworkInfoEntity.setUrl( FAKE_ARTWORK_INFO_URL );
        artworkInfoEntity.setFileName( FAKE_ARTWORK_INFO_FILENAME );
        artworkInfoEntity.setStorageGroup( FAKE_ARTWORK_INFO_STORAGE_GROUP );
        artworkInfoEntity.setType( FAKE_ARTWORK_INFO_TYPE );

        return artworkInfoEntity;
    }

    private CastEntity createFakeCastEntity() {

        CastEntity castEntity = new CastEntity();

        CastMemberEntity[] castMemberEntities = new CastMemberEntity[ 1 ];
        castMemberEntities[ 0 ] = createFakeCastMemberEntity();
        castEntity.setCastMembers( castMemberEntities );

        return castEntity;
    }

    private CastMemberEntity createFakeCastMemberEntity() {

        CastMemberEntity castMemberEntity = new CastMemberEntity();
        castMemberEntity.setName( FAKE_CAST_MEMBER_NAME );
        castMemberEntity.setCharacterName( FAKE_CAST_MEMBER_CHARACTER_NAME );
        castMemberEntity.setRole( FAKE_CAST_MEMBER_ROLE );
        castMemberEntity.setTranslatedRole( FAKE_CAST_MEMBER_TRANSLATED_ROLE );

        return castMemberEntity;
    }

}
