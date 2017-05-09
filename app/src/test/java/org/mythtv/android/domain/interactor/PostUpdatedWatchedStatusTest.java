package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.MediaItemRepository;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 3/18/16.
 */
public class PostUpdatedWatchedStatusTest {

    private static final Media FAKE_MEDIA = Media.PROGRAM;
    private static final int FAKE_ID = -1;
    private static final boolean FAKE_WATCHED = false;

    private PostUpdatedWatchedStatus postUpdatedWatchedStatus;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private MediaItemRepository mockMediaItemRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        postUpdatedWatchedStatus = new PostUpdatedWatchedStatus( FAKE_MEDIA, FAKE_ID, mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testPostUpdatedRecordedWatchedStatusDynamicUseCaseObservableHappyCase() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( "WATCHED", FAKE_WATCHED );
        postUpdatedWatchedStatus.buildUseCaseObservable( parameters );

        verify( mockMediaItemRepository ).updateWatchedStatus( FAKE_MEDIA, FAKE_ID, FAKE_WATCHED );
        verifyNoMoreInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

}
