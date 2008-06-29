package edu.nyu.cs.javagit.api.commands.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitAdd;
import edu.nyu.cs.javagit.api.commands.GitCommit;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.HelperGitCommands;

/**
 * Implements test cases for for GitCommit.
 */
public class TestGitCommit extends TestCase {

  // TODO (jhl): Add tests to fully test the GitCommit command implementation.

  private File repoDirectory;
  private GitCommit commit;
  private GitAdd add;

  @Before
  protected void setUp() throws IOException, JavaGitException {
    repoDirectory = FileUtilities.createTempDirectory("GitCommitTestRepo");
    HelperGitCommands.initRepo(repoDirectory);

    commit = new GitCommit();
    add = new GitAdd();
  }

  @Test
  public void testCommit() throws IOException, JavaGitException {
    FileUtilities.createFile(repoDirectory, "fileA.txt", "Sameple Contents");

    // Add a file to the repo
    List<String> filesToAdd = new ArrayList<String>();
    filesToAdd.add("fileA.txt");
    add.add(repoDirectory.getAbsolutePath(), null, filesToAdd);

    // Call commit
    commit.commit(repoDirectory.getAbsolutePath(), "Making a first test commit");
  }

  @After
  protected void tearDown() throws JavaGitException {
    // delete repo dir
    FileUtilities.removeDirectoryRecursivelyAndForcefully(repoDirectory);
  }

}
