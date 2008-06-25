package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;

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
  //Variable, which if set the fatal messages are not considered for throwing exceptions.
  private boolean dryRun;

  public GitMvResponse mv(String repoPath, GitMvOptions options, String source, String destination)
  throws IOException, JavaGitException {
    return mvProcess(repoPath, options, source, destination);
  }
  public GitMvResponse mv(String repoPath, String source, String destination) throws IOException, 
      JavaGitException {
    return mvProcess(repoPath, null, source, destination);
  }
  /**
   * Exec of git-mv command
   * 
   * @param repositoryPath
   *          The path to the repository, to be treated as root folder for git-mv operation. A 
   *          non-zero length argument is required for this parameter, otherwise a 
   *          <code>NullPointerException</code> or <code>IllegalArgumentException</code> 
   *          will be thrown.
   * @param options
   *          The options to git-mv command.
   * @param source
   *          The source file/folder/symlink which is to be renamed or moved to a different 
   *          location. A non-zero length argument is required for this parameter, otherwise a 
   *          <code>NullPointerException</code> or <code>IllegalArgumentException</code> 
   *          will be thrown.
   * @param destination
   *          The destination file/folder/symlink which the source is renamed or moved to. 
   *          A non-zero length argument is required for this parameter, otherwise a 
   *          <code>NullPointerException</code> or <code>IllegalArgumentException</code> 
   *          will be thrown.
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
  public GitMvResponse mvProcess(String repoPath, GitMvOptions options, String source, 
      String destination) throws IOException, JavaGitException {
    
    CheckUtilities.checkStringArgument(repoPath, "repository path");
    CheckUtilities.checkStringArgument(source, "source");
    CheckUtilities.checkStringArgument(destination, "destination");
    
    ProcessBuilder pb = null;
    StringBuffer optionList = new StringBuffer("-");
    if (options.isOptF()) {
      optionList.append("f");
    }
    if (options.isOptK()) {
      optionList.append("k");
    }
    if (options.isOptN()) {
      optionList.append("n");
      setDryRun(true);
    }
    if (null == options) {
      pb = new ProcessBuilder("git-mv", source, destination);
    }
    else {
      CheckUtilities.checkStringArgument(optionList.substring(1), "options");
      pb = new ProcessBuilder("git-mv", optionList.toString(), source, destination);
    }
    pb.directory(new File(repoPath));
    pb.redirectErrorStream(true);

    GitMvParser parser = new GitMvParser();
    Process p = ProcessUtilities.startProcess(pb);
    ProcessUtilities.getProcessOutput(p, parser);
    ProcessUtilities.waitForAndDestroyProcess(p);
    return parser.getResponse();
  }
  
  /**
   * Implementation of the <code>IParser</code> interface in GitMvParser class.
   */
  public class GitMvParser implements IParser {

    // The response object for an mv operation.
    private GitMvResponse response;
    
    // While handling the error cases this buffer will have the error messages.
    private StringBuffer errorMessage = null;
    
    //Varible showing the status of the git-mv command.
    private boolean status;
    
    /**
     * Parses the line from the git-mv response text.
     * 
     * @param line
     *          The line of text to process.
     *      
     */
    public void parseLine(String line) {
      if(line.startsWith("error")) {
        status = false;
        int colonIndex = line.indexOf(':');
        errorMessage.append(line.substring(colonIndex+2));
      }
      else if (line.startsWith("fatal") && (false == isDryRun())) {
        status = false;
    	  handleErrorCase(line);
      }
      else {
        status = true;
        response = new GitMvResponse(status);
	      //This is to parse the output when -n or -f options were given
	      if (null != line) {
	        parseLineForSuccess(line);
	      }
      } 
    }
    
    /**
     * Handles the case when there was an error executing the git-mv command.
     * 
     * @param line
     *          The line of text to process.
     */
    public void handleErrorCase(String line) {
      errorMessage = new StringBuffer();
      int colonIndex = line.indexOf(':');
      response = new GitMvResponse(status); 
      errorMessage.append(line.substring(colonIndex+2));
    }
    
    /** 
     * Parses the line for successful execution.
     * 
     * @param line
     *          The line of text to process.
     */
    public void parseLineForSuccess(String line) {
      String comment;
      String source;
      String destination;
      if (line.contains("Warning:") || line.contains("fatal")) {
        int colonIndex = line.indexOf(':'); 
        comment = line.substring(colonIndex+2);
        response.addComment(comment);
      }
      else if (line.contains("Checking rename of")) {
        response.addComment(line + "\n");
	      int beginIndex = line.indexOf('\'')+1;
	      int endIndex = line.indexOf('\'', beginIndex);
	      source = line.substring(beginIndex, endIndex);
	      response.setSource(source);
		
	      beginIndex = line.indexOf('\'', endIndex)+1;
	      endIndex = line.indexOf('\'', beginIndex);
	      destination = line.substring(beginIndex, endIndex);
	      response.setDestination(destination);
      }
      else {
        response.addComment(line + "\n");
      }
    }
    
    /**
     * Throws appropriate JavaGitException for an error case or returns the GitMvResponse
     * object to the upper layer.
     * 
     * @return
     *       GitMvResponse object.
     * @throws JavaGitException
     */
    public GitMvResponse getResponse() throws JavaGitException {
      if (null != errorMessage) {
        throw new JavaGitException(425001, ExceptionMessageMap.getMessage("425001")
            + "  The git-mv error message:  { " + errorMessage.toString() + " }");
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
   * @param dryRun the dryRun to set
   */
  public void setDryRun(boolean dryRun) {
    this.dryRun = dryRun;
  }
}
