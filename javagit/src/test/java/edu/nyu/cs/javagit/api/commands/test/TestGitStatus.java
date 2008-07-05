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
  
  @Before
  public void setUp() throws JavaGitException, IOException {
    repositoryDirectory = FileUtilities.createTempDirectory("GitStatusTestRepository");
    HelperGitCommands.initRepo(repositoryDirectory);
    gitCommit = new GitCommit();
    gitAdd = new GitAdd();
    gitStatus = new GitStatus();
    options = new GitStatusOptions();
  }
  
  @Test
  public void testBranch() throws JavaGitException, IOException {
    //Create couple of file
    FileUtilities.createFile(repositoryDirectory, "foobar01", 
        "Sameple Contents");
    FileUtilities.createFile(repositoryDirectory, "foobar02", 
        "Sameple Contents");
    String paths = null;
    String repositoryPath = repositoryDirectory.getAbsolutePath();
    GitStatusResponse response = gitStatus.status(repositoryPath, options, paths); 
    String branch = response.getBranch();
    assertEquals("Branch does not match", "master", branch);
  }
  
  /**
   * Test for new files that are created but not yet added
   * by git-add or git-rm commands. These files are under
   * Untracked files section of the output.
   * @throws IOException
   * @throws JavaGitException
   */
  @Test
  public void testUntrackedNewFiles() throws IOException, JavaGitException {
    //Create couple of file
    FileUtilities.createFile(repositoryDirectory, "foobar01", 
        "Sameple Contents");
    FileUtilities.createFile(repositoryDirectory, "foobar02", 
        "Sameple Contents");
    String paths = null;
    String repositoryPath = repositoryDirectory.getAbsolutePath();
    GitStatusResponse response = gitStatus.status(repositoryPath, options, paths);
    int noOfUntrackedFiles = response.getUntrackedFilesSize();
    assertEquals("Error.No of untracked files does not Match.", 2, noOfUntrackedFiles);
    assertEquals("Error. Filename does not match.", "foobar01", 
        response.getFileFromUntrackedFiles(0));
    assertEquals("Error. Filename does not match.", "foobar02", 
        response.getFileFromUntrackedFiles(1));
  }
  
  /**
   * Test for files that will be committed next time <git-commit>
   * is executed. 
   * @throws IOException
   * @throws JavaGitException
   */
  public void testReadyToCommitNewFiles() throws IOException, JavaGitException {
    //Create couple of new files
    FileUtilities.createFile(repositoryDirectory, "foobar01", 
        "Sameple Contents");
    FileUtilities.createFile(repositoryDirectory, "foobar02", 
        "Sameple Contents");
    String repositoryPath = repositoryDirectory.getAbsolutePath();
    List<String> filesToAdd = new ArrayList<String>();
    filesToAdd.add("foobar01");
    filesToAdd.add("foobar02");
    List<String> addOptions = new ArrayList<String>();
    gitAdd.add(repositoryPath, addOptions, filesToAdd);
    String statusPath = null;
    GitStatusResponse status = gitStatus.status(repositoryPath, options, statusPath);
    int noOfNewFilesToCommit = status.getNewFilesToCommitSize();
    assertEquals("Error. No of New Files to commit does not match", 
        2, noOfNewFilesToCommit);
    assertEquals("Error. Filename does not match", "foobar01", 
        status.getFileFromNewFilesToCommit(0));
    assertEquals("Error. Filename does not match", "foobar02", 
        status.getFileFromNewFilesToCommit(1));
  }
  
  /**
   * Test for files that are indexed and have been modified but
   * <git-add> or <git-rm> command need to be run to get them
   * ready for committing next time <git-commit> is executed.
   * 
   * @throws IOException
   * @throws JavaGitException
   */
  public void testModifiedNotUpdatedFiles()throws IOException, JavaGitException {
    // create two new files
    FileUtilities.createFile(repositoryDirectory, "foobar01", 
    "Sameple Contents");
    FileUtilities.createFile(repositoryDirectory, "foobar02", 
        "Sameple Contents");
    String repositoryPath = repositoryDirectory.getAbsolutePath();
    List<String> filesToAdd = new ArrayList<String>();
    filesToAdd.add("foobar01");
    filesToAdd.add("foobar02");
    List<String> addOptions = new ArrayList<String>();
    // Add the files for committing
    gitAdd.add(repositoryPath, addOptions, filesToAdd);
    // Commit the added files
    gitCommit.commit(repositoryPath, "Test commit of two files");
    File file = new File(repositoryPath + File.separator + "foobar01");
    // modify one of the committed files
    FileUtilities.modifyFileContents(file, "Test append text");
    String statusPath = null;
    // run status to find the modified but not updated files
    GitStatusResponse status = gitStatus.status(repositoryPath, options, statusPath);
    int modifiedNotUpdatedFiles = status.getModifiedFilesNotUpdatedSize();
    assertEquals("No of modified but not updated files not equal", 1, modifiedNotUpdatedFiles);
  }

  @After
  public void tearDown() throws Exception {
    FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryDirectory);
  }

}
