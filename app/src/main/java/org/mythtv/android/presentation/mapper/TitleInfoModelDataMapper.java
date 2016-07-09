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

package org.mythtv.android.presentation.mapper;

import org.mythtv.android.domain.TitleInfo;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.TitleInfoModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 8/27/15.
 */
@PerActivity
public class TitleInfoModelDataMapper {

    private static final String TAG = TitleInfoModelDataMapper.class.getSimpleName();

    @Inject
    public TitleInfoModelDataMapper() {
    }

    public TitleInfoModel transform( TitleInfo titleInfo ) {
//        Log.d( TAG, "transform : enter" );

        TitleInfoModel titleInfoModel = null;
        if( null != titleInfo ) {

            titleInfoModel = new TitleInfoModel();
            titleInfoModel.setTitle( titleInfo.getTitle() );
            titleInfoModel.setInetref( titleInfo.getInetref() );
            titleInfoModel.setCount( titleInfo.getCount() );

        }

//        Log.d( TAG, "transform : titleInfoModle=" + titleInfoModel );
//        Log.d( TAG, "transform : exit" );
        return titleInfoModel;
    }

    public List<TitleInfoModel> transform( Collection<TitleInfo> titleInfoCollection ) {
//        Log.d( TAG, "transform : enter" );

        List<TitleInfoModel> titleInfoModelList = new ArrayList<>( titleInfoCollection.size() );

        TitleInfoModel titleInfoModel;
        for( TitleInfo titleInfo : titleInfoCollection ) {

            titleInfoModel = transform( titleInfo );
            if( null != titleInfoModel ) {
//                Log.d( TAG, "transform : titleInfoModel is not null" );

                titleInfoModelList.add( titleInfoModel );

            }

        }

//        Log.d( TAG, "transform : exit" );
        return titleInfoModelList;
    }

}
