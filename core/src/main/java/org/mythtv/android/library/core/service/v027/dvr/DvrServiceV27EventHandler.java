package org.mythtv.android.library.core.service.v027.dvr;

import android.content.Context;
import android.util.Log;

import org.mythtv.android.library.R;
import org.mythtv.android.library.core.service.DvrService;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.TitleInfoDetails;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.MythTvApi027Context;
import org.mythtv.services.api.MythTvApiContext;
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
public class DvrServiceV27EventHandler implements DvrService {

    private static final String TAG = DvrServiceV27EventHandler.class.getSimpleName();

    private static final String RECORDED_LIST_REQ_ID = "RECORDED_LIST_REQ_ID";
    private static final String TITLE_INFO_LIST_REQ_ID = "TITLE_INFO_LIST_REQ_ID";

    Context mContext;
    MythTvApi027Context mMythTvApiContext;

    ProgramList mProgramList;
    TitleInfoList mTitleInfoList;

    public DvrServiceV27EventHandler( Context context, MythTvApiContext mythTvApiContext ) {

        mContext = context;
        mMythTvApiContext = (MythTvApi027Context) mythTvApiContext;

    }

    @Override
    public AllProgramsEvent getRecordedPrograms( RequestAllRecordedProgramsEvent event ) {

        List<ProgramDetails> programDetails = new ArrayList<ProgramDetails>();

        try {
            ETagInfo eTagInfo = mMythTvApiContext.getEtag(RECORDED_LIST_REQ_ID, true);
            mProgramList = mMythTvApiContext.getDvrService().getRecordedList(event.getDescending(), event.getStartIndex(), event.getCount(), event.getTitleRegEx(), event.getRecGroup(), event.getStorageGroup(), eTagInfo, RECORDED_LIST_REQ_ID);
        } catch( RetrofitError e ) {
            Log.w( TAG, "HTTP Response:" + e.getResponse().getStatus(), e );
        }

        if( null != mProgramList ) {
            for( Program program : mProgramList.getPrograms() ) {
                programDetails.add(ProgramHelper.toDetails( program ) );
            }
        }

        return new AllProgramsEvent( programDetails );
    }

    @Override
    public AllTitleInfosEvent getTitleInfos( RequestAllTitleInfosEvent event ) {

        List<TitleInfoDetails> titleInfoDetails = new ArrayList<TitleInfoDetails>();

        try {
            ETagInfo eTagInfo = mMythTvApiContext.getEtag( TITLE_INFO_LIST_REQ_ID, true );
            mTitleInfoList = mMythTvApiContext.getDvrService().getTitleInfoList( eTagInfo, RECORDED_LIST_REQ_ID );
        } catch( RetrofitError e ) {
            Log.w( TAG, "HTTP Response:" + e.getResponse().getStatus(), e );
        }

        if( null != mTitleInfoList ) {

            titleInfoDetails.add( new TitleInfoDetails( mContext.getResources().getString( R.string.all_recordings ), null ) );

            for( TitleInfo titleInfo : mTitleInfoList.getTitleInfos() ) {
                titleInfoDetails.add(TitleInfoHelper.toDetails(titleInfo));
            }

        }

        return new AllTitleInfosEvent( titleInfoDetails );
    }

}
