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
import org.mythtv.android.data.cache.serializer.VideoEntityJsonSerializer;
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

    private Context context;
    private FileManager fileManager;
    private VideoEntityJsonSerializer videoEntityJsonSerializer;
    private VideoListEntityJsonSerializer videoListEntityJsonSerializer;
    private VideoCache videoCache;
    private File cacheDir;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

        context = RuntimeEnvironment.application.getApplicationContext();
        cacheDir = RuntimeEnvironment.application.getCacheDir();

        fileManager = new FileManager();
        videoEntityJsonSerializer = new VideoEntityJsonSerializer();
        videoListEntityJsonSerializer = new VideoListEntityJsonSerializer();

        videoCache = new VideoCacheImpl( context, videoEntityJsonSerializer, videoListEntityJsonSerializer, fileManager, mockThreadExecutor );

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

        VideoMetadataInfoEntity entryOne = new VideoMetadataInfoEntity();
        entryOne.setId( 1 );
        entryOne.setTitle( "big buck bunny 1080p stereo" );
        entryOne.setDirector( "Unknown" );
        entryOne.setDescription( "None" );
        entryOne.setCertification( "NR" );
        entryOne.setInetref( "00000000" );
        entryOne.setCollectionref( -1 );
        entryOne.setAddDate( new DateTime( "2015-01-05T05:00:00Z" ) );
        entryOne.setUserRating( 0 );
        entryOne.setLength( 0 );
        entryOne.setPlayCount( 0 );
        entryOne.setSeason( 0 );
        entryOne.setEpisode( 0 );
        entryOne.setParentalLevel( 1 );
        entryOne.setVisible( true );
        entryOne.setWatched( false );
        entryOne.setProcessed( true );
        entryOne.setContentType( "MOVIE" );
        entryOne.setFileName( "library\\/Blender\\/big_buck_bunny_1080p_stereo.ogg" );
        entryOne.setHash( "63ad35476c29214c" );
        entryOne.setHostName( "mythcenter" );
        entries.add( entryOne );

        VideoMetadataInfoEntity entryTwo = new VideoMetadataInfoEntity();
        entryTwo.setId( 2 );
        entryTwo.setTitle( "tears of steel 1080p" );
        entryTwo.setDirector( "Unknown" );
        entryTwo.setDescription( "None" );
        entryTwo.setCertification( "NR" );
        entryTwo.setInetref( "00000000" );
        entryTwo.setCollectionref( -1 );
        entryTwo.setAddDate( new DateTime( "2015-01-05T05:00:00Z" ) );
        entryTwo.setUserRating( 0 );
        entryTwo.setLength( 0 );
        entryTwo.setPlayCount( 0 );
        entryTwo.setSeason( 0 );
        entryTwo.setEpisode( 0 );
        entryTwo.setParentalLevel( 1 );
        entryTwo.setVisible( true );
        entryTwo.setWatched( false );
        entryTwo.setProcessed( true );
        entryTwo.setContentType( "MOVIE" );
        entryTwo.setFileName( "library\\/Blender\\/tears_of_steel_1080p.mkv" );
        entryTwo.setHash( "54220ffda5954294" );
        entryTwo.setHostName( "mythcenter" );
        entries.add( entryTwo );

        return entries;
    }
}
