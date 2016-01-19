package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.RecordingInfoEntity;
import org.mythtv.android.domain.RecordingInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dmfrey on 1/18/16.
 */
@Singleton
public class RecordingInfoEntityDataMapper {

    private RecordingInfoEntityDataMapper() { }

    public static RecordingInfo transform( RecordingInfoEntity recordingInfoEntity ) {

        RecordingInfo recordingInfo = null;
        if( null != recordingInfoEntity ) {

            recordingInfo = new RecordingInfo();
            recordingInfo.setRecordedId( recordingInfoEntity.getRecordedId() );
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
