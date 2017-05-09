package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.MediaItemRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetMediaItemListTest {

    private GetMediaItemDetails getMediaItemDetails;

    @Mock
    private MediaItemRepository mockMediaItemRepository;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

    }

    @Test
    public void testGetMediaItemForRecordedProgramDetailsUseCaseObservableHappyCase() {

        Media fakeMedia = Media.PROGRAM;
        int FAKE_RECORDED_ID = 999;

        getMediaItemDetails = new GetMediaItemDetails( fakeMedia, FAKE_RECORDED_ID, mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        getMediaItemDetails.buildUseCaseObservable();

        verify( mockMediaItemRepository ).mediaItem( fakeMedia, FAKE_RECORDED_ID );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void testGetMediaItemForVideoDetailsUseCaseObservableHappyCase() {

        Media fakeMedia = Media.PROGRAM;
        int FAKE_ID = 999;

        getMediaItemDetails = new GetMediaItemDetails( fakeMedia, FAKE_ID, mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        getMediaItemDetails.buildUseCaseObservable();

        verify( mockMediaItemRepository ).mediaItem( fakeMedia, FAKE_ID );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

}
