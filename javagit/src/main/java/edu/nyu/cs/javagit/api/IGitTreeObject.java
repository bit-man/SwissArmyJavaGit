package edu.nyu.cs.javagit.api;

/**
 * <code>IGitTreeNode</code> is the base interface for the hierarchy of
 * objects in a git sandbox and repository tree.
 * 
 * TODO: Build out the class
 */
public interface IGitTreeObject {

  /**
   * Gets the branch this object is on.
   * 
   * @return The branch this object is on.
   */
  public Branch getBranch();

  /**
   * The git SHA1 hash code of this object.
   * 
   * @return The git SHA1 hash code of this object.
   */
  public String getGitHash();

  public void add();

  public void commit();

  /**
   * Gets the parent object of this object.
   * 
   * @return The parent object of this object.
   */
  public IGitTreeObject getParent();

}
