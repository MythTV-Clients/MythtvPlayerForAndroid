package org.mythtv.android.data.repository.datasource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.net.DvrApi;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
public class DvrDataStoreFactoryTest extends ApplicationTestCase {

    private DvrDataStoreFactory dvrDataStoreFactory;

    @Mock
    private DvrApi mockDvrApi;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        dvrDataStoreFactory = new DvrDataStoreFactory( mockDvrApi );

    }

    @Test( expected = IllegalArgumentException.class )
    public void whenInitialize_whenDvrApiIsNull_verifyIllegalArgumentExceptionThrown() {

        new DvrDataStoreFactory( null );

    }

    @Test
    public void whenCreateMasterBackendDataStore_vefifyMasterBackendDataStoreReturned() {

        DvrDataStore dvrDataStore = dvrDataStoreFactory.createMasterBackendDataStore();

        assertThat( dvrDataStore )
            .isNotNull()
            .isInstanceOf( MasterBackendDvrDataStore.class );

    }

}
