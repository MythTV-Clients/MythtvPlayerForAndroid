package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.domain.Program;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class ProgramEntityDataMapper {

    @Inject
    public ProgramEntityDataMapper() {
    }

    public Program transform( ProgramEntity programEntity ) {

        Program program = null;
        if( null != programEntity ) {
            program = new Program();
            program.setStartTime( programEntity.getStartTime() );
            program.setEndTime( programEntity.getEndTime() );
            program.setTitle( programEntity.getTitle() );
            program.setSubTitle( programEntity.getSubTitle() );
            program.setCategory( programEntity.getCategory() );
            program.setCatType( programEntity.getCatType() );
            program.setRepeat( programEntity.isRepeat() );
            program.setVideoProps( programEntity.getVideoProps() );
            program.setAudioProps( programEntity.getAudioProps() );
            program.setSubProps( programEntity.getSubProps() );
            program.setSeriesId( programEntity.getSeriesId() );
            program.setProgramId( programEntity.getProgramId() );
            program.setStars( programEntity.getStars() );
            program.setFileSize( programEntity.getFileSize() );
            program.setLastModified( programEntity.getLastModified() );
            program.setProgramFlags( programEntity.getProgramFlags() );
            program.setFileName( programEntity.getFileName() );
            program.setHostName( programEntity.getHostName() );
            program.setAirdate( programEntity.getAirdate() );
            program.setDescription( programEntity.getDescription() );
            program.setInetref( programEntity.getInetref() );
            program.setSeason( programEntity.getSeason() );
            program.setEpisode( programEntity.getEpisode() );
            program.setTotalEpisodes( programEntity.getTotalEpisodes() );

//            if( null != programEntity.getChannel() ) {
//                program.setChannel( ChannelInfo.fromprogramEntity( programEntity.getChannel() ) );
//            }
//
//            if( null != programEntity.getRecording() ) {
//                program.setRecording( RecordingInfo.fromprogramEntity( programEntity.getRecording() ) );
//            }
//
//            List<ArtworkInfo> artworkInfos = new ArrayList<>();
//            if( null != programEntity.getArtworkInfos() && !programEntity.getArtworkInfos().isEmpty() ) {
//                for( ArtworkInfoprogramEntity detail : programEntity.getArtworkInfos() ) {
//                    artworkInfos.add( ArtworkInfo.fromprogramEntity( detail ) );
//                }
//            }
//            program.setArtworkInfos( artworkInfos );
//
//            List<CastMember> castMembers = new ArrayList<>();
//            if( null != programEntity.getCastMembers() && !programEntity.getCastMembers().isEmpty() ) {
//                for( CastMemberprogramEntity detail : programEntity.getCastMembers() ) {
//                    castMembers.add( CastMember.fromprogramEntity( detail ) );
//                }
//            }
//            program.setCastMembers( castMembers );

        }

        return program;
    }

    public List<Program> transform( Collection<ProgramEntity> programEntityCollection ) {

        List<Program> programList = new ArrayList<>( programEntityCollection.size() );

        Program program;
        for( ProgramEntity programEntity : programEntityCollection ) {

            program = transform( programEntity );
            if( null != program ) {

                programList.add( program );

            }

        }

        return programList;
    }

}
