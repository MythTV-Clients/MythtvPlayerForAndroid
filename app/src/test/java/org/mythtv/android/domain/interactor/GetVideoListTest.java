package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.VideoRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 3/18/16.
 */
public class GetVideoListTest {

    private static final String FAKE_FOLDER = "fake folder";
    private static final String FAKE_SORT = "fake sort";
    private static final boolean FAKE_DESCENDING = false;
    private static final int FAKE_START_INDEX = -1;
    private static final int FAKE_COUNT = -1;

    private GetVideoList getVideoList;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private VideoRepository mockVideoRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        getVideoList = new GetVideoList( FAKE_FOLDER, FAKE_SORT, FAKE_DESCENDING, FAKE_START_INDEX, FAKE_COUNT, mockVideoRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testGetVideoListUseCaseObservableHappyCase() {

        getVideoList.buildUseCaseObservable();

        verify( mockVideoRepository ).getVideoList( FAKE_FOLDER, FAKE_SORT, FAKE_DESCENDING, FAKE_START_INDEX, FAKE_COUNT );
        verifyNoMoreInteractions( mockVideoRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

}
