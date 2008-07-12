package edu.nyu.cs.javagit.api.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitBranch;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

public class GitBranch {
  public GitBranchResponse branch(File repositoryPath, GitBranchOptions options) 
      throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repositoryPath, "repository path");
  
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitBranch gitBranch = client.getGitBranchInstance();
    return gitBranch.branch(repositoryPath, options);
  }
  public GitBranchResponse branch(File repositoryPath, GitBranchOptions options, 
      List<Ref> branchList) throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repositoryPath, "repository path");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitBranch gitBranch = client.getGitBranchInstance();
    return gitBranch.branch(repositoryPath, options);
  }
  public GitBranchResponse branch(File repositoryPath, GitBranchOptions options, Ref arg1, 
      Ref arg2) throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repositoryPath, "repository path");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitBranch gitBranch = client.getGitBranchInstance();
    return gitBranch.branch(repositoryPath, options);
  }
}
