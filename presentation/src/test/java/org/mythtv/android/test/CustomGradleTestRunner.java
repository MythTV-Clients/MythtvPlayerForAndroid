package org.mythtv.android.test;

import org.junit.runners.model.InitializationError;
import org.mythtv.android.data.BuildConfig;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

public class CustomGradleTestRunner extends RobolectricGradleTestRunner {

    private static final int MAX_SDK_LEVEL = 21;

    public CustomGradleTestRunner( Class<?> klass ) throws InitializationError {
        super( klass );
    }

    @Override
    public Config getConfig( Method method ) {

        Config config = super.getConfig( method );
		/*
		Fixing up the Config:
		* SDK can not be higher than 21
		* constants must point to a real BuildConfig class
		 */
        config = new Config.Implementation(
                new int[] { MAX_SDK_LEVEL },
                config.manifest(),
                config.qualifiers(),
                config.packageName(),
                config.resourceDir(),
                config.assetDir(),
                config.shadows(),
                config.application(),
                config.libraries(),
                ensureBuildConfig( config.constants() ) );

        return config;
    }

    private Class<?> ensureBuildConfig( Class<?> constants ) {

        if( constants == Void.class ) return BuildConfig.class;

        return constants;
    }

    private int ensureSdkLevel( int sdkLevel ) {

        if( sdkLevel > MAX_SDK_LEVEL ) return MAX_SDK_LEVEL;

        if( sdkLevel <= 0 ) return MAX_SDK_LEVEL;

        return sdkLevel;
    }

}