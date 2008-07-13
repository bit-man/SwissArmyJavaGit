package edu.nyu.cs.javagit.api.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.GitCommitResponseImpl;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitCommit;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitCommit</code> provides an API to commit changes to a git repository.
 */
public class GitCommit {

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
  public GitCommitResponseImpl commitAll(File repository, String message) throws IOException,
      JavaGitException {
    CheckUtilities.checkNullArgument(repository, "repository");
    CheckUtilities.checkStringArgument(message, "message");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCommit gitCommit = client.getGitCommitInstance();
    return gitCommit.commitAll(repository, message);
  }

  /**
   * Commits staged changes into the specified repository. The specific files that are committed
   * depends on the options specified.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to commit against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
   * @param options
   *          The options to commit with. If null is passed, a <code>NullPointerException</code>
   *          will be thrown.
   * @param message
   *          The message to attach to the commit. A non-zero length argument is required for this
   *          parameter, otherwise a <code>NullPointerException</code> or
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
  public GitCommitResponse commit(File repository, GitCommitOptions options, String message)
      throws IOException, JavaGitException {

    CheckUtilities.checkNullArgument(repository, "repository");
    CheckUtilities.checkStringArgument(message, "message");
    CheckUtilities.checkNullArgument(options, "options");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCommit gitCommit = client.getGitCommitInstance();
    return gitCommit.commit(repository, options, message);
  }

  /**
   * Commits staged changes into the specified repository. The specific files that are committed
   * depends on the options and paths specified.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to commit against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
   * @param options
   *          The options to commit with. If null is passed, a <code>NullPointerException</code>
   *          will be thrown.
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
  public GitCommitResponse commit(File repository, GitCommitOptions options, String message,
      List<File> paths) throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repository, "repository");
    CheckUtilities.checkStringArgument(message, "message");
    CheckUtilities.checkNullArgument(options, "options");
    CheckUtilities.checkNullListArgument(paths, "paths");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCommit gitCommit = client.getGitCommitInstance();
    return gitCommit.commit(repository, options, message, paths);
  }

  /**
   * Commits all staged changes (changes already defined in the index) into the specified
   * repository.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to commit against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
   * @param message
   *          The message to attach to the commit. A non-zero length argument is required for this
   *          parameter, otherwise a <code>NullPointerException</code> or
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
  public GitCommitResponse commit(File repository, String message) throws IOException,
      JavaGitException {
    CheckUtilities.checkNullArgument(repository, "repository");
    CheckUtilities.checkStringArgument(message, "message");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCommit gitCommit = client.getGitCommitInstance();
    return gitCommit.commit(repository, message);
  }

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
  public GitCommitResponse commitOnly(File repository, String message, List<File> paths)
      throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repository, "repository");
    CheckUtilities.checkStringArgument(message, "message");
    CheckUtilities.checkNullListArgument(paths, "paths");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCommit gitCommit = client.getGitCommitInstance();
    return gitCommit.commitOnly(repository, message, paths);
  }

}
