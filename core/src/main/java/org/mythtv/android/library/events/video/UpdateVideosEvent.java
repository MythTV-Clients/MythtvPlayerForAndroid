package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.UpdateEvent;

import java.util.List;

/**
 * Created by dmfrey on 4/16/15.
 */
public class UpdateVideosEvent extends UpdateEvent {

    private final String folder;
    private final String sort;
    private final Boolean descending;
    private final Integer startIndex;
    private final Integer count;

    private List<VideoDetails> details;

    public UpdateVideosEvent( final String folder, final String sort, final Boolean descending, final Integer startIndex, final Integer count ) {

        this.folder = folder;
        this.sort = sort;
        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;

    }

    public UpdateVideosEvent( final String folder, final String sort, final Boolean descending, final Integer startIndex, final Integer count, List<VideoDetails> details ) {

        this.folder = folder;
        this.sort = sort;
        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;

        this.details = details;

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

    public List<VideoDetails> getDetails() {
        return details;
    }

    public void setDetails( List<VideoDetails> details ) {

        this.details = details;

    }

}
