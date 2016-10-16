package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;
import org.mythtv.android.domain.repository.VideoRepository;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetRecordedProgramDetailsTest {

    private static final boolean FAKE_DESCENDING = Boolean.FALSE;
    private static final int FAKE_START_INDEX = -1;
    private static final int FAKE_COUNT = -1;
    private static final String FAKE_TITLE_REGEX = "fake title";
    private static final String FAKE_REC_GROUP = "fake rec group";
    private static final String FAKE_STORAGE_GROUP = "fake storage group";
    private static final String FAKE_FOLDER = "fake folder";
    private static final String FAKE_SORT = "fake sort";

    private GetMediaItemList getMediaItemList;

    @Mock
    private DvrRepository mockDvrRepository;

    @Mock
    private VideoRepository mockVideoRepository;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

        getMediaItemList = new GetMediaItemList( mockDvrRepository, mockVideoRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testGetMediaItemListUseCaseObservableHappyCase_Recorded() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( GetMediaItemList.MEDIA_KEY, Media.PROGRAM );
        parameters.put( GetMediaItemList.DESCENDING_KEY, FAKE_DESCENDING );
        parameters.put( GetMediaItemList.START_INDEX_KEY, FAKE_START_INDEX );
        parameters.put( GetMediaItemList.COUNT_KEY, FAKE_COUNT );
        parameters.put( GetMediaItemList.TITLE_REGEX_KEY, FAKE_TITLE_REGEX );
        parameters.put( GetMediaItemList.REC_GROUP_KEY, FAKE_REC_GROUP );
        parameters.put( GetMediaItemList.STORAGE_GROUP_KEY, FAKE_STORAGE_GROUP );

        getMediaItemList.buildUseCaseObservable( parameters );

        verify( mockDvrRepository ).recordedPrograms( FAKE_DESCENDING, FAKE_START_INDEX, FAKE_COUNT, FAKE_TITLE_REGEX, FAKE_REC_GROUP, FAKE_STORAGE_GROUP );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void testGetMediaItemListUseCaseObservableHappyCase_Recent() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( GetMediaItemList.MEDIA_KEY, Media.RECENT );

        getMediaItemList.buildUseCaseObservable( parameters );

        verify( mockDvrRepository ).recent();
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void testGetMediaItemListUseCaseObservableHappyCase_Upcoming() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( GetMediaItemList.MEDIA_KEY, Media.UPCOMING );

        getMediaItemList.buildUseCaseObservable( parameters );

        verify( mockDvrRepository ).upcoming( -1, -1, Boolean.FALSE, -1, -1 );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void testGetMediaItemListUseCaseObservableHappyCase_Videos() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( GetMediaItemList.MEDIA_KEY, Media.VIDEO );
        parameters.put( GetMediaItemList.FOLDER_KEY, FAKE_FOLDER );
        parameters.put( GetMediaItemList.SORT_KEY, FAKE_SORT );
        parameters.put( GetMediaItemList.COUNT_KEY, FAKE_COUNT );
        parameters.put( GetMediaItemList.START_INDEX_KEY, FAKE_START_INDEX );
        parameters.put( GetMediaItemList.COUNT_KEY, FAKE_COUNT );

        getMediaItemList.buildUseCaseObservable( parameters );

        verify( mockVideoRepository ).getVideoList( FAKE_FOLDER, FAKE_SORT, FAKE_DESCENDING, FAKE_START_INDEX, FAKE_COUNT );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

}
