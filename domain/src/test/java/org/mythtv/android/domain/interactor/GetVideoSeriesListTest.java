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
public class GetVideoSeriesListTest {

    private static final String FAKE_CONTENT_TYPE = "TELEVISION";
    private static final String FAKE_SERIES = "fake series";

    private GetVideoSeriesList getVideoSeriesList;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private VideoRepository mockVideoRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        getVideoSeriesList = new GetVideoSeriesList( FAKE_SERIES, mockVideoRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testGetVideoSeriesListUseCaseObservableHappyCase() {

        getVideoSeriesList.buildUseCaseObservable();

        verify( mockVideoRepository ).getVideoListByContentTypeAndSeries( FAKE_CONTENT_TYPE, FAKE_SERIES );
        verifyNoMoreInteractions( mockVideoRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

}
