package edu.nyu.cs.javagit.client.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitStatusOptions;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.client.GitStatusResponseImpl;
import edu.nyu.cs.javagit.client.IGitStatus;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * Command-line implementation of the <code>IGitStatus</code> interface.
 */
public class CliGitStatus implements IGitStatus {
  
  private static final String GIT_STATUS = "git-status";
  
  public GitStatusResponse status(String repositoryPath, GitStatusOptions options, String paths)
      throws JavaGitException, IOException {
    List<String> argsList = new ArrayList<String>();
    CheckUtilities.checkStringArgument(repositoryPath, "RepositoryPath");
    argsList.add(GIT_STATUS);
    parseAndAddOptions( options, argsList );
    if( paths != null && paths.length() > 0 ) {
      argsList.add(paths);
    }
    //printCommand(argsList);
    return status( repositoryPath, argsList);
  }
  
  private GitStatusResponse status(String directory, List<String> args) 
    throws JavaGitException, IOException {
    GitStatusParser parser = new GitStatusParser();
    return(GitStatusResponseImpl)ProcessUtilities.runCommand(directory, args, parser);
  }
  
  /**
   * Parses options provided by the <code>GitStatusOptions</code> object and 
   * adds them to the list of String <argList>. If there are no options to be
   * provided to <git-status> command, then null is returned.
   * 
   * @param options <code>GitStatusOptions</code> provided by gitclipse.
   * @param argsList List that will contain all the parsed options.
   */
  private void parseAndAddOptions( GitStatusOptions options, List<String> argsList ) {
    if( options == null ) {
      return;
    }
    if ( options.isOptAll() ) {
      argsList.add("-a");
    }
    if ( options.isOptQuite() ) {
      argsList.add("-q");
    }
    if ( options.isOptVerbose() ) {
      argsList.add("-v");
    }
    if ( options.isOptSignOff() ) {
      argsList.add("-s");
    }
    if ( options.isOptEdit() ) {
      argsList.add("-e");
    }
    if ( options.isOptInclude() ) {
      argsList.add("-i");
    }
    if ( options.isOptOnly() ) {
      argsList.add("-o");
    }
    if ( options.isOptNoVerify() ) {
      argsList.add("-n");
    }
    if ( options.isOptUntrackedFiles() ) {
      argsList.add("--untracked-files");
    }
    if ( options.isOptAllowEmpty() ) {
      argsList.add("--allow-empty");
    }
    if ( ! options.isMessageNull() ) {
      argsList.add( "-m " + options.getMessage() );
    }
    if ( ! options.isOptReadFromLogFileNull() ) {
      argsList.add("-F " + options.getOptReadFromLogFile() );
    }
    if ( ! options.isTemplateFileNull() ) {
      argsList.add("-t " + options.getTemplateFile() );
    }
    if ( ! options.isAuthorNull() ) {
      argsList.add("--author" + options.getAuthor() );
    }
    if ( ! options.isReEditMsgNull() ) {
      argsList.add("-c " + options.getReEditMsg() );
    }
    if ( ! options.isReUseMsgNull() ) {
      argsList.add("-C " + options.getReUseMsg() );
    }
  }
  
  public static class GitStatusParser implements IParser {
    
    public static final String FATAL_ERROR_WITH_A_OPTION = "fatal: Paths with -a does not make sense";
    public enum State { FILES_TO_COMMIT, NOT_UPDATED, UNTRACKED_FILES };
    
    private State outputState;
    private int lineNum;
    private GitStatusResponseImpl response;
    
    public GitStatusParser() {
      lineNum = 0;
      response = new GitStatusResponseImpl();
    }
    
    public void parseLine(String line) {
      if(line == null || line.length() == 0 ) {
        return;
      }
      ++lineNum;
      parseForError(line);
      if ( lineNum == 1 ) {
        parseLineOne(line);
      } else {
        parseOtherLines(line);
      }
    }
    
    /*
     * line1 always start with a '#' and contains 
     * the branch name.
     */
    private void parseLineOne(String line) {
      if ( ! line.startsWith("#")) {
        return;
      }
      String branch = getBranch(line);
      if ( branch != null ) {
        response.setBranch(getBranch(line));       
      }
    }
    
