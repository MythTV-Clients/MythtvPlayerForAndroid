package org.mythtv.android.presentation.view.fragment;

import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

/**
 * Created by dmfrey on 11/25/15.
 */
public abstract class AppAbstractBaseVideoPagerFragment extends AppAbstractBaseFragment {

    /**
     * Interface for listening videoMetadataInfo list events.
     */
    public interface VideoListListener {

        void onVideoClicked( final VideoMetadataInfoModel videoMetadataInfoModel, final String contentType );

    }

}
