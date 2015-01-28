package org.mythtv.android.library.events.content;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllLiveStreamInfosEvent extends RequestReadEvent {

    private final String fileName;

    public RequestAllLiveStreamInfosEvent() {

        this.fileName = null;

    }

    public RequestAllLiveStreamInfosEvent( String fileName ) {

        this.fileName = fileName;

    }

    public String getFileName() {
        return fileName;
    }

}
