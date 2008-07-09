package edu.nyu.cs.javagit.api.test;

import junit.framework.TestCase;

import org.junit.Test;

import edu.nyu.cs.javagit.api.Ref;

/**
 * Test case for the <code>CommitName</code> data object.
 */
public class TestCommitName extends TestCase {

  // TODO (jhl388): add exhaustive tests of the CommitName.equals() method
  // TODO (jhl388): add exhaustive tests of the CommitName.hashcode() method

  @Test
  public void testStaticVariables() {
    // Checking HEAD
    assertEquals("Expected CommitNameType of HEAD.", Ref.HEAD.getCommitNameType(),
        Ref.CommitNameType.HEAD);
    assertEquals(Ref.HEAD.getHeadOffset(), 0);
    assertEquals(Ref.HEAD.getSha1Name(), null);

    // Checking HEAD_1
    assertEquals("Expected CommitNameType of HEAD.", Ref.HEAD_1.getCommitNameType(),
        Ref.CommitNameType.HEAD);
    assertEquals(Ref.HEAD_1.getHeadOffset(), 1);
    assertEquals(Ref.HEAD_1.getSha1Name(), null);
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
    Ref cn = Ref.createHeadCommitName(0);
    assertEquals(cn, Ref.HEAD);
    assertTrue(Ref.HEAD == cn);
    assertEquals(cn.toString(), "HEAD");
    cn.hashCode();

    cn = Ref.createHeadCommitName(1);
    assertEquals(cn, Ref.HEAD_1);
    assertTrue(Ref.HEAD_1 == cn);
    assertEquals(cn.toString(), "HEAD^1");
    cn.hashCode();

    cn = Ref.createHeadCommitName(2);
    assertEquals("Expected CommitNameType of HEAD.", cn.getCommitNameType(),
        Ref.CommitNameType.HEAD);
    assertEquals(cn.getHeadOffset(), 2);
    assertEquals(cn.getSha1Name(), null);
    assertEquals(cn.toString(), "HEAD~2");
    cn.hashCode();

    cn = Ref.createHeadCommitName(50);
    assertEquals("Expected CommitNameType of HEAD.", cn.getCommitNameType(),
        Ref.CommitNameType.HEAD);
    assertEquals(cn.getHeadOffset(), 50);
    assertEquals(cn.getSha1Name(), null);
    assertEquals(cn.toString(), "HEAD~50");
    cn.hashCode();
  }

  private void assertIllegalCreateHeadCommitNameArgument(int headOffset, String expectedMessage) {
    try {
      Ref.createHeadCommitName(headOffset);
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
    Ref cn = Ref.createSha1CommitName("a");
    Ref cn2 = Ref.createSha1CommitName("a");
    assertEquals("Expected CommitNameType of SHA1.", cn.getCommitNameType(),
        Ref.CommitNameType.SHA1);
    assertEquals(cn.getHeadOffset(), -1);
    assertEquals(cn.getSha1Name(), "a");
    assertEquals(cn.toString(), "a");
    cn.hashCode();
    assertEquals(cn, cn2);

    cn = Ref.createSha1CommitName("ab238dd4c9fa4d8eabe03715c3e8b212f9532013");
    assertTrue(!cn.equals(cn2));

    cn2 = Ref.createSha1CommitName("ab238dd4c9fa4d8eabe03715c3e8b212f9532013");
    assertEquals("Expected CommitNameType of SHA1.", cn.getCommitNameType(),
        Ref.CommitNameType.SHA1);
    assertEquals(cn.getHeadOffset(), -1);
    assertEquals(cn.getSha1Name(), "ab238dd4c9fa4d8eabe03715c3e8b212f9532013");
    assertEquals(cn.toString(), "ab238dd4c9fa4d8eabe03715c3e8b212f9532013");
    cn.hashCode();
    assertEquals(cn, cn2);
  }

  private void assertCreateSha1CommitNameThrowsNPE(String sha1Name, String message) {
    try {
      Ref.createSha1CommitName(sha1Name);
      assertTrue("NPE exception not thrown when one was expected!", false);
    } catch (NullPointerException e) {
      assertEquals("NPE did not contain the expected message.", message, e.getMessage());
    }
  }

  private void assertCreateSha1CommitNameThrowsIllegalArgException(String sha1Name, String message) {
    try {
      Ref.createSha1CommitName(sha1Name);
      assertTrue("IllegalArgumentException not thrown when one was expected!", false);
    } catch (IllegalArgumentException e) {
      assertEquals("IllegalArgumentException did not contain the expected message.", message, e
          .getMessage());
    }
  }

}
