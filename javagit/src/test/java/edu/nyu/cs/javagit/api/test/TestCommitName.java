package edu.nyu.cs.javagit.api.test;

import junit.framework.TestCase;

import org.junit.Test;

import edu.nyu.cs.javagit.api.CommitName;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * Test case for the <code>CommitName</code> data object.
 */
public class TestCommitName extends TestCase {

  @Test
  public void testStaticVariables() {
    // Checking HEAD
    assertEquals("Expected CommitNameType of HEAD.", CommitName.HEAD.getCommitNameType(),
        CommitName.CommitNameType.HEAD);
    assertEquals(CommitName.HEAD.getHeadOffset(), 0);
    assertEquals(CommitName.HEAD.getSha1Name(), null);

    // Checking HEAD_1
    assertEquals("Expected CommitNameType of HEAD.", CommitName.HEAD_1.getCommitNameType(),
        CommitName.CommitNameType.HEAD);
    assertEquals(CommitName.HEAD_1.getHeadOffset(), 1);
    assertEquals(CommitName.HEAD_1.getSha1Name(), null);
  }

  @Test
  public void testCreateHeadCommitName() {
    // Testing invalid input
    assertIllegalCreateHeadCommitNameArgument(-1,
        "000005: The int argument is not greater than the lower bound (lowerBound < toCheck).  "
            + "{ toCheck=[-1], lowerBound=[-1], variableName=[headOffset] }");
    assertIllegalCreateHeadCommitNameArgument(-23,
        "000005: The int argument is not greater than the lower bound (lowerBound < toCheck).  "
            + "{ toCheck=[-23], lowerBound=[-1], variableName=[headOffset] }");

    // Testing valid input
    CommitName cn = CommitName.createHeadCommitName(0);
    assertEquals(cn, CommitName.HEAD);
    assertTrue(CommitName.HEAD == cn);
    assertEquals(cn.toString(), "HEAD");
    assertEquals(cn.hashCode(), 1043272);

    cn = CommitName.createHeadCommitName(1);
    assertEquals(cn, CommitName.HEAD_1);
    assertTrue(CommitName.HEAD_1 == cn);
    assertEquals(cn.toString(), "HEAD^1");
    assertEquals(cn.hashCode(), 1043273);

    cn = CommitName.createHeadCommitName(2);
    assertEquals("Expected CommitNameType of HEAD.", cn.getCommitNameType(),
        CommitName.CommitNameType.HEAD);
    assertEquals(cn.getHeadOffset(), 2);
    assertEquals(cn.getSha1Name(), null);
    assertEquals(cn.toString(), "HEAD~2");
    assertEquals(cn.hashCode(), 1043274);

    cn = CommitName.createHeadCommitName(50);
    assertEquals("Expected CommitNameType of HEAD.", cn.getCommitNameType(),
        CommitName.CommitNameType.HEAD);
    assertEquals(cn.getHeadOffset(), 50);
    assertEquals(cn.getSha1Name(), null);
    assertEquals(cn.toString(), "HEAD~50");
    assertEquals(cn.hashCode(), 1043322);
  }

  private void assertIllegalCreateHeadCommitNameArgument(int headOffset, String expectedMessage) {
    try {
      CommitName.createHeadCommitName(headOffset);
      assertTrue("No IllegalArgumentException thrown when one was expected.  Error!", false);
    } catch (IllegalArgumentException e) {
      assertEquals("IllegalArgumentException didn't contain expected message.  Error!",
          expectedMessage, e.getMessage());
    }
  }

  @Test
  public void testCreateSha1CommitName() {
    // Testing invalid input
    assertCreateSha1CommitNameThrowsNPE(null,
        "000001: A String argument was not specified but is required.  { variableName=[sha1Name] }");
    assertCreateSha1CommitNameThrowsIllegalArgException("",
        "000001: A String argument was not specified but is required.  { variableName=[sha1Name] }");

    // Testing valid input
    CommitName cn = CommitName.createSha1CommitName("a");
    CommitName cn2 = CommitName.createSha1CommitName("a");
    assertEquals("Expected CommitNameType of SHA1.", cn.getCommitNameType(),
        CommitName.CommitNameType.SHA1);
    assertEquals(cn.getHeadOffset(), -1);
    assertEquals(cn.getSha1Name(), "a");
    assertEquals(cn.toString(), "a");
    assertEquals(cn.hashCode(), 7896522);
    assertEquals(cn, cn2);

    cn = CommitName.createSha1CommitName("ab238dd4c9fa4d8eabe03715c3e8b212f9532013");
    assertTrue(!cn.equals(cn2));

    cn2 = CommitName.createSha1CommitName("ab238dd4c9fa4d8eabe03715c3e8b212f9532013");
    assertEquals("Expected CommitNameType of SHA1.", cn.getCommitNameType(),
        CommitName.CommitNameType.SHA1);
    assertEquals(cn.getHeadOffset(), -1);
    assertEquals(cn.getSha1Name(), "ab238dd4c9fa4d8eabe03715c3e8b212f9532013");
    assertEquals(cn.toString(), "ab238dd4c9fa4d8eabe03715c3e8b212f9532013");
    assertEquals(cn.hashCode(), -1268057138);
    assertEquals(cn, cn2);
  }

  private void assertCreateSha1CommitNameThrowsNPE(String sha1Name, String message) {
    try {
      CommitName.createSha1CommitName(sha1Name);
      assertTrue("NPE exception not thrown when one was expected!", false);
    } catch (NullPointerException e) {
      assertEquals("NPE did not contain the expected message.", message, e.getMessage());
    }
  }

  private void assertCreateSha1CommitNameThrowsIllegalArgException(String sha1Name, String message) {
    try {
      CommitName.createSha1CommitName(sha1Name);
      assertTrue("IllegalArgumentException not thrown when one was expected!", false);
    } catch (IllegalArgumentException e) {
      assertEquals("IllegalArgumentException did not contain the expected message.", message, e
          .getMessage());
    }
  }

}
