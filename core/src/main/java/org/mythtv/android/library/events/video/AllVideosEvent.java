package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.ReadEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by dmfrey on 11/12/14.
 */
public class AllVideosEvent extends ReadEvent {

    private final List<VideoDetails> details;

    public AllVideosEvent( final List<VideoDetails> details ) {

        this.details = Collections.unmodifiableList( details );

    }

    public List<VideoDetails> getDetails() {
        return details;
    }

}
