package org.mythtv.android.tv.internal.di.components;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.internal.di.modules.EncodersModule;
import org.mythtv.android.presentation.internal.di.modules.LiveStreamModule;
import org.mythtv.android.presentation.internal.di.modules.ProgramModule;
import org.mythtv.android.presentation.internal.di.modules.ProgramsModule;
import org.mythtv.android.presentation.internal.di.modules.RecentProgramsModule;
import org.mythtv.android.presentation.internal.di.modules.RecordedProgramWatchedStatusModule;
import org.mythtv.android.presentation.internal.di.modules.TitleInfosModule;
import org.mythtv.android.presentation.internal.di.modules.UpcomingProgramsModule;
import org.mythtv.android.tv.internal.di.modules.ActivityModule;
import org.mythtv.android.tv.view.fragment.CategoryListFragment;
import org.mythtv.android.tv.view.fragment.RecordingsFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 *
 * Created by dmfrey on 8/30/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, TitleInfosModule.class, ProgramsModule.class, ProgramModule.class, UpcomingProgramsModule.class, RecentProgramsModule.class, LiveStreamModule.class, EncodersModule.class, RecordedProgramWatchedStatusModule.class } )
public interface DvrComponent {

    void inject( CategoryListFragment tvCategoryListFragment );
    void inject( RecordingsFragment tvRecordingsFragment );

}
