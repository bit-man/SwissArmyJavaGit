package edu.nyu.cs.javagit.api;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.IOException;
import edu.nyu.cs.javagit.api.commands.GitAdd;
import edu.nyu.cs.javagit.api.commands.GitAddResponse;
import edu.nyu.cs.javagit.api.commands.GitCommit;
import edu.nyu.cs.javagit.api.commands.GitCommitResponse;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * The <code>WorkingTree</code> represents the working copy of the files in the current branch.
 * 
 */
public final class WorkingTree {
  // This guy's a per-repository singleton, so we need a static place to store our instances.
  private static final Map<String, WorkingTree> INSTANCES = new HashMap<String, WorkingTree>();

  // The directory that contains the .git in question.
  private final File path;

  /*
   * The canonical pathname from this file. Store this here so that we don't need to continually hit
   * the filesystem to resolve it.
   */
  private final String canonicalPath;

  // A git-specific representation of the same place this class is pointing.
  private GitDirectory rootDir;

  /**
   * The constructor. Private because this singleton-ish (per each repository) class is only
   * available via the getInstance method.
   * 
   * @param path
   *          The path to the working directory represented by the instance being created.
   * 
   */
  private WorkingTree(File path, String canonicalPath) {
    // TODO (rs2705): Ensure that these arguments are valid (not null, not empty)

    this.path = path;
    this.canonicalPath = canonicalPath;
    rootDir = new GitDirectory(path, DotGit.getInstance(path), null);
  }

  /**
   * Static factory method for retrieving an instance of this class.
   * 
   * @param path
   *          <code>File</code> object representing the path to the repository.
   * @return The <code>WorkingTree</code> instance for this path
   */
  public static synchronized WorkingTree getInstance(File path) {
    WorkingTree workingTree;

    // TODO (rs2705): make sure that path is valid

    /*
     * We want to make sure we're dealing with the canonical path here, since there are multiple
     * ways to refer to the same dir with different strings.
     */
    String canonicalPath = "";

    try {
      canonicalPath = path.getCanonicalPath();
    } catch (Exception e) {
      /*
       * TODO (rs2705): Figure out which exception to throw here, and throw it - or should we simply
       * let it propogate up as-is?
       */
      return null; // Temporary placeholder
    }

    if (!(INSTANCES.containsKey(canonicalPath))) {
      workingTree = new WorkingTree(path, canonicalPath);
      INSTANCES.put(canonicalPath, workingTree);
    } else {
      workingTree = INSTANCES.get(canonicalPath);
    }

    return workingTree;
  }

  /**
   * Convenience method for retrieving an instance of the class using a <code>String</code>
   * instead of a <code>File</code>.
   * 
   * @param path
   *          <code>String</code> object representing the path to the repository.
   * @return The <code>WorkingTree</code> instance for this path
   */
  public static WorkingTree getInstance(String path) {
    // TODO (rs2705): make sure that path is valid
    return getInstance(new File(path));
  }

  /**
   * Adds all known and modified files in the working directory to the index.
   * 
   * @return response from git add
   */
  public GitAddResponse add() throws IOException, JavaGitException {
    GitAdd gitAdd = new GitAdd();
    String args[] = { "git-add" };
    return gitAdd.add(path.getPath(), args);
  }

  /**
   * Adds a directory to the working directory (but not to the repository!)
   * 
   * @param dir
   *          name of the directory
   * 
   * @return The new <code>GitDirectory</code> object
   */
  public GitDirectory addDirectory(String dir) {
    // createDir(dir);
    // TODO (rs2705): Fix the call below. Currently it does nothing - just need it to compile.
    return new GitDirectory(new File(dir), null, null);
  }

  /**
   * Checks out some earlier version of the repository
   * 
   * @param sha1
   *          Git commit id
   */
  public void checkout(String sha1) {
    // GitCheckout.checkout(path, sha1);
  }

  /**
   * Since instances of this class are singletons, don't allow cloning.
   * 
   * @return None - always throws exception
   */
  @Override
  protected Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }

  /**
   * Commits the objects specified in the index to the repository.
   * 
   * @param comment
   *          Developer's comment about the change
   * 
   * @return response from git commit
   */
  public GitCommitResponse commit(String comment) throws IOException, JavaGitException {
    GitCommit gitCommit = new GitCommit();
    return gitCommit.commit(canonicalPath, comment);
  }

  /**
   * Commits all known and modified objects and all new objects already added to the index to the
   * repository.
   * 
   * @param comment
   *          Developer's comment about the change
   * 
   * @return response from git commit
   */
  public GitCommitResponse commitAll(String comment) throws IOException, JavaGitException {
    GitCommit gitCommit = new GitCommit();
    return gitCommit.commitAll(path.getPath(), comment);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof WorkingTree)) {
      return false;
    }

    WorkingTree workingTree = (WorkingTree) obj;
    return CheckUtilities.checkObjectsEqual(canonicalPath, workingTree.canonicalPath);
  }

  /**
   * Gets the currently checked-out branch of the working directory.
   * 
   * @return The currently checked-out branch of the working directory.
   */
  public Branch getCurrentBranch() {
    // GitBranch
    return null;
  }

  /**
   * Show commit logs
   * 
   * @return List of commits for the working directory
   */
  public List<Commit> getLog() {
    // GitLog.log(path);
    return null;
  }

  /**
   * Gets the .git representation for this git repository
   * 
   * @return The DotGit
   */
  public DotGit getDotGit() {
    return DotGit.getInstance(path);
  }

  /**
   * Gets the path to the working directory represented by an instance.
   * 
   * @return The path to the working directory represented by an instance.
   */
  public File getPath() {
    return path;
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
  public List<GitFileSystemObject> getTree() throws IOException {
    return rootDir.getChildren();
  }

  @Override
  public int hashCode() {
    return canonicalPath.hashCode();
  }

  /**
   * Show commit logs
   * 
   * @return List of commits for the working directory
   */

  public List<Commit> log() {
    // GitLog.log(path);
    return null;
  }

  /**
   * Reverts the specified git commit
   * 
   * @param commit
   *          Git commit that user wishes to revert
   */
  public void revert(Commit commit) {
    // GitRevert.revert(commit.getSHA1());
  }

  /**
   * Switches to a new branch
   * 
   * @param branch
   *          Git branch to switch to
   */
  public void setBranch(Branch branch) {
    // GitCheckout.checkout(branch.getBranchName());
    /*
     * TODO (rs2705): Figure out why this function is setting this.path. When does the WorkingTree
     * path change?
     */
    // this.path = branch.getBranchRoot().getPath();
  }
}