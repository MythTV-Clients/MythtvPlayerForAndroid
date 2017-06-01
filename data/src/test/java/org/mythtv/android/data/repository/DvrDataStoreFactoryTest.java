package org.mythtv.android.data.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.data.net.DvrApi;
import org.mythtv.android.data.repository.datasource.DvrDataStore;
import org.mythtv.android.data.repository.datasource.DvrDataStoreFactory;
import org.mythtv.android.data.repository.datasource.MasterBackendDvrDataStore;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
@RunWith( MockitoJUnitRunner.class )
public class DvrDataStoreFactoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
