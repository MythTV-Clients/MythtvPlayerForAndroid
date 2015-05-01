package org.mythtv.android.library.core.service;

import org.mythtv.android.library.events.dvr.AllProgramsCountEvent;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.ProgramRemovedEvent;
import org.mythtv.android.library.events.dvr.ProgramsUpdatedEvent;
import org.mythtv.android.library.events.dvr.RemoveProgramEvent;
import org.mythtv.android.library.events.dvr.RemoveTitleInfoEvent;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsCountEvent;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.SearchRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.TitleInfoRemovedEvent;
import org.mythtv.android.library.events.dvr.TitleInfosUpdatedEvent;
import org.mythtv.android.library.events.dvr.UpdateRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.UpdateTitleInfosEvent;

/**
 * Created by dmfrey on 11/13/14.
 */
public interface DvrService extends Disconnect {

    AllProgramsEvent requestAllRecordedPrograms( RequestAllRecordedProgramsEvent event );

    AllProgramsCountEvent requestAllRecordedProgramsCount( RequestAllRecordedProgramsCountEvent event );

    AllProgramsEvent searchRecordedPrograms( SearchRecordedProgramsEvent event );

    ProgramsUpdatedEvent updateRecordedPrograms( UpdateRecordedProgramsEvent event );

    ProgramRemovedEvent removeProgram( RemoveProgramEvent event );

    AllTitleInfosEvent requestAllTitleInfos( RequestAllTitleInfosEvent event );

    TitleInfosUpdatedEvent updateTitleInfos( UpdateTitleInfosEvent event );

    TitleInfoRemovedEvent removeTitleInfo( RemoveTitleInfoEvent event );

}
