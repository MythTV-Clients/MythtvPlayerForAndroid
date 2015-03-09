package org.mythtv.android.library.persistence.service.dvr;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.library.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.events.DeleteEvent;
import org.mythtv.android.library.events.DeletedEvent;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.ProgramRemovedEvent;
import org.mythtv.android.library.events.dvr.RemoveProgramEvent;
import org.mythtv.android.library.events.dvr.RemoveTitleInfoEvent;
import org.mythtv.android.library.events.dvr.TitleInfoDetails;
import org.mythtv.android.library.events.dvr.TitleInfoRemovedEvent;
import org.mythtv.android.library.persistence.domain.dvr.ChannelInfo;
import org.mythtv.android.library.persistence.domain.dvr.Program;
import org.mythtv.android.library.persistence.domain.dvr.ProgramConstants;
import org.mythtv.android.library.persistence.domain.dvr.RecordingInfo;
import org.mythtv.android.library.persistence.domain.dvr.TitleInfo;
import org.mythtv.android.library.persistence.domain.dvr.TitleInfoConstants;
import org.mythtv.android.library.persistence.repository.MythtvProvider;
import org.mythtv.android.library.persistence.service.DvrPersistenceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmfrey on 3/7/15.
 */
public class DvrPersistenceServiceEventHandler implements DvrPersistenceService {

    private static final String TAG = DvrPersistenceServiceEventHandler.class.getSimpleName();

    Context mContext;

    public DvrPersistenceServiceEventHandler( Context context ) {

        mContext = context;

    }

