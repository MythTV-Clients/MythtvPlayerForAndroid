/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.library.persistence.service.dvr;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.utils.Utils;
import org.mythtv.android.library.events.dvr.AllProgramsCountEvent;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.DeleteProgramsEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.ProgramDetailsEvent;
import org.mythtv.android.library.events.dvr.ProgramRemovedEvent;
import org.mythtv.android.library.events.dvr.ProgramsDeletedEvent;
import org.mythtv.android.library.events.dvr.ProgramsUpdatedEvent;
import org.mythtv.android.library.events.dvr.RemoveProgramEvent;
import org.mythtv.android.library.events.dvr.RemoveTitleInfoEvent;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsCountEvent;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.RequestRecordedProgramEvent;
import org.mythtv.android.library.events.dvr.SearchRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.TitleInfoDetails;
import org.mythtv.android.library.events.dvr.TitleInfoRemovedEvent;
import org.mythtv.android.library.events.dvr.TitleInfosUpdatedEvent;
import org.mythtv.android.library.events.dvr.UpdateRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.UpdateTitleInfosEvent;
import org.mythtv.android.library.persistence.domain.dvr.CastMember;
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
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by dmfrey on 3/7/15.
 */
public class DvrPersistenceServiceEventHandler implements DvrPersistenceService {

    private static final String TAG = DvrPersistenceServiceEventHandler.class.getSimpleName();

    Context mContext;

    public DvrPersistenceServiceEventHandler() {

        mContext = MainApplication.getInstance().getApplicationContext();

    }

    @Override
    public AllProgramsEvent requestAllRecordedPrograms( RequestAllRecordedProgramsEvent event ) {
        Log.v( TAG, "requestAllRecordedPrograms : enter" );

        List<Program> programs = new ArrayList<>();

        String[] projection = new String[]{ "rowid as " + ProgramConstants._ID, ProgramConstants.FIELD_PROGRAM_START_TIME, ProgramConstants.FIELD_PROGRAM_END_TIME, ProgramConstants.FIELD_PROGRAM_TITLE, ProgramConstants.FIELD_PROGRAM_SUB_TITLE, ProgramConstants.FIELD_PROGRAM_INETREF, ProgramConstants.FIELD_PROGRAM_DESCRIPTION, ProgramConstants.FIELD_PROGRAM_SEASON, ProgramConstants.FIELD_PROGRAM_EPISODE, ProgramConstants.FIELD_CHANNEL_CHAN_ID, ProgramConstants.FIELD_CHANNEL_CALLSIGN, ProgramConstants.FIELD_CHANNEL_CHAN_NUM, ProgramConstants.FIELD_RECORDING_RECORDED_ID, ProgramConstants.FIELD_RECORDING_STATUS, ProgramConstants.FIELD_RECORDING_START_TS, ProgramConstants.FIELD_RECORDING_RECORD_ID, ProgramConstants.FIELD_PROGRAM_FILE_NAME, ProgramConstants.FIELD_PROGRAM_HOSTNAME, ProgramConstants.FIELD_RECORDING_REC_GROUP, ProgramConstants.FIELD_RECORDING_STORAGE_GROUP, ProgramConstants.FIELD_CAST_MEMBER_NAMES };
        String selection = ProgramConstants.FIELD_PROGRAM_TYPE + " = ? AND " + ProgramConstants.FIELD_RECORDING_STATUS + " != -2";

        List<String> selectionArgs = new ArrayList<>();
        selectionArgs.add( ProgramConstants.ProgramType.RECORDED.name() );

        String sort = ProgramConstants.FIELD_PROGRAM_END_TIME + " desc";

        if( null != event.getTitle() && !"".equals( event.getTitle() )  ) {
            selection += " AND " + ProgramConstants.FIELD_PROGRAM_TITLE + " = ?";
            selectionArgs.add( event.getTitle() );
        }

        if( null != event.getInetref() && !"".equals( event.getInetref() )  ) {
            selection += " AND " + ProgramConstants.FIELD_PROGRAM_INETREF + " = ?";
            selectionArgs.add( event.getInetref() );
        } else {

            if( null != event.getTitle() && !"".equals( event.getTitle() )  ) {
                selection += " AND (" + ProgramConstants.FIELD_PROGRAM_INETREF + " IS NULL OR "  + ProgramConstants.FIELD_PROGRAM_INETREF + " = ? )";
                selectionArgs.add( "" );
            }

        }

        if( null != event.getRecordingGroup() && !"".equals( event.getRecordingGroup() )  ) {
            selection += " AND " + ProgramConstants.FIELD_RECORDING_REC_GROUP + " = ?";
            selectionArgs.add( event.getRecordingGroup() );
        }

        String limit = " ";
        if( null != event.getLimit() && -1 != event.getLimit() ) {

            limit = " LIMIT " + event.getLimit();

            if( null != event.getOffset() && -1 != event.getOffset() ) {

                limit = " LIMIT " + event.getOffset() + "," + event.getLimit();

            }

        }
        sort += limit;

        Cursor cursor = mContext.getContentResolver().query( ProgramConstants.CONTENT_URI, projection, selection, selectionArgs.toArray( new String[ selectionArgs.size() ] ), sort );
        while( cursor.moveToNext() ) {

            programs.add( convertCursorToProgram( cursor ) );

        }
        cursor.close();

        List<ProgramDetails> details = new ArrayList<>();
        if( !programs.isEmpty() ) {

            for( Program program : programs ) {

                details.add( program.toDetails() );

            }

        }

        return new AllProgramsEvent( details );
    }

