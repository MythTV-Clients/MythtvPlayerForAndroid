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

package org.mythtv.android.presentation.internal.di.components;

import android.content.Context;

import org.mythtv.android.presentation.internal.di.modules.ApplicationModule;
import org.mythtv.android.presentation.view.activity.phone.AbstractBasePhoneActivity;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;
import org.mythtv.android.domain.repository.DvrRepository;
import org.mythtv.android.domain.repository.SearchRepository;
import org.mythtv.android.domain.repository.VideoRepository;
import org.mythtv.android.presentation.internal.di.modules.NetModule;
import org.mythtv.android.presentation.internal.di.modules.SharedPreferencesModule;
import org.mythtv.android.presentation.view.activity.tv.AbstractBaseTvActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
 */
@Singleton // Constraints this component to one-per-application or un-scoped bindings.
@Component( modules = { ApplicationModule.class, SharedPreferencesModule.class, NetModule.class } )
public interface ApplicationComponent {

    void inject( AbstractBasePhoneActivity baseActivity );
    void inject( AbstractBaseTvActivity baseActivity );

    //Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    DvrRepository dvrRepository();
    SearchRepository searchRepository();
    ContentRepository contentRepository();
    VideoRepository videoRepository();

}
