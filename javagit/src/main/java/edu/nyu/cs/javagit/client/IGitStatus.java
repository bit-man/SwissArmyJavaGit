package edu.nyu.cs.javagit.client;

import java.io.IOException;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitStatusOptions;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;

/**
 * An interface to represent the git-status command.
 */
public interface IGitStatus {

  public GitStatusResponse status(String repositoryPath, GitStatusOptions options, String branch) 
    throws JavaGitException, IOException;

}
