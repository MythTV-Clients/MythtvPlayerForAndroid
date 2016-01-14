package org.mythtv.android.presentation.utils;

import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

/**
 * Created by dmfrey on 1/13/16.
 */
public class SeasonEpisodeFormatter {

    private SeasonEpisodeFormatter() {}

    public static String format( VideoMetadataInfoModel videoMetadataInfoModel ) {

        if( videoMetadataInfoModel.getSeason() == -1 || videoMetadataInfoModel.getEpisode() == -1 ) {

            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append( "S" );
        if( videoMetadataInfoModel.getSeason() < 10 ) {
            sb.append( "0" );
        }
        sb.append( videoMetadataInfoModel.getSeason() );

        sb.append( "E" );
        if( videoMetadataInfoModel.getEpisode() < 10 ) {
            sb.append( "0" );
        }
        sb.append( videoMetadataInfoModel.getEpisode() );

        return sb.toString();
    }

}
