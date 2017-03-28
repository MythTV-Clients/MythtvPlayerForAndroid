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
import org.mythtv.android.data.entity.InputEntity;
import org.mythtv.android.domain.Encoder;
import org.mythtv.android.domain.Input;

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

            encoder = new Encoder();
            encoder.setId( encoderEntity.getId() );
            encoder.setHostname( encoderEntity.getHostname() );
            encoder.setLocal( encoderEntity.isLocal() );
            encoder.setConnected( encoderEntity.isConnected() );
            encoder.setState( encoderEntity.getState() );
            encoder.setSleepStatus( encoderEntity.getSleepStatus() );
            encoder.setLowOnFreeSpace( encoderEntity.isLowOnFreeSpace() );

            if( null != encoderEntity.getInputs() ) {

                List<Input> inputs = new ArrayList<>();
                for( InputEntity inputEntity : encoderEntity.getInputs() ) {

                    Input input = new Input();
                    input.setId( inputEntity.getId() );
                    input.setCardId( inputEntity.getCardId() );
                    input.setSourceId( inputEntity.getSourceId() );
                    input.setInputName( inputEntity.getInputName() );
                    input.setDisplayName( inputEntity.getDisplayName() );
                    input.setQuickTune( inputEntity.isQuickTune() );
                    input.setRecordPriority( inputEntity.getRecordPriority() );
                    input.setScheduleOrder( inputEntity.getScheduleOrder() );
                    input.setLiveTvOrder( inputEntity.getLiveTvOrder() );
                    inputs.add( input );

                }
                encoder.setInputs( inputs );
            }

            if( null != encoderEntity.getRecording() ) {

                encoder.setRecording( ProgramEntityDataMapper.transform( encoderEntity.getRecording() ) );

            }

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
