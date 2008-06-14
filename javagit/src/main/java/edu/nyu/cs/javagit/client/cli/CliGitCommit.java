package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;

import edu.nyu.cs.javagit.api.commands.GitCommitResponse;
import edu.nyu.cs.javagit.client.IGitCommit;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * Command-line implementation of the <code>IGitCommit</code> interface.
 */
public class CliGitCommit implements IGitCommit {

	public GitCommitResponse commit(String repoPath, String message)
			throws IOException {
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

		/** Track the number of lines parsed. */
		private int numLinesParsed = 0;

		/** The response object for a commit. */
		private GitCommitResponse response;

		public void parseLine(String line) {
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
		 *            The line of text to process.
		 */
		private void parseLineOne(String line) {
			// TODO (jhl388): handle error messages!
			int locColon = line.indexOf(':');
			int locShortHash = line.lastIndexOf(' ', locColon);
			String shortHash = line.substring(locShortHash + 1, locColon);
			String shortComment = line.substring(locColon + 2);

			response = new GitCommitResponse(shortHash, shortComment);
		}

		/**
		 * Parse the second line of the commit response text.
		 * 
		 * @param line
		 *            The line of text to process.
		 */
		private void parseLineTwo(String line) {
			int spaceOffset = line.indexOf(' ');
			response.setFilesChanged(line.substring(0, spaceOffset));

			int commaOffset = line.indexOf(',', spaceOffset);
			spaceOffset = line.indexOf(' ', commaOffset + 2);
			response.setLinesInserted(line.substring(commaOffset + 2,
					spaceOffset));

			commaOffset = line.indexOf(',', spaceOffset);
			spaceOffset = line.indexOf(' ', commaOffset + 2);
			response.setLinesDeleted(line.substring(commaOffset + 2,
					spaceOffset));
		}

		private void parseAllOtherLines(String line) {
			// TODO (jhl388): Code parsing of the "Other" lines
			if (line.startsWith(" create")) {

			} else if (line.startsWith(" rename")) {

			} else if (line.startsWith(" copy")) {

			} else if (line.startsWith(" delete")) {

			}

		}

		/**
		 * Gets a <code>GitCommitResponse</code> object containing the
		 * information from the commit response text parsed by this IParser
		 * instance.
		 * 
		 * @return The <code>GitCommitResponse</code> object containing the
		 *         commit's response information.
		 */
		public GitCommitResponse getResponse() {
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
