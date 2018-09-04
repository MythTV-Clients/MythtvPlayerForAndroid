package org.mythtv.android.presentation.exception;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.data.exception.ProgramNotFoundException;
import org.mythtv.android.R;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ErrorMessageFactoryTest {

    private Context context;

    @Before
    public void setUp() throws Exception {

        context = new MockContext();

    }

    @Test
    public void testNetworkConnectionErrorMessage() {

        String expectedMessage = context.getString( R.string.exception_message_no_connection );
        String actualMessage = ErrorMessageFactory.create( context, new NetworkConnectionException() );

        assertThat( actualMessage, is( equalTo( expectedMessage ) ) );

    }

    @Test
    public void testProgramNotFoundErrorMessage() {

        String expectedMessage = context.getString(R.string.exception_message_program_not_found);
        String actualMessage = ErrorMessageFactory.create( context, new ProgramNotFoundException() );

        assertThat( actualMessage, is( equalTo( expectedMessage ) ) );

    }

}
