package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.DeleteEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class RemoveTitleInfoEvent extends DeleteEvent {

    private final long key;

    public RemoveTitleInfoEvent( final long key ) {

        this.key = key;
    }

    public long getKey() {
        return key;
    }

}
