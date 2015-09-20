package org.mythtv.android.data.entity.mapper;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.ArtworkEntity;
import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.data.entity.CastEntity;
import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.data.entity.ChannelInfoEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.RecordingInfoEntity;
import org.mythtv.android.domain.ArtworkInfo;
import org.mythtv.android.domain.CastMember;
import org.mythtv.android.domain.ChannelInfo;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.RecordingInfo;

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
public class ProgramEntityDataMapperTest extends ApplicationTestCase {

    private static final DateTime FAKE_START_TIME = new DateTime();
    private static final DateTime FAKE_END_TIME = new DateTime();
    private static final String FAKE_TITLE = "fake title";
    private static final String FAKE_SUB_TITLE = "fake sub title";
    private static final String FAKE_CATEGORY = "fake category";
    private static final String FAKE_CATTYPE = "fake catType";
    private static final boolean FAKE_REPEAT = false;
    private static final int FAKE_VIDEOPROPS = 1;
    private static final int FAKE_AUDIOPROPS = 1;
    private static final int FAKE_SUBPROPS = 1;
    private static final String FAKE_SERIESID = "fake seriesId";
    private static final String FAKE_PROGRAMID = "fake programId";
    private static final double FAKE_STARS = 1.0;
    private static final long FAKE_FILESIZE = 1l;
    private static final DateTime FAKE_LASTMODIFIED = new DateTime();
    private static final int FAKE_PROGRAMFLAGS = 1;
    private static final String FAKE_FILENAME = "fake fileName";
    private static final String FAKE_HOSTNAME = "fake hostName";
    private static final LocalDate FAKE_AIRDATE = new LocalDate();
    private static final String FAKE_DESCRIPTION = "fake description";
    private static final String FAKE_INETREF = "fake inetref";
    private static final int FAKE_SEASON = 1;
    private static final int FAKE_EPISODE = 1;
    private static final int FAKE_TOTALEPISODES = 1;

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


    private ProgramEntityDataMapper programEntityDataMapper;

    @Before
    public void setup() throws Exception {

        programEntityDataMapper = new ProgramEntityDataMapper();

    }

    @Test
    public void testTransformProgramEntity() {

        ProgramEntity programEntity = createFakeProgramEntity();
        Program program = programEntityDataMapper.transform( programEntity );

        assertThat( program, is( instanceOf( Program.class ) ) );
        assertThat( program.getStartTime(), is( FAKE_START_TIME ) );
        assertThat( program.getEndTime(), is( FAKE_END_TIME ) );
        assertThat( program.getTitle(), is( FAKE_TITLE ) );
        assertThat( program.getSubTitle(), is(FAKE_SUB_TITLE) );
        assertThat( program.getCategory(), is( FAKE_CATEGORY ) );
        assertThat( program.getCatType(), is( FAKE_CATTYPE ) );
        assertThat( program.isRepeat(), is( FAKE_REPEAT ) );
        assertThat( program.getVideoProps(), is( FAKE_VIDEOPROPS ) );
        assertThat( program.getAudioProps(), is( FAKE_AUDIOPROPS ) );
        assertThat( program.getSubProps(), is( FAKE_SUBPROPS ) );
        assertThat( program.getSeriesId(), is( FAKE_SERIESID ) );
        assertThat( program.getProgramId(), is( FAKE_PROGRAMID ) );
        assertThat( program.getStars(), is( FAKE_STARS ) );
        assertThat( program.getFileSize(), is( FAKE_FILESIZE ) );
        assertThat( program.getLastModified(), is( FAKE_LASTMODIFIED ) );
        assertThat( program.getProgramFlags(), is( FAKE_PROGRAMFLAGS ) );
        assertThat( program.getFileName(), is( FAKE_FILENAME ) );
        assertThat( program.getHostName(), is( FAKE_HOSTNAME ) );
        assertThat( program.getAirdate(), is( FAKE_AIRDATE ) );
        assertThat( program.getDescription(), is( FAKE_DESCRIPTION ) );
        assertThat( program.getInetref(), is( FAKE_INETREF ) );
        assertThat( program.getSeason(), is( FAKE_SEASON ) );
        assertThat( program.getEpisode(), is( FAKE_EPISODE ) );
        assertThat( program.getTotalEpisodes(), is( FAKE_TOTALEPISODES ) );

        assertChannel( program.getChannel() );
        assertRecording( program.getRecording() );

        assertThat( program.getArtworkInfos().toArray() [ 0 ], is( instanceOf( ArtworkInfo.class ) ) );
        assertThat( program.getArtworkInfos().size(), is( 1 ) );

        assertThat( program.getCastMembers().toArray() [ 0 ], is( instanceOf( CastMember.class ) ) );
        assertThat( program.getCastMembers().size(), is( 1 ) );
    }

