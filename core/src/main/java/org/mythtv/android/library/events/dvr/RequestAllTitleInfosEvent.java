package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllTitleInfosEvent extends RequestReadEvent {

    private final Integer limit;
    private final Integer offset;

    public RequestAllTitleInfosEvent() {

        this.limit = null;
        this.offset = null;

    }

    public RequestAllTitleInfosEvent( final Integer limit, final Integer offset ) {

        this.limit = limit;
        this.offset = offset;

    }

    public Integer getLimit() {

        return limit;
    }

    public Integer getOffset() {

        return offset;
    }

}
