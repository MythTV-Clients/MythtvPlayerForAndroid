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

package org.mythtv.android.presentation.view;

import org.mythtv.android.presentation.model.TitleInfoModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link TitleInfoModel}.
 *
 * Created by dmfrey on 9/9/15.
 */
public interface TitleInfoListView extends LoadDataView {

    /**
     * Render a titleInfo list in the UI.
     *
     * @param titleInfoModelCollection The collection of {@link TitleInfoModel} that will be shown.
     */
    void renderTitleInfoList( Collection<TitleInfoModel> titleInfoModelCollection );

    /**
     * View a {@link TitleInfoModel} details.
     *
     * @param titleInfoModel The titleInfo that will be shown.
     */
    void viewTitleInfo( TitleInfoModel titleInfoModel );

}