    @Override
    public AllProgramsCountEvent requestAllRecordedProgramsCount( RequestAllRecordedProgramsCountEvent event ) {

        String selection = ProgramConstants.FIELD_PROGRAM_TYPE + " = ? AND " + ProgramConstants.FIELD_RECORDING_STATUS + " != -2";

        List<String> selectionArgs = new ArrayList<>();
        selectionArgs.add( ProgramConstants.ProgramType.RECORDED.name() );

        if( null != event.getRecordingGroup() && !"".equals( event.getRecordingGroup() )  ) {
            selection += " AND " + ProgramConstants.FIELD_RECORDING_REC_GROUP + " = ?";
            selectionArgs.add( event.getRecordingGroup() );
        }

        int count = -1;
        Cursor cursor = mContext.getContentResolver().query( ProgramConstants.CONTENT_URI, new String[] { "count(*) AS count" }, selection, selectionArgs.toArray( new String[ selectionArgs.size() ] ), null );
        while( cursor.moveToNext() ) {

            count = cursor.getInt( 0 );

        }
        cursor.close();

        return new AllProgramsCountEvent( count );
    }

    @Override
    public AllProgramsEvent searchRecordedPrograms( SearchRecordedProgramsEvent event ) {
        Log.v( TAG, "searchRecordedPrograms : enter" );

        List<Program> programs = new ArrayList<>();

        String[] projection = new String[]{ ProgramConstants.FIELD_PROGRAM_START_TIME, ProgramConstants.FIELD_PROGRAM_END_TIME, ProgramConstants.FIELD_PROGRAM_TITLE, ProgramConstants.FIELD_PROGRAM_SUB_TITLE, ProgramConstants.FIELD_PROGRAM_INETREF, ProgramConstants.FIELD_PROGRAM_DESCRIPTION, ProgramConstants.FIELD_PROGRAM_SEASON, ProgramConstants.FIELD_PROGRAM_EPISODE, ProgramConstants.FIELD_CHANNEL_CHAN_ID, ProgramConstants.FIELD_CHANNEL_CALLSIGN, ProgramConstants.FIELD_CHANNEL_CHAN_NUM, ProgramConstants.FIELD_RECORDING_RECORDED_ID, ProgramConstants.FIELD_RECORDING_STATUS, ProgramConstants.FIELD_RECORDING_START_TS, ProgramConstants.FIELD_RECORDING_RECORD_ID, ProgramConstants.FIELD_PROGRAM_FILE_NAME, ProgramConstants.FIELD_PROGRAM_HOSTNAME, ProgramConstants.FIELD_RECORDING_REC_GROUP, ProgramConstants.FIELD_RECORDING_STORAGE_GROUP, ProgramConstants.FIELD_CAST_MEMBER_NAMES };
        String selection = ProgramConstants.TABLE_NAME + " MATCH ? AND " + ProgramConstants.FIELD_PROGRAM_TYPE + " = ? AND " + ProgramConstants.FIELD_RECORDING_STATUS + " != -2";
        List<String> selectionArgs = new ArrayList<>();
        selectionArgs.add( event.getQuery() + "*" );
        selectionArgs.add( ProgramConstants.ProgramType.RECORDED.name() );

        if( null != event.getRecordingGroup() && !"".equals( event.getRecordingGroup() )  ) {
            selection += " AND " + ProgramConstants.FIELD_RECORDING_REC_GROUP + " = ?";
            selectionArgs.add( event.getRecordingGroup() );
        }

        String sort = ProgramConstants.FIELD_PROGRAM_END_TIME + " desc";

        Cursor cursor = mContext.getContentResolver().query( Uri.withAppendedPath( ProgramConstants.CONTENT_URI, "/fts" ), projection, selection, selectionArgs.toArray( new String[ selectionArgs.size() ] ), sort );
        while( cursor.moveToNext() ) {

            programs.add( convertCursorToProgram( cursor ) );

        }
        cursor.close();

        List<ProgramDetails> details = new ArrayList<>();
        if( !programs.isEmpty() ) {

            for( Program program : programs ) {

                details.add( program.toDetails() );

            }

        }

        return new AllProgramsEvent( details );
    }

