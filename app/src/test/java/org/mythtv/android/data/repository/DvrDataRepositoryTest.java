package org.mythtv.android.data.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.data.TestData;
import org.mythtv.android.data.entity.EncoderEntity;
import org.mythtv.android.data.repository.datasource.DvrDataStore;
import org.mythtv.android.data.repository.datasource.DvrDataStoreFactory;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith( MockitoJUnitRunner.class )
public class DvrDataRepositoryTest extends TestData {

    private DvrDataRepository dvrDataRepository;

    @Mock private DvrDataStoreFactory mockDvrDataStoreFactory;
    @Mock private DvrDataStore mockDvrDataStore;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks( this );
        dvrDataRepository = new DvrDataRepository( mockDvrDataStoreFactory );

        given( mockDvrDataStoreFactory.createMasterBackendDataStore() ).willReturn( mockDvrDataStore );

    }

    @Test
    public void testGetEncodersHappyCase() {

        List<EncoderEntity> encodersList = new ArrayList<>();
        encodersList.add( createFakeEncoderEntity() );

        given( mockDvrDataStore.encoderEntityList() ).willReturn( Observable.just( encodersList ) );

        dvrDataRepository.encoders();

        verify( mockDvrDataStoreFactory ).createMasterBackendDataStore();
        verify( mockDvrDataStore ).encoderEntityList();

    }

}
