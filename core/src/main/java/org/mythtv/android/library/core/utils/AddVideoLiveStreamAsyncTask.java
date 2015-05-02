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
