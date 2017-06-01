package org.mythtv.android.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.domain.SettingsKeys;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;

/**
 * Base class for Robolectric data layer tests.
 * Inherit from this class to create a test.
 */
@RunWith( RobolectricTestRunner.class )
@Config( constants = BuildConfig.class, application = ApplicationStub.class, sdk = 23, manifest = Config.NONE )
public abstract class ApplicationTestCase extends TestData{

    @Rule
    public TestRule injectMocksRule = ( base, description ) -> {

        MockitoAnnotations.initMocks(ApplicationTestCase.this );

        return base;
    };

    protected void setMasterBackendInSharedPreferences( final String host, final int port ) {

        SharedPreferences.Editor editor = sharedPreferences().edit();
        editor.putString( SettingsKeys.KEY_PREF_BACKEND_URL, host + ":" + port );
        editor.apply();

    }

    public static Context context() {

        return RuntimeEnvironment.application;
    }

    public static SharedPreferences sharedPreferences() {

        return PreferenceManager.getDefaultSharedPreferences( context() );
    }

    public static File cacheDir() {

        return RuntimeEnvironment.application.getCacheDir();
    }

}
