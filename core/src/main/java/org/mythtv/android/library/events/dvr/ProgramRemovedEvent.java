package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.DeletedEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class ProgramRemovedEvent extends DeletedEvent {

    private final Long key;
    private boolean deletionCompleted;

    public ProgramRemovedEvent(final Long key) {

        this.key = key;

        deletionCompleted = true;
    }

    public Long getKey() {
        return key;
    }

    public boolean isDeletionCompleted() {
        return deletionCompleted;
    }

    public static ProgramRemovedEvent deletionFailed( final Long key ) {

        ProgramRemovedEvent event = new ProgramRemovedEvent( key );
        event.deletionCompleted = false;

        return event;
    }

}