    private void parseOtherLines(String line) {
      if( ! (line.charAt(0) == '#')) {
        response.setMessage(line);
        return;
      }
      if ( line.contains("Changes to be committed")) {
        outputState = State.FILES_TO_COMMIT;
        return;
      } else if (line.contains("Changed but not updated") ) {
        outputState = State.NOT_UPDATED;
        return;
      } else {
        if ( line.contains("Untracked files")) {
          outputState = State.UNTRACKED_FILES;
          return;
        }
      }
      if ( ignoreOutput(line) ) {
        return;
      }
      if ( matchDeletedFilePattern(line) ) {
          String deletedFile = getFilename(line);
          addDeletedFile(deletedFile);
          return;
      }
      if( matchModifiedFilePattern(line) ) {
        String modifiedFile = getFilename(line);
        addModifiedFile(modifiedFile);
        return;
      }
      if( matchNewFilePattern(line) ) {
        String newFile = getFilename(line);
        addNewFile(newFile);
        return;
      }
      if( outputState == State.UNTRACKED_FILES ) {
        String untrackedFile = getUntrackedFile(line);
        addUntrackedFile(untrackedFile);
      }
    }
    
    private void parseForError(String line) {
      if ( line.equalsIgnoreCase(FATAL_ERROR_WITH_A_OPTION)) {
        response.setError(FATAL_ERROR_WITH_A_OPTION);
      }
    }
    
    private void addNewFile(String filename) {
      if(outputState == State.FILES_TO_COMMIT) {
        response.addToNewFilesToCommit(filename);
      }
    }
    
    private void addDeletedFile(String filename) {
      switch(outputState) {
      case FILES_TO_COMMIT:
        response.addToDeletedFilesToCommit(filename);
        break;
      
      case NOT_UPDATED:
        response.addToDeletedFilesNotUpdated(filename);
        break;
      }     
    }
    
    private void addModifiedFile(String filename) {
      switch(outputState) {
      case FILES_TO_COMMIT:
        response.addToModifiedFilesToCommit(filename);
        break;
      
      case NOT_UPDATED:
        response.addToModifiedFilesNotUpdated(filename);
        break;
      }      
    }
    
    private void addUntrackedFile(String filename) {
      response.addToUntrackedFiles(filename);
    }
    
    public boolean matchDeletedFilePattern(String line) {
      String pattern = "^#\\s+deleted:\\s+\\w+";
      //String pattern = "#\tdeleted: ";
      if ( line.matches(pattern) ) {
        return true;
      }
      return false;
    }
    
    public boolean matchModifiedFilePattern(String line) {
      String pattern = "^#\\s+modified:\\s+\\w+";
      //String pattern = "#\tmodified: ";
      if ( line.matches(pattern) ) {
        return true;
      }
      return false;
    }
    
    public boolean matchNewFilePattern(String line) {
      String pattern = "^#\\s+new file:\\s+\\w+";
      //String pattern = "#\tnew file: ";
      if ( line.matches(pattern) ) {
        return true;
      }
      return false;
    }
    
    public boolean matchEmptyHashLinePattern(String line) {
      String pattern = "^#\\s*$";
      if ( line.matches(pattern)) {
        return true;
      }
      return false;
    }
    
    private String getBranch(String line) {
      StringTokenizer st = new StringTokenizer(line);
      String last = null;
      while (st.hasMoreTokens()) {
        last = st.nextToken();
      }
      return last;
    }
    
    public String getFilename(String line) {
      String filename = null;
      Scanner scanner = new Scanner(line);
      while( scanner.hasNext() ) {
        filename = scanner.next();
      }
      return filename;
    }
    
    public String getUntrackedFile(String line){
      return getFilename(line);
    }
    
    private boolean ignoreOutput(String line) {
      if ( line.contains("(use \"git reset") ) {
        return true;
      }
      if ( line.contains("(use \"git add ")) {
        return true;
      }
      if ( line.contains("(use \"git add/rm")) {
        return true;
      }
      if ( matchEmptyHashLinePattern(line)) {
        return true;
      }
      return false;
    }
    
    public GitStatusResponse getResponse() {
      return response;
    }
  }
  
  public void printCommand(List<String> args ) {
    for(int i=0; i < args.size(); i++ ) {
      System.out.print(args.get(i) + " ");
    }
    System.out.println();
  }
}

