package org.mythtv.android.presentation.provider;

import android.content.Context;

import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.NotificationOptions;

import org.mythtv.android.presentation.R;
import org.mythtv.android.presentation.view.activity.phone.ExpandedControlsActivity;

import java.util.List;

/**
 * Created by dmfrey on 7/10/16.
 */

public class CastOptionsProvider implements OptionsProvider {

    @Override
    public CastOptions getCastOptions( Context context ) {

        NotificationOptions notificationOptions = new NotificationOptions.Builder()
                .setTargetActivityClassName( ExpandedControlsActivity.class.getName() )
                .build();

        CastMediaOptions mediaOptions = new CastMediaOptions.Builder()
                .setNotificationOptions( notificationOptions )
                .setExpandedControllerActivityClassName( ExpandedControlsActivity.class.getName() )
                .build();

        return new CastOptions.Builder()
                .setReceiverApplicationId( context.getString( R.string.app_id ) )
                .setCastMediaOptions( mediaOptions )
                .build();
    }

    @Override
    public List<SessionProvider> getAdditionalSessionProviders( Context context ) {

        return null;
    }

}
