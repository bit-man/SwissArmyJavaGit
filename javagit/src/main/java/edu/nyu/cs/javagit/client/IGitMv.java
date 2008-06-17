package edu.nyu.cs.javagit.client;

import java.io.IOException;

import edu.nyu.cs.javagit.api.commands.GitMvResponse;

/**
 * An interface to represent the git-mv command.
 */
public interface IGitMv {

	// TODO: Create method signatures for this interface.
	public GitMvResponse mv(String repositoryPath, String option, 
             String source, String destination) throws IOException;
}
