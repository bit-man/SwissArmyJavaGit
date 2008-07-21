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
  
  /**
   * Test for IllegalArgumentException is thrown when -a and -o options are
   * used together in a &lt;git-status&gt; command. This time opt-only is set before
   * opt-all option
   */
  @Test
  public void testAllAndOnlyOptionsTogetherThrowJavaGitException2() {
    try {
      options.setOptOnly(true);
      options.setOptAll(true);
      fail("IllegalArgumentException not thrown");     
    } catch ( IllegalArgumentException excpected ) {
    }
  }
  
  /**
   * Test for IllegalArgumentException is thrown when all(-a) and include(-i) options are
   * used together in a &lt;git-status&gt; command. This time opt-include is set before
   * opt-all option
   */
  @Test
  public void testAllAndOnlyOptionsTogetherThrowJavaGitException3() {
    try {
      options.setOptInclude(true);
      options.setOptAll(true);
      fail("IllegalArgumentException not thrown");     
    } catch ( IllegalArgumentException excpected ) {
    }
  }
  
  /**
   * Test for IllegalArgumentException is thrown when all(-a) and include(-i) options are
   * used together in a &lt;git-status&gt; command. This time opt-all is set before
   * opt-include option
   */
  @Test
  public void testAllAndOnlyOptionsTogetherThrowJavaGitException4() {
    try {
      options.setOptAll(true);
      options.setOptInclude(true);
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
