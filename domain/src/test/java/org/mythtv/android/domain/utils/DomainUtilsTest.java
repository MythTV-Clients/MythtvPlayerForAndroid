package org.mythtv.android.domain.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on 3/18/16.
 */
public class DomainUtilsTest {

    @Test
    public void testRemoveArticles() {

        String testQuickBrownFox = "The quick brown fox jumps over the lazy dog";
        String expectedQuickBrownFox = "quick brown fox jumps over the lazy dog";
        assertThat( DomainUtils.removeArticles( testQuickBrownFox ) ).isEqualTo( expectedQuickBrownFox );

        String testStitchInTime = "A stitch in time saves nine";
        String expectedStitchInTime = "stitch in time saves nine";
        assertThat( DomainUtils.removeArticles( testStitchInTime ) ).isEqualTo( expectedStitchInTime );

        String testAnAnotherThing = "An another thing";
        String expectedAnAnotherThing = "another thing";
        assertThat( DomainUtils.removeArticles( testAnAnotherThing ) ).isEqualTo( expectedAnAnotherThing );

        String testDoesThisMakeSense = "Does this make sense?";
        String expectedDoesThisMakeSense = "Does this make sense?";
        assertThat( DomainUtils.removeArticles( testDoesThisMakeSense ) ).isEqualTo( expectedDoesThisMakeSense );

    }

}
