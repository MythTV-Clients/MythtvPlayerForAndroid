package org.mythtv.android.data.entity.mapper;

import org.junit.Test;
import org.mythtv.android.data.entity.ArtworkEntity;
import org.mythtv.android.data.entity.ArtworkInfoEntity;
import org.mythtv.android.domain.ArtworkInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by dmfrey on 1/18/16.
 */
public class ArtworkInfoEntityDataMapperTest {

    // Artwork Info Fake Values
    private static final String FAKE_ARTWORK_INFO_URL = "fake artwork info url";
    private static final String FAKE_ARTWORK_INFO_FILENAME = "fake artwork info filename";
    private static final String FAKE_ARTWORK_INFO_STORAGE_GROUP = "fake artwork info storage group";
    private static final String FAKE_ARTWORK_INFO_TYPE = "fake artwork info type";

    @Test
    public void testTransformArtworkInfoEntity() {

        ArtworkInfoEntity artworkInfoEntity = createFakeArtworkInfoEntity();
        ArtworkInfo artworkInfo = ArtworkInfoEntityDataMapper.transform( artworkInfoEntity );
        assertArtworkInfo(artworkInfo);

    }

    private void assertArtworkInfo( ArtworkInfo artworkInfo ) {

        assertThat( artworkInfo, is( instanceOf( ArtworkInfo.class ) ) );
        assertThat( artworkInfo.getUrl(), is( FAKE_ARTWORK_INFO_URL ) );
        assertThat( artworkInfo.getFileName(), is( FAKE_ARTWORK_INFO_FILENAME ) );
        assertThat( artworkInfo.getStorageGroup(), is( FAKE_ARTWORK_INFO_STORAGE_GROUP ) );
        assertThat( artworkInfo.getType(), is( FAKE_ARTWORK_INFO_TYPE ) );

    }

    @Test
    public void testTransformArtworkInfoEntityCollection() {

        ArtworkInfoEntity mockArtworkInfoEntityOne = mock( ArtworkInfoEntity.class );
        ArtworkInfoEntity mockArtworkInfoEntityTwo = mock(ArtworkInfoEntity.class);

        List<ArtworkInfoEntity> artworkInfoEntityList = new ArrayList<>( 5 );
        artworkInfoEntityList.add(mockArtworkInfoEntityOne);
        artworkInfoEntityList.add(mockArtworkInfoEntityTwo);

        Collection<ArtworkInfo> artworkInfoCollection = ArtworkInfoEntityDataMapper.transformCollection( artworkInfoEntityList );

        assertThat( artworkInfoCollection.toArray()[ 0 ], is( instanceOf( ArtworkInfo.class ) ) );
        assertThat( artworkInfoCollection.toArray()[ 1 ], is( instanceOf( ArtworkInfo.class ) ) );
        assertThat( artworkInfoCollection.size(), is( 2 ) );

    }

    private ArtworkEntity createFakeArtworkEntity() {

        ArtworkEntity artworkEntity = new ArtworkEntity();

        ArtworkInfoEntity[] artworkInfoEntities = new ArtworkInfoEntity[ 1 ];
        artworkInfoEntities[ 0 ] = createFakeArtworkInfoEntity();
        artworkEntity.setArtworkInfos( artworkInfoEntities );

        return artworkEntity;
    }

    private ArtworkInfoEntity createFakeArtworkInfoEntity() {

        ArtworkInfoEntity artworkInfoEntity = new ArtworkInfoEntity();
        artworkInfoEntity.setUrl( FAKE_ARTWORK_INFO_URL );
        artworkInfoEntity.setFileName( FAKE_ARTWORK_INFO_FILENAME );
        artworkInfoEntity.setStorageGroup( FAKE_ARTWORK_INFO_STORAGE_GROUP );
        artworkInfoEntity.setType( FAKE_ARTWORK_INFO_TYPE );

        return artworkInfoEntity;
    }

}
