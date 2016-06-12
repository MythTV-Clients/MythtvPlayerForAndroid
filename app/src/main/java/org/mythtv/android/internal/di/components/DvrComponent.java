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

package org.mythtv.android.internal.di.components;

import org.mythtv.android.view.fragment.phone.ProgramListFragment;
import org.mythtv.android.view.fragment.phone.RecentListFragment;
import org.mythtv.android.view.fragment.phone.TitleInfoListFragment;
import org.mythtv.android.view.fragment.phone.UpcomingListFragment;
import org.mythtv.android.internal.di.PerActivity;
import org.mythtv.android.internal.di.modules.ActivityModule;
import org.mythtv.android.internal.di.modules.EncodersModule;
import org.mythtv.android.internal.di.modules.LiveStreamModule;
import org.mythtv.android.internal.di.modules.NetModule;
import org.mythtv.android.internal.di.modules.ProgramModule;
import org.mythtv.android.internal.di.modules.ProgramsModule;
import org.mythtv.android.internal.di.modules.RecentProgramsModule;
import org.mythtv.android.internal.di.modules.RecordedProgramWatchedStatusModule;
import org.mythtv.android.internal.di.modules.SharedPreferencesModule;
import org.mythtv.android.internal.di.modules.TitleInfosModule;
import org.mythtv.android.internal.di.modules.UpcomingProgramsModule;
import org.mythtv.android.view.fragment.phone.EncoderListFragment;
import org.mythtv.android.view.fragment.phone.ProgramDetailsFragment;
import org.mythtv.android.view.fragment.tv.CategoryListFragment;
import org.mythtv.android.view.fragment.tv.RecordingsFragment;

import dagger.Component;

/**
 * A scope {@link org.mythtv.android.internal.di.PerActivity} component.
 * Injects user specific Fragments.
 *
 * Created by dmfrey on 8/30/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, SharedPreferencesModule.class, NetModule.class, TitleInfosModule.class, ProgramsModule.class, ProgramModule.class, UpcomingProgramsModule.class, RecentProgramsModule.class, LiveStreamModule.class, EncodersModule.class, RecordedProgramWatchedStatusModule.class } )
public interface DvrComponent {

    // Phone/Tablet fragments
    void inject( TitleInfoListFragment titleInfoListFragment );
    void inject( ProgramListFragment programsListFragment );
    void inject( ProgramDetailsFragment programDetailsFragment );
    void inject( RecentListFragment recentListFragment );
    void inject( UpcomingListFragment upcomingListFragment );
    void inject( EncoderListFragment encoderListFragment );

    // TV fragments
    void inject( CategoryListFragment tvCategoryListFragment );
    void inject( RecordingsFragment tvRecordingsFragment );

}
