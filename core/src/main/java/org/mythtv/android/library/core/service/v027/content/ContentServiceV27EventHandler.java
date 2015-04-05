package org.mythtv.android.library.core.service.v027.content;

import android.content.Context;
import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.service.ContentService;
import org.mythtv.android.library.events.content.AddLiveStreamEvent;
import org.mythtv.android.library.events.content.AddRecordingLiveStreamEvent;
import org.mythtv.android.library.events.content.AddVideoLiveStreamEvent;
import org.mythtv.android.library.events.content.AllLiveStreamsEvent;
import org.mythtv.android.library.events.content.LiveStreamAddedEvent;
import org.mythtv.android.library.events.content.LiveStreamDetails;
import org.mythtv.android.library.events.content.LiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.LiveStreamRemovedEvent;
import org.mythtv.android.library.events.content.LiveStreamsUpdatedEvent;
import org.mythtv.android.library.events.content.RemoveLiveStreamEvent;
import org.mythtv.android.library.events.content.RequestAllLiveStreamsEvent;
import org.mythtv.android.library.events.content.RequestLiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.UpdateLiveStreamsEvent;
import org.mythtv.android.library.persistence.service.ContentPersistenceService;
import org.mythtv.android.library.persistence.service.content.ContentPersistenceServiceEventHandler;
import org.mythtv.services.api.Bool;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.MythTvApi027Context;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.v027.beans.LiveStreamInfo;
import org.mythtv.services.api.v027.beans.LiveStreamInfoList;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;

/**
 * Created by dmfrey on 11/18/14.
 */
public class ContentServiceV27EventHandler implements ContentService {

    private static final String TAG = ContentServiceV27EventHandler.class.getSimpleName();

    private static final String ALL_LIVE_STREAM_REQ_ID = "ALL_LIVE_STREAM_REQ_ID";
    private static final String LIVE_STREAM_REQ_ID = "LIVE_STREAM_REQ_ID";

    MythTvApi027Context mMythTvApiContext;
    ContentPersistenceService mContentPersistenceService;

    LiveStreamInfoList mLiveStreamInfoList;

    public ContentServiceV27EventHandler( Context context, MythTvApiContext mythTvApiContext ) {

        mMythTvApiContext = (MythTvApi027Context) mythTvApiContext;
        mContentPersistenceService = new ContentPersistenceServiceEventHandler( context );

    }

    @Override
    public AllLiveStreamsEvent requestAllLiveStreams( RequestAllLiveStreamsEvent event ) {

        return  mContentPersistenceService.requestAllLiveStreams( event );
    }

    @Override
    public LiveStreamsUpdatedEvent updateLiveStreams( UpdateLiveStreamsEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( ALL_LIVE_STREAM_REQ_ID, false );
        try {

            mLiveStreamInfoList = mMythTvApiContext.getContentService().getLiveStreamList( event.getFileName(), eTagInfo, ALL_LIVE_STREAM_REQ_ID ) ;

            List<LiveStreamDetails> liveStreamDetails = new ArrayList<>();

            for( LiveStreamInfo liveStreamInfo : mLiveStreamInfoList.getLiveStreamInfos() ) {

                liveStreamDetails.add( LiveStreamInfoHelper.toDetails( liveStreamInfo ) );

            }

            event.setDetails( liveStreamDetails );

            LiveStreamsUpdatedEvent updated = mContentPersistenceService.updateLiveStreams( event );
            if( updated.isEntityFound() ) {

                return new LiveStreamsUpdatedEvent( updated.getDetails() );
            }

        } catch( RetrofitError e ) {
            Log.w( TAG, "getLiveStreamInfoList : error", e );

            if( e.getKind() == RetrofitError.Kind.NETWORK ) {

                MainApplication.getInstance().disconnect();

            }

        }

        return LiveStreamsUpdatedEvent.notUpdated();
    }

