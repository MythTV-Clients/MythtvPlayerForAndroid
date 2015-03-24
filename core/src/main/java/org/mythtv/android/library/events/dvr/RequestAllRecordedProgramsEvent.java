package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllRecordedProgramsEvent extends RequestReadEvent {

    private final String inetref;

    public RequestAllRecordedProgramsEvent( final String inetref ) {

        this.inetref = inetref;

    }

    public String getInetref() { return inetref; }

}
