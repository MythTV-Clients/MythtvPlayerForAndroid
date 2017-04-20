package org.mythtv.android.domain.repository;

import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.Series;

import java.util.List;

import rx.Observable;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 4/14/17.
 */
public interface MediaItemRepository {

    Observable<List<Series>> series( Media media );

    Observable<List<MediaItem>> mediaItems( Media media, String title );

    Observable<MediaItem> mediaItem( int id );

    Observable<List<MediaItem>> search( String searchString );

}
