package edu.nyu.cs.javagit.api;

/**
 * <code>Diff</code> represents a diff for one object in git repository
 * 
 * TODO: Build out the class
 */
public class Diff {
  private String name;

  /**
   * The constructor.
   * 
   * @param name
   *          The name of the git object Diff refers to
   */
  public Diff(String name) {
    this.name = name;
  }

  /**
   * Gets the name of the git object Diff refers to
   * 
   * @return The name of the git object Diff refers to
   */
  public String getName() {
    return name;
  }
}