    @Override
    public ProgramsUpdatedEvent updateRecordedPrograms( UpdateRecordedProgramsEvent event ) {
        Log.v( TAG, "updateRecordedPrograms : enter" );

        String[] projection = new String[] { "rowid as " + ProgramConstants._ID, ProgramConstants.FIELD_PROGRAM_FILE_NAME };
        String selection = ProgramConstants.FIELD_PROGRAM_TYPE + " = ? AND " + ProgramConstants.FIELD_RECORDING_STATUS + " != -2";
        String[] selectionArgs = new String[] { "RECORDED" };

        if( null != event.getTitleRegEx() && !"".equals( event.getTitleRegEx() ) ) {

            selection = ProgramConstants.FIELD_PROGRAM_TYPE + " = ? AND " + ProgramConstants.FIELD_PROGRAM_TITLE + " = ? AND " + ProgramConstants.FIELD_RECORDING_STATUS + " != -2";
            selectionArgs = new String[] { "RECORDED", event.getTitleRegEx() };

        }

        Map<String, Long> programIds = new HashMap<>();
        Cursor cursor = mContext.getContentResolver().query( ProgramConstants.CONTENT_URI, projection, selection, selectionArgs, null );
        while( cursor.moveToNext() ) {
            programIds.put(cursor.getString(cursor.getColumnIndex(ProgramConstants.FIELD_PROGRAM_FILE_NAME)), cursor.getLong(cursor.getColumnIndex(ProgramConstants._ID)));
        }
        cursor.close();

        if( null != event.getDetails() && !event.getDetails().isEmpty() ) {

            ArrayList<ContentProviderOperation> ops = new ArrayList<>();

            projection = new String[] { "rowid as " + ProgramConstants._ID };

            ContentValues values;

            for( ProgramDetails details : event.getDetails() ) {

                Program program = ProgramHelper.fromDetails( details );
                //Log.v(TAG, "updateRecordedPrograms : program=" + program);

                if( null == program.getStartTime() || null == program.getEndTime() || null == program.getRecording().getStartTs() || null == program.getRecording().getEndTs() ||
                        "LiveTV".equalsIgnoreCase( program.getRecording().getRecGroup() ) ||
                        "Deleted".equalsIgnoreCase( program.getRecording().getRecGroup() ) ) {
                    continue;
                }

                if( programIds.containsKey( program.getFileName() ) ) {
                    programIds.remove( program.getFileName() );
                }

                selection = ProgramConstants.FIELD_PROGRAM_FILE_NAME + " = ?";
                selectionArgs = new String[] { program.getFileName() };

                values = new ContentValues();
                values.put( ProgramConstants.FIELD_PROGRAM_TYPE, ProgramConstants.ProgramType.RECORDED.name() );
                values.put( ProgramConstants.FIELD_PROGRAM_START_TIME, program.getStartTime().getMillis() );
                values.put( ProgramConstants.FIELD_PROGRAM_END_TIME, program.getEndTime().getMillis() );
                values.put( ProgramConstants.FIELD_PROGRAM_TITLE_SORT, Utils.removeArticles( program.getTitle() ).toUpperCase() );
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
                values.put( ProgramConstants.FIELD_PROGRAM_AIR_DATE, ( null == program.getAirdate() ? 0 : program.getAirdate().toDate().getTime() ) );
                values.put( ProgramConstants.FIELD_PROGRAM_DESCRIPTION, ( null == program.getDescription() ? "" : program.getDescription() ) );
                values.put( ProgramConstants.FIELD_PROGRAM_INETREF, program.getInetref() );
                values.put( ProgramConstants.FIELD_PROGRAM_SEASON, program.getSeason() );
                values.put( ProgramConstants.FIELD_PROGRAM_EPISODE, program.getEpisode() );

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

                if( null != program.getCastMembers() && !program.getCastMembers().isEmpty() ) {

                    StringBuilder castMemberNames = new StringBuilder();
                    StringBuilder castMemberCharacters = new StringBuilder();
                    StringBuilder castMemberRoles = new StringBuilder();

                    for( CastMember castMember : program.getCastMembers() ) {

                        if( null != castMember.getName() && !"".equals( castMember.getName() ) ) {
                            castMemberNames.append( castMember.getName() ).append( "|" );
                        }

                        if( null != castMember.getCharacterName() && !"".equals( castMember.getCharacterName() ) ) {
                            castMemberCharacters.append( castMember.getCharacterName() ).append( "|" );
                        }

                        if( null != castMember.getRole() && !"".equals( castMember.getRole() ) ) {
                            castMemberRoles.append( castMember.getRole() ).append( "|" );
                        }

                    }

                    String names = castMemberNames.toString();
                    if( names.endsWith( "|" ) ) {
                        names = names.substring( 0, names.length() - 1 );
                    }

                    String characters = castMemberCharacters.toString();
                    if( characters.endsWith( "|" ) ) {
                        characters = characters.substring( 0, characters.length() - 1 );
                    }

                    String roles = castMemberRoles.toString();
                    if( roles.endsWith( "|" ) ) {
                        roles = roles.substring( 0, roles.length() - 1 );
                    }

                    values.put( ProgramConstants.FIELD_CAST_MEMBER_NAMES, names );
                    values.put( ProgramConstants.FIELD_CAST_MEMBER_CHARACTERS, characters );
                    values.put( ProgramConstants.FIELD_CAST_MEMBER_ROLES, roles );

                }

                cursor = mContext.getContentResolver().query( ProgramConstants.CONTENT_URI, projection, selection, selectionArgs, null );
                if( cursor.moveToFirst() ) {

                    Long id = cursor.getLong( cursor.getColumnIndex( ProgramConstants._ID ) );
                    //Log.v( TAG, "updateRecordedPrograms : updating existing program - rowid=" + id );
                    ops.add(
                            ContentProviderOperation
                                    .newUpdate( ContentUris.withAppendedId( ProgramConstants.CONTENT_URI, id ) )
                                    .withValues( values )
                                    .build()
                    );

                } else {
                    Log.v( TAG, "updateRecordedPrograms : adding new program" );

                    ops.add(
                            ContentProviderOperation
                                    .newInsert( ProgramConstants.CONTENT_URI )
                                    .withValues( values )
                                    .build()
                    );

                }
                cursor.close();

            }

            if( !programIds.isEmpty() ) {

                for( Long programId : programIds.values() ) {
                    Log.v( TAG, "updateRecordedPrograms : deleting stale program" );

                    ops.add(
                            ContentProviderOperation
                                    .newDelete( ContentUris.withAppendedId( ProgramConstants.CONTENT_URI, programId ) )
                                    .build()
                    );

                }

            }

            try {

                mContext.getContentResolver().applyBatch( MythtvProvider.AUTHORITY, ops );

                Log.v( TAG, "updateRecordedPrograms : exit" );
                return new ProgramsUpdatedEvent( event.getDetails() );

            } catch( Exception e ) {

                Log.e( TAG, "updateRecordedPrograms : error processing programs", e );

            }

        }

        Log.w(TAG, "updateRecordedPrograms : exit, programs not updated");
        return ProgramsUpdatedEvent.notUpdated();
    }

