package edu.nyu.cs.javagit.api.commands.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitAdd;
import edu.nyu.cs.javagit.api.commands.GitMv;
import edu.nyu.cs.javagit.api.commands.GitMvOptions;
import edu.nyu.cs.javagit.api.commands.GitMvResponse;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import edu.nyu.cs.javagit.test.utilities.HelperGitCommands;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;


public class TestGitMvReponse extends TestCase {
  private File repoDirectory;
  private String source;
  private String destination;
  private GitAdd add;
  
  private String subDir = "sub_dir";
  
  /*
   * Before starting this test making sure that repository path is the path to a working directory
   * where the files and sub-directories are located.
   * 
   * Also, making sure that 
   * files 'test.pl','t1.pl, 't2.pl', 't4.pl', 't9.pl', are present 
   * 't3.pl', 't5.pl','t111.pl' are not present and, 
   * 't6.pl' is present but not under version control.
   * 
   * Folder 'sub_dir' is present and empty. Folder temp_dir doesn't exist.
   * 
   * Folders 'sub_dir2' is non-empty and, 'sub_dir3' is present in repoDirectory and sub_dir2.
   */
  @Before
  protected void setUp() throws IOException, JavaGitException {
    repoDirectory = FileUtilities.createTempDirectory("GitMvTestRepo");
    HelperGitCommands.initRepo(repoDirectory);

    add = new GitAdd();
    FileUtilities.createFile(repoDirectory, "test.pl", "Testfile");
    FileUtilities.createFile(repoDirectory, "t1.pl", "Testfile#1");
    FileUtilities.createFile(repoDirectory, "t2.pl", "Testfile#2");
    FileUtilities.createFile(repoDirectory, "t4.pl", "Testfile#4");
    FileUtilities.createFile(repoDirectory, "t9.pl", "Testfile#9");
    FileUtilities.createFile(repoDirectory, "t6.pl", "Testfile#6");
    
    
    File dir1 = new File(repoDirectory, subDir);
    dir1.mkdir();
    
    File dir2 = new File(repoDirectory, subDir+"2");
    dir2.mkdir();
    
    File dir3 = new File(repoDirectory, subDir+"3");
    dir3.mkdir();
    
    File dir4 = new File(dir2, subDir+"3");
    dir4.mkdir();
    
    // Add files to the repository
    List<String> filesToAdd = new ArrayList<String>();
    filesToAdd.add("test.pl");
    filesToAdd.add("t1.pl");
    filesToAdd.add("t2.pl");
    filesToAdd.add("t4.pl");
    filesToAdd.add("t9.pl");
    filesToAdd.add("sub_dir");
    filesToAdd.add("sub_dir2");
    filesToAdd.add("sub_dir3");
    add.add(repoDirectory.getAbsolutePath(), null, filesToAdd);
    
    List<String> addDirToSubDir = new ArrayList<String>();
    addDirToSubDir.add("sub_dir3");
    add.add(dir2.getAbsolutePath(), null, addDirToSubDir);
  }
  
