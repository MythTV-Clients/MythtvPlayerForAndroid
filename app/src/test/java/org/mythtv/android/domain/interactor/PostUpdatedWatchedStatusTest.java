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
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 3/18/16.
 */
public class PostUpdatedWatchedStatusTest {

    private static final int FAKE_ID = -1;
    private static final boolean FAKE_WATCHED = false;

    private PostUpdatedWatchedStatus postUpdatedWatchedStatus;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private DvrRepository mockDvrRepository;

    @Mock
    private VideoRepository mockVideoRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        postUpdatedWatchedStatus = new PostUpdatedWatchedStatus( mockDvrRepository, mockVideoRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testPostUpdatedRecordedWatchedStatusDynamicUseCaseObservableHappyCase() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( "ID", FAKE_ID );
        parameters.put( "WATCHED", FAKE_WATCHED );
        parameters.put( "MEDIA", Media.PROGRAM.name() );
        postUpdatedWatchedStatus.buildUseCaseObservable( parameters );

        verify( mockDvrRepository ).updateWatchedStatus( FAKE_ID, FAKE_WATCHED );
        verifyNoMoreInteractions( mockDvrRepository );
        verifyZeroInteractions( mockVideoRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

    @Test
    public void testPostUpdatedVideoWatchedStatusDynamicUseCaseObservableHappyCase() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( "ID", FAKE_ID );
        parameters.put( "WATCHED", FAKE_WATCHED );
        parameters.put( "MEDIA", Media.VIDEO.name() );
        postUpdatedWatchedStatus.buildUseCaseObservable( parameters );

        verify( mockVideoRepository ).updateWatchedStatus( FAKE_ID, FAKE_WATCHED );
        verifyNoMoreInteractions( mockVideoRepository );
        verifyZeroInteractions( mockDvrRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

}
