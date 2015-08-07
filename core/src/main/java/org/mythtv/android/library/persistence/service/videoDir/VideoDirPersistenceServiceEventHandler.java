package org.mythtv.android.library.persistence.service.videoDir;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.events.videoDir.AllVideoDirsEvent;
import org.mythtv.android.library.events.videoDir.RequestAllVideoDirsEvent;
import org.mythtv.android.library.events.videoDir.VideoDirDetails;
import org.mythtv.android.library.persistence.domain.videoDir.VideoDir;
import org.mythtv.android.library.persistence.domain.videoDir.VideoDirConstants;
import org.mythtv.android.library.persistence.service.VideoDirPersistenceService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 8/3/15.
 */
public class VideoDirPersistenceServiceEventHandler implements VideoDirPersistenceService {

    private static final String TAG = VideoDirPersistenceServiceEventHandler.class.getSimpleName();

    Context mContext;

    public VideoDirPersistenceServiceEventHandler() {

        mContext = MainApplication.getInstance().getApplicationContext();

    }

    @Override
    public AllVideoDirsEvent requestAllVideoDirs( RequestAllVideoDirsEvent event ) {
        Log.v( TAG, "requestAllVideoDirs : enter" );
        Log.v( TAG, "requestAllVideoDirs : parent=" + event.getParent() );

        List<VideoDir> videoDirs = new ArrayList<>();

        String[] projection = null;
        String selection = null;
        List<String> selectionArgs = new ArrayList<>();

        if( null != event.getParent() ) {

            selection = VideoDirConstants.FIELD_VIDEO_DIR_PARENT + " = ?";
            selectionArgs.add( event.getParent() );

        } else {

            selection = VideoDirConstants.FIELD_VIDEO_DIR_PARENT + " IS NULL";

        }

        String sort = VideoDirConstants.FIELD_VIDEO_DIR_NAME;

        Cursor cursor = mContext.getContentResolver().query( VideoDirConstants.CONTENT_URI, projection, selection, selectionArgs.isEmpty() ? null : selectionArgs.toArray( new String[ selectionArgs.size() ] ), sort );
        while( cursor.moveToNext() ) {
//            Log.v( TAG, "requestAllVideoDirs : video dir iteration" );

            videoDirs.add( convertCursorToVideoDir( cursor ) );

        }
        cursor.close();

        List<VideoDirDetails> details = new ArrayList<>();
        if( !videoDirs.isEmpty() ) {
            Log.v( TAG, "requestAllVideoDirs : videoDirs loaded from db " + videoDirs.size() );

            for( VideoDir videoDir : videoDirs ) {

                details.add( videoDir.toDetails() );

            }

        }

        Log.v( TAG, "requestAllVideoDirs : exit" );
        return new AllVideoDirsEvent( details );
    }

    private VideoDir convertCursorToVideoDir( Cursor cursor ) {

        VideoDir videoDir = new VideoDir();
        videoDir.setId( cursor.getLong( cursor.getColumnIndex( VideoDirConstants._ID ) ) );
        videoDir.setPath( cursor.getString( cursor.getColumnIndex( VideoDirConstants.FIELD_VIDEO_DIR_PATH ) ) );
        videoDir.setName( cursor.getString( cursor.getColumnIndex( VideoDirConstants.FIELD_VIDEO_DIR_NAME ) ) );
        videoDir.setParent( cursor.getString( cursor.getColumnIndex( VideoDirConstants.FIELD_VIDEO_DIR_PARENT ) ) );

        return videoDir;
    }

}
