package org.mythtv.android.app.internal.di.components;

import org.mythtv.android.app.view.fragment.ProgramListFragment;
import org.mythtv.android.app.view.fragment.RecentListFragment;
import org.mythtv.android.app.view.fragment.TitleInfoListFragment;
import org.mythtv.android.app.view.fragment.UpcomingListFragment;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.app.internal.di.modules.ActivityModule;
import org.mythtv.android.app.internal.di.modules.EncodersModule;
import org.mythtv.android.app.internal.di.modules.LiveStreamModule;
import org.mythtv.android.app.internal.di.modules.ProgramModule;
import org.mythtv.android.app.internal.di.modules.ProgramsModule;
import org.mythtv.android.app.internal.di.modules.RecentProgramsModule;
import org.mythtv.android.app.internal.di.modules.RecordedProgramWatchedStatusModule;
import org.mythtv.android.app.internal.di.modules.TitleInfosModule;
import org.mythtv.android.app.internal.di.modules.UpcomingProgramsModule;
import org.mythtv.android.app.view.fragment.EncoderListFragment;
import org.mythtv.android.app.view.fragment.ProgramDetailsFragment;

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

    void inject( TitleInfoListFragment titleInfoListFragment );
    void inject( ProgramListFragment programsListFragment );
    void inject( ProgramDetailsFragment programDetailsFragment );
    void inject( RecentListFragment recentListFragment );
    void inject( UpcomingListFragment upcomingListFragment );
    void inject( EncoderListFragment encoderListFragment );

}
