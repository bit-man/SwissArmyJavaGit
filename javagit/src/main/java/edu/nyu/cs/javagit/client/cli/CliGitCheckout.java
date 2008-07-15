package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitCheckoutOptions;
import edu.nyu.cs.javagit.api.commands.GitCheckoutResponse;
import edu.nyu.cs.javagit.client.GitCheckoutResponseImpl;
import edu.nyu.cs.javagit.client.IGitCheckout;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

public class CliGitCheckout implements IGitCheckout {

  /**
   * String pattern for matching files with modified, deleted, added words in the output.
   */
  private enum Pattern {
    MODIFIED("^M\\s+\\w+"), DELETED("^D\\s+\\w+"), ADDED("^A\\s+\\w+");

    String pattern;

    private Pattern(String pattern) {
      this.pattern = pattern;
    }

    public boolean matches(String line) {
      if (line.matches(pattern)) {
        return true;
      }
      return false;
    }
  }

  /**
   * Git checkout with options and base branch information provided to &lt;git-checkout&gt; command.
   */
  public GitCheckoutResponse checkout(File repositoryPath, GitCheckoutOptions options, String branch)
      throws JavaGitException, IOException {
    CheckUtilities.checkFileValidity(repositoryPath);
    String[] command = buildCommand(options, branch);
    GitCheckoutParser parser = new GitCheckoutParser();
    return (GitCheckoutResponse) ProcessUtilities.runCommand(repositoryPath.getAbsolutePath(),
        Arrays.asList(command), parser);
  }

  /**
   * Git checkout without any options and branch information provided. Just a basic checkout command.
   */
  public GitCheckoutResponse checkout(File repositoryPath) throws JavaGitException, IOException {
    return checkout(repositoryPath, null, null);
  }

  /**
   * Checks out a branch from the git repository
   */
  public GitCheckoutResponse checkout(File repositoryPath, String branch) throws JavaGitException,
      IOException {
    return checkout( repositoryPath, branch);
  }

  /**
   * Checks out a list of files from repository.
   */
  public GitCheckoutResponse checkout(File repositoryPath, List<File> paths)
      throws JavaGitException, IOException {
    CheckUtilities.checkFileValidity(repositoryPath);
    CheckUtilities.checkNullListArgument(paths, "list of file paths");
    GitCheckoutParser parser = new GitCheckoutParser();
    List<String> command = buildCommand(paths);
    return (GitCheckoutResponse) ProcessUtilities.runCommand(repositoryPath.getAbsolutePath(),
        command, parser);
  }

  private List<String> buildCommand(List<File> paths) {
    List<String> command = new ArrayList<String>();
    command.add("git-checkout");
    for (File file : paths) {
      command.add(file.getName());
    }
    return command;
  }

  private String[] buildCommand(GitCheckoutOptions options, String branch) throws JavaGitException {
    List<String> command = new ArrayList<String>();
    command.add("git-checkout");
    if (options != null) {
      if (options.optQ()) {
        command.add("-q");
      }
      if (options.optF()) {
        command.add("-f");
      }
      /*
       * --track and --no-track options are valid only with -b option
       */
      String newBranch;
      if ((newBranch = options.getOptB()) != null) {
        if (options.optNoTrack() && options.optTrack()) {
          throw new JavaGitException(120, "Both --notrack and --track options are set");
        }
        if (options.optTrack()) {
          command.add("--track");
        }
        if (options.optNoTrack()) {
          command.add("--no-track");
        }
        command.add("-b");
        command.add(newBranch);
      }
      if (options.optL()) {
        command.add("-l");
      }
      if (options.optM()) {
        command.add("-m");
      }
    }
    if (branch != null && branch.length() > 0) {
      command.add(branch);
    }
    String[] commandArgs = new String[command.size()];
    return command.toArray(commandArgs);
  }

  public class GitCheckoutParser implements IParser {

    private int lineNum;
    private GitCheckoutResponseImpl response;

    public GitCheckoutParser() {
      lineNum = 0;
      response = new GitCheckoutResponseImpl();
    }

    public void parseLine(String line) {
      if (line == null || line.length() == 0) {
        return;
      }
      ++lineNum;
      parseErrorLine(line);
      parseSwitchedToBranchLine(line);
      parseFilesInfo(line);
    }

    private void parseErrorLine(String line) {
      if (line.startsWith("error:") || line.startsWith("fatal")) {
        response.setError(line);
      }
    }

    private void parseSwitchedToBranchLine(String line) {
      if (line.startsWith("Switched to branch")) {
        getSwitchedToBranch(line);
      } else if (line.startsWith("Switched to a new branch")) {
        getSwitchedToNewBranch(line);
      }
    }

    private void getSwitchedToBranch(String line) {
      String branch = extractBranch(line);
      response.setBranch(branch);
    }

    private void getSwitchedToNewBranch(String line) {
      String newBranch = extractBranch(line);
      response.setNewBranch(newBranch);
    }

    private String extractBranch(String line) {
      int startIndex = line.indexOf('"');
      int endIndex = line.indexOf('"', startIndex + 1);
      return line.substring(startIndex, endIndex + 1);
    }

    private void parseFilesInfo(String line) {
      if (Pattern.MODIFIED.matches(line)) {
        addModifiedFile(line);
        return;
      }
      if (Pattern.DELETED.matches(line)) {
        addDeletedFile(line);
        return;
      }
      if (Pattern.ADDED.matches(line)) {
        addAddedFile(line);
      }
    }

    private String extractFile(String line) {
      String filename = null;
      Scanner scanner = new Scanner(line);
      while (scanner.hasNext()) {
        filename = scanner.next();
      }
      return filename;
    }

    private void addModifiedFile(String line) {
      response.addModifiedFile(extractFile(line));
    }

    private void addDeletedFile(String line) {
      response.addDeletedFile(extractFile(line));
    }

    private void addAddedFile(String line) {
      response.addAddedFile(extractFile(line));
    }

    public GitCheckoutResponse getResponse() {
      return response;
    }
  }

}
