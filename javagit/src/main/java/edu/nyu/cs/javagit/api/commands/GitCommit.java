package edu.nyu.cs.javagit.api.commands;

import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitCommit;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitCommit</code> provides an interface to commit changes to a git repository.
 */
public class GitCommit {

  /**
   * Automatically stage all tracked files that have been changed and then commit all files staged
   * in the git repository's index.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
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
  public GitCommitResponse commitAll(String repositoryPath, String message) throws IOException,
      JavaGitException {

    /*
     * TODO (jhl388): Change this method to take the JavaGitConfig object, or whatever it ends up
     * being called, once Ross and Max create it.
     */

    CheckUtilities.checkStringArgument(repositoryPath, "repository path");
    CheckUtilities.checkStringArgument(message, "message");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCommit gitCommit = client.getGitCommitInstance();
    return gitCommit.commitAll(repositoryPath, message);
  }

  /**
   * Commits staged changes into the specified repository. The specific files that are committed
   * depends on the options specified.
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
  public GitCommitResponse commit(String repositoryPath, GitCommitOptions options, String message)
      throws IOException, JavaGitException {

    /*
     * TODO (jhl388): Change this method to take the JavaGitConfig object, or whatever it ends up
     * being called, once Ross and Max create it.
     */

    CheckUtilities.checkStringArgument(repositoryPath, "repository path");
    CheckUtilities.checkStringArgument(message, "message");
    CheckUtilities.checkNullArgument(options, "options");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCommit gitCommit = client.getGitCommitInstance();
    return gitCommit.commit(repositoryPath, options, message);
  }

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
  public GitCommitResponse commit(String repositoryPath, GitCommitOptions options, String message,
      List<String> paths) throws IOException, JavaGitException {

    /*
     * TODO (jhl388): Change this method to take the JavaGitConfig object, or whatever it ends up
     * being called, once Ross and Max create it.
     */

    CheckUtilities.checkStringArgument(repositoryPath, "repository path");
    CheckUtilities.checkStringArgument(message, "message");
    CheckUtilities.checkNullArgument(options, "options");
    CheckUtilities.checkStringListArgument(paths, "paths");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCommit gitCommit = client.getGitCommitInstance();
    return gitCommit.commit(repositoryPath, options, message, paths);
  }

  /**
   * Commits all staged changes (changes already defined in the index) into the specified
   * repository.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
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
  public GitCommitResponse commit(String repositoryPath, String message) throws IOException,
      JavaGitException {

    /*
     * TODO (jhl388): Change this method to take the JavaGitConfig object, or whatever it ends up
     * being called, once Ross and Max create it.
     */

    CheckUtilities.checkStringArgument(repositoryPath, "repository path");
    CheckUtilities.checkStringArgument(message, "message");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCommit gitCommit = client.getGitCommitInstance();
    return gitCommit.commit(repositoryPath, message);
  }

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
  public GitCommitResponse commitOnly(String repositoryPath, String message, List<String> paths)
      throws IOException, JavaGitException {

    /*
     * TODO (jhl388): Change this method to take the JavaGitConfig object, or whatever it ends up
     * being called, once Ross and Max create it.
     */

    CheckUtilities.checkStringArgument(repositoryPath, "repository path");
    CheckUtilities.checkStringArgument(message, "message");
    CheckUtilities.checkStringListArgument(paths, "paths");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitCommit gitCommit = client.getGitCommitInstance();
    return gitCommit.commitOnly(repositoryPath, message, paths);
  }

}
