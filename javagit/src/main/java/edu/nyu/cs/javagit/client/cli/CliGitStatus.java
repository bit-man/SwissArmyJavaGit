/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;
import java.util.*;

import edu.nyu.cs.javagit.api.JavaGitConfiguration;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitStatusOptions;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.client.GitStatusResponseImpl;
import edu.nyu.cs.javagit.client.IGitStatus;
import edu.nyu.cs.javagit.utilities.CheckUtilities;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

/**
 * Command-line implementation of the <code>IGitStatus</code> interface.
 * 
 * TODO - Need to parse -v option in a better way. Currently <code>GitStatusResponse</code>
 * does not save any output related to -v options such as lines containing diffs, or +++ etc.
 */
public class CliGitStatus implements IGitStatus {

  /**
   * Patterns for matching lines for deleted files, modified files, new files and empty lines.
   */
  public static enum Patterns {
    DELETED("^#\\s+deleted:\\s+.*"), 
    MODIFIED("^#\\s+modified:\\s+.*"), 
    NEW_FILE("^#\\s+new file:\\s+.*"), 
    EMPTY_HASH_LINE("^#\\s*$"),
    RENAMED("^#\\s+renamed:\\s+.*");

    String pattern;

    Patterns(String pattern) {
      this.pattern = pattern;
    }

    public boolean matches(String line) {
      return line.matches(this.pattern);
    }
  }

  private File inputFile = null;

  /**
   * Implementation of <code>IGitStatus</code> method for getting the status of a list of files
   * 
   */
  public GitStatusResponse status(File repositoryPath, GitStatusOptions options, List<File> paths)
      throws JavaGitException, IOException {
    CheckUtilities.checkNullArgument(repositoryPath, "RepositoryPath");
    CheckUtilities.checkFileValidity(repositoryPath);
    List<String> command = buildCommandLine(options, paths);
    GitStatusParser parser;
    if (inputFile != null) {
      parser = new GitStatusParser(repositoryPath.getPath() + File.separator, inputFile);
    } else {
      parser = new GitStatusParser(repositoryPath.getPath() + File.separator);
    }
    GitStatusResponse response = (GitStatusResponseImpl) ProcessUtilities.runCommand(repositoryPath,
        command, parser);
    return response;
  }
  
  /**
   * Implementation of <code>IGitStatus</code> method for getting the status of a file.
   */
  public GitStatusResponse status(File repositoryPath, GitStatusOptions options, File file)
  throws JavaGitException, IOException {
    List<File> paths = new ArrayList<File>();
    paths.add(file);
    return status(repositoryPath, options, paths);
  }
  
  /**
   * Implementation of <code>IGitStatus</code> method with only options passed to &lt;git-status&gt; command.
   */
  public GitStatusResponse status(File repositoryPath, GitStatusOptions options)
  throws JavaGitException, IOException {
    List<File> paths = null;
    return status( repositoryPath, options, paths);
  }
  
  /**
   * Implementation of <code>IGitStatus</code> method with file-paths passed to &lt;git-status&gt; command.
   */
  public GitStatusResponse status(File repositoryPath, List<File> paths) throws JavaGitException,
  IOException {
    return status(repositoryPath, null, paths);
  }
  
  /**
   * Implementation of <code>IGitStatus</code> method for getting the status of repository
   * with no options or files provided.
   */
  public GitStatusResponse status(File repositoryPath) throws JavaGitException, IOException {
    GitStatusOptions options = null;
    List<File> paths = null;
    return status(repositoryPath, options, paths);
  }
  
  /**
   * Implementation of <code>IGitStatus</code> method with options set to all(-a)
   */
  public GitStatusResponse statusAll(File repositoryPath) throws JavaGitException, IOException {
    GitStatusOptions options = new GitStatusOptions();
    options.setOptAll(true);
    return status(repositoryPath, options);
  }
  
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
    throws JavaGitException, IOException {
    CheckUtilities.checkNullArgument(repositoryPath, "RepositoryPath");
    CheckUtilities.checkFileValidity(repositoryPath);
    List<String> command  = buildCommandLine(options, null);
    GitStatusParser parser = new GitStatusParser(repositoryPath.getPath() + File.separator,
        file);

    return (GitStatusResponseImpl) ProcessUtilities.runCommand(repositoryPath, command, parser);
  }
  
