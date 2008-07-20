package edu.nyu.cs.javagit.api.commands;


import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.api.commands.GitStatus;
import edu.nyu.cs.javagit.api.commands.GitStatusOptions;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.HelperGitCommands;

public class TestGitStatusOptions extends TestCase{

  GitStatusOptions options;
  File repositoryDirectory;
  GitStatus status;
  
  @Before
  public void setUp() throws Exception {
    repositoryDirectory = FileUtilities.createTempDirectory("GitStatusTestRepository");
    HelperGitCommands.initRepo(repositoryDirectory);
    options = new GitStatusOptions();
  }
  
  /**
   * Test for IllegalArgumentException is thrown when -a and -o options are
   * used together in a git-status command.
   */
  @Test
  public void testAllAndOnlyOptionsTogetherThrowJavaGitException() {
    try {
      options.setOptAll(true);
      options.setOptOnly(true);
      fail("IllegalArgumentException not thrown");     
    } catch ( IllegalArgumentException excpected ) {
    }
  }
  
  @After
  public void tearDown() throws Exception {
    if ( repositoryDirectory.exists() ) {
      FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryDirectory);
    }
  }

}
