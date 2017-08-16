/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
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

package org.mythtv.android.presentation.mapper;

import org.mythtv.android.domain.Encoder;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.EncoderModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/20/16.
 */
@PerActivity
public class EncoderModelDataMapper {

    @Inject
    public EncoderModelDataMapper() {
        // This constructor is intentionally left blank
    }

    public EncoderModel transform( Encoder encoder ) {

        EncoderModel encoderModel = null;
        if( null != encoder ) {

            encoderModel = EncoderModel.create( encoder.id(), encoder.inputName(), encoder.recordingName(), encoder.recordingDescription(), encoder.state() );

        }

        return encoderModel;
    }

    public List<EncoderModel> transform( Collection<Encoder> encoderCollection ) {

        List<EncoderModel> encoderModelList = new ArrayList<>( encoderCollection.size() );

        EncoderModel encoderModel;
        for( Encoder encoder : encoderCollection ) {

            encoderModel = transform( encoder );
            if( null != encoderModel ) {

                encoderModelList.add( encoderModel );

            }

        }

        return encoderModelList;
    }

}
