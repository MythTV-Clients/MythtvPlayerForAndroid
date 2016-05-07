/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
