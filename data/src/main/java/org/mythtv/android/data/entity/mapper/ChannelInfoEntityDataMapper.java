package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.ChannelInfoEntity;
import org.mythtv.android.domain.ChannelInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dmfrey on 1/18/16.
 */
@Singleton
public class ChannelInfoEntityDataMapper {

    private ChannelInfoEntityDataMapper() { }

    public static ChannelInfo transform( ChannelInfoEntity channelInfoEntity ) {

        ChannelInfo channelInfo = null;
        if( null != channelInfoEntity ) {

            channelInfo = new ChannelInfo();
            channelInfo.setChanId( channelInfoEntity.getChanId() );
            channelInfo.setChanNum( channelInfoEntity.getChanNum() );
            channelInfo.setCallSign( channelInfoEntity.getCallSign() );
            channelInfo.setIconURL( channelInfoEntity.getIconURL() );
            channelInfo.setChannelName( channelInfoEntity.getChannelName() );
            channelInfo.setMplexId( channelInfoEntity.getMplexId() );
            channelInfo.setServiceId( channelInfoEntity.getServiceId() );
            channelInfo.setATSCMajorChan( channelInfoEntity.getATSCMajorChan() );
            channelInfo.setATSCMinorChan( channelInfoEntity.getATSCMinorChan() );
            channelInfo.setFormat( channelInfoEntity.getFormat() );
            channelInfo.setFrequencyId( channelInfoEntity.getFrequencyId() );
            channelInfo.setFineTune( channelInfoEntity.getFineTune() );
            channelInfo.setChanFilters( channelInfoEntity.getChanFilters() );
            channelInfo.setSourceId( channelInfoEntity.getSourceId() );
            channelInfo.setInputId( channelInfoEntity.getInputId() );
            channelInfo.setCommFree( channelInfoEntity.isCommFree() );
            channelInfo.setUseEIT( channelInfoEntity.isUseEIT() );
            channelInfo.setVisible( channelInfoEntity.isVisible() );
            channelInfo.setXMLTVID( channelInfoEntity.getXMLTVID() );
            channelInfo.setDefaultAuth( channelInfoEntity.getDefaultAuth() );

            if( null != channelInfoEntity.getPrograms() && channelInfoEntity.getPrograms().length > 0 ) {

                channelInfo.setPrograms( ProgramEntityDataMapper.transform( Arrays.asList( channelInfoEntity.getPrograms() ) ) );

            }

        }

        return channelInfo;
    }

    public static List<ChannelInfo> transformCollection( Collection<ChannelInfoEntity> channelInfoEntityCollection ) {

        List<ChannelInfo> channelInfoList = new ArrayList<>( channelInfoEntityCollection.size() );

        ChannelInfo channelInfo;
        for( ChannelInfoEntity channelInfoEntity : channelInfoEntityCollection ) {

            channelInfo = transform( channelInfoEntity );
            if( null != channelInfo ) {

                channelInfoList.add( channelInfo );

            }

        }

        return channelInfoList;
    }

}
