package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.UpdatedEvent;

import java.util.Collections;
import java.util.List;

/**
 * Created by dmfrey on 4/16/15.
 */
public class VideosUpdatedEvent extends UpdatedEvent {

    private final List<VideoDetails> details;

    public VideosUpdatedEvent( final List<VideoDetails> details ) {

        this.details = Collections.unmodifiableList( details );

        if( null != details ) {
            entityFound = !details.isEmpty();
        } else {
            entityFound = false;
        }

    }

    public List<VideoDetails> getDetails() {

        return details;
    }

    public static VideosUpdatedEvent notUpdated() {

        return new VideosUpdatedEvent( null );
    }

}
