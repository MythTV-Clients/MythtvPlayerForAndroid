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

import org.mythtv.android.data.entity.RecordingInfoEntity;
import org.mythtv.android.domain.RecordingInfo;

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
public class RecordingInfoEntityDataMapper {

    private RecordingInfoEntityDataMapper() { }

    public static RecordingInfo transform( RecordingInfoEntity recordingInfoEntity ) {

        RecordingInfo recordingInfo = null;
        if( null != recordingInfoEntity ) {

            recordingInfo = new RecordingInfo();
            recordingInfo.setRecordedId( recordingInfoEntity.translateRecordedId() );
            recordingInfo.setStatus( recordingInfoEntity.getStatus() );
            recordingInfo.setPriority( recordingInfoEntity.getPriority() );
            recordingInfo.setStartTs( recordingInfoEntity.getStartTs() );
            recordingInfo.setEndTs( recordingInfoEntity.getEndTs() );
            recordingInfo.setRecordId( recordingInfoEntity.getRecordId() );
            recordingInfo.setRecGroup( recordingInfoEntity.getRecGroup() );
            recordingInfo.setPlayGroup( recordingInfoEntity.getPlayGroup() );
            recordingInfo.setStorageGroup( recordingInfoEntity.getStorageGroup() );
            recordingInfo.setRecType( recordingInfoEntity.getRecType() );
            recordingInfo.setDupInType( recordingInfoEntity.getDupInType() );
            recordingInfo.setDupMethod( recordingInfoEntity.getDupMethod() );
            recordingInfo.setEncoderId( recordingInfoEntity.getEncoderId() );
            recordingInfo.setEncoderName( recordingInfoEntity.getEncoderName() );
            recordingInfo.setProfile( recordingInfoEntity.getProfile() );

        }

        return recordingInfo;
    }

    public static List<RecordingInfo> transformCollection(Collection<RecordingInfoEntity> recordingInfoEntityCollection ) {

        List<RecordingInfo> recordingInfoList = new ArrayList<>( recordingInfoEntityCollection.size() );

        RecordingInfo recordingInfo;
        for( RecordingInfoEntity recordingInfoEntity : recordingInfoEntityCollection ) {

            recordingInfo = transform( recordingInfoEntity );
            if( null != recordingInfo ) {

                recordingInfoList.add( recordingInfo );

            }

        }

        return recordingInfoList;
    }


}
