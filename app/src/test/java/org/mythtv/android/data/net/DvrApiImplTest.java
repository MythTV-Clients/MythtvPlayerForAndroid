package org.mythtv.android.data.net;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.mapper.ProgramEntityJsonMapper;
import org.mythtv.android.data.entity.mapper.TitleInfoEntityJsonMapper;
import org.mythtv.android.domain.SettingsKeys;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by dmfrey on 9/27/15.
 */
@Ignore
public class DvrApiImplTest extends ApplicationTestCase {

    private Context context;
    private TitleInfoEntityJsonMapper titleInfoEntityJsonMapper;
    private ProgramEntityJsonMapper programEntityJsonMapper;

    @Mock
    private DvrApiImpl api;

    @Before
    public void setup() throws Exception {

        MockitoAnnotations.initMocks( this );

        context = RuntimeEnvironment.application.getApplicationContext();
//        titleInfoEntityJsonMapper = new TitleInfoEntityJsonMapper();
//        programEntityJsonMapper = new ProgramEntityJsonMapper();

        setMasterBackendInSharedPreferences();

 //       api = new DvrApiImpl( context, titleInfoEntityJsonMapper, programEntityJsonMapper );

    }

    @Test
    public void testTitleInfoEntityList() throws Exception{

//        when( api.titleInfoEntityList() ).thenReturn( Observable.create( setupTitleInfos() ) );

        Observable<List<TitleInfoEntity>> titleInfoEntityList = api.titleInfoEntityList();
        assertThat(titleInfoEntityList, not(nullValue()));

    }

    @Test
    public void testRecordedProgramEntityList() throws Exception{

        Observable<List<ProgramEntity>> recordedProgramEntityList = api.recordedProgramEntityList( true, 1, 1, null, null, null );
        assertThat( recordedProgramEntityList, not( nullValue() ) );

    }

    @Test
    public void testRecordedProgramEntity() throws Exception{

        Observable<ProgramEntity> recordedProgramEntity = api.recordedProgramById( 1, 1, new DateTime() );
        assertThat( recordedProgramEntity, not( nullValue() ) );

    }

    private static ArrayList<TitleInfoEntity> setupTitleInfos() {

        ArrayList<TitleInfoEntity> titleInfos = new ArrayList<>();

        TitleInfoEntity titleInfoEntity = new TitleInfoEntity();
        titleInfoEntity.setTitle( "test title" );
        titleInfoEntity.setInetref( "test inetref" );
        titleInfoEntity.setCount( 1 );
        titleInfos.add( titleInfoEntity );

        return titleInfos;
    }

    private void setMasterBackendInSharedPreferences() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( SettingsKeys.KEY_PREF_BACKEND_URL, "localhost" );
        editor.putString( SettingsKeys.KEY_PREF_BACKEND_PORT, "6544" );
        editor.apply();

    }

}
