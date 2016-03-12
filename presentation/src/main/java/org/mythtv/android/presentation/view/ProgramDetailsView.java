package org.mythtv.android.presentation.view;

import org.mythtv.android.presentation.model.LiveStreamInfoModel;
import org.mythtv.android.presentation.model.ProgramModel;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a program.
 *
 * Created by dmfrey on 8/31/15.
 */
public interface ProgramDetailsView extends LoadDataView {

    /**
     * Render a user in the UI.
     *
     * @param program The {@link ProgramModel} that will be shown.
     */
    void renderProgram( ProgramModel program );

    /**
     * Update the live streaming buttons
     *
     * @param liveStreamInfo liveStreamInfo
     */
    void updateLiveStream( LiveStreamInfoModel liveStreamInfo );

}
