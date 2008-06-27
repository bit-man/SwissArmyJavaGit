package edu.nyu.cs.javagit.utilities.test;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

import junit.framework.TestCase;

/**
 * Test class for edu.nyu.cs.javagit.utiltites.ExceptionMessageMap.
 */
public class TestExceptionMessageMap extends TestCase {

  @Before
  protected void setUp() {
  }

  @Test
  public void testGetMessage() {
    assertGetMessageValid(null, "NO MESSAGE FOR ERROR CODE. { code=[null] }");
    assertGetMessageValid("", "NO MESSAGE FOR ERROR CODE. { code=[] }");
    assertGetMessageValid("0", "NO MESSAGE FOR ERROR CODE. { code=[0] }");
    assertGetMessageValid("000001", "000001: A String argument was not specified but is required.");
  }

  private void assertGetMessageValid(String code, String expectedMessage) {
    String actualMessage = ExceptionMessageMap.getMessage(code);
    assertEquals("Expected message was not received from ExcpetionMessageMap.getMessage()",
        expectedMessage, actualMessage);
  }

}
