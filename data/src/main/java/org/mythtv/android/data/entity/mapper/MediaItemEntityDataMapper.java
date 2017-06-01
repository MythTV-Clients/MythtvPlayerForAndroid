package org.mythtv.android.data.entity.mapper;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.data.entity.ErrorEntity;
import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.Media;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 9/3/16.
 */
@Singleton
@SuppressWarnings( "PMD.GodClass" )
public final class MediaItemEntityDataMapper {

    private static final String TAG = MediaItemEntityDataMapper.class.getSimpleName();

    private static final String BANNER_KEY = "banner";
    private static final String COVERART_KEY = "coverart";
    private static final String FANART_KEY = "fanart";

    private MediaItemEntityDataMapper() { }

    public static MediaItemEntity transform( ProgramEntity programEntity ) {
        Log.i( TAG, "transform : programEntity=" + programEntity.toString() );

        boolean dateValidationError = false, recordedIdValidationError = false;
        List<ErrorEntity> errors = new ArrayList<>();

        Log.i( TAG, "transform : startTs=" + programEntity.recording().startTs() + ", test=" + ( null == programEntity.recording().startTs() ) );
        if( null == programEntity.recording().startTs() ) {
            Log.i( TAG, "transform : added StartTs to errors" );
            errors.add( ErrorEntity.create( "StartTs", "StartTs is not valid for " + programEntity.title() + " - " + programEntity.subTitle(), -1 ) );
            dateValidationError = true;
        }

        if( null == programEntity.recording().endTs() ) {
            errors.add( ErrorEntity.create( "EndTs", "EndTs is not valid for " + programEntity.title() + " - " + programEntity.subTitle(), -1 ) );
            dateValidationError = true;
        }

        if( programEntity.recording().status() != -1 && ( programEntity.recording().recordedId().equals( "" ) || programEntity.recording().recordedId().equals( "0" )  ) ) {
            errors.add( ErrorEntity.create( "RecordedId", "Recorded Id is not valid for " + programEntity.title() + " - " + programEntity.subTitle(), -1 ) );
            recordedIdValidationError = true;
        }

//        if( !recordedIdValidationError ) {
//            mediaItem.setId( programEntity.recording().translateRecordedId() );
//        }

        Media media = null;
        String url = null, contentType = null, previewUrl = null, recordingGroup = null, updateSavedBookmarkUrl = null;
        int percentComplete = 0, liveStreamId = 0;
        boolean watched = false, recording = false;
        long bookmark = 0, duration = 0;
        switch( programEntity.recording().status() ) {

            case -3 :
            case -2 :

                url = "/Content/GetFile?FileName=" + programEntity.fileName();
                if( null != programEntity.getLiveStreamInfoEntity() ) {

                    url = programEntity.getLiveStreamInfoEntity().relativeUrl();
                    percentComplete = programEntity.getLiveStreamInfoEntity().percentComplete();
                    liveStreamId = programEntity.getLiveStreamInfoEntity().id();

                }

                media = Media.PROGRAM;
                contentType = setContentType( url );
                if( !recordedIdValidationError ) {
                    previewUrl = "/Content/GetPreviewImage?RecordedId=" + programEntity.recording().recordedId();
                }

                watched = ( programEntity.programFlags() & 0x00000200 ) > 0;

                recording = programEntity.recording().status() == -2;

                recordingGroup = programEntity.recording().recGroup();

                break;

            case -1 :

                media = Media.UPCOMING;

                break;

            default :

                break;

        }

        if( !dateValidationError ) {
            duration = calculateDuration( programEntity.recording().startTs(), programEntity.recording().endTs() );
        }

        Map<String, String> artworks = defaultArtworks();
        if( null != programEntity.artwork() && null != programEntity.artwork().artworkInfos() && !programEntity.artwork().artworkInfos().isEmpty() ) {

            artworks = addArtwork( programEntity.artwork().artworkInfos() );

        }

        if( null != programEntity.getLiveStreamInfoEntity() ) {

            liveStreamId = programEntity.getLiveStreamInfoEntity().id();

        }

        List<String> castMembers = new ArrayList<>();
        List<String> characters = new ArrayList<>();

        if( null != programEntity.cast() && null != programEntity.cast().castMembers() && !programEntity.cast().castMembers().isEmpty() ) {

            for( CastMemberEntity castMember : programEntity.cast().castMembers() ) {

                if( !castMembers.contains( castMember.name() ) ) {
                    castMembers.add( castMember.name() );
                }

                if( !characters.contains( castMember.characterName() ) ) {
                    characters.add( castMember.characterName() );
                }

            }

        }

        String cast = "";
        if( !castMembers.isEmpty() ) {

            StringBuilder sb = new StringBuilder();
            for( String name : castMembers ) {
                sb.append( name ).append( ' ' );
            }

            cast = sb.toString().trim();

        }

        String characterNames = "";
        if( !characters.isEmpty() ) {

            StringBuilder sb = new StringBuilder();
            for( String name : characters ) {
                sb.append( name ).append( ' ' );
            }

            characterNames = sb.toString().trim();

        }

        if( !recordedIdValidationError ) {
            updateSavedBookmarkUrl = String.format( "/Dvr/SetSavedBookmark?RecordedId=%s1", String.valueOf( programEntity.recording().recordedId() ) );
        }
        bookmark = programEntity.getBookmark();

        Log.i( TAG, "transform : exit" );
        return MediaItemEntity.create(
                programEntity.recording().translateRecordedId(), media, programEntity.title(),
                programEntity.subTitle(), programEntity.description(), programEntity.startTime(),
                programEntity.programFlags(), programEntity.season(), programEntity.episode(),
                programEntity.channel().callSign(), cast.trim(), characterNames, url, artworks.get( FANART_KEY ),
                artworks.get( COVERART_KEY ), artworks.get( BANNER_KEY ), previewUrl, contentType, duration,
                percentComplete, recording, liveStreamId,
                watched, updateSavedBookmarkUrl, bookmark,
                programEntity.inetref(), null, -1, recordingGroup, errors );
    }

