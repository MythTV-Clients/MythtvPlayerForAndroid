package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.Media;

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
public final class SeriesEntityDataMapper {

    private SeriesEntityDataMapper() { }

    public static SeriesEntity transform( final TitleInfoEntity titleInfoEntity ) {

        return SeriesEntity.create(
                titleInfoEntity.title(), Media.PROGRAM,
                "/Content/GetRecordingArtwork?Inetref=" + titleInfoEntity.inetref() + "&Type=banner&Height=100",
                titleInfoEntity.count(), titleInfoEntity.inetref() );
    }

    public static List<SeriesEntity> transformTitleInfoEntities( Collection<TitleInfoEntity> titleInfoEntityCollection ) {

        List<SeriesEntity> seriesList = new ArrayList<>( titleInfoEntityCollection.size() );

        for( TitleInfoEntity programEntity : titleInfoEntityCollection ) {

            seriesList.add( transform( programEntity ) );

        }

        return seriesList;
    }

    public static SeriesEntity transform( final VideoMetadataInfoEntity videoMetadataInfoEntity ) {

        return SeriesEntity.create(
                videoMetadataInfoEntity.title(),Media.VIDEO,
                "/Content/GetVideoArtwork?Id=" + videoMetadataInfoEntity.id() + "&Type=banner&Height=100",
                1, videoMetadataInfoEntity.inetref() );
    }

    public static List<SeriesEntity> transformVideos( Collection<VideoMetadataInfoEntity> videoEntityCollection ) {

        List<SeriesEntity> seriesList = new ArrayList<>( videoEntityCollection.size() );

        for( VideoMetadataInfoEntity videoEntity : videoEntityCollection ) {

            seriesList.add( transform( videoEntity ) );

        }

        return seriesList;
    }

}
