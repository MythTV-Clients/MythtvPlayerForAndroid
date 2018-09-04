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

package org.mythtv.android.presentation.presenter.tv;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import org.mythtv.android.presentation.model.MediaItemModel;

/**
 *
 *
 *
 * @author dmfrey
 */
public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription( ViewHolder viewHolder, Object item ) {

        if( item instanceof MediaItemModel ) {

            MediaItemModel mediaItemModel = (MediaItemModel) item;

            viewHolder.getTitle().setText( mediaItemModel.title() );
            viewHolder.getSubtitle().setText( mediaItemModel.subTitle() );
            viewHolder.getBody().setText( mediaItemModel.description() );

        }

    }

}
