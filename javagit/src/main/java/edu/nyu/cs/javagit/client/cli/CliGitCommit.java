package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;

import edu.nyu.cs.javagit.client.IGitCommit;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * Command-line implementation of the <code>IGitCommit</code> interface.
 */
public class CliGitCommit implements IGitCommit {

	public void commit(String repoPath, String message) throws IOException {
		CheckUtilities.checkStringArgument(repoPath, "repository path");
		CheckUtilities.checkStringArgument(message, "message");

		ProcessBuilder pb = new ProcessBuilder("git-commit", "-m", message);
		pb.directory(new File(repoPath));
		pb.redirectErrorStream(true);

		GitCommitParser parser = new GitCommitParser();

		Process p = ProcessUtilities.startProcess(pb);
		ProcessUtilities.getProcessOutput(p, parser);
		ProcessUtilities.waitForAndDestroyProcess(p);

	}

	public class GitCommitParser implements IParser {
		// todo:
		// -
		public void parseLine(String line) {

		}
	}
}
