package edu.nyu.cs.javagit.api.commands;

import java.io.IOException;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitStatus;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

public class GitStatus {
  
  public GitStatusResponse status(String repositoryPath, GitStatusOptions options, String paths) 
    throws JavaGitException, IOException{
    CheckUtilities.checkStringArgument(repositoryPath, "repositoryPath");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitStatus gitStatus = client.getGitStatusInstance();
    return gitStatus.status(repositoryPath, options, paths);
  }
 
}
