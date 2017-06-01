package org.mythtv.android.presentation.mapper;

import org.junit.Test;
import org.mythtv.android.domain.Series;
import org.mythtv.android.presentation.TestData;
import org.mythtv.android.presentation.model.SeriesModel;

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

public class SeriesModelDataMapperTest extends TestData {

    private SeriesModelDataMapper mapper = new SeriesModelDataMapper();

    @Test
    public void testTransform_givenSeries() {

        SeriesModel model = mapper.transform( createFakeSeries() );
        assertThat( model ).isNotNull();
        assertThat( model.title() ).isEqualTo( FAKE_TITLE );
        assertThat( model.media() ).isEqualTo( FAKE_MEDIA );
        assertThat( model.artworkUrl() ).isEqualTo( FAKE_ARTWORK );
        assertThat( model.count() ).isEqualTo( FAKE_COUNT );
        assertThat( model.inetref() ).isEqualTo( FAKE_INETREF );

    }

    @Test
    public void testTransform_givenSeries_whenSeriesIsNull_verifyNullReturned() {

        SeriesModel model = mapper.transform( (Series) null );
        assertThat( model ).isNull();

    }

    @Test
    public void testTransform_givenEncoderCollection() {

        List<SeriesModel> modelList = mapper.transform( setupSeries() );
        assertThat( modelList )
                .isNotNull()
                .hasSize( 1 );

    }

    private List<Series> setupSeries() {

        return Collections.singletonList( createFakeSeries() );
    }

}
