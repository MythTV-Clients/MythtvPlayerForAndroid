package org.mythtv.android.data.repository.datasource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.net.ContentApi;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on /18/15.
 */
public class ContentDataStoreFactoryTest extends ApplicationTestCase {

    @Mock
    private ContentApi mockContentApi;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );

    }

    @Test
    public void whenCreateMasterBackendContentDataStore_verifyMasterBackendContentDataStoreReturned() {

        ContentDataStoreFactory contentDataStoreFactory = new ContentDataStoreFactory( mockContentApi );
        ContentDataStore contentDataStore = contentDataStoreFactory.createMasterBackendDataStore();

        assertThat( contentDataStore )
                .isNotNull()
                .isInstanceOf( MasterBackendContentDataStore.class );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenCreateMasterBackendContentDataStore_whenContentApiIsNull_verifyIllegalArgumentException() {

        new ContentDataStoreFactory( null );

    }

}
