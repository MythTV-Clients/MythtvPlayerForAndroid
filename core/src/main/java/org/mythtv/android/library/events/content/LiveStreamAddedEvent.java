package org.mythtv.android.library.events.content;

import org.mythtv.android.library.events.CreatedEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class LiveStreamAddedEvent extends CreatedEvent {

    private final Integer key;
    private final LiveStreamDetails details;

    public LiveStreamAddedEvent( final Integer key, final LiveStreamDetails details ) {

        this.key = key;
        this.details = details;

    }

    public Integer getKey() {
        return key;
    }

    public LiveStreamDetails getDetails() {
        return details;
    }

    public static LiveStreamAddedEvent notAdded() {

        return new LiveStreamAddedEvent( null, null );
    }

}
