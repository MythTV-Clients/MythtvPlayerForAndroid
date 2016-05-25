package org.mythtv.android.app.utils;

import android.net.Uri;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.common.images.WebImage;

import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by dmfrey on 5/18/16.
 */
public class MediaInfoHelper {

    private MediaInfoHelper() { }

    public static MediaInfo programModelToMediaInfo( final String masterBackendUrl, final ProgramModel programModel ) {

        try {

            String recordingUrl = buildUrl( masterBackendUrl, programModel.getLiveStreamInfo().getRelativeUrl() );

            MediaMetadata md = new MediaMetadata();
            md.putString( MediaMetadata.KEY_TITLE, programModel.getTitle() );
            md.putString( MediaMetadata.KEY_SUBTITLE, programModel.getSubTitle() );
            md.putString( MediaMetadata.KEY_STUDIO, programModel.getChannel().getChannelName() );
            md.addImage( new WebImage( Uri.parse( masterBackendUrl + "/Content/GetRecordingArtwork?Inetref=" + programModel.getInetref() + "&Type=coverart&Width=150" ) ) );
            md.addImage( new WebImage( Uri.parse( masterBackendUrl + "/Content/GetRecordingArtwork?Inetref=" + programModel.getInetref() + "&Type=coverart&Width=300" ) ) );

            return new MediaInfo.Builder( recordingUrl )
                    .setStreamType( MediaInfo.STREAM_TYPE_BUFFERED )
                    .setContentType( "application/x-mpegURL" )
                    .setMetadata( md )
                    .build();

        } catch( UnsupportedEncodingException e ) {

            return null;
        }

    }

    public static MediaInfo videoModelToMediaInfo( final String masterBackendUrl, final VideoMetadataInfoModel videoModel ) {

        try {

            String videoUrl = buildUrl( masterBackendUrl, "/Content/GetFile?FileName=" + URLEncoder.encode( videoModel.getFileName(), "UTF-8" ) );
            if( null != videoModel.getLiveStreamInfo() ) {

                videoUrl = buildUrl( masterBackendUrl, URLEncoder.encode( videoModel.getLiveStreamInfo().getRelativeUrl(), "UTF-8" ) );

            }

            String mimeType = "video/mp4";
            if( null != videoModel.getLiveStreamInfo() ) {

                mimeType = "application/x-mpegURL";

            } else {

                if( videoModel.getFileName().endsWith( "mkv" ) ) {

                    mimeType = "video/divx";

                } else if( videoModel.getFileName().endsWith( "avi" ) ) {

                    mimeType = "video/avi";

                } else if( videoModel.getFileName().endsWith( "3gp" ) ) {

                    mimeType = "video/3gpp";

                }

            }

            MediaMetadata md = new MediaMetadata();
            md.putString( MediaMetadata.KEY_TITLE, videoModel.getTitle() );
            md.putString( MediaMetadata.KEY_SUBTITLE, videoModel.getSubTitle() );
            md.putString( MediaMetadata.KEY_STUDIO, videoModel.getStudio() );
            md.addImage( new WebImage( Uri.parse( masterBackendUrl + "/Content/GetVideoArtwork?Id=" + videoModel.getId() + "&Type=coverart&Width=150" ) ) );
            md.addImage( new WebImage( Uri.parse( masterBackendUrl + "/Content/GetVideoArtwork?Id=" + videoModel.getId() + "&Type=coverart&Width=300" ) ) );

            return new MediaInfo.Builder( videoUrl )
                    .setStreamType( MediaInfo.STREAM_TYPE_BUFFERED )
                    .setContentType( mimeType )
                    .setMetadata( md )
                    .build();

        } catch( UnsupportedEncodingException e) {

            return null;
        }

    }

    public static String buildUrl( String masterBackendUrl, String context ) throws UnsupportedEncodingException {

        String url = masterBackendUrl + URLEncoder.encode( context, "UTF-8");
        url = url.replaceAll( "%2F", "/" );
        url = url.replaceAll( "%252F", "/" );
        url = url.replaceAll( "\\+", "%20" );
        url = url.replaceAll( "%2B", "%20" );
        url = url.replaceAll( "%3F", "?" );
        url = url.replaceAll( "%3D", "=" );

        return url;
    }

}
