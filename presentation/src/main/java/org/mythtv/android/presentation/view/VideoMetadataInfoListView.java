package org.mythtv.android.presentation.view;

import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link VideoMetadataInfoModel}.
 *
 * Created by dmfrey on 11/13/15.
 */
public interface VideoMetadataInfoListView extends LoadDataView {

    /**
     * Render a videoMetadataInfo list in the UI.
     *
     * @param videoMetadataInfoModelCollection The collection of {@link VideoMetadataInfoModel} that will be shown.
     */
    void renderVideoMetadataInfoList( Collection<VideoMetadataInfoModel> videoMetadataInfoModelCollection );

    /**
     * View a {@link VideoMetadataInfoModel} details.
     *
     * @param videoMetadataInfoModel The videoMetadataInfo that will be shown.
     */
    void viewVideoMetadataInfo( VideoMetadataInfoModel videoMetadataInfoModel );

}
