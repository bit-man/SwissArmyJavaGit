package edu.nyu.cs.javagit.api.commands;

import java.io.IOException;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitBranch;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

public class GitBranch {
  public GitBranchResponse branch(String repositoryPath, GitBranchOptions options) 
      throws IOException, JavaGitException {
    CheckUtilities.checkStringArgument(repositoryPath, "repository path");
  
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitBranch gitBranch = client.getGitBranchInstance();
    return gitBranch.branch(repositoryPath, options);
  }
}
