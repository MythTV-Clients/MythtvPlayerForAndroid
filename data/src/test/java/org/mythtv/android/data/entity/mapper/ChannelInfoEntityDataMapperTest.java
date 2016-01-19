package org.mythtv.android.data.entity.mapper;

import org.junit.Test;
import org.mythtv.android.data.entity.ChannelInfoEntity;
import org.mythtv.android.domain.ChannelInfo;

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
public class ChannelInfoEntityDataMapperTest {

    // Channel Info Fake Values
    private static final int FAKE_CHANNEL_INFO_CHAN_ID = 1;
    private static final String FAKE_CHANNEL_INFO_CHAN_NUM = "fake channel info chan num";
    private static final String FAKE_CHANNEL_INFO_CALLSIGN = "fake channel info callsign";
    private static final String FAKE_CHANNEL_INFO_ICON_URL = "fake channel info iconUrl";
    private static final String FAKE_CHANNEL_INFO_CHANNEL_NAME = "fake channel info channel name";
    private static final int FAKE_CHANNEL_INFO_MPLEXID = 1;
    private static final int FAKE_CHANNEL_INFO_SERVICE_ID = 1;
    private static final int FAKE_CHANNEL_INFO_ATSC_MAJOR_CHAN = 1;
    private static final int FAKE_CHANNEL_INFO_ATSC_MINOR_CHAN = 1;
    private static final String FAKE_CHANNEL_INFO_FORMAT = "fake channel info format";
    private static final String FAKE_CHANNEL_INFO_FREQUENCY_ID = "fake channel info frequence id";
    private static final int FAKE_CHANNEL_INFO_FINETUNE = 1;
    private static final String FAKE_CHANNEL_INFO_CHAN_FILTERS = "fake channel info chan filters";
    private static final int FAKE_CHANNEL_INFO_SOURCE_ID = 1;
    private static final int FAKE_CHANNEL_INFO_INPUT_ID = 1;
    private static final boolean FAKE_CHANNEL_INFO_COMM_FREE = false;
    private static final boolean FAKE_CHANNEL_INFO_USE_EIT = false;
    private static final boolean FAKE_CHANNEL_INFO_VISIBLE = false;
    private static final String FAKE_CHANNEL_INFO_XMLTVID = "fake channel info xmltvid";
    private static final String FAKE_CHANNEL_INFO_DEFAULT_AUTH = "fake channel info default auth";

    @Test
    public void testTransformChannelInfoEntity() {

        ChannelInfoEntity channelInfoEntity = createFakeChannelInfoEntity();
        ChannelInfo channelInfo = ChannelInfoEntityDataMapper.transform( channelInfoEntity );
        assertChannel( channelInfo );

    }

    private void assertChannel( ChannelInfo channelInfo ) {

        assertThat( channelInfo, is( instanceOf( ChannelInfo.class ) ) );
        assertThat( channelInfo.getChanId(), is( FAKE_CHANNEL_INFO_CHAN_ID ) );
        assertThat( channelInfo.getChanNum(), is( FAKE_CHANNEL_INFO_CHAN_NUM ) );
        assertThat( channelInfo.getCallSign(), is( FAKE_CHANNEL_INFO_CALLSIGN ) );
        assertThat( channelInfo.getIconURL(), is( FAKE_CHANNEL_INFO_ICON_URL ) );
        assertThat( channelInfo.getChannelName(), is( FAKE_CHANNEL_INFO_CHANNEL_NAME ) );
        assertThat( channelInfo.getMplexId(), is( FAKE_CHANNEL_INFO_MPLEXID ) );
        assertThat( channelInfo.getServiceId(), is( FAKE_CHANNEL_INFO_SERVICE_ID ) );
        assertThat( channelInfo.getATSCMajorChan(), is( FAKE_CHANNEL_INFO_ATSC_MAJOR_CHAN ) );
        assertThat( channelInfo.getATSCMinorChan(), is( FAKE_CHANNEL_INFO_ATSC_MINOR_CHAN ) );
        assertThat( channelInfo.getFormat(), is( FAKE_CHANNEL_INFO_FORMAT ) );
        assertThat( channelInfo.getFrequencyId(), is( FAKE_CHANNEL_INFO_FREQUENCY_ID ) );
        assertThat( channelInfo.getFineTune(), is( FAKE_CHANNEL_INFO_FINETUNE ) );
        assertThat( channelInfo.getChanFilters(), is( FAKE_CHANNEL_INFO_CHAN_FILTERS ) );
        assertThat( channelInfo.getSourceId(), is( FAKE_CHANNEL_INFO_SOURCE_ID ) );
        assertThat( channelInfo.getInputId(), is( FAKE_CHANNEL_INFO_INPUT_ID ) );
        assertThat( channelInfo.isCommFree(), is( FAKE_CHANNEL_INFO_COMM_FREE ) );
        assertThat( channelInfo.isUseEIT(), is( FAKE_CHANNEL_INFO_USE_EIT ) );
        assertThat( channelInfo.isVisible(), is( FAKE_CHANNEL_INFO_VISIBLE ) );
        assertThat( channelInfo.getXMLTVID(), is( FAKE_CHANNEL_INFO_XMLTVID ) );
        assertThat( channelInfo.getDefaultAuth(), is( FAKE_CHANNEL_INFO_DEFAULT_AUTH ) );

    }

