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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitAddOptions;
import edu.nyu.cs.javagit.api.commands.GitAddResponse;
import edu.nyu.cs.javagit.client.GitAddResponseImpl;
import edu.nyu.cs.javagit.client.IGitAdd;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * Command-line implementation of the <code>IGitAdd</code> interface.
 * 
 * TODO (gsd216) - to implement exception chaining.
 */
public class CliGitAdd implements IGitAdd {

  /**
   * Implementations of &lt;git-add&gt; with options and list of files provided.
   * 
   * TODO: (gsd216) Redo the throwing of exceptions thrown - JavaGitException
   */
  public GitAddResponse add(File repositoryPath, GitAddOptions options, List<File> paths)
      throws JavaGitException, IOException {
    CheckUtilities.checkFileValidity(repositoryPath);
    GitAddParser parser = new GitAddParser();
    List<String> command = buildCommand(repositoryPath, options, paths);
    GitAddResponseImpl response = (GitAddResponseImpl) ProcessUtilities.runCommand(repositoryPath,
        command, parser);
    if (response.containsError()) {
      int line = response.getError(0).getLineNumber();
      String error = response.getError(0).error();
      throw new JavaGitException(401001, "Line " + line + ", " + error);
    }
    if (options != null) {
      addDryRun(options, response);
    }
    return (GitAddResponse) response;
  }
  
  /**
   * Implementations of &lt;git-add&gt; with options and one file to be added to index.
   */
  public GitAddResponse add(File repositoryPath, GitAddOptions options, File file)
  throws JavaGitException, IOException {
    List<File> paths = new ArrayList<File>();
    paths.add(file);
    return add( repositoryPath, options, paths);
  }

  /**
   * Implementation of &lt;git-add&gt; dry run.
   */
  public GitAddResponse addDryRun(File repositoryPath, List<File> paths) throws JavaGitException,
      IOException {
    GitAddOptions options = new GitAddOptions();
    options.setDryRun(true);
    return add(repositoryPath, options, paths);
  }

  /**
   * Implementations of &lt;git-add&gt; in verbose mode.
   */
  public GitAddResponse addVerbose(File repositoryPath, List<File> paths) throws JavaGitException,
      IOException {
    GitAddOptions options = new GitAddOptions();
    options.setVerbose(true);
    return add(repositoryPath, options, paths);
  }

  /**
   * Implementations of &lt;git-add&gt; with force option set.
   */
  public GitAddResponse addWithForce(File repositoryPath, List<File> paths)
      throws JavaGitException, IOException {
    GitAddOptions options = new GitAddOptions();
    options.setForce(true);
    return add(repositoryPath, options, paths);
  }

  /**
   * if the dry run option was selected then set the flag in response.
   * 
   * @param options
   *          <code>GitAddOptions</code>
   * @param response
   *          <code>gitAddResponse</code>
   */
  private void addDryRun(GitAddOptions options, GitAddResponseImpl response) {
    if (options.dryRun()) {
      response.setDryRun(true);
    }
  }

  private List<String> buildCommand(File repositoryPath, GitAddOptions options, List<File> paths) {
    List<String> command = new ArrayList<String>();
    command.add("git");
    command.add("add");
    if (options != null) {
      if (options.dryRun()) {
        command.add("-n");
      }
      if (options.verbose()) {
        command.add("-v");
      }
      if (options.force()) {
        command.add("-f");
      }
      if (options.update()) {
        command.add("-u");
      }
      if (options.refresh()) {
        command.add("--refresh");
      }
      if (options.ignoreErrors()) {
        command.add("--ignore-errors");
      }
    }
    if (paths != null && paths.size() > 0) {
      for (File file : paths) {
        String absolutePath = file.getAbsolutePath();
        String extractedRelativePath = getRelativePathFromRepository(repositoryPath, absolutePath);
        command.add(extractedRelativePath);
      }
    }
    return command;
  }

  private String getRelativePathFromRepository(File repositoryDirectory, String filePath) {
    String repoString = repositoryDirectory.getAbsolutePath();
    if (filePath.startsWith(repoString)) {
      int repoLength = repoString.length();
      //check for root directory scenario
      if(filePath.length() > repoLength) {
        // add 1 to repoLength to handle the path separator
        String relativePath = filePath.substring(repoLength + 1, filePath.length());
        return relativePath;
      }
      else {
        return new String("");
      }
    }
    return filePath;
  }

  /**
   * Parser class that implements <code>IParser</code> for implementing a parser for
   * &lt;git-add&gt; output.
   */
  public class GitAddParser implements IParser {

    private int lineNum;
    private GitAddResponseImpl response;

    public GitAddParser() {
      lineNum = 0;
      response = new GitAddResponseImpl();
    }

    public void parseLine(String line) {
      if (line == null || line.length() == 0) {
        return;
      }
      lineNum++;
      if (isError(line))
        response.setError(lineNum, line);
      else if (isComment(line))
        response.setComment(lineNum, line);
      else
        processLine(line);
    }

    private boolean isError(String line) {
      if (line.trim().startsWith("fatal") || line.trim().startsWith("error")) {
        return true;
      }
      return false;
    }

    private boolean isComment(String line) {
      if (line.startsWith("Nothing specified") || line.contains("nothing added")
          || line.contains("No changes") || line.contains("Maybe you wanted to say")
          || line.contains("usage")) {
        return true;
      }
      return false;
    }

    /**
     * Lines that start with "add" have the second token as the name of the file added by
     * &lt;git-add&gt.
     * 
     * @param line
     */
    private void processLine(String line) {
      if (line.startsWith("add")) {
        StringTokenizer st = new StringTokenizer(line);

        if (st.nextToken().equals("add") && st.hasMoreTokens()) {
          String extractedFileName = filterFileName(st.nextToken());
          if (extractedFileName != null && extractedFileName.length() > 0) {
            File file = new File(extractedFileName);
            response.add(file);
          }
        }
      } else {
        processSpaceDelimitedFilePaths(line);
      }
    }

    private void processSpaceDelimitedFilePaths(String line) {
      if (!line.startsWith("\\s+")) {
        StringTokenizer st = new StringTokenizer(line);
        while (st.hasMoreTokens()) {
          File file = new File(st.nextToken());
          response.add(file);
        }
      }
    }

    public String filterFileName(String token) {
      if (token.length() > 0 && enclosedWithSingleQuotes(token)) {
        int firstQuote = token.indexOf("'");
        int nextQuote = token.indexOf("'", firstQuote + 1);
        if (nextQuote > firstQuote) {
          return token.substring(firstQuote + 1, nextQuote);
        }
      }
      return null;
    }

    public boolean enclosedWithSingleQuotes(String token) {
      if (token.matches("'.*'")) {
        return true;
      }
      return false;
    }

    public GitAddResponse getResponse() {
      return response;
    }
  }
}
