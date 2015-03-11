package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.UpdateEvent;

import java.util.List;

/**
 * Created by dmfrey on 3/10/15.
 */
public class UpdateTitleInfosEvent extends UpdateEvent {

    private List<TitleInfoDetails> details;

    public UpdateTitleInfosEvent() { }

    public UpdateTitleInfosEvent( List<TitleInfoDetails> details ) {

        this.details = details;

    }

    public List<TitleInfoDetails> getDetails() {

        return details;
    }

}
