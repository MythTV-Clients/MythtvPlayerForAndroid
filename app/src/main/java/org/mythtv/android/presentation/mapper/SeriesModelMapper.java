package org.mythtv.android.presentation.mapper;

import org.mythtv.android.domain.Series;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.SeriesModel;

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
 * Created on 7/10/16.
 */

@PerActivity
public class SeriesModelMapper {

    @Inject
    public SeriesModelMapper() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public SeriesModel transform( Series series ) {

        return SeriesModel.create( series.title(), series.media(), series.artworkUrl(), series.count(), series.inetref() );
    }

    public List<SeriesModel> transform( Collection<Series> seriesCollection ) {

        List<SeriesModel> seriesList = new ArrayList<>( seriesCollection.size() );

        SeriesModel seriesModel;
        for( Series series : seriesCollection ) {

            seriesModel = transform( series );
            if( null != seriesModel ) {

                seriesList.add( seriesModel );

            }

        }

        return seriesList;
    }

}
