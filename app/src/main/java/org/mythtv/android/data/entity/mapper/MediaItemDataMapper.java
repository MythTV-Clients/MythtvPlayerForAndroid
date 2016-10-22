package org.mythtv.android.data.entity.mapper;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.Media;
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

        switch( programEntity.getRecording().getStatus() ) {

            case -3 :
            case -2 :

                String url = "/Content/GetFile?FileName=" + URLEncoder.encode( programEntity.getFileName(), "UTF-8" );
                if( null != programEntity.getLiveStreamInfoEntity() ) {

                    url = programEntity.getLiveStreamInfoEntity().getRelativeUrl();
                    mediaItem.setPercentComplete( programEntity.getLiveStreamInfoEntity().getPercentComplete() );
                    mediaItem.setLiveStreamId( programEntity.getLiveStreamInfoEntity().getId() );
                    mediaItem.setRemoveHttpLiveStreamUrl( String.format( "/Content/RemoveLiveStream?Id=%s", String.valueOf( programEntity.getLiveStreamInfoEntity().getId() ) ) );
                    mediaItem.setGetHttpLiveStreamUrl( String.format( "/Content/GetLiveStream?Id=%s", String.valueOf( programEntity.getLiveStreamInfoEntity().getId() ) ) );

                }

                mediaItem.setMedia( Media.PROGRAM );
                mediaItem.setUrl( url );
                setContentType( mediaItem, url );
                mediaItem.setPreviewUrl( "/Content/GetPreviewImage?RecordedId=" + programEntity.getRecording().getRecordedId() );

                mediaItem.setWatched( ( programEntity.getProgramFlags() & 0x00000200 ) > 0 );
                mediaItem.setMarkWatchedUrl( "/Dvr/UpdateRecordedWatchedStatus" );

                mediaItem.setRecording( programEntity.getRecording().getStatus() == -2 );

                break;

            case -1 :

                mediaItem.setMedia( Media.UPCOMING );

                break;

        }

        mediaItem.setTitle( programEntity.getTitle() );
        mediaItem.setSubTitle( programEntity.getSubTitle() );
        mediaItem.setDescription( programEntity.getDescription() );
        mediaItem.setStartDate( programEntity.getStartTime() );
        mediaItem.setProgramFlags( programEntity.getProgramFlags() );
        mediaItem.setSeason( programEntity.getSeason() );
        mediaItem.setEpisode( programEntity.getEpisode() );
        mediaItem.setStudio( programEntity.getChannel().getCallSign() );

        calculateDuration( mediaItem, programEntity.getRecording().getStartTs(), programEntity.getRecording().getEndTs() );

        if( null != programEntity.getArtwork() ) {

            if( null != programEntity.getArtwork().getArtworkInfos() && programEntity.getArtwork().getArtworkInfos().length > 0 ) {

                addArtwork( mediaItem, programEntity.getArtwork().getArtworkInfos() );

            }

        }

        if( null != programEntity.getLiveStreamInfoEntity() ) {

            mediaItem.setLiveStreamId(programEntity.getLiveStreamInfoEntity().getId());

        }
        mediaItem.setCreateHttpLiveStreamUrl( String.format( "/Content/AddRecordingLiveStream?RecordedId=%s&Width=1280", String.valueOf( programEntity.getRecording().getRecordedId() ) ) );
        mediaItem.setRemoveHttpLiveStreamUrl( String.format( "/Content/RemoveLiveStream?Id=%s", String.valueOf( programEntity.getRecording().getRecordedId() ) ) );

        List<String> castMembers = new ArrayList<>();
        List<String> characters = new ArrayList<>();

        if( null != programEntity.getCast() ) {

            if( null != programEntity.getCast().getCastMembers() && programEntity.getCast().getCastMembers().length != 0 ) {

                for( CastMemberEntity castMember : programEntity.getCast().getCastMembers() ) {

                    if( !castMembers.contains( castMember.getName() ) ) {
                        castMembers.add( castMember.getName() );
                    }

                    if( !characters.contains( castMember.getCharacterName() ) ) {
                        characters.add( castMember.getCharacterName() );
                    }

                }

            }

        }

        if( !castMembers.isEmpty() ) {
            String cast = "";
            for( String name : castMembers ) {
                cast += name + " ";
            }
            mediaItem.setCastMembers( cast.trim() );
        }
        if( !characters.isEmpty() ) {
            String cast = "";
            for( String name : characters ) {
                cast += name + " ";
            }
            mediaItem.setCharacters( cast.trim() );
        }

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

        String url = "/Content/GetFile?FileName=" + URLEncoder.encode( videoEntity.getFileName(), "UTF-8" );
        if( null != videoEntity.getLiveStreamInfoEntity() ) {

            url = videoEntity.getLiveStreamInfoEntity().getRelativeUrl();
            mediaItem.setPercentComplete( videoEntity.getLiveStreamInfoEntity().getPercentComplete() );
            mediaItem.setLiveStreamId( videoEntity.getLiveStreamInfoEntity().getId() );
            mediaItem.setRemoveHttpLiveStreamUrl( String.format( "/Content/RemoveLiveStream?Id=%s", String.valueOf( videoEntity.getLiveStreamInfoEntity().getId() ) ) );
            mediaItem.setGetHttpLiveStreamUrl( String.format( "/Content/GetLiveStream?Id=%s", String.valueOf( videoEntity.getLiveStreamInfoEntity().getId() ) ) );

        }

        mediaItem.setUrl( url );
        setContentType( mediaItem, url );

        mediaItem.setDuration( videoEntity.getLength() );

        if( null != videoEntity.getArtwork() ) {

            if( null != videoEntity.getArtwork().getArtworkInfos() && videoEntity.getArtwork().getArtworkInfos().length > 0 ) {

                addArtwork( mediaItem, videoEntity.getArtwork().getArtworkInfos() );

            }

        }

        mediaItem.setCreateHttpLiveStreamUrl( String.format( "/Content/AddVideoLiveStream?Id=%s&Width=1280", String.valueOf( videoEntity.getId() ) ) );

        mediaItem.setWatched( videoEntity.isWatched() );
        mediaItem.setMarkWatchedUrl( "/Video/UpdateVideoWatchedStatus?Id=%s&Watched=true" );

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

    public static MediaItem transform( MediaItemEntity mediaItemEntity ) {

        MediaItem mediaItem = new MediaItem();
        mediaItem.setId( mediaItemEntity.getId() );
        mediaItem.setMedia( mediaItemEntity.getMedia() );
        mediaItem.setTitle( mediaItemEntity.getTitle() );
        mediaItem.setSubTitle( mediaItemEntity.getSubTitle() );
        mediaItem.setDescription( mediaItemEntity.getDescription() );
        mediaItem.setStartDate( mediaItemEntity.getStartDate() );
        mediaItem.setProgramFlags( mediaItemEntity.getProgramFlags() );
        mediaItem.setSeason( mediaItemEntity.getSeason() );
        mediaItem.setEpisode( mediaItemEntity.getEpisode() );
        mediaItem.setStudio( mediaItemEntity.getStudio() );
        mediaItem.setCastMembers( mediaItemEntity.getCastMembers() );
        mediaItem.setCharacters( mediaItemEntity.getCharacters() );
        mediaItem.setUrl( mediaItemEntity.getUrl() );
        mediaItem.setFanartUrl( mediaItemEntity.getFanartUrl() );
        mediaItem.setCoverartUrl( mediaItemEntity.getCoverartUrl() );
        mediaItem.setBannerUrl( mediaItemEntity.getBannerUrl() );
        mediaItem.setPreviewUrl( mediaItemEntity.getPreviewUrl() );
        mediaItem.setContentType( mediaItemEntity.getContentType() );
        mediaItem.setDuration( mediaItemEntity.getDuration() );
        mediaItem.setPercentComplete( mediaItemEntity.getPercentComplete() );
        mediaItem.setRecording( mediaItemEntity.isRecording() );
        mediaItem.setLiveStreamId( mediaItemEntity.getLiveStreamId() );
        mediaItem.setCreateHttpLiveStreamUrl( mediaItemEntity.getCreateHttpLiveStreamUrl() );
        mediaItem.setRemoveHttpLiveStreamUrl( mediaItemEntity.getRemoveHttpLiveStreamUrl() );
        mediaItem.setWatched( mediaItemEntity.isWatched() );
        mediaItem.setMarkWatchedUrl( mediaItemEntity.getMarkWatchedUrl() );

        return mediaItem;
    }

    public static List<MediaItem> transformMediaItemEntities( Collection<MediaItemEntity> mediaItemEntityCollection ) {

        List<MediaItem> mediaItemList = new ArrayList<>( mediaItemEntityCollection.size() );

        MediaItem mediaItem;
        for( MediaItemEntity mediaItemEntity : mediaItemEntityCollection ) {

            mediaItem = transform( mediaItemEntity );
            if( null != mediaItem ) {

                mediaItemList.add( mediaItem );

            }

        }

        return mediaItemList;
    }

    public static MediaItemEntity transform( MediaItem mediaItem ) {

        MediaItemEntity mediaItemEntity = new MediaItemEntity();
        mediaItemEntity.setId( mediaItem.getId() );
        mediaItemEntity.setMedia( mediaItem.getMedia() );
        mediaItemEntity.setTitle( mediaItem.getTitle() );
        mediaItemEntity.setSubTitle( mediaItem.getSubTitle() );
        mediaItemEntity.setDescription( mediaItem.getDescription() );
        mediaItemEntity.setStartDate( mediaItem.getStartDate() );
        mediaItemEntity.setProgramFlags( mediaItem.getProgramFlags() );
        mediaItemEntity.setSeason( mediaItem.getSeason() );
        mediaItemEntity.setEpisode( mediaItem.getEpisode() );
        mediaItemEntity.setStudio( mediaItem.getStudio() );
        mediaItemEntity.setCastMembers( mediaItem.getCastMembers() );
        mediaItemEntity.setCharacters( mediaItem.getCharacters() );
        mediaItemEntity.setUrl( mediaItem.getUrl() );
        mediaItemEntity.setFanartUrl( mediaItem.getFanartUrl() );
        mediaItemEntity.setCoverartUrl( mediaItem.getCoverartUrl() );
        mediaItemEntity.setBannerUrl( mediaItem.getBannerUrl() );
        mediaItemEntity.setPreviewUrl( mediaItem.getPreviewUrl() );
        mediaItemEntity.setContentType( mediaItem.getContentType() );
        mediaItemEntity.setDuration( mediaItem.getDuration() );
        mediaItemEntity.setPercentComplete( mediaItem.getPercentComplete() );
        mediaItemEntity.setRecording( mediaItem.isRecording() );
        mediaItemEntity.setLiveStreamId( mediaItem.getLiveStreamId() );
        mediaItemEntity.setCreateHttpLiveStreamUrl( mediaItem.getCreateHttpLiveStreamUrl() );
        mediaItemEntity.setRemoveHttpLiveStreamUrl( mediaItem.getRemoveHttpLiveStreamUrl() );
        mediaItemEntity.setWatched( mediaItem.isWatched() );
        mediaItemEntity.setMarkWatchedUrl( mediaItem.getMarkWatchedUrl() );

        return mediaItemEntity;
    }

    public static List<MediaItemEntity> transformMediaItems( Collection<MediaItem> mediaItemCollection ) {

        List<MediaItemEntity> mediaItemList = new ArrayList<>( mediaItemCollection.size() );

        MediaItemEntity mediaItemEntity;
        for( MediaItem mediaItem : mediaItemCollection ) {

            mediaItemEntity = transform( mediaItem );
            if( null != mediaItemEntity ) {

                mediaItemList.add( mediaItemEntity );

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

        } else if( fileName.endsWith( "ts" ) ) {

            mediaItem.setContentType( "video/mp2t" );

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
