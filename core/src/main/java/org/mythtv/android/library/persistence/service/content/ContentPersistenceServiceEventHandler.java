package org.mythtv.android.library.persistence.service.content;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.mythtv.android.library.events.content.AllLiveStreamInfosEvent;
import org.mythtv.android.library.events.content.LiveStreamAddedEvent;
import org.mythtv.android.library.events.content.LiveStreamDetails;
import org.mythtv.android.library.events.content.LiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.LiveStreamRemovedEvent;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.library.persistence.domain.content.LiveStreamInfo;
import org.mythtv.android.library.persistence.repository.MythtvProvider;
import org.mythtv.android.library.persistence.service.ContentPersistenceService;

import java.util.ArrayList;

/**
 * Created by dmfrey on 1/25/15.
 */
public class ContentPersistenceServiceEventHandler implements ContentPersistenceService {

    private static final String TAG = ContentPersistenceServiceEventHandler.class.getSimpleName();

    Context mContext;

    public ContentPersistenceServiceEventHandler(Context context) {

        mContext = context;

    }

    @Override
    public AllLiveStreamInfosEvent refreshLiveStreamInfoList( AllLiveStreamInfosEvent event ) {
        Log.v( TAG, "refreshLiveStreamInfoList : enter" );

        if( null != event.getDetails() && !event.getDetails().isEmpty() ) {

            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

            String[] projection = new String[]{ LiveStreamConstants._ID };
            String selection = LiveStreamConstants.FIELD_LIVE_STREAM_ID + " = ?";
            String[] selectionArgs = null;

            ContentValues values;

            for( LiveStreamDetails details : event.getDetails() ) {
                LiveStreamInfo liveStream = LiveStreamInfoHelper.fromDetails( details );

                selectionArgs = new String[] { String.valueOf( liveStream.getId() ) };

                values = new ContentValues();
                values.put( LiveStreamConstants.FIELD_LIVE_STREAM_ID, liveStream.getId() );
                values.put( LiveStreamConstants.FIELD_WIDTH, liveStream.getWidth() );
                values.put( LiveStreamConstants.FIELD_HEIGHT, liveStream.getHeight() );
                values.put( LiveStreamConstants.FIELD_BITRATE, liveStream.getBitrate() );
                values.put( LiveStreamConstants.FIELD_AUDIO_BITRATE, liveStream.getAudioBitrate() );
                values.put( LiveStreamConstants.FIELD_SEGMENT_SIZE, liveStream.getSegmentSize() );
                values.put( LiveStreamConstants.FIELD_MAX_SEGMENTS, liveStream.getMaxSegments() );
                values.put( LiveStreamConstants.FIELD_START_SEGMENT, liveStream.getStartSegment() );
                values.put( LiveStreamConstants.FIELD_CURRENT_SEGMENT, liveStream.getCurrentSegment() );
                values.put( LiveStreamConstants.FIELD_SEGMENT_COUNT, liveStream.getSegmentCount() );
                values.put( LiveStreamConstants.FIELD_PERCENT_COMPLETE, liveStream.getPercentComplete() );
                values.put( LiveStreamConstants.FIELD_RELATIVE_URL, liveStream.getRelativeURL() );
                values.put( LiveStreamConstants.FIELD_STATUS_STR, liveStream.getStatusStr() );
                values.put( LiveStreamConstants.FIELD_STATUS_INT, liveStream.getStatusInt() );
                values.put( LiveStreamConstants.FIELD_STATUS_MESSAGE, liveStream.getStatusMessage() );
                values.put( LiveStreamConstants.FIELD_SOURCE_FILE, liveStream.getSourceFile() );
                values.put( LiveStreamConstants.FIELD_SOURCE_HOST, liveStream.getSourceHost() );
                values.put( LiveStreamConstants.FIELD_SOURCE_WIDTH, liveStream.getSourceWidth() );
                values.put( LiveStreamConstants.FIELD_SOURCE_HEIGHT, liveStream.getSourceHeight() );
                values.put( LiveStreamConstants.FIELD_AUDIO_ONLY_BITRATE, liveStream.getAudioOnlyBitrate() );
                values.put( LiveStreamConstants.FIELD_CREATED_DATE, liveStream.getCreated().getMillis() );
                values.put( LiveStreamConstants.FIELD_LAST_MODIFIED_DATE, liveStream.getLastModified().getMillis() );

                Cursor cursor = mContext.getContentResolver().query( LiveStreamConstants.CONTENT_URI, projection, selection, selectionArgs, null );
                if( cursor.moveToFirst() ) {

                    Long id = cursor.getLong( cursor.getColumnIndexOrThrow( LiveStreamConstants._ID ) );
                    ops.add(
                            ContentProviderOperation
                                    .newUpdate( ContentUris.withAppendedId( LiveStreamConstants.CONTENT_URI, id ) )
                                    .withValues( values )
                                    .build()
                    );

                } else {

                    ops.add(
                            ContentProviderOperation
                                    .newInsert(LiveStreamConstants.CONTENT_URI)
                                    .withValues(values)
                                    .build()
                    );

                }
                cursor.close();

            }

            try {

                mContext.getContentResolver().applyBatch( MythtvProvider.AUTHORITY, ops );

            } catch( Exception e ) {

                Log.e( TAG, "refreshLiveStreamInfoList : error processing live streams", e );

            }

        }

        Log.v( TAG, "refreshLiveStreamInfoList : exit" );
        return event;
    }

