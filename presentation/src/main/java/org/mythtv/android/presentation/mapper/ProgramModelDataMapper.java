/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.presentation.mapper;

import android.util.Log;

import org.mythtv.android.domain.ArtworkInfo;
import org.mythtv.android.domain.CastMember;
import org.mythtv.android.domain.ChannelInfo;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.RecordingInfo;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.ArtworkInfoModel;
import org.mythtv.android.presentation.model.CastMemberModel;
import org.mythtv.android.presentation.model.ChannelInfoModel;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.model.RecordingInfoModel;

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

    private static final String TAG = ProgramModelDataMapper.class.getSimpleName();

    private final LiveStreamInfoModelDataMapper liveStreamInfoModelDataMapper;

    @Inject
    public ProgramModelDataMapper( LiveStreamInfoModelDataMapper liveStreamInfoModelDataMapper ) {

        this.liveStreamInfoModelDataMapper = liveStreamInfoModelDataMapper;

    }

    public ProgramModel transform( Program program ) {

        if( null == program ) {

            throw new IllegalArgumentException( "Cannot transform a null value" );
        }

//        Log.i( TAG, "transform : program=" + program.toString() );

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

        if( null != program.getChannel() ) {

            ChannelInfo channelInfo = program.getChannel();

            ChannelInfoModel channelInfoModel = new ChannelInfoModel();
            channelInfoModel.setChanId( channelInfo.getChanId() );
            channelInfoModel.setChanNum( channelInfo.getChanNum() );
            channelInfoModel.setCallSign( channelInfo.getCallSign() );
            channelInfoModel.setIconURL( channelInfo.getIconURL() );
            channelInfoModel.setChannelName( channelInfo.getChannelName() );
            channelInfoModel.setMplexId( channelInfo.getMplexId() );
            channelInfoModel.setServiceId( channelInfo.getServiceId() );
            channelInfoModel.setATSCMajorChan( channelInfo.getATSCMajorChan() );
            channelInfoModel.setATSCMinorChan( channelInfo.getATSCMinorChan() );
            channelInfoModel.setFormat( channelInfo.getFormat() );
            channelInfoModel.setFrequencyId( channelInfo.getFrequencyId() );
            channelInfoModel.setFineTune( channelInfo.getFineTune() );
            channelInfoModel.setChanFilters( channelInfo.getChanFilters() );
            channelInfoModel.setSourceId( channelInfo.getSourceId() );
            channelInfoModel.setInputId( channelInfo.getInputId() );
            channelInfoModel.setCommFree( channelInfo.isCommFree() );
            channelInfoModel.setUseEIT( channelInfo.isUseEIT() );
            channelInfoModel.setVisible( channelInfo.isVisible() );
            channelInfoModel.setXMLTVID( channelInfo.getXMLTVID() );
            channelInfoModel.setDefaultAuth( channelInfo.getDefaultAuth() );
            programModel.setChannel( channelInfoModel );

        }

        if( null != program.getRecording() ) {

            RecordingInfo recordingInfo = program.getRecording();
//            Log.i( TAG, "transform : recordingInfo=" + recordingInfo.toString() );

            RecordingInfoModel recordingInfoModel = new RecordingInfoModel();
            recordingInfoModel.setRecordedId( recordingInfo.getRecordedId() );
            recordingInfoModel.setStatus( recordingInfo.getStatus() );
            recordingInfoModel.setPriority( recordingInfo.getPriority() );
            recordingInfoModel.setStartTs( recordingInfo.getStartTs() );
            recordingInfoModel.setEndTs( recordingInfo.getEndTs() );
            recordingInfoModel.setRecordId( recordingInfo.getRecordId() );
            recordingInfoModel.setRecGroup( recordingInfo.getRecGroup() );
            recordingInfoModel.setPlayGroup( recordingInfo.getPlayGroup() );
            recordingInfoModel.setStorageGroup( recordingInfo.getStorageGroup() );
            recordingInfoModel.setRecType( recordingInfo.getRecType() );
            recordingInfoModel.setDupInType( recordingInfo.getDupInType() );
            recordingInfoModel.setDupMethod( recordingInfo.getDupMethod() );
            recordingInfoModel.setEncoderId( recordingInfo.getEncoderId() );
            recordingInfoModel.setEncoderName( recordingInfo.getEncoderName() );
            recordingInfoModel.setProfile( recordingInfo.getProfile() );
            programModel.setRecording( recordingInfoModel );
//            Log.i( TAG, " transform : programModel.getRecording=" + programModel.getRecording().toString() );

        }

        List<ArtworkInfoModel> artworkInfos = new ArrayList<>();
        if( null != program.getArtworkInfos() && !program.getArtworkInfos().isEmpty() ) {

            for( ArtworkInfo detail : program.getArtworkInfos() ) {

                ArtworkInfoModel artworkInfoModel = new ArtworkInfoModel();
                artworkInfoModel.setUrl( detail.getUrl() );
                artworkInfoModel.setFileName( detail.getFileName() );
                artworkInfoModel.setStorageGroup( detail.getStorageGroup() );
                artworkInfoModel.setType( detail.getType() );
                artworkInfos.add( artworkInfoModel );

            }

        }
        programModel.setArtworkInfos( artworkInfos );

        List<CastMemberModel> castMembers = new ArrayList<>();
        if( null != program.getCastMembers() && !program.getCastMembers().isEmpty() ) {

            for( CastMember detail : program.getCastMembers() ) {

                CastMemberModel castMemberModel = new CastMemberModel();
                castMemberModel.setName( detail.getName() );
                castMemberModel.setCharacterName( detail.getCharacterName() );
                castMemberModel.setRole( detail.getRole() );
                castMemberModel.setTranslatedRole( detail.getTranslatedRole() );
                castMembers.add( castMemberModel );

            }

        }
        programModel.setCastMembers( castMembers );

        if( null != program.getLiveStreamInfo() ) {

            programModel.setLiveStreamInfo( liveStreamInfoModelDataMapper.transform( program.getLiveStreamInfo() ) );

        }

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
