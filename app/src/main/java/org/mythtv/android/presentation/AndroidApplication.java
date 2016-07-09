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

package org.mythtv.android.presentation;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.google.android.libraries.cast.companionlibrary.cast.CastConfiguration;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.squareup.leakcanary.LeakCanary;

import org.mythtv.android.presentation.internal.di.components.ApplicationComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerApplicationComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerNetComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerSharedPreferencesComponent;
import org.mythtv.android.presentation.internal.di.components.NetComponent;
import org.mythtv.android.presentation.internal.di.components.SharedPreferencesComponent;
import org.mythtv.android.presentation.internal.di.modules.ApplicationModule;
import org.mythtv.android.presentation.internal.di.modules.NetModule;
import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;

import java.util.Locale;

/**
 * Android Main Application
 *
 * Created by dmfrey on 8/30/15.
 */
public class AndroidApplication extends Application {

    public static final double VOLUME_INCREMENT = 0.05;
    public static final int PRELOAD_TIME_S = 20;

    private ApplicationComponent applicationComponent;
    private SharedPreferencesComponent sharedPreferencesComponent;
    private NetComponent netComponent;

    @Override
    protected void attachBaseContext( Context base ) {

        MultiDex.install( this );

        super.attachBaseContext( base );
    }

    @Override
    public void onCreate() {

        super.onCreate();
        this.initializeInjector();

        Stetho.initializeWithDefaults( this );
        LeakCanary.install( this );

        String applicationId = getString( R.string.app_id );

        // Build a CastConfiguration object and initialize VideoCastManager
        CastConfiguration options = new CastConfiguration.Builder( applicationId )
                .enableAutoReconnect()
                .enableDebug()
                .enableLockScreen()
                .enableNotification()
                .enableWifiReconnection()
                .setCastControllerImmersive( true )
                .setLaunchOptions( false, Locale.getDefault() )
                .setNextPrevVisibilityPolicy( CastConfiguration.NEXT_PREV_VISIBILITY_POLICY_DISABLED )
                .addNotificationAction( CastConfiguration.NOTIFICATION_ACTION_REWIND, false )
                .addNotificationAction( CastConfiguration.NOTIFICATION_ACTION_PLAY_PAUSE, true )
                .addNotificationAction( CastConfiguration.NOTIFICATION_ACTION_DISCONNECT, true )
                .setForwardStep( 10 )
                .build();

        VideoCastManager.initialize( this, options );


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
