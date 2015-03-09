package org.mythtv.android.library.core.service;

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
public interface DvrService extends Disconnect {

    AllProgramsEvent getRecordedPrograms( RequestAllRecordedProgramsEvent event );

    ProgramRemovedEvent removeProgram( RemoveProgramEvent event );

    AllTitleInfosEvent getTitleInfos( RequestAllTitleInfosEvent event );

    TitleInfoRemovedEvent removeTitleInfo( RemoveTitleInfoEvent event );

}
