package org.mythtv.android.data.exception;

import org.mythtv.android.data.ApplicationTestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class RepositoryErrorBundleTest extends ApplicationTestCase {

    private RepositoryErrorBundle repositoryErrorBundle;

    @Mock
    private Exception mockException;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        repositoryErrorBundle = new RepositoryErrorBundle( mockException );

    }

    @Test
    public void testGetErrorMessageInteraction() {

        repositoryErrorBundle.getErrorMessage();

        verify( mockException ).getMessage();
    }

}
