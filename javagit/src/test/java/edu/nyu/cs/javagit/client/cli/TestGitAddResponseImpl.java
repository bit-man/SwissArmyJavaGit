package edu.nyu.cs.javagit.client.cli;

import junit.framework.TestCase;

import org.junit.Test;

import edu.nyu.cs.javagit.api.commands.GitAddOptions;
import edu.nyu.cs.javagit.client.cli.CliGitAdd;
import edu.nyu.cs.javagit.client.cli.CliGitAdd.GitAddParser;


public class TestGitAddResponseImpl extends TestCase {

  CliGitAdd gitAdd;
  GitAddParser parser;
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
    gitAdd = new CliGitAdd();
    parser = gitAdd.new GitAddParser();
  }

  @Test
  public void testEnclosingQuoteStringParser() {
    String tokenWithSingQuotes = "'foobar'";
    String tokenWithoutSingleQuotes = "foobar";
    assertTrue(parser.enclosedWithSingleQuotes(tokenWithSingQuotes));
    assertFalse(parser.enclosedWithSingleQuotes(tokenWithoutSingleQuotes));
  }
  
  @Test
  public void testFilterFilename() {
    String tokenWithSingleQuotes = "'This_is_a_test'";
    assertEquals("This_is_a_test", parser.filterFileName(tokenWithSingleQuotes));
    // File with spaces in the name
    tokenWithSingleQuotes = "'This is a test'";
    assertEquals("This is a test", parser.filterFileName(tokenWithSingleQuotes));
    // File name with relative path starting with a dot
    tokenWithSingleQuotes = "'./some/relative/path'";
    assertEquals("./some/relative/path", parser.filterFileName(tokenWithSingleQuotes));
    // File name with relative path starting without a dot
    tokenWithSingleQuotes = "'some/relative/path'";
    assertEquals("some/relative/path", parser.filterFileName(tokenWithSingleQuotes));
    // File name with digits in filename
    tokenWithSingleQuotes = "'some1/relative1'";
    assertEquals("some1/relative1", parser.filterFileName(tokenWithSingleQuotes));
    // File name with single char as filename
    tokenWithSingleQuotes = "'A'";
    assertEquals("A", parser.filterFileName(tokenWithSingleQuotes));
    // File with Windows style path  name
    tokenWithSingleQuotes = "'foo\bar'";
    assertEquals("foo\bar", parser.filterFileName(tokenWithSingleQuotes));
    // Windows file without single quotes
    tokenWithSingleQuotes = "foo\bar";
    assertEquals(null, parser.filterFileName(tokenWithSingleQuotes));
  }
  
  @Test
  public void testGitAddResponseObj() {
    GitAddOptions options = new GitAddOptions();
    options.setDryRun(true);
  }
}
