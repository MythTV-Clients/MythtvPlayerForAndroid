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

import org.mythtv.android.model.TvCategoryModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link TvCategoryModel}.
 *
 * Created by dmfrey on 1/28/16.
 */
public interface TvCategoryListView extends LoadDataView {

    /**
     * Render a tvCategory list in the UI.
     *
     * @param tvCategoryModelCollection The collection of {@link TvCategoryModel} that will be shown.
     */
    void renderTvCategoryList( Collection<TvCategoryModel> tvCategoryModelCollection);

    /**
     * View a {@link TvCategoryModel} details.
     *
     * @param tvCategoryModel The tvCategory that will be shown.
     */
    void viewTvCategory( TvCategoryModel tvCategoryModel );

}
