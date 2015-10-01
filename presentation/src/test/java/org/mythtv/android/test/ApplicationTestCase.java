package org.mythtv.android.test;

import org.junit.runner.RunWith;
import org.mythtv.android.data.BuildConfig;
import org.robolectric.annotation.Config;

/**
 * Base class for Robolectric data layer tests.
 * Inherit from this class to create a test.
 */
@RunWith( CustomGradleTestRunner.class )
@Config( constants = BuildConfig.class, application = ApplicationStub.class )
public abstract class ApplicationTestCase {
}