package org.mythtv.android.library.core.domain.content;

/**
 * Created by dmfrey on 1/25/15.
 */
public class LiveStreamInfoList {

    private LiveStreamInfo[] liveStreamInfos;

    /**
     * @return the liveStreamInfos
     */
    public LiveStreamInfo[] getLiveStreamInfos() {
        return liveStreamInfos;
    }

    /**
     * @param liveStreamInfos the liveStreamInfos to set
     */
    public void setLiveStreamInfos( LiveStreamInfo[] liveStreamInfos ) {
        this.liveStreamInfos = liveStreamInfos;
    }

}
