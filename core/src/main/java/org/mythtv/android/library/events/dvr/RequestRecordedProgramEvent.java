package org.mythtv.android.library.events.dvr;

import org.joda.time.DateTime;
import org.mythtv.android.library.events.RequestReadEvent;

/**
 * Created by dmfrey on 4/5/15.
 */
public class RequestRecordedProgramEvent extends RequestReadEvent {

    private Integer recordedId;
    private Integer chanId;
    private DateTime startTime;
    private String filename;

    public RequestRecordedProgramEvent() { }

    public Integer getRecordedId() {

        return recordedId;
    }

    public void setRecordedId( Integer recordedId ) {

        this.recordedId = recordedId;

    }

    public Integer getChanId() {

        return chanId;
    }

    public void setChanId( Integer chanId ) {

        this.chanId = chanId;

    }

    public DateTime getStartTime() {

        return startTime;
    }

    public void setStartTime( DateTime startTime ) {

        this.startTime = startTime;

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename( String filename ) {

        this.filename = filename;

    }

}
