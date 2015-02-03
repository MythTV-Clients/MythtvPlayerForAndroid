package org.mythtv.android.library.core.service.v028.content;

import android.content.Context;
import android.util.Log;

import org.mythtv.android.library.core.service.ContentService;
import org.mythtv.android.library.events.content.AddLiveStreamEvent;
import org.mythtv.android.library.events.content.AddRecordingLiveStreamEvent;
import org.mythtv.android.library.events.content.AddVideoLiveStreamEvent;
import org.mythtv.android.library.events.content.AllLiveStreamInfosEvent;
import org.mythtv.android.library.events.content.LiveStreamAddedEvent;
import org.mythtv.android.library.events.content.LiveStreamDetails;
import org.mythtv.android.library.events.content.LiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.LiveStreamRemovedEvent;
import org.mythtv.android.library.events.content.RemoveLiveStreamEvent;
import org.mythtv.android.library.events.content.RequestAllLiveStreamInfosEvent;
import org.mythtv.android.library.events.content.RequestLiveStreamDetailsEvent;
import org.mythtv.android.library.persistence.service.ContentPersistenceService;
import org.mythtv.android.library.persistence.service.content.ContentPersistenceServiceEventHandler;
import org.mythtv.services.api.Bool;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.MythTvApi028Context;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.v028.beans.LiveStreamInfo;
import org.mythtv.services.api.v028.beans.LiveStreamInfoList;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by dmfrey on 11/18/14.
 */
public class ContentServiceV28EventHandler implements ContentService {

    private static final String TAG = ContentServiceV28EventHandler.class.getSimpleName();

    private static final String ALL_LIVE_STREAM_REQ_ID = "ALL_LIVE_STREAM_REQ_ID";
    private static final String LIVE_STREAM_REQ_ID = "LIVE_STREAM_REQ_ID";

    MythTvApi028Context mMythTvApiContext;
    ContentPersistenceService mContentPersistenceService;

    LiveStreamInfoList mLiveStreamInfoList;

    public ContentServiceV28EventHandler( Context context, MythTvApiContext mythTvApiContext ) {

        mMythTvApiContext = (MythTvApi028Context) mythTvApiContext;
        mContentPersistenceService = new ContentPersistenceServiceEventHandler( context );

    }

    @Override
    public AllLiveStreamInfosEvent getLiveStreamInfoList( RequestAllLiveStreamInfosEvent event ) {
        Log.v( TAG, "getLiveStreamInfoList : enter" );

        List<LiveStreamDetails> liveStreamDetails = new ArrayList<LiveStreamDetails>();

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( ALL_LIVE_STREAM_REQ_ID, false );
        try {
            mLiveStreamInfoList = mMythTvApiContext.getContentService().getLiveStreamList( event.getFileName(), eTagInfo, ALL_LIVE_STREAM_REQ_ID ) ;
        } catch( RetrofitError e ) {
            Log.e( TAG, "getLiveStreamInfoList : error - " + e.getMessage() );
        }

        if( null != mLiveStreamInfoList ) {
            for( LiveStreamInfo liveStreamInfo : mLiveStreamInfoList.getLiveStreamInfos() ) {
                liveStreamDetails.add( LiveStreamInfoHelper.toDetails( liveStreamInfo ) );
            }

            mContentPersistenceService.refreshLiveStreamInfoList( new AllLiveStreamInfosEvent( liveStreamDetails ) );
        }

        Log.v( TAG, "getLiveStreamInfoList : enter" );
        return new AllLiveStreamInfosEvent( liveStreamDetails );
    }

    @Override
    public LiveStreamDetailsEvent getLiveStream( RequestLiveStreamDetailsEvent event ) {
        Log.v( TAG, "getLiveStream : enter" );

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, true );
        try {
            LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().getLiveStream(event.getKey(), eTagInfo, LIVE_STREAM_REQ_ID);
            if( null != liveStreamInfo ) {

                Log.v( TAG, "getLiveStream : exit" );
                return new LiveStreamDetailsEvent( liveStreamInfo.getId(), LiveStreamInfoHelper.toDetails( liveStreamInfo ) );
            }

        } catch( RetrofitError e ) {
            if( e.getResponse().getStatus() == 304 ) {

                Log.v( TAG, "getLiveStream : exit, not modified" );
                return LiveStreamDetailsEvent.notModified( event.getKey() );
            }

        }

