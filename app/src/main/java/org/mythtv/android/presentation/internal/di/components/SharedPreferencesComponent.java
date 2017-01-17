package org.mythtv.android.presentation.internal.di.components;

import android.content.SharedPreferences;

import org.mythtv.android.presentation.internal.di.modules.ApplicationModule;
import org.mythtv.android.presentation.view.activity.phone.AbstractBasePhoneActivity;
import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 5/9/16.
 */
@Singleton
@Component( modules = { ApplicationModule.class, SharedPreferencesModule.class } )
public interface SharedPreferencesComponent {

    void inject( AbstractBasePhoneActivity baseActivity );

    //Exposed to sub-graphs.
    SharedPreferences sharedPreferences();

}
