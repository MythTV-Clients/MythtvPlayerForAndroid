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
public class GetVideoContentTypeListTest {

    private static final String FAKE_CONTENT_TYPE = "fake content type";

    private GetVideoContentTypeList getVideoContentTypeList;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private VideoRepository mockVideoRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        getVideoContentTypeList = new GetVideoContentTypeList( FAKE_CONTENT_TYPE, mockVideoRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testGetVideoListUseCaseObservableHappyCase() {

        getVideoContentTypeList.buildUseCaseObservable();

        verify( mockVideoRepository ).getVideoListByContentType( FAKE_CONTENT_TYPE );
        verifyNoMoreInteractions( mockVideoRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

}
