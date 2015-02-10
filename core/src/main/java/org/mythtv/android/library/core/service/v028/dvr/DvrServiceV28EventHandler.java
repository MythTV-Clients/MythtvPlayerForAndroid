package org.mythtv.android.library.core.service.v028.dvr;

import android.content.Context;
import android.util.Log;

import org.mythtv.android.library.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.service.DvrService;
import org.mythtv.android.library.events.DeleteEvent;
import org.mythtv.android.library.events.DeletedEvent;
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

import retrofit.RetrofitError;

/**
 * Created by dmfrey on 11/13/14.
 */
public class DvrServiceV28EventHandler implements DvrService {

    private static final String TAG = DvrServiceV28EventHandler.class.getSimpleName();

    private static final String RECORDED_LIST_REQ_ID = "RECORDED_LIST_REQ_ID";
    private static final String TITLE_INFO_LIST_REQ_ID = "TITLE_INFO_LIST_REQ_ID";

    Context mContext;
    MythTvApi028Context mMythTvApiContext;

    ProgramList mProgramList;
    TitleInfoList mTitleInfoList;

    public DvrServiceV28EventHandler( Context context, MythTvApiContext mythTvApiContext ) {

        mContext = context;
        mMythTvApiContext = (MythTvApi028Context) mythTvApiContext;

    }

    @Override
    public AllProgramsEvent getRecordedPrograms( RequestAllRecordedProgramsEvent event ) {

        List<ProgramDetails> programDetails = new ArrayList<ProgramDetails>();

        try {
            ETagInfo eTagInfo = mMythTvApiContext.getEtag( RECORDED_LIST_REQ_ID, true );
            mProgramList = mMythTvApiContext.getDvrService().getRecordedList( event.getDescending(), event.getStartIndex(), event.getCount(), event.getTitleRegEx(), event.getRecGroup(), event.getStorageGroup(), eTagInfo, RECORDED_LIST_REQ_ID );
        } catch( RetrofitError e ) {
            Log.w( TAG, "HTTP Response:" + e.getResponse().getStatus(), e );

            if( e.getKind() == RetrofitError.Kind.NETWORK ) {
                MainApplication.getInstance().disconnect();
            }

        }

        if( null != mProgramList ) {
            Log.v( TAG, "getRecordedList : programs returned, size=" + mProgramList.getCount() );

            for( Program program : mProgramList.getPrograms() ) {
                Log.v( TAG, "getRecordedList : program iteration" );
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

            if( e.getKind() == RetrofitError.Kind.NETWORK ) {
                MainApplication.getInstance().disconnect();
            }

        }

        if( null != mTitleInfoList ) {

            titleInfoDetails.add( new TitleInfoDetails( mContext.getResources().getString( R.string.all_recordings ), null ) );

            for( TitleInfo titleInfo : mTitleInfoList.getTitleInfos() ) {
                titleInfoDetails.add( TitleInfoHelper.toDetails( titleInfo ) );
            }

        }

        return new AllTitleInfosEvent( titleInfoDetails );
    }

    @Override
    public DeletedEvent cleanup( DeleteEvent event ) {

        mProgramList = null;
        mTitleInfoList = null;

        return new DeletedEvent();
    }

}
