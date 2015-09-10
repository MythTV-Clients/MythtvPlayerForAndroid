package org.mythtv.android.presentation.internal.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the activity to be memorized in the
 * correct component.
 *
 * Created by dmfrey on 8/30/15.
 */
@Scope
@Retention( RUNTIME )
public @interface PerActivity {
}
