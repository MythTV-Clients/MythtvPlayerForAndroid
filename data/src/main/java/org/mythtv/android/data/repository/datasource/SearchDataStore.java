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

import org.mythtv.android.data.entity.SearchResultEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;

import java.util.Collection;
import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 10/8/15.
 */
public interface SearchDataStore {

    Observable<List<SearchResultEntity>> search( String searchString );

    void refreshTitleInfoData( Collection<TitleInfoEntity> titleInfoEntityCollection );

    void refreshRecordedProgramData( Collection<SearchResultEntity> searchResultEntityCollection );

    void refreshVideoData( Collection<SearchResultEntity> searchResultEntityCollection );

}
