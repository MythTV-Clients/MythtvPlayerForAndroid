package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;
import org.mythtv.android.domain.repository.VideoRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetVideoTest {

    private static final int FAKE_VIDEO_ID = 999;

    private GetVideo getVideo;

    @Mock
    private VideoRepository mockVideoRepository;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        getVideo = new GetVideo( FAKE_VIDEO_ID, mockVideoRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testGetVideoUseCaseObservableHappyCase() {

        getVideo.buildUseCaseObservable();

        verify( mockVideoRepository ).getVideo( FAKE_VIDEO_ID );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

}
