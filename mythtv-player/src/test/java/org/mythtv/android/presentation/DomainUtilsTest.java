package org.mythtv.android.presentation;

import org.junit.Test;
import org.mythtv.android.domain.utils.DomainUtils;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 3/18/16.
 */
public class DomainUtilsTest {

    @Test
    public void testRemoveArticles() {

        String testQuickBrownFox = "The quick brown fox jumps over the lazy dog";
        String expectedQuickBrownFox = "quick brown fox jumps over the lazy dog";
        assertThat( DomainUtils.removeArticles( testQuickBrownFox ), equalTo( expectedQuickBrownFox ) );

        String testStitchInTime = "A stitch in time saves nine";
        String expectedStitchInTime = "stitch in time saves nine";
        assertThat( DomainUtils.removeArticles( testStitchInTime ), equalTo( expectedStitchInTime ) );

        String testAnAnotherThing = "An another thing";
        String expectedAnAnotherThing = "another thing";
        assertThat( DomainUtils.removeArticles( testAnAnotherThing ), equalTo( expectedAnAnotherThing ) );

        String testDoesThisMakeSense = "Does this make sense?";
        String expectedDoesThisMakeSense = "Does this make sense?";
        assertThat( DomainUtils.removeArticles( testDoesThisMakeSense ), equalTo( expectedDoesThisMakeSense ) );

    }

}
