package org.mythtv.android.library.events.videoDir;

import org.mythtv.android.library.events.ReadEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by dmfrey on 8/3/15.
 */
public class AllVideoDirsEvent extends ReadEvent {

    private final List<VideoDirDetails> details;

    public AllVideoDirsEvent( List<VideoDirDetails> details ) {

        this.details = Collections.unmodifiableList( details );
        this.entityFound = !this.details.isEmpty();

    }

    public List<VideoDirDetails> getDetails() {

        return details;
    }

}
