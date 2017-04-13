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

package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.domain.Encoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/18/16.
 */
@Singleton
public final class EncoderEntityDataMapper {

    private EncoderEntityDataMapper() { }

    public static Encoder transform( EncoderEntity encoderEntity ) {

        Encoder encoder = null;
        if( null != encoderEntity ) {

            encoder = Encoder.create(
                encoderEntity.id(),
                ( null == encoderEntity.inputs() || encoderEntity.inputs().isEmpty() )
                        ? String.valueOf( encoderEntity.id() )
                        : ( "".equals( encoderEntity.inputs().get( 0 ).displayName() ) ) ? encoderEntity.inputs().get( 0 ).inputName() : encoderEntity.inputs().get( 0 ).displayName(),
                ( null == encoderEntity.recording() ) ? "" : encoderEntity.recording().title(),
                ( null == encoderEntity.recording() ) ? "" : encoderEntity.recording().description(),
                 encoderEntity.state() );


        }

        return encoder;
    }

    public static List<Encoder> transformCollection( Collection<EncoderEntity> encoderEntityCollection ) {

        List<Encoder> encoderList = new ArrayList<>( encoderEntityCollection.size() );

        Encoder encoder;
        for( EncoderEntity encoderEntity : encoderEntityCollection ) {

            encoder = transform( encoderEntity );
            if( null != encoder ) {

                encoderList.add( encoder );

            }

        }

        return encoderList;
    }

}
