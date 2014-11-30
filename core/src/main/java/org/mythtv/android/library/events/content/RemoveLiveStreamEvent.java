package org.mythtv.android.library.events.content;

import org.mythtv.android.library.events.DeleteEvent;

/**
 * Created by dmfrey on 11/18/14.
 */
public class RemoveLiveStreamEvent extends DeleteEvent {

    private final int key;

    public RemoveLiveStreamEvent( final int key ) {

        this.key = key;
    }

    public int getKey() {
        return key;
    }

}