    @Override
    public LiveStreamDetailsEvent updateLiveStream( LiveStreamDetailsEvent event ) {
        Log.v( TAG, "updateLiveStream : enter" );

        LiveStreamInfo liveStream = LiveStreamInfoHelper.fromDetails( event.getDetails() );

        String[] projection = new String[]{ LiveStreamConstants._ID };
        String selection = LiveStreamConstants.FIELD_LIVE_STREAM_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf( liveStream.getId() ) };

        ContentValues values = new ContentValues();
        values.put( LiveStreamConstants.FIELD_LIVE_STREAM_ID, liveStream.getId() );
        values.put( LiveStreamConstants.FIELD_WIDTH, liveStream.getWidth() );
        values.put( LiveStreamConstants.FIELD_HEIGHT, liveStream.getHeight() );
        values.put( LiveStreamConstants.FIELD_BITRATE, liveStream.getBitrate() );
        values.put( LiveStreamConstants.FIELD_AUDIO_BITRATE, liveStream.getAudioBitrate() );
        values.put( LiveStreamConstants.FIELD_SEGMENT_SIZE, liveStream.getSegmentSize() );
        values.put( LiveStreamConstants.FIELD_MAX_SEGMENTS, liveStream.getMaxSegments() );
        values.put( LiveStreamConstants.FIELD_START_SEGMENT, liveStream.getStartSegment() );
        values.put( LiveStreamConstants.FIELD_CURRENT_SEGMENT, liveStream.getCurrentSegment() );
        values.put( LiveStreamConstants.FIELD_SEGMENT_COUNT, liveStream.getSegmentCount() );
        values.put( LiveStreamConstants.FIELD_PERCENT_COMPLETE, liveStream.getPercentComplete() );
        values.put( LiveStreamConstants.FIELD_RELATIVE_URL, liveStream.getRelativeURL() );
        values.put( LiveStreamConstants.FIELD_STATUS_STR, liveStream.getStatusStr() );
        values.put( LiveStreamConstants.FIELD_STATUS_INT, liveStream.getStatusInt() );
        values.put( LiveStreamConstants.FIELD_STATUS_MESSAGE, liveStream.getStatusMessage() );
        values.put( LiveStreamConstants.FIELD_SOURCE_FILE, liveStream.getSourceFile() );
        values.put( LiveStreamConstants.FIELD_SOURCE_HOST, liveStream.getSourceHost() );
        values.put( LiveStreamConstants.FIELD_SOURCE_WIDTH, liveStream.getSourceWidth() );
        values.put( LiveStreamConstants.FIELD_SOURCE_HEIGHT, liveStream.getSourceHeight() );
        values.put( LiveStreamConstants.FIELD_AUDIO_ONLY_BITRATE, liveStream.getAudioOnlyBitrate() );
        values.put( LiveStreamConstants.FIELD_CREATED_DATE, liveStream.getCreated().getMillis() );
        values.put( LiveStreamConstants.FIELD_LAST_MODIFIED_DATE, liveStream.getLastModified().getMillis() );

        Cursor cursor = mContext.getContentResolver().query( LiveStreamConstants.CONTENT_URI, projection, selection, selectionArgs, null );
        if( cursor.moveToFirst() ) {

            Long id = cursor.getLong( cursor.getColumnIndexOrThrow( LiveStreamConstants._ID ) );
            mContext.getContentResolver().update(ContentUris.withAppendedId(LiveStreamConstants.CONTENT_URI, id), values, null, null);

        }
        cursor.close();

