package org.mythtv.android.presentation.utils;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 *
 * @author dmfrey
 *
 * Created on 2/1/17.
 *
 */

public class UtilsTest {

    @Test
    public void testMeetsMinimumVersion() throws Exception {

        assertThat( Utils.meetsMinimumVersion( "v0.27", 0.28f ), equalTo( false ) );
        assertThat( Utils.meetsMinimumVersion( "0.27", 0.28f ), equalTo( false ) );
        assertThat( Utils.meetsMinimumVersion( "v0.27-xyz", 0.28f ), equalTo( false ) );
        assertThat( Utils.meetsMinimumVersion( "v0.26", 0.28f ), equalTo( false ) );
        assertThat( Utils.meetsMinimumVersion( "0.26", 0.28f ), equalTo( false ) );
        assertThat( Utils.meetsMinimumVersion( "v0.26-xyz", 0.28f ), equalTo( false ) );

        assertThat( Utils.meetsMinimumVersion( "v0.28", 0.28f ), equalTo( true ) );
        assertThat( Utils.meetsMinimumVersion( "0.28", 0.28f ), equalTo( true ) );
        assertThat( Utils.meetsMinimumVersion( "v0.28-xyz", 0.28f ), equalTo( true ) );

        assertThat( Utils.meetsMinimumVersion( "v0.29", 0.28f ), equalTo( true ) );
        assertThat( Utils.meetsMinimumVersion( "0.29", 0.28f ), equalTo( true ) );
        assertThat( Utils.meetsMinimumVersion( "v0.29-xyz", 0.28f ), equalTo( true ) );

    }

}
