package org.mythtv.android.presentation.mapper;

import org.joda.time.Duration;
import org.mythtv.android.domain.Program;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.domain.VideoMetadataInfo;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.internal.di.components.SharedPreferencesComponent;
import org.mythtv.android.presentation.model.MediaItemModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;

/**
 * Created by dmfrey on 7/10/16.
 */

@PerActivity
public class MediaItemModelMapper {

    private SharedPreferencesComponent sharedPreferencesComponent;

    @Inject
    public MediaItemModelMapper( SharedPreferencesComponent sharedPreferencesComponent ) {

        this.sharedPreferencesComponent = sharedPreferencesComponent;

    }

    public MediaItemModel transform( Program program ) {

        MediaItemModel mediaItemModel = new MediaItemModel();
        mediaItemModel.setTitle( program.getTitle() );
        mediaItemModel.setSubTitle( program.getSubTitle() );
        mediaItemModel.setStudio( program.getChannel().getChannelName() );

        Duration duration = new Duration( program.getRecording().getStartTs(), program.getRecording().getEndTs() );
        mediaItemModel.setDuration( duration.getStandardMinutes() );

        try {

            String masterBackendUrl = getMasterBackendUrl();

            String recordingUrl = "", contentType = "";
            if( program.getFileName().endsWith( "mp4" ) ) {

                recordingUrl = buildUrl( masterBackendUrl, "/Content/GetFile?FileName=" + URLEncoder.encode( program.getFileName(), "UTF-8" ) );
                contentType = "video/mp4";

            } else {

                if( null != program.getLiveStreamInfo() ) {

                    recordingUrl = buildUrl( masterBackendUrl, program.getLiveStreamInfo().getRelativeUrl() );
                    contentType = "application/x-mpegURL";

                }

            }

            mediaItemModel.setUrl( recordingUrl );
            mediaItemModel.setContentType( contentType );

            mediaItemModel.getImages().add( masterBackendUrl + "/Content/GetRecordingArtwork?Inetref=" + program.getInetref() + "&Type=coverart&Width=150" );
            mediaItemModel.getImages().add( masterBackendUrl + "/Content/GetRecordingArtwork?Inetref=" + program.getInetref() + "&Type=coverart&Width=300" );

        } catch( UnsupportedEncodingException e ) {

        }

        return mediaItemModel;
    }

    public MediaItemModel transform( VideoMetadataInfo videoMetadataInfo ) {

        MediaItemModel mediaItemModel = new MediaItemModel();
        mediaItemModel.setTitle( videoMetadataInfo.getTitle() );
        mediaItemModel.setSubTitle( videoMetadataInfo.getSubTitle() );
        mediaItemModel.setStudio( videoMetadataInfo.getStudio() );

        mediaItemModel.setDuration( videoMetadataInfo.getLength() );

        try {

            String masterBackendUrl = getMasterBackendUrl();

            String contentType = "";
            String videoUrl = buildUrl( getMasterBackendUrl(), "/Content/GetFile?FileName=" + URLEncoder.encode( videoMetadataInfo.getFileName(), "UTF-8" ) );
            if( videoMetadataInfo.getFileName().endsWith( "mp4" ) ) {

                contentType = "video/mp4";

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

            mediaItemModel.setUrl( videoUrl );
            mediaItemModel.setContentType( contentType );
            mediaItemModel.getImages().add( masterBackendUrl + "/Content/GetVideoArtwork?Id=" + videoMetadataInfo.getId() + "&Type=coverart&Width=150" );
            mediaItemModel.getImages().add( masterBackendUrl + "/Content/GetVideoArtwork?Id=" + videoMetadataInfo.getId() + "&Type=coverart&Width=300" );

        } catch( UnsupportedEncodingException e ) {

        }

        return mediaItemModel;
    }

    private String getMasterBackendUrl() {

        String host = sharedPreferencesComponent.sharedPreferences().getString( SettingsKeys.KEY_PREF_BACKEND_URL, "" );
        String port = sharedPreferencesComponent.sharedPreferences().getString( SettingsKeys.KEY_PREF_BACKEND_PORT, "6544" );

        return "http://" + host + ":" + port;

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
