package edu.nyu.cs.javagit.api;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import edu.nyu.cs.javagit.api.WorkingTree;
import edu.nyu.cs.javagit.api.commands.GitAdd;
import edu.nyu.cs.javagit.api.commands.GitAddResponse;
import edu.nyu.cs.javagit.api.commands.GitCommit;
import edu.nyu.cs.javagit.api.commands.GitCommitResponse;
import edu.nyu.cs.javagit.api.commands.GitMv;
import edu.nyu.cs.javagit.api.commands.GitMvResponse;
import edu.nyu.cs.javagit.api.commands.GitRm;
import edu.nyu.cs.javagit.api.commands.GitRmResponse;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitFileSystemObject</code> provides some implementation shared by files and directories
 */

// TODO (rs2705): Alphabetize the methods in this class
public abstract class GitFileSystemObject {

  public static enum Status {
    // untracked (created but not added to the repository)
    UNTRACKED,
    // new, waiting to commit
    NEW_TO_COMMIT,
    // in repository and deleted locally
    DELETED,
    // deleted, waiting to commit
    DELETED_TO_COMMIT,
    // changed locally, but not updated
    MODIFIED,
    // changed and added to the index
    MODIFIED_TO_COMMIT,
    // in repository
    IN_REPOSITORY
  }

  protected File file;
  protected WorkingTree workingTree;

  /**
   * The constructor.
   * 
   * @param file
   *          underlying <code>java.io.File</code> object
   */
  protected GitFileSystemObject(File file, WorkingTree workingTree) {
    this.file = file;
    this.workingTree = workingTree;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GitFileSystemObject)) {
      return false;
    }

    GitFileSystemObject gitObj = (GitFileSystemObject) obj;
    return CheckUtilities.checkObjectsEqual(gitObj.getFile(), file);
  }

  /**
   * Gets the name of the file system object
   * 
   * @return The name
   */
  public String getName() {
    return file.getName();
  }

  /**
   * Gets the underlying <code>java.io.File</code> object
   * 
   * @return <code>java.io.File</code> object
   */
  public File getFile() {
    return file;
  }

  /**
   * Gets parent directory of this <code>GitFileSystemObject</code> object
   * 
   * @return parent directory
   */
  public GitDirectory getParent() {
    // TODO (rs2705): Check to ensure that this parent isn't above the root of our WorkingTree root.

    // NOTE: file.getParentFile() returns null if there is no parent.
    return new GitDirectory(file.getParentFile(), workingTree);
  }

  /**
   * Adds the object to the git index
   * 
   * @return response from git add
   */
  public GitAddResponse add() throws IOException, JavaGitException {
    GitAdd gitAdd = new GitAdd();

    // create a list of filenames and add yourself to it
    List<File> list = new ArrayList<File>();
    list.add(file);

    // run git-add command
    return gitAdd.add(workingTree.getPath(), null, list);
  }

  /**
   * Commits the file system object
   * 
   * @param comment
   *          Developer's comment
   * 
   * @return response from git commit
   */
  public GitCommitResponse commit(String comment) throws IOException, JavaGitException {
    // first add the file
    add();

    // create a list of filenames and add yourself to it
    List<File> list = new ArrayList<File>();
    list.add(file);

    GitCommit gitCommit = new GitCommit();
    return gitCommit.commitOnly(workingTree.getPath(), comment, list);
  }

  /**
   * Moves or renames the object
   * 
   * @param dest
   *          destination path (relative to the Git Repository)
   * 
   * @return response from git mv
   */
  public GitMvResponse mv(File dest) throws IOException, JavaGitException {
    // source; current location
    File source = file;

    // perform git-mv
    GitMv gitMv = new GitMv();
    GitMvResponse response = gitMv.mv(workingTree.getPath(), source, dest);

    // file has changed; update
    file = dest;

    return response;
  }

  /**
   * Removes the file system object from the working tree and the index
   * 
   * @return response from git rm
   */
  public GitRmResponse rm() throws IOException, JavaGitException {
    GitRm gitRm = new GitRm();

    // run git rm command
    return gitRm.rm(workingTree.getPath(), file);
  }

  /**
   * Checks out some earlier version of the object
   * 
   * @param sha1
   *          Commit id
   */
  public void checkout(String sha1) throws JavaGitException {
    System.out.println("getting earlier version " + sha1);
    // GitCheckout.checkout(path, sha1);
  }

  /**
   * Show differences between current file system object and index version of it
   * 
   * @return diff between working directory and git index
   */
  public Diff diff() throws JavaGitException {
    // GitLog.log(path);
    return null;
  }

  /**
   * Show differences between current file system object and some commit
   * 
   * @param commit
   *          Git commit to compare with
   * 
   * @return diff between working directory and a given git commit
   */
  public Diff diff(Commit commit) throws JavaGitException {
    // GitLog.log(path);
    return null;
  }

  /**
   * Show commit logs
   * 
   * @return List of commits for the object
   */
  public List<Commit> getLog() throws JavaGitException {
    // GitLog.log(path);
    return null;
  }
  
  /**
   * Return the <code>WorkingTree</code> this object is in
   * 
   * @return working tree
   */
  public WorkingTree getWorkingTree() {
    return workingTree;
  }
}
