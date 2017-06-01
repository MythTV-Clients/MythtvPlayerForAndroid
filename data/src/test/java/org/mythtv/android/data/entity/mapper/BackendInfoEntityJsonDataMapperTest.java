package org.mythtv.android.data.entity.mapper;

import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mythtv.android.data.entity.BackendInfoEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on 9/18/15.
 */
@RunWith( MockitoJUnitRunner.class )
public class BackendInfoEntityJsonDataMapperTest {

    private static final String JSON_RESPONSE_MYTH_GET_BACKENDINFO = "{\"BackendInfo\": {\"Build\": {\"Version\": \"v0.28.1-23-gaf98262\", \"LibX264\": \"true\", \"LibDNS_SD\": \"true\"}, \"Env\": {\"LANG\": \"en_US.UTF-8\", \"LCALL\": \"\", \"LCCTYPE\": \"\", \"HOME\": \"\\/home\\/mythtv\", \"MYTHCONFDIR\": \"\"}, \"Log\": {\"LogArgs\": \" --verbose general --loglevel info --quiet --syslog local7\"}}}";
    private static final String JSON_RESPONSE_MYTH_GET_BACKENDINFO_BAD = "{\"BackendInfo\": {\"Build\": {\"Version\": \"v0.28.1-23-gaf98262\", \"LibX264\": \"true\", \"LibDNS_SD\": \"true\"}, \"Env\": {\"LANG\": \"en_US.UTF-8\", \"LCALL\": \"\", \"LCCTYPE\": \"\", \"HOME\": \"\\/home\\/mythtv\", \"MYTHCONFDIR\": \"\"}, \"Log\": {\"LogArgs\": \" --verbose general --loglevel info --quiet --syslog local7";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BackendInfoEntityJsonMapper backendInfoEntityJsonMapper;

    @Before
    public void setUp() {

        backendInfoEntityJsonMapper = new BackendInfoEntityJsonMapper();

    }

    @Test
    public void testTransformBackendInfoEntityHappyCase() {

        BackendInfoEntity backendInfoEntity = backendInfoEntityJsonMapper.transformBackendInfoEntity( JSON_RESPONSE_MYTH_GET_BACKENDINFO );

        assertThat( backendInfoEntity.build() ).isNotNull();
        assertThat( backendInfoEntity.build().version() ).isEqualTo( "v0.28.1-23-gaf98262" );
        assertThat( backendInfoEntity.build().libX264() ).isEqualTo( true );
        assertThat( backendInfoEntity.build().libDnsSd() ).isEqualTo( true );
        assertThat( backendInfoEntity.env() ).isNotNull();
        assertThat( backendInfoEntity.env().lang() ).isEqualTo( "en_US.UTF-8" );
        assertThat( backendInfoEntity.env().lcall() ).isEmpty();
        assertThat( backendInfoEntity.env().lccType() ).isEmpty();
        assertThat( backendInfoEntity.env().home() ).isEqualTo( "/home/mythtv" );
        assertThat( backendInfoEntity.env().mythConfDir() ).isEmpty();
        assertThat( backendInfoEntity.log() ).isNotNull();
        assertThat( backendInfoEntity.log().logArgs() ).isEqualTo( " --verbose general --loglevel info --quiet --syslog local7" );

    }

    @Test( expected = JsonSyntaxException.class )
    public void testTransformBackendInfoEntityBadJson() {

        backendInfoEntityJsonMapper.transformBackendInfoEntity( JSON_RESPONSE_MYTH_GET_BACKENDINFO_BAD );

    }

}
