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

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.client.cli.CliGitStatus;
import edu.nyu.cs.javagit.client.cli.CliGitStatus.GitStatusParser;

public class TestGitStatusResponse extends TestCase {

  GitStatusParser parser;
  CliGitStatus gitStatus;
  
  @Before
  public void setUp() throws Exception {   
    parser = new GitStatusParser();
    gitStatus = new CliGitStatus();
  }
  
  @Test
  public void testGitStatus() {
    parser.parseLine("# On branch foo");
    parser.parseLine("# Changes to be committed:");
    parser.parseLine("#   (use \"git reset HEAD <file>...\" to unstage)");
    parser.parseLine("#");
    parser.parseLine("# new file:   foobar03");
    parser.parseLine("#");
    parser.parseLine("# Untracked files:");
    parser.parseLine("#   (use \"git add <file>...\" to include in what will be committed)");
    parser.parseLine("#");
    parser.parseLine("# foobar01");
    parser.parseLine("# foobar02");

    GitStatusResponse response = parser.getResponse();
    assertEquals("Branch Name", "foo", response.getBranch().getName());
  }
  
  @Test
  public void testModifiedFilePattern() {
    String line = "#  modified:   patttrn06";
    assertTrue(CliGitStatus.Patterns.MODIFIED.matches(line));
    line = "# xyz modified: pattern06";
    assertFalse(CliGitStatus.Patterns.MODIFIED.matches(line));
    // Files with spaces may exist in Windows system
    line = "# modified: xyz pattern06";
    assertTrue(CliGitStatus.Patterns.MODIFIED.matches(line));
    line = "# modified:   dir1/dir2/dir3/foobar07";
    assertTrue(CliGitStatus.Patterns.MODIFIED.matches(line));
  }
  
  @Test
  public void testNewFilePattern() {
    String line = "#  new file:   foobar03";
    assertTrue(CliGitStatus.Patterns.NEW_FILE.matches(line));
    line = "#       new file:                  foobar03";
    line = "# xyz new file: pattern06";
    assertFalse(CliGitStatus.Patterns.NEW_FILE.matches(line));
    // Files with spaces may exist in Windows systems
    line = "# new file: xyz pattern06";
    assertTrue(CliGitStatus.Patterns.NEW_FILE.matches(line));
    line = "#  new file:   dir1/dir2/dir3/foobar07";
    assertTrue(CliGitStatus.Patterns.NEW_FILE.matches(line));
  }
  
  @Test
  public void testDeletedFilePattern() {
    String line = "# deleted:    foobar03";
    assertTrue(CliGitStatus.Patterns.DELETED.matches(line));
    line = "#       deleted:                  foobar03";
    assertTrue(CliGitStatus.Patterns.DELETED.matches(line));
    line = "# xyz deleted: pattern06";
    assertFalse(CliGitStatus.Patterns.DELETED.matches(line));   
    line = "# deleted: xyz pattern06";
    assertTrue(CliGitStatus.Patterns.DELETED.matches(line));  
    line = "#  deleted:   dir1/dir2/dir3/foobar07";
    assertTrue(CliGitStatus.Patterns.DELETED.matches(line));
  }
  
  public void testEmptyHashLinePattern() {
    String line = "#";
    assertTrue(CliGitStatus.Patterns.EMPTY_HASH_LINE.matches(line));
    line = "#      ";
    assertTrue(CliGitStatus.Patterns.EMPTY_HASH_LINE.matches(line));
    line = "# test";
    assertFalse(CliGitStatus.Patterns.EMPTY_HASH_LINE.matches(line));
  }
  
  @Test
  public void testGetFilename() {
    String line = "# deleted:    foobar03";
    assertEquals("foobar03", parser.getFilename(line));
    line = "#  new file:   foobar04";
    assertEquals("foobar04", parser.getFilename(line));
    line = "#  new file:   testDir1/foobar04";
    assertEquals("testDir1/foobar04", parser.getFilename(line));
  }
}
