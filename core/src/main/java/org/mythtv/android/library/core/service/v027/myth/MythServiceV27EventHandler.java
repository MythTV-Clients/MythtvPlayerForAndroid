package org.mythtv.android.library.core.service.v027.myth;

import android.content.Context;
import android.util.Log;

import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.service.MythService;
import org.mythtv.android.library.events.myth.HostNameDetails;
import org.mythtv.android.library.events.myth.HostNameDetailsEvent;
import org.mythtv.android.library.events.myth.RequestHostNameEvent;
import org.mythtv.services.api.ETagInfo;
import org.mythtv.services.api.MythTvApi027Context;
import org.mythtv.services.api.MythTvApiContext;

import retrofit.RetrofitError;

/**
 * Created by dmfrey on 1/31/15.
 */
public class MythServiceV27EventHandler implements MythService {

    private static final String TAG = MythServiceV27EventHandler.class.getSimpleName();

    private static final String HOST_NAME_REQ_ID = "HOST_NAME_REQ_ID";

    MythTvApi027Context mMythTvApiContext;

    String mHostName;

    public MythServiceV27EventHandler() {

        mMythTvApiContext = (MythTvApi027Context) MainApplication.getInstance().getMythTvApiContext();

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