        Log.v( TAG, "getLiveStream : exit, not found" );
        return LiveStreamDetailsEvent.notFound( event.getKey() );
    }

    @Override
    public LiveStreamAddedEvent addLiveStream( AddLiveStreamEvent event ) {
        Log.v( TAG, "addLiveStream : enter" );

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, false );
        LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().addLiveStream( event.getStorageGroup(), event.getFileName(), event.getHostName(), event.getMaxSegments(), event.getWidth(), event.getHeight(), event.getBitrate(), event.getAudioBitrate(), event.getSampleRate(), eTagInfo, LIVE_STREAM_REQ_ID );
        if( null != liveStreamInfo ) {

            LiveStreamAddedEvent added = new LiveStreamAddedEvent( liveStreamInfo.getId(), LiveStreamInfoHelper.toDetails( liveStreamInfo ) );
            mContentPersistenceService.addLiveStream( added );

            Log.v( TAG, "addLiveStream : exit" );
            return added;
        }

        Log.v( TAG, "addLiveStream : exit, not added" );
        return LiveStreamAddedEvent.notAdded();
    }

    @Override
    public LiveStreamAddedEvent addRecordingLiveStream( AddRecordingLiveStreamEvent event ) {
        Log.v( TAG, "addRecordingLiveStream : enter" );

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, false );
        LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().addRecordingLiveStream( event.getRecordedId(), event.getChanId(), event.getStartTime(), event.getMaxSegments(), event.getWidth(), event.getHeight(), event.getBitrate(), event.getAudioBitrate(), event.getSampleRate(), eTagInfo, LIVE_STREAM_REQ_ID );
        if( null != liveStreamInfo ) {

            LiveStreamAddedEvent added = LiveStreamAddedEvent.recordingAdded( event.getRecordedId(), LiveStreamInfoHelper.toDetails( liveStreamInfo ) );
            mContentPersistenceService.addLiveStream( added );

            Log.v( TAG, "addRecordingLiveStream : exit" );
            return added;
        }

        Log.v( TAG, "addRecordingLiveStream : exit, not added" );
        return LiveStreamAddedEvent.notAdded();
    }

    @Override
    public LiveStreamAddedEvent addVideoLiveStream( AddVideoLiveStreamEvent event ) {
        Log.v( TAG, "addVideoLiveStream : enter" );

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, false );
        LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().addVideoLiveStream( event.getId(), event.getMaxSegments(), event.getWidth(), event.getHeight(), event.getBitrate(), event.getAudioBitrate(), event.getSampleRate(), eTagInfo, LIVE_STREAM_REQ_ID );
        if( null != liveStreamInfo ) {

            LiveStreamAddedEvent added = LiveStreamAddedEvent.recordingAdded( event.getId(), LiveStreamInfoHelper.toDetails( liveStreamInfo ) );
            mContentPersistenceService.addLiveStream( added );

            Log.v( TAG, "addVideoLiveStream : exit" );
            return added;
        }

        Log.v( TAG, "addVideoLiveStream : exit, not added" );
        return LiveStreamAddedEvent.notAdded();
    }

    @Override
    public LiveStreamRemovedEvent removeLiveStream( RemoveLiveStreamEvent event ) {
        Log.v( TAG, "removeLiveStream : enter" );

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, false );
        Bool deleted = mMythTvApiContext.getContentService().removeLiveStream( event.getKey(), eTagInfo, LIVE_STREAM_REQ_ID );
        if( deleted.getValue() ) {

            LiveStreamRemovedEvent removed = new LiveStreamRemovedEvent( event.getKey() );
            mContentPersistenceService.removeLiveStream( removed );

            Log.v( TAG, "removeLiveStream : exit" );
            return removed;
        }

        Log.v( TAG, "removeLiveStream : exit, delete failed" );
        return LiveStreamRemovedEvent.deletionFailed( event.getKey() );
    }

}
