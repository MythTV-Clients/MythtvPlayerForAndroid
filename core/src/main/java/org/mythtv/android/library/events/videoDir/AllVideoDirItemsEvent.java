package org.mythtv.android.library.events.videoDir;

import org.mythtv.android.library.events.ReadEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by dmfrey on 8/3/15.
 */
public class AllVideoDirItemsEvent extends ReadEvent {

    private final List<VideoDirItemDetails> details;

    public AllVideoDirItemsEvent( List<VideoDirItemDetails> details ) {

        this.details = Collections.unmodifiableList( details );
        this.entityFound = !this.details.isEmpty();

    }

    public List<VideoDirItemDetails> getDetails() {

        return details;
    }

}
