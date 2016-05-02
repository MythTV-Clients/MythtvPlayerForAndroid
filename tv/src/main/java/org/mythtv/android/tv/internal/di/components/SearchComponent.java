package org.mythtv.android.tv.internal.di.components;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.tv.internal.di.modules.ActivityModule;
import org.mythtv.android.tv.internal.di.modules.SearchResultsModule;
import org.mythtv.android.tv.view.fragment.SearchResultListFragment;

import dagger.Component;

/**
 * Created by dmfrey on 10/12/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, SearchResultsModule.class } )
public interface SearchComponent {

    void inject( SearchResultListFragment searchResultListFragment );

}
