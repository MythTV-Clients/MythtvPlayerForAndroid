package org.mythtv.android.presentation.view.fragment;

import android.app.Activity;

import org.mythtv.android.presentation.model.VideoMetadataInfoModel;

/**
 * Created by dmfrey on 11/25/15.
 */
public abstract class TvAbstractBaseVideoFragment extends TvAbstractBaseBrowseFragment {

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
