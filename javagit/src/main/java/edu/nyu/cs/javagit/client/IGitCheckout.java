package edu.nyu.cs.javagit.client;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitCheckoutOptions;
import edu.nyu.cs.javagit.api.commands.GitCheckoutResponse;

public interface IGitCheckout {

  /**
   * Checks out either an existing branch or new branch from the repository.
   * 
   * @param repositoryPath
   *          Path to the root of the repository
   * @param options
   *          GitCheckoutOptions object used for passing options to &lt;git-checkout&gt;
   * @param branch
   *          Name of the base branch that need to be checked out or if the new branch is being
   *          checkout based on this base branch.
   * @return <code>GitCheckoutResponse</code> object
   * @throws JavaGitException
   *           exception thrown if invalid options are passed
   * @throws IOException
   *           exception thrown if directory or files do not exist.
   */
  public GitCheckoutResponse checkout(File repositoryPath, GitCheckoutOptions options, String branch)
      throws JavaGitException, IOException;

  /**
   * Vanilla version of &lt;git-checkout&gt; where no options and no branch info is passed to it.
   * 
   * @param repositoryPath
   *          path to the root of the repository
   * @return GitCheckoutResponse object
   * @throws JavaGitException
   *           exception thrown if invalid options are passed
   * @throws IOException
   *           exception thrown if directory or files do not exist
   */
  public GitCheckoutResponse checkout(File repositoryPath) throws JavaGitException, IOException;

  /**
   * Checks out an existing branch with no options provided.
   * 
   * @param repositoryPath
   *          path to the root of the repository
   * @param branch
   *          name of the base branch that need to be checked out
   * @return GitCheckoutResponse object
   * @throws JavaGitException
   *           thrown if branch name is null
   * @throws IOException
   *           exception thrown if directory or files do not exist
   */
  public GitCheckoutResponse checkout(File repositoryPath, String branch) throws JavaGitException,
      IOException;

  /**
   * &lt;git-checkout&gt; where a list of files is given to be checked out.
   * 
   * @param repositoryPath
   *          path to the root of the repository
   * @param paths
   *          list of file paths or directory that need to be checked out from git repository.
   * @return GitCheckoutResponse object
   * @throws JavaGitException
   *           exception thrown if invalid options are passed
   * @throws IOException
   *           exception thrown if directory or files do not exist
   */
  public GitCheckoutResponse checkout(File repositoryPath, List<File> paths)
      throws JavaGitException, IOException;
}
