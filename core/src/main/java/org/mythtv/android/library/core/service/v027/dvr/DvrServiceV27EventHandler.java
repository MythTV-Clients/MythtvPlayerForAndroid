package org.mythtv.android.library.core.service.v027.dvr;

import org.mythtv.android.library.core.service.DvrService;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.MythTvApi027Context;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.v027.beans.Program;
import org.mythtv.services.api.v027.beans.ProgramList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/13/14.
 */
public class DvrServiceV27EventHandler implements DvrService {

    private static final String RECORDED_LIST_REQ_ID = "RECORDED_LIST_REQ_ID";

    MythTvApi027Context mMythTvApiContext;

    public DvrServiceV27EventHandler( MythTvApiContext mythTvApiContext ) {

        mMythTvApiContext = (MythTvApi027Context) mythTvApiContext;

    }

    @Override
    public AllProgramsEvent getRecordedPrograms( RequestAllRecordedProgramsEvent event ) {

        List<ProgramDetails> programDetails = new ArrayList<ProgramDetails>();

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( RECORDED_LIST_REQ_ID, true );
        ProgramList programList = mMythTvApiContext.getDvrService().getRecordedList( event.getDescending(), event.getStartIndex(), event.getCount(), event.getTitleRegEx(), event.getRecGroup(), event.getStorageGroup(), eTagInfo, RECORDED_LIST_REQ_ID );
        for( Program program : programList.getPrograms() ) {
            programDetails.add( ProgramHelper.toDetails( program ) );
        }

        return new AllProgramsEvent( programDetails );
    }

}
