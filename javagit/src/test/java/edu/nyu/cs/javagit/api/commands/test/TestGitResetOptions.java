package edu.nyu.cs.javagit.api.commands.test;

import junit.framework.TestCase;

import org.junit.Test;

import edu.nyu.cs.javagit.api.CommitName;
import edu.nyu.cs.javagit.api.commands.GitResetOptions;

/**
 * Tests the <code>GitResetOptions</code> class.
 */
public class TestGitResetOptions extends TestCase {

  // TODO (jhl388): add exhaustive tests of the GitResetOptions.equals() method
  
  @Test
  public void testInvalidConstruction() {
    try {
      new GitResetOptions((CommitName) null);
      assertTrue("No NPE thrown when one was expected.  Error!", false);
    } catch (NullPointerException e) {
      assertEquals("000003: An Object argument was not specified but is required.  "
          + "{ variableName=[commitName] }", e.getMessage());
    }

    try {
      new GitResetOptions((GitResetOptions.ResetType) null);
      assertTrue("No NPE thrown when one was expected.  Error!", false);
    } catch (NullPointerException e) {
      assertEquals("000003: An Object argument was not specified but is required.  "
          + "{ variableName=[resetType] }", e.getMessage());
    }

    assertbla(null, null, "000003: An Object argument was not specified but is required.  "
        + "{ variableName=[resetType] }");
    assertbla(GitResetOptions.ResetType.MIXED, null, "000003: An Object argument was not "
        + "specified but is required.  { variableName=[commitName] }");
  }

  private void assertbla(GitResetOptions.ResetType resetType, CommitName commitName,
      String expecteMessage) {
    try {
      new GitResetOptions(resetType, commitName);
      assertTrue("No NPE thrown when one was expected.  Error!", false);
    } catch (NullPointerException e) {
      assertEquals(expecteMessage, e.getMessage());
    }
  }

  @Test
  public void testValidConstruction() {
    GitResetOptions grOpts = new GitResetOptions();
    assertEquals(CommitName.HEAD, grOpts.getCommitName());
    assertEquals(GitResetOptions.ResetType.MIXED, grOpts.getResetType());
    assertTrue(!grOpts.isQuiet());
    assertEquals(grOpts.toString(), "--mixed HEAD");
    assertEquals(grOpts.hashCode(), 17448126);

    grOpts = new GitResetOptions(CommitName.HEAD_1);
    assertEquals(CommitName.HEAD_1, grOpts.getCommitName());
    assertEquals(GitResetOptions.ResetType.MIXED, grOpts.getResetType());
    assertTrue(!grOpts.isQuiet());
    assertEquals(grOpts.toString(), "--mixed HEAD^1");
    assertEquals(grOpts.hashCode(), 17448127);

    grOpts = new GitResetOptions(GitResetOptions.ResetType.HARD);
    assertEquals(CommitName.HEAD, grOpts.getCommitName());
    assertEquals(GitResetOptions.ResetType.HARD, grOpts.getResetType());
    assertTrue(!grOpts.isQuiet());
    assertEquals(grOpts.toString(), "--hard HEAD");
    assertEquals(grOpts.hashCode(), 7603416);

    grOpts = new GitResetOptions(GitResetOptions.ResetType.HARD, CommitName.HEAD_1);
    assertEquals(CommitName.HEAD_1, grOpts.getCommitName());
    assertEquals(GitResetOptions.ResetType.HARD, grOpts.getResetType());
    assertTrue(!grOpts.isQuiet());
    assertEquals(grOpts.toString(), "--hard HEAD^1");
    assertEquals(grOpts.hashCode(), 7603417);

    grOpts.setCommitName(CommitName.HEAD);
    assertEquals(CommitName.HEAD, grOpts.getCommitName());
    assertEquals(grOpts.toString(), "--hard HEAD");
    assertEquals(grOpts.hashCode(), 7603416);

    grOpts.setQuiet(true);
    assertTrue(grOpts.isQuiet());
    assertEquals(grOpts.toString(), "--hard -q HEAD");
    assertEquals(grOpts.hashCode(), 7603417);

    grOpts.setResetType(GitResetOptions.ResetType.SOFT);
    assertEquals(GitResetOptions.ResetType.SOFT, grOpts.getResetType());
    assertEquals(grOpts.toString(), "--soft -q HEAD");
    assertEquals(grOpts.hashCode(), 4686866);
  }

}
