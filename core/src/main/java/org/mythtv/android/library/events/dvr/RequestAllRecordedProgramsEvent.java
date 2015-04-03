package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllRecordedProgramsEvent extends RequestReadEvent {

    private final String title;
    private final String inetref;

    public RequestAllRecordedProgramsEvent( final String title, final String inetref ) {

        this.title = title;
        this.inetref = inetref;

    }

    public String getTitle() { return title; }

    public String getInetref() { return inetref; }

}
