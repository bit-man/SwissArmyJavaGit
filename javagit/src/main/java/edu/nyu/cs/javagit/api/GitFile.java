package edu.nyu.cs.javagit.api;

import java.io.File;
/**
 * <code>GitFile</code> a file object in a git working tree.
 * 
 * TODO: Build out the class
 */
public class GitFile extends GitFileSystemObject {
  // parent directory
  private GitDirectory parent = null;

  /**
   * The constructor.
   * 
   * @param file
   *          underlying java.io.File object
   * @param dotGit
   *          The object representing .git directory of the repository
   * @param parent
   *          The parent directory
   */
  public GitFile(File file, DotGit dotGit, GitDirectory parent) {
    super(file, dotGit);
    this.parent = parent;
  }

  /**
   * Gets parent directory of this File object
   * 
   * @return parent directory
   */
  public GitDirectory getParent() {
    return parent;
  }

}
