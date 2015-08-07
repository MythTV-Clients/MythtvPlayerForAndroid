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

package org.mythtv.android.library.persistence.service;

import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.DeleteVideosEvent;
import org.mythtv.android.library.events.video.DeleteVideoEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosForParentEvent;
import org.mythtv.android.library.events.video.SearchVideosEvent;
import org.mythtv.android.library.events.video.UpdateVideosEvent;
import org.mythtv.android.library.events.video.VideoDetailsEvent;
import org.mythtv.android.library.events.video.VideoDeletedEvent;
import org.mythtv.android.library.events.video.VideosDeletedEvent;
import org.mythtv.android.library.events.video.VideosUpdatedEvent;
import org.mythtv.android.library.events.video.RequestVideoEvent;

/*
 * Created by dmfrey on 4/15/15.
 */
public interface VideoPersistenceService {

    AllVideosEvent requestAllVideos( RequestAllVideosEvent event );

    AllVideosEvent requestAllVideoTvTitles( RequestAllVideosEvent event );

    AllVideosEvent requestAllVideoTvTitleSeasons( RequestAllVideosEvent event );

    AllVideosEvent requestAllVideosForParent( RequestAllVideosForParentEvent event );

    AllVideosEvent searchVideos( SearchVideosEvent event );

    VideosUpdatedEvent updateVideos( UpdateVideosEvent event );

    VideosDeletedEvent deleteVideos( DeleteVideosEvent event );

    VideoDetailsEvent requestVideo( RequestVideoEvent event );

    VideoDeletedEvent deleteVideo( DeleteVideoEvent event );

}
