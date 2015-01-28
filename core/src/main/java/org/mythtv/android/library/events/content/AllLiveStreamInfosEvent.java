package org.mythtv.android.library.events.content;

import org.mythtv.android.library.core.domain.content.LiveStreamInfo;
import org.mythtv.android.library.events.ReadEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by dmfrey on 11/12/14.
 */
public class AllLiveStreamInfosEvent extends ReadEvent {

    private final List<LiveStreamDetails> details;

    public AllLiveStreamInfosEvent(final List<LiveStreamDetails> details) {

        this.details = Collections.unmodifiableList( details );
        this.entityFound = details.size() > 0;

    }

    public List<LiveStreamDetails> getDetails() {
        return details;
    }

}
