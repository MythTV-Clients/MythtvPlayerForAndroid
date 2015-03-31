package org.mythtv.android.player.tv.recordings;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import org.mythtv.android.library.core.domain.dvr.Program;

public class RecordingDetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription( ViewHolder viewHolder, Object item ) {

        Program program = (Program) item;

        if( null != program ) {
            viewHolder.getTitle().setText( program.getTitle() );
            viewHolder.getSubtitle().setText( program.getSubTitle() );
            viewHolder.getBody().setText( program.getDescription() );
        }

    }

}
