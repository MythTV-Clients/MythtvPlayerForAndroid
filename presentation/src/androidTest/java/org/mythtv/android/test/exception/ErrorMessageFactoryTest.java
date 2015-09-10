package org.mythtv.android.test.exception;

import android.test.AndroidTestCase;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.data.exception.ProgramNotFoundException;
import org.mythtv.android.presentation.exception.ErrorMessageFactory;
import org.mythtv.android.R;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ErrorMessageFactoryTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testNetworkConnectionErrorMessage() {

        String expectedMessage = getContext().getString(R.string.exception_message_no_connection);
        String actualMessage = ErrorMessageFactory.create( getContext(), new NetworkConnectionException() );

        assertThat( actualMessage, is( equalTo( expectedMessage ) ) );

    }

    public void testProgramNotFoundErrorMessage() {

        String expectedMessage = getContext().getString( R.string.exception_message_program_not_found );
        String actualMessage = ErrorMessageFactory.create( getContext(), new ProgramNotFoundException() );

        assertThat( actualMessage, is( equalTo( expectedMessage ) ) );

    }

}
