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

package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.domain.TitleInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class TitleInfoEntityDataMapper {

    private TitleInfoEntityDataMapper() { }

    public static TitleInfo transform( TitleInfoEntity titleInfoEntity ) {

        TitleInfo titleInfo = null;
        if( null != titleInfoEntity ) {
            titleInfo = new TitleInfo();
            titleInfo.setTitle( titleInfoEntity.getTitle() );
            titleInfo.setInetref( titleInfoEntity.getInetref() );
            titleInfo.setCount( titleInfoEntity.getCount() );

        }

        return titleInfo;
    }

    public static List<TitleInfo> transform( Collection<TitleInfoEntity> titleInfoEntityCollection ) {

        List<TitleInfo> titleInfoList = new ArrayList<>( titleInfoEntityCollection.size() );

        TitleInfo titleInfo;
        for( TitleInfoEntity titleInfoEntity : titleInfoEntityCollection ) {

            titleInfo = transform( titleInfoEntity );
            if( null != titleInfo ) {

                titleInfoList.add( titleInfo );

            }

        }

        return titleInfoList;
    }

}
