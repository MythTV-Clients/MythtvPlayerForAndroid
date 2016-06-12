package org.mythtv.android.internal.di.components;

import android.content.SharedPreferences;

import org.mythtv.android.internal.di.modules.ApplicationModule;
import org.mythtv.android.view.activity.phone.AbstractBasePhoneActivity;
import org.mythtv.android.internal.di.modules.SharedPreferencesModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by dmfrey on 5/9/16.
 */
@Singleton
@Component( modules = { ApplicationModule.class, SharedPreferencesModule.class } )
public interface SharedPreferencesComponent {

    void inject( AbstractBasePhoneActivity baseActivity );

    //Exposed to sub-graphs.
    SharedPreferences sharedPreferences();

}
