package org.mythtv.android.tv.internal.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;
import org.mythtv.android.domain.repository.DvrRepository;
import org.mythtv.android.domain.repository.SearchRepository;
import org.mythtv.android.domain.repository.VideoRepository;
import org.mythtv.android.tv.internal.di.modules.ApplicationModule;
import org.mythtv.android.tv.internal.di.modules.SharedPreferencesModule;
import org.mythtv.android.tv.view.activity.AbstractBaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 *
 * Created by dmfrey on 8/30/15.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component( modules = { ApplicationModule.class, SharedPreferencesModule.class } )
public interface ApplicationComponent {

    void inject( AbstractBaseActivity baseActivity );

    //Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    DvrRepository dvrRepository();
    SearchRepository searchRepository();
    ContentRepository contentRepository();
    VideoRepository videoRepository();

    SharedPreferences sharedPreferences();

}
