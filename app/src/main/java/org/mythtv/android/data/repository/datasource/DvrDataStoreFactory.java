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

package org.mythtv.android.data.repository.datasource;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.EncoderEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.LongJsonMapper;
import org.mythtv.android.data.entity.mapper.ProgramEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityJsonMapper;
import org.mythtv.android.data.net.DvrApi;
import org.mythtv.android.data.net.DvrApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class DvrDataStoreFactory {

    private static final String TAG = DvrDataStoreFactory.class.getSimpleName();

    private final SearchDataStoreFactory searchDataStoreFactory;
    private final DvrApi api;

    @Inject
    public DvrDataStoreFactory( final Context context, final SharedPreferences sharedPreferences, final OkHttpClient okHttpClient, final Gson gson, final SearchDataStoreFactory searchDataStoreFactory ) {
        Log.d( TAG, "initialize : enter" );

        if( null == context || null == sharedPreferences || null == okHttpClient || null == gson || null == searchDataStoreFactory ) {

            throw new IllegalArgumentException( "Constructor parameters cannot be null!!!" );
        }

        this.searchDataStoreFactory = searchDataStoreFactory;

        api = new DvrApiImpl( context.getApplicationContext(), sharedPreferences, okHttpClient, new TitleInfoEntityJsonMapper( gson ), new ProgramEntityJsonMapper( gson ), new EncoderEntityJsonMapper( gson ), new BooleanJsonMapper(), new LongJsonMapper() );

        Log.d( TAG, "initialize : exit" );
    }

    public DvrDataStore createMasterBackendDataStore() {
        Log.d( TAG, "createMasterBackendDataStore : enter" );

        Log.d( TAG, "createMasterBackendDataStore : exit" );
        return new MasterBackendDvrDataStore( api, this.searchDataStoreFactory );
    }

}
