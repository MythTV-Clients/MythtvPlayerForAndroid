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

package org.mythtv.android.library.core.service;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.events.DeleteEvent;
import org.mythtv.android.library.events.DeletedEvent;
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
import org.mythtv.android.library.persistence.service.DvrPersistenceService;
import org.mythtv.android.library.persistence.service.dvr.DvrPersistenceServiceEventHandler;

/**
 * Created by dmfrey on 11/13/14.
 */
public class DvrServiceEventHandler implements DvrService {

    DvrPersistenceService mDvrPersistenceService;

    public DvrServiceEventHandler() {

        mDvrPersistenceService = new DvrPersistenceServiceEventHandler();

    }

    @Override
    public AllProgramsEvent requestAllRecordedPrograms( RequestAllRecordedProgramsEvent event ) {

        return mDvrPersistenceService.requestAllRecordedPrograms( event );
    }

    @Override
    public AllProgramsCountEvent requestAllRecordedProgramsCount( RequestAllRecordedProgramsCountEvent event ) {

        return mDvrPersistenceService.requestAllRecordedProgramsCount( event );
    }

    @Override
    public AllProgramsEvent searchRecordedPrograms( SearchRecordedProgramsEvent event ) {

        return mDvrPersistenceService.searchRecordedPrograms( event );
    }

    @Override
    public ProgramsUpdatedEvent updateRecordedPrograms( UpdateRecordedProgramsEvent event ) {

        return MainApplication.getInstance().getDvrApiService().updateRecordedPrograms( event );
    }

    @Override
    public ProgramRemovedEvent removeProgram( RemoveProgramEvent event ) {

        return mDvrPersistenceService.removeProgram( event );
    }

    @Override
    public AllTitleInfosEvent requestAllTitleInfos( RequestAllTitleInfosEvent event ) {

        return mDvrPersistenceService.requestAllTitleInfos( event );
    }

    @Override
    public TitleInfosUpdatedEvent updateTitleInfos( UpdateTitleInfosEvent event ) {

        return MainApplication.getInstance().getDvrApiService().updateTitleInfos( event );
    }

    @Override
    public TitleInfoRemovedEvent removeTitleInfo( RemoveTitleInfoEvent event ) {

        return mDvrPersistenceService.removeTitleInfo( event );
    }

    @Override
    public DeletedEvent cleanup( DeleteEvent event ) {

        return new DeletedEvent();
    }

}
