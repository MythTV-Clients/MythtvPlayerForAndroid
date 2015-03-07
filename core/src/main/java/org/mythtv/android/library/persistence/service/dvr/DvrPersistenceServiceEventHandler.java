package org.mythtv.android.library.persistence.service.dvr;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.mythtv.android.library.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.events.DeleteEvent;
import org.mythtv.android.library.events.DeletedEvent;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.AllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.RemoveTitleInfoEvent;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.RequestAllTitleInfosEvent;
import org.mythtv.android.library.events.dvr.TitleInfoDetails;
import org.mythtv.android.library.events.dvr.TitleInfoRemovedEvent;
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
        return null;
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

}
