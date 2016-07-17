package org.mythtv.android.presentation.utils;

import org.joda.time.Duration;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by dmfrey on 5/18/16.
 */
public class MediaInfoHelper {

    private MediaInfoHelper() { }

    public static MediaItemModel transform( final String masterBackendUrl, final ProgramModel programModel ) {

        try {

            String recordingUrl = "", contentType = "";
            if( programModel.getFileName().endsWith( "mp4" ) ) {

                recordingUrl = buildUrl( masterBackendUrl, "/Content/GetFile?FileName=" + URLEncoder.encode( programModel.getFileName(), "UTF-8" ) );
                contentType = "video/mp4";

            } else {

                if( null == programModel.getLiveStreamInfo() ) {

                    return null;
                }

                recordingUrl = buildUrl( masterBackendUrl, programModel.getLiveStreamInfo().getRelativeUrl() );
                contentType = "application/x-mpegURL";
            }

            MediaItemModel mediaItemModel = new MediaItemModel();
            mediaItemModel.setTitle( programModel.getTitle() );
            mediaItemModel.setSubTitle( programModel.getSubTitle() );
            mediaItemModel.setStudio( programModel.getChannel().getChannelName() );

            Duration duration = new Duration( programModel.getRecording().getStartTs(), programModel.getRecording().getEndTs() );
            mediaItemModel.setDuration( duration.getStandardMinutes() );

            mediaItemModel.setUrl( recordingUrl );
            mediaItemModel.setContentType( contentType );

            mediaItemModel.getImages().add( masterBackendUrl + "/Content/GetRecordingArtwork?Inetref=" + programModel.getInetref() + "&Type=coverart&Width=150" );
            mediaItemModel.getImages().add( masterBackendUrl + "/Content/GetRecordingArtwork?Inetref=" + programModel.getInetref() + "&Type=coverart&Width=300" );

            return mediaItemModel;

        } catch( UnsupportedEncodingException e ) {

            return null;
        }

    }

    public static MediaItemModel transform( final String masterBackendUrl, final VideoMetadataInfoModel videoMetadataInfo ) {

        try {

            String contentType = "";
            String videoUrl = buildUrl( masterBackendUrl, "/Content/GetFile?FileName=" + URLEncoder.encode( videoMetadataInfo.getFileName(), "UTF-8" ) );
            if( videoMetadataInfo.getFileName().endsWith( "mp4" ) ) {

                contentType = "video/mp4";

            } else if( videoMetadataInfo.getFileName().endsWith( "ogg" ) || videoMetadataInfo.getFileName().endsWith( "ogv" ) ) {

                contentType = "video/ogg";

            } else if( videoMetadataInfo.getFileName().endsWith( "mkv" ) ) {

                contentType = "video/divx";

            } else if( videoMetadataInfo.getFileName().endsWith( "avi" ) ) {

                contentType = "video/avi";

            } else if( videoMetadataInfo.getFileName().endsWith( "3gp" ) ) {

                contentType = "video/3gpp";

            } else if( null != videoMetadataInfo.getLiveStreamInfo() ) {

                videoUrl = buildUrl( masterBackendUrl, URLEncoder.encode( videoMetadataInfo.getLiveStreamInfo().getRelativeUrl(), "UTF-8" ) );
                contentType = "application/x-mpegURL";

            }

            MediaItemModel mediaItemModel = new MediaItemModel();
            mediaItemModel.setTitle( videoMetadataInfo.getTitle() );
            mediaItemModel.setSubTitle( videoMetadataInfo.getSubTitle() );
            mediaItemModel.setStudio( videoMetadataInfo.getStudio() );

            mediaItemModel.setDuration( videoMetadataInfo.getLength() );
            mediaItemModel.setUrl( videoUrl );
            mediaItemModel.setContentType( contentType );
            mediaItemModel.getImages().add( masterBackendUrl + "/Content/GetVideoArtwork?Id=" + videoMetadataInfo.getId() + "&Type=coverart&Width=150" );
            mediaItemModel.getImages().add( masterBackendUrl + "/Content/GetVideoArtwork?Id=" + videoMetadataInfo.getId() + "&Type=coverart&Width=300" );

            return mediaItemModel;

        } catch( UnsupportedEncodingException e) {

            return null;
        }

    }

    public static String buildUrl( String masterBackendUrl, String context ) throws UnsupportedEncodingException {

        String url = masterBackendUrl + URLEncoder.encode( context, "UTF-8");
        url = url.replaceAll( "%2F", "/" );
        url = url.replaceAll( "%252F", "/" );
        url = url.replaceAll( "%253A", ":" );
        url = url.replaceAll( "\\+", "%20" );
        url = url.replaceAll( "%2B", "%20" );
        url = url.replaceAll( "%3F", "?" );
        url = url.replaceAll( "%3D", "=" );

        return url;
    }

}
