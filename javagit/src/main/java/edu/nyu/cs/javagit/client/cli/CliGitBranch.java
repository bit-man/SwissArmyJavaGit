/**
 * 
 */
package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.api.commands.GitBranchOptions;
import edu.nyu.cs.javagit.api.commands.GitBranchResponse;
import edu.nyu.cs.javagit.api.commands.GitBranchResponse.responseType;
import edu.nyu.cs.javagit.client.IGitBranch;
import edu.nyu.cs.javagit.utilities.CheckUtilities;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;
/**
 * Command-line implementation of the <code>IGitBranch</code> interface.
 */
public class CliGitBranch implements IGitBranch {
  public GitBranchResponse branch(File repoPath, GitBranchOptions options) throws IOException, 
      JavaGitException {
    return branchProcess(repoPath, options, null, null, null );
  }

  public GitBranchResponse branch(File repoPath, GitBranchOptions options, List<Ref> branchList) 
      throws IOException, JavaGitException {
    return branchProcess(repoPath, options, null, null, branchList );
  }
 
  public GitBranchResponse branch(File repoPath, GitBranchOptions options, Ref arg1, 
      Ref arg2) throws IOException, JavaGitException {
    return branchProcess(repoPath, options, arg1, arg2, null);
  }

  /**
   * Process the git-branch command, to show/delete/create/rename branches.
   * 
   * @param repositoryPath
   *          A <code>File</code> instance for the path to the repository. If null is passed, 
   *          a <code>NullPointerException</code> will be thrown.
   * @param options
   *          The options to include on the command line.
   * @param arg1
   *          When renaming a branch to a different name, this is the old branch. 
   *          When creating a branch this the branch name.
   * @param arg2
   *          When renaming a branch to a new branch name, this is the new branch name.
   *          When creating a branch, this is the head to start from. 
   * @param branchList
   *          List of branches need to be deleted.
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
  public GitBranchResponse branchProcess(File repoPath, GitBranchOptions options, Ref arg1, 
      Ref arg2, List<Ref> branchList) throws IOException, JavaGitException {
    CheckUtilities.checkNullArgument(repoPath, "repository path");
    List<String> commandLine = buildCommand(options, arg1, arg2, branchList );
    GitBranchParser parser = new GitBranchParser();

    return (GitBranchResponse) ProcessUtilities.runCommand(repoPath.getAbsolutePath(), commandLine, parser);
   }

  /**
   * Builds a list of command arguments to pass to <code>ProcessBuilder</code>.
   * 
   * @param options
   *          The options to include on the command line.
   * @param arg1
   *          When renaming a branch to a different name, this is the old branch. 
   *          When creating a branch this the branch name.
   * @param arg2
   *          When renaming a branch to a new branch name, this is the new branch name.
   *          When creating a branch, this is the head to start from. 
   * @param branchList
   *          List of branches need to be deleted.
   * @return A list of the individual arguments to pass to <code>ProcessBuilder</code>.
   */
  protected List<String> buildCommand(GitBranchOptions options, Ref arg1, Ref arg2, 
      List<Ref> branchList) {
    List<String> cmd = new ArrayList<String>();
    cmd.add("git-branch");

    if (null != options) {
      String commit = options.getOptContains();
      if (null != commit && commit.length() > 0) {
        cmd.add("--contains");
        cmd.add("commit");
      }
      if(options.isOptAbbrev()) {
        cmd.add("--abbrev");
      }
      String abbrev = options.getOptAbbrevVal();
      if (null != abbrev && abbrev.length() > 0) {
        cmd.add(abbrev);
      }
      if(options.isOptNoAbbrev()) {
        cmd.add("--no-abbrev");
      }
      if (options.isOptA()) {
        cmd.add("-a");
      }
      if (options.isOptBigD()) {
        cmd.add("-D");
      }
      if (options.isOptBigM()) {
        cmd.add("-M");
      }
      if (options.isOptColor()) {
        cmd.add("--color");
      }
      if (options.isOptNoColor()) {
        cmd.add("--no-color");
      }
      if (options.isOptF()) {
        cmd.add("-f");
      }
      if (options.isOptL()) {
        cmd.add("-l");
      }
      if (options.isOptMerged()) {
        cmd.add("--merged");
      }
      if (options.isOptNoMerged()) {
        cmd.add("--no-merged");
      }
      if (options.isOptR()) {
        cmd.add("-r");
      }
      if (options.isOptSmallD()) {
        cmd.add("-d");
      }
      if (options.isOptSmallM()) {
        cmd.add("-m");
      }
      if (options.isOptTrack()) {
        cmd.add("--track");
      }
      if (options.isOptNoTrack()) {
        cmd.add("--no-track");
      }
      if(options.isOptVerbose()) {
        cmd.add("--verbose");
      }
    }
    if (null != branchList) {
      for (Ref branch : branchList) {
        cmd.add(branch.getName());
      }
    }
    else {
      if (null != arg1) {
        cmd.add(arg1.getName());
      }
      if (null != arg2) {
        cmd.add(arg2.getName());
      }
    }
    return cmd;
  }
  /**
   * Implementation of the <code>IParser</code> interface in GitMvParser class.
   */
  public class GitBranchParser implements IParser {
    //  The response object for a branch operation.
    private GitBranchResponse response;
    
