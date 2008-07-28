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
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.HelperGitCommands;


public class TestGitMvResponse extends TestCase {
  private File repoDirectory;
  private File source;
  private File destination;
  private GitAdd gitAdd;
  private GitMv gitMv;
  private GitCommit gitCommit;
  
  private File fileOne;
  private File fileTwo;

  
  @Before
  protected void setUp() throws IOException, JavaGitException {
    repoDirectory = FileUtilities.createTempDirectory("GitMvTestRepo");
    HelperGitCommands.initRepo(repoDirectory);

    gitAdd = new GitAdd();
    gitMv = new GitMv();
    gitCommit = new GitCommit();
    
    fileOne = FileUtilities.createFile(repoDirectory, "fileOne", "Testfile#1");
    fileTwo = FileUtilities.createFile(repoDirectory, "fileTwo", "Testfile#2");
    
    // Add files to the repository
    List<File> filesToAdd = new ArrayList<File>();
    filesToAdd.add(fileOne);
    filesToAdd.add(fileTwo);

    gitAdd.add(repoDirectory, null, filesToAdd);
    gitCommit.commit(repoDirectory, "Making the commit");
  }

  @After
  protected void tearDown() throws JavaGitException {
    // delete repo directory
    FileUtilities.removeDirectoryRecursivelyAndForcefully(repoDirectory);
  }

  @Test
  public void testGitMvInvalidResponse() {
    //Options to be set for git-mv
    GitMvOptions options = new GitMvOptions();
    
    source = fileOne;
    destination = fileTwo;
    options.setOptN(true);
    try {
      GitMvResponse response = gitMv.mv(repoDirectory, options, source, 
          destination);
      assertNull("No response",response);
    } catch (Exception e) {
      assertEquals("Equal", "424001: Error calling git-mv for dry-run.   " +
      		"The git-mv dry-run error message:  { "
              + "line1=[fatal: destination exists, source="+ fileOne.getPath() +", destination="+ 
              fileTwo.getPath() + "], line2=[Checking rename of '" + fileOne.getPath() +"' to '" + 
              fileTwo.getPath() + "'] }", e.getMessage());
    }
  }
}
