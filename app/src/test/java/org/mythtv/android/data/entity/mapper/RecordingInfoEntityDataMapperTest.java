package org.mythtv.android.data.entity.mapper;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mythtv.android.data.entity.RecordingInfoEntity;
import org.mythtv.android.domain.RecordingInfo;

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
public class RecordingInfoEntityDataMapperTest {

    // Recording Info Fake Values
    private static final String FAKE_RECORDING_INFO_RECORDED_ID = "1";
    private static final int FAKE_RECORDING_INFO_STATUS = 1;
    private static final int FAKE_RECORDING_INFO_PRIORITY = 1;
    private static final DateTime FAKE_RECORDING_INFO_START_TS = new DateTime();
    private static final DateTime FAKE_RECORDING_INFO_END_TS = new DateTime();
    private static final int FAKE_RECORDING_INFO_RECORD_ID = 1;
    private static final String FAKE_RECORDING_INFO_REC_GROUP = "fake recording info rec group";
    private static final String FAKE_RECORDING_INFO_PLAY_GROUP = "fake recording info play group";
    private static final String FAKE_RECORDING_INFO_STORAGE_GROUP = "fake recording info storage group";
    private static final int FAKE_RECORDING_INFO_REC_TYPE = 1;
    private static final int FAKE_RECORDING_INFO_DUP_IN_TYPE = 1;
    private static final int FAKE_RECORDING_INFO_DUP_METHOD = 1;
    private static final int FAKE_RECORDING_INFO_ENCODER_ID = 1;
    private static final String FAKE_RECORDING_INFO_ENCODER_NAME = "fake recording info encoder name";
    private static final String FAKE_RECORDING_INFO_PROFILE = "fake recording info profile";

    @Test
    public void testTransformRecordingInfoEntity() {

        RecordingInfoEntity recordingInfoEntity = createFakeRecordingInfoEntity();
        RecordingInfo recordingInfo = RecordingInfoEntityDataMapper.transform( recordingInfoEntity );
        assertRecording(recordingInfo);

    }

    private void assertRecording( RecordingInfo recordingInfo ) {

        assertThat( recordingInfo, is( instanceOf( RecordingInfo.class ) ) );
        assertThat( recordingInfo.getRecordedId(), is( Integer.parseInt( FAKE_RECORDING_INFO_RECORDED_ID ) ) );
        assertThat( recordingInfo.getStatus(), is( FAKE_RECORDING_INFO_STATUS ) );
        assertThat( recordingInfo.getPriority(), is( FAKE_RECORDING_INFO_PRIORITY ) );
        assertThat( recordingInfo.getStartTs(), is( FAKE_RECORDING_INFO_START_TS ) );
        assertThat( recordingInfo.getEndTs(), is( FAKE_RECORDING_INFO_END_TS ) );
        assertThat( recordingInfo.getRecordId(), is( FAKE_RECORDING_INFO_RECORD_ID ) );
        assertThat( recordingInfo.getRecGroup(), is( FAKE_RECORDING_INFO_REC_GROUP ) );
        assertThat( recordingInfo.getPlayGroup(), is( FAKE_RECORDING_INFO_PLAY_GROUP ) );
        assertThat( recordingInfo.getStorageGroup(), is( FAKE_RECORDING_INFO_STORAGE_GROUP ) );
        assertThat( recordingInfo.getRecType(), is( FAKE_RECORDING_INFO_REC_TYPE ) );
        assertThat( recordingInfo.getDupInType(), is( FAKE_RECORDING_INFO_DUP_IN_TYPE ) );
        assertThat( recordingInfo.getDupMethod(), is( FAKE_RECORDING_INFO_DUP_METHOD ) );
        assertThat( recordingInfo.getEncoderId(), is( FAKE_RECORDING_INFO_ENCODER_ID ) );
        assertThat( recordingInfo.getEncoderName(), is( FAKE_RECORDING_INFO_ENCODER_NAME ) );
        assertThat( recordingInfo.getProfile(), is( FAKE_RECORDING_INFO_PROFILE ) );

    }

    @Test
    public void testTransformRecordingInfoEntityCollection() {

        RecordingInfoEntity mockRecordingInfoEntityOne = mock(RecordingInfoEntity.class);
        RecordingInfoEntity mockRecordingInfoEntityTwo = mock(RecordingInfoEntity.class);

        List<RecordingInfoEntity> recordingInfoEntityList = new ArrayList<>( 5 );
        recordingInfoEntityList.add(mockRecordingInfoEntityOne);
        recordingInfoEntityList.add(mockRecordingInfoEntityTwo);

        Collection<RecordingInfo> recordingInfoCollection = RecordingInfoEntityDataMapper.transformCollection( recordingInfoEntityList );

        assertThat( recordingInfoCollection.toArray()[ 0 ], is( instanceOf( RecordingInfo.class ) ) );
        assertThat( recordingInfoCollection.toArray()[ 1 ], is( instanceOf( RecordingInfo.class ) ) );
        assertThat( recordingInfoCollection.size(), is( 2 ) );

    }

    private RecordingInfoEntity createFakeRecordingInfoEntity() {

        RecordingInfoEntity recordingInfoEntity = new RecordingInfoEntity();
        recordingInfoEntity.setRecordedId( FAKE_RECORDING_INFO_RECORDED_ID );
        recordingInfoEntity.setStatus( FAKE_RECORDING_INFO_STATUS );
        recordingInfoEntity.setPriority( FAKE_RECORDING_INFO_PRIORITY );
        recordingInfoEntity.setStartTs( FAKE_RECORDING_INFO_START_TS );
        recordingInfoEntity.setEndTs( FAKE_RECORDING_INFO_END_TS );
        recordingInfoEntity.setRecordId( FAKE_RECORDING_INFO_RECORD_ID );
        recordingInfoEntity.setRecGroup( FAKE_RECORDING_INFO_REC_GROUP );
        recordingInfoEntity.setPlayGroup( FAKE_RECORDING_INFO_PLAY_GROUP );
        recordingInfoEntity.setStorageGroup( FAKE_RECORDING_INFO_STORAGE_GROUP );
        recordingInfoEntity.setRecType( FAKE_RECORDING_INFO_REC_TYPE );
        recordingInfoEntity.setDupInType( FAKE_RECORDING_INFO_DUP_IN_TYPE );
        recordingInfoEntity.setDupMethod( FAKE_RECORDING_INFO_DUP_METHOD );
        recordingInfoEntity.setEncoderId( FAKE_RECORDING_INFO_ENCODER_ID );
        recordingInfoEntity.setEncoderName( FAKE_RECORDING_INFO_ENCODER_NAME );
        recordingInfoEntity.setProfile( FAKE_RECORDING_INFO_PROFILE );

        return recordingInfoEntity;
    }

}
