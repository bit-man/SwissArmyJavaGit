package edu.nyu.cs.javagit.api;

import edu.nyu.cs.javagit.api.commands.*;
import edu.nyu.cs.javagit.client.GitCommitResponseImpl;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * <code>GitFileSystemObject</code> provides some implementation shared by files and directories
 * 
 * TODO: Build out the class
 */
public abstract class GitFileSystemObject implements IGitTreeObject {

  protected String path;
  protected String repositoryPath;
  protected String name;
  protected IGitTreeObject.Status status;

  /**
   * The constructor.
   * 
   * @param path
   *          The full (relative to repository) path
   */
  public GitFileSystemObject(String path, String repositoryPath, IGitTreeObject.Status status) {
    this.path = path;
    this.status = status;
  }

  /**
   * Gets the name of the file system object
   * 
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the path
   * 
   * @return The path
   */
  public String getPath() {
    return path;
  }

  /**
   * Adds the object to the git index
   * 
   * @return response from git add
   */
  public GitAddResponse add() throws IOException, JavaGitException {
    GitAdd gitAdd = new GitAdd();

    // create a list of filenames and add yourself to it
    List<String> list = new Vector<String>();
    list.add(path);

    // run git-add command
    return gitAdd.add(repositoryPath, null, list);
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
    add();

    GitCommit gitCommit = new GitCommit();
    return gitCommit.commit(this.path, comment);
  }

  /**
   * Moves or renames the object
   * 
   * @param dest
   *          destination
   * 
   * @return response from git mv
   */
  public GitMvResponse mv(String dest) throws IOException, JavaGitException {
    String source = path;
    path = dest;

    GitMv gitMv = new GitMv();
    return gitMv.mv(repositoryPath, source, dest);
  }

  /**
   * Removes the file system object from the working tree and the index
   * 
   * @return response from git rm
   */
  public GitRmResponse rm() throws IOException {
    GitRm gitRm = new GitRm();

    // create a list of filenames and add yourself to it
    List<String> list = new Vector<String>();
    list.add(path);

    // run git rm command
    return gitRm.rm(repositoryPath, list);
  }

  /**
   * Checks out some earlier version of the object
   * 
   * @param sha1
   *          Commit id
   */
  public void checkout(String sha1) {
    System.out.println("getting earlier version " + sha1);
    // GitCheckout.checkout(path, sha1);
  }

  /**
   * Show differences between current file system object and index version of it
   * 
   * @return diff between working directory and git index
   */
  public Diff diff() {
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
  public Diff diff(Commit commit) {
    // GitLog.log(path);
    return null;
  }

  /**
   * Show commit logs
   * 
   * @return List of commits for the object
   */
  public List<Commit> log() {
    // GitLog.log(path);
    return null;
  }

  /**
   * Show object's status in the working directory
   * 
   * @return status (untracked, changed but not updated, etc
   */
  public IGitTreeObject.Status status() {
    return status;
  }

}
