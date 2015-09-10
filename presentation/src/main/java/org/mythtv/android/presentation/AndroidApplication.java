package org.mythtv.android.presentation;

import android.app.Application;

import org.mythtv.android.presentation.internal.di.components.ApplicationComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerApplicationComponent;
import org.mythtv.android.presentation.internal.di.modules.ApplicationModule;

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

    }

    private void initializeInjector() {

        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( new ApplicationModule( this ) )
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}
