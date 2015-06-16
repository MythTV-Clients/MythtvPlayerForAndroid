/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.library.core.service.v028;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;
import org.mythtv.android.library.core.service.DvrService;
import org.mythtv.android.library.core.service.v028.dvr.DvrServiceV28ApiEventHandler;
import org.mythtv.android.library.events.dvr.ProgramDetails;
import org.mythtv.android.library.events.dvr.ProgramsUpdatedEvent;
import org.mythtv.android.library.events.dvr.UpdateRecordedProgramsEvent;
import org.mythtv.services.api.ApiVersion;
import org.mythtv.services.api.MythTvApiContext;
import org.mythtv.services.api.ServerVersionQuery;

import java.util.List;

/*
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

        DvrService dvrService = new DvrServiceV28ApiEventHandler();

        ProgramsUpdatedEvent event = dvrService.updateRecordedPrograms( new UpdateRecordedProgramsEvent( false, 0, null, null, null, null ) );
        assertNotNull( event );
        assertTrue( event.isEntityFound() );

        List<ProgramDetails> programs = event.getDetails();
        assertFalse(programs.isEmpty());

    }

}
