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

package org.mythtv.android.data.repository.datasource;

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;

import java.util.List;

import rx.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/9/15.
 */
public interface VideoDataStore {

    Observable<List<VideoMetadataInfoEntity>> getVideos( final String folder, final String sort, final boolean descending, final int startIndex, final int count );

    Observable<List<VideoMetadataInfoEntity>> getCategory( String category );

    Observable<List<VideoMetadataInfoEntity>> getSeriesInCategory( String category, String series );

    Observable<VideoMetadataInfoEntity> getVideoById( final int id );

    Observable<VideoMetadataInfoEntity> getVideoByFilename( final String filename );

    Observable<Boolean> updateWatchedStatus( final int videoId, final boolean watched );

}
