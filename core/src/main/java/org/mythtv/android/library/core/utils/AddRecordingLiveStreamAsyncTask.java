package org.mythtv.android.library.core.utils;

import android.os.AsyncTask;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.events.content.AddLiveStreamEvent;
import org.mythtv.android.library.events.content.AddRecordingLiveStreamEvent;
import org.mythtv.services.api.ApiVersion;

/**
 * Created by dmfrey on 3/1/15.
 */
public class AddRecordingLiveStreamAsyncTask extends AsyncTask<Program, Void, Void> {

    @Override
    protected Void doInBackground( Program... params ) {

        Program program = params[ 0 ];

//        switch( MainApplication.getInstance().getApiVersion() ) {
//
//            case v027 :
//
//                MainApplication.getInstance().getContentService().addLiveStream( new AddLiveStreamEvent( program.getRecording().getStorageGroup(), program.getFileName(), program.getHostName(), 0, 1280, 720, null, null, null ) );
//                break;
//
//            default :
//
//                MainApplication.getInstance().getContentService().addLiveStream( new AddLiveStreamEvent( program.getRecording().getStorageGroup(), program.getFileName(), program.getHostName(), 0, 1280, 720, null, null, null ) );
//                MainApplication.getInstance().getContentService().addRecordingLiveStream( new AddRecordingLiveStreamEvent( program.getRecording().getRecordedId(), program.getChannel().getChanId(), program.getRecording().getStartTs(), 0, 1280, 720, null, null, null ) );
//                break;
//        }

        MainApplication.getInstance().getContentService().addLiveStream( new AddLiveStreamEvent( program.getRecording().getStorageGroup(), program.getFileName(), program.getHostName(), 0, 1280, 720, null, null, null ) );

        return null;
    }

}
