package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.TestBase;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.utilities.FileUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;


public class TestGitMvResponse extends TestBase {
    private File repoDirectory;
    private File source;
    private File destination;
    private GitAdd gitAdd;
    private GitMv gitMv;
    private GitCommit gitCommit;
    private GitMvOptions options;

    private File fileOne;
    private File fileTwo;


    @Before
    public void setUp() throws IOException, JavaGitException {
        super.setUp();
        repoDirectory = FileUtilities.createTempDirectory("GitMvTestRepo");
        getDeletor().add(repoDirectory);
        GitInit gitInit = new GitInit();
        gitInit.init(repoDirectory);
        gitAdd = new GitAdd();
        gitMv = new GitMv();
        gitCommit = new GitCommit();
        options = new GitMvOptions();

        fileOne = FileUtilities.createFile(repoDirectory, "fileOne", "Testfile#1");
        fileTwo = FileUtilities.createFile(repoDirectory, "fileTwo", "Testfile#2");

        // Add files to the repository
        List<File> filesToAdd = new ArrayList<File>();
        filesToAdd.add(fileOne);
        filesToAdd.add(fileTwo);

        gitAdd.add(repoDirectory, null, filesToAdd);
        gitCommit.commit(repoDirectory, "Making the commit");
    }

    @Test
    public void testGitMvErrorResponse() {
        source = fileOne;
        destination = fileTwo;
        options.setOptN(true);
        try {
            GitMvResponse response = gitMv.mv(repoDirectory, options, source,
                    destination);
            assertNull("No response", response);
        } catch (Exception e) {
            assertEquals(true, e.getMessage().contains("424001"));
        }
    }

    @Test
    public void testGitMvValidResponseForceAndDyrRun() {
        source = fileOne;
        destination = fileTwo;
        options.setOptN(true);
        options.setOptF(true);
        try {
            GitMvResponse response = gitMv.mv(repoDirectory, options, source, destination);
            assertEquals("response", "Source: " + source.getPath() + " Destination: " +
                    destination.getPath() + " ", response.toString() );
        } catch (Exception e) {
            assertNull("Exception not expected", e);
        }


        source = fileOne;
        destination = new File("fileThree");
        options.setOptN(true);
        try {
            GitMvResponse response = gitMv.mv(repoDirectory, options, source, destination);
            assertEquals("response", "Source: " + source.getPath() + " Destination: " +
                    destination.getPath() + " ", response.toString());
        } catch (Exception e) {
            assertNull("Exception not expected", e);
        }
    }

    @Test
    public void testGitMvValidResponseDryRun() throws IOException {
        source = fileOne;
        destination = fileTwo;
        options.setOptN(true);
        options.setOptF(false);
        try {
            GitMvResponse response = gitMv.mv(repoDirectory, options, source, destination);
            fail("JavaGitException should be thrown");
        } catch (JavaGitException e) {
            // well done private !
        }


        source = fileOne;
        destination = new File("fileThree");
        options.setOptN(true);
        options.setOptF(false);
        try {
            GitMvResponse response = gitMv.mv(repoDirectory, options, source, destination);
            assertEquals("response", "Source: " + source.getPath() + " Destination: " +
                    destination.getPath() + " ", response.toString());
        } catch (Exception e) {
            assertNull("Exception not expected", e);
        }
    }
}