    @Override
    public AllProgramsEvent refreshRecordedPrograms( AllProgramsEvent event ) {

        String[] projection = new String[] { ProgramConstants.FIELD_CHANNEL_CHAN_ID, ProgramConstants.FIELD_RECORDING_START_TS, ProgramConstants._ID };
        String selection = null;
        String[] selectionArgs = null;

        Map<ProgramKey, Long> programIds = new HashMap<>();

        Cursor cursor = mContext.getContentResolver().query( ProgramConstants.CONTENT_URI, projection, selection, selectionArgs, null );
        while( cursor.moveToNext() ) {

            ProgramKey key = new ProgramKey( cursor.getInt( cursor.getColumnIndex( ProgramConstants.FIELD_CHANNEL_CHAN_ID ) ), cursor.getLong( cursor.getColumnIndex( ProgramConstants.FIELD_RECORDING_START_TS ) ) );

            programIds.put( key, cursor.getLong( cursor.getColumnIndex( ProgramConstants._ID ) ) );

        }
        cursor.close();

        if( null != event.getDetails() && !event.getDetails().isEmpty() ) {

            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

            projection = new String[] { ProgramConstants._ID };
            selection = ProgramConstants.FIELD_CHANNEL_CHAN_ID + " = ? AND " + ProgramConstants.FIELD_RECORDING_START_TS + " = ?";

            ContentValues values;

            for( ProgramDetails details : event.getDetails() ) {

                Program program = ProgramHelper.fromDetails( details );
                ProgramKey existing = new ProgramKey( program.getChannel().getChanId(), program.getRecording().getStartTs().getMillis() );

                if( programIds.containsKey( existing ) ) {
                    programIds.remove( existing );
                }

                selectionArgs = new String[] { String.valueOf( program.getChannel().getChanId() ), String.valueOf( program.getRecording().getStartTs().getMillis() ) };

                values = new ContentValues();
                values.put( ProgramConstants.FIELD_PROGRAM_TYPE, ProgramConstants.ProgramType.RECORDED.name() );
                values.put( ProgramConstants.FIELD_PROGRAM_START_TIME, program.getStartTime().getMillis() );
                values.put( ProgramConstants.FIELD_PROGRAM_END_TIME, program.getEndTime().getMillis() );
                values.put( ProgramConstants.FIELD_PROGRAM_TITLE_SORT, removeArticles(program.getTitle()).toUpperCase() );
                values.put( ProgramConstants.FIELD_PROGRAM_TITLE, program.getTitle() );
                values.put( ProgramConstants.FIELD_PROGRAM_SUB_TITLE, ( null == program.getSubTitle() ? "" : program.getSubTitle() ) );
                values.put( ProgramConstants.FIELD_PROGRAM_CATEGORY, ( null == program.getCategory() ? "" : program.getCategory() ) );
                values.put( ProgramConstants.FIELD_PROGRAM_CAT_TYPE, ( null == program.getCatType() ? "" : program.getCatType() ) );
                values.put( ProgramConstants.FIELD_PROGRAM_REPEAT, ( program.getRepeat() ? 1 : 0 ) );
                values.put( ProgramConstants.FIELD_PROGRAM_SERIES_ID, ( null == program.getSeriesId() ? "" : program.getSeriesId() ) );
                values.put( ProgramConstants.FIELD_PROGRAM_PROGRAM_ID, ( null == program.getProgramId() ? "" : program.getProgramId() ) );
                values.put( ProgramConstants.FIELD_PROGRAM_STARS, program.getStars() );
                values.put( ProgramConstants.FIELD_PROGRAM_FILE_SIZE, program.getFileSize() );
                values.put( ProgramConstants.FIELD_PROGRAM_LAST_MODIFIED, program.getLastModified().getMillis() );
                values.put( ProgramConstants.FIELD_PROGRAM_FILE_NAME, ( null == program.getFileName() ? "" : program.getFileName() ) );
                values.put( ProgramConstants.FIELD_PROGRAM_HOSTNAME, ( null == program.getHostName() ? "" : program.getHostName() ) );
                values.put( ProgramConstants.FIELD_PROGRAM_AIR_DATE, program.getAirdate().toDate().getTime() );
                values.put( ProgramConstants.FIELD_PROGRAM_DESCRIPTION, ( null == program.getDescription() ? "" : program.getDescription() ) );
                values.put( ProgramConstants.FIELD_PROGRAM_INETREF, program.getInetref() );
                values.put( ProgramConstants.FIELD_PROGRAM_SEASON, program.getSeason() );
                values.put(ProgramConstants.FIELD_PROGRAM_EPISODE, program.getEpisode());

                values.put( ProgramConstants.FIELD_CHANNEL_CHAN_ID, program.getChannel().getChanId() );
                values.put( ProgramConstants.FIELD_CHANNEL_CHAN_NUM, ( null == program.getChannel().getChanNum() ? "" : program.getChannel().getChanNum() ) );
                values.put( ProgramConstants.FIELD_CHANNEL_CALLSIGN, ( null == program.getChannel().getCallSign() ? "" : program.getChannel().getCallSign() ) );
                values.put( ProgramConstants.FIELD_CHANNEL_ICON_URL, ( null == program.getChannel().getIconURL() ? "" : program.getChannel().getIconURL() ) );
                values.put( ProgramConstants.FIELD_CHANNEL_CHANNEL_NAME, ( null == program.getChannel().getChannelName() ? "" : program.getChannel().getChannelName() ) );

                values.put( ProgramConstants.FIELD_RECORDING_RECORDED_ID, ( null == program.getRecording().getRecordedId() ? -1 : program.getRecording().getRecordedId() ) );
                values.put( ProgramConstants.FIELD_RECORDING_STATUS, program.getRecording().getStatus() );
                values.put( ProgramConstants.FIELD_RECORDING_PRIORITY, program.getRecording().getPriority() );
                values.put( ProgramConstants.FIELD_RECORDING_START_TS, program.getRecording().getStartTs().getMillis() );
                values.put( ProgramConstants.FIELD_RECORDING_END_TS, program.getRecording().getEndTs().getMillis() );
                values.put( ProgramConstants.FIELD_RECORDING_RECORD_ID, program.getRecording().getRecordId() );
                values.put( ProgramConstants.FIELD_RECORDING_REC_GROUP, ( null == program.getRecording().getRecGroup() ? "" : program.getRecording().getRecGroup() ) );
                values.put( ProgramConstants.FIELD_RECORDING_PLAY_GROUP, ( null == program.getRecording().getPlayGroup() ? "" : program.getRecording().getPlayGroup() ) );
                values.put( ProgramConstants.FIELD_RECORDING_STORAGE_GROUP, ( null == program.getRecording().getStorageGroup() ? "" : program.getRecording().getStorageGroup() ) );
                values.put( ProgramConstants.FIELD_RECORDING_REC_TYPE, program.getRecording().getRecType() );
                values.put( ProgramConstants.FIELD_RECORDING_DUP_IN_TYPE, program.getRecording().getDupInType() );
                values.put( ProgramConstants.FIELD_RECORDING_DUP_METHOD, program.getRecording().getDupMethod() );
                values.put( ProgramConstants.FIELD_RECORDING_ENCODER_ID, program.getRecording().getEncoderId() );
                values.put( ProgramConstants.FIELD_RECORDING_ENCODER_NAME, ( null == program.getRecording().getEncoderName() ? "" : program.getRecording().getEncoderName() ) );
                values.put( ProgramConstants.FIELD_RECORDING_PROFILE, ( null == program.getRecording().getProfile() ? "" : program.getRecording().getProfile() ) );

                cursor = mContext.getContentResolver().query( ProgramConstants.CONTENT_URI, projection, selection, selectionArgs, null );
                if( cursor.moveToFirst() ) {

                    Long id = cursor.getLong( cursor.getColumnIndexOrThrow( ProgramConstants._ID ) );
                    ops.add(
                            ContentProviderOperation
                                    .newUpdate( ContentUris.withAppendedId( ProgramConstants.CONTENT_URI, id ) )
                                    .withValues( values )
                                    .build()
                    );

                } else {

                    ops.add(
                            ContentProviderOperation
                                    .newInsert( ProgramConstants.CONTENT_URI )
                                    .withValues( values )
                                    .build()
                    );

                }
                cursor.close();

            }

            try {

                mContext.getContentResolver().applyBatch( MythtvProvider.AUTHORITY, ops );

            } catch( Exception e ) {

                Log.e( TAG, "refreshPrograms : error processing programs", e);

            } finally {

                if( !programIds.isEmpty() ) {

                    Map<ProgramDetails, Long> programDetailsIds = new HashMap<>();

                    for( long programId : programIds.values() ) {

                        removeProgram( new RemoveProgramEvent( programId ) );

                    }

                    event.setDeleted( programDetailsIds );

                }

            }

        }

        return event;
    }

