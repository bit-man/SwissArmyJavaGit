package edu.nyu.cs.javagit.client;

import edu.nyu.cs.javagit.api.commands.GitAddResponse;
import java.io.IOException;

/**
 * An interface to represent the git-add command.
 */
public interface IGitAdd {

  /**
   * 
   * @param repositoryPath
   * @param args
   * @return
   * @throws IOException can be thrown because
   *   - The path to filename does not exist
   *  - or the file does not exist
   *  - repository path does not exist
   */
  public GitAddResponse add( String directory, String[] args ) throws IOException;

}