    // While handling the error cases this buffer will have the error messages.
    private StringBuffer errorMessage = null;
    
    // Track the number of lines parsed.
    private int numLinesParsed = 0;
    
    /**
     * Parses the line from the git-branch response text.
     * 
     * @param line
     *          The line of text to process.
     */
    public void parseLine(String line) {
      if (null != errorMessage) {
        errorMessage.append("\n"+line);
        return;
      }
      if (line.contains("fatal:") || line.contains("error:")) {
        if (null == errorMessage) {
          errorMessage = new StringBuffer();
        }
        errorMessage.append(line);
      }
      else {
        if (null == response) {
          response = new GitBranchResponse();
        }
        
        if (line.startsWith("Deleted branch")) {
          int indexOfBranch = line.indexOf("branch");
          String branchName = line.substring(indexOfBranch+7);
          response.setResponseType(responseType.MESSAGE);
          if (0 == numLinesParsed) {
            response.addMessages(line.substring(0, indexOfBranch+6));
            numLinesParsed++;
          }
          response.addIntoBranchList(Ref.createBranchRef(branchName));
        }
        else if (null == line) {
          response.setResponseType(responseType.EMPTY);
        }
        else {
          response.setResponseType(responseType.BRANCH_LIST);
          int indexOfSpace = line.indexOf(" ", 2);
          int lastIndexOfSpace = line.lastIndexOf(" ");   
          response.addIntoBranchList(Ref.createBranchRef(line.substring(2, indexOfSpace)));
          if (line.startsWith("*")) {
            response.setCurrentBranch(Ref.createBranchRef(line.substring(2, indexOfSpace)));
          }
          if(indexOfSpace != lastIndexOfSpace) {
            int indexOfNextSpace = line.indexOf(" ", indexOfSpace);
            String sha = line.substring(indexOfSpace, indexOfNextSpace);
            int noOfSpacesToBeTruncated = sha.lastIndexOf(" ");
            response.addIntoShaList(Ref.createSha1Ref(sha.substring(noOfSpacesToBeTruncated)));
            response.addComments(line.substring(indexOfNextSpace+1));
          }
        }
      }
    }
    
    /**
     * Throws appropriate JavaGitException for an error case or returns the GitBranchResponse
     * object to the upper layer.
     * 
     * @return
     *       GitBranchResponse object.
     * @throws JavaGitException
     */
    public GitBranchResponse getResponse() throws JavaGitException {
      if (null != errorMessage) {
        throw new JavaGitException(404000, ExceptionMessageMap.getMessage("404000")
            + "  The git-branch error message:  { " + errorMessage.toString() + " }");
      }
      return response;
    }
  }
}
