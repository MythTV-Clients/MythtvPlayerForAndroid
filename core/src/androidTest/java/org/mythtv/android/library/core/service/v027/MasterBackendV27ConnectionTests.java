package org.mythtv.android.library.core.service.v027;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;
import org.mythtv.android.library.core.service.DvrService;
import org.mythtv.android.library.core.service.v027.dvr.DvrServiceV27EventHandler;
import org.mythtv.android.library.events.dvr.AllProgramsEvent;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.ProgramsUpdatedEvent;
import org.mythtv.android.library.events.dvr.RequestAllRecordedProgramsEvent;
import org.mythtv.android.library.events.dvr.UpdateRecordedProgramsEvent;
import org.mythtv.services.api.ApiVersion;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.ServerVersionQuery;

import java.util.List;

/**
 * Created by dmfrey on 11/26/14.
 */
@Ignore
public class MasterBackendV27ConnectionTests extends TestCase {

    String hostname = "192.168.10.200";
    int port = 6544;
    ApiVersion version = ApiVersion.v027;

    @Test
    public void testThatV027BackendIsConnected() throws Exception {

        ApiVersion apiVersion = ServerVersionQuery.getMythVersion( "http://" + hostname + ":" + port + "/" );
        assertEquals( ApiVersion.v027, apiVersion );

        MythTvApiContext mMythTvApiContext = MythTvApiContext.newBuilder().setHostName( hostname ).setPort( port ).setVersion( version ).build();

        DvrService dvrService = new DvrServiceV27EventHandler();

        ProgramsUpdatedEvent event = dvrService.updateRecordedPrograms( new UpdateRecordedProgramsEvent( false, 0, null, null, null, null ) );
        assertNotNull( event );
        assertTrue( event.isEntityFound() );

        List<ProgramDetails> programs = event.getDetails();
        assertFalse(programs.isEmpty());

    }

}
