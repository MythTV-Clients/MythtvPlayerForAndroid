package org.mythtv.android.presentation.mapper;

import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.domain.Program;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.ProgramModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Program} (in the domain layer) to {@link ProgramModel} in the
 * presentation layer.
 */
@PerActivity
public class ProgramModelDataMapper {

    @Inject
    public ProgramModelDataMapper() {
    }

    public ProgramModel transform( Program program ) {

        if( null == program ) {

            throw new IllegalArgumentException( "Cannot transform a null value" );
        }

        ProgramModel programModel = new ProgramModel();
        programModel.setStartTime( program.getStartTime() );
        programModel.setEndTime( program.getEndTime() );
        programModel.setTitle( program.getTitle() );
        programModel.setSubTitle( program.getSubTitle() );
        programModel.setCategory( program.getCategory() );
        programModel.setCatType( program.getCatType() );
        programModel.setRepeat( program.isRepeat() );
        programModel.setVideoProps( program.getVideoProps() );
        programModel.setAudioProps( program.getAudioProps() );
        programModel.setSubProps( program.getSubProps() );
        programModel.setSeriesId( program.getSeriesId() );
        programModel.setProgramId( program.getProgramId() );
        programModel.setStars( program.getStars() );
        programModel.setFileSize( program.getFileSize() );
        programModel.setLastModified( program.getLastModified() );
        programModel.setProgramFlags( program.getProgramFlags() );
        programModel.setFileName( program.getFileName() );
        programModel.setHostName( program.getHostName() );
        programModel.setAirdate( program.getAirdate() );
        programModel.setDescription( program.getDescription() );
        programModel.setInetref( program.getInetref() );
        programModel.setSeason( program.getSeason() );
        programModel.setEpisode( program.getEpisode() );
        programModel.setTotalEpisodes( program.getTotalEpisodes() );

//        if( null != programEntity.getChannel() ) {
//            program.setChannel( ChannelInfo.fromprogramEntity( programEntity.getChannel() ) );
//         }
//
//         if( null != programEntity.getRecording() ) {
//             program.setRecording( RecordingInfo.fromprogramEntity( programEntity.getRecording() ) );
//         }
//
//         List<ArtworkInfo> artworkInfos = new ArrayList<>();
//         if( null != programEntity.getArtworkInfos() && !programEntity.getArtworkInfos().isEmpty() ) {
//             for( ArtworkInfoprogramEntity detail : programEntity.getArtworkInfos() ) {
//                  artworkInfos.add( ArtworkInfo.fromprogramEntity( detail ) );
//             }
//         }
//         program.setArtworkInfos( artworkInfos );
//
//         List<CastMember> castMembers = new ArrayList<>();
//         if( null != programEntity.getCastMembers() && !programEntity.getCastMembers().isEmpty() ) {
//             for( CastMemberprogramEntity detail : programEntity.getCastMembers() ) {
//                  castMembers.add( CastMember.fromprogramEntity( detail ) );
//             }
//         }
//         program.setCastMembers( castMembers );

        return programModel;
    }

    public List<ProgramModel> transform( Collection<Program> programCollection ) {

        List<ProgramModel> programModelList = new ArrayList<>( programCollection.size() );

        ProgramModel programModel;
        for( Program program : programCollection ) {

            programModel = transform( program );
            if( null != programModel ) {

                programModelList.add( programModel );

            }

        }

        return programModelList;
    }

}
