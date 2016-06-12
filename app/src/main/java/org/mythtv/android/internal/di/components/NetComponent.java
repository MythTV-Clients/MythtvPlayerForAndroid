package org.mythtv.android.internal.di.components;

import com.squareup.picasso.Picasso;

import org.mythtv.android.internal.di.modules.ApplicationModule;
import org.mythtv.android.view.activity.phone.AbstractBasePhoneActivity;
import org.mythtv.android.internal.di.modules.NetModule;
import org.mythtv.android.internal.di.modules.SharedPreferencesModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by dmfrey on 5/9/16.
 */
@Singleton
@Component( modules = { ApplicationModule.class, SharedPreferencesModule.class, NetModule.class } )
public interface NetComponent {

    void inject( AbstractBasePhoneActivity baseActivity );

    //Exposed to sub-graphs.
    OkHttpClient okHttpClient();
    Picasso picasso();

}
