package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.api.commands.GitRmResponse;
import edu.nyu.cs.javagit.client.IGitRm;

/**
 * Command-line implementation of the <code>IGitRm</code> interface.
 */
public class CliGitRm implements IGitRm {

	public CliGitRm() {
	}

	public GitRmResponse rm(String repoPath, List<String> filePaths) throws IOException {

		List<String> pbin = new ArrayList<String>();
		pbin.add("git");
		pbin.add("rm");
		pbin.addAll(filePaths);


		ProcessBuilder pb = new ProcessBuilder(pbin);
		pb.directory(new File(repoPath));
		pb.redirectErrorStream(true);

		GitRmParser parser = new GitRmParser();

		Process p = ProcessUtilities.startProcess(pb);
		ProcessUtilities.getProcessOutput(p, parser);
		ProcessUtilities.waitForAndDestroyProcess(p);

		return parser.getResponse();

	}

	class GitRmParser implements IParser {

		private boolean success;

		GitRmParser() {
			success = true;
		}

		@Override
		public void parseLine(String line) {
			// handle failures
			if(line.indexOf("index file corrupt") == 0 ||
					line.matches("pathspec .* did not match any files") ||
					line.matches("not removing. * recursively without -r") ||
					line.indexOf("git-rm: ") == 0 ||
					line.indexOf("Unable to write new index file") == 0) {
				success = false;
			}

		}
		// TODO Auto-generated method stub

		public GitRmResponse getResponse() {
			return new GitRmResponse(success);
		}

	}

}
