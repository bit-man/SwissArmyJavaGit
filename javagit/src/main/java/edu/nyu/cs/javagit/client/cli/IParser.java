package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.CommandResponse;

/**
 * <code>IParser</code> is an interface for parsing a git command line output stream.
 */
public interface IParser {

  /**
   * Parses a line of output from a git command line output stream.
   * 
   * @param line
   *          The line to parse.
   */
  public void parseLine(String line);

  /**
   * Gets the response for the command for which the parser is implemented.
   * 
   * @return The response for the command for which the parser is implemented.
   * @throws JavaGitException
   *           Thrown when there are problems creating the response, if there was an error running
   *           the command, ....
   */
  public CommandResponse getResponse() throws JavaGitException;

}
