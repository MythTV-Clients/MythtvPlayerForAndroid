package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.DeletedEvent;

/**
 * Created by dmfrey on 4/16/15.
 */
public class VideoDeletedEvent extends DeletedEvent {

    private final Integer id;
    private boolean deletionCompleted;

    public VideoDeletedEvent( final Integer id ) {

        this.id = id;

        deletionCompleted = true;
    }

    public Integer getId() {
        return id;
    }

    public boolean isDeletionCompleted() {
        return deletionCompleted;
    }

    public static VideoDeletedEvent deletionFailed( final Integer id ) {

        VideoDeletedEvent event = new VideoDeletedEvent( id );
        event.deletionCompleted = false;

        return event;
    }

}
