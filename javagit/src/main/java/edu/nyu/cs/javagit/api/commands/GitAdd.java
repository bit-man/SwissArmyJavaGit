package edu.nyu.cs.javagit.api.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitAdd;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitAdd</code> provides an interface for adding files or directory tree into git
 * repository
 */
public final class GitAdd {
  /**
   * This command adds the current content of new or modified files to the index, thus staging that
   * content for inclusion in the next commit.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param options
   *          <code>GitAddOptions</code> that are passed to the &lt;git-add&gt; command.
   * @param paths
   *          <code>List</code> of file paths that will be added to the index.
   * @return results from the add command.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown if -
   *              <ul>
   *              <li>repositoryPath is not valid</li>
   *              <li>filenames assigned do not exist</li>
   *              <li>files or repository path does not have access rights</li>
   *              </ul>
   */
  public GitAddResponse add(File repositoryPath, GitAddOptions options, List<File> paths)
      throws IOException, JavaGitException {
    CheckUtilities.checkFileValidity(repositoryPath.getAbsoluteFile());
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitAdd gitAdd = client.getGitAddInstance();
    return gitAdd.add(repositoryPath, options, paths);
  }

  /**
   * This command adds the current content of new or modified files to the index, with verbose
   * option set.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param options
   *          <code>GitAddOptions</code> that are passed to the &lt;git-add&gt; command.
   * @param paths
   *          <code>List</code> of file paths that will be added to the index.
   * @return results <code>GitAddResponse</code> from the add command.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown if -
   *              <ul>
   *              <li>repositoryPath is not valid</li>
   *              <li>filenames assigned do not exist</li>
   *              <li>files or repository path does not have access rights</li>
   *              </ul>
   */
  public GitAddResponse addVerbose(File repositoryPath, List<File> paths) throws IOException,
      JavaGitException {
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitAdd gitAdd = client.getGitAddInstance();
    return gitAdd.addVerbose(repositoryPath, paths);
  }

  /**
   * This command adds the current content of new or modified files to the index, with Force option
   * set i.e. it adds the ignored files as well.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param options
   *          <code>GitAddOptions</code> that are passed to the &lt;git-add&gt; command.
   * @param paths
   *          <code>List</code> of file paths that will be added to the index.
   * @return results <code>GitAddResponse</code> from the add command.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown if -
   *              <ul>
   *              <li>repositoryPath is not valid</li>
   *              <li>filenames assigned do not exist</li>
   *              <li>files or repository path does not have access rights</li>
   *              </ul>
   */
  public GitAddResponse addWithForce(File repositoryPath, List<File> paths) throws IOException,
      JavaGitException {
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitAdd gitAdd = client.getGitAddInstance();
    return gitAdd.addWithForce(repositoryPath, paths);
  }

  /**
   * This command just executes &lt;git-add&gt; in dry run mode.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param options
   *          <code>GitAddOptions</code> that are passed to the &lt;git-add&gt; command.
   * @param paths
   *          <code>List</code> of file paths that will be added to the index.
   * @return results <code>GitAddResponse</code> from the add command.
   * @exception IOException
   *              There are many reasons for which an <code>IOException</code> could be thrown if -
   *              <ul>
   *              <li>repositoryPath is not valid</li>
   *              <li>filenames assigned do not exist</li>
   *              <li>files or repository path does not have access rights</li>
   *              </ul>
   */
  public GitAddResponse addDryRun(File repositoryPath, List<File> paths) throws IOException,
      JavaGitException {
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitAdd gitAdd = client.getGitAddInstance();
    return gitAdd.addDryRun(repositoryPath, paths);
  }
  
  /**
   * This command adds the current content of new or modified file to the index, thus staging that
   * content for inclusion in the next commit.
   * 
   * @param repositoryPath
   *          The path to the repository to commit against. A non-zero length argument is required
   *          for this parameter, otherwise a <code>NullPointerException</code> or
   *          <code>IllegalArgumentException</code> will be thrown.
   * @param options
   *          <code>GitAddOptions</code> that are passed to the &lt;git-add&gt; command.
   * @param paths
   *          <code>File</code> to be added to the index.
   * @return <code>GitAddResponse</code> object.
   * @exception </code>IOException</code>
   *              There are many reasons for which an <code>IOException</code> may be thrown if -
   *              <ul>
   *              <li>repositoryPath is not valid</li>
   *              <li>filenames assigned do not exist</li>
   *              <li>files or repository path does not have access rights</li>
   *              </ul>
   */
  public GitAddResponse add(File repositoryPath, GitAddOptions options, File file)
  throws IOException, JavaGitException {
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitAdd gitAdd = client.getGitAddInstance();
    return gitAdd.add(repositoryPath, options, file);
  }

}
