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
import edu.nyu.cs.javagit.api.commands.GitCommitOptions;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.HelperGitCommands;

/**
 * Implements test cases for for GitCommit.
 */
public class TestGitCommit extends TestCase {

  /*
   * TODO (jhl388): Create tests for the following:
   * 
   * commands -- add, move(same dir, different dir in same tree, totally different dir), delete,
   * copy (same as for move), just edits
   * 
   * methods -- all methods in GitCommit, thus all the types of commits.
   * 
   * errors -- test errors states returned by git-commit (?)
   */

  private File repoDirectory;
  private GitCommit commit;
  private GitAdd add;

  private GitCommitOptions options;
  private List<String> paths;

  @Before
  protected void setUp() throws IOException, JavaGitException {
    repoDirectory = FileUtilities.createTempDirectory("GitCommitTestRepo");
    HelperGitCommands.initRepo(repoDirectory);

    commit = new GitCommit();
    add = new GitAdd();

    options = new GitCommitOptions();
    paths = new ArrayList<String>();
  }

  @After
  protected void tearDown() throws JavaGitException {
    // delete repo dir
    FileUtilities.removeDirectoryRecursivelyAndForcefully(repoDirectory);
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

  @Test
  public void testBadArugmentPassing() throws IOException, JavaGitException {
    // GitCommit.commitAll(String, String);
    assertCommitAllNPEThrown(null, null,
        "000001: A String argument was not specified but is required.  "
            + "{ variableName=[repositoryPath] }");
    assertCommitAllNPEThrown("SomePath", null,
        "000001: A String argument was not specified but is required.  { variableName=[message] }");

    assertCommitAllIllegalArgumentExceptionThrown("", "",
        "000001: A String argument was not specified but is required.  "
            + "{ variableName=[repositoryPath] }");
    assertCommitAllIllegalArgumentExceptionThrown("SomePath", "",
        "000001: A String argument was not specified but is required.  { variableName=[message] }");

    // GitCommit.commit(String, GitCommitOptions, String);
    assertCommitWithOptsNPEThrown(null, null, null,
        "000001: A String argument was not specified but is required.  "
            + "{ variableName=[repositoryPath] }");
    assertCommitWithOptsNPEThrown("something", null, null,
        "000001: A String argument was not specified but is required.  { variableName=[message] }");
    assertCommitWithOptsNPEThrown("/some/path", null, "A message",
        "000003: An Object argument was not specified but is required.  { variableName=[options] }");

    assertCommitWithOptsIllegalArgumentExceptionThrown("", null, "",
        "000001: A String argument was not specified but is required.  "
            + "{ variableName=[repositoryPath] }");
    assertCommitWithOptsIllegalArgumentExceptionThrown("c:\\path\\to somewhere", null, "",
        "000001: A String argument was not specified but is required.  { variableName=[message] }");

    // GitCommit.commit(String, GitCommitOptions, String, List<String>);
    assertCommitAllParametersNeededNPEThrown(null, null, null, null,
        "000001: A String argument was not specified but is required.  "
            + "{ variableName=[repositoryPath] }");
    assertCommitAllParametersNeededNPEThrown("something", null, null, null,
        "000001: A String argument was not specified but is required.  { variableName=[message] }");
    assertCommitAllParametersNeededNPEThrown("/some/path", null, "A message", null,
        "000003: An Object argument was not specified but is required.  { variableName=[options] }");
    assertCommitAllParametersNeededNPEThrown("/some/path", options, "A message", null,
        "000002: A List<String> argument was not specified but is required.  "
            + "{ variableName=[paths] }");
    paths.add(null);
    assertCommitAllParametersNeededNPEThrown("something", options, "test msg", paths,
        "000001: A String argument was not specified but is required.  { variableName=[paths] }");

    assertCommitAllParametersNeededIllegalArgumentExceptionThrown("", null, "", null,
        "000001: A String argument was not specified but is required.  "
            + "{ variableName=[repositoryPath] }");
    assertCommitAllParametersNeededIllegalArgumentExceptionThrown("c:\\path\\to somewhere", null,
        "", null,
        "000001: A String argument was not specified but is required.  { variableName=[message] }");
    paths.clear();
    assertCommitAllParametersNeededIllegalArgumentExceptionThrown("/some/path", options,
        "A message", paths, "000002: A List<String> argument was not specified but is required.  "
            + "{ variableName=[paths] }");
    paths.add("");
    assertCommitAllParametersNeededIllegalArgumentExceptionThrown("c:\\path\\to somewhere",
        options, "test message", paths,
        "000001: A String argument was not specified but is required.  { variableName=[paths] }");

    // GitCommit.commit(String, String);
    assertCommitNPEThrown(null, null,
        "000001: A String argument was not specified but is required.  "
            + "{ variableName=[repositoryPath] }");
    assertCommitNPEThrown("SomePath", null,
        "000001: A String argument was not specified but is required.  { variableName=[message] }");

    assertCommitIllegalArgumentExceptionThrown("", "",
        "000001: A String argument was not specified but is required.  "
            + "{ variableName=[repositoryPath] }");
    assertCommitIllegalArgumentExceptionThrown("SomePath", "",
        "000001: A String argument was not specified but is required.  { variableName=[message] }");

    // GitCommit.commitOnly(String, String, List<String>);
    assertCommitOnlyNPEThrown(null, null, null,
        "000001: A String argument was not specified but is required.  "
            + "{ variableName=[repositoryPath] }");
    assertCommitOnlyNPEThrown("something", null, null,
        "000001: A String argument was not specified but is required.  { variableName=[message] }");
    assertCommitOnlyNPEThrown("/some/path", "A message", null,
        "000002: A List<String> argument was not specified but is required.  { variableName=[paths] }");
    paths.clear();
    paths.add(null);
    assertCommitOnlyNPEThrown("something", "test msg", paths,
        "000001: A String argument was not specified but is required.  { variableName=[paths] }");

    assertCommitOnlyIllegalArgumentExceptionThrown("", "", null,
        "000001: A String argument was not specified but is required.  "
            + "{ variableName=[repositoryPath] }");
    assertCommitOnlyIllegalArgumentExceptionThrown("c:\\path\\to somewhere", "", null,
        "000001: A String argument was not specified but is required.  { variableName=[message] }");
    paths.clear();
    assertCommitOnlyIllegalArgumentExceptionThrown("/some/path", "A message", paths,
        "000002: A List<String> argument was not specified but is required.  "
            + "{ variableName=[paths] }");
    paths.add("");
    assertCommitOnlyIllegalArgumentExceptionThrown("c:\\path\\to somewhere", "test message", paths,
        "000001: A String argument was not specified but is required.  { variableName=[paths] }");

  }

  private void assertCommitAllNPEThrown(String repoPath, String message, String expectedMessage) {
    try {
      commit.commitAll(repoPath, message);
      assertTrue("No NullPointerException thrown when one was expected.  Error!", false);
    } catch (NullPointerException e) {
      assertEquals("The message from the caught NPE is not what was expected!", expectedMessage, e
          .getMessage());
    } catch (Throwable e) {
      e.printStackTrace();
      assertTrue("Caught Throwable when none was expected.  Error!", false);
    }
  }

  private void assertCommitAllIllegalArgumentExceptionThrown(String repoPath, String message,
      String expectedMessage) {
    try {
      commit.commitAll(repoPath, message);
      assertTrue("No IllegalArgumentException thrown when one was expected.  Error!", false);
    } catch (IllegalArgumentException e) {
      assertEquals(
          "The message from the caught IllegalArgumentException is not what was expected!",
          expectedMessage, e.getMessage());
    } catch (Throwable e) {
      e.printStackTrace();
      assertTrue("Caught Throwable when none was expected.  Error!", false);
    }
  }

  private void assertCommitWithOptsNPEThrown(String repoPath, GitCommitOptions options,
      String message, String expectedMessage) {
    try {
      commit.commit(repoPath, options, message);
      assertTrue("No NullPointerException thrown when one was expected.  Error!", false);
    } catch (NullPointerException e) {
      assertEquals("The message from the caught NPE is not what was expected!", expectedMessage, e
          .getMessage());
    } catch (Throwable e) {
      e.printStackTrace();
      assertTrue("Caught Throwable when none was expected.  Error!", false);
    }
  }

  private void assertCommitWithOptsIllegalArgumentExceptionThrown(String repoPath,
      GitCommitOptions options, String message, String expectedMessage) {
    try {
      commit.commit(repoPath, options, message);
      assertTrue("No IllegalArgumentException thrown when one was expected.  Error!", false);
    } catch (IllegalArgumentException e) {
      assertEquals(
          "The message from the caught IllegalArgumentException is not what was expected!",
          expectedMessage, e.getMessage());
    } catch (Throwable e) {
      e.printStackTrace();
      assertTrue("Caught Throwable when none was expected.  Error!", false);
    }
  }

  private void assertCommitAllParametersNeededNPEThrown(String repoPath, GitCommitOptions options,
      String message, List<String> paths, String expectedMessage) {
    try {
      commit.commit(repoPath, options, message, paths);
      assertTrue("No NullPointerException thrown when one was expected.  Error!", false);
    } catch (NullPointerException e) {
      assertEquals("The message from the caught NPE is not what was expected!", expectedMessage, e
          .getMessage());
    } catch (Throwable e) {
      e.printStackTrace();
      assertTrue("Caught Throwable when none was expected.  Error!", false);
    }
  }

  private void assertCommitAllParametersNeededIllegalArgumentExceptionThrown(String repoPath,
      GitCommitOptions options, String message, List<String> paths, String expectedMessage) {
    try {
      commit.commit(repoPath, options, message, paths);
      assertTrue("No IllegalArgumentException thrown when one was expected.  Error!", false);
    } catch (IllegalArgumentException e) {
      assertEquals(
          "The message from the caught IllegalArgumentException is not what was expected!",
          expectedMessage, e.getMessage());
    } catch (Throwable e) {
      e.printStackTrace();
      assertTrue("Caught Throwable when none was expected.  Error!", false);
    }
  }

  private void assertCommitNPEThrown(String repoPath, String message, String expectedMessage) {
    try {
      commit.commit(repoPath, message);
      assertTrue("No NullPointerException thrown when one was expected.  Error!", false);
    } catch (NullPointerException e) {
      assertEquals("The message from the caught NPE is not what was expected!", expectedMessage, e
          .getMessage());
    } catch (Throwable e) {
      e.printStackTrace();
      assertTrue("Caught Throwable when none was expected.  Error!", false);
    }
  }

  private void assertCommitIllegalArgumentExceptionThrown(String repoPath, String message,
      String expectedMessage) {
    try {
      commit.commit(repoPath, message);
      assertTrue("No IllegalArgumentException thrown when one was expected.  Error!", false);
    } catch (IllegalArgumentException e) {
      assertEquals(
          "The message from the caught IllegalArgumentException is not what was expected!",
          expectedMessage, e.getMessage());
    } catch (Throwable e) {
      e.printStackTrace();
      assertTrue("Caught Throwable when none was expected.  Error!", false);
    }
  }

  private void assertCommitOnlyNPEThrown(String repoPath, String message, List<String> paths,
      String expectedMessage) {
    try {
      commit.commitOnly(repoPath, message, paths);
      assertTrue("No NullPointerException thrown when one was expected.  Error!", false);
    } catch (NullPointerException e) {
      assertEquals("The message from the caught NPE is not what was expected!", expectedMessage, e
          .getMessage());
    } catch (Throwable e) {
      e.printStackTrace();
      assertTrue("Caught Throwable when none was expected.  Error!", false);
    }
  }

  private void assertCommitOnlyIllegalArgumentExceptionThrown(String repoPath, String message,
      List<String> paths, String expectedMessage) {
    try {
      commit.commitOnly(repoPath, message, paths);
      assertTrue("No IllegalArgumentException thrown when one was expected.  Error!", false);
    } catch (IllegalArgumentException e) {
      assertEquals(
          "The message from the caught IllegalArgumentException is not what was expected!",
          expectedMessage, e.getMessage());
    } catch (Throwable e) {
      e.printStackTrace();
      assertTrue("Caught Throwable when none was expected.  Error!", false);
    }
  }

}
