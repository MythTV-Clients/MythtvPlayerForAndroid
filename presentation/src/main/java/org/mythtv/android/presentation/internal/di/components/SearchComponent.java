package org.mythtv.android.presentation.internal.di.components;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.internal.di.modules.ActivityModule;
import org.mythtv.android.presentation.internal.di.modules.SearchResultsModule;
import org.mythtv.android.presentation.view.fragment.SearchResultListFragment;

import dagger.Component;

/**
 * Created by dmfrey on 10/12/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, SearchResultsModule.class } )
public interface SearchComponent {

    void inject( SearchResultListFragment searchResultListFragment );

}
