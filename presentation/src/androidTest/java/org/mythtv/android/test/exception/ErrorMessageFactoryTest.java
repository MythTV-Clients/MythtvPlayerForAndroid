package org.mythtv.android.test.exception;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.data.exception.ProgramNotFoundException;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.presentation.R;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ErrorMessageFactoryTest extends AndroidTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testNetworkConnectionErrorMessage() {

        String expectedMessage = getContext().getString( R.string.exception_message_no_connection );
        String actualMessage = ErrorMessageFactory.create( getContext(), new NetworkConnectionException() );

        assertThat( actualMessage, is( equalTo( expectedMessage ) ) );

    }

    @Test
    public void testProgramNotFoundErrorMessage() {

        String expectedMessage = getContext().getString(R.string.exception_message_program_not_found);
        String actualMessage = ErrorMessageFactory.create( getContext(), new ProgramNotFoundException() );

        assertThat( actualMessage, is( equalTo( expectedMessage ) ) );

    }

}
