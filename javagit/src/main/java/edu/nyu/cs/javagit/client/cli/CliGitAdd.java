package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitAddResponse;
import edu.nyu.cs.javagit.client.IGitAdd;
import edu.nyu.cs.javagit.utilities.CheckUtilities;


/**
 * Command-line implementation of the <code>IGitAdd</code> interface.
 * 
 * 
 * TO RESOLVE / QUESTIONS
 * 1. How to handle '-i or --interactive' option ? 
 * 2. Do we need this option ?`
 * 3. Similarly, '-p' options need interactive access 
 *    to the git-add process.
 * 
 */
public class CliGitAdd implements IGitAdd {
  
  public static final String GIT_ADD = "git-add";
  public static final String GIT = "git";
  public static final String ADD = "add";
  public static final String DRY_RUN_ARG = "-n";

  public GitAddResponse add( String directory, String[] args ) throws IOException {
    doValidityChecks(directory, args);
    
    System.out.println( "Command: " + getCommandString(args));
    ProcessBuilder pb = new ProcessBuilder( args );
    pb.directory(new File(directory));
    pb.redirectErrorStream(true);

    GitAddParser parser = new GitAddParser();
    parser.setOptions(args);

    Process p = ProcessUtilities.startProcess(pb);
    ProcessUtilities.getProcessOutput(p, parser);
    ProcessUtilities.waitForAndDestroyProcess(p);

    return parser.getResponse();
  }
  
  /**
   * returns the command "git-add" or "git" after separating out the 
   * complete path e.g. /usr/local/bin/git-add or /usr/local/bin/git
   * in-case absolute path is used.
   * Assumes that the parameter assigned is not null and is valid.
   * @param command
   */
  private String getArg0(String command) {
    String extractedCommand = command;
    if ( command.contains( System.getProperty("file.separator"))) {
      StringTokenizer st = new StringTokenizer(command, System.getProperty("file.separator"));
      int count = st.countTokens();
      while( count > 0 && st.hasMoreTokens() ) {
        extractedCommand = st.nextToken();
      }
      return extractedCommand;
    }
    return command;
  }
  
  /**
   * Simple tests to validate the paths and filename given for adding
   * to the repository
   * @param repositoryPath
   *           Path to the repository
   * @param filename
   *           Filename to be added to the repository
   * @throws IOException
   *           IOException thrown if the path or filename is not valid.
   * 
   */
  private void doValidityChecks(String directory, String[] args) 
                   throws IOException, IllegalArgumentException {
    
    // Throw exception if git command has no arguments
    if ( args.length == 0 ) {
      throw new IllegalArgumentException("Need to provide command atleast");
    }
    
    // Throw exception if git command does not start with "git-add" OR "git add"
    if ( ! getArg0(args[0]).equals(GIT_ADD) && ! getArg0(args[0]).equals(GIT)) {
      throw new IllegalArgumentException("Illegal command name: " + args[0]);
    }
    
    // If the command starts with "git" then the next argument should be "add"
    if ( getArg0(args[0]).equals( GIT ))
    {
      if ( args.length < 2 || ! args[1].equals(ADD)) {
        throw new IllegalArgumentException("Illegal argument count or arg 2 is not \"" + ADD +"\"" );
      }
    }
    
    CheckUtilities.checkStringArgument(directory, "Working Directory");
    
    // Making sure that none of the arguments are null
    for( String arg : args ) {
      CheckUtilities.checkStringArgument( arg, "Command line arguments");
    }
  }
  
  /**
   * Debugging only -
   * Converts the arguments supplied to add method into a valid command string.
   * @return
   */
  private String getCommandString( String[] args ) {
    StringBuilder sb = new StringBuilder();
    for(int i=0; i < args.length; i++ ) {
      sb.append(args[i]);
      if ( i < args.length - 1 ) {
        sb.append(" ");
      }
    }
    return sb.toString();
  }
  
  public class GitAddParser implements IParser {
    
    private int lineNum;
    
    private GitAddResponse response;
    
    public GitAddParser() {
      lineNum = 0;
      response = new GitAddResponse();
    }
    
    public void parseLine(String line)  {
      if ( line == null && line.length() == 0 ) {
        return;
      }
      lineNum++;
      switch( lineNum ) {
        case 1:
          processLine1(line);
          break;
        case 2:
          processLine2(line);
          break;
          
        default:
          processLine(line);
      }
    }
    
    private void processLine1( String line ){
      if ( line == null || line.length() == 0 ) {
        return;
      }
      
      response.setNoOutput(false);
      if ( ! line.startsWith("Nothing specified") && 
          ! line.contains("nothing added") &&
          ! line.contains("No changes")) {
        processLine(line);
      }
        
    }
    
    private void processLine2( String line ) {
      if ( line.startsWith("Maybe you wanted to say")) {
        // ignore line
        return;
      }
      processLine(line);
    }
    
    //-----------------------------------------------------------------
    //TODO - the file is enclosed by single-quotes. Should these quotes
    //TODO - be removed before adding the file to response?
    //-----------------------------------------------------------------
    /**
     * Lines that start with "add" have the second token as the name
     * of the file added by git-added.
     * The file_path added is enclosed in single quotes. I am currently
     * not removing those single quotes as the file_paths that are added
     * may contain files with spaces in it, so it's convenient to use
     * extract those files with single quotes already present. ( Thoughts? )
     * 
     * @param line
     */
    private void processLine( String line ){
      if ( line.trim().startsWith("fatal") || line.trim().startsWith("error")) {
        //throw new JavaGitException(0, line);
        response.add(line);
        return;
      }
      
      if ( line.startsWith("add")) {
        StringTokenizer st = new StringTokenizer(line);
        
        if ( st.nextToken().equals("add") && st.hasMoreTokens() ) {
          response.add(st.nextToken());
        }
      } 
      else {
        processSpaceDelimitedFilePaths(line);
      }
    }
    
    private void setOptions(String[] args) {
      
      List<String> options = new ArrayList<String>();
      for ( int i=0; i < args.length; i++ ) {
        if( args[i].startsWith("-")) {
          options.add(args[i]);
          
          if ( args[i].equalsIgnoreCase(DRY_RUN_ARG)) {
            response.setAction(GitAddResponse.Action.DRY_RUN_ADD);
          }
        }
      }
      response.setOptions(options);
    }

    /**
     * git-add -v generates output with multiple file-paths on the
     * same line delimited by spaces.
     * @param line
     */
    private void processSpaceDelimitedFilePaths(String line) {
      StringTokenizer st = new StringTokenizer(line);
      while( st.hasMoreTokens() ) {
        String filepath = st.nextToken();
        response.add(filepath);
      }
    }
    
    public GitAddResponse getResponse()
    {
      return response;
    }
  }
}
