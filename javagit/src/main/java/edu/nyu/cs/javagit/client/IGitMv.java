package edu.nyu.cs.javagit.client;

import java.io.IOException;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitMvOptions;
import edu.nyu.cs.javagit.api.commands.GitMvResponse;

/**
 * An interface to represent the git-mv command.
 */
public interface IGitMv {

  public GitMvResponse mv(String repositoryPath, GitMvOptions options, String source, 
      String destination) throws IOException, JavaGitException;
  public GitMvResponse mv(String repositoryPath, String source, String destination)
      throws IOException, JavaGitException;
}
