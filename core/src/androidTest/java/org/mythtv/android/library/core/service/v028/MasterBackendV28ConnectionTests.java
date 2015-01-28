package org.mythtv.android.library.core.service.v028;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;
import org.mythtv.android.library.core.service.DvrService;
import org.mythtv.android.library.core.service.v028.dvr.DvrServiceV28EventHandler;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.services.api.ApiVersion;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.ServerVersionQuery;

import java.util.List;

/**
 * Created by dmfrey on 11/26/14.
 */
@Ignore
public class MasterBackendV28ConnectionTests extends TestCase {

    String hostname = "192.168.10.200";
    int port = 6544;

    @Test
    public void testThatV028BackendIsConnected() throws Exception {

        ApiVersion apiVersion = ServerVersionQuery.getMythVersion( "http://" + hostname + ":" + port + "/" );
        assertEquals( ApiVersion.v028, apiVersion );

        MythTvApiContext mMythTvApiContext = MythTvApiContext.newBuilder().setHostName( hostname ).setPort( port ).setVersion( apiVersion ).build();

        DvrService dvrService = new DvrServiceV28EventHandler( mMythTvApiContext );

        AllProgramsEvent event = dvrService.getRecordedPrograms( new RequestAllRecordedProgramsEvent( false, 0, null, null, null, null ) );
        assertNotNull( event );
        assertTrue( event.isEntityFound() );

        List<ProgramDetails> programs = event.getDetails();
        assertFalse(programs.isEmpty());

    }

}
