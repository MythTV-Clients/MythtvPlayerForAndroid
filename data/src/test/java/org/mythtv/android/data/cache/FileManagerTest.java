package org.mythtv.android.data.cache;

import android.content.Context;

import org.mythtv.android.data.ApplicationTestCase;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileManagerTest extends ApplicationTestCase {

    private static final String TESTS_PREFERENCES = "test_preferences";

    private Context context;
    private FileManager fileManager;
    private File cacheDir;

    @Before
    public void setUp() {

        context = RuntimeEnvironment.application.getApplicationContext();
        fileManager = new FileManager();
        cacheDir = RuntimeEnvironment.application.getCacheDir();

    }

    @After
    public void tearDown() {

        if( null != cacheDir ) {
            fileManager.clearDirectory(cacheDir);
        }

    }

    @Test
    public void testWriteToFile() {

        File fileToWrite = createDummyFile();
        String fileContent = "content";

        fileManager.writeToFile( fileToWrite, fileContent );

        assertThat( fileManager.exists( fileToWrite ), is( true ) );
    }

    @Test
    public void testFileContent() {

        File fileToWrite = createDummyFile();
        String fileContent = "content\n";

        fileManager.writeToFile( fileToWrite, fileContent );
        String expectedContent = fileManager.readFileContent( fileToWrite );

        assertThat( expectedContent, is( equalTo( fileContent ) ) );

    }

    @Test
    public void testWriteToPreferences() {

        String key = "key";
        long value = 1L;

        fileManager.writeToPreferences( context, TESTS_PREFERENCES, key, 1L );

        assertThat( value, is( equalTo( fileManager.getFromPreferences( context, TESTS_PREFERENCES, key ) ) ) );

    }

    private File createDummyFile() {

        String dummyFilePath = cacheDir.getPath() + File.separator + "dumyFile";

        return new File( dummyFilePath );
    }

}