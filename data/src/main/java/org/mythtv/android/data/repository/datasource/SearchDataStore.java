package org.mythtv.android.data.repository.datasource;

import org.mythtv.android.data.entity.SearchResultEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;

import java.util.Collection;
import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 10/8/15.
 */
public interface SearchDataStore {

    Observable<List<SearchResultEntity>> search( String searchString );

    void refreshTitleInfoData( Collection<TitleInfoEntity> titleInfoEntityCollection );

    void refreshRecordedProgramData( Collection<SearchResultEntity> searchResultEntityCollection );

    void refreshVideoData( Collection<SearchResultEntity> searchResultEntityCollection );

}
