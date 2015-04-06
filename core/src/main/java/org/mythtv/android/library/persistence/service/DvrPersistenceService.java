package org.mythtv.android.library.persistence.service;

import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.DeleteProgramsEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.ProgramDetailsEvent;
import org.mythtv.android.library.events.dvr.ProgramRemovedEvent;
import org.mythtv.android.library.events.dvr.ProgramsDeletedEvent;
import org.mythtv.android.library.events.dvr.ProgramsUpdatedEvent;
import org.mythtv.android.library.events.dvr.RemoveProgramEvent;
import org.mythtv.android.library.events.dvr.RemoveTitleInfoEvent;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.RequestRecordedProgramEvent;
import org.mythtv.android.library.events.dvr.SearchRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.TitleInfoRemovedEvent;
import org.mythtv.android.library.events.dvr.TitleInfosUpdatedEvent;
import org.mythtv.android.library.events.dvr.UpdateRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.UpdateTitleInfosEvent;

/**
 * Created by dmfrey on 11/13/14.
 */
public interface DvrPersistenceService {

    AllProgramsEvent requestAllRecordedPrograms( RequestAllRecordedProgramsEvent event );

    AllProgramsEvent searchRecordedPrograms( SearchRecordedProgramsEvent event );

    ProgramsUpdatedEvent updateRecordedPrograms( UpdateRecordedProgramsEvent event );

    ProgramsDeletedEvent deleteRecordedPrograms( DeleteProgramsEvent event );

    ProgramDetailsEvent requestProgram( RequestRecordedProgramEvent event );

    ProgramRemovedEvent removeProgram( RemoveProgramEvent event );

    AllTitleInfosEvent requestAllTitleInfos( RequestAllTitleInfosEvent event );

    TitleInfosUpdatedEvent updateTitleInfos( UpdateTitleInfosEvent event );

    TitleInfoRemovedEvent removeTitleInfo( RemoveTitleInfoEvent event );

}
