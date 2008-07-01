package edu.nyu.cs.javagit.api.commands.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;

import edu.nyu.cs.javagit.api.commands.GitAddResponse;
import edu.nyu.cs.javagit.api.commands.GitAddResponse.Action;

public class TestGitAddResponse extends TestCase {
  private GitAddResponse response1;
  private GitAddResponse response2;
  private String nullRepositoryPath;
  private List<String> optionsList;


  @Before
  protected void setUp() {
    response1 = new GitAddResponse();
    response1.addOption("-n");
    response1.setRepositoryPath("/home/gsd216/nyu/osp");
    response1.setAction(Action.DRY_RUN_ADD);
    response1.add("/test/path/1");
    response1.add("/test/path/2");
    response1.add("/test/path/3");
    response1.add("/test/path/4");
    
    response2 = new GitAddResponse();
    optionsList = new ArrayList<String>();
    optionsList.add("-v");
    optionsList.add("-f");
    response2.setOptions(optionsList);
    response2.setAction(Action.ADD);
  }
  
  
  public void testGitAddOptions() {
    assertEquals("Options not equal", "-n", response1.getOption(0));
    
    List<String> optionsList = response2.getOptions();
    assertEquals("Options not equal", "-v", optionsList.get(0));
    assertEquals("Options not equal", "-f", optionsList.get(1));
  }
  
  public void testGitAddRepositoryPath() {
    assertEquals("RepositoryPath does not match", response1.getRepositoryPath(), "/home/gsd216/nyu/osp");
  }
  
  public void testGitAddAction() {
    assertEquals("Git Add Action does not match", response1.getAction(), Action.DRY_RUN_ADD);
    assertEquals("Git Add Action does not match", response2.getAction(), Action.ADD);
  }
  
  /**
   * Test for checking the filepaths added to response and the no. of filePaths added
   */
  public void testGitAddFilePaths() {
    assertEquals("FilePath does not match", "/test/path/1", response1.get(0));
    assertEquals("FilePath does not match", "/test/path/2", response1.get(1));
    assertEquals("FilePath does not match", "/test/path/3", response1.get(2));
    assertEquals("FilePath does not match", "/test/path/4", response1.get(3));
    assertEquals("FilePathList size does not match", response1.getFileListSize(), 4);
  }
}
