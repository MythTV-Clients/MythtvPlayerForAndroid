package org.mythtv.android.data.net;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 10/17/15.
 */
public interface ContentApi {

    String ADD_LIVE_STREAM_BASE_URL = "/Content/AddLiveStream";
    String ADD_RECORDING_LIVE_STREAM_BASE_URL = "/Content/AddRecordingLiveStream";
    String ADD_VIDEO_LIVE_STREAM_BASE_URL = "/Content/AddVideoLiveStream";
    String LIVE_STREAM_INFO_LIST_BASE_URL = "/Content/GetLiveStreamList";
    String LIVE_STREAM_INFO_URL = "/Content/GetLiveStream?Id=%s";
    String REMOVE_LIVE_STREAM_URL = "/Content/AddLiveStream?Id=%s";
    String STOP_LIVE_STREAM_URL = "/Content/AddLiveStream?Id=%s";

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

    Observable<LiveStreamInfoEntity> addliveStream( final String storageGroup, final String filename, final String hostname );

    Observable<LiveStreamInfoEntity> addRecordingliveStream( final int recordedId, final int chanId, final DateTime startTime );

    Observable<LiveStreamInfoEntity> addVideoliveStream( final int id );

    Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntityList( final String filename );

    Observable<LiveStreamInfoEntity> liveStreamInfoById( final int id );

    Observable<Boolean> removeLiveStream( final int id );

    Observable<Boolean> stopLiveStream( final int id );

}
