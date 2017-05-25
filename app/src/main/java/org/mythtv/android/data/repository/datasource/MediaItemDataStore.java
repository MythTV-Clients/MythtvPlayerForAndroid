package org.mythtv.android.data.repository.datasource;

import org.mythtv.android.data.entity.MediaItemEntity;
import org.mythtv.android.data.entity.SeriesEntity;
import org.mythtv.android.domain.Media;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 4/21/17.
 */

public interface MediaItemDataStore {

    Observable<List<SeriesEntity>> series( Media media );

    Observable<List<MediaItemEntity>> mediaItems( Media media, String title );

    Observable<MediaItemEntity> mediaItem( Media media, int id );

    Observable<MediaItemEntity> addLiveStream( Media media, int id );

    Observable<MediaItemEntity> removeLiveStream( Media media, int id );

    Observable<MediaItemEntity> updateWatchedStatus( Media media, int id, boolean watched );

}
