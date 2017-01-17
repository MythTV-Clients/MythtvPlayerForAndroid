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

import android.app.Activity;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.internal.di.modules.ActivityModule;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link org.mythtv.android.presentation.internal.di.PerActivity}
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = ActivityModule.class )
public interface ActivityComponent {

    //Exposed to sub-graphs.
    Activity activity();

}
