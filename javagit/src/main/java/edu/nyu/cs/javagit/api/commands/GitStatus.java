package edu.nyu.cs.javagit.api.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitStatus;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitStatus</code> provides an API to status of a git repository.
 */
public class GitStatus {

  /**
   * It returns a <code>GitStatusResponse</code> object that contains all the details of the
   * output of &lt;git-status&gt; command.
   * 
   * @param repositoryPath
   *          Directory pointing to the root of the repository.
   * @param options
   *          Options that are passed to &lt;git-status&gt; command.
   * @param paths
   *          List of paths or patterns
   * @return <code>GitStatusResponse</code> object
   * @throws JavaGitException
   *          Exception thrown if the repositoryPath is null or if options or paths are invalid.
   * @throws IOException
   *          Exception is thrown if any of the IO operations fail.
   */
  public GitStatusResponse status(File repositoryPath, GitStatusOptions options,
      List<File> paths) throws JavaGitException, IOException {
    CheckUtilities.checkFileValidity(repositoryPath);
    IClient client = ClientManager.getInstance().getPreferredClient();
    IGitStatus gitStatus = client.getGitStatusInstance();
    return gitStatus.status(repositoryPath, options, paths);
  }

  /**
   * It returns a <code>GitStatusResonse</code> object that contains all the details of the output
   * of &lt;git-status&gt; command. It has no options passed as parameter to the method.
   * 
   * @param repositoryPath
   *          Directory path to the root of the repository.
   * @param path
   *          List of paths or file patterns.
   * @return <code>GitStatusResponse</code> object
   * @throws JavaGitException
   *           Exception thrown if the repositoryPath is null or if options or paths are invalid.
   * @throws IOException
   *           Exception is thrown if any of the IO operations fail.
   */
  public GitStatusResponse status(File repositoryPath, List<File> path)
      throws JavaGitException, IOException {
    GitStatusOptions options = null;
    return status(repositoryPath, options, path);
  }
  
  /**
   * It returns a <code>GitStatusResonse</code> object that contains all the details of the output
   * of &lt;git-status&gt; command. It has no path passed as parameter to the method.
   * 
   * @param repositoryPath
   *          Directory path to the root of the repository.
   * @param options
   *          Options that are passed to &lt;git-status&gt; command.
   * @return <code>GitStatusResponse</code> object
   * @throws JavaGitException
   *           Exception thrown if the repositoryPath is null or if options or paths are invalid.
   * @throws IOException
   *           Exception is thrown if any of the IO operations fail.
   */
  public GitStatusResponse status(File repositoryPath, GitStatusOptions options)
      throws JavaGitException, IOException {
    List<File> paths = null;
    return status(repositoryPath, options, paths);
  } 

  /**
   * It returns a <code>GitStatusResonse</code> object that contains all the details of the output
   * of &lt;git-status&gt; command. Instead of passing a list of paths, this method takes
   * a <code>String</code> argument to a file-path.
   * 
   * @param repositoryPath
   *          Directory path to the root of the repository.
   * @param options
   *          Options that are passed to &lt;git-status&gt; command.
   * @param path
   *          Filename or directory
   * @return <code>GitStatusResponse</code> object
   * @throws JavaGitException
   *           Exception thrown if the repositoryPath is null or if options or paths are invalid.
   * @throws IOException
   *           Exception is thrown if any of the IO operations fail.
   */  
  public GitStatusResponse status(File repositoryPath, GitStatusOptions options, File path)
      throws JavaGitException, IOException {
    CheckUtilities.checkFileValidity(path);
    List<File> paths = new ArrayList<File>();
    paths.add(path);
    return status(repositoryPath, options, paths);
  }

  /**
   * It returns a <code>GitStatusResonse</code> object that contains all the details of the output
   * of &lt;git-status&gt; command. It does not pass any options or file paths. This is the most generic
   * execution of &lt;git-status&gt; command.
   * 
   * @param repositoryPath
   *          Directory path to the root of the repository.
   * @return <code>GitStatusResponse</code> object
   * @throws JavaGitException
   *           Exception thrown if the repositoryPath is null or if options or paths are invalid.
   * @throws IOException
   *           Exception is thrown if any of the IO operations fail.
   */ 
  public GitStatusResponse status(File repositoryPath) throws JavaGitException, IOException {
    List<File> paths = null;
    GitStatusOptions options = null;
    return status(repositoryPath, options, paths);
  }

  /**
   * It returns a <code>GitStatusResonse</code> object that contains all the details of the output
   * of &lt;git-status&gt; command. It is equivalent of executing &lt;git-status&gt; command with '-a' option.
   * 
   * @param repositoryPath
   *          Directory path to the root of the repository.
   * @return <code>GitStatusResponse</code> object
   * @throws JavaGitException
   *           Exception thrown if the repositoryPath is null or if options or paths are invalid.
   * @throws IOException
   *           Exception is thrown if any of the IO operations fail.
   */ 
  public GitStatusResponse statusAll(File repositoryPath) throws JavaGitException, IOException {
    GitStatusOptions options = new GitStatusOptions();
    options.setOptAll(true);
    return status(repositoryPath, options);
  }
}
