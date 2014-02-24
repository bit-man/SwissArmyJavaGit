package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.TestBase;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitAdd;
import edu.nyu.cs.javagit.api.commands.GitCommit;
import edu.nyu.cs.javagit.api.commands.GitInitOptions;
import edu.nyu.cs.javagit.api.commands.GitInitResponse;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.fail;

public class TestCliGitInit extends TestBase {
    private File repoDirectory;
    private GitCommit commit;
    private GitAdd add;

    @Before
    public void setUp() throws IOException, JavaGitException {
        super.setUp();
        repoDirectory = FileUtilities.createTempDirectory("GitLogTestRepo");
        getDeletor().add(repoDirectory);
    }

    /**
     * Test to verify if git logs is successfully parsing logs
     *
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testGitInitaliaze() throws IOException, JavaGitException {

        CliGitInit gitInit = new CliGitInit();
        GitInitOptions options = new GitInitOptions();
        GitInitResponse response = gitInit.init(repoDirectory, options);
        if (!response.isInitialized()) {
            fail("Failed to initialize git repository");
        }
    }


}
