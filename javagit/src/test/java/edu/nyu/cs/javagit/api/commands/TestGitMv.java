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
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

public class TestGitMv extends TestCase {
  //Repository path.
  private File repoPath;
   
  @Before
  protected void setUp() {
  }
  
  @After
  protected void tearDown() throws JavaGitException {
    // delete repo directory.
    FileUtilities.removeDirectoryRecursivelyAndForcefully(repoPath);
  }

  //check if exceptions are thrown below for invalid arguments
  @Test
  public void testGitMvInvalidInput() throws IOException, JavaGitException {
    repoPath = FileUtilities.createTempDirectory("GitMvTestRepo");
    
    //source file.
    File source = new File("t2.pl");

    //destination file.
    File destination = new File("t3.pl");

    GitMv gitMv = new GitMv();
    try {
      gitMv.mv(null, source, destination);
    } catch (Exception e) {
      assertEquals("Should have null pointer exception or illegal argument exception",
          ExceptionMessageMap.getMessage("000003") + "  { variableName=[repository path] }", e
              .getMessage());
    }
    
    List<File> fileList = new ArrayList<File>();
    try {
      gitMv.mv(repoPath, fileList, destination);
    } catch (Exception e) {
      assertEquals("Should have null pointer exception or illegal argument exception",
          ExceptionMessageMap.getMessage("000005") + "  { variableName=[sources] }", 
          e.getMessage());
    }
    
    try {
      gitMv.mv(repoPath, source, null);
    } catch (Exception e) {
      assertEquals("Should have null pointer exception or illegal argument exception",
          ExceptionMessageMap.getMessage("000003") + "  { variableName=[destination] }", e
              .getMessage());
    }

    try {
      gitMv.mv(repoPath, null, source, destination);
    } catch (Exception e) {
      assertEquals("Should have null pointer exception or illegal argument exception",
          ExceptionMessageMap.getMessage("000003") + "  { variableName=[options] }", 
          e.getMessage());
    }
    
    try {
      gitMv.mv(repoPath, source, destination);
    } catch (Exception e) {
      assertEquals("Should have null pointer exception or illegal argument exception",
          ExceptionMessageMap.getMessage("424000")+ "  The git-mv error message:  " +
          		"{ line1=[fatal: Not a git repository] }",
              e.getMessage());
    }
  }
}
