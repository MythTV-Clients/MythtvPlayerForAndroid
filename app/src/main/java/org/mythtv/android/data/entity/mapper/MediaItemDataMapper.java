package org.mythtv.android.data.entity.mapper;

import static org.mythtv.android.domain.MediaItem.Media;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.MediaItem;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dmfrey on 9/3/16.
 */

@Singleton
public class MediaItemDataMapper {

    private MediaItemDataMapper() { }

    public static MediaItem transform( ProgramEntity programEntity ) throws UnsupportedEncodingException {

        MediaItem mediaItem = new MediaItem();
        mediaItem.setId( programEntity.getRecording().getRecordedId() );
        mediaItem.setMedia( Media.PROGRAM );
        mediaItem.setTitle( programEntity.getTitle() );
        mediaItem.setSubTitle( programEntity.getSubTitle() );
        mediaItem.setDescription( programEntity.getDescription() );
        mediaItem.setStartDate( programEntity.getStartTime() );
        mediaItem.setSeason( programEntity.getSeason() );
        mediaItem.setEpisode( programEntity.getEpisode() );
        mediaItem.setStudio( programEntity.getChannel().getCallSign() );

        mediaItem.setUrl( "/Content/GetFile?FileName=" + URLEncoder.encode( programEntity.getFileName(), "UTF-8" ) );
        setContentType( mediaItem, programEntity.getFileName() );

        calculateDuration( mediaItem, programEntity.getRecording().getStartTs(), programEntity.getRecording().getEndTs() );

        if( null != programEntity.getArtwork() ) {

            if( null != programEntity.getArtwork().getArtworkInfos() && programEntity.getArtwork().getArtworkInfos().length > 0 ) {

                addArtwork( mediaItem, programEntity.getArtwork().getArtworkInfos() );

            }

        }
        mediaItem.setPreviewUrl( "/Content/GetPreviewImage?RecordedId=" + programEntity.getRecording().getRecordedId() );

        return mediaItem;
    }

    public static List<MediaItem> transformPrograms( Collection<ProgramEntity> programEntityCollection ) throws UnsupportedEncodingException {

        List<MediaItem> mediaItemList = new ArrayList<>( programEntityCollection.size() );

        MediaItem mediaItem;
        for( ProgramEntity programEntity : programEntityCollection ) {

            mediaItem = transform( programEntity );
            if( null != mediaItem ) {

                mediaItemList.add( mediaItem );

            }

        }

        return mediaItemList;
    }

    public static MediaItem transform( VideoMetadataInfoEntity videoEntity ) throws UnsupportedEncodingException {

        MediaItem mediaItem = new MediaItem();
        mediaItem.setId( videoEntity.getId() );
        mediaItem.setMedia( Media.VIDEO );
        mediaItem.setTitle( videoEntity.getTitle() );
        mediaItem.setSubTitle( videoEntity.getSubTitle() );
        mediaItem.setDescription( videoEntity.getDescription() );
        mediaItem.setSeason( videoEntity.getSeason() );
        mediaItem.setEpisode( videoEntity.getEpisode() );
        mediaItem.setStudio( videoEntity.getStudio() );

        mediaItem.setUrl( "/Content/GetFile?FileName=" + URLEncoder.encode( videoEntity.getFileName(), "UTF-8" ) );
        setContentType( mediaItem, videoEntity.getFileName() );

        mediaItem.setDuration( videoEntity.getLength() );

        if( null != videoEntity.getArtwork() ) {

            if( null != videoEntity.getArtwork().getArtworkInfos() && videoEntity.getArtwork().getArtworkInfos().length > 0 ) {

                addArtwork( mediaItem, videoEntity.getArtwork().getArtworkInfos() );

            }

        }

        return mediaItem;
    }

    public static List<MediaItem> transformVideos( Collection<VideoMetadataInfoEntity> videoEntityCollection ) throws UnsupportedEncodingException {

        List<MediaItem> mediaItemList = new ArrayList<>( videoEntityCollection.size() );

        MediaItem mediaItem;
        for( VideoMetadataInfoEntity videoEntity : videoEntityCollection ) {

            mediaItem = transform( videoEntity );
            if( null != mediaItem ) {

                mediaItemList.add( mediaItem );

            }

        }

        return mediaItemList;
    }

    private static void calculateDuration( MediaItem mediaItem, DateTime startTime, DateTime endTime ) {

        long duration = new Duration( startTime, endTime ).getStandardMinutes();
        mediaItem.setDuration( duration );
    }

    private static void setContentType( MediaItem mediaItem, String fileName ) {

        if( fileName.endsWith( "mp4" ) ) {

            mediaItem.setContentType( "video/mp4" );

        } else if( fileName.endsWith( "ogg" ) || fileName.endsWith( "ogv" ) ) {

            mediaItem.setContentType( "video/ogg" );

        } else if( fileName.endsWith( "mkv" ) ) {

            mediaItem.setContentType( "video/divx" );

        } else if( fileName.endsWith( "avi" ) ) {

            mediaItem.setContentType( "video/avi" );

        } else if( fileName.endsWith( "3gp" ) ) {

            mediaItem.setContentType( "video/3gpp" );

        } else if( fileName.endsWith( "m3u8" ) ) {

            mediaItem.setContentType( "application/x-mpegURL" );

        }

    }

    private static void addArtwork( MediaItem mediaItem, ArtworkInfoEntity[] artworkInfoEntities ) {

        for( ArtworkInfoEntity artworkInfoEntity : artworkInfoEntities ) {

            switch( artworkInfoEntity.getType() ) {

                case "banner" :

                    mediaItem.setBannerUrl( artworkInfoEntity.getUrl() );
                    break;

                case "coverart" :

                    mediaItem.setCoverartUrl( artworkInfoEntity.getUrl() );
                    break;

                case "fanart" :

                    mediaItem.setFanartUrl( artworkInfoEntity.getUrl() );
                    break;

            }

        }

    }

}
