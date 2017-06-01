package org.mythtv.android.data.repository;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.data.net.ContentApi;
import org.mythtv.android.data.repository.datasource.ContentDataStore;
import org.mythtv.android.data.repository.datasource.ContentDataStoreFactory;
import org.mythtv.android.data.repository.datasource.MasterBackendContentDataStore;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on /18/15.
 */
@RunWith( MockitoJUnitRunner.class )
public class ContentDataStoreFactoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ContentApi mockContentApi;

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
