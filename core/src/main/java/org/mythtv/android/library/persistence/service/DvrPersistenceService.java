package org.mythtv.android.library.persistence.service;

import org.mythtv.android.library.core.service.Disconnect;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.ProgramRemovedEvent;
import org.mythtv.android.library.events.dvr.RemoveProgramEvent;
import org.mythtv.android.library.events.dvr.RemoveTitleInfoEvent;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.TitleInfoRemovedEvent;

/**
 * Created by dmfrey on 11/13/14.
 */
public interface DvrPersistenceService extends Disconnect {

    AllProgramsEvent refreshRecordedPrograms( AllProgramsEvent event );

    ProgramRemovedEvent removeProgram( RemoveProgramEvent event );

    AllTitleInfosEvent refreshTitleInfos( AllTitleInfosEvent event );

    TitleInfoRemovedEvent removeTitleInfo( RemoveTitleInfoEvent event );

}
