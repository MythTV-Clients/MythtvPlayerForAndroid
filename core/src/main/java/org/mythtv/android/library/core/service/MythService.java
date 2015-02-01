package org.mythtv.android.library.core.service;

import org.mythtv.android.library.events.myth.HostNameDetailsEvent;
import org.mythtv.android.library.events.myth.RequestHostNameEvent;

/**
 * Created by dmfrey on 1/31/15.
 */
public interface MythService {

    HostNameDetailsEvent getHostName( RequestHostNameEvent event );

}
