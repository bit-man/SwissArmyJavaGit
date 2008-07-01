package edu.nyu.cs.javagit.api;

import java.util.List;

/**
 * <code>Commit</code> represents information about a commit to a git repository
 * 
 * TODO: Build out the class
 */
public class Commit {
  // commit "id"
  private String sha1;
  // commit comment
  private String comment;

  /**
   * The constructor.
   * 
   * @param sha1
   *          The full SHA1 object name (commit id).
   */
  public Commit(String sha1) {
    this.sha1 = sha1;
  }

  /**
   * Gets the full SHA1 object name
   * 
   * @return The full SHA1 object name.
   */
  public String getSha1() {
    return sha1;
  }

  /**
   * Gets the author's comment
   * 
   * @return The author's comment.
   */
  public String getComment() {
    return comment;
  }

  /**
   * Returns differences for this commit
   * 
   * 
   * @return The list of differences (one per each git object).
   */
  public List<Diff> diff() {
    // GitDiff.diff();
    return null;
  }

  /**
   * Diffs this commit with another commit
   * 
   * @param otherCommit
   *          The commit to compare current commit to
   * 
   * @return The list of differences (one per each git object).
   */
  public List<Diff> diff(Commit otherCommit) {
    // GitDiff.diff();
    return null;
  }
}