    @Override
    public ProgramsDeletedEvent deleteRecordedPrograms( DeleteProgramsEvent event ) {
        Log.v( TAG, "deleteRecordedPrograms : enter" );

        String[] projection = new String[] { "rowid as " + ProgramConstants._ID };
        String selection = ProgramConstants.FIELD_PROGRAM_FILE_NAME + " = ?";
        String[] selectionArgs = null;

        if( null != event.getDetails() && !event.getDetails().isEmpty() ) {

            ArrayList<ContentProviderOperation> ops = new ArrayList<>();

            ContentValues values;

            for( ProgramDetails details : event.getDetails() ) {

                Program program = ProgramHelper.fromDetails( details );
                //Log.v( TAG, "deleteRecordedPrograms : program=" + program );

                Cursor cursor = mContext.getContentResolver().query( ProgramConstants.CONTENT_URI, projection, selection, selectionArgs, null );
                if( cursor.moveToFirst() ) {

//                    Long id = cursor.getLong( cursor.getColumnIndex( ProgramConstants._ID ) );
//                    Log.v( TAG, "deleteRecordedPrograms : updating existing program - rowid=" + id );
//                    ops.add(
//                            ContentProviderOperation
//                                    .newUpdate( ContentUris.withAppendedId( ProgramConstants.CONTENT_URI, id ) )
//                                    .withValues( values )
//                                    .build()
//                    );

                } else {
                    Log.v( TAG, "updateRecordedPrograms : adding new program" );

//                    ops.add(
//                            ContentProviderOperation
//                                    .newInsert( ProgramConstants.CONTENT_URI )
//                                    .withValues( values )
//                                    .build()
//                    );

                }
                cursor.close();

            }

            try {

                mContext.getContentResolver().applyBatch( MythtvProvider.AUTHORITY, ops );

                Log.v( TAG, "deleteRecordedPrograms : exit" );
                return new ProgramsDeletedEvent( event.getDetails() );

            } catch( Exception e ) {

                Log.e( TAG, "deleteRecordedPrograms : error processing programs", e );

            }

        }

        Log.v(TAG, "deleteRecordedPrograms : error, delete failed");
        return ProgramsDeletedEvent.deletionFailed();
    }

