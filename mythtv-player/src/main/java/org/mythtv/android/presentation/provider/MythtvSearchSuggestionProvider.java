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

package org.mythtv.android.presentation.provider;

import android.content.SearchRecentSuggestionsProvider;

import org.mythtv.android.BuildConfig;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 3/15/15.
 */
public class MythtvSearchSuggestionProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = BuildConfig.APPLICATION_ID + ".presentation.provider.MythtvSearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MythtvSearchSuggestionProvider() {

        setupSuggestions( AUTHORITY, MODE );

    }

}