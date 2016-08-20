package org.mythtv.android.domain;

import org.joda.time.DateTime;

import lombok.Data;

/**
 * Created by dmfrey on 7/10/16.
 */

@Data
public class MediaItem {

    public enum Media {
        PROGRAM, VIDEO
    }

    public enum Artwork {
        coverart, fanart, banner
    }

    private int id;
    private Media media;
    private String title;
    private String subTitle;
    private String description;
    private DateTime startDate;
    private String season;
    private String episode;
    private String studio; // video = studio, recording = channel
    private String url;
    private String contentType;
    private long duration;

//    public String getCoverArtUrl( String masterBackendUrl,  ) {
//
//        String url = "";
//
//
//        return url;
//    }

}