    @Test
    public void testTransformProgramEntityCollection() {

        ProgramEntity mockProgramEntityOne = mock( ProgramEntity.class );
        ProgramEntity mockProgramEntityTwo = mock( ProgramEntity.class );

        List<ProgramEntity> programEntityList = new ArrayList<>( 5 );
        programEntityList.add( mockProgramEntityOne );
        programEntityList.add( mockProgramEntityTwo );

        Collection<Program> programCollection = programEntityDataMapper.transform( programEntityList );

        assertThat( programCollection.toArray()[ 0 ], is( instanceOf( Program.class ) ) );
        assertThat( programCollection.toArray()[ 1 ], is( instanceOf( Program.class ) ) );
        assertThat( programCollection.size(), is( 2 ) );

    }

    @Test
    public void testTransformChannelInfoEntity() {

        ChannelInfoEntity channelInfoEntity = createFakeChannelInfoEntity();
        ChannelInfo channelInfo = programEntityDataMapper.transformChannelInfo( channelInfoEntity );
        assertChannel( channelInfo );

    }

    private void assertChannel( ChannelInfo channelInfo ) {

        assertThat( channelInfo, is( instanceOf( ChannelInfo.class ) ) );
        assertThat( channelInfo.getChanId(), is( FAKE_CHANNEL_INFO_CHAN_ID ) );
        assertThat( channelInfo.getChanNum(), is( FAKE_CHANNEL_INFO_CHAN_NUM ) );
        assertThat( channelInfo.getCallSign(), is( FAKE_CHANNEL_INFO_CALLSIGN ) );
        assertThat( channelInfo.getIconURL(), is( FAKE_CHANNEL_INFO_ICON_URL ) );
        assertThat( channelInfo.getChannelName(), is( FAKE_CHANNEL_INFO_CHANNEL_NAME ) );
        assertThat( channelInfo.getMplexId(), is( FAKE_CHANNEL_INFO_MPLEXID ) );
        assertThat( channelInfo.getServiceId(), is( FAKE_CHANNEL_INFO_SERVICE_ID ) );
        assertThat( channelInfo.getATSCMajorChan(), is( FAKE_CHANNEL_INFO_ATSC_MAJOR_CHAN ) );
        assertThat( channelInfo.getATSCMinorChan(), is( FAKE_CHANNEL_INFO_ATSC_MINOR_CHAN ) );
        assertThat( channelInfo.getFormat(), is( FAKE_CHANNEL_INFO_FORMAT ) );
        assertThat( channelInfo.getFrequencyId(), is( FAKE_CHANNEL_INFO_FREQUENCY_ID ) );
        assertThat( channelInfo.getFineTune(), is( FAKE_CHANNEL_INFO_FINETUNE ) );
        assertThat( channelInfo.getChanFilters(), is( FAKE_CHANNEL_INFO_CHAN_FILTERS ) );
        assertThat( channelInfo.getSourceId(), is( FAKE_CHANNEL_INFO_SOURCE_ID ) );
        assertThat( channelInfo.getInputId(), is( FAKE_CHANNEL_INFO_INPUT_ID ) );
        assertThat( channelInfo.isCommFree(), is( FAKE_CHANNEL_INFO_COMM_FREE ) );
        assertThat( channelInfo.isUseEIT(), is( FAKE_CHANNEL_INFO_USE_EIT ) );
        assertThat( channelInfo.isVisible(), is( FAKE_CHANNEL_INFO_VISIBLE ) );
        assertThat( channelInfo.getXMLTVID(), is( FAKE_CHANNEL_INFO_XMLTVID ) );
        assertThat( channelInfo.getDefaultAuth(), is( FAKE_CHANNEL_INFO_DEFAULT_AUTH ) );

    }

    @Test
    public void testTransformChannelInfoEntityCollection() {

        ChannelInfoEntity mockChannelInfoEntityOne = mock( ChannelInfoEntity.class );
        ChannelInfoEntity mockChannelInfoEntityTwo = mock( ChannelInfoEntity.class );

        List<ChannelInfoEntity> channelInfoEntityList = new ArrayList<>( 5 );
        channelInfoEntityList.add( mockChannelInfoEntityOne );
        channelInfoEntityList.add( mockChannelInfoEntityTwo );

        Collection<ChannelInfo> channelInfoCollection = programEntityDataMapper.transformChannelInfoCollection(channelInfoEntityList);

        assertThat( channelInfoCollection.toArray()[ 0 ], is( instanceOf( ChannelInfo.class ) ) );
        assertThat( channelInfoCollection.toArray()[ 1 ], is( instanceOf( ChannelInfo.class ) ) );
        assertThat( channelInfoCollection.size(), is( 2 ) );

    }

