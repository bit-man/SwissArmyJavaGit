package edu.nyu.cs.javagit.client;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitRmOptions;
import edu.nyu.cs.javagit.api.commands.GitRmResponse;

/**
 * An interface implementing functionality of the git-rm command.
 */
public interface IGitRm {

  /**
   * Remove files relative to the path within the repository.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to run rm against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
   * @param options
   *          The options to run rm with. If null is passed, a <code>NullPointerException</code>
   *          will be thrown.
   * @param paths
   *          A list of files to remove. A non-zero length argument is required for this parameter
   *          and its children, otherwise a <code>NullPointerException</code> or
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
  public GitRmResponse rm(File repository, GitRmOptions options, List<File> paths)
      throws IOException, JavaGitException;

  /**
   * Remove files relative to the path within the repository.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to run rm against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
   * @param path
   *          A single file to remove. If null is passed, a <code>NullPointerException</code> will
   *          be thrown.
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
  public GitRmResponse rm(File repository, File path) throws IOException, JavaGitException;

  /**
   * Remove files relative to the path within the repository.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to run rm against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
   * @param paths
   *          A list of files to remove. A non-zero length argument is required for this parameter
   *          and its children, otherwise a <code>NullPointerException</code> or
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
  public GitRmResponse rm(File repository, List<File> paths) throws IOException, JavaGitException;

  /**
   * Remove files relative to the path within the repository but only effect the index.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to run rm against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
   * @param paths
   *          A list of files to remove. A non-zero length argument is required for this parameter
   *          and its children, otherwise a <code>NullPointerException</code> or
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
  public GitRmResponse rmCached(File repository, List<File> paths) throws IOException,
      JavaGitException;

  /**
   * Recursively remove files relative to the path within the repository.
   * 
   * @param repository
   *          A <code>File</code> instance for the path to the repository to run rm against. If
   *          null is passed, a <code>NullPointerException</code> will be thrown.
   * @param paths
   *          A list of files to remove. A non-zero length argument is required for this parameter
   *          and its children, otherwise a <code>NullPointerException</code> or
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
  public GitRmResponse rmRecursive(File repository, List<File> paths) throws IOException,
      JavaGitException;

}
