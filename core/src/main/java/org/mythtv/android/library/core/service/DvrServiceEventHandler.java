package org.mythtv.android.library.core.service;

import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.service.v028.dvr.ProgramHelper;
import org.mythtv.android.library.core.service.v028.dvr.TitleInfoHelper;
import org.mythtv.android.library.events.DeleteEvent;
import org.mythtv.android.library.events.DeletedEvent;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.ProgramRemovedEvent;
import org.mythtv.android.library.events.dvr.ProgramsUpdatedEvent;
import org.mythtv.android.library.events.dvr.RemoveProgramEvent;
import org.mythtv.android.library.events.dvr.RemoveTitleInfoEvent;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.SearchRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.TitleInfoDetails;
import org.mythtv.android.library.events.dvr.TitleInfoRemovedEvent;
import org.mythtv.android.library.events.dvr.TitleInfosUpdatedEvent;
import org.mythtv.android.library.events.dvr.UpdateRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.UpdateTitleInfosEvent;
import org.mythtv.android.library.persistence.service.DvrPersistenceService;
import org.mythtv.android.library.persistence.service.dvr.DvrPersistenceServiceEventHandler;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.MythTvApi028Context;
import org.mythtv.services.api.v028.beans.Program;
import org.mythtv.services.api.v028.beans.ProgramList;
import org.mythtv.services.api.v028.beans.TitleInfo;
import org.mythtv.services.api.v028.beans.TitleInfoList;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by dmfrey on 11/13/14.
 */
public class DvrServiceEventHandler implements DvrService {

    private static final String TAG = DvrServiceEventHandler.class.getSimpleName();

    private static final String RECORDED_LIST_REQ_ID = "RECORDED_LIST_REQ_ID";
    private static final String TITLE_INFO_LIST_REQ_ID = "TITLE_INFO_LIST_REQ_ID";

    DvrPersistenceService mDvrPersistenceService;

    public DvrServiceEventHandler() {

        mDvrPersistenceService = new DvrPersistenceServiceEventHandler();

    }

    @Override
    public AllProgramsEvent requestAllRecordedPrograms( RequestAllRecordedProgramsEvent event ) {

        return mDvrPersistenceService.requestAllRecordedPrograms( event );
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
