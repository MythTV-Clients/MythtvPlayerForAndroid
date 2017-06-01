package org.mythtv.android.presentation.mapper;

import org.junit.Test;
import org.mythtv.android.domain.Encoder;
import org.mythtv.android.presentation.TestData;
import org.mythtv.android.presentation.model.EncoderModel;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 5/17/17.
 */

public class EncoderModelDataMapperTest extends TestData {

    private EncoderModelDataMapper mapper = new EncoderModelDataMapper();

    @Test
    public void testTransform_givenEncoder() {

        EncoderModel model = mapper.transform( createFakeEncoder() );
        assertThat( model ).isNotNull();
        assertThat( model.id() ).isEqualTo( FAKE_ID );
        assertThat( model.inputName() ).isEqualTo( FAKE_INPUT_NAME );
        assertThat( model.recordingName() ).isEqualTo( FAKE_TITLE );
        assertThat( model.recordingDescription() ).isEqualTo( FAKE_DESCRIPTION );
        assertThat( model.state() ).isEqualTo( FAKE_STATE );

    }

    @Test
    public void testTransform_givenEncoder_whenEncoderIsNull_verifyNullReturned() {

        EncoderModel model = mapper.transform( (Encoder) null );
        assertThat( model ).isNull();

    }

    @Test
    public void testTransform_givenEncoderCollection() {

        List<EncoderModel> modelList = mapper.transform( setupEncoders() );
        assertThat( modelList )
                .isNotNull()
                .hasSize( 1 );

    }

    private List<Encoder> setupEncoders() {

        return Collections.singletonList( createFakeEncoder() );
    }

}
