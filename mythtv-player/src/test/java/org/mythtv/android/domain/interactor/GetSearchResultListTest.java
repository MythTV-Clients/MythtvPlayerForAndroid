package org.mythtv.android.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.SearchRepository;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by dmfrey on 3/18/16.
 */
public class GetSearchResultListTest {

    private static final String FAKE_SEARCH_TEXT = "fake test search";

    private GetSearchResultList getSearchResultList;

    @Mock
    private ThreadExecutor mockThreadExecutor;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private SearchRepository mockSearchRepository;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        getSearchResultList = new GetSearchResultList( mockSearchRepository, mockThreadExecutor, mockPostExecutionThread );

    }

    @Test
    public void testGetSearchResultListUseCaseObservableHappyCase() {

        Map<String, String> parameters = Collections.singletonMap( "SEARCH_TEXT", FAKE_SEARCH_TEXT );
        getSearchResultList.buildUseCaseObservable( parameters );

        verify( mockSearchRepository ).search( FAKE_SEARCH_TEXT );
        verifyNoMoreInteractions( mockSearchRepository );
        verifyZeroInteractions( mockThreadExecutor );
        verifyZeroInteractions( mockPostExecutionThread );

    }

}
