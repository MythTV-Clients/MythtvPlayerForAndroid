package org.mythtv.android.library.events.dvr;

import org.joda.time.DateTime;
import org.mythtv.android.library.events.ReadEvent;

/**
 * Created by dmfrey on 4/5/15.
 */
public class ProgramDetailsEvent extends ReadEvent {

    private final Integer chanId;
    private final DateTime startTime;
    private final ProgramDetails details;

    public ProgramDetailsEvent( final Integer chanId, final DateTime startTime, final ProgramDetails details ) {

        this.chanId = chanId;
        this.startTime = startTime;
        this.details = details;

    }

    public int getChanId() {

        return chanId;
    }

    public DateTime getStartTime() {

        return startTime;
    }

    public ProgramDetails getDetails() {

        return details;
    }

    public static ProgramDetailsEvent notFound( final Integer chanId, final DateTime startTime ) {

        ProgramDetailsEvent ev = new ProgramDetailsEvent( chanId, startTime, null );
        ev.entityFound = false;

        return ev;
    }

}
