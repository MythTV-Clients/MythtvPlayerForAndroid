package org.mythtv.android.library.events.content;

import org.mythtv.android.library.events.UpdatedEvent;

import java.util.List;

/**
 * Created by dmfrey on 4/5/15.
 */
public class LiveStreamsUpdatedEvent extends UpdatedEvent {

    private final List<LiveStreamDetails> details;

    public LiveStreamsUpdatedEvent(final List<LiveStreamDetails> details) {

        this.details = details;

        if( null != details ) {

            entityFound = !details.isEmpty();

        } else {

            entityFound = false;

        }

    }

    public List<LiveStreamDetails> getDetails() {

        return details;
    }

    public static LiveStreamsUpdatedEvent notUpdated() {

        return new LiveStreamsUpdatedEvent( null );
    }

}
