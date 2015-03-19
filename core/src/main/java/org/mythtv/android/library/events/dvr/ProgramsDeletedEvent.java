package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.DeletedEvent;

import java.util.List;

/**
 * Created by dmfrey on 3/18/15.
 */
public class ProgramsDeletedEvent extends DeletedEvent {

    private final List<ProgramDetails> details;
    private boolean deletionCompleted;

    public ProgramsDeletedEvent( final List<ProgramDetails> details ) {

        this.details = details;
        this.deletionCompleted = true;

    }

    public List<ProgramDetails> getDetails() {

        return details;
    }

    public boolean isDeletionCompleted() {
        return deletionCompleted;
    }

    public static ProgramsDeletedEvent deletionFailed() {

        ProgramsDeletedEvent ev = new ProgramsDeletedEvent( null );
        ev.deletionCompleted = false;

        return ev;
    }

}
