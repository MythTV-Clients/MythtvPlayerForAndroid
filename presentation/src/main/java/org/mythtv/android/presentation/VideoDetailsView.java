package org.mythtv.android.presentation;

import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.view.LoadDataView;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a video.
 *
 * Created by dmfrey on 11/25/15.
 */
public interface VideoDetailsView extends LoadDataView {

    /**
     * Render a user in the UI.
     *
     * @param videoMetadataInfoModel The {@link VideoMetadataInfoModel} that will be shown.
     */
    void renderVideo( VideoMetadataInfoModel videoMetadataInfoModel );

    /**
     * Update the live streaming buttons
     *
     * @param liveStreamInfo
     */
    void updateLiveStream( LiveStreamInfoModel liveStreamInfo );

}
