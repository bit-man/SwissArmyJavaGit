package edu.nyu.cs.javagit.api;

/**
 * <code>Branch</code> represents a branch in a git repository.
 * 
 * TODO: Build out the class
 */
public class Branch {
  private String branchName;

  /**
   * The constructor.
   * 
   * @param name
   *          The name of the branch
   */
  public Branch(String name) {
    this.branchName = name;
  }

  /**
   * Gets the name of the branch.
   * 
   * @return The name of the branch.
   */
  public String getBranchName() {
    return branchName;
  }

  /**
   * Gets the directory at the root of this branch.
   * 
   * @return The directory at the root of the branch.
   */
  public GitDirectory getBranchRoot() {
    return null;
  }

  /**
   * Moves the branch
   * 
   * @param newName
   *          The name of the new branch
   */
  public void mv(String newName) {
    // GitBranch.branch(-m);
  }
}