    @Override
    public ProgramDetailsEvent requestProgram( RequestRecordedProgramEvent event ) {

        if( null == event.getRecordedId() && null == event.getChanId() && null == event.getStartTime() && null == event.getFilename() ) {

            return ProgramDetailsEvent.notFound( event.getChanId(), event.getStartTime() );
        }

        Program program = null;

        String[] projection = new String[]{ "rowid as " + ProgramConstants._ID, ProgramConstants.FIELD_PROGRAM_START_TIME, ProgramConstants.FIELD_PROGRAM_END_TIME, ProgramConstants.FIELD_PROGRAM_TITLE, ProgramConstants.FIELD_PROGRAM_SUB_TITLE, ProgramConstants.FIELD_PROGRAM_INETREF, ProgramConstants.FIELD_PROGRAM_DESCRIPTION, ProgramConstants.FIELD_PROGRAM_SEASON, ProgramConstants.FIELD_PROGRAM_EPISODE, ProgramConstants.FIELD_CHANNEL_CHAN_ID, ProgramConstants.FIELD_CHANNEL_CALLSIGN, ProgramConstants.FIELD_CHANNEL_CHAN_NUM, ProgramConstants.FIELD_RECORDING_RECORDED_ID, ProgramConstants.FIELD_RECORDING_STATUS, ProgramConstants.FIELD_RECORDING_START_TS, ProgramConstants.FIELD_RECORDING_RECORD_ID, ProgramConstants.FIELD_PROGRAM_FILE_NAME, ProgramConstants.FIELD_PROGRAM_HOSTNAME, ProgramConstants.FIELD_RECORDING_REC_GROUP, ProgramConstants.FIELD_RECORDING_STORAGE_GROUP, ProgramConstants.FIELD_CAST_MEMBER_NAMES };
        String selection = ProgramConstants.FIELD_PROGRAM_TYPE + " = ? AND " + ProgramConstants.FIELD_RECORDING_STATUS + " != -2";

        List<String> selectionArgs = new ArrayList<>();
        selectionArgs.add( ProgramConstants.ProgramType.RECORDED.name() );

        String sort = ProgramConstants.FIELD_PROGRAM_END_TIME + " desc";

        if( null != event.getRecordedId() ) {

            selection += " AND " + ProgramConstants.FIELD_RECORDING_RECORD_ID + " = ?";
            selectionArgs.add( String.valueOf( event.getRecordedId() ) );

        }

        if( null != event.getChanId() && null != event.getStartTime() ) {

            selection += " AND " + ProgramConstants.FIELD_CHANNEL_CHAN_ID + " = ? AND " + ProgramConstants.FIELD_RECORDING_START_TS + " = ?";
            selectionArgs.add( String.valueOf( event.getChanId() ) );
            selectionArgs.add( String.valueOf(event.getStartTime().getMillis()) );

        }

        if( null != event.getFilename() && !"".equals( event.getFilename() ) ) {

            selection += " AND " + ProgramConstants.FIELD_PROGRAM_FILE_NAME + " = ?";
            selectionArgs.add( event.getFilename() );

        }

        Cursor cursor = mContext.getContentResolver().query(ProgramConstants.CONTENT_URI, projection, selection, selectionArgs.toArray(new String[selectionArgs.size()]), sort);
        while( cursor.moveToNext() ) {

            program = convertCursorToProgram( cursor );

        }
        cursor.close();

        if( null != program ) {

            return new ProgramDetailsEvent( program.getChannel().getChanId(), program.getRecording().getStartTs(), program.toDetails() );
        }

        return ProgramDetailsEvent.notFound(event.getChanId(), event.getStartTime());
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
    public AllProgramsEvent requestAllRecordingGroups( RequestAllRecordedProgramsEvent event ) {

        List<Program> programs = new ArrayList<>();

        String[] projection = new String[] { "rowid as " + ProgramConstants._ID, ProgramConstants.FIELD_PROGRAM_START_TIME, ProgramConstants.FIELD_PROGRAM_END_TIME, ProgramConstants.FIELD_PROGRAM_TITLE, ProgramConstants.FIELD_PROGRAM_SUB_TITLE, ProgramConstants.FIELD_PROGRAM_INETREF, ProgramConstants.FIELD_PROGRAM_DESCRIPTION, ProgramConstants.FIELD_PROGRAM_SEASON, ProgramConstants.FIELD_PROGRAM_EPISODE, ProgramConstants.FIELD_CHANNEL_CHAN_ID, ProgramConstants.FIELD_CHANNEL_CALLSIGN, ProgramConstants.FIELD_CHANNEL_CHAN_NUM, ProgramConstants.FIELD_RECORDING_RECORDED_ID, ProgramConstants.FIELD_RECORDING_STATUS, ProgramConstants.FIELD_RECORDING_START_TS, ProgramConstants.FIELD_RECORDING_RECORD_ID, ProgramConstants.FIELD_PROGRAM_FILE_NAME, ProgramConstants.FIELD_PROGRAM_HOSTNAME, ProgramConstants.FIELD_RECORDING_REC_GROUP, ProgramConstants.FIELD_RECORDING_STORAGE_GROUP, ProgramConstants.FIELD_CAST_MEMBER_NAMES };
        String selection = ProgramConstants.FIELD_PROGRAM_TYPE + " = ? AND " + ProgramConstants.FIELD_RECORDING_STATUS + " != -2";

        List<String> selectionArgs = new ArrayList<>();
        selectionArgs.add(ProgramConstants.ProgramType.RECORDED.name());

        String sort = ProgramConstants.FIELD_RECORDING_REC_GROUP;

        Cursor cursor = mContext.getContentResolver().query( Uri.withAppendedPath( ProgramConstants.CONTENT_URI, "/recording_groups" ), projection, selection, selectionArgs.toArray( new String[ selectionArgs.size() ] ), sort );
        while( cursor.moveToNext() ) {

            programs.add( convertCursorToProgram( cursor ) );

        }
        cursor.close();

        List<ProgramDetails> details = new ArrayList<>();
        if( !programs.isEmpty() ) {

            for( Program program : programs ) {

                details.add( program.toDetails() );

            }

        }

        return new AllProgramsEvent( details );
    }

    @Override
    public AllProgramsEvent requestAllTitles( RequestAllRecordedProgramsEvent event ) {

        List<Program> programs = new ArrayList<>();

        String[] projection = new String[] { "rowid as " + ProgramConstants._ID, ProgramConstants.FIELD_PROGRAM_START_TIME, ProgramConstants.FIELD_PROGRAM_END_TIME, ProgramConstants.FIELD_PROGRAM_TITLE, ProgramConstants.FIELD_PROGRAM_SUB_TITLE, ProgramConstants.FIELD_PROGRAM_INETREF, ProgramConstants.FIELD_PROGRAM_DESCRIPTION, ProgramConstants.FIELD_PROGRAM_SEASON, ProgramConstants.FIELD_PROGRAM_EPISODE, ProgramConstants.FIELD_CHANNEL_CHAN_ID, ProgramConstants.FIELD_CHANNEL_CALLSIGN, ProgramConstants.FIELD_CHANNEL_CHAN_NUM, ProgramConstants.FIELD_RECORDING_RECORDED_ID, ProgramConstants.FIELD_RECORDING_STATUS, ProgramConstants.FIELD_RECORDING_START_TS, ProgramConstants.FIELD_RECORDING_RECORD_ID, ProgramConstants.FIELD_PROGRAM_FILE_NAME, ProgramConstants.FIELD_PROGRAM_HOSTNAME, ProgramConstants.FIELD_RECORDING_REC_GROUP, ProgramConstants.FIELD_RECORDING_STORAGE_GROUP, ProgramConstants.FIELD_CAST_MEMBER_NAMES };
        String selection = ProgramConstants.FIELD_PROGRAM_TYPE + " = ? AND " + ProgramConstants.FIELD_RECORDING_STATUS + " != -2";

        List<String> selectionArgs = new ArrayList<>();
        selectionArgs.add( ProgramConstants.ProgramType.RECORDED.name() );

        if( null != event.getRecordingGroup() && !"".equals( event.getRecordingGroup() )  ) {
            selection += " AND " + ProgramConstants.FIELD_RECORDING_REC_GROUP + " = ?";
            selectionArgs.add( event.getRecordingGroup() );
        }

        String sort = ProgramConstants.FIELD_PROGRAM_TITLE_SORT;

        Cursor cursor = mContext.getContentResolver().query( Uri.withAppendedPath( ProgramConstants.CONTENT_URI, "/titles" ), projection, selection, selectionArgs.toArray( new String[ selectionArgs.size() ] ), sort );
        while( cursor.moveToNext() ) {

            programs.add( convertCursorToProgram( cursor ) );

        }
        cursor.close();

        List<ProgramDetails> details = new ArrayList<>();
        if( !programs.isEmpty() ) {

            for( Program program : programs ) {

                details.add( program.toDetails() );

            }

        }

        return new AllProgramsEvent( details );
    }

    @Override
    public AllTitleInfosEvent requestAllTitleInfos( RequestAllTitleInfosEvent event ) {
        Log.v( TAG, "requestAllTitleInfos : enter" );

        List<TitleInfoDetails> titleInfosDetails = new ArrayList<>();
        List<TitleInfo> titleInfos = new ArrayList<>();

        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sort = TitleInfoConstants.FIELD_SORT + ", " + TitleInfoConstants.FIELD_TITLE_SORT;

        String limit = " ";
        if( null != event.getLimit() && -1 != event.getLimit() ) {

            limit = " LIMIT " + event.getLimit();

            if( null != event.getOffset() && -1 != event.getOffset() ) {

                limit = " LIMIT " + event.getOffset() + "," + event.getLimit();

            }

        }
        sort += limit;

        Cursor cursor = mContext.getContentResolver().query( TitleInfoConstants.CONTENT_URI, projection, selection, selectionArgs, sort );
        while( cursor.moveToNext() ) {

            TitleInfo titleInfo = new TitleInfo();
            titleInfo.setId(cursor.getLong(cursor.getColumnIndex(TitleInfoConstants._ID)));
            titleInfo.setTitle(cursor.getString(cursor.getColumnIndex(TitleInfoConstants.FIELD_TITLE)));
            titleInfo.setInetref(cursor.getString(cursor.getColumnIndex(TitleInfoConstants.FIELD_INETREF)));

            titleInfos.add(titleInfo);
            //Log.v(TAG, "requestAllTitleInfos : cursor iteration, titleInfo=" + titleInfo);

        }
        cursor.close();

        if( !titleInfos.isEmpty() ) {

            for( TitleInfo titleInfo : titleInfos ) {

                titleInfosDetails.add( titleInfo.toDetails() );

            }

        }

        Log.v(TAG, "requestAllTitleInfos : exit");
        return new AllTitleInfosEvent( titleInfosDetails );
    }

    @Override
    public TitleInfosUpdatedEvent updateTitleInfos( UpdateTitleInfosEvent event ) {

        String[] projection = new String[]{ TitleInfoConstants.FIELD_TITLE, TitleInfoConstants._ID };
        String selection = null;
        String[] selectionArgs = null;

        Map<String, Long> titleInfoIds = new HashMap<>();

        Cursor cursor = mContext.getContentResolver().query(TitleInfoConstants.CONTENT_URI, projection, selection, selectionArgs, null);
        while( cursor.moveToNext() ) {

            titleInfoIds.put( cursor.getString( cursor.getColumnIndex( TitleInfoConstants.FIELD_TITLE ) ), cursor.getLong( cursor.getColumnIndex( TitleInfoConstants._ID ) ) );

        }
        cursor.close();

        if( null != event.getDetails() && !event.getDetails().isEmpty() ) {

            ArrayList<ContentProviderOperation> ops = new ArrayList<>();

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
                values.put( TitleInfoConstants.FIELD_TITLE_SORT, Utils.removeArticles( titleInfo.getTitle() ).toUpperCase() );

                if( titleInfo.getTitle().equals( "All Recordings" ) ) {

                    values.put( TitleInfoConstants.FIELD_SORT, 0 );

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

            if( !titleInfoIds.isEmpty() ) {

                for( Long id : titleInfoIds.values() ) {

                    ops.add(
                            ContentProviderOperation
                                    .newDelete( ContentUris.withAppendedId( TitleInfoConstants.CONTENT_URI, id ) )
                                    .build()
                    );

                }

            }

            try {

                mContext.getContentResolver().applyBatch( MythtvProvider.AUTHORITY, ops );

                return new TitleInfosUpdatedEvent( event.getDetails() );

            } catch( Exception e ) {

                Log.e( TAG, "updateTitleInfos : error processing title infos", e );

            }

        }

        return TitleInfosUpdatedEvent.notUpdated();
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

            return startTs == that.startTs;
        }

        @Override
        public int hashCode() {
            int result = chanId;
            result = 31 * result + (int) (startTs ^ (startTs >>> 32));
            return result;
        }

        @Override
        public String toString() {
            return "ProgramKey{" +
                    "chanId=" + chanId +
                    ", startTs=" + startTs +
                    '}';
        }

    }

    private Program convertCursorToProgram( Cursor cursor ) {

        Program program = new Program();
        program.setStartTime( new DateTime( cursor.getLong( cursor.getColumnIndex( ProgramConstants.FIELD_PROGRAM_START_TIME ) ) ) );
        program.setEndTime( new DateTime( cursor.getLong( cursor.getColumnIndex(ProgramConstants.FIELD_PROGRAM_END_TIME ) ) ) );
        program.setTitle( cursor.getString( cursor.getColumnIndex( ProgramConstants.FIELD_PROGRAM_TITLE ) ) );
        program.setSubTitle( cursor.getString( cursor.getColumnIndex( ProgramConstants.FIELD_PROGRAM_SUB_TITLE ) ) );
        program.setInetref( cursor.getString( cursor.getColumnIndex( ProgramConstants.FIELD_PROGRAM_INETREF ) ) );
        program.setDescription( cursor.getString( cursor.getColumnIndex( ProgramConstants.FIELD_PROGRAM_DESCRIPTION ) ) );
        program.setFileName( cursor.getString( cursor.getColumnIndex( ProgramConstants.FIELD_PROGRAM_FILE_NAME ) ) );
        program.setHostName( cursor.getString( cursor.getColumnIndex( ProgramConstants.FIELD_PROGRAM_HOSTNAME ) ) );
        program.setSeason( cursor.getInt( cursor.getColumnIndex( ProgramConstants.FIELD_PROGRAM_SEASON ) ) );
        program.setEpisode( cursor.getInt( cursor.getColumnIndex( ProgramConstants.FIELD_PROGRAM_EPISODE ) ) );

        ChannelInfo channel = new ChannelInfo();
        channel.setChanId( cursor.getInt( cursor.getColumnIndex( ProgramConstants.FIELD_CHANNEL_CHAN_ID ) ) );
        channel.setCallSign( cursor.getString( cursor.getColumnIndex( ProgramConstants.FIELD_CHANNEL_CALLSIGN ) ) );
        channel.setChanNum( cursor.getString( cursor.getColumnIndex( ProgramConstants.FIELD_CHANNEL_CHAN_NUM ) ) );
        program.setChannel( channel );

        RecordingInfo recording = new RecordingInfo();
        recording.setRecordedId( cursor.getInt( cursor.getColumnIndex( ProgramConstants.FIELD_RECORDING_RECORDED_ID ) ) );
        recording.setStatus( cursor.getInt( cursor.getColumnIndex( ProgramConstants.FIELD_RECORDING_STATUS ) ) );
        recording.setStartTs( new DateTime( cursor.getLong( cursor.getColumnIndex( ProgramConstants.FIELD_RECORDING_START_TS ) ) ) );
        recording.setRecordId( cursor.getInt( cursor.getColumnIndex( ProgramConstants.FIELD_RECORDING_RECORD_ID ) ) );
        recording.setRecGroup( cursor.getString( cursor.getColumnIndex( ProgramConstants.FIELD_RECORDING_REC_GROUP ) ) );
        recording.setStorageGroup( cursor.getString( cursor.getColumnIndex( ProgramConstants.FIELD_RECORDING_STORAGE_GROUP ) ) );
        program.setRecording( recording );

        String castMembers = cursor.getString( cursor.getColumnIndex( ProgramConstants.FIELD_CAST_MEMBER_NAMES ) );
        if( null != castMembers && !"".equals( castMembers ) ) {

            List<CastMember> castMemberNames = new ArrayList<>();

            StringTokenizer st = new StringTokenizer( castMembers, "|" );
            while( st.hasMoreTokens() ) {

                CastMember member = new CastMember();
                member.setName( st.nextToken() );
                castMemberNames.add( member );

            }
            program.setCastMembers( castMemberNames );

        }

        return program;
    }

}
