package org.mythtv.android.tv;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;
import org.mythtv.android.tv.internal.di.components.ApplicationComponent;
import org.mythtv.android.tv.internal.di.components.DaggerApplicationComponent;
import org.mythtv.android.tv.internal.di.modules.ApplicationModule;

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
