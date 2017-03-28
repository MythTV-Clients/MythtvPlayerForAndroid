package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.Media;
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

    public static Series transform( final TitleInfoEntity titleInfoEntity ) {

        Series series = new Series();
        series.setTitle( titleInfoEntity.getTitle() );
        series.setMedia( Media.PROGRAM );
        series.setCount( titleInfoEntity.getCount() );
        series.setArtworkUrl( "/Content/GetRecordingArtwork?Inetref=" + titleInfoEntity.getInetref() + "&Type=banner&Height=100" );
        series.setInetref( titleInfoEntity.getInetref() );

        return series;
    }

    public static List<Series> transformPrograms( Collection<TitleInfoEntity> programEntityCollection ) {

        List<Series> seriesList = new ArrayList<>( programEntityCollection.size() );

        Series series;
        for( TitleInfoEntity programEntity : programEntityCollection ) {

            series = transform( programEntity );
            if( null != series ) {

                seriesList.add( series );

            }

        }

        return seriesList;

    }

    public static Series transform( final VideoMetadataInfoEntity videoMetadataInfoEntity ) {

        Series series = new Series();
        series.setTitle( videoMetadataInfoEntity.getTitle() );
        series.setMedia( Media.VIDEO );
        series.setArtworkUrl( "/Content/GetVideoArtwork?Id=" + videoMetadataInfoEntity.getId() + "&Type=banner&Height=100" );

        return series;
    }

    public static List<Series> transformVideos( Collection<VideoMetadataInfoEntity> videoEntityCollection ) {

        List<Series> seriesList = new ArrayList<>( videoEntityCollection.size() );

        Series series;
        for( VideoMetadataInfoEntity videoEntity : videoEntityCollection ) {

            series = transform( videoEntity );
            if( null != series ) {

                seriesList.add( series );

            }

        }

        return seriesList;

    }

}
