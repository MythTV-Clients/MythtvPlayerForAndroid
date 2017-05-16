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

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mythtv.android.domain.interactor.GetSeriesList.MEDIA_KEY;

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

    @Test( expected = UnsupportedOperationException.class )
    public void givenGetSeriesList_whenNoParameters_verifyUnsupportedOperationExceptionThrown() {

        GetSeriesList useCase = new GetSeriesList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        useCase.buildUseCaseObservable();

        verifyZeroInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test( expected = IllegalArgumentException.class )
    public void givenGetSeriesList_whenNullParameters_verifyIllegalArgumentExceptionThrown() {

        GetSeriesList useCase = new GetSeriesList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        useCase.buildUseCaseObservable( null );

        verifyZeroInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test( expected = IllegalArgumentException.class )
    public void givenGetSeriesList_whenEmptyParameters_verifyIllegalArgumentExceptionThrown() {

        GetSeriesList useCase = new GetSeriesList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        useCase.buildUseCaseObservable( Collections.emptyMap() );

        verifyZeroInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetSeriesList_whenMediaProgram_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.series( any( Media.class ) ) ).willReturn( setupSeries() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.PROGRAM );

        GetSeriesList useCase = new GetSeriesList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<Series>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<Series>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).series( any( Media.class ) );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test( expected = IllegalArgumentException.class)
    public void givenGetSeriesList_whenMediaRecent_verifyIllegalArgumentExceptionThrown() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.RECENT );

        GetSeriesList useCase = new GetSeriesList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        useCase.buildUseCaseObservable( parameters );

        verifyZeroInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetSeriesList_whenMediaVideo_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.series( any( Media.class ) ) ).willReturn( setupSeries() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.VIDEO );

        GetSeriesList useCase = new GetSeriesList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<Series>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<Series>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository ).series( any( Media.class ) );
        verifyZeroInteractions( mockPostExecutionThread );
        verifyZeroInteractions( mockThreadExecutor );

    }

    @Test
    public void givenGetSeriesList_whenTelevision_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.series( any( Media.class ) ) ).willReturn( setupSeries() );

        Map<String, Object> parameters = new HashMap<>();
        parameters.put( MEDIA_KEY, Media.TELEVISION );

        GetSeriesList useCase = new GetSeriesList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<Series>> observable = useCase.buildUseCaseObservable( parameters );
        TestSubscriber<List<Series>> testSubscriber = new TestSubscriber<>();
        observable.subscribe( testSubscriber );

        assertThat( testSubscriber.getOnNextEvents().get( 0 ) )
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
