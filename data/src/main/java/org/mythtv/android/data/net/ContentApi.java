package org.mythtv.android.data.net;

import org.joda.time.DateTime;
import org.mythtv.android.data.entity.LiveStreamInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 10/17/15.
 */
public interface ContentApi {

    public static final String ADD_LIVE_STREAM_BASE_URL = "/Content/AddLiveStream";
    public static final String ADD_RECORDING_LIVE_STREAM_BASE_URL = "/Content/AddRecordingLiveStream";
    public static final String ADD_VIDEO_LIVE_STREAM_BASE_URL = "/Content/AddVideoLiveStream";
    public static final String LIVE_STREAM_INFO_LIST_BASE_URL = "/Content/GetLiveStreamList";
    public static final String LIVE_STREAM_INFO_URL = "/Content/GetLiveStream?Id=%s";
    public static final String REMOVE_LIVE_STREAM_URL = "/Content/AddLiveStream?Id=%s";
    public static final String STOP_LIVE_STREAM_URL = "/Content/AddLiveStream?Id=%s";

    public static final String STORAGE_GROUP_QS = "StorageGroup=%s";
    public static final String FILENAME_QS = "FileName=%s";
    public static final String HOSTNAME_QS = "HostName=%s";
    public static final String RECORDED_ID_QS = "RecordedId=%s";
    public static final String CHAN_ID_QS = "ChanId=%s";
    public static final String START_TIME_QS = "StartTime=%s";
    public static final String ID_QS = "Id=%s";
    public static final String MAX_SEGMENTS_QS = "MaxSegments=%s";
    public static final String WIDTH_QS = "Width=%s";
    public static final String HEIGHT_QS = "Height=%s";
    public static final String BITRATE_QS = "Bitrate=%s";
    public static final String AUDIO_BITRATE_QS = "AudioBitrate=%s";
    public static final String SAMPLE_RATE_QS = "SampleRate=%s";

    Observable<LiveStreamInfoEntity> addliveStream( final String storageGroup, final String filename, final String hostname );

    Observable<LiveStreamInfoEntity> addRecordingliveStream( final int recordedId, final int chanId, final DateTime startTime );

    Observable<LiveStreamInfoEntity> addVideoliveStream( final int id );

    Observable<List<LiveStreamInfoEntity>> liveStreamInfoEntityList( final String filename );

    Observable<LiveStreamInfoEntity> liveStreamInfoById( final int id );

    Observable<Boolean> removeLiveStream( final int id );

    Observable<Boolean> stopLiveStream( final int id );

}
