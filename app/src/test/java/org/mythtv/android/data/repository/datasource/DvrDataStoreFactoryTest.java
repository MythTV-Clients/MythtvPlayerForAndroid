package org.mythtv.android.data.repository.datasource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.net.DvrApi;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
public class DvrDataStoreFactoryTest extends ApplicationTestCase {

    private DvrDataStoreFactory dvrDataStoreFactory;

    @Mock
    private DvrApi mockDvrApi;

    @Mock
    private SearchDataStoreFactory mockSearchDataStoreFactory;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        dvrDataStoreFactory = new DvrDataStoreFactory( mockDvrApi, mockSearchDataStoreFactory );

    }

    @Test
    public void testCreateMasterBackendDvrDataStore() {

        DvrDataStore userDataStore = dvrDataStoreFactory.createMasterBackendDataStore();

        assertThat( userDataStore, is( notNullValue() ) );
        assertThat( userDataStore, is( instanceOf( MasterBackendDvrDataStore.class ) ) );

    }

}
