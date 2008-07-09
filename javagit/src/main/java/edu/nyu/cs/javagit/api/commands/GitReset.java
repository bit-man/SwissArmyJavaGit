package edu.nyu.cs.javagit.api.commands;

import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitReset;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitReset</code> provides an API to perform git-reset functionality against a git
 * repository: reset the current HEAD to the specified state.
 */
public class GitReset {

  /**
   * Perform a reset on the repository. The results of this method are what one would get by running
   * &quote;git-reset&quote; against the repository.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @return The results of the reset.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>a directory doesn't exist</li>
   *              <li>access to a file is denied</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @exception JavaGitException
   *              Thrown when there is an error making the commit.
   */
  public static GitResetResponse gitReset(String repositoryPath) throws IOException,
      JavaGitException {

    /*
     * TODO (jhl388): Change this method to take the JavaGitConfig object, or whatever it ends up
     * being called, once Ross and Max create it.
     */

    CheckUtilities.checkStringArgument(repositoryPath, "repositoryPath");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitReset(repositoryPath);
  }

  /**
   * Perform a reset on the repository.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param options
   *          The options for a git-reset command. If the value is null, a
   *          <code>NullPointerException</code> will be thrown.
   * @return The results of the reset.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>a directory doesn't exist</li>
   *              <li>access to a file is denied</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @exception JavaGitException
   *              Thrown when there is an error making the commit.
   */
  public static GitResetResponse gitReset(String repositoryPath, GitResetOptions options)
      throws IOException, JavaGitException {

    /*
     * TODO (jhl388): Change this method to take the JavaGitConfig object, or whatever it ends up
     * being called, once Ross and Max create it.
     */

    CheckUtilities.checkStringArgument(repositoryPath, "repositoryPath");
    CheckUtilities.checkNullArgument(options, "options");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitReset(repositoryPath, options);
  }

  /**
   * Perform a reset on the repository.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param commitName
   *          The name of the commit to reset to. If the value is null, a
   *          <code>NullPointerException</code> will be thrown.
   * @param paths
   *          A list paths to folders or files to reset. A non-null and non-empty list is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @return The results of the reset.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>a directory doesn't exist</li>
   *              <li>access to a file is denied</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @exception JavaGitException
   *              Thrown when there is an error making the commit.
   */
  public GitResetResponse gitReset(String repositoryPath, Ref commitName, List<String> paths)
      throws IOException, JavaGitException {

    /*
     * TODO (jhl388): Change this method to take the JavaGitConfig object, or whatever it ends up
     * being called, once Ross and Max create it.
     */

    CheckUtilities.checkStringArgument(repositoryPath, "repositoryPath");
    CheckUtilities.checkNullArgument(commitName, "options");
    CheckUtilities.checkStringListArgument(paths, "paths");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitReset(repositoryPath, commitName, paths);
  }

  /**
   * Perform a reset on the repository.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param paths
   *          A list paths to folders or files to reset. A non-null and non-empty list is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @return The results of the reset.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>a directory doesn't exist</li>
   *              <li>access to a file is denied</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @exception JavaGitException
   *              Thrown when there is an error making the commit.
   */
  public static GitResetResponse gitReset(String repositoryPath, List<String> paths)
      throws IOException, JavaGitException {

    /*
     * TODO (jhl388): Change this method to take the JavaGitConfig object, or whatever it ends up
     * being called, once Ross and Max create it.
     */

    CheckUtilities.checkStringArgument(repositoryPath, "repositoryPath");
    CheckUtilities.checkStringListArgument(paths, "paths");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitReset(repositoryPath, paths);
  }

  /**
   * Perform a hard reset on the repository to the specified <code>CommitName</code>.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param commitName
   *          The name of the commit to reset to. If the value is null, a
   *          <code>NullPointerException</code> will be thrown.
   * @return The results of the reset.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>a directory doesn't exist</li>
   *              <li>access to a file is denied</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @exception JavaGitException
   *              Thrown when there is an error making the commit.
   */
  public static GitResetResponse gitResetHard(String repositoryPath, Ref commitName)
      throws IOException, JavaGitException {

    /*
     * TODO (jhl388): Change this method to take the JavaGitConfig object, or whatever it ends up
     * being called, once Ross and Max create it.
     */

    CheckUtilities.checkStringArgument(repositoryPath, "repositoryPath");
    CheckUtilities.checkNullArgument(commitName, "commitName");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitResetHard(repositoryPath, commitName);
  }

  /**
   * Perform a soft reset on the repository to the specified <code>CommitName</code>.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param commitName
   *          The name of the commit to reset to. If the value is null, a
   *          <code>NullPointerException</code> will be thrown.
   * @return The results of the reset.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>a directory doesn't exist</li>
   *              <li>access to a file is denied</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @exception JavaGitException
   *              Thrown when there is an error making the commit.
   */
  public static GitResetResponse gitResetSoft(String repositoryPath, Ref commitName)
      throws IOException, JavaGitException {

    /*
     * TODO (jhl388): Change this method to take the JavaGitConfig object, or whatever it ends up
     * being called, once Ross and Max create it.
     */

    CheckUtilities.checkStringArgument(repositoryPath, "repositoryPath");
    CheckUtilities.checkNullArgument(commitName, "commitName");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitResetSoft(repositoryPath, commitName);
  }

}
