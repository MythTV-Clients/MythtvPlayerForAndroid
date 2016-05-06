package org.mythtv.android.app;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.mythtv.android.app.internal.di.components.ApplicationComponent;
import org.mythtv.android.app.internal.di.components.DaggerApplicationComponent;
import org.mythtv.android.app.internal.di.modules.ApplicationModule;
import org.mythtv.android.app.internal.di.modules.SharedPreferencesModule;

/**
 * Android Main Application
 *
 * Created by dmfrey on 8/30/15.
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        this.initializeInjector();

        Stetho.initializeWithDefaults( this );

    }

    private void initializeInjector() {

        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( this ) )
                .sharedPreferencesModule( new SharedPreferencesModule( this ) )
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}
