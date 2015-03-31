package org.mythtv.android.player.tv.videos;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import org.mythtv.android.library.core.domain.video.Video;

public class VideoDetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription( ViewHolder viewHolder, Object item ) {

        Video video = (Video) item;

        if( null != video ) {
            viewHolder.getTitle().setText( video.getTitle() );
            viewHolder.getSubtitle().setText( video.getSubTitle() );
            viewHolder.getBody().setText( video.getDescription() );
        }

    }

}
