package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.domain.TitleInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dmfrey on 8/27/15.
 */
@Singleton
public class TitleInfoEntityDataMapper {

    private TitleInfoEntityDataMapper() { }

    public static TitleInfo transform( TitleInfoEntity titleInfoEntity ) {

        TitleInfo titleInfo = null;
        if( null != titleInfoEntity ) {
            titleInfo = new TitleInfo();
            titleInfo.setTitle( titleInfoEntity.getTitle() );
            titleInfo.setInetref( titleInfoEntity.getInetref() );
            titleInfo.setCount( titleInfoEntity.getCount() );

        }

        return titleInfo;
    }

    public static List<TitleInfo> transform( Collection<TitleInfoEntity> titleInfoEntityCollection ) {

        List<TitleInfo> titleInfoList = new ArrayList<>( titleInfoEntityCollection.size() );

        TitleInfo titleInfo;
        for( TitleInfoEntity titleInfoEntity : titleInfoEntityCollection ) {

            titleInfo = transform( titleInfoEntity );
            if( null != titleInfo ) {

                titleInfoList.add( titleInfo );

            }

        }

        return titleInfoList;
    }

}
