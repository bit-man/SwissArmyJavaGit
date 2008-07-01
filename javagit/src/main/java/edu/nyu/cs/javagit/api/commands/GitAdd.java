package edu.nyu.cs.javagit.api.commands;

import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitAdd;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitAdd</code> provides an interface for adding files or directory tree into git
 * repository
 */
public class GitAdd {
  /**
   * This command adds the current content of new or modified files to the index, thus staging that
   * content for inclusion in the next commit.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param completeCommand
   *          Complete command with options and the list of files whose content need to be added to
   *          the index. files can be file-pattern as well.
   * @return results from the add command.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown if -
   *              <ul>
   *              <li>repositoryPath is not valid</li>
   *              <li>filenames assigned do not exist</li>
   *              <li>files or repository path does not have access rights</li>
   *              </ul>
   */
  public GitAddResponse add(String repositoryPath, String[] completeCommand) throws IOException,
      JavaGitException {

    CheckUtilities.checkStringArgument(repositoryPath, "repository path");
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitAdd gitAdd = client.getGitAddInstance();
    return gitAdd.add(repositoryPath, completeCommand);
  }

  /**
   * This command adds the current content of new or modified files to the index, thus staging that
   * content for inclusion in the next commit.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param options
   *          Options used for running git-add command e.g. -n, or -v or -f or a combination of
   *          them.
   * @param fileNames
   *          Filenames and filePattern that will be added to the index by git-add command
   * @return results from the add command.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown if -
   *              <ul>
   *              <li>repositoryPath is not valid</li>
   *              <li>filenames assigned do not exist</li>
   *              <li>files or repository path does not have access rights</li>
   *              </ul>
   */
  public GitAddResponse add(String repositoryPath, List<String> options, List<String> fileNames)
      throws IOException {
    CheckUtilities.checkStringArgument(repositoryPath, "RepositoryPath");
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitAdd gitAdd = client.getGitAddInstance();
    return gitAdd.add(repositoryPath, options, fileNames);
  }

}