    @Test
    public void testTransformRecordingInfoEntity() {

        RecordingInfoEntity recordingInfoEntity = createFakeRecordingInfoEntity();
        RecordingInfo recordingInfo = programEntityDataMapper.transformRecordingInfo(recordingInfoEntity);
        assertRecording(recordingInfo);

    }

    private void assertRecording( RecordingInfo recordingInfo ) {

        assertThat( recordingInfo, is( instanceOf( RecordingInfo.class ) ) );
        assertThat( recordingInfo.getRecordedId(), is( FAKE_RECORDING_INFO_RECORDED_ID ) );
        assertThat( recordingInfo.getStatus(), is( FAKE_RECORDING_INFO_STATUS ) );
        assertThat( recordingInfo.getPriority(), is( FAKE_RECORDING_INFO_PRIORITY ) );
        assertThat( recordingInfo.getStartTs(), is( FAKE_RECORDING_INFO_START_TS ) );
        assertThat( recordingInfo.getEndTs(), is( FAKE_RECORDING_INFO_END_TS ) );
        assertThat( recordingInfo.getRecordId(), is( FAKE_RECORDING_INFO_RECORD_ID ) );
        assertThat( recordingInfo.getRecGroup(), is( FAKE_RECORDING_INFO_REC_GROUP ) );
        assertThat( recordingInfo.getPlayGroup(), is( FAKE_RECORDING_INFO_PLAY_GROUP ) );
        assertThat( recordingInfo.getStorageGroup(), is( FAKE_RECORDING_INFO_STORAGE_GROUP ) );
        assertThat( recordingInfo.getRecType(), is( FAKE_RECORDING_INFO_REC_TYPE ) );
        assertThat( recordingInfo.getDupInType(), is( FAKE_RECORDING_INFO_DUP_IN_TYPE ) );
        assertThat( recordingInfo.getDupMethod(), is( FAKE_RECORDING_INFO_DUP_METHOD ) );
        assertThat( recordingInfo.getEncoderId(), is( FAKE_RECORDING_INFO_ENCODER_ID ) );
        assertThat( recordingInfo.getEncoderName(), is( FAKE_RECORDING_INFO_ENCODER_NAME ) );
        assertThat( recordingInfo.getProfile(), is( FAKE_RECORDING_INFO_PROFILE ) );

    }

    @Test
    public void testTransformRecordingInfoEntityCollection() {

        RecordingInfoEntity mockRecordingInfoEntityOne = mock(RecordingInfoEntity.class);
        RecordingInfoEntity mockRecordingInfoEntityTwo = mock(RecordingInfoEntity.class);

        List<RecordingInfoEntity> recordingInfoEntityList = new ArrayList<>( 5 );
        recordingInfoEntityList.add(mockRecordingInfoEntityOne);
        recordingInfoEntityList.add(mockRecordingInfoEntityTwo);

        Collection<RecordingInfo> recordingInfoCollection = programEntityDataMapper.transformRecordingInfoCollection(recordingInfoEntityList);

        assertThat( recordingInfoCollection.toArray()[ 0 ], is( instanceOf( RecordingInfo.class ) ) );
        assertThat( recordingInfoCollection.toArray()[ 1 ], is( instanceOf( RecordingInfo.class ) ) );
        assertThat( recordingInfoCollection.size(), is( 2 ) );

    }

    @Test
    public void testTransformArtworkInfoEntity() {

        ArtworkInfoEntity artworkInfoEntity = createFakeArtworkInfoEntity();
        ArtworkInfo artworkInfo = programEntityDataMapper.transformArtworkInfo(artworkInfoEntity);
        assertArtworkInfo(artworkInfo);

    }

    private void assertArtworkInfo( ArtworkInfo artworkInfo ) {

        assertThat( artworkInfo, is( instanceOf( ArtworkInfo.class ) ) );
        assertThat( artworkInfo.getUrl(), is( FAKE_ARTWORK_INFO_URL ) );
        assertThat( artworkInfo.getFileName(), is( FAKE_ARTWORK_INFO_FILENAME ) );
        assertThat( artworkInfo.getStorageGroup(), is( FAKE_ARTWORK_INFO_STORAGE_GROUP ) );
        assertThat(artworkInfo.getType(), is(FAKE_ARTWORK_INFO_TYPE));

    }

