package org.mythtv.android.library.core.service.v028.dvr;

import android.content.Context;

import org.mythtv.android.library.R;
import org.mythtv.android.library.core.service.DvrService;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.TitleInfoDetails;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.MythTvApi028Context;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.v028.beans.Program;
import org.mythtv.services.api.v028.beans.ProgramList;
import org.mythtv.services.api.v028.beans.TitleInfo;
import org.mythtv.services.api.v028.beans.TitleInfoList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/13/14.
 */
public class DvrServiceV28EventHandler implements DvrService {

    private static final String RECORDED_LIST_REQ_ID = "RECORDED_LIST_REQ_ID";
    private static final String TITLE_INFO_LIST_REQ_ID = "TITLE_INFO_LIST_REQ_ID";

    Context mContext;
    MythTvApi028Context mMythTvApiContext;

    public DvrServiceV28EventHandler( Context context, MythTvApiContext mythTvApiContext ) {

        mContext = context;
        mMythTvApiContext = (MythTvApi028Context) mythTvApiContext;

    }

    @Override
    public AllProgramsEvent getRecordedPrograms( RequestAllRecordedProgramsEvent event ) {

        List<ProgramDetails> programDetails = new ArrayList<ProgramDetails>();

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( RECORDED_LIST_REQ_ID, true );
        ProgramList programList = mMythTvApiContext.getDvrService().getRecordedList( event.getDescending(), event.getStartIndex(), event.getCount(), event.getTitleRegEx(), event.getRecGroup(), event.getStorageGroup(), eTagInfo, RECORDED_LIST_REQ_ID );
        for( Program program : programList.getPrograms() ) {
            programDetails.add( ProgramHelper.toDetails(program) );
        }

        return new AllProgramsEvent( programDetails );
    }

    @Override
    public AllTitleInfosEvent getTitleInfos( RequestAllTitleInfosEvent event ) {

        List<TitleInfoDetails> titleInfoDetails = new ArrayList<TitleInfoDetails>();
        titleInfoDetails.add( new TitleInfoDetails( mContext.getResources().getString( R.string.all_recordings ), null ) );

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( TITLE_INFO_LIST_REQ_ID, true );
        TitleInfoList titleInfoList = mMythTvApiContext.getDvrService().getTitleInfoList(eTagInfo, RECORDED_LIST_REQ_ID);
        for( TitleInfo titleInfo : titleInfoList.getTitleInfos() ) {
            titleInfoDetails.add( TitleInfoHelper.toDetails( titleInfo ) );
        }

        return new AllTitleInfosEvent( titleInfoDetails );
    }

}
