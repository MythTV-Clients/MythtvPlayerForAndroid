package org.mythtv.android.library.persistence.service;

import org.mythtv.android.library.events.videoDir.AllVideoDirsEvent;
import org.mythtv.android.library.events.videoDir.RequestAllVideoDirsEvent;

/**
 * Created by dmfrey on 8/3/15.
 */
public interface VideoDirPersistenceService {

    AllVideoDirsEvent requestAllVideoDirs( RequestAllVideoDirsEvent event );

}
