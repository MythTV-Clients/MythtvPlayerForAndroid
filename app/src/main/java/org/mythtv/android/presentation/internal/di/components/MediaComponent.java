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

package org.mythtv.android.presentation.internal.di.components;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.internal.di.modules.ActivityModule;
import org.mythtv.android.presentation.internal.di.modules.EncodersModule;
import org.mythtv.android.presentation.internal.di.modules.MediaItemModule;
import org.mythtv.android.presentation.internal.di.modules.MediaItemsModule;
import org.mythtv.android.presentation.internal.di.modules.NetModule;
import org.mythtv.android.presentation.internal.di.modules.SearchResultsModule;
import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;
import org.mythtv.android.presentation.internal.di.modules.TitleInfosModule;
import org.mythtv.android.presentation.view.fragment.phone.EncoderListFragment;
import org.mythtv.android.presentation.view.fragment.phone.MediaItemDetailsFragment;
import org.mythtv.android.presentation.view.fragment.phone.MediaItemSearchResultListFragment;
import org.mythtv.android.presentation.view.fragment.phone.SeriesListFragment;
import org.mythtv.android.presentation.view.fragment.tv.CategoryListFragment;
import org.mythtv.android.presentation.view.fragment.tv.TvSearchResultListFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, SharedPreferencesModule.class, NetModule.class, MediaItemsModule.class, MediaItemModule.class, TitleInfosModule.class, EncodersModule.class, SearchResultsModule.class } )
public interface MediaComponent {

    // Phone fragments
    void inject( SeriesListFragment seriesListFragment );
    void inject( MediaItemDetailsFragment mediaItemDetailsFragment );
    void inject( org.mythtv.android.presentation.view.fragment.phone.MediaItemListFragment mediaItemListFragment );
    void inject( MediaItemSearchResultListFragment mediaItemSearchResultListFragment );
    void inject( EncoderListFragment encoderListFragment );

    // TV fragments
    void inject( CategoryListFragment tvCategoryListFragment );
    void inject( org.mythtv.android.presentation.view.fragment.tv.MediaItemListFragment mediaItemListFragment);
    void inject( TvSearchResultListFragment tvSearchResultListFragment );

}
