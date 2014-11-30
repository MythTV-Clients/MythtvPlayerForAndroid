package org.mythtv.android.library.core.service.v027.video;

import org.mythtv.android.library.core.service.v027.dvr.ArtworkInfoHelper;
import org.mythtv.android.library.events.dvr.ArtworkInfoDetails;
import org.mythtv.android.library.events.dvr.CastMemberDetails;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.services.api.v027.beans.ArtworkInfo;
import org.mythtv.services.api.v027.beans.VideoMetadataInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/24/14.
 */
public class VideoHelper {

    public static VideoDetails toDetails( VideoMetadataInfo video ) {

        VideoDetails details = new VideoDetails();
        details.setId( video.getId() );
        details.setTitle(video.getTitle());
        details.setSubTitle(video.getSubTitle());
        details.setTagline(video.getTagline());
        details.setDirector(video.getDirector());
        details.setStudio(video.getStudio());
        details.setDescription(video.getDescription());
        details.setCertification(video.getCertification());
        details.setInetref(video.getInetref());
        details.setCollectionref(video.getCollectionref());
        details.setHomePage(video.getHomePage());
        details.setReleaseDate(video.getReleaseDate());
        details.setAddDate(video.getAddDate());
        details.setUserRating(video.getUserRating());
        details.setLength(video.getLength());
        details.setPlayCount(video.getPlayCount());
        details.setSeason(video.getSeason());
        details.setEpisode(video.getEpisode());
        details.setParentalLevel(video.getParentalLevel());
        details.setVisible(video.isVisible());
        details.setWatched(video.isWatched());
        details.setProcessed(video.isProcessed());
        details.setContentType(video.getContentType());
        details.setFileName(video.getFileName());
        details.setHash(video.getHash());
        details.setHostName(video.getHostName());
        details.setCoverart(video.getCoverart());
        details.setFanart(video.getFanart());
        details.setBanner(video.getBanner());
        details.setScreenshot(video.getScreenshot());
        details.setTrailer(video.getTrailer());

        List<ArtworkInfoDetails> artworkInfoDetails = new ArrayList<ArtworkInfoDetails>();
        if( null != video.getArtwork() && null != video.getArtwork().getArtworkInfos() && video.getArtwork().getArtworkInfos().length > 0 ) {
            for( ArtworkInfo artworkInfo : video.getArtwork().getArtworkInfos() ) {
                artworkInfoDetails.add( ArtworkInfoHelper.toDetails( artworkInfo ) );
            }
        }
        details.setArtworkInfos( artworkInfoDetails );

        List<CastMemberDetails> castMemberDetails = new ArrayList<CastMemberDetails>();
        details.setCastMembers( castMemberDetails );

        return details;
    }

}
