package edu.nyu.cs.javagit.api;

import java.util.List;

/**
 * <code>GitFileSystemObject</code> provides some implementation shared by
 * files and directories
 * 
 * TODO: Build out the class
 */
public abstract class GitFileSystemObject implements IGitTreeObject {

  protected String path;
  protected String name;
  protected IGitTreeObject.Status status;

   /**
   * The constructor.
   * 
   * @param path
   *            The full (relative to repository) path
   */
  public GitFileSystemObject(String path, IGitTreeObject.Status status) {
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
   */
  public void add() {
    System.out.println("Adding " + path);
    //GitAdd.add(path);
  }

  /**
   * Commits the file system object
   * 
   * @param comment
   *      Developer's comment
   */
  public void commit(String comment) {
    add();
    System.out.println("Commiting " + path);
    //GitCommit.commit(this.path, comment);
  }

  /**
   * Moves or renames the object
   * 
   * @param dest
   *      destination
   */
  public void mv(String dest) {
    System.out.println(path + " -> " + dest);
    path = dest;
    //GitMv.mv(path, dest);
  }

  /**
   * Removes the file system object from the working tree and the index
   */ 
  public void rm() {
    System.out.println("Removing " + path);
    //GitRm.rm(path);
  }

  /**
   *  Checks out some earlier version of the object
   *  
   * @param sha1
   *      Commit id
   */
  public void checkout(String sha1) {
    System.out.println("getting earlier version " + sha1);
     //GitCheckout.checkout(path, sha1);
  }

   /**
   *  Show differences between current file system object and index version of it
   * 
   * @return diff between working directory and git index
   */
  public Diff diff() {
    //GitLog.log(path);
    return null;
  }

  /**
   *  Show differences between current file system object and some commit
   * 
   * @param commit
   *      Git commit to compare with
   *      
   * @return diff between working directory and a given git commit
   */
  public Diff diff(Commit commit) {
    //GitLog.log(path);
    return null;
  }

  /**
   *  Show commit logs
   * 
   * @return List of commits for the object
   */
  public List<Commit> log() {
    //GitLog.log(path);
    return null;
  }

   /**
   *  Show object's status in the working directory
   * 
   * @return status (untracked, changed but not updated, etc 
   */
  public IGitTreeObject.Status status() {
    return status;
  }

}
