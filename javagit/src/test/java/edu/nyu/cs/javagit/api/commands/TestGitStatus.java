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
import edu.nyu.cs.javagit.api.commands.GitCommit;
import edu.nyu.cs.javagit.api.commands.GitStatus;
import edu.nyu.cs.javagit.api.commands.GitStatusOptions;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.HelperGitCommands;

public class TestGitStatus extends TestCase {

  private File repositoryDirectory;
  private GitCommit gitCommit;
  private GitAdd gitAdd;
  private GitStatus gitStatus;
  private GitStatusOptions options;

  File file1;
  File file2;
  File testDir;
  File file3;

  @Before
  public void setUp() throws JavaGitException, IOException {
    repositoryDirectory = FileUtilities.createTempDirectory("GitStatusTestRepository");
    HelperGitCommands.initRepo(repositoryDirectory);
    gitCommit = new GitCommit();
    gitAdd = new GitAdd();
    gitStatus = new GitStatus();
    options = new GitStatusOptions();
    // Create Few files
    file1 = new File(repositoryDirectory.getAbsolutePath() + File.separator + "foobar01");
    file2 = new File(repositoryDirectory.getAbsolutePath() + File.separator + "foobar02");
    file1.createNewFile();
    file2.createNewFile();
    testDir = new File(repositoryDirectory.getAbsolutePath() + File.separator + "testDirectory");
    testDir.mkdir();
    // file3 = FileUtilities.createFile(testDir, "foobar03", "Sample contents of foobar03 under
    // testDir\n");
    file3 = new File(testDir.getAbsoluteFile() + File.separator + "foobar03");
    file3.createNewFile();
  }

  /**
   * Test for IOException where Repository Directory is invalid.
   * 
   * @throws JavaGitException
   */
  @Test
  public void testIOExceptionThrownForInvalidRepositoryDirectory() throws JavaGitException {
    repositoryDirectory = new File("/_______non_existing_dir_______");
    try {
      gitStatus.status(repositoryDirectory);
      fail("IOException not thrown");
    } catch (IOException expected) {

    }
  }

  /**
   * Second Test for IOException being thrown where Repository directory does not exist and we try
   * to create a file in that directory.
   */

  @Test
  public void testIOExceptionThrownForInvalidRepositoryDirectory2() throws JavaGitException {
    repositoryDirectory = new File("/_______non_existing_dir________");
    try {
      // Create couple of file
      FileUtilities.createFile(repositoryDirectory, "foobar01", "Sameple Contents");
      List<File> paths = null;
      gitStatus.status(repositoryDirectory, options, paths);
      fail("Failed to throw JavaGitException");
    } catch (IOException expected) {
    }
  }

  /**
   * Test for verifying the branch name of the repository on which git-status command is run
   * 
   * @throws JavaGitException
   * @throws IOException
   */

  @Test
  public void testBranch() throws JavaGitException, IOException {
    List<File> paths = null;
    GitStatusResponse response = gitStatus.status(repositoryDirectory, options, paths);
    String branch = response.getBranch().getName();
    assertEquals("Branch does not match", "master", branch);
  }

  /**
   * Test for new files that are created but not yet added by git-add or git-rm commands. These
   * files are under Untracked files section of the output.
   * 
   * @throws IOException
   * @throws JavaGitException
   */

  @Test
  public void testUntrackedNewFiles() throws IOException, JavaGitException {
    List<File> paths = null;
    GitStatusResponse response = gitStatus.status(repositoryDirectory, options, paths);
    int noOfUntrackedFiles = response.getUntrackedFilesSize();
    assertEquals("Error.No of untracked files does not Match.", 3, noOfUntrackedFiles);
    assertEquals("Error. Filename does not match.", "foobar01", response.getFileFromUntrackedFiles(
        0).getName());
    assertEquals("Error. Filename does not match.", "foobar02", response.getFileFromUntrackedFiles(
        1).getName());
    assertEquals("Error. Filename does not match.", "testDirectory", response
        .getFileFromUntrackedFiles(2).getName());
  }

  /**
   * Test for files that will be committed next time &lt;git-commit&gt; is executed.
   * 
   * @throws IOException
   * @throws JavaGitException
   */
  @Test
  public void testReadyToCommitNewFiles() throws IOException, JavaGitException {
    List<File> filesToAdd = new ArrayList<File>();
    filesToAdd.add(file1);
    filesToAdd.add(file2);
    filesToAdd.add(testDir);
    filesToAdd.add(file3);
    GitAddOptions addOptions = new GitAddOptions();
    gitAdd.add(repositoryDirectory, addOptions, filesToAdd);
    List<File> statusPath = null;
    GitStatusResponse status = gitStatus.status(repositoryDirectory, options, statusPath);
    int noOfNewFilesToCommit = status.getNewFilesToCommitSize();
    assertEquals("Error. No of New Files to commit does not match", 3, noOfNewFilesToCommit);
    assertEquals("Error. Filename does not match", "foobar01", status
        .getFileFromNewFilesToCommit(0).getName());
    assertEquals("Error. Filename does not match", "foobar02", status
        .getFileFromNewFilesToCommit(1).getName());
    assertEquals("Error. Filename does not match", "testDirectory" + File.separator + "foobar03",
        status.getFileFromNewFilesToCommit(2).toString());
  }

  /**
   * Test for files that are indexed and have been modified but git-add or git-rm command need to be
   * run to get them ready for committing next time <git-commit> is executed.
   * 
   * @throws IOException
   * @throws JavaGitException
   */
  @Test
  public void testModifiedNotUpdatedFiles() throws IOException, JavaGitException {
    List<File> filesToAdd = new ArrayList<File>();
    filesToAdd.add(file1);
    filesToAdd.add(file2);
    filesToAdd.add(testDir);
    filesToAdd.add(file3);
    GitAddOptions addOptions = new GitAddOptions();
    // Add the files for committing
    gitAdd.add(repositoryDirectory, addOptions, filesToAdd);
    // Commit the added files
    gitCommit.commit(repositoryDirectory, "Test commit of two files");
    // modify one of the committed files
    FileUtilities.modifyFileContents(file1, "Test append text\n");
    FileUtilities.modifyFileContents(file3, "Another sample text added to foobar03\n");
    List<File> statusPath = null;
    // run status to find the modified but not updated files
    GitStatusResponse status = gitStatus.status(repositoryDirectory, options, statusPath);
    int modifiedNotUpdatedFiles = status.getModifiedFilesNotUpdatedSize();
    assertEquals("No of modified but not updated files not equal", 2, modifiedNotUpdatedFiles);
    if (file2.delete()) {
      status = gitStatus.status(repositoryDirectory, options, statusPath);
      modifiedNotUpdatedFiles = status.getModifiedFilesNotUpdatedSize();
      int deletedFileNotUpdated = status.getDeletedFilesNotUpdatedSize();
      assertEquals("No of deleted files not equal", 1, deletedFileNotUpdated);
      assertEquals("No of modified but not updated files not equal", 2, modifiedNotUpdatedFiles);
    } else {
      fail("Failed to delete file \"foobar02\"");
    }
  }

  @After
  public void tearDown() throws Exception {
    if (repositoryDirectory.exists()) {
      FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryDirectory);
    }
  }
}
