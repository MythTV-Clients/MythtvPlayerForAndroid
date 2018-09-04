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

package org.mythtv.android.data.repository;

import android.util.Log;

import org.mythtv.android.data.entity.mapper.EncoderEntityDataMapper;
import org.mythtv.android.data.repository.datasource.DvrDataStore;
import org.mythtv.android.data.repository.datasource.DvrDataStoreFactory;
import org.mythtv.android.domain.Encoder;
import org.mythtv.android.domain.repository.DvrRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/27/15.
 */
@Singleton
public class DvrDataRepository implements DvrRepository {

    private static final String TAG = DvrDataRepository.class.getSimpleName();
    private static final String CONVERT2METHODREF = "Convert2MethodRef";

    private final DvrDataStoreFactory dvrDataStoreFactory;

    @Inject
    public DvrDataRepository( DvrDataStoreFactory dvrDataStoreFactory ) {

        this.dvrDataStoreFactory = dvrDataStoreFactory;

    }

    @SuppressWarnings( CONVERT2METHODREF )
    @Override
    public Observable<List<Encoder>> encoders() {
        Log.d( TAG, "encoders : enter" );

        final DvrDataStore dvrDataStore = this.dvrDataStoreFactory.createMasterBackendDataStore();

        return dvrDataStore.encoderEntityList()
                .doOnError( throwable -> Log.e( TAG, "encoders : error", throwable ) )
                .map( encoderEntities -> EncoderEntityDataMapper.transformCollection( encoderEntities ) );
    }

}