    @Override
    public LiveStreamDetailsEvent getLiveStream( RequestLiveStreamDetailsEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, true );
        try {
            LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().getLiveStream(event.getKey(), eTagInfo, LIVE_STREAM_REQ_ID);
            if( null != liveStreamInfo ) {

                LiveStreamDetailsEvent details = new LiveStreamDetailsEvent( liveStreamInfo.getId(), LiveStreamInfoHelper.toDetails( liveStreamInfo ) );
                mContentPersistenceService.updateLiveStream( details );

                return details;
            }

        } catch( RetrofitError e ) {
            Log.w( TAG, "getLiveStream : error", e );

            if( e.getResponse().getStatus() == 304 ) {

                return LiveStreamDetailsEvent.notModified( event.getKey() );
            } else if( e.getKind() == RetrofitError.Kind.NETWORK ) {
                MainApplication.getInstance().disconnect();
            }

        }

        return LiveStreamDetailsEvent.notFound( event.getKey() );
    }

    @Override
    public LiveStreamAddedEvent addLiveStream( AddLiveStreamEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, false );
        try {

            LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().addLiveStream( event.getStorageGroup(), event.getFileName(), event.getHostName(), event.getMaxSegments(), event.getWidth(), event.getHeight(), event.getBitrate(), event.getAudioBitrate(), event.getSampleRate(), eTagInfo, LIVE_STREAM_REQ_ID );
            if( null != liveStreamInfo ) {

                LiveStreamAddedEvent added = new LiveStreamAddedEvent( liveStreamInfo.getId(), LiveStreamInfoHelper.toDetails( liveStreamInfo ) );
                mContentPersistenceService.addLiveStream( added );

                return added;
            }

        } catch( RetrofitError e ) {
            Log.w( TAG, "addLiveStream : error", e );
        }

        return LiveStreamAddedEvent.notAdded();
    }

    @Override
    public LiveStreamAddedEvent addRecordingLiveStream( AddRecordingLiveStreamEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, false );
        try {

            LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().addRecordingLiveStream( event.getChanId(), event.getStartTime(), event.getMaxSegments(), event.getWidth(), event.getHeight(), event.getBitrate(), event.getAudioBitrate(), event.getSampleRate(), eTagInfo, LIVE_STREAM_REQ_ID );
            if( null != liveStreamInfo ) {

                LiveStreamAddedEvent added = LiveStreamAddedEvent.recordingAdded(  event.getChanId(), event.getStartTime(), LiveStreamInfoHelper.toDetails( liveStreamInfo ) );
                mContentPersistenceService.addLiveStream( added );

                return added;
            }

        } catch( RetrofitError e ) {
            Log.w( TAG, "addRecordingLiveStream : error", e );
        }

        return LiveStreamAddedEvent.notAdded();
    }

    @Override
    public LiveStreamAddedEvent addVideoLiveStream( AddVideoLiveStreamEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( LIVE_STREAM_REQ_ID, false );
        try {

            LiveStreamInfo liveStreamInfo = mMythTvApiContext.getContentService().addVideoLiveStream( event.getId(), event.getMaxSegments(), event.getWidth(), event.getHeight(), event.getBitrate(), event.getAudioBitrate(), event.getSampleRate(), eTagInfo, LIVE_STREAM_REQ_ID );
            if( null != liveStreamInfo ) {

                LiveStreamAddedEvent added = LiveStreamAddedEvent.recordingAdded( event.getId(), LiveStreamInfoHelper.toDetails( liveStreamInfo ) );
                mContentPersistenceService.addLiveStream( added );

                return added;
            }

        } catch( RetrofitError e ) {
            Log.w( TAG, "addVideoLiveStream : error", e );
        }

        return LiveStreamAddedEvent.notAdded();
    }

    @Override
    public LiveStreamRemovedEvent removeLiveStream( RemoveLiveStreamEvent event ) {

        ETagInfo eTagInfo = mMythTvApiContext.getEtag( String.valueOf( event.getKey() ), true );
        try {

            Bool deleted = mMythTvApiContext.getContentService().removeLiveStream( event.getKey(), eTagInfo, String.valueOf( event.getKey() ) );
            if( deleted.getValue() ) {

                LiveStreamRemovedEvent removed = new LiveStreamRemovedEvent( event.getKey() );
                mContentPersistenceService.removeLiveStream( removed );

                return removed;
            }

        } catch( RetrofitError e ) {
            Log.w( TAG, "removeLiveStream : error", e );

            LiveStreamRemovedEvent removed = new LiveStreamRemovedEvent( event.getKey() );
            mContentPersistenceService.removeLiveStream( removed );

            return removed;
        }

        return LiveStreamRemovedEvent.deletionFailed( event.getKey() );
    }

}
