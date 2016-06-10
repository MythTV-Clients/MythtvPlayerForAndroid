/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.mythtv.android.presenter.tv;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import org.mythtv.android.model.ProgramModel;
import org.mythtv.android.model.VideoMetadataInfoModel;
import org.mythtv.android.utils.SeasonEpisodeFormatter;

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {

        if( null != item ) {

            if( item instanceof ProgramModel) {
                ProgramModel programModel = (ProgramModel) item;

                viewHolder.getTitle().setText( programModel.getTitle() );
                viewHolder.getSubtitle().setText( programModel.getSubTitle() );
                viewHolder.getBody().setText( programModel.getDescription() );

            }

            if( item instanceof VideoMetadataInfoModel ) {
                VideoMetadataInfoModel videoMetadataInfoModel = (VideoMetadataInfoModel) item;

                String seasonEpisode = "";
                if( "TELEVISION".equals( videoMetadataInfoModel.getContentType() ) ) {

                    seasonEpisode = SeasonEpisodeFormatter.format( videoMetadataInfoModel );

                }

                viewHolder.getTitle().setText( videoMetadataInfoModel.getTitle() );
                viewHolder.getSubtitle().setText( String.format( "%s %s", videoMetadataInfoModel.getSubTitle(), seasonEpisode ) );
                viewHolder.getBody().setText( videoMetadataInfoModel.getDescription() );

            }

        }

    }

}
