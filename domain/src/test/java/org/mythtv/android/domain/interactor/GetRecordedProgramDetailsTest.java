package org.mythtv.android.domain.interactor;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetRecordedProgramDetailsTest {

    private static final int FAKE_CHAN_ID = 999;
    private static final DateTime FAKE_START_TIME = new DateTime();

    private GetRecordedProgramDetails getRecordedProgramDetails;

    @Mock
    private DvrRepository mockDvrRepository;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        getRecordedProgramDetails = new GetRecordedProgramDetails( FAKE_CHAN_ID, FAKE_START_TIME, mockDvrRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testGetUserDetailsUseCaseObservableHappyCase() {

        getRecordedProgramDetails.buildUseCaseObservable();

        verify( mockDvrRepository ).recordedProgram( FAKE_CHAN_ID, FAKE_START_TIME );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

}
