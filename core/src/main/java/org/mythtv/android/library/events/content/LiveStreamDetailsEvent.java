package org.mythtv.android.library.events.content;

import org.mythtv.android.library.events.ReadEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class LiveStreamDetailsEvent extends ReadEvent {

    private final Integer key;
    private final LiveStreamDetails details;

    private boolean modified;

    public LiveStreamDetailsEvent( final Integer key, final LiveStreamDetails details ) {

        this.key = key;
        this.details = details;

        entityFound = true;
        modified = true;

    }

    public Integer getKey() {
        return key;
    }

    public LiveStreamDetails getDetails() {
        return details;
    }

    public boolean isModified() {
        return modified;
    }

    public static LiveStreamDetailsEvent notModified( Integer key ) {

        LiveStreamDetailsEvent event = new LiveStreamDetailsEvent( key, null );
        event.entityFound = false;
        event.modified = false;

        return event;
    }

    public static LiveStreamDetailsEvent notFound( Integer key ) {

        LiveStreamDetailsEvent event = new LiveStreamDetailsEvent( key, null );
        event.entityFound = false;
        event.modified = false;

        return event;
    }

}
