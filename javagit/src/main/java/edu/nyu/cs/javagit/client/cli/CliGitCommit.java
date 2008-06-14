package edu.nyu.cs.javagit.client.cli;

import java.io.File;

import edu.nyu.cs.javagit.client.IGitCommit;

/**
 * Command-line implementation of the <code>IGitCommit</code> interface.
 */
public class CliGitCommit implements IGitCommit {

	public void commit(String repoPath, String message) {
		// call a standard class that makes the git-commit call
		// get the results string and process it.

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
