package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 3/18/16.
 */
public class GetLiveStreamsListTest {

    private static final String FAKE_FILENAME = "fake filename";

    private GetLiveStreamsList getLiveStreamsList;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private ContentRepository mockContentRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        getLiveStreamsList = new GetLiveStreamsList( mockContentRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testGetLiveStreamsListUseCaseObservableHappyCase() {

        Map<String, String> parameters = Collections.singletonMap( "FILE_NAME", FAKE_FILENAME );
        getLiveStreamsList.buildUseCaseObservable( parameters );

        verify( mockContentRepository ).liveStreamInfos( FAKE_FILENAME );
        verifyNoMoreInteractions( mockContentRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

}
