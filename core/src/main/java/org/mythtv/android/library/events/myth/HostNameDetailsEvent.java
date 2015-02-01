package org.mythtv.android.library.events.myth;

import org.mythtv.android.library.events.ReadEvent;

/**
 * Created by dmfrey on 1/31/15.
 */
public class HostNameDetailsEvent extends ReadEvent {

    private final HostNameDetails details;

    public HostNameDetailsEvent( final HostNameDetails details ) {

        this.details = details;

    }

    public HostNameDetails getDetails() {
        return details;
    }

    public static HostNameDetailsEvent notFound() {

        HostNameDetailsEvent ev = new HostNameDetailsEvent();
        ev.entityFound = false;

        return ev;
    }

    private HostNameDetailsEvent() {

        this.details = null;

    }

}
