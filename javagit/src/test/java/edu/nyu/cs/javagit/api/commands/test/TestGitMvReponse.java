package edu.nyu.cs.javagit.api.commands.test;

import junit.framework.TestCase;

import org.junit.Before;

import edu.nyu.cs.javagit.api.commands.GitMv;
import edu.nyu.cs.javagit.api.commands.GitMvOptions;
import edu.nyu.cs.javagit.api.commands.GitMvResponse;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

public class TestGitMvReponse extends TestCase {
  @Before
  protected void setUp() {
  } 
  
  /**
   * Before starting this test make sure that repository path is the path to a working directory
   * where the files and sub-directories are located.
   * 
   * Also, make sure that 
   * files 'test.pl','t1.pl, 't2.pl', 't4.pl', 't9.pl', are present 
   * 't3.pl', 't5.pl','t111.pl' are not present and, 
   * 't6.pl' is present but not under version control.
   * 
   * Also, folder 'sub_dir' should be present and empty.
   * folder temp_dir shouldn't exist.
   * 
   * folder 'sub_dir2' and 'sub_dir3' should be non-empty. 'sub_dir3' should have 'sub_dir2'
   * inside it.
   */
  public void testGitMvInvalidResponse() {
    //Should be a valid git repository path.
    String repoPath = "/home/nutan/git_repository";
    
    //Should be a valid source file.
    String source = "t11.pl"; 
    
    //Should be a valid destination file.
    String destination = "t12.pl";
    
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
      GitMvResponse response = gitMv.mv(repoPath, source, destination);
      assertEquals("Response for success expected", true, response.IsSuccess());
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
      GitMvResponse response = gitMv.mv(repoPath, options, source, destination);
      assertEquals("Response for failure expected", true, response.IsSuccess());
      assertEquals("a message saying destination exists is expected", 
          "destination exists; will overwrite!\n",response.getComment());
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
      GitMvResponse response = gitMv.mv(repoPath, source, destination);
      assertEquals("Response for failure expected", false, response.IsSuccess());
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424001")+"bad source, source=" + source +", destination=" 
          + destination, e.getMessage());
    }
    /**
     * Testing response message and JavaGitException for the case when git-mv is run with 
     * -n option, and source is invalid i.e. doesn't exist. It is case of success because
     * it does what it is expected to do i.e. tells what would happen if git-mv is run 
     * without -n option.
     * succeeds with a message 'bad source'.
     * 
     * Source file doesn't exist.
     */
    source = "t111.pl";
    options.setOptK(false);
    options.setOptF(false);
    options.setOptN(true);
    try {
      GitMvResponse response = gitMv.mv(repoPath, options, source, destination);
      assertEquals("Response for success expected", true, response.IsSuccess());
      assertEquals("A message saying 'checking rename of' expected", "bad source, source="+ 
          source+", destination=" +destination+"\nChecking rename of '"+source+"' to '"+destination 
          +"'\n", response.getComment());
    } catch (Exception e) {
      assertNull("Should not get an Exception", e.getMessage());
    }
    
    /**
     * Testing response message and JavaGitException for the case when git-mv is run with 
     * -k (quiet) option, and source is invalid i.e. doesn't exist. It is a case of failure 
     * but it doesn't give any message(that is what is expected hence, success).
     * 
     * Source file doesn't exist.
     */
    source = "t111.pl";
    options.setOptN(false);
    options.setOptF(false);
    options.setOptK(true);
    try {
      GitMvResponse response = gitMv.mv(repoPath, options, source, destination);
      assertEquals("Response for success expected", true, response.IsSuccess());
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
      GitMvResponse response = gitMv.mv(repoPath, source, destination);
      assertEquals("Response for failure expected", false, response.IsSuccess());
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424001")+"destination exists, source=" + source +", " +
          		"destination=" + destination, e.getMessage());
    }
    
    /**
     * Testing response message and JavaGitException for the case when git-mv, without any option,
     * fails because the source file is not under version control i.e. the source file is not 
     * added (by git-add) to the repository yet.
     * 
     * Set the source to a file which exists but not added to the repository. Set the destination 
     * to a file which is not present in the repository.
     */
    source = "t6.pl";
    destination = "t5.pl";
    try {
      GitMvResponse response = gitMv.mv(repoPath, source, destination);
      assertEquals("Response for failure expected", false, response.IsSuccess());
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424001")+"not under version control, source=" + source +
          ", destination=" + destination, e.getMessage());
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
      GitMvResponse response = gitMv.mv(repoPath, source, destination);
      assertEquals("Response for failure expected", false, response.IsSuccess());
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424001")+"source directory is empty, source=" + source +
          ", destination=" + destination, e.getMessage());
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
      GitMvResponse response = gitMv.mv(repoPath, source, destination);
      assertEquals("Response for failure expected", false, response.IsSuccess());
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424001")+"can not move directory into itself, source=" + 
          source +", destination=" + destination+"/"+source, e.getMessage());
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
      GitMvResponse response = gitMv.mv(repoPath, source, destination);
      assertEquals("Response for failure expected", false, response.IsSuccess());
    } catch (Exception e) {
      assertEquals("Should throw a JavaGitException related to git-mv with following message",
          ExceptionMessageMap.getMessage("424001")+"cannot move directory over file, source=" + 
          source +", destination=" + destination+"/" + source, e.getMessage());
    }
    
    /**
     * Testing response message and JavaGitException for the case when git-mv, with -n(dry-run) 
     * option, succeeds with message 'Checking rename of...'.
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
      GitMvResponse response = gitMv.mv(repoPath, options, source, destination);
      assertEquals("Response for success expected", true, response.IsSuccess());
      assertEquals("a message saying 'checking rename of' expected", 
          "Checking rename of '"+source+"' to '"+destination +"'\nRenaming "+ source
          + " to " + destination + "\nAdding   : "+destination + "\nDeleting : "+ source +
          "\n",response.getComment());
    } catch (Exception e) {
      assertNull("Should not get an Exception", e.getMessage());
    }
  }
}
