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
   * @param name
   *          The name of the directory
   * @param parent
   *          The parent directory
   * @param repositoryPath
   *          The path to the repository
   */
  public GitDirectory(String path, GitDirectory parent, String repositoryPath) {
    super(path, repositoryPath, IGitTreeObject.Status.UNTRACKED);
    this.parent = parent;
  }

  /**
   * The constructor.
   * 
   * @param name
   *          The name of the directory
   * @param parent
   *          The parent directory
   * @param repositoryPath
   *          The path to the repository
   * @param status
   *          current status of the directory
   */
  public GitDirectory(String path, GitDirectory parent, String repositoryPath,
      IGitTreeObject.Status status) {
    super(path, repositoryPath, status);
    this.parent = parent;
  }

  /**
   * Gets the children of this directory.
   * 
   * @return The children of this directory.
   */
  public List<IGitTreeObject> getChildren() {
    return null;
  }

  /**
   * Adds a GitFile to the working directory.
   * 
   * @param file
   *          The underlying java,io.File object
   * 
   * @return The GitFile object
   */
  public GitFile addFile(File file) {
    return new GitFile(file, this, repositoryPath);
  }

  /**
   * Gets the type of the git object (DIRECTORY)
   */
  public IGitTreeObject.Type getType() {
    return IGitTreeObject.Type.DIRECTORY;
  }

  /**
   * Gets parent directory of this File object
   * 
   * @return parent directory
   */
  public IGitTreeObject getParent() {
    return parent;
  }

}
