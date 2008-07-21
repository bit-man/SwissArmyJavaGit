package edu.nyu.cs.javagit.api.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.HelperGitCommands;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;


public class TestGitMvResponse extends TestCase {
  private File repoDirectory;
  private File source;
  private File destination;
  private GitAdd gitAdd;
  private GitMv gitMv;
  private GitCommit gitCommit;
  
  private File fileOne;
  private File fileTwo;
  private File fileThree;
  private File fileFour;
  private File fileFive;
  private File fileSix;
  
  private File subDirOne;
  private File subDirTwo;
  private File subDirThree;
  private File subDirFour;
  
  @Before
  protected void setUp() throws IOException, JavaGitException {
    repoDirectory = FileUtilities.createTempDirectory("GitMvTestRepo");
    HelperGitCommands.initRepo(repoDirectory);

    gitAdd = new GitAdd();
    gitMv = new GitMv();
    gitCommit = new GitCommit();
    
    fileOne = FileUtilities.createFile(repoDirectory, "fileOne", "Testfile#1");
    fileTwo = FileUtilities.createFile(repoDirectory, "fileTwo", "Testfile#2");
    fileThree = FileUtilities.createFile(repoDirectory, "fileThree", "Testfile#3");
    fileFour = FileUtilities.createFile(repoDirectory, "fileFour", "Testfile#4");
    fileFive = FileUtilities.createFile(repoDirectory, "fileFive", "Testfile#5");
    fileSix = FileUtilities.createFile(repoDirectory, "fileSix", "Testfile#6");
    
    subDirOne = new File(repoDirectory, "subDirOne");
    subDirOne.mkdir();
    
    subDirTwo = new File(repoDirectory, "subDirTwo");
    subDirTwo.mkdir();
    
    subDirThree = new File(repoDirectory, "subDirThree");
    subDirThree.mkdir();
    
    subDirFour = new File(subDirTwo, "subDirThree");
    subDirFour.mkdir();
    
    // Add files to the repository
    List<File> filesToAdd = new ArrayList<File>();
    filesToAdd.add(fileOne);
    filesToAdd.add(fileTwo);
    filesToAdd.add(fileThree);
    filesToAdd.add(fileFour);
    filesToAdd.add(fileFive);
    filesToAdd.add(subDirOne);
    filesToAdd.add(subDirTwo);
    filesToAdd.add(subDirThree);
    gitAdd.add(repoDirectory, null, filesToAdd);
    
    List<File> addDirToSubDir = new ArrayList<File>();
    addDirToSubDir.add(subDirFour);
    gitAdd.add(subDirTwo, null, addDirToSubDir);
    gitCommit.commit(repoDirectory, "Making the commit");
  }

  @After
  protected void tearDown() throws JavaGitException {
    // delete repo directory
    FileUtilities.removeDirectoryRecursivelyAndForcefully(repoDirectory);
  }

  /*
   * Testing successful response message of git-mv, without an option. To pass this test
   * the source and destination defined below should be valid i.e. source should exist and
   * destination shouldn't exist in given directory(repository path).
   * 
   * Source and destination files should be interchanged after each iteration.
   */
  @Test
  public void testMv() throws IOException, JavaGitException {
    source = fileThree;
    destination = new File(repoDirectory.getAbsolutePath(), "fileEight");

    gitMv.mv(repoDirectory, source, destination);
  }
  
