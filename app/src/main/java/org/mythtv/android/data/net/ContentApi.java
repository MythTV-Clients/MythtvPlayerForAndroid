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

package org.mythtv.android.data.net;

import org.mythtv.android.data.entity.LiveStreamInfoEntity;

import java.util.List;

import rx.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 10/17/15.
 */
public interface ContentApi {

    String ADD_LIVE_STREAM_BASE_URL = "/Content/AddLiveStream";
    String ADD_RECORDING_LIVE_STREAM_BASE_URL = "/Content/AddRecordingLiveStream";
    String ADD_VIDEO_LIVE_STREAM_BASE_URL = "/Content/AddVideoLiveStream";
    String LIVE_STREAM_INFO_LIST_BASE_URL = "/Content/GetLiveStreamList";
    String LIVE_STREAM_INFO_URL = "/Content/GetLiveStream?Id=%s";
    String REMOVE_LIVE_STREAM_URL = "/Content/RemoveLiveStream?Id=%s";
    String STOP_LIVE_STREAM_URL = "/Content/StopLiveStream?Id=%s";

    String STORAGE_GROUP_QS = "StorageGroup=%s";
    String FILENAME_QS = "FileName=%s";
    String HOSTNAME_QS = "HostName=%s";
    String RECORDED_ID_QS = "RecordedId=%s";
    String CHAN_ID_QS = "ChanId=%s";
    String START_TIME_QS = "StartTime=%s";
    String ID_QS = "Id=%s";
    String MAX_SEGMENTS_QS = "MaxSegments=%s";
    String WIDTH_QS = "Width=%s";
    String HEIGHT_QS = "Height=%s";
    String BITRATE_QS = "Bitrate=%s";
    String AUDIO_BITRATE_QS = "AudioBitrate=%s";
    String SAMPLE_RATE_QS = "SampleRate=%s";

    Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntityList( final String filename );

}
