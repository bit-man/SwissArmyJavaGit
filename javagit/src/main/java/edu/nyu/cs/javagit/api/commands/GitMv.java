/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.api.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitMv;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitMv</code> provides an interface to move files, symlinks or directories to a different
 * location or rename them.
 * 
 */
public class GitMv {
  /**
   * Moves the specified source file/symlink/directory to the destination directory. If
   * destination is non-existent then same as rename.
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
   *              Thrown when there is an error executing git-mv.
   */
  public GitMvResponse mv(File repositoryPath, File source, File destination)
      throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repositoryPath, "repository path");
    CheckUtilities.checkNullArgument(source, "source");
    CheckUtilities.checkNullArgument(destination, "destination");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitMv gitMv = client.getGitMvInstance();
    return gitMv.mv(repositoryPath, source, destination);
  }
  
  /**
   * Moves the specified source file/symlink/directory to the destination directory. If destination
   * is non-existent then same as rename.
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
   *              Thrown when there is an error executing git-mv.
   */
  public GitMvResponse mv(File repositoryPath, GitMvOptions options, File source,
      File destination) throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repositoryPath, "repository path");
    CheckUtilities.checkNullArgument(source, "source");
    CheckUtilities.checkNullArgument(options, "options");
    CheckUtilities.checkNullArgument(destination, "destination");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitMv gitMv = client.getGitMvInstance();
    return gitMv.mv(repositoryPath, options, source, destination);
  }
  
  /**
   * Moves the specified source file/symlink/directory to the destination directory. If destination
   * is non-existent then same as rename.
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
  public GitMvResponse mv(File repositoryPath, List<File> sources, File destination) 
      throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repositoryPath, "repository path");
    CheckUtilities.checkNullListArgument(sources, "sources");
    CheckUtilities.checkDirectoryArgument(destination, "destination");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitMv gitMv = client.getGitMvInstance();
    return gitMv.mv(repositoryPath, sources, destination);
  }
  
  /**
   * Moves the specified source file/symlink/directory to the destination directory. If destination
   * is non-existent then same as rename.
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
      File destination) throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repositoryPath, "repository path");
    CheckUtilities.checkNullArgument(options, "options");
    CheckUtilities.checkNullListArgument(sources, "sources");
    CheckUtilities.checkDirectoryArgument(destination, "destination");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitMv gitMv = client.getGitMvInstance();
    return gitMv.mv(repositoryPath, options, sources, destination);
  }
}
