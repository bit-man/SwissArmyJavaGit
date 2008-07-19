package edu.nyu.cs.javagit.api;

// TODO (rs2705): change this to only import the commands we need
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import edu.nyu.cs.javagit.api.commands.GitAdd;
import edu.nyu.cs.javagit.api.commands.GitAddResponse;
import edu.nyu.cs.javagit.api.commands.GitCommit;
import edu.nyu.cs.javagit.api.commands.GitCommitResponse;
import edu.nyu.cs.javagit.api.commands.GitMv;
import edu.nyu.cs.javagit.api.commands.GitMvResponse;
import edu.nyu.cs.javagit.api.commands.GitRm;
import edu.nyu.cs.javagit.api.commands.GitRmResponse;
import edu.nyu.cs.javagit.api.commands.GitStatus;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitFileSystemObject</code> provides some implementation shared by files and directories
 */
public abstract class GitFileSystemObject {

  /*
   * TODO (rs2705): Get rid of the ugly .getPath().getPath() calls in the git command calls here.
   * We're doing this because, as of right now, they take Strings. But we want them to take GitFile
   * (or File) objects instead.
   */

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

  protected DotGit dotGit;
  protected File file;

  /**
   * The constructor.
   * 
   * @param file
   *          underlying <code>java.io.File</code> object
   */
  public GitFileSystemObject(File file) throws JavaGitException {
    this.file = file;

    // search for .git up the directory tree
    boolean found = false;
    File currentParent = file;
    while (currentParent != null) {
      if (DotGit.existsInstance(currentParent)) {
        dotGit = DotGit.getInstance(currentParent);
        found = true;
        break;
      } else {
        currentParent = currentParent.getParentFile();
      }
    }

    // TODO(ma1683): pick proper code
    if (!found) {
      String errorMessage = file.getPath() + " is not part of any git repository";
      throw new JavaGitException(999, errorMessage);
    }
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
    if (file.getParentFile() == null) {
      return null;
    }

    GitDirectory parent;
    try {
      parent = new GitDirectory(file.getParentFile());
    } catch (JavaGitException e) {
      // no valid parent
      return null;
    }

    return parent;
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
    return gitAdd.add(dotGit.getPath(), null, list);
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
    return gitCommit.commitOnly(dotGit.getPath(), comment, list);
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
    GitMvResponse response = gitMv.mv(dotGit.getPath(), source, dest);

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
    return gitRm.rm(dotGit.getPath(), file);
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

}
