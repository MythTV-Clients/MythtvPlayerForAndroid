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

package org.mythtv.android.tv;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.mythtv.android.tv.internal.di.components.ApplicationComponent;
import org.mythtv.android.tv.internal.di.components.DaggerApplicationComponent;
import org.mythtv.android.tv.internal.di.modules.ApplicationModule;
import org.mythtv.android.tv.internal.di.modules.SharedPreferencesModule;

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
