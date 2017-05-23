package org.mythtv.android.presentation.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on 5/22/17.
 */

public class SeasonEpisodeFormatterTest {

    @Test
    public void testFormat() {

        assertThat( SeasonEpisodeFormatter.format( 0, 0 ) ).isEqualTo( "" );
        assertThat( SeasonEpisodeFormatter.format( -1, 0 ) ).isEqualTo( "" );
        assertThat( SeasonEpisodeFormatter.format( 0, -1 ) ).isEqualTo( "" );

        assertThat( SeasonEpisodeFormatter.format( 1, 1 ) ).isEqualTo( "S01E01" );
        assertThat( SeasonEpisodeFormatter.format( 10, 1 ) ).isEqualTo( "S10E01" );
        assertThat( SeasonEpisodeFormatter.format( 1, 10 ) ).isEqualTo( "S01E10" );

        assertThat( SeasonEpisodeFormatter.format( 1, 0 ) ).isEqualTo( "" );
        assertThat( SeasonEpisodeFormatter.format( 0, 1 ) ).isEqualTo( "" );

    }

}
