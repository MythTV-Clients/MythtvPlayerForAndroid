package org.mythtv.android.presentation.internal.di.components;

import com.google.gson.Gson;

import org.mythtv.android.presentation.internal.di.modules.ApplicationModule;
import org.mythtv.android.presentation.internal.di.modules.NetModule;
import org.mythtv.android.presentation.view.activity.phone.AbstractBasePhoneActivity;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 5/9/16.
 */
@Singleton
@Component( modules = { ApplicationModule.class, NetModule.class } )
public interface NetComponent {

    void inject( AbstractBasePhoneActivity baseActivity );

    //Exposed to sub-graphs.
    OkHttpClient okHttpClient();

}
