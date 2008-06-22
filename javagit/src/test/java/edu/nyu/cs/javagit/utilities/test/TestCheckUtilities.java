package edu.nyu.cs.javagit.utilities.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * Test cases for testing the edu.nyu.cs.javagit.utilities.CheckUtilities class.
 */
public class TestCheckUtilities extends TestCase {

  @Before
  protected void setup() {

  }

  @Test
  public void testCheckObjectEquals() {
    // Test for false results
    String s1 = "test me!";
    String s2 = null;
    assertTrue(!CheckUtilities.checkObjectsEqual(s1, s2));

    s2 = "test";
    assertTrue(!CheckUtilities.checkObjectsEqual(s1, s2));

    s1 = null;
    assertTrue(!CheckUtilities.checkObjectsEqual(s1, s2));

    // Test for true results
    s1 = "test me!";
    s2 += " me!";
    assertTrue(CheckUtilities.checkObjectsEqual(s1, s2));
  }

  @Test
  public void testCheckUnorderedListsEqual() {
    // Test for false results
    List l1 = new ArrayList<String>();
    List l2 = null;
    assertTrue(!CheckUtilities.checkUnorderedListsEqual(l1, l2));

    l1 = null;
    l2 = new ArrayList<String>();
    assertTrue(!CheckUtilities.checkUnorderedListsEqual(l1, l2));

    l1 = new ArrayList<String>();
    l1.add("str");
    assertTrue(!CheckUtilities.checkUnorderedListsEqual(l1, l2));

    String s = "st";
    s += "r";
    l2.add(s);
    l2.add("Bing Bop");
    assertTrue(!CheckUtilities.checkUnorderedListsEqual(l1, l2));

    s = "Bing";
    l1.add(s);
    assertTrue(!CheckUtilities.checkUnorderedListsEqual(l1, l2));

    // Test for true results
    l1.remove(1);
    s += " Bop";
    l1.add(s);
    assertTrue(CheckUtilities.checkUnorderedListsEqual(l1, l2));
  }

  @Test
  public void testCheckStringListArgument() {
    // Test for exceptions being thrown
    assertCheckStringListArgumentThrowsNPE(null, "aVariableName",
        "000002: A List<String> variable was not specified but is required.  "
            + "{ variableName=[aVariableName] }");

    List<String> l = new ArrayList<String>();
    l.add(null);
    assertCheckStringListArgumentThrowsNPE(l, "aVariableName",
        "000001: A String variable was not specified but is required.  "
            + "{ variableName=[aVariableName] }");

    l.clear();
    l.add("str1");
    l.add(null);
    l.add("str3");
    assertCheckStringListArgumentThrowsNPE(l, "aVariableName",
        "000001: A String variable was not specified but is required.  "
            + "{ variableName=[aVariableName] }");

    l.clear();
    l.add("");
    assertCheckStringListArgumentThrowsIllegalArgException(l, "aVariableName",
        "000001: A String variable was not specified but is required.  "
            + "{ variableName=[aVariableName] }");

    l.clear();
    l.add("str1");
    l.add("");
    l.add("str3");
    assertCheckStringListArgumentThrowsIllegalArgException(l, "aVariableName",
        "000001: A String variable was not specified but is required.  "
            + "{ variableName=[aVariableName] }");

    // Test for valid arguments
    l.clear();
    assertCheckStringListArgumentIsValid(l, "aVariableName");

    l.add("str1");
    l.add("str2");
    l.add("str3");
    l.add("str4");
    assertCheckStringListArgumentIsValid(l, "aVariableName");
  }

  private void assertCheckStringListArgumentIsValid(List<String> l, String variableName) {
    try {
      CheckUtilities.checkStringListArgument(l, variableName);
    } catch (NullPointerException e) {
      assertTrue("NPE exception thrown when one was not expected!", false);
    }
  }

  private void assertCheckStringListArgumentThrowsNPE(List<String> l, String variableName,
      String message) {
    try {
      CheckUtilities.checkStringListArgument(l, variableName);
      assertTrue("NPE exception not thrown when one was expected!", false);
    } catch (NullPointerException e) {
      assertEquals("NPE did not contain the expected message.", message, e.getMessage());
    }
  }

