package edu.nyu.cs.javagit.client;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitMvOptions;
import edu.nyu.cs.javagit.api.commands.GitMvResponse;

/**
 * An interface to represent the git-mv command.
 */
public interface IGitMv {
  /**
   * Moves the specified source file/symlink/directory to the destination file/symlink/directory. If
   * destination is non-existant then same as rename.
   * 
   * @param repositoryPath
   *          The path to the repository, to be treated as root folder for git-mv operation. A
   *          non-zero length argument is required for this parameter, otherwise a
   *          <code>NullPointerException</code> or <code>IllegalArgumentException</code> will be
   *          thrown.
   * @param source
   *          The source file/folder/symlink which is to be renamed or moved to a different
   *          location. A non-zero length argument is required for this parameter, otherwise a
   *          <code>NullPointerException</code> or <code>IllegalArgumentException</code> will be
   *          thrown.
   * @param destination
   *          The destination file/folder/symlink which the source is renamed or moved to. A
   *          non-zero length argument is required for this parameter, otherwise a
   *          <code>NullPointerException</code> or <code>IllegalArgumentException</code> will be
   *          thrown.
   * @return The results from the git-mv.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>access to a file is denied</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @exception JavaGitException
   *              Thrown when there is an error excecuting git-mv.
   */
  public GitMvResponse mv(File repositoryPath, File source, File destination)
      throws IOException, JavaGitException;
  
  /**
   * Moves the specified source file/symlink/directory to the destination file/symlink/directory. If
   * destination is non-existant then same as rename.
   * 
   * @param repositoryPath
   *          The path to the repository, to be treated as root folder for git-mv operation. A
   *          non-zero length argument is required for this parameter, otherwise a
   *          <code>NullPointerException</code> or <code>IllegalArgumentException</code> will be
   *          thrown.
   * @param options
   *          The options to git-mv command.
   * @param source
   *          The source file/folder/symlink which is to be renamed or moved to a different
   *          location. A non-zero length argument is required for this parameter, otherwise a
   *          <code>NullPointerException</code> or <code>IllegalArgumentException</code> will be
   *          thrown.
   * @param destination
   *          The destination file/folder/symlink which the source is renamed or moved to. A
   *          non-zero length argument is required for this parameter, otherwise a
   *          <code>NullPointerException</code> or <code>IllegalArgumentException</code> will be
   *          thrown.
   * @return The results from the git-mv.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>access to a file is denied</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @exception JavaGitException
   *              Thrown when there is an error excecuting git-mv.
   */
  public GitMvResponse mv(File repositoryPath, GitMvOptions options, File source,
      File destination) throws IOException, JavaGitException;

  /**
   * Moves the specified source files/symlinks/directories to the destination directory. 
   * 
   * @param repositoryPath
   *          The path to the repository, to be treated as root folder for git-mv operation. A
   *          non-zero length argument is required for this parameter, otherwise a
   *          <code>NullPointerException</code> or <code>IllegalArgumentException</code> will be
   *          thrown.
   * @param sources
   *          The <code>List</code> of source file/folder/symlink which are to be moved to a 
   *          different location. A non-zero length argument is required for this parameter, 
   *          otherwise a <code>NullPointerException</code> or 
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param destination
   *          The destination folder which the source is moved to. A non-zero length argument is 
   *          required for this parameter, otherwise a <code>NullPointerException</code> or 
   *          <code>IllegalArgumentException</code> will be thrown.
   * @return The results from the git-mv.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>access to a file is denied</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @exception JavaGitException
   *              Thrown when there is an error executing git-mv.
   */
  public GitMvResponse mv(File repositoryPath, List<File> sources, File destination) 
      throws IOException, JavaGitException;
  
  /**
   * Moves the specified source files/symlinks/directories to the destination directory. 
   * 
   * @param repositoryPath
   *          The path to the repository, to be treated as root folder for git-mv operation. A
   *          non-zero length argument is required for this parameter, otherwise a
   *          <code>NullPointerException</code> or <code>IllegalArgumentException</code> will be
   *          thrown.
   * @param options
   *          The options to git-mv command.
   * @param sources
   *          The <code>List</code> of source file/folder/symlink which are to be moved to a 
   *          different location. A non-zero length argument is required for this parameter, 
   *          otherwise a <code>NullPointerException</code> or 
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param destination
   *          The destination folder which the source is moved to. A non-zero length argument is 
   *          required for this parameter, otherwise a <code>NullPointerException</code> or 
   *          <code>IllegalArgumentException</code> will be thrown.
   * @return The results from the git-mv.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>access to a file is denied</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @exception JavaGitException
   *              Thrown when there is an error executing git-mv.
   */
  public GitMvResponse mv(File repositoryPath, GitMvOptions options, List<File> sources,
      File destination) throws IOException, JavaGitException;
}
