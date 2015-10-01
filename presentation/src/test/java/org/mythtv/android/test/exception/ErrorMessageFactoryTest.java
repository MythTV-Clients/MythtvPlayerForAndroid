package org.mythtv.android.test.exception;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.data.exception.ProgramNotFoundException;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.R;
import org.mythtv.android.test.ApplicationTestCase;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class ErrorMessageFactoryTest extends ApplicationTestCase {

    @Mock
    private Context context;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks( this );

    }

    @Test
    public void testNetworkConnectionErrorMessage() {

        when( context.getString( R.string.exception_message_no_connection ) ).thenReturn( "There is no internet connection" );

        String expectedMessage = context.getString( R.string.exception_message_no_connection );
        String actualMessage = ErrorMessageFactory.create( context, new NetworkConnectionException() );

        assertThat( actualMessage, is( equalTo( expectedMessage ) ) );

    }

    @Test
    public void testProgramNotFoundErrorMessage() {

        when( context.getString( R.string.exception_message_program_not_found ) ).thenReturn( "Cannot retrieve program data. Check your internet connection" );

        String expectedMessage = context.getString(R.string.exception_message_program_not_found);
        String actualMessage = ErrorMessageFactory.create( context, new ProgramNotFoundException() );

        assertThat( actualMessage, is( equalTo( expectedMessage ) ) );

    }

}
