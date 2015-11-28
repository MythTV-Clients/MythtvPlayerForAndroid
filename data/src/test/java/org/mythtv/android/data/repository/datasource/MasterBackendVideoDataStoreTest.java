package org.mythtv.android.data.repository.datasource;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.cache.VideoCache;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.data.net.VideoApi;
import org.mythtv.android.domain.ContentType;

import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 11/12/15.
 */
public class MasterBackendVideoDataStoreTest extends ApplicationTestCase {

    private static final int FAKE_VIDEO_ID = 999;

    private MasterBackendVideoDataStore masterBackendVideoDataStore;

    @Mock
    private VideoApi mockVideoApi;

    @Mock
    private VideoCache mockVideoCache;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        masterBackendVideoDataStore = new MasterBackendVideoDataStore( mockVideoApi, mockVideoCache );

    }

    @Test
    public void testGetVideoEntityListFromApi() {

        List<VideoMetadataInfoEntity> fakeVideoMetadataInfoEntities = new ArrayList<>();
        VideoMetadataInfoEntity fakeVideoMetadataInfoEntity = new VideoMetadataInfoEntity();
        fakeVideoMetadataInfoEntity.setContentType( ContentType.MOVIE );
        fakeVideoMetadataInfoEntities.add( fakeVideoMetadataInfoEntity );
        Observable<List<VideoMetadataInfoEntity>> fakeObservable = Observable.just( fakeVideoMetadataInfoEntities );
        given( mockVideoApi.getVideoList( null, null, false, -1, -1 ) ).willReturn( fakeObservable );

        masterBackendVideoDataStore.getVideos(null, null, false, -1, -1);
        verify( mockVideoApi ).getVideoList( null, null, false, -1, -1);

    }

    @Test
    public void testGetVideoEntityDetailsFromApi() {

        VideoMetadataInfoEntity fakeVideoMetadataInfoEntity = new VideoMetadataInfoEntity();
        Observable<VideoMetadataInfoEntity> fakeObservable = Observable.just( fakeVideoMetadataInfoEntity );
        given( mockVideoApi.getVideoById( FAKE_VIDEO_ID ) ).willReturn( fakeObservable );

        masterBackendVideoDataStore.getVideoById(FAKE_VIDEO_ID);

        verify( mockVideoApi ).getVideoById( FAKE_VIDEO_ID );

    }

}
