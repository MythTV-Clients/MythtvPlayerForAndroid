package org.mythtv.android.library.events.video;

import org.mythtv.android.library.events.DeleteEvent;

/**
 * Created by dmfrey on 4/16/15.
 */
public class DeleteVideoEvent extends DeleteEvent {

    private final Integer id;

    public DeleteVideoEvent( final Integer id ) {

        this.id = id;

    }

    public Integer getId() { return id; }

}
