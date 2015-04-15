package org.mythtv.android.library.core.utils;

import android.os.AsyncTask;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.events.content.AddVideoLiveStreamEvent;

/**
 * Created by dmfrey on 4/14/15.
 */
public class AddVideoLiveStreamAsyncTask extends AsyncTask<Video, Void, Void> {

    @Override
    protected Void doInBackground( Video... params ) {

        Video video = params[ 0 ];

        MainApplication.getInstance().getContentService().addVideoLiveStream( new AddVideoLiveStreamEvent( video.getId(), null, MainApplication.getInstance().getVideoWidth(), MainApplication.getInstance().getVideoHeight(), MainApplication.getInstance().getVideoBitrate(), MainApplication.getInstance().getAudioBitrate(), null));

        return null;
    }

}
