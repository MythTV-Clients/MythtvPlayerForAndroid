package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 11/12/14.
 */
public class RequestAllVideosEvent extends RequestReadEvent {

    private final String folder;
    private final String sort;
    private final Boolean descending;
    private final Integer startIndex;
    private final Integer count;

    public RequestAllVideosEvent( final String folder, final String sort, final Boolean descending, final Integer startIndex, final Integer count ) {

        this.folder = folder;
        this.sort = sort;
        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;

    }

    public String getFolder() {
        return folder;
    }

    public String getSort() {
        return sort;
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

}
