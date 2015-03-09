package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.DeleteEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class RemoveProgramEvent extends DeleteEvent {

    private final long key;

    public RemoveProgramEvent( final long key ) {

        this.key = key;
    }

    public long getKey() {
        return key;
    }

}
