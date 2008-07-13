package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.api.GitFileSystemObject;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitMvOptions;
import edu.nyu.cs.javagit.api.commands.GitMvResponse;
import edu.nyu.cs.javagit.client.IGitMv;
import edu.nyu.cs.javagit.utilities.CheckUtilities;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

/**
 * Command-line implementation of the <code>IGitMv</code> interface.
 */
public class CliGitMv implements IGitMv {
  // Variable, which if set the fatal messages are not considered for throwing exceptions.
  private boolean dryRun;

  public GitMvResponse mv(File repoPath, File source, File destination)
      throws IOException, JavaGitException {
    return mvProcess(repoPath, null, source, destination);
  }
  public GitMvResponse mv(File repoPath, GitMvOptions options, File source, File destination)
      throws IOException, JavaGitException {
    return mvProcess(repoPath, options, source, destination);
  }
  public GitMvResponse mv(File repoPath, GitFileSystemObject source, GitFileSystemObject 
      destination) throws IOException,JavaGitException {
    return mvProcess(repoPath, null, source.getFile(), destination.getFile());
  }
  public GitMvResponse mv(File repoPath, GitMvOptions options, GitFileSystemObject source, 
      GitFileSystemObject destination) throws IOException, JavaGitException {
    return mvProcess(repoPath, options, source.getFile(), destination.getFile());
  }
  
  /**
   * Exec of git-mv command
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
  public GitMvResponse mvProcess(File repoPath, GitMvOptions options, File source,
      File destination) throws IOException, JavaGitException {

    CheckUtilities.checkNullArgument(repoPath, "repository path");
    CheckUtilities.checkNullArgument(source, "source");
    CheckUtilities.checkNullArgument(destination, "destination");
    
    List<String> commandLine = buildCommand(options, source.getAbsolutePath(), 
        destination.getAbsolutePath());
    GitMvParser parser = new GitMvParser();

    return (GitMvResponse) ProcessUtilities.runCommand(repoPath.getAbsolutePath(), commandLine, 
        parser);
  }
  /**
   * Builds a list of command arguments to pass to <code>ProcessBuilder</code>.
   * 
   * @param options
   *          The options to include on the command line.
   * @param source
   *          The source file/directory/symlink to rename/move.
   * @param destination
   *          The destination file/directory/symlink to rename/move to.
   * @return A list of the individual arguments to pass to <code>ProcessBuilder</code>.
   */
  protected List<String> buildCommand(GitMvOptions options, String source, String destination) {
    List<String> cmd = new ArrayList<String>();
    cmd.add("git-mv");
    if (null != options) {
      if (options.isOptF()) {
        cmd.add("-f");
      }
      if (options.isOptK()) {
        cmd.add("-k");
      }
      if (options.isOptN()) {
        cmd.add("-n");
        setDryRun(true);
      }
    }
    cmd.add(source);
    cmd.add(destination);
    return cmd;
  }

  /**
   * Implementation of the <code>IParser</code> interface in GitMvParser class.
   */
  public class GitMvParser implements IParser {

    // The response object for an mv operation.
    private GitMvResponse response = null;

    // While handling the error cases this buffer will have the error messages.
    private StringBuffer errorMessage = null;

    /**
     * Parses the line from the git-mv response text.
     * 
     * @param line
     *          The line of text to process.
     * 
     */
    public void parseLine(String line) {
      if (null != errorMessage) {
        errorMessage.append("\n" + line);
        return;
      }
      if (line.startsWith("error") || line.startsWith("fatal")) {
        if (null == errorMessage) {
          errorMessage = new StringBuffer();
        }
        errorMessage.append(line);
      } else {
        if (null == response) {
          response = new GitMvResponse();
        }
        // This is to parse the output when -n or -f options were given
        if (null != line) {
          parseLineForSuccess(line);
        }
      }
    }

    /**
     * Parses the line for successful execution.
     * 
     * @param line
     *          The line of text to process.
     */
    public void parseLineForSuccess(String line) {
      if (line.contains("Warning:")) {
        response.addComment(line);
      }
      if(line.contains("Adding")) {
        response.setDestination(line.substring(11));
      }
      if(line.contains("Deleting")) {
        response.setSource(line.substring(11));
      }
    }

    /**
     * Throws appropriate JavaGitException for an error case or returns the GitMvResponse object to
     * the upper layer.
     * 
     * @return GitMvResponse object.
     * @throws JavaGitException
     */
    public GitMvResponse getResponse() throws JavaGitException {
      if (null != errorMessage) {
        if (isDryRun()) {
          throw new JavaGitException(424001, ExceptionMessageMap.getMessage("424001")
              + errorMessage.toString());
        } else {
          throw new JavaGitException(424000, ExceptionMessageMap.getMessage("424000")
              + errorMessage.toString());
        }
      }
      return response;
    }
  }

  /**
   * @return the dryRun
   */
  public boolean isDryRun() {
    return dryRun;
  }

  /**
   * @param dryRun
   *          the dryRun to set
   */
  public void setDryRun(boolean dryRun) {
    this.dryRun = dryRun;
  }
}
