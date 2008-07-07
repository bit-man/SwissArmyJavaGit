package edu.nyu.cs.javagit.client;

import java.io.IOException;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitBranchOptions;
import edu.nyu.cs.javagit.api.commands.GitBranchResponse;

/**
 * An interface to represent git-branch command.
 *
 */
public interface IGitBranch {
  public GitBranchResponse branch(String repositoryPath, GitBranchOptions options) 
      throws IOException, JavaGitException;
  public GitBranchResponse branch(String repositoryPath, GitBranchOptions options, 
      String branchName) throws IOException, JavaGitException;
  public GitBranchResponse branch(String repositoryPath, GitBranchOptions options, 
      String arg1, String arg2) throws IOException, JavaGitException;
}
