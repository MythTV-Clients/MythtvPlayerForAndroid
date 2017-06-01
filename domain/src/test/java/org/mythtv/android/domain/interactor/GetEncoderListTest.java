package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.Encoder;
import org.mythtv.android.domain.TestData;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.DvrRepository;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 3/18/16.
 */
public class GetEncoderListTest extends TestData {

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private DvrRepository mockDvrRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

    }

    @Test
    public void testGetEncoderListUseCaseObservableHappyCase() {

        given( mockDvrRepository.encoders() ).willReturn( setupEncoders() );

        GetEncoderList useCase = new GetEncoderList( mockDvrRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<Encoder>> observable = useCase.buildUseCaseObservable( null );
        TestObserver<List<Encoder>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockDvrRepository ).encoders();
        verifyNoMoreInteractions( mockDvrRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

    private Observable<List<Encoder>> setupEncoders() {

        return Observable.just(Collections.singletonList( createFakeEncoder() ) );
    }
}
