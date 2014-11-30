package org.mythtv.android.library.events.content;

import org.mythtv.android.library.events.DeletedEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class LiveStreamRemovedEvent extends DeletedEvent {

    private final Integer key;
    private boolean deletionCompleted;

    public LiveStreamRemovedEvent( final Integer key ) {

        this.key = key;

        deletionCompleted = true;
    }

    public Integer getKey() {
        return key;
    }

    public boolean isDeletionCompleted() {
        return deletionCompleted;
    }

    public static LiveStreamRemovedEvent deletionFailed( final Integer key ) {

        LiveStreamRemovedEvent event = new LiveStreamRemovedEvent( key );
        event.deletionCompleted = false;

        return event;
    }

}
