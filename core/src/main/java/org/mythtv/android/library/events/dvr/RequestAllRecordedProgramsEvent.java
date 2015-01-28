package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.RequestReadEvent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllRecordedProgramsEvent extends RequestReadEvent {

    private final Boolean descending;
    private final Integer startIndex;
    private final Integer count;
    private final String titleRegEx;
    private final String recGroup;
    private final String storageGroup;

    public RequestAllRecordedProgramsEvent(final Boolean descending, final Integer startIndex, final Integer count, final String titleRegEx, final String recGroup, final String storageGroup) {

        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;
        this.titleRegEx = titleRegEx;
        this.recGroup = recGroup;
        this.storageGroup = storageGroup;

    }

    public Boolean getDescending() {
        return descending;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public Integer getCount() {
        return count;
    }

    public String getTitleRegEx() {

        return titleRegEx;

//        if( null == titleRegEx ) {
//            return null;
//        }
//
//        try {
//            return URLEncoder.encode( titleRegEx, "UTF-8" );
//        } catch( UnsupportedEncodingException e ) {
//            return null;
//        }

    }

    public String getRecGroup() {
        return recGroup;
    }

    public String getStorageGroup() {
        return storageGroup;
    }

}
