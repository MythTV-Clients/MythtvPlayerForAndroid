package org.mythtv.android.data.net;

import org.mythtv.android.data.entity.VideoMetadataInfoEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 11/9/15.
 */
public interface VideoApi {

    String VIDEO_LIST_BASE_URL = "/Video/GetVideoList";
    String VIDEO_BASE_URL = "/Video/GetVideo?";

    String FOLDER_QS = "Folder=%s";
    String SORT_QS = "Sort=%s";
    String DESCENDING_QS = "Descending=%s";
    String START_INDEX_QS = "StartIndex=%s";
    String COUNT_QS = "Count=%s";
    String ID_QS = "Id=%s";
    String FILENAME_QS = "FileName=%s";

    Observable<List<VideoMetadataInfoEntity>> getVideoList( final String folder, final String sort, final boolean descending, final int startIndex, final int count );

    Observable<VideoMetadataInfoEntity> getVideoById( final int id );

    Observable<VideoMetadataInfoEntity> getVideoByFilename( final String filename );

}
