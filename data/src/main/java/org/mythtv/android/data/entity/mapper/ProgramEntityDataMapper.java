package org.mythtv.android.data.entity.mapper;

import android.util.Log;

import org.mythtv.android.data.entity.ArtworkInfoEntity;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class ProgramEntityDataMapper {

    private static final String TAG = ProgramEntityDataMapper.class.getSimpleName();

    private final LiveStreamInfoEntityDataMapper liveStreamInfoEntityDataMapper;

    @Inject
    public ProgramEntityDataMapper( LiveStreamInfoEntityDataMapper liveStreamInfoEntityDataMapper ) {

        this.liveStreamInfoEntityDataMapper = liveStreamInfoEntityDataMapper;

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

            if( null != programEntity.getChannel() ) {

                program.setChannel( transformChannelInfo( programEntity.getChannel() ) );

            }

            if( null != programEntity.getRecording() ) {
                Log.i( TAG, "transform : recording=" + programEntity.getRecording() );

                program.setRecording( transformRecordingInfo( programEntity.getRecording() ) );

            }

            List<ArtworkInfo> artworkInfos = new ArrayList<>();
            if( null != programEntity.getArtwork() && null != programEntity.getArtwork().getArtworkInfos() && programEntity.getArtwork().getArtworkInfos().length > 0 ) {

                for( ArtworkInfoEntity artworkInfoEntity : programEntity.getArtwork().getArtworkInfos() ) {

                    artworkInfos.add( transformArtworkInfo(artworkInfoEntity) );

                }

            }
            program.setArtworkInfos( artworkInfos );

            List<CastMember> castMembers = new ArrayList<>();
            if( null != programEntity.getCast() && null != programEntity.getCast().getCastMembers() && programEntity.getCast().getCastMembers().length > 0 ) {

                for( CastMemberEntity castMemberEntity : programEntity.getCast().getCastMembers() ) {

                    castMembers.add( transformCastMember( castMemberEntity ) );

                }

            }
            program.setCastMembers( castMembers );

            if( null != programEntity.getLiveStreamInfoEntity() ) {

                program.setLiveStreamInfo( liveStreamInfoEntityDataMapper.transform( programEntity.getLiveStreamInfoEntity() ) );
            }

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

    public ChannelInfo transformChannelInfo( ChannelInfoEntity channelInfoEntity ) {

        ChannelInfo channelInfo = null;
        if( null != channelInfoEntity ) {

            channelInfo = new ChannelInfo();
            channelInfo.setChanId( channelInfoEntity.getChanId() );
            channelInfo.setChanNum( channelInfoEntity.getChanNum() );
            channelInfo.setCallSign( channelInfoEntity.getCallSign() );
            channelInfo.setIconURL( channelInfoEntity.getIconURL() );
            channelInfo.setChannelName( channelInfoEntity.getChannelName() );
            channelInfo.setMplexId( channelInfoEntity.getMplexId() );
            channelInfo.setServiceId( channelInfoEntity.getServiceId() );
            channelInfo.setATSCMajorChan( channelInfoEntity.getATSCMajorChan() );
            channelInfo.setATSCMinorChan( channelInfoEntity.getATSCMinorChan() );
            channelInfo.setFormat( channelInfoEntity.getFormat() );
            channelInfo.setFrequencyId( channelInfoEntity.getFrequencyId() );
            channelInfo.setFineTune( channelInfoEntity.getFineTune() );
            channelInfo.setChanFilters( channelInfoEntity.getChanFilters() );
            channelInfo.setSourceId( channelInfoEntity.getSourceId() );
            channelInfo.setInputId( channelInfoEntity.getInputId() );
            channelInfo.setCommFree( channelInfoEntity.isCommFree() );
            channelInfo.setUseEIT( channelInfoEntity.isUseEIT() );
            channelInfo.setVisible( channelInfoEntity.isVisible() );
            channelInfo.setXMLTVID( channelInfoEntity.getXMLTVID() );
            channelInfo.setDefaultAuth( channelInfoEntity.getDefaultAuth() );

            if( null != channelInfoEntity.getPrograms() && channelInfoEntity.getPrograms().length > 0 ) {

                channelInfo.setPrograms( transform( Arrays.asList(channelInfoEntity.getPrograms()) ) );

            }

        }

        return channelInfo;
    }

    public List<ChannelInfo> transformChannelInfoCollection( Collection<ChannelInfoEntity> channelInfoEntityCollection ) {

        List<ChannelInfo> channelInfoList = new ArrayList<>( channelInfoEntityCollection.size() );

        ChannelInfo channelInfo;
        for( ChannelInfoEntity channelInfoEntity : channelInfoEntityCollection ) {

            channelInfo = transformChannelInfo(channelInfoEntity);
            if( null != channelInfo ) {

                channelInfoList.add( channelInfo );

            }

        }

        return channelInfoList;
    }

    public RecordingInfo transformRecordingInfo( RecordingInfoEntity recordingInfoEntity ) {

        RecordingInfo recordingInfo = null;
        if( null != recordingInfoEntity ) {

            recordingInfo = new RecordingInfo();
            recordingInfo.setRecordedId( recordingInfoEntity.getRecordedId() );
            recordingInfo.setStatus( recordingInfoEntity.getStatus() );
            recordingInfo.setPriority( recordingInfoEntity.getPriority() );
            recordingInfo.setStartTs( recordingInfoEntity.getStartTs() );
            recordingInfo.setEndTs( recordingInfoEntity.getEndTs() );
            recordingInfo.setRecordId( recordingInfoEntity.getRecordId() );
            recordingInfo.setRecGroup( recordingInfoEntity.getRecGroup() );
            recordingInfo.setPlayGroup( recordingInfoEntity.getPlayGroup() );
            recordingInfo.setStorageGroup( recordingInfoEntity.getStorageGroup() );
            recordingInfo.setRecType( recordingInfoEntity.getRecType() );
            recordingInfo.setDupInType( recordingInfoEntity.getDupInType() );
            recordingInfo.setDupMethod( recordingInfoEntity.getDupMethod() );
            recordingInfo.setEncoderId( recordingInfoEntity.getEncoderId() );
            recordingInfo.setEncoderName( recordingInfoEntity.getEncoderName() );
            recordingInfo.setProfile( recordingInfoEntity.getProfile() );

        }

        return recordingInfo;
    }

    public List<RecordingInfo> transformRecordingInfoCollection( Collection<RecordingInfoEntity> recordingInfoEntityCollection ) {

        List<RecordingInfo> recordingInfoList = new ArrayList<>( recordingInfoEntityCollection.size() );

        RecordingInfo recordingInfo;
        for( RecordingInfoEntity recordingInfoEntity : recordingInfoEntityCollection ) {

            recordingInfo = transformRecordingInfo(recordingInfoEntity);
            if( null != recordingInfo ) {

                recordingInfoList.add( recordingInfo );

            }

        }

        return recordingInfoList;
    }

    public ArtworkInfo transformArtworkInfo( ArtworkInfoEntity artworkInfoEntity ) {

        ArtworkInfo artworkInfo = null;
        if( null != artworkInfoEntity ) {

            artworkInfo = new ArtworkInfo();
            artworkInfo.setUrl( artworkInfoEntity.getUrl() );
            artworkInfo.setFileName( artworkInfoEntity.getFileName() );
            artworkInfo.setStorageGroup( artworkInfoEntity.getStorageGroup() );
            artworkInfo.setType( artworkInfoEntity.getType() );

        }

        return artworkInfo;
    }

    public List<ArtworkInfo> transformArtworkInfoCollection( Collection<ArtworkInfoEntity> artworkInfoEntityCollection ) {

        List<ArtworkInfo> artworkInfoList = new ArrayList<>( artworkInfoEntityCollection.size() );

        ArtworkInfo artworkInfo;
        for( ArtworkInfoEntity artworkInfoEntity : artworkInfoEntityCollection ) {

            artworkInfo = transformArtworkInfo(artworkInfoEntity);
            if( null != artworkInfo ) {

                artworkInfoList.add( artworkInfo );

            }

        }

        return artworkInfoList;
    }

    public CastMember transformCastMember( CastMemberEntity castMemberEntity ) {

        CastMember castMember = null;
        if( null != castMemberEntity ) {

            castMember = new CastMember();
            castMember.setName( castMemberEntity.getName() );
            castMember.setCharacterName( castMemberEntity.getCharacterName() );
            castMember.setRole( castMemberEntity.getRole() );
            castMember.setTranslatedRole( castMemberEntity.getTranslatedRole() );

        }

        return castMember;
    }

    public List<CastMember> transformCastMemberCollection( Collection<CastMemberEntity> castMemberEntityCollection ) {

        List<CastMember> castMemberList = new ArrayList<>( castMemberEntityCollection.size() );

        CastMember castMember;
        for( CastMemberEntity castMemberEntity : castMemberEntityCollection ) {

            castMember = transformCastMember( castMemberEntity );
            if( null != castMember ) {

                castMemberList.add( castMember );

            }

        }

        return castMemberList;
    }

}
