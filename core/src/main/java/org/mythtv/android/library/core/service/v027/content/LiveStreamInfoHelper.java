/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.library.core.service.v027.content;

import org.mythtv.android.library.events.content.LiveStreamDetails;
import org.mythtv.services.api.v027.beans.LiveStreamInfo;

/**
 * Created by dmfrey on 11/18/14.
 */
public class LiveStreamInfoHelper {

    public static LiveStreamDetails toDetails( LiveStreamInfo liveStreamInfo ) {

        LiveStreamDetails details = new LiveStreamDetails();
        details.setId( liveStreamInfo.getId() );
        details.setWidth( liveStreamInfo.getWidth() );
        details.setHeight( liveStreamInfo.getHeight() );
        details.setBitrate( liveStreamInfo.getBitrate() );
        details.setAudioBitrate( liveStreamInfo.getAudioBitrate() );
        details.setSegmentSize( liveStreamInfo.getSegmentSize() );
        details.setMaxSegments( liveStreamInfo.getMaxSegments() );
        details.setStartSegment( liveStreamInfo.getStartSegment() );
        details.setCurrentSegment( liveStreamInfo.getCurrentSegment() );
        details.setSegmentCount( liveStreamInfo.getSegmentCount() );
        details.setPercentComplete( liveStreamInfo.getPercentComplete() );
        details.setCreated( liveStreamInfo.getCreated() );
        details.setLastModified( liveStreamInfo.getLastModified() );
        details.setRelativeURL( liveStreamInfo.getRelativeURL() );
        details.setFullURL( liveStreamInfo.getFullURL() );
        details.setStatusStr( liveStreamInfo.getStatusStr() );
        details.setStatusInt( liveStreamInfo.getStatusInt() );
        details.setStatusMessage( liveStreamInfo.getStatusMessage() );
        details.setSourceFile( liveStreamInfo.getSourceFile() );
        details.setSourceHost( liveStreamInfo.getSourceHost() );
        details.setSourceWidth( liveStreamInfo.getSourceWidth() );
        details.setSourceHeight( liveStreamInfo.getSourceHeight() );
        details.setAudioOnlyBitrate( liveStreamInfo.getAudioOnlyBitrate() );

        return details;
    }

    public static LiveStreamInfo fromDetails( LiveStreamDetails details ) {

        LiveStreamInfo liveStreamInfo = new LiveStreamInfo();
        liveStreamInfo.setId( details.getId() );
        liveStreamInfo.setWidth( details.getWidth() );
        liveStreamInfo.setHeight( details.getHeight() );
        liveStreamInfo.setBitrate( details.getBitrate() );
        liveStreamInfo.setAudioBitrate( details.getAudioBitrate() );
        liveStreamInfo.setSegmentSize( details.getSegmentSize() );
        liveStreamInfo.setMaxSegments( details.getMaxSegments() );
        liveStreamInfo.setStartSegment( details.getStartSegment() );
        liveStreamInfo.setCurrentSegment( details.getCurrentSegment() );
        liveStreamInfo.setSegmentCount( details.getSegmentCount() );
        liveStreamInfo.setPercentComplete( details.getPercentComplete() );
        liveStreamInfo.setCreated( details.getCreated() );
        liveStreamInfo.setLastModified( details.getLastModified() );
        liveStreamInfo.setRelativeURL( details.getRelativeURL() );
        liveStreamInfo.setFullURL( details.getFullURL() );
        liveStreamInfo.setStatusStr( details.getStatusStr() );
        liveStreamInfo.setStatusInt( details.getStatusInt() );
        liveStreamInfo.setStatusMessage( details.getStatusMessage() );
        liveStreamInfo.setSourceFile( details.getSourceFile() );
        liveStreamInfo.setSourceHost( details.getSourceHost() );
        liveStreamInfo.setSourceWidth( details.getSourceWidth() );
        liveStreamInfo.setSourceHeight( details.getSourceHeight() );
        liveStreamInfo.setAudioOnlyBitrate( details.getAudioOnlyBitrate() );

        return liveStreamInfo;
    }

}
