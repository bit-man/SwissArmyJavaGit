package edu.nyu.cs.javagit.client;

import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.CommitName;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitResetOptions;
import edu.nyu.cs.javagit.api.commands.GitResetResponse;

/**
 * An interface to represent the git-reset command.
 */
public interface IGitReset {

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
  public GitResetResponse gitReset(String repositoryPath) throws IOException, JavaGitException;

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
  public GitResetResponse gitReset(String repositoryPath, GitResetOptions options)
      throws IOException, JavaGitException;

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
  public GitResetResponse gitReset(String repositoryPath, CommitName commitName, List<String> paths)
      throws IOException, JavaGitException;

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
  public GitResetResponse gitReset(String repositoryPath, List<String> paths) throws IOException,
      JavaGitException;

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
  public GitResetResponse gitResetHard(String repositoryPath, CommitName commitName)
      throws IOException, JavaGitException;

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
  public GitResetResponse gitResetSoft(String repositoryPath, CommitName commitName)
      throws IOException, JavaGitException;

}
