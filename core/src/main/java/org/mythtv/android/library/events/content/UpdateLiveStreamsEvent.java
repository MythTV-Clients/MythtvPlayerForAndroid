package org.mythtv.android.library.events.content;

import org.mythtv.android.library.events.UpdateEvent;

import java.util.List;

/**
 * Created by dmfrey on 4/5/15.
 */
public class UpdateLiveStreamsEvent extends UpdateEvent {

    private final String fileName;

    private List<LiveStreamDetails> details;

    public UpdateLiveStreamsEvent() {

        this.fileName = null;

    }

    public UpdateLiveStreamsEvent( String fileName ) {

        this.fileName = fileName;

    }

    public String getFileName() {
        return fileName;
    }

    public List<LiveStreamDetails> getDetails() {

        return details;
    }

    public void setDetails( List<LiveStreamDetails> details ) {

        this.details = details;

    }

}
