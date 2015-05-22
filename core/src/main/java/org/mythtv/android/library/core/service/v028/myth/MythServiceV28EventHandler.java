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

package org.mythtv.android.library.core.service.v028.myth;

import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.service.MythService;
import org.mythtv.android.library.events.myth.HostNameDetails;
import org.mythtv.android.library.events.myth.HostNameDetailsEvent;
import org.mythtv.android.library.events.myth.RequestHostNameEvent;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.MythTvApi028Context;

import retrofit.RetrofitError;

/**
 * Created by dmfrey on 1/31/15.
 */
public class MythServiceV28EventHandler implements MythService {

    private static final String TAG = MythServiceV28EventHandler.class.getSimpleName();

    private static final String HOST_NAME_REQ_ID = "HOST_NAME_REQ_ID";

    MythTvApi028Context mMythTvApiContext;

    String mHostName;

    public MythServiceV28EventHandler() {

        mMythTvApiContext = (MythTvApi028Context) MainApplication.getInstance().getMythTvApiContext();

    }

    @Override
    public HostNameDetailsEvent getHostName( RequestHostNameEvent event ) {
        Log.v( TAG, "getHostName : enter" );

        try {
            ETagInfo eTagInfo = mMythTvApiContext.getEtag( HOST_NAME_REQ_ID, true );
            mHostName = mMythTvApiContext.getMythService().getHostName( eTagInfo, HOST_NAME_REQ_ID );

            Log.v( TAG, "getHostName : exit" );
            return new HostNameDetailsEvent( new HostNameDetails( mHostName ) );

        } catch( RetrofitError e ) {
            //Log.w( TAG, "getHostName : error", e );

            if( 304 == e.getResponse().getStatus() ) {

                Log.v( TAG, "getHostName : exit, not modified" );
                return new HostNameDetailsEvent( new HostNameDetails( mHostName ) );
            }

            if( e.getKind() == RetrofitError.Kind.NETWORK ) {
                MainApplication.getInstance().disconnect();
            }

        }

        Log.v( TAG, "getHostName : exit, not found" );
        return HostNameDetailsEvent.notFound();
    }

}
