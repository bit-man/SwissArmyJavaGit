package edu.nyu.cs.javagit.api;

import java.util.List;
import java.io.File;

/**
 * <code>GitDirectory</code> represents a directory object in a git working tree.
 * 
 * TODO: Build out the class
 */
public class GitDirectory extends GitFileSystemObject {
  // parent directory
  private GitDirectory parent = null;

  /**
   * The constructor.
   * 
   * @param dir
   *          underlying java.io.File object
   * @param dotGit
   *          The object representing .git directory of the repository
   * @param parent
   *          The parent directory
   */
  public GitDirectory(File dir, DotGit dotGit, GitDirectory parent) {
    super(dir, dotGit);
    this.parent = parent;
  }


  /**
   * Gets the children of this directory.
   * 
   * @return The children of this directory.
   */
  public List<GitFileSystemObject> getChildren() {
    return null;
  }

  /**
   * Adds a GitFile to the working directory.
   * 
   * @param name
   *          The name of the file
   * 
   * @return The GitFile object
   */
  public GitFile addFile(String name) {
    String filePath = file.getPath() + File.separator + name;
    return new GitFile(new File(filePath), dotGit, this);
  }

  /**
   * Adds a GitFile to the working directory.
   * 
   * @param name
   *          The name of the file
   * 
   * @return The GitFile object
   */
  public GitDirectory addDirectory(String name) {
    String dirPath = file.getPath() + File.separator + name;
    return new GitDirectory(new File(dirPath), dotGit, this);
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
