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
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.events.content.AddLiveStreamEvent;

/**
 * Created by dmfrey on 3/1/15.
 */
public class AddRecordingLiveStreamAsyncTask extends AsyncTask<Program, Void, Void> {

    @Override
    protected Void doInBackground( Program... params ) {

        Program program = params[ 0 ];

        MainApplication.getInstance().getContentService().addLiveStream( new AddLiveStreamEvent( program.getRecording().getStorageGroup(), program.getFileName(), program.getHostName(), 0, MainApplication.getInstance().getVideoWidth(), MainApplication.getInstance().getVideoHeight(), MainApplication.getInstance().getVideoBitrate(), MainApplication.getInstance().getAudioBitrate(), null ) );

        return null;
    }

}
