package org.mythtv.android.presentation.mapper;

import org.mythtv.android.domain.Encoder;
import org.mythtv.android.domain.Input;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.EncoderModel;
import org.mythtv.android.presentation.model.InputModel;

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

            if( null != encoder.getInputs() ) {

                List<InputModel> inputModels = new ArrayList<>();
                for( Input input : encoder.getInputs() ) {

                    InputModel inputModel = new InputModel();
                    inputModel.setId( input.getId() );
                    inputModel.setCardId( input.getCardId() );
                    inputModel.setSourceId( input.getSourceId() );
                    inputModel.setInputName( input.getInputName() );
                    inputModel.setDisplayName( input.getDisplayName() );
                    inputModel.setQuickTune( input.isQuickTune() );
                    inputModel.setRecordPriority( input.getRecordPriority() );
                    inputModel.setScheduleOrder( input.getScheduleOrder() );
                    inputModel.setLiveTvOrder( input.getLiveTvOrder() );
                    inputModels.add( inputModel );

                }
                encoderModel.setInputs( inputModels );
            }

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
