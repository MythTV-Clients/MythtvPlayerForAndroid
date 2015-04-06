package org.mythtv.android.library.events.content;

import org.joda.time.DateTime;
import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class RequestLiveStreamDetailsEvent extends RequestReadEvent {

    private final Integer chanId;
    private final DateTime startTime;

    public RequestLiveStreamDetailsEvent( final Integer chanId, final DateTime startTime ) {

        this.chanId = chanId;
        this.startTime = startTime;

    }

    public Integer getChanId() {
        return chanId;
    }

    public DateTime getStartTime() { return startTime; }

}
