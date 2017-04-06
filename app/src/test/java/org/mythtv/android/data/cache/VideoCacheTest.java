package org.mythtv.android.data.cache;

import android.content.Context;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.cache.serializer.VideoListEntityJsonSerializer;
import org.mythtv.android.data.entity.VideoMetadataInfoEntity;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.robolectric.RuntimeEnvironment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 12/16/15.
 */
@Ignore
public class VideoCacheTest extends ApplicationTestCase {

    @Mock
    private ThreadExecutor mockThreadExecutor;

    private FileManager fileManager;
    private VideoCache videoCache;
    private File cacheDir;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

        Context context = RuntimeEnvironment.application.getApplicationContext();
        cacheDir = RuntimeEnvironment.application.getCacheDir();

        fileManager = new FileManager();

        videoCache = new VideoCacheImpl( context, new VideoListEntityJsonSerializer( this.gson ), fileManager, mockThreadExecutor );

    }

    @After
    public void tearDown() {

        if( null != cacheDir ) {
            fileManager.clearDirectory( cacheDir );
        }

    }

    @Test
    public void testPut() throws Exception {

        videoCache.put( testEntries() );

        Observable<VideoMetadataInfoEntity> video = videoCache.get( 1 );
        assertThat( video.elementAt( 0 ), not( nullValue() ) );

    }

    private List<VideoMetadataInfoEntity> testEntries() {

        List<VideoMetadataInfoEntity> entries = new ArrayList<>();

        VideoMetadataInfoEntity entryOne = VideoMetadataInfoEntity.create(
            1, "big buck bunny 1080p stereo", null, null, "Unknown", null, "None", "NR", "00000000",
            -1, null, null, new DateTime( "2015-01-05T05:00:00Z" ), 0, 0, 0, 0, 0, 1, true, false,
            true, "MOVIE", "library\\/Blender\\/big_buck_bunny_1080p_stereo.ogg", "63ad35476c29214c",
            "mythcenter", null, null, null, null, null, null, null );
        entries.add( entryOne );

        VideoMetadataInfoEntity entryTwo = VideoMetadataInfoEntity.create(
            2, "tears of steel 1080p", null, null, "Unknown", null, "None", "NR", "00000000",
            -1, null, null, new DateTime( "2015-01-05T05:00:00Z" ), 0, 0, 0, 0, 0, 1, true, false,
            true, "MOVIE", "library\\/Blender\\/tears_of_steel_1080p.mkv", "54220ffda5954294",
            "mythcenter", null, null, null, null, null, null, null );
        entries.add( entryTwo );

        return entries;
    }
}
