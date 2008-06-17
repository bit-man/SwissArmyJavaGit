package edu.nyu.cs.javagit.client.cli;

/**
 * <code>IParser</code> is an interface for parsing a git command line output
 * stream.
 */
public interface IParser {

  /**
   * Parses a line of output from a git command line output stream.
   * 
   * @param line
   *            The line to parse.
   */
  public void parseLine(String line);
}
