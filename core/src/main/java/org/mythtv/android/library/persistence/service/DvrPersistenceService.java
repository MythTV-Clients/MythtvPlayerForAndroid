/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.library.persistence.service;

import org.mythtv.android.library.events.dvr.AllProgramsCountEvent;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.DeleteProgramsEvent;
import org.mythtv.android.library.events.dvr.ProgramDetailsEvent;
import org.mythtv.android.library.events.dvr.ProgramRemovedEvent;
import org.mythtv.android.library.events.dvr.ProgramsDeletedEvent;
import org.mythtv.android.library.events.dvr.ProgramsUpdatedEvent;
import org.mythtv.android.library.events.dvr.RemoveProgramEvent;
import org.mythtv.android.library.events.dvr.RemoveTitleInfoEvent;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsCountEvent;
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

    AllProgramsCountEvent requestAllRecordedProgramsCount( RequestAllRecordedProgramsCountEvent event );

    AllProgramsEvent searchRecordedPrograms( SearchRecordedProgramsEvent event );

    ProgramsUpdatedEvent updateRecordedPrograms( UpdateRecordedProgramsEvent event );

    ProgramsDeletedEvent deleteRecordedPrograms( DeleteProgramsEvent event );

    ProgramDetailsEvent requestProgram( RequestRecordedProgramEvent event );

    ProgramRemovedEvent removeProgram( RemoveProgramEvent event );

    AllProgramsEvent requestAllRecordingGroups( RequestAllRecordedProgramsEvent event );

    AllProgramsEvent requestAllTitles( RequestAllRecordedProgramsEvent event );

    AllTitleInfosEvent requestAllTitleInfos( RequestAllTitleInfosEvent event );

    TitleInfosUpdatedEvent updateTitleInfos( UpdateTitleInfosEvent event );

    TitleInfoRemovedEvent removeTitleInfo( RemoveTitleInfoEvent event );

}
