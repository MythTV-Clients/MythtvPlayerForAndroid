package org.mythtv.android.data.exception;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class RepositoryErrorBundleTest {

    private RepositoryErrorBundle repositoryErrorBundle;

    @Mock
    private Exception mockException;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        repositoryErrorBundle = new RepositoryErrorBundle( mockException );

    }

    @Test( expected = Exception.class )
    public void testGetExceptionInteraction() throws Exception{

        throw repositoryErrorBundle.getException();

    }

    @Test
    public void testGetErrorMessageInteraction() {

        repositoryErrorBundle.getErrorMessage();

        verify( mockException ).getMessage();
    }

}
