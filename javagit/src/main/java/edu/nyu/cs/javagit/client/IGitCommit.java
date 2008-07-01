package edu.nyu.cs.javagit.client;

import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitCommitOptions;

/**
 * An interface to represent the git-commit command.
 */
public interface IGitCommit {

  /**
   * Automatically stage all tracked files that have been changed and then commit all files staged
   * in the git repository's index.
   * 
   * @param repoPath
   *          The path to the repository. A non-zero length argument is required for this parameter,
   *          otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param message
   *          The message to attach to the commit. A non-zero length argument is required for this
   *          parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
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
  public GitCommitResponseImpl commitAll(String repoPath, String message) throws IOException,
      JavaGitException;

  /**
   * Commits staged changes into the specified repository. The specific files that are committed
   * depends on the options specified.
   * 
   * @param repoPath
   *          The path to the repository. A non-zero length argument is required for this parameter,
   *          otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param options
   *          The options to commit with.
   * @param message
   *          The message to attach to the commit. A non-zero length argument is required for this
   *          parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
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
  public GitCommitResponseImpl commit(String repoPath, GitCommitOptions options, String message)
      throws IOException, JavaGitException;

  /**
   * Commits staged changes into the specified repository. The specific files that are committed
   * depends on the options and paths specified.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param options
   *          The options to commit with.
   * @param message
   *          The message to attach to the commit. A non-zero length argument is required for this
   *          parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param paths
   *          A list paths to folders or files to commit. A non-null and non-empty list is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @return The results from the commit.
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
  public GitCommitResponseImpl commit(String repositoryPath, GitCommitOptions options, String message,
      List<String> paths) throws IOException, JavaGitException;

  /**
   * Commit everything that is staged in the git repository's index.
   * 
   * @param repoPath
   *          The path to the repository. A non-zero length argument is required for this parameter,
   *          otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param message
   *          The message to attach to the commit. A non-zero length argument is required for this
   *          parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
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
  public GitCommitResponseImpl commit(String repoPath, String message) throws IOException,
      JavaGitException;

  /**
   * Commits only the changes in the specified paths into the specified repository. Any staged
   * changes (changes already defined in the index) are ignored.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param message
   *          The message to attach to the commit. A non-zero length argument is required for this
   *          parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param paths
   *          A list paths to folders or files to commit. A non-null and non-empty list is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @return The results from the commit.
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
  public GitCommitResponseImpl commitOnly(String repositoryPath, String message, List<String> paths)
      throws IOException, JavaGitException;

}
