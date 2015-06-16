/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.library.persistence.service.video;

import org.mythtv.android.library.events.dvr.ArtworkInfoDetails;
import org.mythtv.android.library.events.dvr.CastMemberDetails;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.android.library.persistence.domain.dvr.ArtworkInfo;
import org.mythtv.android.library.persistence.domain.dvr.CastMember;
import org.mythtv.android.library.persistence.domain.video.Video;
import org.mythtv.android.library.persistence.service.dvr.ArtworkInfoHelper;
import org.mythtv.android.library.persistence.service.dvr.CastMemberHelper;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by dmfrey on 11/24/14.
 */
public class VideoHelper {

    public static VideoDetails toDetails( Video video ) {

        VideoDetails details = new VideoDetails();
        details.setId( video.getId() );
        details.setTitle( video.getTitle() );
        details.setSubTitle( video.getSubTitle() );
        details.setTagline( video.getTagline() );
        details.setDirector( video.getDirector() );
        details.setStudio( video.getStudio() );
        details.setDescription( video.getDescription() );
        details.setCertification( video.getCertification() );
        details.setInetref( video.getInetref() );
        details.setCollectionref( video.getCollectionref() );
        details.setHomePage( video.getHomePage() );
        details.setReleaseDate( video.getReleaseDate() );
        details.setAddDate( video.getAddDate() );
        details.setUserRating( video.getUserRating() );
        details.setLength( video.getLength() );
        details.setPlayCount( video.getPlayCount() );
        details.setSeason( video.getSeason() );
        details.setEpisode( video.getEpisode() );
        details.setParentalLevel( video.getParentalLevel() );
        details.setVisible( video.isVisible() );
        details.setWatched( video.isWatched() );
        details.setProcessed( video.isProcessed() );
        details.setContentType( video.getContentType() );
        details.setFileName( video.getFileName() );
        details.setHash( video.getHash() );
        details.setHostName( video.getHostName() );
        details.setCoverart( video.getCoverart() );
        details.setFanart( video.getFanart() );
        details.setBanner( video.getBanner() );
        details.setScreenshot( video.getScreenshot() );
        details.setTrailer( video.getTrailer() );

        List<ArtworkInfoDetails> artworkInfoDetails = new ArrayList<>();
        if( null != video.getArtworkInfos() && !video.getArtworkInfos().isEmpty() ) {
            for( ArtworkInfo artworkInfo : video.getArtworkInfos() ) {
                artworkInfoDetails.add( ArtworkInfoHelper.toDetails(artworkInfo) );
            }
        }
        details.setArtworkInfos( artworkInfoDetails );

        List<CastMemberDetails> castMemberDetails = new ArrayList<>();
        if( null != video.getCastMembers() && !video.getCastMembers().isEmpty() ) {
            for( CastMember castMember : video.getCastMembers() ) {
                castMemberDetails.add( CastMemberHelper.toDetails( castMember ) );
            }
        }
        details.setCastMembers( castMemberDetails );

        return details;
    }

    public static Video fromDetails( VideoDetails details ) {

        Video video = new Video();
        video.setId( details.getId() );
        video.setTitle( details.getTitle() );
        video.setSubTitle( details.getSubTitle() );
        video.setTagline( details.getTagline() );
        video.setDirector( details.getDirector() );
        video.setStudio( details.getStudio() );
        video.setDescription( details.getDescription() );
        video.setCertification( details.getCertification() );
        video.setInetref( details.getInetref() );
        video.setCollectionref( details.getCollectionref() );
        video.setHomePage( details.getHomePage() );
        video.setReleaseDate( details.getReleaseDate() );
        video.setAddDate( details.getAddDate() );
        video.setUserRating( details.getUserRating() );
        video.setLength( details.getLength() );
        video.setPlayCount( details.getPlayCount() );
        video.setSeason( details.getSeason() );
        video.setEpisode( details.getEpisode() );
        video.setParentalLevel( details.getParentalLevel() );
        video.setVisible( details.isVisible() );
        video.setWatched( details.isWatched() );
        video.setProcessed( details.isProcessed() );
        video.setContentType( details.getContentType() );
        video.setFileName( details.getFileName() );
        video.setHash( details.getHash() );
        video.setHostName( details.getHostName() );
        video.setCoverart( details.getCoverart() );
        video.setFanart( details.getFanart() );
        video.setBanner( details.getBanner() );
        video.setScreenshot( details.getScreenshot() );
        video.setTrailer( details.getTrailer() );

        List<ArtworkInfo> artworkInfos = new ArrayList<>();
        if( null != details.getArtworkInfos() && !details.getArtworkInfos().isEmpty() ) {
            for( ArtworkInfoDetails artworkInfoDetails : details.getArtworkInfos() ) {
                artworkInfos.add( ArtworkInfoHelper.fromDetails(artworkInfoDetails) );
            }
        }
        video.setArtworkInfos(artworkInfos);

        List<CastMember> castMembers = new ArrayList<>();
        if( null != details.getCastMembers() && !details.getCastMembers().isEmpty() ) {
            for( CastMemberDetails castMemberDetails : details.getCastMembers() ) {
                castMembers.add( CastMemberHelper.fromDetails(castMemberDetails ) );
            }
        }
        video.setCastMembers( castMembers );

        return video;
    }

}
