package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllVideosEvent extends RequestReadEvent {

    private final String contentType;

    public RequestAllVideosEvent( final String contentType ) {

        this.contentType = contentType;

    }

    public String getContentType() {
        return contentType;
    }

}
