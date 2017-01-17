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

import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.domain.ArtworkInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/18/16.
 */
@Singleton
public class ArtworkInfoEntityDataMapper {

    private ArtworkInfoEntityDataMapper() { }

    public static ArtworkInfo transform( ArtworkInfoEntity artworkInfoEntity ) {

        ArtworkInfo artworkInfo = null;
        if( null != artworkInfoEntity ) {

            artworkInfo = new ArtworkInfo();
            artworkInfo.setUrl( artworkInfoEntity.getUrl() );
            artworkInfo.setFileName( artworkInfoEntity.getFileName() );
            artworkInfo.setStorageGroup( artworkInfoEntity.getStorageGroup() );
            artworkInfo.setType( artworkInfoEntity.getType() );

        }

        return artworkInfo;
    }

    public static List<ArtworkInfo> transformCollection( Collection<ArtworkInfoEntity> artworkInfoEntityCollection ) {

        List<ArtworkInfo> artworkInfoList = new ArrayList<>( artworkInfoEntityCollection.size() );

        ArtworkInfo artworkInfo;
        for( ArtworkInfoEntity artworkInfoEntity : artworkInfoEntityCollection ) {

            artworkInfo = transform( artworkInfoEntity );
            if( null != artworkInfo ) {

                artworkInfoList.add( artworkInfo );

            }

        }

        return artworkInfoList;
    }

}
