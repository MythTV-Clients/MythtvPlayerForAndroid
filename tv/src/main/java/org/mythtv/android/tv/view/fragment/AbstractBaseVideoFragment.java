package org.mythtv.android.tv.view.fragment;

import android.app.Activity;

import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

/**
 * Created by dmfrey on 11/25/15.
 */
public abstract class AbstractBaseVideoFragment extends AbstractBaseBrowseFragment {

    protected VideoListListener videoListListener;

    /**
     * Interface for listening videoMetadataInfo list events.
     */
    public interface VideoListListener {

        void onVideoClicked( final VideoMetadataInfoModel videoMetadataInfoModel, final String contentType );

    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );

        if( activity instanceof VideoListListener ) {
            this.videoListListener = (VideoListListener) activity;
        }

    }


}
