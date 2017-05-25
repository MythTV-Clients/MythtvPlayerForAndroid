package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.TestData;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.MediaItemRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 3/18/16.
 */
public class GetSearchResultListTest extends TestData {

    private static final String FAKE_SEARCH_TEXT = "fake test search";

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private MediaItemRepository mockMediaItemRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

    }

    @Test( expected = NullPointerException.class )
    public void givenGetSearchResultList_whenNullParameters_verifyIllegalArgumentExceptionThrown() {

        GetSearchResultList useCase = new GetSearchResultList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        useCase.buildUseCaseObservable( null );

        verifyZeroInteractions( mockMediaItemRepository );
        verifyNoMoreInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

    @Test
    public void whenGetSearchResultList_verifyMediaItemsReturned() {

        given( mockMediaItemRepository.search( anyString() ) ).willReturn( setupMediaItems() );

        Map<String, String> parameters = Collections.singletonMap( "SEARCH_TEXT", FAKE_TITLE );

        GetSearchResultList useCase = new GetSearchResultList( mockMediaItemRepository, mockThreadExecutor, mockPostExecutionThread );
        Observable<List<MediaItem>> observable = useCase.buildUseCaseObservable( GetSearchResultList.Params.forSearch( FAKE_TITLE ) );
        TestObserver<List<MediaItem>> testObserver = new TestObserver<>();
        observable.subscribe( testObserver );

        assertThat( testObserver.values().get( 0 ) )
                .isNotNull()
                .hasSize( 1 );

        verify( mockMediaItemRepository, times( 1 ) ).search( anyString() );
        verifyNoMoreInteractions( mockMediaItemRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

    private Observable<List<MediaItem>> setupMediaItems() {

        return Observable.just( Collections.singletonList( createFakeMediaItem() ) );
    }

}
