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

package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.domain.ArtworkInfo;
import org.mythtv.android.domain.CastMember;
import org.mythtv.android.domain.Program;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class ProgramEntityDataMapper {

    private ProgramEntityDataMapper() { }

    public static Program transform( ProgramEntity programEntity ) {

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

                program.setChannel( ChannelInfoEntityDataMapper.transform( programEntity.getChannel() ) );

            }

            if( null != programEntity.getRecording() ) {

                program.setRecording( RecordingInfoEntityDataMapper.transform( programEntity.getRecording() ) );

            }

            List<ArtworkInfo> artworkInfos = new ArrayList<>();
            if( null != programEntity.getArtwork() && null != programEntity.getArtwork().getArtworkInfos() && programEntity.getArtwork().getArtworkInfos().length > 0 ) {

                for( ArtworkInfoEntity artworkInfoEntity : programEntity.getArtwork().getArtworkInfos() ) {

                    artworkInfos.add( ArtworkInfoEntityDataMapper.transform( artworkInfoEntity ) );

                }

            }
            program.setArtworkInfos( artworkInfos );

            List<CastMember> castMembers = new ArrayList<>();
            if( null != programEntity.getCast() && null != programEntity.getCast().getCastMembers() && programEntity.getCast().getCastMembers().length > 0 ) {

                for( CastMemberEntity castMemberEntity : programEntity.getCast().getCastMembers() ) {

                    castMembers.add( CastMemberEntityDataMapper.transform( castMemberEntity ) );

                }

            }
            program.setCastMembers( castMembers );

            if( null != programEntity.getLiveStreamInfoEntity() ) {

                program.setLiveStreamInfo( LiveStreamInfoEntityDataMapper.transform( programEntity.getLiveStreamInfoEntity() ) );
            }

        }

        return program;
    }

    public static List<Program> transform( Collection<ProgramEntity> programEntityCollection ) {

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
