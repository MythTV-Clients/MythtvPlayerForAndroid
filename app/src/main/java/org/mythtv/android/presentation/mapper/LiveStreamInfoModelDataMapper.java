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

import org.mythtv.android.domain.LiveStreamInfo;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.LiveStreamInfoModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 10/17/15.
 */
@PerActivity
public class LiveStreamInfoModelDataMapper {

    @Inject
    public LiveStreamInfoModelDataMapper() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public LiveStreamInfoModel transform( LiveStreamInfo liveStreamInfo ) {

        LiveStreamInfoModel liveStreamInfoModel = null;
        if( null != liveStreamInfo ) {

            liveStreamInfoModel = new LiveStreamInfoModel();
            liveStreamInfoModel.setId( liveStreamInfo.getId() );
            liveStreamInfoModel.setWidth( liveStreamInfo.getWidth() );
            liveStreamInfoModel.setHeight( liveStreamInfo.getHeight() );
            liveStreamInfoModel.setBitrate( liveStreamInfo.getBitrate() );
            liveStreamInfoModel.setAudioBitrate( liveStreamInfo.getAudioBitrate() );
            liveStreamInfoModel.setSegmentSize( liveStreamInfo.getSegmentSize() );
            liveStreamInfoModel.setMaxSegments( liveStreamInfo.getMaxSegments() );
            liveStreamInfoModel.setStartSegment( liveStreamInfo.getStartSegment() );
            liveStreamInfoModel.setCurrentSegment( liveStreamInfo.getCurrentSegment() );
            liveStreamInfoModel.setSegmentCount( liveStreamInfo.getSegmentCount() );
            liveStreamInfoModel.setPercentComplete( liveStreamInfo.getPercentComplete() );
            liveStreamInfoModel.setCreated( liveStreamInfo.getCreated() );
            liveStreamInfoModel.setLastModified( liveStreamInfo.getLastModified() );
            liveStreamInfoModel.setRelativeUrl( liveStreamInfo.getRelativeUrl() );
            liveStreamInfoModel.setFullUrl( liveStreamInfo.getFullUrl() );
            liveStreamInfoModel.setStatusString( liveStreamInfo.getStatusString() );
            liveStreamInfoModel.setStatusInt( liveStreamInfo.getStatusInt() );
            liveStreamInfoModel.setStatusMessage( liveStreamInfo.getStatusMessage() );
            liveStreamInfoModel.setSourceFile( liveStreamInfo.getSourceFile() );
            liveStreamInfoModel.setSourceHost( liveStreamInfo.getSourceHost() );
            liveStreamInfoModel.setSourceWidth( liveStreamInfo.getSourceWidth() );
            liveStreamInfoModel.setSourceHeight( liveStreamInfo.getSourceHeight() );
            liveStreamInfoModel.setAudioOnlyBitrate( liveStreamInfo.getAudioOnlyBitrate() );

        }

        return liveStreamInfoModel;
    }

    public List<LiveStreamInfoModel> transform( Collection<LiveStreamInfo> liveStreamInfoCollection ) {

        List<LiveStreamInfoModel> liveStreamInfoModelList = new ArrayList<>( liveStreamInfoCollection.size() );

        LiveStreamInfoModel liveStreamInfoModel;
        for( LiveStreamInfo liveStreamInfo : liveStreamInfoCollection ) {

            liveStreamInfoModel = transform( liveStreamInfo );
            if( null != liveStreamInfoModel ) {

                liveStreamInfoModelList.add( liveStreamInfoModel );

            }

        }

        return liveStreamInfoModelList;
    }

}
