package org.mythtv.android.presentation.internal.di.components;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.internal.di.modules.ActivityModule;
import org.mythtv.android.presentation.internal.di.modules.ProgramModule;
import org.mythtv.android.presentation.internal.di.modules.ProgramsModule;
import org.mythtv.android.presentation.internal.di.modules.TitleInfosModule;
import org.mythtv.android.presentation.model.TitleInfoModel;
import org.mythtv.android.presentation.view.fragment.ProgramDetailsFragment;
import org.mythtv.android.presentation.view.fragment.ProgramListFragment;
import org.mythtv.android.presentation.view.fragment.TitleInfoListFragment;

import dagger.Component;

/**
 * A scope {@link org.mythtv.android.presentation.internal.di.PerActivity} component.
 * Injects user specific Fragments.
 *
 * Created by dmfrey on 8/30/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, TitleInfosModule.class, ProgramsModule.class, ProgramModule.class } )
public interface DvrComponent {

    void inject( TitleInfoListFragment titleInfoListFragment );

    void inject( ProgramListFragment programsListFragment );

    void inject( ProgramDetailsFragment programDetailsFragment );

}
