package org.mythtv.android.library.events.videoDir;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 8/3/15.
 */
public class RequestAllVideoDirItemsEvent extends RequestReadEvent {

    private final String parent;

    public RequestAllVideoDirItemsEvent( String parent ) {

        this.parent = parent;

    }

    public String getParent() {

        return parent;
    }

}
