/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
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

package org.mythtv.android.data.net;

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/9/15.
 */
public interface VideoApi {

    String VIDEO_LIST_BASE_URL = "/Video/GetVideoList";
    String VIDEO_BASE_URL = "/Video/GetVideo?";
    String UPDATE_VIDEO_WATCHED_STATUS_URL = "/Video/UpdateVideoWatchedStatus";

    String FOLDER_QS = "Folder=%s";
    String SORT_QS = "Sort=%s";
    String DESCENDING_QS = "Descending=%s";
    String START_INDEX_QS = "StartIndex=%s";
    String COUNT_QS = "Count=%s";
    String ID_QS = "Id=%s";

    Flowable<VideoMetadataInfoEntity> getVideoList( final String folder, final String sort, final boolean descending, final int startIndex, final int count );

    Observable<VideoMetadataInfoEntity> getVideoById( final int id );

    Observable<Boolean> updateWatchedStatus( final int videoId, final boolean watched );

}
