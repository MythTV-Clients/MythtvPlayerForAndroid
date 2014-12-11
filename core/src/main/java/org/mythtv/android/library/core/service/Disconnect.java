package org.mythtv.android.library.core.service;

import org.mythtv.android.library.events.DeleteEvent;
import org.mythtv.android.library.events.DeletedEvent;

/**
 * Created by dmfrey on 12/11/14.
 */
public interface Disconnect {

    DeletedEvent cleanup( DeleteEvent event );

}