  @After
  protected void tearDown() throws JavaGitException {
    // delete repo dir
    FileUtilities.removeDirectoryRecursivelyAndForcefully(repoDirectory);
  }
  
  
  public void testGitMvInvalidResponse() {
    
    //Options to be set for git-mv
    GitMvOptions options = new GitMvOptions();
      
    GitMv gitMv = new GitMv();
    
    /**
     * Testing successful response message of git-mv, without an option. To pass this test
     * the source and destination defined below should be valid i.e. source should exist and
     * destination shouldn't exist in given directory(repository path).
     * 
     * Source and destination files should be interchanged after each iteration.
     */
    source = "t2.pl";
    destination = "t3.pl";
    try {
      GitMvResponse response = gitMv.mv(repoDirectory.getAbsolutePath(), source, destination);
      assertEquals("Empty Response for success expected", "", response.getComment());
      assertEquals("Empty Response for success expected", "", response.getSource());
      assertEquals("Empty Response for success expected", "", response.getDestination());
    } catch (Exception e) {
      assertNull("Should not get an Exception", e.getMessage());
    }
    
    /**
     * Testing response message and JavaGitException for the case when git-mv, with -f option,
     * succeeds with warning message "destination exists; will overwrite!".
     * 
     * Set the source and destination to files which already exist in given repository path and
     * are added to the repository. The source file should be created and added after each 
     * iteration.
     */
    source = "t4.pl";
    destination = "t9.pl";
    options.setOptF(true);
    try {
      GitMvResponse response = gitMv.mv(repoDirectory.getAbsolutePath(), options, source, 
          destination);
      assertEquals("a message saying destination exists is expected", 
          "Warning: destination exists; will overwrite!",response.getComment());
    } catch (Exception e) {
      assertNull("Should not get an Exception", e.getMessage());
    }
    
    /**
     * Testing response message and JavaGitException for the case when git-mv, without any option,
     * fails because of bad source file i.e. source file not present in the repository.
     * 
     * Once, above test is successful the source is invalid i.e. it doesn't exist. Hence, following
     * should pass.
     */
    source = "t111.pl";
    try {
      GitMvResponse response = gitMv.mv(repoDirectory.getAbsolutePath(), source, destination);
      assertNull("Response is not expected",response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: bad source, source=" + source +", " +
          "destination=" + destination, e.getMessage());
    }
    /**
     * Testing response message and <code>JavaGitException</code> for the case when git-mv is run 
     * with -n option, and source is invalid i.e. doesn't exist. 
     * Fails with a message 'bad source'.
     * 
     * Source file doesn't exist.
     */
    source = "t111.pl";
    options.setOptK(false);
    options.setOptF(false);
    options.setOptN(true);
    try {
      GitMvResponse response = gitMv.mv(repoDirectory.getAbsolutePath(), options, source, 
          destination);
      assertNull("Response is not expected",response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424001")+"fatal: bad source, source="+ source+", " +
          "destination=" +destination+"\nChecking rename of '"+source+"' to '"+destination
          + "'", e.getMessage());
    }
    
    /**
     * Testing response message and <code>JavaGitException</code> for the case when git-mv is run 
     * with -k (quiet) option, and source is invalid i.e. doesn't exist. It is a case of failure 
     * but it doesn't give any message(that is what is expected hence, success).
     * 
     * Source file doesn't exist.
     */
    source = "t111.pl";
    options.setOptN(false);
    options.setOptF(false);
    options.setOptK(true);
    try {
      GitMvResponse response = gitMv.mv(repoDirectory.getAbsolutePath(), options, source, 
          destination);
      assertEquals("No message expected", "",response.getComment()); 
    } catch (Exception e) {
      assertNull("Should not get an Exception", e.getMessage());
    }
        
    /**
     * Testing response message and JavaGitException for the case when git-mv, without any option,
     * fails because destination exists.
     * 
     * Set the source and destination to files which already exist in given repository path.
     */
    source = "test.pl";
    destination = "t1.pl";
    try {
      GitMvResponse response = gitMv.mv(repoDirectory.getAbsolutePath(), source, destination);
      assertNull("No response expected", response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: destination exists, source=" + source +
          ", " + "destination=" + destination, e.getMessage());
    }
    
    /**
     * Testing response message and JavaGitException for the case when git-mv, without any option.
     * It fails because the source file is not under version control i.e. the source file is not 
     * added (by git-add) to the repository yet.
     * 
     * Set the source to a file which exists but not added to the repository. Set the destination 
     * to a file which is not present in the repository.
     */
    source = "t6.pl";
    destination = "t5.pl";
    try {
      GitMvResponse response = gitMv.mv(repoDirectory.getAbsolutePath(), source, destination);
      assertNull("No response expected", response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: not under version control, source=" + 
          source + ", destination=" + destination, e.getMessage());
    }
    
    /**
     * Testing response message and JavaGitException for the case when git-mv, without any option,
     * fails because source directory is empty.
     * 
     * Set the source to a valid directory which is empty. Set the destination to a name which 
     * doesn't exist.
     */
    source = "sub_dir";
    destination = "temp_dir";
    try {
      GitMvResponse response = gitMv.mv(repoDirectory.getAbsolutePath(), source, destination);
      assertNull("No response expected", response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: source directory is empty, source=" + 
          source + ", destination=" + destination, e.getMessage());
    }
    
    /**
     * Testing response message and JavaGitException for the case when git-mv, without any option,
     * fails because source directory is not empty and destination is same as source.
     * 
     * Set the source to a valid directory which is non-empty. Set the destination to the same 
     * name.
     */
    source = "sub_dir2";
    destination = "sub_dir2";
    try {
      GitMvResponse response = gitMv.mv(repoDirectory.getAbsolutePath(), source, destination);
      assertNull("No response expected", response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: can not move directory into itself, " +
          "source=" + source +", destination=" + destination+"/"+source, e.getMessage());
    }
    /**
     * Testing response message and JavaGitException for the case when git-mv, without any option,
     * fails because of the following.
     * 
     * Source is set to a valid directory. Destination is set to a directory which has source 
     * directory inside it i.e. source directory is also present inside the destination directory.
     * This gives error message as "cannot move directory over file". 
     * 
     * In this case sub_dir2 and sub_dir3 are present in the given repository plus sub_dir3 is also
     * present inside sub_dir2.
     */
    source = "sub_dir3";
    destination = "sub_dir2";
    try {
      GitMvResponse response = gitMv.mv(repoDirectory.getAbsolutePath(), source, destination);
      assertNull("No response expected", response);
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424000")+"fatal: cannot move directory over file, " +
          "source=" + source +", destination=" + destination+"/" + source, e.getMessage());
    }
    
    /**
     * Testing response message and JavaGitException for the case when git-mv, with -n(dry-run) 
     * option, succeeds with source and destination objects.
     * 
     * The source and destination defined below should be valid i.e. source should exist and
     * destination shouldn't exist in given directory(repository path).
     */
    source = "test.pl";
    destination = "test1.pl";
    options.setOptK(false);
    options.setOptF(false);
    options.setOptN(true);
    try {
      GitMvResponse response = gitMv.mv(repoDirectory.getAbsolutePath(), options, source, 
          destination);
      assertEquals("Source object is expected", source, response.getSource());
      assertEquals("Destination object is expected", destination, response.getDestination());
    } catch (Exception e) {
      assertNull("Should not get an Exception", e.getMessage());
    }
  }
}
