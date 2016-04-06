package org.mythtv.android.data.entity.mapper;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.ApplicationTestCase;
import org.mythtv.android.data.entity.ProgramEntity;
import org.mythtv.android.data.entity.ProgramListEntity;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeDeserializer;
import org.mythtv.android.data.entity.mapper.serializers.DateTimeSerializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
@Ignore
public class ProgramEntityJsonDataMapperIssue113Test extends ApplicationTestCase {

    private ProgramEntityJsonMapper programEntityJsonMapper;

    private Gson gson;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {

        Type dateTimeType = new TypeToken<DateTime>(){}.getType();
        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .setFieldNamingPolicy( FieldNamingPolicy.UPPER_CAMEL_CASE )
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter( dateTimeType, new DateTimeSerializer() )
                .registerTypeAdapter( dateTimeType, new DateTimeDeserializer() )
                .create();

        programEntityJsonMapper = new ProgramEntityJsonMapper();

    }

    @Test
    public void testTransformProgramEntityCollectionIssue113() throws FileNotFoundException {

        JsonReader reader = new JsonReader( new FileReader( "/opt/Development/Android/git/MythtvPlayerForAndroid/data/src/test/java/org/mythtv/android/data/entity/mapper/Issue113_GetRecordedList-ford.txt" ) );

        Type programListEntityType = new TypeToken<ProgramListEntity>() {}.getType();
        ProgramListEntity programListEntity = this.gson.fromJson( reader, programListEntityType );

        assertThat( programListEntity, not( nullValue() ) );
        assertThat( programListEntity.getPrograms(), not( nullValue() ) );
        assertThat( programListEntity.getPrograms().getCount(), equalTo( 5287 ) );
        assertThat( programListEntity.getPrograms().getPrograms(), not( nullValue() ) );
        assertThat( programListEntity.getPrograms().getPrograms().length, equalTo( 5287 ) );

        for( ProgramEntity programEntity : programListEntity.getPrograms().getPrograms() ) {

            if( null == programEntity.getStartTime() ) {
                System.out.println( "StartTime not set! - " + programEntity.getStartTime() + " " + programEntity.getChannel().getChanId() );
            }
            if( null == programEntity.getChannel() ) {
                System.out.println( "Channel not set! - " + programEntity.getStartTime() + " " + programEntity.getChannel().getChanId() );
            }
            if( null == programEntity.getRecording() ) {
                System.out.println( "Recording not set! - " + programEntity.getStartTime() + " " + programEntity.getChannel().getChanId() );
            }
            if( null == programEntity.getTitle() ) {
                System.out.println( "Title not set! - " + programEntity.getStartTime() + " " + programEntity.getChannel().getChanId() );
            }
            if( null == programEntity.getSubTitle() ) {
                System.out.println( "SubTitle not set! - " + programEntity.getStartTime() + " " + programEntity.getChannel().getChanId() );
            }
            if( null == programEntity.getDescription() ) {
                System.out.println( "Description not set! - " + programEntity.getStartTime() + " " + programEntity.getChannel().getChanId() );
            }
            if( null == programEntity.getInetref() ) {
                System.out.println( "Intetref not set! - " + programEntity.getStartTime() + " " + programEntity.getChannel().getChanId() );
            }
            if( 0 == programEntity.getSeason() ) {
                System.out.println( "Season not set! - " + programEntity.getStartTime() + " " + programEntity.getChannel().getChanId() );
            }
            if( 0 == programEntity.getEpisode() ) {
                System.out.println( "Episode not set! - " + programEntity.getStartTime() + " " + programEntity.getChannel().getChanId() );
            }

        }
    }

}
