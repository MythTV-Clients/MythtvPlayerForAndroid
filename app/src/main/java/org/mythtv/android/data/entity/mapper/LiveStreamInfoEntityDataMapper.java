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

import org.mythtv.android.data.entity.LiveStreamInfoEntity;
import org.mythtv.android.domain.LiveStreamInfo;

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
 * Created on 10/17/15.
 */
@Singleton
public final class LiveStreamInfoEntityDataMapper {

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
