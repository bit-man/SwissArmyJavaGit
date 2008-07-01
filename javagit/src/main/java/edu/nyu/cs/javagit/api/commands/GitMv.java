/**
 * 
 */
package edu.nyu.cs.javagit.api.commands;

import java.io.IOException;

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
  public GitMvResponse mv(String repositoryPath, GitMvOptions options, String source,
      String destination) throws IOException, JavaGitException {

    // TODO : Change this method to take the JavaGitConfig object,
    // or whatever it ends up being called, once Ross and Max create it.
    CheckUtilities.checkStringArgument(repositoryPath, "repository path");
    CheckUtilities.checkStringArgument(source, "source");
    CheckUtilities.checkStringArgument(destination, "destination");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitMv gitMv = client.getGitMvInstance();
    return gitMv.mv(repositoryPath, options, source, destination);
  }

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
  public GitMvResponse mv(String repositoryPath, String source, String destination)
      throws IOException, JavaGitException {

    // TODO : Change this method to take the JavaGitConfig object,
    // or whatever it ends up being called, once Ross and Max create it.
    CheckUtilities.checkStringArgument(repositoryPath, "repository path");
    CheckUtilities.checkStringArgument(source, "source");
    CheckUtilities.checkStringArgument(destination, "destination");

    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitMv gitMv = client.getGitMvInstance();
    return gitMv.mv(repositoryPath, source, destination);
  }

}
