package org.mythtv.android.tv.internal.di.components;

import android.app.Activity;

import org.mythtv.android.presentation.internal.di.PerActivity;
import org.mythtv.android.tv.internal.di.modules.ActivityModule;

import dagger.Component;

/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 * {@link PerActivity}
 *
 * Created by dmfrey on 8/30/15.
 */
@PerActivity
@Component( dependencies = ApplicationComponent.class, modules = ActivityModule.class )
public interface ActivityComponent {

    //Exposed to sub-graphs.
    Activity activity();

}