    public static List<MediaItemEntity> transformPrograms( Collection<ProgramEntity> programEntityCollection ) {

        List<MediaItemEntity> mediaItemList = new ArrayList<>( programEntityCollection.size() );

        MediaItemEntity mediaItem;
        for( ProgramEntity programEntity : programEntityCollection ) {

            mediaItem = transform( programEntity );
            if( null != mediaItem.media() ) {
                Log.i( TAG, "transformPrograms : mediaItemEntity=" + mediaItem.toString() );

                mediaItemList.add( mediaItem );

            }

        }

        return mediaItemList;
    }

    public static MediaItemEntity transform( VideoMetadataInfoEntity videoEntity ) throws UnsupportedEncodingException {

        String url = null, contentType = null, previewUrl = null, recordingGroup = null, updateSavedBookmarkUrl = null;
        int percentComplete = 0, liveStreamId = 0;
        boolean watched = false, recording = false;
        long bookmark = 0, duration = 0;

        url = "/Content/GetFile?FileName=" + URLEncoder.encode( videoEntity.fileName(), "UTF-8" );
        if( null != videoEntity.getLiveStreamInfoEntity() ) {

            url = videoEntity.getLiveStreamInfoEntity().relativeUrl();
            percentComplete = videoEntity.getLiveStreamInfoEntity().percentComplete();
            liveStreamId = videoEntity.getLiveStreamInfoEntity().id();

        }

        contentType = setContentType( url );

        duration = videoEntity.length();

        Map<String, String> artworks = defaultArtworks();
        if( null != videoEntity.artwork() && null != videoEntity.artwork().artworkInfos() && !videoEntity.artwork().artworkInfos().isEmpty() ) {

            artworks = addArtwork( videoEntity.artwork().artworkInfos() );

        }

        watched = videoEntity.watched();

        return MediaItemEntity.create(
                videoEntity.id(), Media.valueOf( videoEntity.contentType() ), videoEntity.title(),
                videoEntity.subTitle(), videoEntity.description(), null,
                -1, videoEntity.season(), videoEntity.episode(),
                videoEntity.studio(), null, null, url, artworks.get( FANART_KEY ),
                artworks.get( COVERART_KEY ), artworks.get( BANNER_KEY ), previewUrl, contentType, duration,
                percentComplete, recording, liveStreamId,
                watched, updateSavedBookmarkUrl, bookmark,
                videoEntity.inetref(), videoEntity.certification(), videoEntity.parentalLevel(), recordingGroup, Collections.emptyList() );
    }

    public static List<MediaItemEntity> transformVideos( Collection<VideoMetadataInfoEntity> videoEntityCollection ) throws UnsupportedEncodingException {

        List<MediaItemEntity> mediaItemList = new ArrayList<>( videoEntityCollection.size() );

        MediaItemEntity mediaItem;
        for( VideoMetadataInfoEntity videoEntity : videoEntityCollection ) {

            mediaItem = transform( videoEntity );
            if( null != mediaItem ) {

                mediaItemList.add( mediaItem );

            }

        }

        return mediaItemList;
    }

    private static long calculateDuration( DateTime startTime, DateTime endTime ) {

        return new Duration( startTime, endTime ).getStandardMinutes();
    }

    private static String setContentType( String fileName ) {

        if( fileName.endsWith( "mp4" ) ) {

            return "video/mp4";

        } else if( fileName.endsWith( "ogg" ) || fileName.endsWith( "ogv" ) ) {

            return "video/ogg";

        } else if( fileName.endsWith( "mkv" ) ) {

            return "video/divx";

        } else if( fileName.endsWith( "avi" ) ) {

            return "video/avi";

        } else if( fileName.endsWith( "3gp" ) ) {

            return "video/3gpp";

        } else if( fileName.endsWith( "m3u8" ) ) {

            return "application/x-mpegURL";

        } else if( fileName.endsWith( "ts" ) ) {

            return "video/mp2t";

        }

        return null;
    }

    private static Map<String, String> addArtwork( List<ArtworkInfoEntity> artworkInfoEntities ) {

        Map<String, String> artworks = defaultArtworks();

        for( ArtworkInfoEntity artworkInfoEntity : artworkInfoEntities ) {

            switch( artworkInfoEntity.type() ) {

                case BANNER_KEY :

                    artworks.put( BANNER_KEY, artworkInfoEntity.url() );
                    break;

                case COVERART_KEY :

                    artworks.put( COVERART_KEY, artworkInfoEntity.url() );
                    break;

                case FANART_KEY :

                    artworks.put( FANART_KEY, artworkInfoEntity.url() );
                    break;

                default :

                    break;

            }

        }

        return artworks;
    }

    private static Map<String, String> defaultArtworks() {

        Map<String, String> artworks = new HashMap<>();
        artworks.put( BANNER_KEY, null );
        artworks.put( COVERART_KEY, null );
        artworks.put( FANART_KEY, null );


        return artworks;
    }

}
