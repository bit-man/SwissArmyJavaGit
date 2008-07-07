/**
 * 
 */
package edu.nyu.cs.javagit.client.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitBranchOptions;
import edu.nyu.cs.javagit.api.commands.GitBranchResponse;
import edu.nyu.cs.javagit.client.IGitBranch;
import edu.nyu.cs.javagit.utilities.CheckUtilities;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

/**
 * 
 *
 */
public class CliGitBranch implements IGitBranch {
  
  public GitBranchResponse branch(String repoPath, GitBranchOptions options) throws IOException, 
      JavaGitException {
    return branchProcess(repoPath, options, null, null );
  }
  public GitBranchResponse branch(String repoPath, GitBranchOptions options, String branchName) 
      throws IOException, JavaGitException {
    return branchProcess(repoPath, options, branchName, null );
  }
  public GitBranchResponse branch(String repoPath, GitBranchOptions options, String arg1, 
      String arg2) throws IOException, JavaGitException {
    return branchProcess(repoPath, options, arg1, arg2);
  }
  
  public GitBranchResponse branchProcess(String repoPath, GitBranchOptions options, String arg1, 
      String arg2) throws IOException, JavaGitException {
    CheckUtilities.checkStringArgument(repoPath, "repository path");
    List<String> commandLine = buildCommand(options, arg1, arg2);
    GitBranchParser parser = new GitBranchParser();

    return (GitBranchResponse) ProcessUtilities.runCommand(repoPath, commandLine, parser);
   }
  /**
   * Builds a list of command arguments to pass to <code>ProcessBuilder</code>.
   * 
   * @param options
   *          The options to include on the command line.
   * @param message
   *          The message for the commit.
   * @return A list of the individual arguments to pass to <code>ProcessBuilder</code>.
   */
  protected List<String> buildCommand(GitBranchOptions options, String arg1, String arg2) {
    List<String> cmd = new ArrayList<String>();
    cmd.add("git-branch");

    if (null != options) {
      String commit = options.getOptContains();
      if (null != commit && commit.length() > 0) {
        cmd.add("--contains");
        cmd.add("commit");
      }
      String abbrev = options.getOptAbbrev();
      if (null != abbrev && abbrev.length() > 0) {
        cmd.add("--abbrev");
        cmd.add("abbrev");
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
    if (null != arg1) {
      cmd.add("arg1");
    }
    if (null != arg2) {
      cmd.add("arg2");
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
    
    /**
     * Parses the line from the git-branch response text.
     * 
     * @param line
     *          The line of text to process.
     *     
     * TODO (ns1344): Add more to parsing to handle some more options.
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
        boolean isCurrentBranch = false;
        if (null == response) {
          response = new GitBranchResponse();
        }
        if (line.startsWith("*")) {
          isCurrentBranch = true;
        }
        if (line.startsWith("Deleted branch")) {
          response.addComment(line);
        }
        else
        {
          response.addIntoBranchList(line, isCurrentBranch);
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
        throw new JavaGitException(404001, ExceptionMessageMap.getMessage("404001")
            + "  The git-branch error message:  { " + errorMessage.toString() + " }");
      }
      return response;
    }
  }
}