  private void assertCheckStringListArgumentThrowsIllegalArgException(List<String> l,
      String variableName, String message) {
    try {
      CheckUtilities.checkStringListArgument(l, variableName);
      assertTrue("IllegalArgumentException not thrown when one was expected!", false);
    } catch (IllegalArgumentException e) {
      assertEquals("IllegalArgumentException did not contain the expected message.", message, e
          .getMessage());
    }
  }

  @Test
  public void testCheckStringArgument() {
    assertCheckStringArgumentThrowsNPE(null, "aVariableName",
        "000001: A String variable was not specified but is required.  "
            + "{ variableName=[aVariableName] }");

    assertCheckStringArgumentThrowsIllegalArgException("", "aVariableName",
        "000001: A String variable was not specified but is required.  "
            + "{ variableName=[aVariableName] }");

    assertCheckStringArgumentIsValid("str", "SomeVarName");
  }

  private void assertCheckStringArgumentIsValid(String str, String variableName) {
    try {
      CheckUtilities.checkStringArgument(str, variableName);
    } catch (NullPointerException e) {
      assertTrue("NPE exception thrown when one was not expected!", false);
    }
  }

  private void assertCheckStringArgumentThrowsNPE(String str, String variableName, String message) {
    try {
      CheckUtilities.checkStringArgument(str, variableName);
      assertTrue("NPE exception not thrown when one was expected!", false);
    } catch (NullPointerException e) {
      assertEquals("NPE did not contain the expected message.", message, e.getMessage());
    }
  }

  private void assertCheckStringArgumentThrowsIllegalArgException(String str, String variableName,
      String message) {
    try {
      CheckUtilities.checkStringArgument(str, variableName);
      assertTrue("IllegalArgumentException not thrown when one was expected!", false);
    } catch (IllegalArgumentException e) {
      assertEquals("IllegalArgumentException did not contain the expected message.", message, e
          .getMessage());
    }
  }

  @Test
  public void testCheckFileValidity() {
    final String unixFileDir = "/tmp/";
    final String windowsFileDir = "c:\\";
    final String filename1 = "bababa.3223.5asdbbw3gni.gagwe";
    final String filename2 = "baasdfbj65adaba.sdh3hs2s5y23.5asdbbw3gni.gagwe";

    String dirBase = null;
    if (System.getProperty("os.name").contains("indows")) {
      dirBase = windowsFileDir;
    } else {
      dirBase = unixFileDir;
    }

    // Find a test filename that doesnot exist.
    String fullFilePath = dirBase + filename1;
    File file = new File(fullFilePath);
    if (file.exists()) {
      fullFilePath = dirBase + filename2;
      file = new File(fullFilePath);
      if (file.exists()) {
        assertTrue(
            "In checking for file validity, the two test filenames are already in existance.",
            false);
      }
    }

    // Check for invalid case
    assertCheckFileValidityIOException(fullFilePath,
        "010001: File or path does not exist.  { filename=[" + fullFilePath + "] }");

    // Check for valid case
    try {
      if (!file.createNewFile()) {
        assertTrue("Unable to create temporary file to test checkFileValidity", false);
      }
    } catch (IOException e) {
      assertTrue("Unable to create temporary file to test checkFileValidity", false);
    }
    assertCheckFileValidityIsValid(fullFilePath);

    // Cleanup
    if (!file.delete()) {
      assertTrue("Unable to delete temporary file after testing checkFileValidity", false);
    }
  }

  private void assertCheckFileValidityIsValid(String filename) {
    try {
      CheckUtilities.checkFileValidity(filename);
    } catch (IOException e) {
      assertTrue("IOException thrown when one was not expected!", false);
    }
  }

  private void assertCheckFileValidityIOException(String filename, String message) {
    try {
      CheckUtilities.checkFileValidity(filename);
      assertTrue("IOException not thrown when one was expected!", false);
    } catch (IOException e) {
      assertEquals("IllegalArgumentException message not what was expected.", message, e
          .getMessage());
    }
  }

}
