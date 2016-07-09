package org.mythtv.android.domain.interactor;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 3/18/16.
 */
public class PostUpdatedRecordedWatchedStatusTest {

    private static final int FAKE_CHAN_ID = -1;
    private static final DateTime FAKE_START_TIME = new DateTime();
    private static final boolean FAKE_WATCHED = false;

    private PostUpdatedRecordedWatchedStatus postUpdatedRecordedWatchedStatus;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private DvrRepository mockDvrRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        postUpdatedRecordedWatchedStatus = new PostUpdatedRecordedWatchedStatus( mockDvrRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testPostUpdatedRecordedWatchedStatusDynamicUseCaseObservableHappyCase() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( "CHAN_ID", FAKE_CHAN_ID );
        parameters.put( "START_TIME", FAKE_START_TIME );
        parameters.put( "WATCHED", FAKE_WATCHED );
        postUpdatedRecordedWatchedStatus.buildUseCaseObservable( parameters );

        verify( mockDvrRepository ).updateWatchedStatus( FAKE_CHAN_ID, FAKE_START_TIME, FAKE_WATCHED );
        verifyNoMoreInteractions( mockDvrRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

}