    @Override
    public ProgramRemovedEvent removeProgram( RemoveProgramEvent event ) {
        Log.v( TAG, "removeProgram : enter" );

        String selection = null;
        String[] selectionArgs = null;

        int deleted = mContext.getContentResolver().delete( ContentUris.withAppendedId( ProgramConstants.CONTENT_URI, event.getKey() ), selection, selectionArgs );
        if( deleted == 1 ) {

            Log.v( TAG, "removeProgram : exit" );
            return new ProgramRemovedEvent( event.getKey() );
        }

        Log.v( TAG, "removeProgram : error, program not deleted" );
        return ProgramRemovedEvent.deletionFailed(event.getKey());
    }

    @Override
    public AllTitleInfosEvent refreshTitleInfos( AllTitleInfosEvent event ) {
        Log.v( TAG, "refreshTitleInfos : enter" );

        String[] projection = new String[]{ TitleInfoConstants.FIELD_TITLE, TitleInfoConstants._ID };
        String selection = null;
        String[] selectionArgs = null;

        Map<String, Long> titleInfoIds = new HashMap<>();

        Cursor cursor = mContext.getContentResolver().query( TitleInfoConstants.CONTENT_URI, projection, selection, selectionArgs, null );
        while( cursor.moveToNext() ) {

            titleInfoIds.put( cursor.getString( cursor.getColumnIndex( TitleInfoConstants.FIELD_TITLE ) ), cursor.getLong( cursor.getColumnIndex( TitleInfoConstants._ID ) ) );

        }
        cursor.close();

        if( null != event.getDetails() && !event.getDetails().isEmpty() ) {

            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

            selection = TitleInfoConstants.FIELD_TITLE + " = ? AND " + TitleInfoConstants.FIELD_INETREF + " = ?";

            ContentValues values;

            for( TitleInfoDetails details : event.getDetails() ) {
                TitleInfo titleInfo = TitleInfoHelper.fromDetails( details );

                if( titleInfoIds.containsKey( titleInfo.getTitle() ) ) {
                    titleInfoIds.remove( titleInfo.getTitle() );
                }

                selectionArgs = new String[] { titleInfo.getTitle(), titleInfo.getInetref() };

                values = new ContentValues();
                values.put( TitleInfoConstants.FIELD_TITLE, titleInfo.getTitle() );
                values.put( TitleInfoConstants.FIELD_INETREF, titleInfo.getInetref() );
                values.put( TitleInfoConstants.FIELD_TITLE_SORT, removeArticles( titleInfo.getTitle() ).toUpperCase() );

                if( titleInfo.getTitle().equals( MainApplication.getInstance().getResources().getString( R.string.all_recordings ) ) ) {

                    values.put(TitleInfoConstants.FIELD_SORT, 0);

                } else {

                    values.put( TitleInfoConstants.FIELD_SORT, 1 );

                }

                cursor = mContext.getContentResolver().query( TitleInfoConstants.CONTENT_URI, projection, selection, selectionArgs, null );
                if( cursor.moveToFirst() ) {

                    Long id = cursor.getLong( cursor.getColumnIndexOrThrow( TitleInfoConstants._ID ) );
                    ops.add(
                            ContentProviderOperation
                                    .newUpdate( ContentUris.withAppendedId( TitleInfoConstants.CONTENT_URI, id ) )
                                    .withValues( values )
                                    .build()
                    );

                } else {

                    ops.add(
                            ContentProviderOperation
                                    .newInsert( TitleInfoConstants.CONTENT_URI )
                                    .withValues( values )
                                    .build()
                    );

                }
                cursor.close();

            }

            try {

                mContext.getContentResolver().applyBatch( MythtvProvider.AUTHORITY, ops );

            } catch( Exception e ) {

                Log.e( TAG, "refreshTitleInfos : error processing title infos", e );

            } finally {

                if( !titleInfoIds.isEmpty() ) {

                    event.setDeleted( titleInfoIds );

                }

            }

        }

        Log.v( TAG, "refreshTitleInfos : exit" );
        return event;
    }

