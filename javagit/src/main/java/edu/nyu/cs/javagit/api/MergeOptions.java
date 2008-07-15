package edu.nyu.cs.javagit.api;

/**
 * <code>MergeOpions</code> represents merge options
 * 
 */
public class MergeOptions {
  private final String message;
  private Strategy strategy;

  public static enum Strategy {
    RESOLVE, RECURSIVE, OCTOPUS, OURS, SUBTREE
  }

  /**
   * The constructor.
   * 
   * @param m
   *          Message for the commit
   * @param s
   *          Merge strategy; see enum list
   */
  public MergeOptions(Strategy s) {
    this.strategy = s;
    //no message
    this.message = null;
  }

  /**
   * The constructor.
   * 
   * @param m
   *          Message for the commit
   * @param s
   *          Merge strategy; see enum list
   */
  public MergeOptions(Strategy s, String m) {
    this.strategy = s;
    this.message = m;
  }
  
  /**
   * Gets the commit message
   * 
   * @return The commit message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets the merge strategy
   * 
   * @return The strategy for the merge (resolve, recursive, etc)
   */
  public Strategy getStrategy() {
    return strategy;
  }
}