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

        return Series.create(
                titleInfoEntity.title(), Media.PROGRAM,
                "/Content/GetRecordingArtwork?Inetref=" + titleInfoEntity.inetref() + "&Type=banner&Height=100",
                titleInfoEntity.count(), titleInfoEntity.inetref() );
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

        return Series.create(
                videoMetadataInfoEntity.title(),Media.VIDEO,
                "/Content/GetVideoArtwork?Id=" + videoMetadataInfoEntity.id() + "&Type=banner&Height=100",
                1, videoMetadataInfoEntity.inetref() );
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
