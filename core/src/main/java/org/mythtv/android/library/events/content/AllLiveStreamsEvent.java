package org.mythtv.android.library.events.content;

import org.mythtv.android.library.events.ReadEvent;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by dmfrey on 11/12/14.
 */
public class AllLiveStreamsEvent extends ReadEvent {

    private final List<LiveStreamDetails> details;
    private Map<Integer, Long> deleted;

    public AllLiveStreamsEvent(final List<LiveStreamDetails> details) {

        this.details = Collections.unmodifiableList( details );
        this.entityFound = details.size() > 0;

    }

    public List<LiveStreamDetails> getDetails() {
        return details;
    }

    public Map<Integer, Long> getDeleted() {
        return deleted;
    }

    public void setDeleted( Map<Integer, Long> deleted ) {

        this.deleted = deleted;

    }

}
