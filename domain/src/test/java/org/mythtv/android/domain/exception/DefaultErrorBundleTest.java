package org.mythtv.android.domain.exception;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by dmfrey on 8/26/15.
 */
public class DefaultErrorBundleTest {

    private DefaultErrorBundle defaultErrorBundle;

    @Mock
    private Exception mockException;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

    }

    @Test
    public void testGetErrorMessageInteraction() {

        defaultErrorBundle = new DefaultErrorBundle( mockException );
        defaultErrorBundle.getErrorMessage();

        verify( mockException ).getMessage();
        assertThat( defaultErrorBundle.getException(), not( nullValue() ) );

    }

    @Test
    public void testGetUnknownErrorMessageInteraction() {

        defaultErrorBundle = new DefaultErrorBundle( null );
        defaultErrorBundle.getErrorMessage();

        assertThat( defaultErrorBundle.getErrorMessage(), equalTo( "Unknown error" ) );

    }

}
