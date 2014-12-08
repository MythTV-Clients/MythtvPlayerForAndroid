package org.mythtv.android.library.core.service;

import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;

/**
 * Created by dmfrey on 11/13/14.
 */
public interface DvrService {

    AllProgramsEvent getRecordedPrograms( RequestAllRecordedProgramsEvent event );

    AllTitleInfosEvent getTitleInfos( RequestAllTitleInfosEvent event );

}