    @Test
    public void testTransformArtworkInfoEntityCollection() {

        ArtworkInfoEntity mockArtworkInfoEntityOne = mock( ArtworkInfoEntity.class );
        ArtworkInfoEntity mockArtworkInfoEntityTwo = mock(ArtworkInfoEntity.class);

        List<ArtworkInfoEntity> artworkInfoEntityList = new ArrayList<>( 5 );
        artworkInfoEntityList.add(mockArtworkInfoEntityOne);
        artworkInfoEntityList.add(mockArtworkInfoEntityTwo);

        Collection<ArtworkInfo> artworkInfoCollection = programEntityDataMapper.transformArtworkInfoCollection(artworkInfoEntityList);

        assertThat( artworkInfoCollection.toArray()[ 0 ], is( instanceOf( ArtworkInfo.class ) ) );
        assertThat( artworkInfoCollection.toArray()[ 1 ], is( instanceOf( ArtworkInfo.class ) ) );
        assertThat( artworkInfoCollection.size(), is( 2 ) );

    }

    @Test
    public void testTransformCastMemberEntity() {

        CastMemberEntity castMemberEntity = createFakeCastMemberEntity();
        CastMember castMember = programEntityDataMapper.transformCastMember(castMemberEntity);
        assertCastMember( castMember );

    }

    private void assertCastMember( CastMember castMember ) {

        assertThat( castMember, is( instanceOf( CastMember.class ) ) );
        assertThat( castMember.getName(), is( FAKE_CAST_MEMBER_NAME ) );
        assertThat( castMember.getCharacterName(), is( FAKE_CAST_MEMBER_CHARACTER_NAME ) );
        assertThat( castMember.getRole(), is( FAKE_CAST_MEMBER_ROLE ) );
        assertThat( castMember.getTranslatedRole(), is( FAKE_CAST_MEMBER_TRANSLATED_ROLE ) );

    }

    @Test
    public void testTransformCastMemberEntityCollection() {

        CastMemberEntity mockCastMemberEntityOne = mock( CastMemberEntity.class );
        CastMemberEntity mockCastMemberEntityTwo = mock(CastMemberEntity.class);

        List<CastMemberEntity> castMemberEntityList = new ArrayList<>( 5 );
        castMemberEntityList.add(mockCastMemberEntityOne);
        castMemberEntityList.add(mockCastMemberEntityTwo);

        Collection<CastMember> castMemberCollection = programEntityDataMapper.transformCastMemberCollection(castMemberEntityList);

        assertThat( castMemberCollection.toArray()[ 0 ], is( instanceOf( CastMember.class ) ) );
        assertThat( castMemberCollection.toArray()[ 1 ], is( instanceOf( CastMember.class ) ) );
        assertThat( castMemberCollection.size(), is( 2 ) );

    }

    private ProgramEntity createFakeProgramEntity() {

        ProgramEntity programEntity = new ProgramEntity();
        programEntity.setStartTime( FAKE_START_TIME );
        programEntity.setEndTime( FAKE_END_TIME );
        programEntity.setTitle( FAKE_TITLE );
        programEntity.setSubTitle( FAKE_SUB_TITLE );
        programEntity.setCategory( FAKE_CATEGORY );
        programEntity.setCatType( FAKE_CATTYPE );
        programEntity.setRepeat( FAKE_REPEAT );
        programEntity.setVideoProps( FAKE_VIDEOPROPS );
        programEntity.setAudioProps( FAKE_AUDIOPROPS );
        programEntity.setSubProps( FAKE_SUBPROPS );
        programEntity.setSeriesId( FAKE_SERIESID );
        programEntity.setProgramId( FAKE_PROGRAMID );
        programEntity.setStars( FAKE_STARS );
        programEntity.setFileSize( FAKE_FILESIZE );
        programEntity.setLastModified( FAKE_LASTMODIFIED );
        programEntity.setProgramFlags( FAKE_PROGRAMFLAGS );
        programEntity.setFileName( FAKE_FILENAME );
        programEntity.setHostName( FAKE_HOSTNAME );
        programEntity.setAirdate( FAKE_AIRDATE );
        programEntity.setDescription( FAKE_DESCRIPTION );
        programEntity.setInetref( FAKE_INETREF );
        programEntity.setSeason( FAKE_SEASON );
        programEntity.setEpisode( FAKE_EPISODE );
        programEntity.setTotalEpisodes( FAKE_TOTALEPISODES );

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
