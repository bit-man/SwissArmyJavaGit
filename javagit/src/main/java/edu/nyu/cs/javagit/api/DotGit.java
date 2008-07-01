package edu.nyu.cs.javagit.api;

import java.util.List;

/**
 * The <code>DotGit</code> represents the .git directory.
 * 
 * TODO: Build out the class
 */
public class DotGit {
  // where .git directory is located
  private String path;
  private WorkingTree workingTree = null;

  /**
   * The constructor.
   * 
   * @param path
   *          The path to the repository represented by the instance being created.
   */
  public DotGit(String path) {
    this.path = path;
    workingTree = new WorkingTree(path);
  }

  /**
   * Gets the working tree for this git repository
   * 
   * @return The working tree
   */
  public WorkingTree getWorkingTree() {
    return workingTree;
  }

  /**
   * Initializes Git repository
   */
  public void init() {
    // GitInit.init();
  }

  /**
   * Gets a list of the branches in the repository.
   * 
   * @return The branches in the repository.
   */
  public List<Branch> getBranches() {
    // return GitBranch.branch();
    return null;
  }

  /**
   * Creates a new branch
   * 
   * @param name
   *          The name of the branch to create
   * 
   * @return The new branch
   */
  public Branch createBranch(String name) {
    // GitBranch.branch();
    return new Branch(name);
  }

  /**
   * Deletes a branch
   * 
   * @param branch
   *          The branch to delete
   */
  public void deleteBranch(Branch branch) {
    // GitBranch.branch(-d);
    branch = null;
  }

  /**
   * Merges development histories together
   * 
   * @param otherGit
   *          The other .git to merge with
   * @param m
   *          The merge options (strategy, message, etc)
   */
  public void merge(DotGit otherGit, MergeOptions m) {
  }

  /**
   * Merges development histories together
   * 
   * @param gitList
   *          The list of .git to merge with; implies octopus
   * @param m
   *          The merge options (strategy, message, etc)
   */
  public void merge(List<DotGit> gitList, MergeOptions m) {
  }

  /**
   * Gets the repository path represented by this repository object.
   * 
   * @return The path to the repository
   */
  public String getPath() {
    return path;
  }

  /**
   * Clones the repository into new location
   * 
   * @param gitFrom
   *          The .git being cloned
   */
  public void clone(DotGit gitFrom) {
  }

  /**
   * Fetch from git repository and merge with the current one
   * 
   * @param gitFrom
   *          The git repository being fetched from
   */
  public void pull(DotGit gitFrom) {
  }

  /**
   * Updates the remote git repository with the content of the current one
   * 
   * @param gitTo
   *          The .git being updated
   */
  public void push(DotGit gitTo) {
  }

}
