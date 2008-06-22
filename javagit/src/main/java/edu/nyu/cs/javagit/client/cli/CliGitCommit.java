package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitCommitResponse;
import edu.nyu.cs.javagit.client.IGitCommit;
import edu.nyu.cs.javagit.utilities.CheckUtilities;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

/**
 * Command-line implementation of the <code>IGitCommit</code> interface.
 */
public class CliGitCommit implements IGitCommit {

  public GitCommitResponse commit(String repoPath, String message) throws IOException,
      JavaGitException {
    CheckUtilities.checkStringArgument(repoPath, "repository path");
    CheckUtilities.checkStringArgument(message, "message");

    ProcessBuilder pb = new ProcessBuilder("git-commit", "-m", message);
    pb.directory(new File(repoPath));
    pb.redirectErrorStream(true);

    GitCommitParser parser = new GitCommitParser();

    Process p = ProcessUtilities.startProcess(pb);
    ProcessUtilities.getProcessOutput(p, parser);
    ProcessUtilities.waitForAndDestroyProcess(p);

    return parser.getResponse();
  }

  public class GitCommitParser implements IParser {

    // Holding onto the error message to make part of an exception
    private StringBuffer errorMsg = null;

    // Track the number of lines parsed.
    private int numLinesParsed = 0;

    // The response object for a commit.
    private GitCommitResponse response;

    public void parseLine(String line) {

      // TODO (jhl388): handle error messages in a better manner.

      if (null != errorMsg) {
        ++numLinesParsed;
        errorMsg.append(", line" + numLinesParsed + "=[" + line + "]");
        return;
      }

      switch (numLinesParsed) {
      case 0:
        parseLineOne(line);
        break;
      case 1:
        parseLineTwo(line);
        break;
      default:
        parseAllOtherLines(line);
      }
      ++numLinesParsed;
    }

    /**
     * Parses the first line of the commit response text.
     * 
     * @param line
     *          The line of text to process.
     */
    private void parseLineOne(String line) {
      if (line.startsWith("Created initial commit ") || line.startsWith("Created commit ")) {
        int locColon = line.indexOf(':');
        int locShortHash = line.lastIndexOf(' ', locColon);
        String shortHash = line.substring(locShortHash + 1, locColon);
        String shortComment = line.substring(locColon + 2);
        response = new GitCommitResponse(shortHash, shortComment);
      } else {
        errorMsg = new StringBuffer();
        errorMsg.append("line1=[" + line + "]");
      }
    }

    /**
     * Parse the second line of the commit response text.
     * 
     * @param line
     *          The line of text to process.
     */
    private void parseLineTwo(String line) {
      int spaceOffset = line.indexOf(' ', 1);
      response.setFilesChanged(line.substring(1, spaceOffset));

      int commaOffset = line.indexOf(',', spaceOffset);
      spaceOffset = line.indexOf(' ', commaOffset + 2);
      response.setLinesInserted(line.substring(commaOffset + 2, spaceOffset));

      commaOffset = line.indexOf(',', spaceOffset);
      spaceOffset = line.indexOf(' ', commaOffset + 2);
      response.setLinesDeleted(line.substring(commaOffset + 2, spaceOffset));
    }

    /**
     * Parses the rest of the lines in the response from a successful commit.
     * 
     * @param line
     *          The line to parse.
     */
    private void parseAllOtherLines(String line) {
      if (line.startsWith(" create")) {
        parseAddDeleteLine(line, true);
      } else if (line.startsWith(" copy")) {
        parseCopyRenameLine(line, true);
      } else if (line.startsWith(" delete")) {
        parseAddDeleteLine(line, false);
      } else if (line.startsWith(" rename")) {
        parseCopyRenameLine(line, false);
      }
    }

    /**
     * Parses an add or delete line. The formats of the lines it parses are (without quotes):
     * <ul>
     * <li>
     * 
     * <pre>
     * &quot; create mode 100644 a/file/name.txt&quot;
     * </pre>
     * 
     * </li>
     * <li>
     * 
     * <pre>
     * &quot; delete mode 100644 a/file/name.txt&quot;
     * </pre>
     * 
     * </li>
     * </ul>
     * 
     * @param line
     *          The line of text to parse.
     * @param isAdd
     *          True if parsing an add (create) line, false if parsing a delete line.
     */
    private void parseAddDeleteLine(String line, boolean isAdd) {
      final int modeOffset = 13;
      final int endModeOffset = 19;
      final int startPathOffset = 20;
      String mode = line.substring(modeOffset, endModeOffset);
      String path = line.substring(startPathOffset);
      if (isAdd) {
        response.addAddedFile(path, mode);
      } else {
        response.addDeletedFile(path, mode);
      }
    }

    /**
     * Parses a copy or rename line. The formats of the lines it parses are (without quotes):
     * <ul>
     * <li>
     * 
     * <pre>
     * &quot; copy path/to/{file.txt =&gt; newName.txt} (100%)&quot;
     * </pre>
     * 
     * </li>
     * <li>
     * 
     * <pre>
     * &quot; copy path/to/file.txt =&gt; different/path/newName.txt (5%)&quot;
     * </pre>
     * 
     * </li>
     * </ul>
     * 
     * @param line
     *          The line of text to parse.
     * @param isCopy
     *          True if parsing a copy line, false if parsing a rename line.
     */
    private void parseCopyRenameLine(String line, boolean isCopy) {
      final int PATH_START_COPY = 6;
      final int PATH_START_RENAME = 8;
      int pathStart = (isCopy ? PATH_START_COPY : PATH_START_RENAME);
      int openCurlyOffset = line.indexOf('{', pathStart);
      int openParenOffset = line.lastIndexOf('(');

      String fromPath = null;
      String toPath = null;

      if (-1 == openCurlyOffset) {
        int arrowOffset = line.indexOf("=>");
        fromPath = line.substring(pathStart, arrowOffset - 1);
        toPath = line.substring(arrowOffset + 3, openParenOffset - 1);
      } else {
        String base = line.substring(pathStart, openCurlyOffset);
        int arrowOffset = line.indexOf("=>", openCurlyOffset);
        fromPath = base + line.substring(openCurlyOffset + 1, arrowOffset - 1);
        int closeCurlyOffset = line.indexOf('}', arrowOffset + 3);
        toPath = base + line.substring(arrowOffset + 3, closeCurlyOffset);
      }

      int percentOffset = line.lastIndexOf('%');
      int percentage = 0;
      try {
        percentage = Integer.parseInt(line.substring(openParenOffset + 1, percentOffset));
      } catch (NumberFormatException e) {
        // TODO (jhl388): log this error somehow! Or, at least, deal with it in a better manner.
      }

      if (isCopy) {
        response.addCopiedFile(fromPath, toPath, percentage);
      } else {
        response.addRenamedFile(fromPath, toPath, percentage);
      }
    }

    /**
     * Gets a <code>GitCommitResponse</code> object containing the information from the commit
     * response text parsed by this IParser instance.
     * 
     * @return The <code>GitCommitResponse</code> object containing the commit's response
     *         information.
     */
    public GitCommitResponse getResponse() throws JavaGitException {
      if (null != errorMsg) {
        throw new JavaGitException(100001, ExceptionMessageMap.getMessage("100001")
            + "  The git-commit error message:  { " + errorMsg.toString() + " }");
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
