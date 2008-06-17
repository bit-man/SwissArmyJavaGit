package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;

import edu.nyu.cs.javagit.api.commands.GitMvResponse;
import edu.nyu.cs.javagit.client.IGitMv;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * Command-line implementation of the <code>IGitMv</code> interface.
 */
public class CliGitMv implements IGitMv {

	public GitMvResponse mv(String repoPath, String option, String source, 
                          String destination) throws IOException {
		CheckUtilities.checkStringArgument(option, "option");
		CheckUtilities.checkStringArgument(source, "source");
		CheckUtilities.checkStringArgument(destination, "destination");

		ProcessBuilder pb = new ProcessBuilder("git-mv", option, source, destination);
		pb.directory(new File(repoPath));
		pb.redirectErrorStream(true);

		GitMvParser parser = new GitMvParser();

		Process p = ProcessUtilities.startProcess(pb);
		ProcessUtilities.getProcessOutput(p, parser);
		ProcessUtilities.waitForAndDestroyProcess(p);

		return parser.getResponse();
	}
	
	public class GitMvParser implements IParser {

		// The response object for an mv operation.
		private GitMvResponse response;
		
		public void parseLine(String line) {

			// TODO : handle error messages! (git-mv responses)
			String comment;
			

			int quoteIndex = line.indexOf('\'');
			if (-1 == quoteIndex){
				int colonIndex = line.indexOf(':');
				//This is message with -n option
				comment = line.substring(colonIndex);
			}
			else{
				//This is message with -f option
				comment = line.substring(0, quoteIndex);
			}
				
			response = new GitMvResponse(comment);

		}
		
		public GitMvResponse getResponse() {
			return response;
		}
	}

}
