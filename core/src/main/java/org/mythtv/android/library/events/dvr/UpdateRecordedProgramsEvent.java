package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.RequestReadEvent;

import java.util.List;

/**
 * Created by dmfrey on 11/12/14.
 */
public class UpdateRecordedProgramsEvent extends RequestReadEvent {

    private final Boolean descending;
    private final Integer startIndex;
    private final Integer count;
    private final String titleRegEx;
    private final String recGroup;
    private final String storageGroup;

    private List<ProgramDetails> details;

    public UpdateRecordedProgramsEvent( final Boolean descending, final Integer startIndex, final Integer count, final String titleRegEx, final String recGroup, final String storageGroup ) {

        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;
        this.titleRegEx = titleRegEx;
        this.recGroup = recGroup;
        this.storageGroup = storageGroup;

    }

    public UpdateRecordedProgramsEvent( final Boolean descending, final Integer startIndex, final Integer count, final String titleRegEx, final String recGroup, final String storageGroup, List<ProgramDetails> details ) {

        this.descending = descending;
        this.startIndex = startIndex;
        this.count = count;
        this.titleRegEx = titleRegEx;
        this.recGroup = recGroup;
        this.storageGroup = storageGroup;

        this.details = details;

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

    public String getTitleRegEx() { return titleRegEx; }

    public String getRecGroup() {
        return recGroup;
    }

    public String getStorageGroup() {
        return storageGroup;
    }

    public List<ProgramDetails> getDetails() {
        return details;
    }

    public void setDetails( List<ProgramDetails> details ) {
        this.details = details;
    }

}
