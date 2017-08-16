package org.mythtv.android.data.entity.mapper;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.entity.MythTvTypeAdapterFactory;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.ProgramListEntity;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeTypeConverter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
public class ProgramEntityJsonDataMapperIssue113Test {

    private Gson gson;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE )
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapterFactory( MythTvTypeAdapterFactory.create() )
                .registerTypeAdapter( DateTime.class, new DateTimeTypeConverter() )
                .create();

    }

    @Test
    public void testTransformProgramEntityCollectionIssue113() throws IOException {

        URL url = getClass().getClassLoader().getResource( "Issue113_GetRecordedList-ford.txt" );

        JsonReader reader = new JsonReader( new InputStreamReader( url.openStream() ) );

        Type programListEntityType = new TypeToken<ProgramListEntity>() {}.getType();
        ProgramListEntity programListEntity = this.gson.fromJson( reader, programListEntityType );

        assertThat( programListEntity, not( nullValue() ) );
        assertThat( programListEntity.programs(), not( nullValue() ) );
        assertThat( programListEntity.programs().count(), equalTo( 5287 ) );
        assertThat( programListEntity.programs().programs(), not( nullValue() ) );
        assertThat( programListEntity.programs().programs().size(), equalTo( 5287 ) );

    }

    @Test
    public void testRxTransformProgramEntityCollectionIssue113() throws IOException {

        URL url = getClass().getClassLoader().getResource( "Issue113_GetRecordedList-ford.txt" );

        JsonReader reader = new JsonReader( new InputStreamReader( url.openStream() ) );

        Type programListEntityType = new TypeToken<ProgramListEntity>() {}.getType();
        ProgramListEntity programListEntity = this.gson.fromJson( reader, programListEntityType );

        TestSubscriber<List<ProgramEntity>> testSubscriber = new TestSubscriber<>();
        Observable<List<ProgramEntity>> programEntitiesObservable = Observable.from( programListEntity.programs().programs() )
                .filter( programEntity -> !programEntity.recording().recGroup().equalsIgnoreCase( "LiveTV" ) || !programEntity.recording().storageGroup().equalsIgnoreCase( "LiveTV" ) || "Deleted".equalsIgnoreCase( programEntity.recording().recGroup() ) )
                .toList();

        programEntitiesObservable.subscribe( testSubscriber );
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount( 1 );

    }

}
