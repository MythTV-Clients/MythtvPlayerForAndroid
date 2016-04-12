package org.mythtv.android.presentation.internal.di.components;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.internal.di.modules.ActivityModule;
import org.mythtv.android.presentation.internal.di.modules.EncodersModule;
import org.mythtv.android.presentation.internal.di.modules.LiveStreamModule;
import org.mythtv.android.presentation.internal.di.modules.ProgramModule;
import org.mythtv.android.presentation.internal.di.modules.ProgramsModule;
import org.mythtv.android.presentation.internal.di.modules.RecentProgramsModule;
import org.mythtv.android.presentation.internal.di.modules.RecordedProgramWatchedStatusModule;
import org.mythtv.android.presentation.internal.di.modules.TitleInfosModule;
import org.mythtv.android.presentation.internal.di.modules.UpcomingProgramsModule;
import org.mythtv.android.presentation.view.fragment.AppEncoderListFragment;
import org.mythtv.android.presentation.view.fragment.AppProgramDetailsFragment;
import org.mythtv.android.presentation.view.fragment.AppProgramListFragment;
import org.mythtv.android.presentation.view.fragment.AppRecentListFragment;
import org.mythtv.android.presentation.view.fragment.AppTitleInfoListFragment;
import org.mythtv.android.presentation.view.fragment.AppUpcomingListFragment;
import org.mythtv.android.presentation.view.fragment.TvCategoryListFragment;
import org.mythtv.android.presentation.view.fragment.TvRecordingsFragment;

import dagger.Component;

/**
 * A scope {@link org.mythtv.android.presentation.internal.di.PerActivity} component.
 * Injects user specific Fragments.
 *
 * Created by dmfrey on 8/30/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, TitleInfosModule.class, ProgramsModule.class, ProgramModule.class, UpcomingProgramsModule.class, RecentProgramsModule.class, LiveStreamModule.class, EncodersModule.class, RecordedProgramWatchedStatusModule.class } )
public interface DvrComponent {

    void inject( AppTitleInfoListFragment titleInfoListFragment );
    void inject( AppProgramListFragment programsListFragment );
    void inject( AppProgramDetailsFragment programDetailsFragment );
    void inject( AppRecentListFragment recentListFragment );
    void inject( AppUpcomingListFragment upcomingListFragment );
    void inject( AppEncoderListFragment encoderListFragment );

    void inject( TvCategoryListFragment tvCategoryListFragment );
    void inject( TvRecordingsFragment tvRecordingsFragment );

}
