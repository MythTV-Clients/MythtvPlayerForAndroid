package org.mythtv.android.utils;

import android.content.Context;
import android.test.ActivityTestCase;
import android.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by dmfrey on 6/27/16.
 */

//@RunWith( RobolectricTestRunner.class )
//@Config( manifest = Config.NONE )
public class ArticleCleanerTest extends ActivityTestCase {

//    @Mock
    private Context context;

    @Before
    public void setup() throws Exception {
        super.setUp();

//        MockitoAnnotations.initMocks( this );
        context = getInstrumentation().getContext();

    }

    @Test
    public void testClean() throws Exception {

        when( context.getResources().getStringArray( any( int.class ) ) ).thenReturn( new String[] { "THE ", "AN ", "A " } );

        assertThat( ArticleCleaner.clean( context, "The quick brown fox..." ), equalTo( "quick brown fox..." ) );
        assertThat( ArticleCleaner.clean( context, "THE QUICK BROWN FOX..." ), equalTo( "QUICK BROWN FOX..." ) );
        assertThat( ArticleCleaner.clean( context, "the quick brown fox..." ), equalTo( "quick brown fox..." ) );

        assertThat( ArticleCleaner.clean( context, "An interesting idea..." ), equalTo( "interesting idea..." ) );
        assertThat( ArticleCleaner.clean( context, "an interesting idea..." ), equalTo( "interesting idea..." ) );

        assertThat( ArticleCleaner.clean( context, "A stitch in time..." ), equalTo( "stitch in time..." ) );
        assertThat( ArticleCleaner.clean( context, "a stitch in time..." ), equalTo( "stitch in time..." ) );

        assertThat( ArticleCleaner.clean( context, "The An A ..." ), equalTo( "..." ) );
        assertThat( ArticleCleaner.clean( context, "THE AN A ..." ), equalTo( "..." ) );
        assertThat( ArticleCleaner.clean( context, "the an a ..." ), equalTo( "..." ) );

    }

}
