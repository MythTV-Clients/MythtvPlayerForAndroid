package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.UpdatedEvent;

import java.util.List;

/**
 * Created by dmfrey on 3/10/15.
 */
public class TitleInfosUpdatedEvent extends UpdatedEvent {

    private final List<TitleInfoDetails> details;

    public TitleInfosUpdatedEvent( final List<TitleInfoDetails> details ) {

        this.details = details;

        if( null != details ) {
            entityFound = !details.isEmpty();
        } else {
            entityFound = false;
        }

    }

    public List<TitleInfoDetails> getDetails() {

        return details;
    }

    public static TitleInfosUpdatedEvent notUpdated() {

        return new TitleInfosUpdatedEvent( null );
    }

}
