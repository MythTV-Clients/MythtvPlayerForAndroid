package org.mythtv.android.library.core.service.v027.dvr;

import org.mythtv.android.library.events.dvr.ChannelInfoDetails;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.services.api.v027.beans.ChannelInfo;
import org.mythtv.services.api.v027.beans.Program;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/15/14.
 */
public class ChannelInfoHelper {

    public static ChannelInfoDetails toDetails( ChannelInfo channelInfo ) {

        ChannelInfoDetails details = new ChannelInfoDetails();
        details.setChanId( channelInfo.getChanId() );
        details.setChanNum( channelInfo.getChanNum() );
        details.setCallSign( channelInfo.getCallSign() );
        details.setIconURL( channelInfo.getIconURL() );
        details.setChannelName( channelInfo.getChannelName() );
        details.setMplexId( channelInfo.getMplexId() );
        details.setServiceId( channelInfo.getServiceId() );
        details.setaTSCMajorChan( channelInfo.getATSCMajorChan() );
        details.setaTSCMinorChan( channelInfo.getATSCMinorChan() );
        details.setFormat( channelInfo.getFormat() );
        details.setFrequencyId( channelInfo.getFrequencyId() );
        details.setFineTune( channelInfo.getFineTune() );
        details.setChanFilters( channelInfo.getChanFilters() );
        details.setSourceId( channelInfo.getSourceId() );
        details.setInputId( channelInfo.getInputId() );
        details.setCommFree( channelInfo.getCommFree() == 0 ? false : true );
        details.setUseEIT( channelInfo.isUseEIT() );
        details.setVisible( channelInfo.isVisible() );
        details.setxMLTVID( channelInfo.getXMLTVID() );
        details.setDefaultAuth( channelInfo.getDefaultAuth() );

        List<ProgramDetails> programDetails = new ArrayList<ProgramDetails>();
        if( null != channelInfo.getPrograms() &&  channelInfo.getPrograms().length > 0 ) {
            for( Program program : channelInfo.getPrograms() ) {
                programDetails.add( ProgramHelper.toDetails( program ) );
            }
        }
        details.setPrograms( programDetails );

        return details;
    }

    public static ChannelInfo fromDetails( ChannelInfoDetails details ) {

        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setChanId( details.getChanId() );
        channelInfo.setChanNum( details.getChanNum() );
        channelInfo.setCallSign( details.getCallSign() );
        channelInfo.setIconURL( details.getIconURL() );
        channelInfo.setChannelName( details.getChannelName() );
        channelInfo.setMplexId( details.getMplexId() );
        channelInfo.setServiceId( details.getServiceId() );
        channelInfo.setATSCMajorChan( details.getaTSCMajorChan() );
        channelInfo.setATSCMinorChan( details.getaTSCMinorChan() );
        channelInfo.setFormat( details.getFormat() );
        channelInfo.setFrequencyId( details.getFrequencyId() );
        channelInfo.setFineTune( details.getFineTune() );
        channelInfo.setChanFilters( details.getChanFilters() );
        channelInfo.setSourceId( details.getSourceId() );
        channelInfo.setInputId( details.getInputId() );
        channelInfo.setCommFree( details.getCommFree() ? 1 : 0 );
        channelInfo.setUseEIT( details.getUseEIT() );
        channelInfo.setVisible( details.getVisible() );
        channelInfo.setXMLTVID( details.getxMLTVID() );
        channelInfo.setDefaultAuth( details.getDefaultAuth() );

        List<Program> programs = new ArrayList<Program>();
        if( null != details.getPrograms() && !details.getPrograms().isEmpty() ) {
            for( ProgramDetails detail : details.getPrograms() ) {
                programs.add( ProgramHelper.fromDetails( detail ) );
            }
        }
        channelInfo.setPrograms( programs.toArray( new Program[ programs.size() ]) );

        return channelInfo;
    }

}
