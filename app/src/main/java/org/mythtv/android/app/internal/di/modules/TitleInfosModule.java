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

package org.mythtv.android.app.internal.di.modules;

import org.mythtv.android.domain.interactor.GetTitleInfoList;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides programs related collaborators.
 *
 * Created by dmfrey on 9/9/15.
 */
@Module
public class TitleInfosModule {

    public TitleInfosModule() {}

    @Provides
    @PerActivity
    @Named( "titleInfoList" )
    UseCase provideTitleInfoListUseCase( GetTitleInfoList getTitleInfoList ) {

        return getTitleInfoList;
    }

}
