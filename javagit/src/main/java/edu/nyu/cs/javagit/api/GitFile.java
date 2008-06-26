package edu.nyu.cs.javagit.api;

import java.io.File;

/**
 * <code>GitFile</code> a file object in a git working tree.
 * 
 * TODO: Build out the class
 */
public class GitFile extends GitFileSystemObject {
  //parent directory
  private GitDirectory parent = null;
  private File file = null;

  /**
   * The constructor.
   * 
   * @param file
   *            The underlying java.io.File object
   * @param parent
   *            The parent directory
   */
  public GitFile(File file, GitDirectory parent) {
    super(file.getPath(), IGitTreeObject.Status.UNTRACKED);
    this.parent = parent;
    this.file = file;
  }


   /**
   * The constructor.
   * 
   * @param file
   *            The underlying java.io.File object
   * @param parent
   *            The parent directory
   * @param status
   *            git status
   */
  public GitFile(File file, GitDirectory parent, IGitTreeObject.Status status) {
    super(file.getPath(), status);
    this.parent = parent;
  }


  /**
   * Gets the type of the git object (FILE)
   */
  public IGitTreeObject.Type getType() {
    return IGitTreeObject.Type.FILE;
  }


  /**
   *  Gets parent directory of this File object
   * 
   * @return parent directory
   */
  public GitDirectory getParent() {
    return parent;
  }

  /**
   *  Gets the underlying java.io.File object
   * 
   * @return java.io.File object
   */
  public File getFile() {
    return file;
  }
}
