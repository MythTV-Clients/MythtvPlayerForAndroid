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

package org.mythtv.android.library.persistence.service.dvr;

import org.mythtv.android.library.events.dvr.RecordingInfoDetails;
import org.mythtv.android.library.persistence.domain.dvr.RecordingInfo;

/**
 * Created by dmfrey on 11/15/14.
 */
public class RecordingInfoHelper {

    public static RecordingInfoDetails toDetails( RecordingInfo recording ) {

        RecordingInfoDetails details = new RecordingInfoDetails();
        details.setRecordedId( recording.getRecordedId() );
        details.setStatus( recording.getStatus() );
        details.setPriority( recording.getPriority() );
        details.setStartTs( recording.getStartTs() );
        details.setEndTs( recording.getEndTs() );
        details.setRecordId( recording.getRecordId() );
        details.setRecGroup( recording.getRecGroup() );
        details.setPlayGroup( recording.getPlayGroup() );
        details.setStorageGroup( recording.getStorageGroup() );
        details.setRecType( recording.getRecType() );
        details.setDupInType( recording.getDupInType() );
        details.setDupMethod( recording.getDupMethod() );
        details.setEncoderId( recording.getEncoderId() );
        details.setEncoderName( recording.getEncoderName() );
        details.setProfile( recording.getProfile() );

        return details;
    }

    public static RecordingInfo fromDetails( RecordingInfoDetails details ) {

        RecordingInfo recordingInfo = new RecordingInfo();
        recordingInfo.setRecordedId( details.getRecordedId() );
        recordingInfo.setStatus( details.getStatus() );
        recordingInfo.setPriority( details.getPriority() );
        recordingInfo.setStartTs( details.getStartTs() );
        recordingInfo.setEndTs( details.getEndTs() );
        recordingInfo.setRecordId( details.getRecordId() );
        recordingInfo.setRecGroup( details.getRecGroup() );
        recordingInfo.setPlayGroup( details.getPlayGroup() );
        recordingInfo.setStorageGroup( details.getStorageGroup() );
        recordingInfo.setRecType( details.getRecType() );
        recordingInfo.setDupInType( details.getDupInType() );
        recordingInfo.setDupMethod( details.getDupMethod() );
        recordingInfo.setEncoderId( details.getEncoderId() );
        recordingInfo.setEncoderName( details.getEncoderName() );
        recordingInfo.setProfile( details.getProfile() );

        return recordingInfo;
    }

}
