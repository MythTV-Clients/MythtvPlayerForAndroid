package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 4/16/15.
 */
public class RequestVideoEvent extends RequestReadEvent {

    private final Integer id;

    public RequestVideoEvent( final Integer id ) {

        this.id = id;

    }

    public Integer getId() { return id; }

}
