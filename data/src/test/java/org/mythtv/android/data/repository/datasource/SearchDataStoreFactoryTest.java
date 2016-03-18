package org.mythtv.android.data.repository.datasource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 10/10/15.
 */
public class SearchDataStoreFactoryTest extends ApplicationTestCase {

    private SearchDataStoreFactory searchDataStoreFactory;

    @Before
    public void setup() throws Exception {

        MockitoAnnotations.initMocks( this );

        searchDataStoreFactory = new SearchDataStoreFactory( RuntimeEnvironment.application );

    }

    @Test
    public void testCreateReadSearchDataStore() throws Exception {

        SearchDataStore searchDataStore = searchDataStoreFactory.createReadSearchDataStore();

        assertThat( searchDataStore, is( notNullValue() ) );

    }

    @Test
    public void testCreateWriteSearchDataStore() throws Exception {

        SearchDataStore searchDataStore = searchDataStoreFactory.createWriteSearchDataStore();

        assertThat( searchDataStore, is( notNullValue() ) );

    }

}
