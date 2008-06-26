package edu.nyu.cs.javagit.api;

import java.util.List;

/**
 * The <code>WorkingTree</code> represents the working copy of the files in the current 
 * branch
 * 
 */
public class WorkingTree {

  private String path;
  private GitDirectory rootDir;
  
  /**
   * The constructor.
   * 
   * @param path
   *            The path to the working directory represented by the instance being
   *            created.
   *            
   * @param repo
   *            The repository
   */
  public WorkingTree(String path) {
    this.path = path;
    rootDir = new GitDirectory(path, null);
  }

  
  /**
   * Adds all known and modified files in the working directory to the index.
   */
  public void add() {
    //GitAdd.add(path);
  }

  
  /**
   * Commits the objects specified in the index to the repository.
   * @param comment
   *            Developer's comment about the change
   */
  public void commit(String comment) {
    //GitCommit.commit(path, comment);
  }

  
  /**
   * Commits all known and modified objects and all new objects already added
   * to the index to the repository.
   * 
   * @param comment
   *            Developer's comment about the change
   */
  public void commitAll(String comment) {
    add();
    commit(comment);
  }

   /**
   *  Show commit logs
   * 
   * @return List of commits for the working directory
   */
  public List<Commit> log() {
    //GitLog.log(path);
    return null;
  }

   /**
   * Reverts the specified git commit
   * 
   * @param commit
   *            Git commit that user wishes to revert
   */
  public void revert(Commit commit) {
    //GitRevert.revert(commit.getSHA1());
  }

  /**
   *  Checks out some earlier version of the repository
   *  
   * 
   * @param sha1
   *            Git commit id
   */
  public void checkout(String sha1) {
     //GitCheckout.checkout(path, sha1);
  }
  
  
  /**
   * Gets the directory at the root of the working directory.
   * 
   * @return The root directory of the working directory.
   */
  public GitDirectory getRootDir() {
    return rootDir;
  }


  /**
   * Gets the filesystem tree; equivalent to git-status
   * 
   * @return The list of objects at the root directory
   */
  public List<IGitTreeObject> getTree() {
    return rootDir.getChildren();
  }
  

  /**
   * Gets the currently checked-out branch of the working directory.
   * 
   * @return The currently checked-out branch of the working directory.
   */
  public Branch getCurrentBranch() {
    //GitBranch
    return null;
  }

  
  /**
   * Switches to a new branch
   * 
   * @param branch
   *            Git branch to switch to
   */
  public void setBranch(Branch branch) {
    //GitCheckout.checkout(branch.getBranchName());
    this.path = branch.getBranchRoot().getPath();
  }

  
  /**
   * Gets the path to the working directory represented by an instance.
   * 
   * @return The path to the working directory represented by an instance.
   */
  public String getPath() {
    return path;
  }

  
  /**
   * Adds a directory to the working directory (but not to the repository!)
   * 
   * @return The new Directory object
   */
  public GitDirectory addDirectory(String dir) {
    //createDir(dir);
    return new GitDirectory(dir, null);
  }

  
}