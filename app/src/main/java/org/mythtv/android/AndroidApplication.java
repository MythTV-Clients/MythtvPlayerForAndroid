/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import org.mythtv.android.internal.di.components.ApplicationComponent;
import org.mythtv.android.internal.di.components.DaggerApplicationComponent;
import org.mythtv.android.internal.di.components.DaggerNetComponent;
import org.mythtv.android.internal.di.components.DaggerSharedPreferencesComponent;
import org.mythtv.android.internal.di.components.NetComponent;
import org.mythtv.android.internal.di.components.SharedPreferencesComponent;
import org.mythtv.android.internal.di.modules.ApplicationModule;
import org.mythtv.android.internal.di.modules.NetModule;
import org.mythtv.android.internal.di.modules.SharedPreferencesModule;

/**
 * Android Main Application
 *
 * Created by dmfrey on 8/30/15.
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;
    private SharedPreferencesComponent sharedPreferencesComponent;
    private NetComponent netComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        this.initializeInjector();

        Stetho.initializeWithDefaults( this );
        LeakCanary.install( this );

    }

    private void initializeInjector() {

        ApplicationModule applicationModule = new ApplicationModule( this );
        SharedPreferencesModule sharedPreferencesModule = new SharedPreferencesModule( this );
        NetModule netModule = new NetModule();

        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule( applicationModule )
                .sharedPreferencesModule( sharedPreferencesModule )
                .netModule( netModule )
                .build();

        this.sharedPreferencesComponent = DaggerSharedPreferencesComponent.builder()
                .sharedPreferencesModule( sharedPreferencesModule )
                .build();

        this.netComponent = DaggerNetComponent.builder()
                .applicationModule( applicationModule )
                .sharedPreferencesModule( sharedPreferencesModule )
                .netModule( netModule )
                .build();

    }

    public ApplicationComponent getApplicationComponent() {

        return this.applicationComponent;
    }

    public SharedPreferencesComponent getSharedPreferencesComponent() {

        return this.sharedPreferencesComponent;
    }

    public NetComponent getNetComponent() {

        return this.netComponent;
    }

}
