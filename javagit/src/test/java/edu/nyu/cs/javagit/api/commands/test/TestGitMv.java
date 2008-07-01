package edu.nyu.cs.javagit.api.commands.test;

import junit.framework.TestCase;

import org.junit.Before;

import edu.nyu.cs.javagit.api.commands.GitMv;
import edu.nyu.cs.javagit.api.commands.GitMvOptions;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

public class TestGitMv extends TestCase {
  @Before
  protected void setUp() {
  }

  // check if exception is thrown below for ivalid arguments
  public void testGitMvInvalidInput() {

    // Repository path. Need not be valid.
    String repoPath = "/home/nutan/git_repository";

    // Valid or invalid source file.
    String source = "t2.pl";

    // Valid or invalid destination file.
    String destination = "t3.pl";

    GitMv gitMv = new GitMv();
    try {
      gitMv.mv("", source, destination);
    } catch (Exception e) {
      assertEquals("Should have null pointer exception or illegal argument exception",
          ExceptionMessageMap.getMessage("000001") + "  { variableName=[repository path] }", e
              .getMessage());
    }
    try {
      gitMv.mv(repoPath, "", destination);
    } catch (Exception e) {
      assertEquals("Should have null pointer exception or illegal argument exception",
          ExceptionMessageMap.getMessage("000001") + "  { variableName=[source] }", e.getMessage());
    }
    try {
      gitMv.mv(repoPath, source, "");
    } catch (Exception e) {
      assertEquals("Should have null pointer exception or illegal argument exception",
          ExceptionMessageMap.getMessage("000001") + "  { variableName=[destination] }", e
              .getMessage());
    }

    GitMvOptions options = new GitMvOptions();
    try {
      gitMv.mv(repoPath, options, source, destination);
    } catch (Exception e) {
      assertEquals("Should have null pointer exception or illegal argument exception",
          ExceptionMessageMap.getMessage("000001") + "  { variableName=[options] }", e.getMessage());
    }
  }
}
