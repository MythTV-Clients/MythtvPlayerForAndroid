package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.domain.Series;

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
 * Created on 9/24/16.
 */
@Singleton
public final class SeriesDataMapper {

    private SeriesDataMapper() { }

    public static Series transform( final SeriesEntity seriesEntity ) {

        return Series.create(
                seriesEntity.title(),
                seriesEntity.media(),
                seriesEntity.artworkUrl(),
                seriesEntity.count(),
                seriesEntity.inetref() );
    }

    public static List<Series> transform( Collection<SeriesEntity> seriesEntityCollection ) {

        List<Series> seriesList = new ArrayList<>( seriesEntityCollection.size() );

        for( SeriesEntity programEntity : seriesEntityCollection ) {

            seriesList.add( transform( programEntity ) );

        }

        return seriesList;

    }

}
