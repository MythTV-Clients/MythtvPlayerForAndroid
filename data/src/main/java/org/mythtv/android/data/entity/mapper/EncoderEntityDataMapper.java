package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.domain.Encoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dmfrey on 1/18/16.
 */
@Singleton
public class EncoderEntityDataMapper {

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
