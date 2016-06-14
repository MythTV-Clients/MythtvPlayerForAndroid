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

import org.mythtv.android.model.ProgramModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link ProgramModel}.
 *
 * Created by dmfrey on 8/31/15.
 */
public interface ProgramListView extends LoadDataView {

    /**
     * Render a program list in the UI.
     *
     * @param programModelCollection The collection of {@link ProgramModel} that will be shown.
     */
    void renderProgramList( Collection<ProgramModel> programModelCollection );

    /**
     * View a {@link ProgramModel} details.
     *
     * @param programModel The program that will be shown.
     */
    void viewProgram( ProgramModel programModel );

}
