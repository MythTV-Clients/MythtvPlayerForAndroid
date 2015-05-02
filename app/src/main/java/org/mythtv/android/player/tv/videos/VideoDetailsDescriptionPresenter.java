/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