  /**
   * Parses options provided by the <code>GitStatusOptions</code> object and adds them to the
   * command.
   * 
   * @param options
   *          <code>GitStatusOptions</code> provided by &lt;gitclipse&gt;.
   * @param paths
   *          List of file paths.
   * @return command to be executed.
   */
  private List<String> buildCommandLine(GitStatusOptions options, List<File> paths) {
    List<String> command = new ArrayList<String>();

    command.add(JavaGitConfiguration.getGitCommand());
      command.add("status");
      command.add("--porcelain");

    if (options != null) {
      setOptions(command, options);
    }

    if (paths != null) {
      for (File file : paths) {
        command.add(file.getPath());
      }
    }
    return command;
  }

  private void setOptions(List<String> argsList, GitStatusOptions options) {
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
    if (!options.isOptReadFromLogFileNull()) {
      argsList.add("-F");
      argsList.add(options.getOptReadFromLogFile().getPath());
    }
    if (!options.isAuthorNull()) {
      argsList.add("--author");
      argsList.add(options.getAuthor());
    }
  }

  public static class GitStatusParser implements IParser {

      private static Map<Tuple<PorcelainField, PorcelainField>, XYSolver> solver = new HashMap<Tuple<PorcelainField, PorcelainField>, XYSolver>();

      static {

          // FIXME PorcelainParseResult must be used to fill GitStatusResponse
          solver.put(Tuple.create(PorcelainField.UNTRACKED, PorcelainField.UNTRACKED),
                                    new UntrackedXYSolver());
          solver.put(Tuple.create(PorcelainField.IGNORED, PorcelainField.IGNORED),
                                    new IgnoredXYSolver());
      }

      private enum State {
          FILES_TO_COMMIT, NOT_UPDATED, UNTRACKED_FILES
      }

    private State outputState;
    private GitStatusResponseImpl response;
    private File inputFile = null;
    
    // The working directory for the command that was run.
    private String workingDirectory;

    public GitStatusParser(String workingDirectory) {
      this.workingDirectory = workingDirectory;
      response = new GitStatusResponseImpl(workingDirectory);
    }

    public GitStatusParser(String workingDirectory, File in) {
      this.workingDirectory = workingDirectory;
      inputFile = in;
      response = new GitStatusResponseImpl(workingDirectory);
    }

      public void parseLine(String line) throws PorcelainParseWrongFormatException {
          if (line == null || line.length() == 0) {
              return;
          }

          PorcelainParseResult p = new PorcelainParser(line).parse();
          solver.get(p.getFields()).solve(response, p);
      }

    private void addNewFile(String filename) {
      response.addToNewFilesToCommit(new File(workingDirectory + filename));
    }

    private void addDeletedFile(String filename) {
      File file = new File(workingDirectory + filename);
      switch (outputState) {
      case FILES_TO_COMMIT:
        response.addToDeletedFilesToCommit(file);
        break;

      case NOT_UPDATED:
        response.addToDeletedFilesNotUpdated(file);
        break;
      }
    }

    private void addModifiedFile(String filename) {
      File file = new File(workingDirectory + filename);
      switch (outputState) {
      case FILES_TO_COMMIT:
        response.addToModifiedFilesToCommit(file);
        break;

      case NOT_UPDATED:
        response.addToModifiedFilesNotUpdated(file);
        break;
      }
    }

    private void addRenamedFileToCommit(String renamedFile) {
      response.addToRenamedFilesToCommit(new File(workingDirectory + renamedFile));	
    }
    
    private void addUntrackedFile(String filename) {
      response.addToUntrackedFiles(new File(workingDirectory + filename));
    }

    private String getBranch(String line) {
      StringTokenizer st = new StringTokenizer(line);
      String last = null;
      while (st.hasMoreTokens()) {
        last = st.nextToken();
      }
      return last;
    }

    //

