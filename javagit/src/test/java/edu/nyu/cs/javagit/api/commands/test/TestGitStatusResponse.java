package edu.nyu.cs.javagit.api.commands.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.client.cli.CliGitStatus;
import edu.nyu.cs.javagit.client.cli.CliGitStatus.GitStatusParser;

public class TestGitStatusResponse extends TestCase {

  GitStatusParser parser;
  
  @Before
  public void setUp() throws Exception {   
    parser = new GitStatusParser();
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
    assertEquals("Branch Name", "foo", response.getBranch());
  }
  
  @Test
  public void testModifiedFilePattern() {
    String line = "#  modified:   patttrn06";
    assertTrue(parser.matchModifiedFilePattern(line));
    line = "# xyz modified: pattern06";
    assertFalse(parser.matchModifiedFilePattern(line));
    line = "# modified: xyz pattern06";
    assertFalse(parser.matchModifiedFilePattern(line));
  }
  
  @Test
  public void testNewFilePattern() {
    String line = "#  new file:   foobar03";
    assertTrue(parser.matchNewFilePattern(line));
    line = "#       new file:                  foobar03";
    line = "# xyz new file: pattern06";
    assertFalse(parser.matchModifiedFilePattern(line));
    line = "# new file: xyz pattern06";
    assertFalse(parser.matchModifiedFilePattern(line));
  }
  
  @Test
  public void testDeletedFilePattern() {
    String line = "# deleted:    foobar03";
    assertTrue(parser.matchDeletedFilePattern(line));
    line = "#       deleted:                  foobar03";
    assertTrue(parser.matchDeletedFilePattern(line));
    line = "# xyz deleted: pattern06";
    assertFalse(parser.matchDeletedFilePattern(line));   
    line = "# deleted: xyz pattern06";
    assertFalse(parser.matchDeletedFilePattern(line));    
  }
  
  public void testEmptyHashLinePattern() {
    String line = "#";
    assertTrue(parser.matchEmptyHashLinePattern(line));
    line = "#      ";
    assertTrue(parser.matchEmptyHashLinePattern(line));
    line = "# test";
    assertFalse(parser.matchEmptyHashLinePattern(line));
  }
  
  @Test
  public void testGetFilename() {
    String line = "# deleted:    foobar03";
    assertEquals("foobar03", parser.getFilename(line));
    line = "#  new file:   foobar04";
    assertEquals("foobar04", parser.getFilename(line));
  }
  
  
  
  
}
