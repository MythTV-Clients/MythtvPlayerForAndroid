package org.mythtv.android.library.core.service;

import org.mythtv.android.library.events.videoDir.AllVideoDirItemsEvent;
import org.mythtv.android.library.events.videoDir.RequestAllVideoDirItemsEvent;

/**
 * Created by dmfrey on 8/3/15.
 */
public interface VideoDirService {

    AllVideoDirItemsEvent requestAllVideoDirItems( RequestAllVideoDirItemsEvent event );

}
