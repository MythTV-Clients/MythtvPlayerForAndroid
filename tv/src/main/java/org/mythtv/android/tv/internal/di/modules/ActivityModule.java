package org.mythtv.android.tv.internal.di.modules;

import android.app.Activity;

import org.mythtv.android.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * A module to wrap the Activity state and expose it to the graph.
 *
 * Created by dmfrey on 8/30/15.
 */
@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule( Activity activity ) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }

}
