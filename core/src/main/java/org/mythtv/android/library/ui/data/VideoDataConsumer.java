package org.mythtv.android.library.ui.data;

import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.library.core.domain.video.Video;

import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public interface VideoDataConsumer extends ErrorHandler {

    public void setVideos( List<Video> videos );

}