    @Override
    public TitleInfoRemovedEvent removeTitleInfo( RemoveTitleInfoEvent event ) {
        Log.v( TAG, "removeTitleInfo : enter" );

        String selection = null;
        String[] selectionArgs = null;

        int deleted = mContext.getContentResolver().delete( ContentUris.withAppendedId( TitleInfoConstants.CONTENT_URI, event.getKey() ), selection, selectionArgs );
        if( deleted == 1 ) {

            Log.v( TAG, "removeTitleInfo : exit" );
            return new TitleInfoRemovedEvent( event.getKey() );
        }

        Log.v( TAG, "removeTitleInfo : error, title info not deleted" );
        return TitleInfoRemovedEvent.deletionFailed(event.getKey());
    }

    @Override
    public DeletedEvent cleanup( DeleteEvent event ) {

        return null;
    }

    private String removeArticles( String value ) {

        String ret = value;

        if( value.toLowerCase().startsWith( "the " ) ) {
            ret = ret.substring( "the ".length() );
        }

        if( value.toLowerCase().startsWith("an ") ) {
            ret = ret.substring( "an ".length() );
        }

        if( value.toLowerCase().startsWith( "a " ) ) {
            ret = ret.substring( "a ".length() );
        }

        return ret;
    }

    private class ProgramKey {

        private final int chanId;
        private final long startTs;

        public ProgramKey( final int chanId, final long startTs ) {

            this.chanId = chanId;
            this.startTs = startTs;

        }

        public int getChanId() {
            return chanId;
        }

        public long getStartTs() {
            return startTs;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ProgramKey that = (ProgramKey) o;

            if (chanId != that.chanId) return false;
            if (startTs != that.startTs) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = chanId;
            result = 31 * result + (int) (startTs ^ (startTs >>> 32));
            return result;
        }

    }

}
