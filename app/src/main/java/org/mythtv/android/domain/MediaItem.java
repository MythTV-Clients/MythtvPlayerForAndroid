package org.mythtv.android.domain;

import lombok.Data;

/**
 * Created by dmfrey on 7/10/16.
 */

@Data
public class MediaItem {

    public enum Media {
        PROGRAM, VIDEO
    }

    private int id;
    private Media media;
    private String title;
    private String subTitle;
    private String studio;
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
