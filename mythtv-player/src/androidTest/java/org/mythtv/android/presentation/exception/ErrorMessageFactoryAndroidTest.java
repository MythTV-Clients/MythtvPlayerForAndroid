package org.mythtv.android.presentation.exception;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mythtv.android.data.exception.NetworkConnectionException;
import org.mythtv.android.data.exception.ProgramNotFoundException;
import org.mythtv.android.R;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith( AndroidJUnit4.class )
public class ErrorMessageFactoryAndroidTest {

    Context mMockContext;

    @Before
    public void setUp() {
        mMockContext = new RenamingDelegatingContext( InstrumentationRegistry.getInstrumentation().getTargetContext(), "test_" );
    }

    @Test
    public void testNetworkConnectionErrorMessage() throws Exception {

        String expectedMessage = mMockContext.getString( R.string.exception_message_no_connection );
        String actualMessage = ErrorMessageFactory.create( mMockContext, new NetworkConnectionException() );

        assertThat( actualMessage, is( equalTo( expectedMessage ) ) );

    }

    @Test
    public void testProgramNotFoundErrorMessage() throws Exception {

        String expectedMessage = mMockContext.getString(R.string.exception_message_program_not_found);
        String actualMessage = ErrorMessageFactory.create( mMockContext, new ProgramNotFoundException() );

        assertThat( actualMessage, is( equalTo( expectedMessage ) ) );

    }

}
