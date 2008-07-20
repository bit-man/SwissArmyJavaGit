package edu.nyu.cs.javagit.api.commands;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitAddResponse;

public class TestGitAddResponse extends TestCase {

  @Before
  protected void setUp() throws IOException, JavaGitException, Exception {
    super.setUp();
  }
  
  @Test
  public void testVoidTest() {
    assertTrue(true);
  }
}
