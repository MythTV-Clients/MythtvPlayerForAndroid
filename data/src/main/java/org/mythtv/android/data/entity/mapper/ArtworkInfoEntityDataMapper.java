package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.domain.ArtworkInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dmfrey on 1/18/16.
 */
@Singleton
public class ArtworkInfoEntityDataMapper {

    private ArtworkInfoEntityDataMapper() { }

    public static ArtworkInfo transform( ArtworkInfoEntity artworkInfoEntity ) {

        ArtworkInfo artworkInfo = null;
        if( null != artworkInfoEntity ) {

            artworkInfo = new ArtworkInfo();
            artworkInfo.setUrl( artworkInfoEntity.getUrl() );
            artworkInfo.setFileName( artworkInfoEntity.getFileName() );
            artworkInfo.setStorageGroup( artworkInfoEntity.getStorageGroup() );
            artworkInfo.setType( artworkInfoEntity.getType() );

        }

        return artworkInfo;
    }

    public static List<ArtworkInfo> transformCollection( Collection<ArtworkInfoEntity> artworkInfoEntityCollection ) {

        List<ArtworkInfo> artworkInfoList = new ArrayList<>( artworkInfoEntityCollection.size() );

        ArtworkInfo artworkInfo;
        for( ArtworkInfoEntity artworkInfoEntity : artworkInfoEntityCollection ) {

            artworkInfo = transform( artworkInfoEntity );
            if( null != artworkInfo ) {

                artworkInfoList.add( artworkInfo );

            }

        }

        return artworkInfoList;
    }

}
