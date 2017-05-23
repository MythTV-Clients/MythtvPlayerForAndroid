package org.mythtv.android.presentation.utils;

import android.content.Context;
import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by dmfrey on 6/27/16.
 */

//@RunWith( RobolectricTestRunner.class )
//@Config( manifest = Config.NONE )
public class ArticleCleanerTest {

    @Mock
    private Context mockContext;
    
    @Mock
    private Resources mockResources;

    @Before
    public void setup() throws Exception {

        MockitoAnnotations.initMocks( this );

    }

    @Test
    public void testClean() throws Exception {

        when( mockResources.getStringArray( any( int.class ) ) ).thenReturn( new String[] { "THE ", "AN ", "A " } );
        when( mockContext.getResources() ).thenReturn( mockResources );

        assertThat( ArticleCleaner.clean( mockContext, "The quick brown fox..." ) ).isEqualTo( "quick brown fox..." );
        assertThat( ArticleCleaner.clean( mockContext, "THE QUICK BROWN FOX..." ) ).isEqualTo( "QUICK BROWN FOX..." );
        assertThat( ArticleCleaner.clean( mockContext, "the quick brown fox..." ) ).isEqualTo( "quick brown fox..." );

        assertThat( ArticleCleaner.clean( mockContext, "An interesting idea..." ) ).isEqualTo( "interesting idea..." );
        assertThat( ArticleCleaner.clean( mockContext, "an interesting idea..." ) ).isEqualTo( "interesting idea..." );

        assertThat( ArticleCleaner.clean( mockContext, "A stitch in time..." ) ).isEqualTo( "stitch in time..." );
        assertThat( ArticleCleaner.clean( mockContext, "a stitch in time..." ) ).isEqualTo( "stitch in time..." );

        assertThat( ArticleCleaner.clean( mockContext, "The An A ..." ) ).isEqualTo( "..." );
        assertThat( ArticleCleaner.clean( mockContext, "THE AN A ..." ) ).isEqualTo( "..." );
        assertThat( ArticleCleaner.clean( mockContext, "the an a ..." ) ).isEqualTo( "..." );

        assertThat( ArticleCleaner.clean( mockContext, "" ) ).isEqualTo( "" );
        assertThat( ArticleCleaner.clean( mockContext, null ) ).isNull();

        when( mockResources.getStringArray( any( int.class ) ) ).thenReturn( new String[] { "THE", "AN", "A" } );
        when( mockContext.getResources() ).thenReturn( mockResources );

        assertThat( ArticleCleaner.clean( mockContext, "The quick brown fox..." ) ).isEqualTo( "quick brown fox..." );
        assertThat( ArticleCleaner.clean( mockContext, "THE QUICK BROWN FOX..." ) ).isEqualTo( "QUICK BROWN FOX..." );
        assertThat( ArticleCleaner.clean( mockContext, "the quick brown fox..." ) ).isEqualTo( "quick brown fox..." );

        assertThat( ArticleCleaner.clean( mockContext, "An interesting idea..." ) ).isEqualTo( "interesting idea..." );
        assertThat( ArticleCleaner.clean( mockContext, "an interesting idea..." ) ).isEqualTo( "interesting idea..." );

        assertThat( ArticleCleaner.clean( mockContext, "A stitch in time..." ) ).isEqualTo( "stitch in time..." );
        assertThat( ArticleCleaner.clean( mockContext, "a stitch in time..." ) ).isEqualTo( "stitch in time..." );

        assertThat( ArticleCleaner.clean( mockContext, "The An A ..." ) ).isEqualTo( "..." );
        assertThat( ArticleCleaner.clean( mockContext, "THE AN A ..." ) ).isEqualTo( "..." );
        assertThat( ArticleCleaner.clean( mockContext, "the an a ..." ) ).isEqualTo( "..." );

        assertThat( ArticleCleaner.clean( mockContext, "" ) ).isEqualTo( "" );
        assertThat( ArticleCleaner.clean( mockContext, null ) ).isNull();

    }

}
