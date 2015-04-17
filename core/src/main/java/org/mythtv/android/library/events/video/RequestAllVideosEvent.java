package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllVideosEvent extends RequestReadEvent {

    private final Integer id;

    public RequestAllVideosEvent( final Integer id ) {

        this.id = id;

    }

    public Integer getId() {
        return id;
    }

}
