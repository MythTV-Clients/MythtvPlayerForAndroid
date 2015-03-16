package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class SearchRecordedProgramsEvent extends RequestReadEvent {

    private final String query;

    public SearchRecordedProgramsEvent( final String query ) {

        this.query = query;

    }

    public String getQuery() { return query; }

}
