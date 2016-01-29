/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.TestBase;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class TestGitAdd extends TestBase {

    File repoDirectory;
    GitAdd gitAdd;
    GitStatus gitStatus;

    @Before
    public void setUp() throws IOException, JavaGitException {
        super.setUp();
        repoDirectory = FileUtilities.createTempDirectory("GitCommitTestRepo");
        getDeletor().add(repoDirectory);
        GitInit gitInit = new GitInit();
        gitInit.init(repoDirectory);
        gitAdd = new GitAdd();
        gitStatus = new GitStatus();
    }

    /**
     * Test for adding couple of files and a directory to the repository in verbose mode and then
     * verifying that these are in fact added by &lt;git-add&gt; command.
     *
     * @throws IOException      thrown -
     *                          <ul>
     *                          <li> if the files do not physically exist.
     *                          <li> if the files do not have proper permissions.
     *                          </ul>
     * @throws JavaGitException thrown if the &lt;git-add&gt; command was not executed.
     */
    @Test
    public void testAddingFilesToRepository() throws IOException, JavaGitException {
        File file1 = FileUtilities.createFile(repoDirectory, "fileB.txt", "This is file fileB.txt");
        File tmpDir = new File(repoDirectory.getAbsolutePath() + File.separator + "dirA");
        if (tmpDir.mkdir()) {
            File file2 = FileUtilities.createFile(repoDirectory, "dirA/fileB.txt", "Sample Contents");
            List<File> paths = new ArrayList<File>();
            paths.add(file1);
            paths.add(new File("dirA"));
            paths.add(file2);
            GitAddOptions options = new GitAddOptions();
            options.setVerbose(true);
            GitAddResponse response = gitAdd.add(repoDirectory, options, paths);
            assertEquals("Number of files added is incorrect", 2, response.getFileListSize());
        } else {
            throw new IOException("Unable to create directory: " + tmpDir);
        }
    }

    /***
     @Test public void testAddFileWithSpace() throws IOException, JavaGitException {
     File file1 = FileUtilities.createFile(repoDirectory, "file 1.txt", "This is file file 1.txt");
     List<File> paths = new ArrayList<File>();
     paths.add(file1);

     GitAddOptions options = new GitAddOptions();
     options.setVerbose(true);
     GitAddResponse response = gitAdd.add(repoDirectory, options, paths);

     assertEquals("Number of files added is incorrect", 1, response.getFileListSize());
     assertEquals("Files added is incorrect", file1, response.get(1));
     }
     ***/

    /**
     * Test to add one file at a time to the repository with no options provided.
     *
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testAddingOneFileToRepository() throws IOException, JavaGitException {
        File file1 = FileUtilities.createFile(repoDirectory, "fileA.txt", "This is file fileA.txt");
        File tmpDir = new File(repoDirectory.getPath() + File.separator + "dirA");
        if (tmpDir.mkdir()) {
            File file2 = FileUtilities.createFile(repoDirectory, "dirA" + File.separator + "fileB.txt",
                    "Sample Contents fileB.txt");
            gitAdd.add(repoDirectory, file1);
            gitAdd.add(repoDirectory, new File("dirA"));
            gitAdd.add(repoDirectory, file2);
            GitStatusResponse statusResponse = gitStatus.status(repoDirectory);
            assertEquals("File to commit", 2, statusResponse.getNewFilesToCommitSize());
        } else {
            fail("Failed to add files to repository");
            throw new IOException("Unable to create directory: " + tmpDir);
        }
    }


    @Test
    @Ignore
    public void testAddingOneFileToRepositoryBlanksInPath() throws IOException, JavaGitException {
        File file1 = FileUtilities.createFile(repoDirectory, "file A.txt", "This is file fileA.txt");
        gitAdd.add(repoDirectory, file1);
        GitStatusResponse statusResponse = gitStatus.status(repoDirectory);
        assertEquals("File to commit", 1, statusResponse.getNewFilesToCommitSize());
        assertEquals("Wrong path, ", repoDirectory.getAbsolutePath() + File.separator + "file A.txt",statusResponse.getNewFilesToCommit().iterator().next().getAbsolutePath() );
    }

    /**
     * Test for adding multiple files to add to repository with no options provided.
     *
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testAddingFilesToRepositoryWithNoOptions() throws IOException, JavaGitException {
        File file1 = FileUtilities.createFile(repoDirectory, "fileA.txt", "This is file fileA.txt");
        File tmpDir = new File(repoDirectory.getAbsolutePath() + File.separator + "dirA");
        if (tmpDir.mkdir()) {
            File file2 = FileUtilities.createFile(repoDirectory, "dirA" + File.separator + "fileB.txt",
                    "Sample Contents fileB.txt");
            List<File> paths = new ArrayList<File>();
            paths.add(file1);
            paths.add(new File("dirA"));
            paths.add(file2);
            gitAdd.add(repoDirectory, paths);
            GitStatusResponse statusResponse = gitStatus.status(repoDirectory);
            assertEquals("File to commit", 2, statusResponse.getNewFilesToCommitSize());
        } else {
            fail("Failed to add files to repository");
            throw new IOException("Unable to create directory: " + tmpDir);
        }
    }

}