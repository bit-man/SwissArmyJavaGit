package edu.nyu.cs.javagit.client;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.api.commands.GitBranchOptions;
import edu.nyu.cs.javagit.api.commands.GitBranchResponse;

/**
 * An interface to represent git-branch command.
 *
 */
public interface IGitBranch {
  /**
   * Perform a show branch on the repository.
   * 
   * @param repositoryPath
   *          A <code>File</code> instance for the path to the repository. If null is passed, 
   *          a <code>NullPointerException</code> will be thrown.
   * @param options
   *          The options for a git-branch command. If the value is null, a
   *          <code>NullPointerException</code> will be thrown.
   * @return The result of the git branch.
   * @throws IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>a directory doesn't exist</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @throws JavaGitException
   *              Thrown when there is an error executing git-branch.
   */
  public GitBranchResponse branch(File repositoryPath, GitBranchOptions options) 
      throws IOException, JavaGitException;
  /**
   * Perform a delete branch on the repository.
   * 
   * @param repositoryPath
   *          A <code>File</code> instance for the path to the repository. If null is passed, 
   *          a <code>NullPointerException</code> will be thrown.
   * @param options
   *          The options for a git-branch command. If the value is null, a
   *          <code>NullPointerException</code> will be thrown.
   * @param branchNames
   *          The list of branches to be deleted.
   * @return The result of the git branch.
   * @throws IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>a directory doesn't exist</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @throws JavaGitException
   *              Thrown when there is an error executing git-branch.
   */
  public GitBranchResponse branch(File repositoryPath, GitBranchOptions options, 
      List<Ref> branchNames) throws IOException, JavaGitException;
  /**
   * Perform a create/rename branch on the repository.
   * 
   * @param repositoryPath
   *          A <code>File</code> instance for the path to the repository. If null is passed, 
   *          a <code>NullPointerException</code> will be thrown.
   * @param options
   *          The options for a git-branch command. If the value is null, a
   *          <code>NullPointerException</code> will be thrown.
   * @param arg1
   *          When renaming a branch to a different name, this is the old branch. 
   *          When creating a branch this the branch name.
   * @param arg2
   *          When renaming a branch to a new branch name, this is the new branch name.
   *          When creating a branch, this is the head to start from. 
   * @return The result of the git branch.
   * @throws IOException
   *              There are many reasons for which an <code>IOException</code> may be thrown.
   *              Examples include:
   *              <ul>
   *              <li>a directory doesn't exist</li>
   *              <li>a command is not found on the PATH</li>
   *              </ul>
   * @throws JavaGitException
   *              Thrown when there is an error executing git-branch.
   */
  public GitBranchResponse branch(File repositoryPath, GitBranchOptions options, 
      Ref arg1, Ref arg2) throws IOException, JavaGitException;
  
}
