package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.DeletedEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class TitleInfoRemovedEvent extends DeletedEvent {

    private final Long key;
    private boolean deletionCompleted;

    public TitleInfoRemovedEvent( final Long key ) {

        this.key = key;

        deletionCompleted = true;
    }

    public Long getKey() {
        return key;
    }

    public boolean isDeletionCompleted() {
        return deletionCompleted;
    }

    public static TitleInfoRemovedEvent deletionFailed( final Long key ) {

        TitleInfoRemovedEvent event = new TitleInfoRemovedEvent( key );
        event.deletionCompleted = false;

        return event;
    }

}
