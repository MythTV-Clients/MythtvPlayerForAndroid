package org.mythtv.android.library.events.content;

import org.joda.time.DateTime;
import org.mythtv.android.library.events.ReadEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class LiveStreamDetailsEvent extends ReadEvent {

    private final Integer chanId;
    private final DateTime startTime;
    private final LiveStreamDetails details;

    private boolean modified;

    public LiveStreamDetailsEvent( final Integer chanId, final DateTime startTime, final LiveStreamDetails details ) {

        this.chanId = chanId;
        this.startTime = startTime;
        this.details = details;

        entityFound = true;
        modified = true;

    }

    public Integer getChanId() {
        return chanId;
    }

    public DateTime getStartTime() { return startTime; }

    public LiveStreamDetails getDetails() {
        return details;
    }

    public boolean isModified() {
        return modified;
    }

    public static LiveStreamDetailsEvent notModified( final Integer chanId, final DateTime startTime ) {

        LiveStreamDetailsEvent event = new LiveStreamDetailsEvent( chanId, startTime, null );
        event.entityFound = false;
        event.modified = false;

        return event;
    }

    public static LiveStreamDetailsEvent notFound( final Integer chanId, final DateTime startTime ) {

        LiveStreamDetailsEvent event = new LiveStreamDetailsEvent( chanId, startTime, null );
        event.entityFound = false;
        event.modified = false;

        return event;
    }

}
