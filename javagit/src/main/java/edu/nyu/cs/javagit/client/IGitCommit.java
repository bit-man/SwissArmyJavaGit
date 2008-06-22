package edu.nyu.cs.javagit.client;

import java.io.IOException;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitCommitResponse;

/**
 * An interface to represent the git-commit command.
 */
public interface IGitCommit {

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
  public GitCommitResponse commit(String repoPath, String message) throws IOException,
      JavaGitException;

}
