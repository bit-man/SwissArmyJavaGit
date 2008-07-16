package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
 
  /**
   * Patterns for matching lines for deleted files, modified files, new files and empty lines.
   */
  private enum Patterns {
    DELETED("^#\\s+deleted:\\s+\\w+"), MODIFIED("^#\\s+modified:\\s+\\w+"), 
    NEW_FILE("^#\\s+new file:\\s+\\w+"), EMPTY_HASH_LINE("^#\\s*$");

    String pattern;

    Patterns(String pattern) {
      this.pattern = pattern;
    }

    boolean matches(String line) {
      return line.matches(this.pattern);
    }
  }
  
  private File inputFile = null;

  /**
   * This method returns <code>GitStatusResponse</code> object after parsing the options and then
   * executing the &lt;git-status&gt; command.
   * 
   * @param repositoryPath
   *          Path to repository
   * @param GitStatusOptions
   *          Options passed to &lt;git-status&gt; command
   * @param paths
   *          List of files or file-pattern
   */
  public GitStatusResponse status(File repositoryPath, GitStatusOptions options, List<File> paths)
      throws JavaGitException, IOException {
    CheckUtilities.checkNullArgument(repositoryPath, "RepositoryPath");
    CheckUtilities.checkFileValidity(repositoryPath);
    List<String> command  = buildCommandLine(options, paths);
    GitStatusParser parser;
    if(inputFile != null) {
      parser = new GitStatusParser(inputFile);
    }
    else {
      parser = new GitStatusParser();
    }
    return (GitStatusResponseImpl) ProcessUtilities.runCommand(repositoryPath.getAbsolutePath(), command, parser);
  }

  /**
   * Parses options provided by the <code>GitStatusOptions</code> object and adds them to the command. 
   * 
   * @param options
   *          <code>GitStatusOptions</code> provided by &lt;gitclipse&gt;.
   * @param paths
   *          List of file paths.
   * @return
   *          command to be executed.
   */
  private List<String> buildCommandLine(GitStatusOptions options, List<File> paths) {
    List<String> argsList = new ArrayList<String>();

    argsList.add("git");
    argsList.add("status");
    
    if ( options != null ) {
      setOptions( argsList, options );
    }
    
    if ( paths != null ) {
      if((options != null) && options.isOptCheckInputFileOnly() && (paths.size() == 1)) {
        inputFile = paths.get(0);
      }
      else {
        setPaths( argsList, paths);
      }
    }
    
    return argsList;
  }
  
  private void setOptions( List<String> argsList, GitStatusOptions options ) {
    if (options.isOptAll()) {
      argsList.add("-a");
    }
    if (options.isOptQuiet()) {
      argsList.add("-q");
    }
    if (options.isOptVerbose()) {
      argsList.add("-v");
    }
    if (options.isOptSignOff()) {
      argsList.add("-s");
    }
    if (options.isOptEdit()) {
      argsList.add("-e");
    }
    if (options.isOptInclude()) {
      argsList.add("-i");
    }
    if (options.isOptOnly()) {
      argsList.add("-o");
    }
    if (options.isOptNoVerify()) {
      argsList.add("-n");
    }
    if (options.isOptUntrackedFiles()) {
      argsList.add("--untracked-files");
    }
    if (options.isOptAllowEmpty()) {
      argsList.add("--allow-empty");
    }
    if (!options.isMessageNull()) {
      argsList.add("-m");
      argsList.add(options.getMessage());
    }
    if (!options.isOptReadFromLogFileNull()) {
      argsList.add("-F");
      argsList.add(options.getOptReadFromLogFile().getName());
    }
    if (!options.isAuthorNull()) {
      argsList.add("--author");
      argsList.add(options.getAuthor());
    }
  }
  
  private void setPaths( List<String> argsList, List<File> paths) {
    if ( paths == null ) {
      return;
    }
    for( File file: paths) {
      argsList.add( file.getAbsolutePath());
    }
  }

  public static class GitStatusParser implements IParser {

    private enum State {
      FILES_TO_COMMIT, NOT_UPDATED, UNTRACKED_FILES
    }
    private State outputState;
    private int lineNum;
    private GitStatusResponseImpl response;
    private File inputFile = null;

    public GitStatusParser() {
      lineNum = 0;
      response = new GitStatusResponseImpl();
    }
    
    public GitStatusParser(File in) {
      inputFile = in;
      lineNum = 0;
      response = new GitStatusResponseImpl();
    }
    
    public void parseLine(String line) {
      if (line == null || line.length() == 0) {
        return;
      }
      ++lineNum;
      parseForError(line);
      if (lineNum == 1) {
        parseLineOne(line);
      } else {
        parseOtherLines(line);
      }
    }

    /*
     * line1 always start with a '#' and contains the branch name.
     */
    private void parseLineOne(String line) {
      if (!line.startsWith("#")) {
        return;
      }
      String branch = getBranch(line);
      if (branch != null) {
        response.setBranch(getBranch(line));
      }
    }

    private void parseOtherLines(String line) {
      if (!(line.charAt(0) == '#')) {
        response.setMessage(line);
        return;
      }
      if (line.contains("Changes to be committed")) {
        outputState = State.FILES_TO_COMMIT;
        return;
      } else if (line.contains("Changed but not updated")) {
        outputState = State.NOT_UPDATED;
        return;
      } else {
        if (line.contains("Untracked files")) {
          outputState = State.UNTRACKED_FILES;
          return;
        }
      }
      if (ignoreOutput(line)) {
        return;
      }
      if (matchDeletedFilePattern(line)) {
        String deletedFile = getFilename(line);
        if((inputFile != null) && (!deletedFile.matches(inputFile.getName())))
            return;
        addDeletedFile(deletedFile);
        return;
      }
      if (matchModifiedFilePattern(line)) {
        String modifiedFile = getFilename(line);
        if((inputFile != null) && (!modifiedFile.matches(inputFile.getName())))
            return;
        addModifiedFile(modifiedFile);
        return;
      }
      if (matchNewFilePattern(line)) {
        String newFile = getFilename(line);
        if((inputFile != null) && (!newFile.matches(inputFile.getName())))
            return;
        addNewFile(newFile);
        return;
      }
      if (outputState == State.UNTRACKED_FILES) {
        String untrackedFile = getFilename(line);
        if((inputFile != null) && (!untrackedFile.matches(inputFile.getName())))
            return;
        addUntrackedFile(untrackedFile);
      }
    }

    private void parseForError(String line) {
      if (line.startsWith("fatal") || line.startsWith("Error") || line.startsWith("error")) {
        response.setError(line);
      }
    }

    private void addNewFile(String filename) {
      if (outputState == State.FILES_TO_COMMIT) {
        response.addToNewFilesToCommit(filename);
      }
    }

    private void addDeletedFile(String filename) {
      switch (outputState) {
      case FILES_TO_COMMIT:
        response.addToDeletedFilesToCommit(filename);
        break;

      case NOT_UPDATED:
        response.addToDeletedFilesNotUpdated(filename);
        break;
      }
    }

    private void addModifiedFile(String filename) {
      switch (outputState) {
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
      if (Patterns.DELETED.matches(line)) {
        return true;
      }
      return false;
    }

    public boolean matchModifiedFilePattern(String line) {
      if (Patterns.MODIFIED.matches(line)) {
        return true;
      }
      return false;
    }

    public boolean matchNewFilePattern(String line) {
      if (Patterns.NEW_FILE.matches(line)) {
        return true;
      }
      return false;
    }

    public boolean matchEmptyHashLinePattern(String line) {
      if (Patterns.EMPTY_HASH_LINE.matches(line)) {
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
      while (scanner.hasNext()) {
        filename = scanner.next();
      }
      return filename;
    }

    private boolean ignoreOutput(String line) {
      if (line.contains("(use \"git reset")) {
        return true;
      }
      if (line.contains("(use \"git add ")) {
        return true;
      }
      if (line.contains("(use \"git add/rm")) {
        return true;
      }
      if (matchEmptyHashLinePattern(line)) {
        return true;
      }
      return false;
    }

    public GitStatusResponse getResponse() {
      return response;
    }
  }
}
