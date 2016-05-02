package org.mythtv.android.tv.view.fragment;

import android.support.v17.leanback.app.DetailsFragment;

import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;

/**
 * Created by dmfrey on 2/14/16.
 */
public abstract class AbstractBaseDetailsFragment extends DetailsFragment {

    /**
     * Get a SharedPreferences module for dependency injection.
     *
     * @return {@link org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule}
     */
    protected SharedPreferencesModule getSharedPreferencesModule() {

        return new SharedPreferencesModule( getActivity() );
    }

}
