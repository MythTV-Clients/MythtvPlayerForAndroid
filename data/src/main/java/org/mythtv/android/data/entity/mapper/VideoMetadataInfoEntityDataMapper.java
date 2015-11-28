package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.Artwork;
import org.mythtv.android.domain.ArtworkInfo;
import org.mythtv.android.domain.Cast;
import org.mythtv.android.domain.CastMember;
import org.mythtv.android.domain.VideoMetadataInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class VideoMetadataInfoEntityDataMapper {

    @Inject
    public VideoMetadataInfoEntityDataMapper() {
    }

    public VideoMetadataInfo transform( VideoMetadataInfoEntity videoMetadataInfoEntity ) {

        VideoMetadataInfo videoMetadataInfo = null;
        if( null != videoMetadataInfoEntity ) {

            videoMetadataInfo = new VideoMetadataInfo();
            videoMetadataInfo.setId( videoMetadataInfoEntity.getId() );
            videoMetadataInfo.setTitle( videoMetadataInfoEntity.getTitle() );
            videoMetadataInfo.setSubTitle( videoMetadataInfoEntity.getSubTitle() );
            videoMetadataInfo.setTagline( videoMetadataInfoEntity.getTagline() );
            videoMetadataInfo.setDirector( videoMetadataInfoEntity.getDirector() );
            videoMetadataInfo.setStudio( videoMetadataInfoEntity.getStudio() );
            videoMetadataInfo.setDescription( videoMetadataInfoEntity.getDescription() );
            videoMetadataInfo.setCertification( videoMetadataInfoEntity.getCertification() );
            videoMetadataInfo.setInetref( videoMetadataInfoEntity.getInetref() );
            videoMetadataInfo.setCollectionref( videoMetadataInfoEntity.getCollectionref() );
            videoMetadataInfo.setHomePage( videoMetadataInfoEntity.getHomePage() );
            videoMetadataInfo.setReleaseDate( videoMetadataInfoEntity.getReleaseDate() );
            videoMetadataInfo.setAddDate( videoMetadataInfoEntity.getAddDate() );
            videoMetadataInfo.setUserRating( videoMetadataInfoEntity.getUserRating() );
            videoMetadataInfo.setLength( videoMetadataInfoEntity.getLength() );
            videoMetadataInfo.setPlayCount( videoMetadataInfoEntity.getPlayCount() );
            videoMetadataInfo.setSeason( videoMetadataInfoEntity.getSeason() );
            videoMetadataInfo.setEpisode( videoMetadataInfoEntity.getEpisode() );
            videoMetadataInfo.setParentalLevel( videoMetadataInfoEntity.getParentalLevel() );
            videoMetadataInfo.setVisible( videoMetadataInfoEntity.isVisible() );
            videoMetadataInfo.setWatched( videoMetadataInfoEntity.isWatched() );
            videoMetadataInfo.setProcessed( videoMetadataInfoEntity.isProcessed() );
            videoMetadataInfo.setContentType( videoMetadataInfoEntity.getContentType() );
            videoMetadataInfo.setFileName( videoMetadataInfoEntity.getFileName() );
            videoMetadataInfo.setHash( videoMetadataInfoEntity.getHash() );
            videoMetadataInfo.setHostName( videoMetadataInfoEntity.getHostName() );
            videoMetadataInfo.setCoverart( videoMetadataInfoEntity.getCoverart() );
            videoMetadataInfo.setFanart( videoMetadataInfoEntity.getFanart() );
            videoMetadataInfo.setBanner( videoMetadataInfoEntity.getBanner() );
            videoMetadataInfo.setScreenshot( videoMetadataInfoEntity.getScreenshot() );
            videoMetadataInfo.setTrailer( videoMetadataInfoEntity.getTrailer() );

            if( null != videoMetadataInfoEntity.getArtwork() && null != videoMetadataInfoEntity.getArtwork().getArtworkInfos() && videoMetadataInfoEntity.getArtwork().getArtworkInfos().length > 0 ) {

                Artwork artwork = new Artwork();
                ArtworkInfo[] artworkInfos = new ArtworkInfo[ videoMetadataInfoEntity.getArtwork().getArtworkInfos().length ];
                for( int i = 0; i < videoMetadataInfoEntity.getArtwork().getArtworkInfos().length; i++ ) {

                    ArtworkInfoEntity artworkInfoEntity = videoMetadataInfoEntity.getArtwork().getArtworkInfos()[ i ];

                    ArtworkInfo artworkInfo = new ArtworkInfo();
                    artworkInfo.setFileName( artworkInfoEntity.getFileName() );
                    artworkInfo.setStorageGroup( artworkInfoEntity.getStorageGroup() );
                    artworkInfo.setType( artworkInfoEntity.getType() );
                    artworkInfo.setUrl( artworkInfoEntity.getUrl() );

                    artworkInfos[ i ] = artworkInfo;

                }
                artwork.setArtworkInfos( artworkInfos );
                videoMetadataInfo.setArtwork( artwork );

            }

            if( null != videoMetadataInfoEntity.getCast() && null != videoMetadataInfoEntity.getCast().getCastMembers() && videoMetadataInfoEntity.getCast().getCastMembers().length > 0 ) {

                Cast cast = new Cast();
                CastMember[] castMembers = new CastMember[ videoMetadataInfoEntity.getCast().getCastMembers().length ];
                for( int i = 0; i < videoMetadataInfoEntity.getCast().getCastMembers().length; i++ ) {

                    CastMemberEntity castMemberEntity = videoMetadataInfoEntity.getCast().getCastMembers()[ i ];

                    CastMember castMember = new CastMember();
                    castMember.setName( castMemberEntity.getName() );
                    castMember.setCharacterName( castMemberEntity.getCharacterName() );
                    castMember.setRole( castMemberEntity.getRole() );
                    castMember.setTranslatedRole( castMemberEntity.getTranslatedRole() );

                    castMembers[ i ] = castMember;

                }
                cast.setCastMembers( castMembers );
                videoMetadataInfo.setCast( cast );

            }

        }

        return videoMetadataInfo;
    }

    public List<VideoMetadataInfo> transform( Collection<VideoMetadataInfoEntity> videoMetadataInfoEntityCollection ) {

        List<VideoMetadataInfo> videoMetadataInfoList = new ArrayList<>( videoMetadataInfoEntityCollection.size() );

        VideoMetadataInfo videoMetadataInfo;
        for( VideoMetadataInfoEntity videoMetadataInfoEntity : videoMetadataInfoEntityCollection ) {

            videoMetadataInfo = transform( videoMetadataInfoEntity );
            if( null != videoMetadataInfo ) {

                videoMetadataInfoList.add( videoMetadataInfo );

            }

        }

        return videoMetadataInfoList;
    }

}
