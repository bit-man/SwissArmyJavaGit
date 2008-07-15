package edu.nyu.cs.javagit.api.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitCheckout;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

public class GitCheckout {

  /**
   * For checking out a branch or creating a new branch. The <new-branch>
   * is the value of the option '-b' passed in <code>GitCheckoutOptions</code>
   * starting at <branch-name> provided as the last argument to the checkout
   * method.
   * @param repositoryPath Path to the Git repository.
   * @param options <code>GitCheckoutOptions</code> passed.
   * @param branch name of the branch that will be checked out OR if creating
   * a new branch then the starting for the new branch will be this branch.
   * @return <code>GitCheckoutResponse</code> object
   */
  public GitCheckoutResponse checkout(File repositoryPath, GitCheckoutOptions options, String branch) 
    throws IOException, JavaGitException {
    CheckUtilities.checkFileValidity(repositoryPath);
    CheckUtilities.checkStringArgument(branch, "branch name");
    CheckUtilities.checkNullArgument(options, "options");
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCheckout gitCheckout = client.getGitCheckoutInstance();
    return gitCheckout.checkout(repositoryPath, options, branch);
  }
  
  /**
   * For checking a file or list of files from a branch.
   * @param repositoryPath path to the Git repository.
   * @param paths List of file paths that are to be checked out.
   * @return <code>GitCheckoutResponse</code> object
   */
  public GitCheckoutResponse checkout(File repositoryPath, List<File> paths) 
    throws IOException, JavaGitException {
    CheckUtilities.checkFileValidity(repositoryPath);
    CheckUtilities.checkNullListArgument(paths, "List of Paths");
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCheckout gitCheckout = client.getGitCheckoutInstance();
    return gitCheckout.checkout(repositoryPath, paths);
  }
}
