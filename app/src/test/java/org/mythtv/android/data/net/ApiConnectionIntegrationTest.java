package org.mythtv.android.data.net;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mythtv.android.data.TestData;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 5/9/17.
 */

public class ApiConnectionIntegrationTest extends TestData {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private MockWebServer server;

    private OkHttpClient client = new OkHttpClient();

    @Before
    public void setup() throws Exception {

        server = new MockWebServer();

    }

    @Test
    public void givenServerAvailable_whenRequestSyncCall_thenJsonResponseIsReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetRecordedList-Recent.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();

        ApiConnection apiConnection = ApiConnection.create( client, server.url( "/Dvr/GetRecordedList?Descencing=true&StartIndex=0&Count=50" ).toString() );

        assertThat( apiConnection.requestSyncCall() ).isEqualTo( json );

        server.shutdown();

    }

    @Test
    public void givenServerNotAvailable_whenRequestSyncCall_thenResponseIsNull() throws Exception {

        String json = getUrlContents( "Dvr_GetRecordedList-Recent.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        server.shutdown();

        ApiConnection apiConnection = ApiConnection.create( client, server.url( "/Dvr/GetRecordedList?Descencing=true&StartIndex=0&Count=50" ).toString() );

        assertThat( apiConnection.requestSyncCall() ).isNull();

    }

    @Test
    public void givenServerAvailable_whenRequestSyncCallWithParameters_thenJsonResponseIsReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetRecordedList-Recent.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();

        ApiConnection apiConnection = ApiConnection.create( client, server.url( "/Dvr/GetRecordedList?Descencing=true&StartIndex=0&Count=50" ).toString() );

        Map<String, String> parameters = new HashMap<>();
        parameters.put( "Id", "test" );
        assertThat( apiConnection.requestSyncCall( parameters ) ).isEqualTo( json );

        server.shutdown();

    }

    @Test
    public void givenServerNotAvailable_whenRequestSyncCallWithParameters_thenResponseIsNull() throws Exception {

        String json = getUrlContents( "Dvr_GetRecordedList-Recent.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();
        server.shutdown();

        ApiConnection apiConnection = ApiConnection.create( client, server.url( "/Dvr/GetRecordedList?Descencing=true&StartIndex=0&Count=50" ).toString() );

        Map<String, String> parameters = new HashMap<>();
        parameters.put( "Id", "test" );
        assertThat( apiConnection.requestSyncCall( parameters ) ).isNull();

    }

    @Test
    public void givenServerAvailable_whenRequestSyncCall_thenServerReturned404() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 404 ) );

        server.start();

        ApiConnection apiConnection = ApiConnection.create( client, server.url( "/Dvr/GetRecordedList?Descencing=true&StartIndex=0&Count=50" ).toString() );

        assertThat( apiConnection.requestSyncCall() ).isEqualTo( "" );

        server.shutdown();

    }

    @Test
    public void givenServerAvailable_whenRequestSyncCall_thenServerReturned500() throws Exception {

        server.enqueue( new MockResponse().setResponseCode( 500 ) );

        server.start();

        ApiConnection apiConnection = ApiConnection.create( client, server.url( "/Dvr/GetRecordedList?Descencing=true&StartIndex=0&Count=50" ).toString() );

        assertThat( apiConnection.requestSyncCall() ).isEqualTo( "" );

        server.shutdown();

    }

    @Test
    public void givenServerAvailable_whenCall_thenJsonResponseIsReturned() throws Exception {

        String json = getUrlContents( "Dvr_GetRecordedList-Recent.json" );

        server.enqueue( new MockResponse().setBody( json ) );

        server.start();

        ApiConnection apiConnection = ApiConnection.create( client, server.url( "/Dvr/GetRecordedList?Descencing=true&StartIndex=0&Count=50" ).toString() );

        assertThat( apiConnection.call() ).isEqualTo( json );

        server.shutdown();

    }

}
