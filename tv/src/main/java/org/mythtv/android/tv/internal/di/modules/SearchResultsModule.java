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

package org.mythtv.android.tv.internal.di.modules;

import android.util.Log;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.interactor.GetSearchResultList;
import org.mythtv.android.domain.interactor.UseCase;
import org.mythtv.android.domain.repository.SearchRepository;
import org.mythtv.android.presentation.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dmfrey on 10/12/15.
 */
@Module
public class SearchResultsModule {

    private static final String TAG = SearchResultsModule.class.getSimpleName();

    private String searchText = null;

//    public SearchResultsModule() { }

    public SearchResultsModule( String searchText ) {
        Log.d( TAG, "initialize : enter - searchText=" + searchText );

        this.searchText = searchText;

        Log.d( TAG, "initialize : exit" );
    }

    @Provides
    @PerActivity
    @Named( "searchResultList" )
    UseCase provideSearchResultListUseCase( SearchRepository searchRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        Log.d( TAG, "provideSearchResultListUseCase : enter - searchText=" + searchText );

        return new GetSearchResultList( searchText, searchRepository, threadExecutor, postExecutionThread );
    }

    public void setSearchText( String searchText ) {
        Log.d( TAG, "setSearchText : enter - searchText=" + searchText );

        this.searchText = searchText;

        Log.d( TAG, "setSearchText : exit" );
    }

}
