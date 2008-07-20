package edu.nyu.cs.javagit.api.commands;

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
import edu.nyu.cs.javagit.api.commands.GitAddOptions;
import edu.nyu.cs.javagit.api.commands.GitAddResponse;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.HelperGitCommands;

public class TestGitAdd extends TestCase {

  File repoDirectory;
  GitAdd gitAdd;

  @Before
  public void setUp() throws Exception {
    repoDirectory = FileUtilities.createTempDirectory("GitCommitTestRepo");
    HelperGitCommands.initRepo(repoDirectory);

    gitAdd = new GitAdd();
  }

  /**
   * Test for adding couple of files and a directory to the repository in verbose mode and then
   * verifying that these are in fact added by &lt;git-add&gt; command.
   * 
   * @throws IOException
   *           thrown -
   *           <ul>
   *           <li> if the files do not physically exist.
   *           <li> if the files do not have proper permissions.
   *           </ul>
   * @throws JavaGitException
   *           thrown if the &lt;git-add&gt; command was not executed.
   */
  @Test
  public void testAddingFilesToRepository() throws IOException, JavaGitException {
    File file1 = FileUtilities.createFile(repoDirectory, "fileA.txt", "Sameple Contents");
    File tmpDir = new File(repoDirectory.getAbsolutePath() + File.separator + "dirA");
    if (tmpDir.mkdir()) {
      File file2 = FileUtilities.createFile(repoDirectory, "dirA/fileB.txt", "Sameple Contents");
      List<File> paths = new ArrayList<File>();
      paths.add(file1);
      paths.add(file2);
      GitAddOptions options = new GitAddOptions();
      options.setVerbose(true);
      GitAddResponse response = gitAdd.add(repoDirectory, options, paths);
      assertEquals("Number of files added is incorrect", 2, response.getFileListSize());
    } else {
      throw new IOException("Unable to create directory: " + tmpDir);
    }
  }

  @After
  public void tearDown() throws Exception {
    if (repoDirectory.exists()) {
      FileUtilities.removeDirectoryRecursivelyAndForcefully(repoDirectory);
    }
  }

}
