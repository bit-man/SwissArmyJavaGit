package edu.nyu.cs.javagit.api.commands;

import java.io.IOException;

import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitCommit;
import edu.nyu.cs.javagit.client.IGitRm;
import edu.nyu.cs.javagit.utilities.CheckUtilities;


/**
 * <code>GitRm</code> provides an interface to remove files from a git
 * repository.
 */
 
public class GitRm {

  /**
   * Removes files from the specified repository.
   * 
   * @param repositoryPath
   *            The path to the repository to commit against. A non-zero
   *            length argument is required for this parameter, otherwise a
   *            <code>NullPointerException</code> or
   *            <code>IllegalArgumentException</code> will be thrown.
   * @return The results from the rm.
   * @exception IOException
   *                There are many reasons for which an
   *                <code>IOException</code> may be thrown. Examples
   *                include:
   *                <ul>
   *                <li>a directory doesn't exist</li>
   *                <li>access to a file is denied</li>
   *                <li>a command is not found on the PATH</li>
   *                </ul>
   */
  public GitRmResponse rm(String repositoryPath, String[] files)
      throws IOException {

    // TODO (jhl388): Change this method to take the JavaGitConfig object,
    // or whatever it ends up being called, once Ross and Max create it.

    CheckUtilities.checkStringArgument(repositoryPath, "repository path");
    CheckUtilities.checkStringArrayArgument(files, "file list");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitRm GitRm = client.getGitRmInstance();
    return GitRm.rm(repositoryPath, files);
  }

}
