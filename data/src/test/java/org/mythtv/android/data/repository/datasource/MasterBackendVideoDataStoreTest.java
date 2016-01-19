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
import org.mythtv.android.data.entity.mapper.SearchResultEntityDataMapper;
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
    private static final String FAKE_VIDEO_FILENAME= "filename";

    private MasterBackendVideoDataStore masterBackendVideoDataStore;

    @Mock
    private VideoApi mockVideoApi;

    @Mock
    private VideoCache mockVideoCache;

    @Mock
    private SearchDataStoreFactory mockSearchDataStoreFactory;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        masterBackendVideoDataStore = new MasterBackendVideoDataStore( mockVideoApi, mockVideoCache, mockSearchDataStoreFactory );

    }

    @Test
    public void testGetVideos() {

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
    public void testGetCategory() {

        List<VideoMetadataInfoEntity> fakeVideoMetadataInfoEntities = new ArrayList<>();
        VideoMetadataInfoEntity fakeVideoMetadataInfoEntity = new VideoMetadataInfoEntity();
        fakeVideoMetadataInfoEntity.setContentType( ContentType.MOVIE );
        fakeVideoMetadataInfoEntities.add( fakeVideoMetadataInfoEntity );
        Observable<List<VideoMetadataInfoEntity>> fakeObservable = Observable.just( fakeVideoMetadataInfoEntities );
        given( mockVideoApi.getVideoList( null, null, false, -1, -1 ) ).willReturn( fakeObservable );

        masterBackendVideoDataStore.getCategory( null );
        verify( mockVideoApi ).getVideoList( null, null, false, -1, -1);

    }

    @Test
    public void testGetVideoById() {

        VideoMetadataInfoEntity fakeVideoMetadataInfoEntity = new VideoMetadataInfoEntity();
        Observable<VideoMetadataInfoEntity> fakeObservable = Observable.just( fakeVideoMetadataInfoEntity );
        given( mockVideoApi.getVideoById( FAKE_VIDEO_ID ) ).willReturn( fakeObservable );

        masterBackendVideoDataStore.getVideoById(FAKE_VIDEO_ID);

        verify( mockVideoApi ).getVideoById( FAKE_VIDEO_ID );

    }

    @Test
    public void testGetVideoByFilename() {

        VideoMetadataInfoEntity fakeVideoMetadataInfoEntity = new VideoMetadataInfoEntity();
        Observable<VideoMetadataInfoEntity> fakeObservable = Observable.just( fakeVideoMetadataInfoEntity );
        given( mockVideoApi.getVideoByFilename( FAKE_VIDEO_FILENAME ) ).willReturn( fakeObservable );

        masterBackendVideoDataStore.getVideoByFilename( FAKE_VIDEO_FILENAME );

        verify( mockVideoApi ).getVideoByFilename( FAKE_VIDEO_FILENAME );

    }

}
