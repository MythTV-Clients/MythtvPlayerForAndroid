package org.mythtv.android.presentation.mapper;

import org.mythtv.android.domain.Encoder;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.EncoderModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 1/20/16.
 */
@PerActivity
public class EncoderModelDataMapper {

    private final ProgramModelDataMapper programModelDataMapper;

    @Inject
    public EncoderModelDataMapper( ProgramModelDataMapper programModelDataMapper ) {

        this.programModelDataMapper = programModelDataMapper;

    }

    public EncoderModel transform( Encoder encoder ) {

        EncoderModel encoderModel = null;
        if( null != encoder ) {

            encoderModel = new EncoderModel();
            encoderModel.setId( encoder.getId() );
            encoderModel.setHostname( encoder.getHostname() );
            encoderModel.setLocal( encoder.isLocal() );
            encoderModel.setConnected( encoder.isConnected() );
            encoderModel.setState( encoder.getState() );
            encoderModel.setSleepStatus( encoder.getSleepStatus() );
            encoderModel.setLowOnFreeSpace( encoder.isLowOnFreeSpace() );

            if( null != encoder.getRecording() ) {

                encoderModel.setRecording( programModelDataMapper.transform( encoder.getRecording() ) );

            }

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
