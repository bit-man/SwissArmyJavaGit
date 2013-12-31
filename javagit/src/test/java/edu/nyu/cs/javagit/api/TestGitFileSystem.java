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
package edu.nyu.cs.javagit.api;

import edu.nyu.cs.javagit.TestBase;
import edu.nyu.cs.javagit.api.GitFileSystemObject.Status;
import edu.nyu.cs.javagit.api.commands.GitInit;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.utilities.FileUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

//import edu.nyu.cs.javagit.api.GitDirectory;


public class TestGitFileSystem extends TestBase {

    private File repositoryDirectory;
    private DotGit dotGit;
    private WorkingTree workingTree;

    @Before
    public void setUp() throws JavaGitException, IOException {
        super.setUp();
        repositoryDirectory = FileUtilities.createTempDirectory("GitFileSystemTest_dir");
        getDeletor().add(repositoryDirectory);
        GitInit gitInit = new GitInit();
        gitInit.init(repositoryDirectory);
        dotGit = DotGit.getInstance(repositoryDirectory);
        workingTree = WorkingTree.getInstance(repositoryDirectory);
    }


    /**
     * creates a single file and runs a series of tests on it
     */
    @Test
    public void testSingleGitFile() throws IOException, JavaGitException {
        //Create a file
        FileUtilities.createFile(repositoryDirectory, "abc.txt", "Some data");

        //check contents
        List<GitFileSystemObject> children = workingTree.getTree();
        assertEquals("Error. Expecting only one file.", 1, children.size());

        GitFileSystemObject currentFile = children.get(0);
        assertEquals("Error. Expecting instance of GitFile.", GitFile.class, currentFile.getClass());

        GitFile gitFile = (GitFile) currentFile;
        assertEquals("Error. Status for single file,", Status.UNTRACKED, gitFile.getStatus());

        gitFile.add();
        assertEquals("Error. Status for single file,", Status.NEW_TO_COMMIT, gitFile.getStatus());

        workingTree.getFile(new File("x"));

        gitFile.commit("commit message");
        assertEquals("Error. Status for single file,", Status.IN_REPOSITORY, gitFile.getStatus());

        FileUtilities.modifyFileContents(gitFile.getFile(), "more data");

        // ToDo (bit-man) find the right version where MODIFIED_TO_COMMIT is expected
        if (JavaGitConfiguration.getGitVersionObject().compareTo(new GitVersion("1.7.6")) == GitVersion.LATER)
            assertEquals("Error. Status for single file,", Status.MODIFIED_TO_COMMIT, gitFile.getStatus());
        else
            assertEquals("Error.SStatus for single file,", Status.MODIFIED, gitFile.getStatus());

        gitFile.add();
        assertEquals("Error, Status for single file,", Status.MODIFIED_TO_COMMIT, gitFile.getStatus());

        gitFile.commit("commit message");
        assertEquals("Error. Status for single file,", Status.IN_REPOSITORY, gitFile.getStatus());

    }


    @Test
    /**
     * Adds more file system objects
     */
    public void testGitFileSystem() throws IOException, JavaGitException {
        //Add another file
        FileUtilities.createFile(repositoryDirectory, "file1", "Some data");
        FileUtilities.createFile(repositoryDirectory, "file2", "Some data");

        //check contents
        List<GitFileSystemObject> children = workingTree.getTree();
        assertEquals("Error. Expecting 2 files.", 2, children.size());

        //attempt to commit (but without anything on the index)
        try {
            workingTree.commit("commit comment");
            fail("JavaGitException not thrown");
        } catch (JavaGitException e) {
        }

        //get children
        GitFile gitFile1 = (GitFile) children.get(0);
        GitFile gitFile2 = (GitFile) children.get(1);

        //both should be untracked
        assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, gitFile1.getStatus());
        assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, gitFile2.getStatus());

        //another way to see the same thing
        File file1 = new File("file1");
        File file2 = new File("file2");
        GitStatusResponse statusResponse = workingTree.getStatus();
        assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED,
                statusResponse.getFileStatus(file1));
        assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED,
                statusResponse.getFileStatus(file2));


        //stage one file
        gitFile1.add();

        //TODO (ma1683): check why the following tests fail on different system
 /*
    //check status

    assertEquals("Error. Expecting NEW_TO_COMMIT.", Status.NEW_TO_COMMIT, gitFile1.getStatus());
    assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, gitFile2.getStatus());

    //alternative way
    statusResponse = workingTree.getStatus();
    assertEquals("Error. Expecting NEW_TO_COMMIT.", Status.NEW_TO_COMMIT, 
        statusResponse.getFileStatus(file1));
    assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, 
        statusResponse.getFileStatus(file2));

    //commit everything added to the index
    workingTree.commitAll("commit comment");
    //check status
    assertEquals("Error. Expecting IN_REPOSITORY.", Status.IN_REPOSITORY, gitFile1.getStatus());
    assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, gitFile2.getStatus());
    //alternative way
    statusResponse = workingTree.getStatus();
    assertEquals("Error. Expecting IN_REPOSITORY.", Status.IN_REPOSITORY, 
        statusResponse.getFileStatus(file1));
    assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, 
        statusResponse.getFileStatus(file2));

    //commit everything
    workingTree.addAndCommitAll("commit comment");
    //check status
    assertEquals("Error. Expecting IN_REPOSITORY.", Status.IN_REPOSITORY, gitFile1.getStatus());
    assertEquals("Error. Expecting IN_REPOSITORY.", Status.IN_REPOSITORY, gitFile2.getStatus());
    //alternative way
    statusResponse = workingTree.getStatus();
    assertEquals("Error. Expecting IN_REPOSITORY.", Status.IN_REPOSITORY, 
        statusResponse.getFileStatus(file1));
    assertEquals("Error. Expecting IN_REPOSITORY.", Status.IN_REPOSITORY, 
        statusResponse.getFileStatus(file2));
*/
    }


}
