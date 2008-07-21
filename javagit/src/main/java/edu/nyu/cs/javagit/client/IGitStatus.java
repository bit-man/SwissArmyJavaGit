package edu.nyu.cs.javagit.client;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitStatusOptions;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;

/**
 * An interface to represent the &lt;git-status&gt; command.
 */
public interface IGitStatus {

  public GitStatusResponse status(File repositoryPath, GitStatusOptions options, List<File> paths) 
    throws JavaGitException, IOException;

  /**
   * Return status for a single <code>File</code>
   * 
   * @param repositoryPath
   *          Directory path to the root of the repository.
   * @param options
   *          Options that are passed to &lt;git-status&gt; command.
   * @param file
   *          <code>File</code> instance 
   * @return <code>GitStatusResponse</code> object
   * @throws JavaGitException
   *           Exception thrown if the repositoryPath is null
   * @throws IOException
   *           Exception is thrown if any of the IO operations fail.
   */
  public GitStatusResponse getSingleFileStatus(File repositoryPath, GitStatusOptions options, File file) 
    throws JavaGitException, IOException;

}
