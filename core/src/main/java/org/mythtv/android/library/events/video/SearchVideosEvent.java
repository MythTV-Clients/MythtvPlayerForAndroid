package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 4/16/15.
 */
public class SearchVideosEvent extends RequestReadEvent {

    private final String query;

    public SearchVideosEvent( final String query ) {

        this.query = query;

    }

    public String getQuery() { return query; }

}
