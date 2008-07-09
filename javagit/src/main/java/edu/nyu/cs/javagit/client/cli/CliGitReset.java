package edu.nyu.cs.javagit.client.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitResetOptions;
import edu.nyu.cs.javagit.api.commands.GitResetResponse;
import edu.nyu.cs.javagit.api.commands.GitResetOptions.ResetType;
import edu.nyu.cs.javagit.client.GitResetResponseImpl;
import edu.nyu.cs.javagit.client.IGitReset;
import edu.nyu.cs.javagit.utilities.CheckUtilities;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

/**
 * Command-line implementation of the <code>IGitReset</code> interface.
 */
public class CliGitReset implements IGitReset {

  public GitResetResponse gitReset(String repositoryPath) throws IOException, JavaGitException {
    return null;
  }

  public GitResetResponse gitReset(String repositoryPath, GitResetOptions options)
      throws IOException, JavaGitException {
    return resetProcessor(repositoryPath, new GitResetOptions(), null);
  }

  public GitResetResponse gitReset(String repositoryPath, Ref commitName, List<String> paths)
      throws IOException, JavaGitException {
    return resetProcessor(repositoryPath, new GitResetOptions(commitName), paths);
  }

  public GitResetResponse gitReset(String repositoryPath, List<String> paths) throws IOException,
      JavaGitException {
    return resetProcessor(repositoryPath, new GitResetOptions(), paths);
  }

  public GitResetResponse gitResetHard(String repositoryPath, Ref commitName)
      throws IOException, JavaGitException {
    return resetProcessor(repositoryPath, new GitResetOptions(ResetType.HARD, commitName), null);
  }

  public GitResetResponse gitResetSoft(String repositoryPath, Ref commitName)
      throws IOException, JavaGitException {
    return resetProcessor(repositoryPath, new GitResetOptions(ResetType.SOFT, commitName), null);
  }

  protected GitResetResponseImpl resetProcessor(String repositoryPath, GitResetOptions options,
      List<String> paths) throws IOException, JavaGitException {
    CheckUtilities.checkStringArgument(repositoryPath, "repository path");

    List<String> commandLine = buildCommand(options, paths);
    GitResetParser parser = new GitResetParser();

    return (GitResetResponseImpl) ProcessUtilities.runCommand(repositoryPath, commandLine, parser);
  }

  protected List<String> buildCommand(GitResetOptions options, List<String> paths) {

    // TODO (jhl388): Add a unit test for this method.

    List<String> cmd = new ArrayList<String>();
    cmd.add("git-reset");

    if (null != options) {
      if (null == paths) {
        // Only include the reset type if there are no paths. -- jhl388 2008.07.04
        cmd.add(options.getResetType().toString());
      }

      if (options.isQuiet()) {
        cmd.add("-q");
      }

      cmd.add(options.getCommitName().toString());
    }

    if (null != paths) {
      cmd.add("--");
      cmd.addAll(paths);
    }

    return cmd;
  }

  public class GitResetParser implements IParser {

    // TODO (jhl388): Create test case for this class.
    // TODO (jhl388): Finish implementing the GitResetParser.

    // Holding onto the error message to make part of an exception
    private StringBuffer errorMsg = null;

    // Track the number of lines parsed.
    private int numLinesParsed = 0;

    // The response object for a reset.
    private GitResetResponseImpl response;

    public void parseLine(String line) {

      // TODO (jhl388): handle error messages in a better manner.

      if (null != errorMsg) {
        ++numLinesParsed;
        errorMsg.append(", line" + numLinesParsed + "=[" + line + "]");
        return;
      }

      switch (numLinesParsed) {
      case 0:
        // parseLineOne(line);
        break;
      case 1:
        // parseLineTwo(line);
        break;
      default:
        // parseAllOtherLines(line);
      }
      ++numLinesParsed;
    }

    /**
     * Gets a <code>GitResetResponseImpl</code> object containing the information from the reset
     * response text parsed by this IParser instance.
     * 
     * @return The <code>GitResetResponseImpl</code> object containing the reset's response
     *         information.
     */
    public GitResetResponseImpl getResponse() throws JavaGitException {
      if (null != errorMsg) {
        throw new JavaGitException(432000, ExceptionMessageMap.getMessage("432000")
            + "  The git-reset error message:  { " + errorMsg.toString() + " }");
      }
      return response;
    }

    /**
     * Gets the number of lines of response text parsed by this IParser.
     * 
     * @return The number of lines of response text parsed by this IParser.
     */
    public int getNumLinesParsed() {
      return numLinesParsed;
    }
  }
}
