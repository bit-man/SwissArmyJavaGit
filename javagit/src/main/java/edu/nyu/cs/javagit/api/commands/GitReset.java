package edu.nyu.cs.javagit.api.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitReset;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitReset</code> provides an API to perform git-reset functionality against a git
 * repository: reset the current HEAD to the specified state.
 */
public class GitReset {

  /*
   * TODO (jhl388): Determine if "repository" is the right name to call the initial File argument to
   * the reset methods. It may be better to call it something like "workingDirectory" or something
   * to indicate that it can be something further down the git repository working tree other than
   * the root directory.
   */

  /**
   * Perform a reset on the repository. The results of this method are what one would get by running
   * &quote;git-reset&quote; against the repository.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to perform reset
   *          against. If null is passed, a <code>NullPointerException</code> will be thrown.
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
  public static GitResetResponse gitReset(File repository) throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repository, "repository");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitReset(repository);
  }

  /**
   * Perform a reset on the repository.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to perform reset
   *          against. If null is passed, a <code>NullPointerException</code> will be thrown.
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
  public static GitResetResponse gitReset(File repository, GitResetOptions options)
      throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repository, "repository");
    CheckUtilities.checkNullArgument(options, "options");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitReset(repository, options);
  }

  /**
   * Perform a reset on the repository.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to perform reset
   *          against. If null is passed, a <code>NullPointerException</code> will be thrown.
   * @param commitName
   *          The name of the commit to reset to. If the value is null, a
   *          <code>NullPointerException</code> will be thrown.
   * @param paths
   *          A list of paths to folders or files to reset. A non-null and non-empty list is
   *          required for this parameter, otherwise a <code>NullPointerException</code> or
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
  public GitResetResponse gitReset(File repository, Ref commitName, List<File> paths)
      throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repository, "repository");
    CheckUtilities.checkNullArgument(commitName, "options");
    CheckUtilities.checkNullListArgument(paths, "paths");
    // TODO (jhl388): Check that commitName is a correct type.

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitReset(repository, commitName, paths);
  }

  /**
   * Perform a reset on the repository.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to perform reset
   *          against. If null is passed, a <code>NullPointerException</code> will be thrown.
   * @param paths
   *          A list of paths to folders or files to reset. A non-null and non-empty list is
   *          required for this parameter, otherwise a <code>NullPointerException</code> or
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
  public static GitResetResponse gitReset(File repository, List<File> paths) throws IOException,
      JavaGitException {
    CheckUtilities.checkNullArgument(repository, "repository");
    CheckUtilities.checkNullListArgument(paths, "paths");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitReset(repository, paths);
  }

  /**
   * Perform a hard reset on the repository to the specified <code>CommitName</code>.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to perform reset
   *          against. If null is passed, a <code>NullPointerException</code> will be thrown.
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
  public static GitResetResponse gitResetHard(File repository, Ref commitName) throws IOException,
      JavaGitException {
    CheckUtilities.checkNullArgument(repository, "repository");
    CheckUtilities.checkNullArgument(commitName, "commitName");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitResetHard(repository, commitName);
  }

  /**
   * Perform a soft reset on the repository to the specified <code>CommitName</code>.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to perform reset
   *          against. If null is passed, a <code>NullPointerException</code> will be thrown.
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
  public static GitResetResponse gitResetSoft(File repository, Ref commitName) throws IOException,
      JavaGitException {
    CheckUtilities.checkNullArgument(repository, "repository");
    CheckUtilities.checkNullArgument(commitName, "commitName");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitReset gitReset = client.getGitResetInstance();
    return gitReset.gitResetSoft(repository, commitName);
  }

}
