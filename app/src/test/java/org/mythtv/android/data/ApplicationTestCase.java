package org.mythtv.android.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mythtv.android.BuildConfig;
import org.mythtv.android.data.entity.MythTvTypeAdapterFactory;
import org.mythtv.android.data.entity.mapper.BooleanJsonMapper;
import org.mythtv.android.data.entity.mapper.EncoderEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.LiveStreamInfoEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.LongJsonMapper;
import org.mythtv.android.data.entity.mapper.ProgramEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.VideoMetadataInfoEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeTypeConverter;
import org.mythtv.android.domain.SettingsKeys;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * Base class for Robolectric data layer tests.
 * Inherit from this class to create a test.
 */
@RunWith( RobolectricTestRunner.class )
@Config( manifest = Config.NONE, constants = BuildConfig.class, sdk = 23 )
public abstract class ApplicationTestCase extends TestData{

    protected final Context context;
    protected final SharedPreferences sharedPreferences;
    protected final Gson gson;
    protected final TitleInfoEntityJsonMapper titleInfoEntityJsonMapper;
    protected final ProgramEntityJsonMapper programEntityJsonMapper;
    protected final EncoderEntityJsonMapper encoderEntityJsonMapper;
    protected final LiveStreamInfoEntityJsonMapper liveStreamInfoEntityJsonMapper;
    protected final VideoMetadataInfoEntityJsonMapper videoMetadataInfoEntityJsonMapper;
    protected final BooleanJsonMapper booleanJsonMapper;
    protected final LongJsonMapper longJsonMapper;

    public ApplicationTestCase() {

        this.context = RuntimeEnvironment.application.getApplicationContext();
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this.context );

        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE )
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapterFactory( MythTvTypeAdapterFactory.create() )
                .registerTypeAdapter( DateTime.class, new DateTimeTypeConverter() )
                .create();

        this.titleInfoEntityJsonMapper = new TitleInfoEntityJsonMapper( this.gson );
        this.programEntityJsonMapper = new ProgramEntityJsonMapper( this.gson );
        this.encoderEntityJsonMapper = new EncoderEntityJsonMapper( this.gson );
        this.liveStreamInfoEntityJsonMapper = new LiveStreamInfoEntityJsonMapper( this.gson );
        this.videoMetadataInfoEntityJsonMapper = new VideoMetadataInfoEntityJsonMapper( this.gson );
        this.booleanJsonMapper = new BooleanJsonMapper();
        this.longJsonMapper = new LongJsonMapper();

    }

    @Before
    public void setup() throws Exception {

        ShadowLog.stream = System.out;

        setMasterBackendInSharedPreferences();

    }

    private void setMasterBackendInSharedPreferences() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( SettingsKeys.KEY_PREF_BACKEND_URL, "localhost:6544" );
//        editor.putString( SettingsKeys.KEY_PREF_BACKEND_PORT, "6544" );
        editor.apply();

    }

    protected void setMasterBackendInSharedPreferences( final String host, final int port ) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( SettingsKeys.KEY_PREF_BACKEND_URL, host + ":" + port );
//        editor.putString( SettingsKeys.KEY_PREF_BACKEND_PORT, String.valueOf( port ) );
        editor.apply();

    }

}