        Log.v( TAG, "updateLiveStream : exit" );
        return event;
    }

    @Override
    public LiveStreamAddedEvent addLiveStream( LiveStreamAddedEvent event ) {
        Log.v( TAG, "addLiveStream : enter" );

        LiveStreamInfo liveStream = LiveStreamInfoHelper.fromDetails( event.getDetails() );

        ContentValues values = new ContentValues();
        values.put( LiveStreamConstants.FIELD_LIVE_STREAM_ID, liveStream.getId() );
        values.put( LiveStreamConstants.FIELD_WIDTH, liveStream.getWidth() );
        values.put( LiveStreamConstants.FIELD_HEIGHT, liveStream.getHeight() );
        values.put( LiveStreamConstants.FIELD_BITRATE, liveStream.getBitrate() );
        values.put( LiveStreamConstants.FIELD_AUDIO_BITRATE, liveStream.getAudioBitrate() );
        values.put( LiveStreamConstants.FIELD_SEGMENT_SIZE, liveStream.getSegmentSize() );
        values.put( LiveStreamConstants.FIELD_MAX_SEGMENTS, liveStream.getMaxSegments() );
        values.put( LiveStreamConstants.FIELD_START_SEGMENT, liveStream.getStartSegment() );
        values.put( LiveStreamConstants.FIELD_CURRENT_SEGMENT, liveStream.getCurrentSegment() );
        values.put( LiveStreamConstants.FIELD_SEGMENT_COUNT, liveStream.getSegmentCount() );
        values.put( LiveStreamConstants.FIELD_PERCENT_COMPLETE, liveStream.getPercentComplete() );
        values.put( LiveStreamConstants.FIELD_RELATIVE_URL, liveStream.getRelativeURL() );
        values.put( LiveStreamConstants.FIELD_STATUS_STR, liveStream.getStatusStr() );
        values.put( LiveStreamConstants.FIELD_STATUS_INT, liveStream.getStatusInt() );
        values.put( LiveStreamConstants.FIELD_STATUS_MESSAGE, liveStream.getStatusMessage() );
        values.put( LiveStreamConstants.FIELD_SOURCE_FILE, liveStream.getSourceFile() );
        values.put( LiveStreamConstants.FIELD_SOURCE_HOST, liveStream.getSourceHost() );
        values.put( LiveStreamConstants.FIELD_SOURCE_WIDTH, liveStream.getSourceWidth() );
        values.put( LiveStreamConstants.FIELD_SOURCE_HEIGHT, liveStream.getSourceHeight() );
        values.put( LiveStreamConstants.FIELD_AUDIO_ONLY_BITRATE, liveStream.getAudioOnlyBitrate() );
        values.put( LiveStreamConstants.FIELD_CREATED_DATE, liveStream.getCreated().getMillis() );
        values.put( LiveStreamConstants.FIELD_LAST_MODIFIED_DATE, liveStream.getLastModified().getMillis() );

        mContext.getContentResolver().insert( LiveStreamConstants.CONTENT_URI, values );

        Log.v( TAG, "addLiveStream : exit" );
        return event;
    }

    @Override
    public LiveStreamAddedEvent addRecordingLiveStream( LiveStreamAddedEvent event ) {
        Log.v( TAG, "addRecordingLiveStream : enter" );

        LiveStreamInfo liveStream = LiveStreamInfoHelper.fromDetails( event.getDetails() );

        ContentValues values = new ContentValues();
        values.put( LiveStreamConstants.FIELD_LIVE_STREAM_ID, liveStream.getId() );
        values.put( LiveStreamConstants.FIELD_WIDTH, liveStream.getWidth() );
        values.put( LiveStreamConstants.FIELD_HEIGHT, liveStream.getHeight() );
        values.put( LiveStreamConstants.FIELD_BITRATE, liveStream.getBitrate() );
        values.put( LiveStreamConstants.FIELD_AUDIO_BITRATE, liveStream.getAudioBitrate() );
        values.put( LiveStreamConstants.FIELD_SEGMENT_SIZE, liveStream.getSegmentSize() );
        values.put( LiveStreamConstants.FIELD_MAX_SEGMENTS, liveStream.getMaxSegments() );
        values.put( LiveStreamConstants.FIELD_START_SEGMENT, liveStream.getStartSegment() );
        values.put( LiveStreamConstants.FIELD_CURRENT_SEGMENT, liveStream.getCurrentSegment() );
        values.put( LiveStreamConstants.FIELD_SEGMENT_COUNT, liveStream.getSegmentCount() );
        values.put( LiveStreamConstants.FIELD_PERCENT_COMPLETE, liveStream.getPercentComplete() );
        values.put( LiveStreamConstants.FIELD_RELATIVE_URL, liveStream.getRelativeURL() );
        values.put( LiveStreamConstants.FIELD_STATUS_STR, liveStream.getStatusStr() );
        values.put( LiveStreamConstants.FIELD_STATUS_INT, liveStream.getStatusInt() );
        values.put( LiveStreamConstants.FIELD_STATUS_MESSAGE, liveStream.getStatusMessage() );
        values.put( LiveStreamConstants.FIELD_SOURCE_FILE, liveStream.getSourceFile() );
        values.put( LiveStreamConstants.FIELD_SOURCE_HOST, liveStream.getSourceHost() );
        values.put( LiveStreamConstants.FIELD_SOURCE_WIDTH, liveStream.getSourceWidth() );
        values.put( LiveStreamConstants.FIELD_SOURCE_HEIGHT, liveStream.getSourceHeight() );
        values.put( LiveStreamConstants.FIELD_AUDIO_ONLY_BITRATE, liveStream.getAudioOnlyBitrate() );
        values.put( LiveStreamConstants.FIELD_CREATED_DATE, liveStream.getCreated().getMillis() );
        values.put( LiveStreamConstants.FIELD_LAST_MODIFIED_DATE, liveStream.getLastModified().getMillis() );

        if( null != event.getRecordedId() ) {
            values.put( LiveStreamConstants.FIELD_RECORDED_ID, event.getRecordedId() );
        }

        if( null != event.getChanId() ) {
            values.put( LiveStreamConstants.FIELD_CHAN_ID, event.getChanId() );
        }

        if( null != event.getStartTime() ) {
            values.put( LiveStreamConstants.FIELD_START_TIME, event.getStartTime().getMillis() );
        }

        mContext.getContentResolver().insert( LiveStreamConstants.CONTENT_URI, values );

        Log.v( TAG, "addRecordingLiveStream : exit" );
        return event;
    }

    @Override
    public LiveStreamAddedEvent addVideoLiveStream( LiveStreamAddedEvent event ) {
        Log.v( TAG, "addVideoLiveStream : enter" );

        LiveStreamInfo liveStream = LiveStreamInfoHelper.fromDetails( event.getDetails() );

        ContentValues values = new ContentValues();
        values.put( LiveStreamConstants.FIELD_LIVE_STREAM_ID, liveStream.getId() );
        values.put( LiveStreamConstants.FIELD_WIDTH, liveStream.getWidth() );
        values.put( LiveStreamConstants.FIELD_HEIGHT, liveStream.getHeight() );
        values.put( LiveStreamConstants.FIELD_BITRATE, liveStream.getBitrate() );
        values.put( LiveStreamConstants.FIELD_AUDIO_BITRATE, liveStream.getAudioBitrate() );
        values.put( LiveStreamConstants.FIELD_SEGMENT_SIZE, liveStream.getSegmentSize() );
        values.put( LiveStreamConstants.FIELD_MAX_SEGMENTS, liveStream.getMaxSegments() );
        values.put( LiveStreamConstants.FIELD_START_SEGMENT, liveStream.getStartSegment() );
        values.put( LiveStreamConstants.FIELD_CURRENT_SEGMENT, liveStream.getCurrentSegment() );
        values.put( LiveStreamConstants.FIELD_SEGMENT_COUNT, liveStream.getSegmentCount() );
        values.put( LiveStreamConstants.FIELD_PERCENT_COMPLETE, liveStream.getPercentComplete() );
        values.put( LiveStreamConstants.FIELD_RELATIVE_URL, liveStream.getRelativeURL() );
        values.put( LiveStreamConstants.FIELD_STATUS_STR, liveStream.getStatusStr() );
        values.put( LiveStreamConstants.FIELD_STATUS_INT, liveStream.getStatusInt() );
        values.put( LiveStreamConstants.FIELD_STATUS_MESSAGE, liveStream.getStatusMessage() );
        values.put( LiveStreamConstants.FIELD_SOURCE_FILE, liveStream.getSourceFile() );
        values.put( LiveStreamConstants.FIELD_SOURCE_HOST, liveStream.getSourceHost() );
        values.put( LiveStreamConstants.FIELD_SOURCE_WIDTH, liveStream.getSourceWidth() );
        values.put( LiveStreamConstants.FIELD_SOURCE_HEIGHT, liveStream.getSourceHeight() );
        values.put( LiveStreamConstants.FIELD_AUDIO_ONLY_BITRATE, liveStream.getAudioOnlyBitrate() );
        values.put( LiveStreamConstants.FIELD_CREATED_DATE, liveStream.getCreated().getMillis() );
        values.put( LiveStreamConstants.FIELD_LAST_MODIFIED_DATE, liveStream.getLastModified().getMillis() );
        values.put( LiveStreamConstants.FIELD_VIDEO_ID, event.getVideoId() );

        mContext.getContentResolver().insert( LiveStreamConstants.CONTENT_URI, values );

        Log.v( TAG, "addVideoLiveStream : exit" );
        return event;
    }

    @Override
    public LiveStreamRemovedEvent removeLiveStream( LiveStreamRemovedEvent event ) {
        Log.v( TAG, "removeLiveStream : enter" );

        if( event.isDeletionCompleted() ) {

            mContext.getContentResolver().delete( ContentUris.withAppendedId( LiveStreamConstants.CONTENT_URI, event.getKey() ), null, null );

            Log.v( TAG, "removeLiveStream : live stream removed" );
        }

        Log.v( TAG, "removeLiveStream : exit" );
        return event;
    }

}
