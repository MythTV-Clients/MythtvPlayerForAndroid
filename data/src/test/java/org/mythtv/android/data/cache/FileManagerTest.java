package org.mythtv.android.data.cache;

import org.mythtv.android.data.ApplicationTestCase;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileManagerTest extends ApplicationTestCase {

    private FileManager fileManager;
    private File cacheDir;

    @Before
    public void setUp() {
        fileManager = new FileManager();
        cacheDir = RuntimeEnvironment.application.getCacheDir();
    }

    @After
    public void tearDown() {
        if (cacheDir != null) {
            fileManager.clearDirectory(cacheDir);
        }
    }

    @Test
    public void testWriteToFile() {
        File fileToWrite = createDummyFile();
        String fileContent = "content";

        fileManager.writeToFile(fileToWrite, fileContent);

        assertThat(fileToWrite.exists(), is(true));
    }

    @Test
    public void testFileContent() {
        File fileToWrite = createDummyFile();
        String fileContent = "content\n";

        fileManager.writeToFile(fileToWrite, fileContent);
        String expectedContent = fileManager.readFileContent(fileToWrite);

        assertThat(expectedContent, is(equalTo(fileContent)));
    }

    private File createDummyFile() {
        String dummyFilePath = cacheDir.getPath() + File.separator + "dumyFile";
        File dummyFile = new File(dummyFilePath);

        return dummyFile;
    }

}