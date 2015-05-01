package org.mythtv.android.library.core.service.v027.dvr;

import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.service.DvrService;
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
import org.mythtv.services.api.MythTvApi027Context;
import org.mythtv.services.api.v027.beans.Program;
import org.mythtv.services.api.v027.beans.ProgramList;
import org.mythtv.services.api.v027.beans.TitleInfo;
import org.mythtv.services.api.v027.beans.TitleInfoList;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by dmfrey on 11/13/14.
 */
public class DvrServiceV27ApiEventHandler implements DvrService {

    private static final String TAG = DvrServiceV27ApiEventHandler.class.getSimpleName();

    private static final String RECORDED_LIST_REQ_ID = "RECORDED_LIST_REQ_ID";
    private static final String TITLE_INFO_LIST_REQ_ID = "TITLE_INFO_LIST_REQ_ID";

    MythTvApi027Context mMythTvApiContext;
    DvrPersistenceService mDvrPersistenceService;

    public DvrServiceV27ApiEventHandler() {

        mMythTvApiContext = (MythTvApi027Context) MainApplication.getInstance().getMythTvApiContext();
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

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( RECORDED_LIST_REQ_ID, true );
        try {

            ProgramList programList = mMythTvApiContext.getDvrService().getRecordedList( event.getDescending(), event.getStartIndex(), event.getCount(), event.getTitleRegEx(), event.getRecGroup(), event.getStorageGroup(), eTagInfo, RECORDED_LIST_REQ_ID );
            if( null != programList ) {

                List<ProgramDetails> programDetails = new ArrayList<ProgramDetails>();

                for( Program program : programList.getPrograms() ) {

                    programDetails.add( ProgramHelper.toDetails( program ) );

                }

                event.setDetails( programDetails );
                ProgramsUpdatedEvent updated = mDvrPersistenceService.updateRecordedPrograms( event );
                if( updated.isEntityFound() ) {

                    return new ProgramsUpdatedEvent( updated.getDetails() );
                }

            }

        } catch( RetrofitError e ) {
            //Log.w( TAG, "updateRecordedPrograms : error", e );

            if( e.getKind() == RetrofitError.Kind.NETWORK ) {
                MainApplication.getInstance().disconnect();
            }

        }

        return ProgramsUpdatedEvent.notUpdated();
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

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( TITLE_INFO_LIST_REQ_ID, true );
        try {

            List<TitleInfoDetails> titleInfoDetails = new ArrayList<TitleInfoDetails>();

            TitleInfoList titleInfoList = mMythTvApiContext.getDvrService().getTitleInfoList( eTagInfo, RECORDED_LIST_REQ_ID );
            if( null != titleInfoList ) {

                for( TitleInfo titleInfo : titleInfoList.getTitleInfos() ) {
                    titleInfoDetails.add( TitleInfoHelper.toDetails( titleInfo ) );
                }

                TitleInfosUpdatedEvent updated = mDvrPersistenceService.updateTitleInfos( new UpdateTitleInfosEvent( titleInfoDetails ) );
                if( updated.isEntityFound() ) {

                    AllTitleInfosEvent allTitleInfos = mDvrPersistenceService.requestAllTitleInfos( new RequestAllTitleInfosEvent() );
                    if( allTitleInfos.isEntityFound() ) {

                        return new TitleInfosUpdatedEvent( allTitleInfos.getDetails() );
                    }

                }

            }

        } catch( RetrofitError e ) {
            //Log.w( TAG, "updateTitleInfos : error", e );

            if( e.getKind() == RetrofitError.Kind.NETWORK ) {
                MainApplication.getInstance().disconnect();
            }

        }

        return TitleInfosUpdatedEvent.notUpdated();
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
