package org.mythtv.android.library.core.utils;

import android.os.AsyncTask;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.events.content.AddLiveStreamEvent;

/**
 * Created by dmfrey on 3/1/15.
 */
public class AddRecordingLiveStreamAsyncTask extends AsyncTask<Program, Void, Void> {

    @Override
    protected Void doInBackground( Program... params ) {

        Program program = params[ 0 ];

        MainApplication.getInstance().getContentService().addLiveStream( new AddLiveStreamEvent( program.getRecording().getStorageGroup(), program.getFileName(), program.getHostName(), 0, 1280, 720, null, null, null ) );

        return null;
    }

}
