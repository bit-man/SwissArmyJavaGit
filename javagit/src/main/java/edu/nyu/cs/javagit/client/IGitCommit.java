package edu.nyu.cs.javagit.client;

import java.io.File;
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
   * @param repository
   *          A <code>File</code> instance for the path to the repository to commit against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
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
  public GitCommitResponseImpl commitAll(File repository, String message) throws IOException,
      JavaGitException;

  /**
   * Commits staged changes into the specified repository. The specific files that are committed
   * depends on the options specified.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to commit against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
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
  public GitCommitResponseImpl commit(File repository, GitCommitOptions options, String message)
      throws IOException, JavaGitException;

  /**
   * Commits staged changes into the specified repository. The specific files that are committed
   * depends on the options and paths specified.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to commit against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
   * @param options
   *          The options to commit with.
   * @param message
   *          The message to attach to the commit. A non-zero length argument is required for this
   *          parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param paths
   *          A list of folders and/or files to commit. A non-null and non-empty list is required
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
  public GitCommitResponseImpl commit(File repository, GitCommitOptions options, String message,
      List<File> paths) throws IOException, JavaGitException;

  /**
   * Commit everything that is staged in the git repository's index.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to commit against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
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
  public GitCommitResponseImpl commit(File repository, String message) throws IOException,
      JavaGitException;

  /**
   * Commits only the changes in the specified paths into the specified repository. Any staged
   * changes (changes already defined in the index) are ignored.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to commit against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
   * @param message
   *          The message to attach to the commit. A non-zero length argument is required for this
   *          parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param paths
   *          A list of folders and/or files to commit. A non-null and non-empty list is required
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
  public GitCommitResponseImpl commitOnly(File repository, String message, List<File> paths)
      throws IOException, JavaGitException;

}
