package edu.nyu.cs.javagit.client.cli.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.api.commands.GitAddResponse;
import edu.nyu.cs.javagit.client.cli.CliGitAdd;

public class TestCliGitAdd {

  @Before
  public void setUp() throws Exception {
  }
   
  /**
   * Test for NullPointerException
   * @throws IOException
   */
  @Test(expected=NullPointerException.class)
  public void testGitAddNoFilesProvided() throws IOException{
    CliGitAdd gitAdd = new CliGitAdd();
    List<String> options = null;
    List<String> fileNames = null;
    gitAdd.add("/home/gsd216/osp2008/git", options, fileNames);
  }
  
  /**
   * Test for testing IOException
   * @throws IOException thrown if file paths or repository path provided
   *   is found or git command is not found.
   */
  @Test(expected=IOException.class)
  public void testGitAddIOExceptionThrown() throws IOException{
    CliGitAdd gitAdd = new CliGitAdd();
    List<String> options = null;
    List<String> fileNames = new ArrayList<String>();
    fileNames.add("testFile");
    gitAdd.add("/foo/tmp", options, fileNames);
  }
  
  /**
   * Test for testing IllegalArgumentException
   * @throws IOException thrown if file paths or repository path provided
   *   is found or git command is not found.
   */
  @Test(expected=IllegalArgumentException.class)
  public void testGitAddIllegalArgumentRepositoryPath() throws IOException{
    CliGitAdd gitAdd = new CliGitAdd();
    List<String> options = null;
    List<String> fileNames = new ArrayList<String>();
    fileNames.add("testFile");
    gitAdd.add("", options, fileNames);
  }
  
  /**
   * Test for adding files that do not exist in the repository.
   * @throws IOException
   */
  @Test
  public void testGitAddResponseNoMatchingFile() throws IOException {
    CliGitAdd gitAdd = new CliGitAdd();
    List<String> options = new ArrayList<String>();
    options.add("-v");
    List<String> fileNames = new ArrayList<String>();
    fileNames.add("__foobar1__001");
    fileNames.add("__foobar2__002");
    fileNames.add("__foobar3__003");
    GitAddResponse response = gitAdd.add("/home/dhindsg/dhindsg/osp/sampleRepository/test", options, fileNames);
    int filesCount = response.getFileListSize();
    assertEquals(0, filesCount);
    assertTrue( response.inErrorState() );
  }

  @Test
  public void testGitAddNewFile() throws IOException {
    Random rand = new Random();
    int nextRandom = rand.nextInt();
    String fileName = "/home/dhindsg/dhindsg/osp/sampleRepository/test/__newTestFile" + nextRandom;
    File newFile = new File(fileName);
    if ( !newFile.exists()) {
      if ( newFile.createNewFile() ) {
        CliGitAdd gitAdd = new CliGitAdd();
        List<String> options = new ArrayList<String>();
        options.add("-v");
        List<String> fileNames = new ArrayList<String>();
        fileNames.add(fileName);
        GitAddResponse response = gitAdd.add("/home/dhindsg/dhindsg/osp/sampleRepository/test", options, fileNames);
        int filesCount = response.getFileListSize();
        assertEquals(1, filesCount);
      }
    }
  }

}
