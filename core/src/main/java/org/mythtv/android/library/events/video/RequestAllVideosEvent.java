package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllVideosEvent extends RequestReadEvent {

    private final String contentType;
    private final String title;
    private final Integer season;

    public RequestAllVideosEvent( final String contentType, final String title, final Integer season ) {

        this.contentType = contentType;
        this.title = title;
        this.season = season;

    }

    public String getContentType() {
        return contentType;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSeason() { return season; }

}
