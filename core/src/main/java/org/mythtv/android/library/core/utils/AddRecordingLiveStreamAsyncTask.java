package org.mythtv.android.library.core.utils;

import android.os.AsyncTask;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.events.content.AddRecordingLiveStreamEvent;

/**
 * Created by dmfrey on 3/1/15.
 */
public class AddRecordingLiveStreamAsyncTask extends AsyncTask<Program, Void, Void> {

    @Override
    protected Void doInBackground( Program... params ) {

        Program program = params[ 0 ];

        MainApplication.getInstance().getContentService().addRecordingLiveStream( new AddRecordingLiveStreamEvent( program.getRecording().getRecordedId(), program.getChannel().getChanId(), program.getRecording().getStartTs(), 0, 1280, 720, null, null, null ) );

        return null;
    }

}
