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

package org.mythtv.android.domain.repository;

import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.Series;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 8/26/15.
 */
public interface VideoRepository {

    Observable<List<MediaItem>> getVideoList( String folder, String sort, boolean descending, int startIndex, int count );

    Observable<List<MediaItem>> getVideoListByContentType( String contentType );

    Observable<List<Series>> getVideoSeriesListByContentType( String contentType );

    Observable<List<MediaItem>> getVideoListByContentTypeAndSeries( String contentType, String series );

    Observable<MediaItem> getVideo( int id );

    Observable<Boolean> updateWatchedStatus( final int videoId, final boolean watched );

}
