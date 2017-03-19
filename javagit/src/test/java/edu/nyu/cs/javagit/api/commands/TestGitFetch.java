package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.TestBase;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.url.FileUrl;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Implements test cases for for GitFetch.
 */
public class TestGitFetch
        extends TestBase {

    private File repoPath;
    private final static File tmpFolder = new File(System.getProperty("java.io.tmpdir"));

    @Before
    public void setUp() throws IOException, JavaGitException {
        super.setUp();
        repoPath = FileUtilities.createTempDirectory("TestGitFetchRepository");
        getDeletor().add(repoPath);
        final GitInitResponse gitInitResponse = new GitInit().init(repoPath);
        if ( gitInitResponse.containsError() )
            throw new RuntimeException("Error on git init");
    }

    @Test
    public void testFetch() throws IOException, JavaGitException, URISyntaxException {

        final File cloneFolder = FileUtilities.createNonExistingTempFolder();
        getDeletor().add(cloneFolder);

        new GitClone().clone(tmpFolder, new FileUrl(repoPath), cloneFolder );

        addFileToRepository("uno");

        GitFetchResponse fetchResponse = new GitFetch().fetch(cloneFolder, new FileUrl(repoPath), new GitFetchOptions());
        assertEquals(3, fetchResponse.getObjectsTransfered());
        assertTrue(fetchResponse.getSourceNews().containsKey(new FileUrl(repoPath).toString()));

        // No new objects
        fetchResponse = new GitFetch().fetch(cloneFolder, new FileUrl(repoPath), new GitFetchOptions());
        assertEquals(0, fetchResponse.getObjectsTransfered());
    }

    private File addFileToRepository(String filePath) throws IOException, JavaGitException {
        final File fileUno = FileUtilities.createFile(repoPath, filePath,
                "Uno busca lleno de esperanzas el camino que los sueños prometieron a sus ansias");
        new GitAdd().add(repoPath, fileUno);
        new GitCommit().commit(repoPath, "Uno");
        return fileUno;
    }
}