      public String getFilename(String line) {
          String filename = "";
          boolean checkForColon = line.contains(":") && line.contains("#");

          Scanner scanner = new Scanner(line);
          boolean startFileName = false;
          while (scanner.hasNext() && !startFileName) {
              String s = scanner.next();
              // If line contains only # then no deleted: new file: or other comment ending in : mark the file name start
              if (checkForColon)
                  startFileName = s.endsWith(":");
              else
                  startFileName = s.endsWith("#");
          }

          boolean continueFilename = true;
          while (scanner.hasNext() && continueFilename) {
              String s = scanner.next();
              if (s.equals("->")) {
                  filename = line.substring( line.indexOf("->") + 2 ).trim();
                  continueFilename = false;
              } else {
                  String separator = filename == "" ? "" : " ";
                  filename += separator + s;
                  continueFilename = true;
              }

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
      if (Patterns.EMPTY_HASH_LINE.matches(line)) {
        return true;
      }
      return false;
    }

    public void processExitCode(int code) {
    }
    
    public GitStatusResponse getResponse() throws JavaGitException {
      if( response.errorState() ) {
        throw new JavaGitException(438000, ExceptionMessageMap.getMessage("438000") + 
            " - git status error message: { " + response.getError() + " }");
      }
      return response;
    }

      public static class UntrackedXYSolver
              implements XYSolver {

          public void solve(GitStatusResponseImpl response, PorcelainParseResult result) {
              response.addToUntrackedFiles(result.getIndexPath());
          }

      }

      public static class IgnoredXYSolver
              implements XYSolver {

          public void solve(GitStatusResponseImpl response, PorcelainParseResult result) {
              response.addToIgnoredFiles(result.getIndexPath());
          }

      }

      public static class PorcelainParser {
          private final String line;

          public PorcelainParser(String line) {
              this.line = line;
          }

          public PorcelainParseResult parse() throws PorcelainParseWrongFormatException {
              if (line == null ) {
                  throw new PorcelainParseWrongFormatException("NULL line");
              }

              String[] split = line.split(" ", 4);

              if (split.length < 2 ) {
                  throw new PorcelainParseWrongFormatException("Less than 2 fields");
              }
              return (split.length == 2) ?
                      new PorcelainParseResult(parseStatusCode(split[0]),
                              new File(split[1])) :
                      new PorcelainParseResult(parseStatusCode(split[0]),
                              new File(split[1]), new File(split[3]));
          }

          private Tuple<PorcelainField,PorcelainField> parseStatusCode(String s) {
                return Tuple.create(PorcelainField.char2field(s.charAt(0)),
                        PorcelainField.char2field(s.charAt(1)));
          }
      }

      public static enum PorcelainField {
          MODIFIED('M'),
          UNMODIFIED(' '),
          ADDED('A'),
          DELETED('D'),
          RENAMED('R'),
          COPIED('C'),
          UPDATED_BUT_UNMERGED('U'),
          UNTRACKED('?'),
          IGNORED('!');
          private final char c;

          PorcelainField(char c) {
              this.c = c;
          }

          public static PorcelainField char2field(char c) {
              for( PorcelainField p : values()) {
                  if (p.c == c ) {
                      return p;
                  }
              }
              return null;
          }
      }

      public static class PorcelainParseResult {
          private final Tuple<PorcelainField, PorcelainField> fields;

          private final File headPath;
          private final File indexPath;
          public PorcelainParseResult(Tuple<PorcelainField, PorcelainField> fields, File headPath) {
            this(fields, headPath,null);
          }

          public PorcelainParseResult(Tuple<PorcelainField, PorcelainField> fields, File headPath, File indexPath) {
              this.fields = fields;
              this.headPath = headPath;
              this.indexPath = indexPath;
          }

          public Tuple<PorcelainField, PorcelainField> getFields() {
              return fields;
          }

          public File getHeadPath() {
              return headPath;
          }

          public File getIndexPath() {
              return indexPath;
          }
      }

      public static class Tuple<S,T> {
          private S a;

          private T b;

          private Tuple(){};

          private Tuple(S a, T b) {
              this.a = a;
              this.b = b;
          }

          public static <S,T> Tuple<S,T> create(S a, T b) {
              return new Tuple<S, T>();
          }

          public S getA() {
              return a;
          }

          public T getB() {
              return b;
          }

          public boolean equals(Object that) {
              if ( ! (that instanceof Tuple) ) {
                  return super.equals(that);
              }

              Tuple<S,T> o = (Tuple<S,T> ) that;

              if ( o == this) {
                  return true;
              }

              return a.equals(o.a) && b.equals(o.b);
          }
      }
  }
}