  @Test
  public void testGitMvInvalidResponse() {
    
    //Options to be set for git-mv
    GitMvOptions options = new GitMvOptions();
      
    /*
     * Testing response message and JavaGitException for the case when git-mv, with -f option,
     * succeeds with warning message "destination exists; will overwrite!".
     * 
     * Set the source and destination to files which already exist in given repository path and
     * are added to the repository. The source file should be created and added after each 
     * iteration.
     */
    source = fileFour;
    destination = fileFive;
    options.setOptF(true);
    try {
      GitMvResponse response = gitMv.mv(repoDirectory, options, source, 
          destination);
      assertEquals("a message saying destination exists is expected", 
          "Warning: destination exists; will overwrite!",response.getComment());
    } catch (Exception e) {
      assertNull("Should not get an Exception", e.getMessage());
    }
    
    /*
     * Testing response message and JavaGitException for the case when git-mv, without any option,
     * fails because of bad source file i.e. source file not present in the repository.
     * 
     * Once, above test is successful the source is invalid i.e. it doesn't exist. Hence, following
     * should pass.
     */
    source = new File(repoDirectory.getAbsolutePath(), "fileNine");
    try {
      GitMvResponse response = gitMv.mv(repoDirectory, source, destination);
      assertNull("Response is not expected",response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: bad source, source=" + source.getName()
          + ", destination=" + destination.getName(), e.getMessage());
    }
    /*
     * Testing response message and <code>JavaGitException</code> for the case when git-mv is run 
     * with -n option, and source is invalid i.e. doesn't exist. 
     * Fails with a message 'bad source'.
     * 
     * Source file doesn't exist.
     */
    source = new File(repoDirectory.getAbsolutePath(), "fileNine");
    options.setOptK(false);
    options.setOptF(false);
    options.setOptN(true);
    try {
      GitMvResponse response = gitMv.mv(repoDirectory, options, source, destination);
      assertNull("Response is not expected",response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424001")+"fatal: bad source, source="+ source.getName()
          + ", destination=" +destination.getName()+"\nChecking rename of '" + source.getName() +
          "' to '"+destination.getName() + "'", e.getMessage());
    }
    
    /*
     * Testing response message and <code>JavaGitException</code> for the case when git-mv is run 
     * with -k (quiet) option, and source is invalid i.e. doesn't exist. It is a case of failure 
     * but it doesn't give any message(that is what is expected hence, success).
     * 
     * Source file doesn't exist.
     */
    source = new File(repoDirectory.getAbsolutePath(), "fileNine");
    options.setOptN(false);
    options.setOptF(false);
    options.setOptK(true);
    try {
      GitMvResponse response = gitMv.mv(repoDirectory, options, source, destination);
      assertEquals("No message expected", "",response.getComment()); 
    } catch (Exception e) {
      assertNull("Should not get an Exception", e.getMessage());
    }
        
    /*
     * Testing response message and JavaGitException for the case when git-mv, without any option,
     * fails because destination exists.
     * 
     * Set the source and destination to files which already exist in given repository path.
     */
    source = fileOne;
    destination = fileTwo;
    try {
      GitMvResponse response = gitMv.mv(repoDirectory, source, destination);
      assertNull("No response expected", response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: destination exists, source=" + 
          source.getName() + ", " + "destination=" + destination.getName(), e.getMessage());
    }
    
    /*
     * Testing response message and JavaGitException for the case when git-mv, without any option.
     * It fails because the source file is not under version control i.e. the source file is not 
     * added (by git-add) to the repository yet.
     * 
     * Set the source to a file which exists but not added to the repository. Set the destination 
     * to a file which is not present in the repository.
     */
    source = fileSix;
    destination = new File(repoDirectory.getAbsolutePath(), "fileSeven");
    try {
      GitMvResponse response = gitMv.mv(repoDirectory, source, destination);
      assertNull("No response expected", response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: not under version control, source=" + 
          source.getName() + ", destination=" + destination.getName(), e.getMessage());
    }
    
    /*
     * Testing response message and JavaGitException for the case when git-mv, without any option,
     * fails because source directory is empty.
     * 
     * Set the source to a valid directory which is empty. Set the destination to a name which 
     * doesn't exist.
     */
    source = subDirOne;
    destination = new File(repoDirectory, "subDirFive");
    try {
      GitMvResponse response = gitMv.mv(repoDirectory, source, destination);
      assertNull("No response expected", response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: source directory is empty, source=" + 
          source.getName() + ", destination=" + destination.getName(), e.getMessage());
    }
    
    /*
     * Testing response message and JavaGitException for the case when git-mv, without any option,
     * fails because source directory is not empty and destination is same as source.
     * 
     * Set the source to a valid directory which is non-empty. Set the destination to the same 
     * name.
     */
    source = subDirTwo;
    destination = subDirTwo;
    try {
      GitMvResponse response = gitMv.mv(repoDirectory, source, destination);
      assertNull("No response expected", response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: can not move directory into itself, " +
          "source=" + source.getName() +", destination=" + destination.getName()+"/" + 
          source.getName(), e.getMessage());
    }
    /*
     * Testing response message and JavaGitException for the case when git-mv, without any option,
     * fails because of the following.
     * 
     * Source is set to a valid directory. Destination is set to a directory which has source 
     * directory inside it i.e. source directory is also present inside the destination directory.
     * This gives error message as "cannot move directory over file". 
     * 
     * In this case subDirTwo and subDirThree are present in the given repository plus subDirThree is also
     * present inside subDirTwo.
     */
    source = subDirThree;
    destination = subDirTwo;
    try {
      GitMvResponse response = gitMv.mv(repoDirectory, source, destination);
      assertNull("No response expected", response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: cannot move directory over file, " +
          "source=" + source.getName() +", destination=" + destination.getName() +"/" + 
          source.getName(), e.getMessage());
    }
    
    /*
     * Testing response message and JavaGitException for the case when git-mv, with -n(dry-run) 
     * option, succeeds with source and destination objects.
     * 
     * The source and destination defined below should be valid i.e. source should exist and
     * destination shouldn't exist in given directory(repository path).
     */
    source = fileOne;
    destination = fileTwo;
    options.setOptK(false);
    options.setOptF(false);
    options.setOptN(true);
    try {
      gitMv.mv(repoDirectory, options, source, destination);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424001") + "fatal: destination exists, source=" +
          source.getName() + ", destination=" + destination.getName() + "\nChecking rename of '"
          + source.getName() + "' to '" + destination.getName() +"'", e.getMessage());
    }
  }
}
