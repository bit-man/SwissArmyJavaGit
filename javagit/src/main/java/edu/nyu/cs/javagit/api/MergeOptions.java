package edu.nyu.cs.javagit.api;

/**
 * <code>MergeOpions</code> represents merge options
 * 
 * TODO: Build out the class
 */
public class MergeOptions {
  private String message;
  private Strategy strategy;

  public static enum Strategy {
    RESOLVE, RECURSIVE, OCTOPUS, OURS, SUBTREE
  }

  /**
   * The constructor.
   * 
   * @param m
   *            Message for the commit
   * @param s
   *            Merge strategy; see enum list
   */
  public MergeOptions(String m, Strategy s) {
    this.message = m;
    this.strategy = s;
  }

  /**
   *  Gets the commit message
   * 
   * @return The commit message
   */
  public String getMessage() {
    return message;
  }

  /**
   *  Gets the merge strategy
   * 
   * @return The strategy for the merge (resolve, recursive, etc)
   */
  public Strategy getStrategy() {
    return strategy;
  }
}