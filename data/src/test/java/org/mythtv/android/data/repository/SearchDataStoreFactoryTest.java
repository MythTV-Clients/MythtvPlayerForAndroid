package org.mythtv.android.data.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.repository.datasource.SearchDataStore;
import org.mythtv.android.data.repository.datasource.SearchDataStoreFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on 10/10/15.
 */
public class SearchDataStoreFactoryTest extends ApplicationTestCase {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private SearchDataStoreFactory searchDataStoreFactory;

    @Before
    public void setup() {

        searchDataStoreFactory = new SearchDataStoreFactory( context() );

    }

    @Test
    public void testCreateReadSearchDataStore() throws Exception {

        SearchDataStore searchDataStore = searchDataStoreFactory.createReadSearchDataStore();

        assertThat( searchDataStore ).isNotNull();

    }

    @Test
    public void testCreateWriteSearchDataStore() throws Exception {

        SearchDataStore searchDataStore = searchDataStoreFactory.createWriteSearchDataStore();

        assertThat( searchDataStore ).isNotNull();

    }

}
