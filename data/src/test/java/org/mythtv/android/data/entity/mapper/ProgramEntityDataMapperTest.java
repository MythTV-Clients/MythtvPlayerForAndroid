package org.mythtv.android.data.entity.mapper;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.ArtworkEntity;
import org.mythtv.android.data.entity.CastEntity;
import org.mythtv.android.data.entity.ChannelInfoEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.RecordingInfoEntity;
import org.mythtv.android.domain.Program;

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
    private static final ChannelInfoEntity FAKE_CHANNEL = new ChannelInfoEntity();
    private static final RecordingInfoEntity FAKE_RECORDING = new RecordingInfoEntity();
    private static final ArtworkEntity FAKE_ARTWORK = new ArtworkEntity();
    private static final CastEntity FAKE_CAST = new CastEntity();

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
//        assertThat( program.getChannel(), is( FAKE_CHANNEL ) );
//        assertThat( program.getRecording(), is( FAKE_RECORDING ) );
//        assertThat( program.getArtworkInfos(), is( FAKE_ARTWORK ) );
//        assertThat( program.getCastMembers(), is( FAKE_CAST ) );

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
        programEntity.setChannel( FAKE_CHANNEL );
        programEntity.setRecording( FAKE_RECORDING );
        programEntity.setArtwork( FAKE_ARTWORK );
        programEntity.setCast( FAKE_CAST );

        return programEntity;
    }

}
