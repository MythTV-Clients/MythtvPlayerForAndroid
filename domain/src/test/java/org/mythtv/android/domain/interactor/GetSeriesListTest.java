package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.Series;
import org.mythtv.android.domain.TestData;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.MediaItemRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 8/26/15.
 */
public class GetSeriesListTest extends TestData {

    @Mock private MediaItemRepository mockMediaItemRepository;
    @Mock private ThreadExecutor mockThreadExecutor;
    @Mock private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

    }

    @Test( expected = NullPointerException.class )
    public void givenGetSeriesList_whenNullParameters_verifyIllegalArgumentExceptionThrown() {

        GetSeriesList useCase = new GetSeriesList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        useCase.buildUseCaseObservable( null );

        verifyZeroInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetSeriesList_whenMediaProgram_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.series( any( Media.class ) ) ).willReturn( setupSeries() );

        GetSeriesList useCase = new GetSeriesList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<Series>> observable = useCase.buildUseCaseObservable( GetSeriesList.Params.forMedia( Media.PROGRAM ) );
        TestObserver<List<Series>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).series( any( Media.class ) );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetSeriesList_whenMediaVideo_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.series( any( Media.class ) ) ).willReturn( setupSeries() );

        GetSeriesList useCase = new GetSeriesList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<Series>> observable = useCase.buildUseCaseObservable( GetSeriesList.Params.forMedia( Media.VIDEO ) );
        TestObserver<List<Series>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).series( any( Media.class ) );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetSeriesList_whenTelevision_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.series( any( Media.class ) ) ).willReturn( setupSeries() );

        GetSeriesList useCase = new GetSeriesList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<Series>> observable = useCase.buildUseCaseObservable( GetSeriesList.Params.forMedia( Media.TELEVISION ) );
        TestObserver<List<Series>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).series( any( Media.class ) );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    private Observable<List<Series>> setupSeries() {

        return Observable.just( Collections.singletonList( createFakeSeries() ) );
    }

}
