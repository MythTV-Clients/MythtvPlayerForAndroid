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

package org.mythtv.android.view;

import org.mythtv.android.model.SearchResultModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link SearchResultModel}.
 *
 * Created by dmfrey on 10/14/15.
 */
public interface SearchResultListView extends LoadDataView {

    /**
     * Render a searchResult list in the UI.
     *
     * @param searchResultModelCollection The collection of {@link SearchResultModel} that will be shown.
     */
    void renderSearchResultList( Collection<SearchResultModel> searchResultModelCollection);

    /**
     * View a {@link SearchResultModel} details.
     *
     * @param searchResultModel The search result that will be shown.
     */
    void viewSearchResult( SearchResultModel searchResultModel );

}