    @Test
    public void testTransformChannelInfoEntityCollection() {

        ChannelInfoEntity mockChannelInfoEntityOne = mock( ChannelInfoEntity.class );
        ChannelInfoEntity mockChannelInfoEntityTwo = mock( ChannelInfoEntity.class );

        List<ChannelInfoEntity> channelInfoEntityList = new ArrayList<>( 5 );
        channelInfoEntityList.add( mockChannelInfoEntityOne );
        channelInfoEntityList.add( mockChannelInfoEntityTwo );

        Collection<ChannelInfo> channelInfoCollection = ChannelInfoEntityDataMapper.transformCollection( channelInfoEntityList );

        assertThat( channelInfoCollection.toArray()[ 0 ], is( instanceOf( ChannelInfo.class ) ) );
        assertThat( channelInfoCollection.toArray()[ 1 ], is( instanceOf( ChannelInfo.class ) ) );
        assertThat( channelInfoCollection.size(), is( 2 ) );

    }

    private ChannelInfoEntity createFakeChannelInfoEntity() {

        ChannelInfoEntity channelInfoEntity = new ChannelInfoEntity();
        channelInfoEntity.setChanId( FAKE_CHANNEL_INFO_CHAN_ID );
        channelInfoEntity.setChanNum( FAKE_CHANNEL_INFO_CHAN_NUM );
        channelInfoEntity.setCallSign( FAKE_CHANNEL_INFO_CALLSIGN );
        channelInfoEntity.setIconURL( FAKE_CHANNEL_INFO_ICON_URL );
        channelInfoEntity.setChannelName( FAKE_CHANNEL_INFO_CHANNEL_NAME );
        channelInfoEntity.setMplexId( FAKE_CHANNEL_INFO_MPLEXID );
        channelInfoEntity.setServiceId( FAKE_CHANNEL_INFO_SERVICE_ID );
        channelInfoEntity.setATSCMajorChan( FAKE_CHANNEL_INFO_ATSC_MAJOR_CHAN );
        channelInfoEntity.setATSCMinorChan( FAKE_CHANNEL_INFO_ATSC_MINOR_CHAN );
        channelInfoEntity.setFormat( FAKE_CHANNEL_INFO_FORMAT );
        channelInfoEntity.setFrequencyId( FAKE_CHANNEL_INFO_FREQUENCY_ID );
        channelInfoEntity.setFineTune( FAKE_CHANNEL_INFO_FINETUNE );
        channelInfoEntity.setChanFilters( FAKE_CHANNEL_INFO_CHAN_FILTERS );
        channelInfoEntity.setSourceId( FAKE_CHANNEL_INFO_SOURCE_ID );
        channelInfoEntity.setInputId( FAKE_CHANNEL_INFO_INPUT_ID );
        channelInfoEntity.setCommFree( FAKE_CHANNEL_INFO_COMM_FREE );
        channelInfoEntity.setUseEIT( FAKE_CHANNEL_INFO_USE_EIT );
        channelInfoEntity.setVisible( FAKE_CHANNEL_INFO_VISIBLE );
        channelInfoEntity.setXMLTVID( FAKE_CHANNEL_INFO_XMLTVID );
        channelInfoEntity.setDefaultAuth( FAKE_CHANNEL_INFO_DEFAULT_AUTH );

        return channelInfoEntity;
    }

}
