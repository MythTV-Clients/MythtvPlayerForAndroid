package org.mythtv.android.app.internal.di.components;

import android.content.SharedPreferences;

import org.mythtv.android.app.internal.di.modules.ApplicationModule;
import org.mythtv.android.app.view.activity.AbstractBaseActivity;
import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dmfrey on 5/9/16.
 */
@Singleton
@Component( modules = { ApplicationModule.class, SharedPreferencesModule.class } )
public interface SharedPreferencesComponent {

    void inject( AbstractBaseActivity baseActivity );

    //Exposed to sub-graphs.
    SharedPreferences sharedPreferences();

}
