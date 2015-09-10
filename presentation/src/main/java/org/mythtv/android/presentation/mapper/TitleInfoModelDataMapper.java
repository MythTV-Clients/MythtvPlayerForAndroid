package org.mythtv.android.presentation.mapper;

import android.util.Log;

import org.mythtv.android.domain.TitleInfo;
import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.presentation.model.TitleInfoModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by dmfrey on 8/27/15.
 */
@PerActivity
public class TitleInfoModelDataMapper {

    private static final String TAG = TitleInfoModelDataMapper.class.getSimpleName();
    @Inject
    public TitleInfoModelDataMapper() {
    }

    public TitleInfoModel transform( TitleInfo titleInfo ) {
        Log.d( TAG, "transform : enter" );

        TitleInfoModel titleInfoModel = null;
        if( null != titleInfo ) {

            titleInfoModel = new TitleInfoModel();
            titleInfoModel.setTitle( titleInfo.getTitle() );
            titleInfoModel.setInetref( titleInfo.getInetref() );
            titleInfoModel.setCount( titleInfo.getCount() );

        }

        Log.d( TAG, "transform : titleInfoModle=" + titleInfoModel );
        Log.d( TAG, "transform : exit" );
        return titleInfoModel;
    }

    public List<TitleInfoModel> transform( Collection<TitleInfo> titleInfoCollection ) {
        Log.d( TAG, "transform : enter" );

        List<TitleInfoModel> titleInfoModelList = new ArrayList<>( titleInfoCollection.size() );

        TitleInfoModel titleInfoModel;
        for( TitleInfo titleInfo : titleInfoCollection ) {

            titleInfoModel = transform( titleInfo );
            if( null != titleInfoModel ) {
                Log.d( TAG, "transform : titleInfoModel is not null" );

                titleInfoModelList.add( titleInfoModel );

            }

        }

        Log.d( TAG, "transform : exit" );
        return titleInfoModelList;
    }

}
