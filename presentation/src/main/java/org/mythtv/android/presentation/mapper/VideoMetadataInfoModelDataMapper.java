package org.mythtv.android.presentation.mapper;

import org.mythtv.android.domain.ArtworkInfo;
import org.mythtv.android.domain.CastMember;
import org.mythtv.android.domain.VideoMetadataInfo;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.ArtworkInfoModel;
import org.mythtv.android.presentation.model.ArtworkModel;
import org.mythtv.android.presentation.model.CastMemberModel;
import org.mythtv.android.presentation.model.CastModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 8/27/15.
 */
@PerActivity
public class VideoMetadataInfoModelDataMapper {

    @Inject
    public VideoMetadataInfoModelDataMapper() {
    }

    public VideoMetadataInfoModel transform( VideoMetadataInfo videoMetadataInfo ) {

        VideoMetadataInfoModel videoMetadataInfoModel = null;
        if( null != videoMetadataInfo ) {

            videoMetadataInfoModel = new VideoMetadataInfoModel();
            videoMetadataInfoModel.setId( videoMetadataInfo.getId() );
            videoMetadataInfoModel.setTitle( videoMetadataInfo.getTitle() );
            videoMetadataInfoModel.setSubTitle( videoMetadataInfo.getSubTitle() );
            videoMetadataInfoModel.setTagline( videoMetadataInfo.getTagline() );
            videoMetadataInfoModel.setDirector( videoMetadataInfo.getDirector() );
            videoMetadataInfoModel.setStudio( videoMetadataInfo.getStudio() );
            videoMetadataInfoModel.setDescription( videoMetadataInfo.getDescription() );
            videoMetadataInfoModel.setCertification( videoMetadataInfo.getCertification() );
            videoMetadataInfoModel.setInetref( videoMetadataInfo.getInetref() );
            videoMetadataInfoModel.setCollectionref( videoMetadataInfo.getCollectionref() );
            videoMetadataInfoModel.setHomePage( videoMetadataInfo.getHomePage() );
            videoMetadataInfoModel.setReleaseDate( videoMetadataInfo.getReleaseDate() );
            videoMetadataInfoModel.setAddDate( videoMetadataInfo.getAddDate() );
            videoMetadataInfoModel.setUserRating( videoMetadataInfo.getUserRating() );
            videoMetadataInfoModel.setLength( videoMetadataInfo.getLength() );
            videoMetadataInfoModel.setPlayCount( videoMetadataInfo.getPlayCount() );
            videoMetadataInfoModel.setSeason( videoMetadataInfo.getSeason() );
            videoMetadataInfoModel.setEpisode( videoMetadataInfo.getEpisode() );
            videoMetadataInfoModel.setParentalLevel( videoMetadataInfo.getParentalLevel() );
            videoMetadataInfoModel.setVisible( videoMetadataInfo.isVisible() );
            videoMetadataInfoModel.setWatched( videoMetadataInfo.isWatched() );
            videoMetadataInfoModel.setProcessed( videoMetadataInfo.isProcessed() );
            videoMetadataInfoModel.setContentType( videoMetadataInfo.getContentType() );
            videoMetadataInfoModel.setFileName( videoMetadataInfo.getFileName() );
            videoMetadataInfoModel.setHash( videoMetadataInfo.getHash() );
            videoMetadataInfoModel.setHostName( videoMetadataInfo.getHostName() );
            videoMetadataInfoModel.setCoverart( videoMetadataInfo.getCoverart() );
            videoMetadataInfoModel.setFanart( videoMetadataInfo.getFanart() );
            videoMetadataInfoModel.setBanner( videoMetadataInfo.getBanner() );
            videoMetadataInfoModel.setScreenshot( videoMetadataInfo.getScreenshot() );
            videoMetadataInfoModel.setTrailer( videoMetadataInfo.getTrailer() );

            if( null != videoMetadataInfo.getArtwork() && null != videoMetadataInfo.getArtwork().getArtworkInfos() && videoMetadataInfo.getArtwork().getArtworkInfos().length > 0 ) {

                ArtworkModel artworkModel = new ArtworkModel();
                ArtworkInfoModel[] artworkInfoModels = new ArtworkInfoModel[ videoMetadataInfo.getArtwork().getArtworkInfos().length ];
                for( int i = 0; i < videoMetadataInfo.getArtwork().getArtworkInfos().length; i++ ) {

                    ArtworkInfo artworkInfo = videoMetadataInfo.getArtwork().getArtworkInfos()[ i ];

                    ArtworkInfoModel artworkInfoModel = new ArtworkInfoModel();
                    artworkInfoModel.setFileName( artworkInfo.getFileName() );
                    artworkInfoModel.setStorageGroup( artworkInfo.getStorageGroup() );
                    artworkInfoModel.setType( artworkInfo.getType() );
                    artworkInfoModel.setUrl( artworkInfo.getUrl() );

                    artworkInfoModels[ i ] = artworkInfoModel;

                }
                artworkModel.setArtworkInfos( artworkInfoModels );
                videoMetadataInfoModel.setArtwork( artworkModel );

            }

            if( null != videoMetadataInfo.getCast() && null != videoMetadataInfo.getCast().getCastMembers() && videoMetadataInfo.getCast().getCastMembers().length > 0 ) {

                CastModel castModel = new CastModel();
                CastMemberModel[] castMemberModels = new CastMemberModel[ videoMetadataInfo.getCast().getCastMembers().length ];
                for( int i = 0; i < videoMetadataInfo.getCast().getCastMembers().length; i++ ) {

                    CastMember castMember = videoMetadataInfo.getCast().getCastMembers()[ i ];

                    CastMemberModel castMemberModel = new CastMemberModel();
                    castMemberModel.setName( castMember.getName() );
                    castMemberModel.setCharacterName( castMember.getCharacterName() );
                    castMemberModel.setRole( castMember.getRole() );
                    castMemberModel.setTranslatedRole( castMember.getTranslatedRole() );

                    castMemberModels[ i ] = castMemberModel;

                }
                castModel.setCastMembers( castMemberModels );
                videoMetadataInfoModel.setCast( castModel );

            }

        }

        return videoMetadataInfoModel;
    }

    public List<VideoMetadataInfoModel> transform( Collection<VideoMetadataInfo> videoMetadataInfoCollection ) {

        List<VideoMetadataInfoModel> videoMetadataInfoModelList = new ArrayList<>( videoMetadataInfoCollection.size() );

        VideoMetadataInfoModel videoMetadataInfoModel;
        for( VideoMetadataInfo videoMetadataInfo : videoMetadataInfoCollection ) {

            videoMetadataInfoModel = transform( videoMetadataInfo );
            if( null != videoMetadataInfoModel ) {

                videoMetadataInfoModelList.add( videoMetadataInfoModel );

            }

        }

        return videoMetadataInfoModelList;
    }

}
