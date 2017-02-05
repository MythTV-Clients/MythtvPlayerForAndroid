package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.VideoRepository;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 3/18/16.
 */
public class PostUpdatedVideoWatchedStatusTest {

    private static final int FAKE_VIDEO_ID = -1;
    private static final boolean FAKE_WATCHED = false;

    private PostUpdatedVideoWatchedStatus postUpdatedVideoWatchedStatus;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private VideoRepository mockVideoRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        postUpdatedVideoWatchedStatus = new PostUpdatedVideoWatchedStatus( mockVideoRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testPostUpdatedRecordedWatchedStatusDynamicUseCaseObservableHappyCase() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( "VIDEO_ID", FAKE_VIDEO_ID );
        parameters.put( "WATCHED", FAKE_WATCHED );
        postUpdatedVideoWatchedStatus.buildUseCaseObservable( parameters );

        verify( mockVideoRepository ).updateWatchedStatus( FAKE_VIDEO_ID, FAKE_WATCHED );
        verifyNoMoreInteractions( mockVideoRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

}
