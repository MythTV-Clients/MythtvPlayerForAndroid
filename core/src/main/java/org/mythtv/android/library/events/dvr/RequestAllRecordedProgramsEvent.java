package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllRecordedProgramsEvent extends RequestReadEvent {

    private final String title;

    public RequestAllRecordedProgramsEvent( final String title ) {

        this.title = title;

    }

    public String getTitle() { return title; }

}
