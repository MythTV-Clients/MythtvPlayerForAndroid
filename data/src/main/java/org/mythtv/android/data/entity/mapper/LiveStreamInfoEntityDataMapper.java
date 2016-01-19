package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.domain.LiveStreamInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dmfrey on 10/17/15.
 */
@Singleton
public class LiveStreamInfoEntityDataMapper {

    private LiveStreamInfoEntityDataMapper() { }

    public static LiveStreamInfo transform( LiveStreamInfoEntity liveStreamInfoEntity ) {

        LiveStreamInfo liveStreamInfo = null;
        if( null != liveStreamInfoEntity ) {

            liveStreamInfo = new LiveStreamInfo();
            liveStreamInfo.setId( liveStreamInfoEntity.getId() );
            liveStreamInfo.setWidth( liveStreamInfoEntity.getWidth() );
            liveStreamInfo.setHeight( liveStreamInfoEntity.getHeight() );
            liveStreamInfo.setBitrate( liveStreamInfoEntity.getBitrate() );
            liveStreamInfo.setAudioBitrate( liveStreamInfoEntity.getAudioBitrate() );
            liveStreamInfo.setSegmentSize( liveStreamInfoEntity.getSegmentSize() );
            liveStreamInfo.setMaxSegments( liveStreamInfoEntity.getMaxSegments() );
            liveStreamInfo.setStartSegment( liveStreamInfoEntity.getStartSegment() );
            liveStreamInfo.setCurrentSegment( liveStreamInfoEntity.getCurrentSegment() );
            liveStreamInfo.setSegmentCount( liveStreamInfoEntity.getSegmentCount() );
            liveStreamInfo.setPercentComplete( liveStreamInfoEntity.getPercentComplete() );
            liveStreamInfo.setCreated( liveStreamInfoEntity.getCreated() );
            liveStreamInfo.setLastModified( liveStreamInfoEntity.getLastModified() );
            liveStreamInfo.setRelativeUrl( liveStreamInfoEntity.getRelativeUrl() );
            liveStreamInfo.setFullUrl( liveStreamInfoEntity.getFullUrl() );
            liveStreamInfo.setStatusString( liveStreamInfoEntity.getStatusString() );
            liveStreamInfo.setStatusInt( liveStreamInfoEntity.getStatusInt() );
            liveStreamInfo.setStatusMessage( liveStreamInfoEntity.getStatusMessage() );
            liveStreamInfo.setSourceFile( liveStreamInfoEntity.getSourceFile() );
            liveStreamInfo.setSourceHost( liveStreamInfoEntity.getSourceHost() );
            liveStreamInfo.setSourceWidth( liveStreamInfoEntity.getSourceWidth() );
            liveStreamInfo.setSourceHeight( liveStreamInfoEntity.getSourceHeight() );
            liveStreamInfo.setAudioOnlyBitrate( liveStreamInfoEntity.getAudioOnlyBitrate() );

        }

        return liveStreamInfo;
    }

    public static List<LiveStreamInfo> transform( Collection<LiveStreamInfoEntity> liveStreamInfoEntityCollection ) {

        List<LiveStreamInfo> liveStreamInfoList = new ArrayList<>( liveStreamInfoEntityCollection.size() );

        LiveStreamInfo liveStreamInfo;
        for( LiveStreamInfoEntity liveStreamInfoEntity : liveStreamInfoEntityCollection ) {

            liveStreamInfo = transform( liveStreamInfoEntity );
            if( null != liveStreamInfo ) {

                liveStreamInfoList.add( liveStreamInfo );

            }

        }

        return liveStreamInfoList;
    }

}
