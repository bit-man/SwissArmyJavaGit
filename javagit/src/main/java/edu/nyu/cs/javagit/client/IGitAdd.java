package edu.nyu.cs.javagit.client;

import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.commands.GitAddResponse;

/**
 * An interface to represent the git-add command.
 */
public interface IGitAdd {

	/**
	 * 
	 * @param repositoryPath
	 * @param completeCommand to be added
	 * @return
	 * @throws IOException
	 *             can be thrown because - The path to filename does not exist -
	 *             or the file does not exist - repository path does not exist
	 */
	public GitAddResponse add(String repositoryPath, String[] completeCommand)
	  throws IOException;
	
	public GitAddResponse add(String repositoryPath, List<String> options, List<String> files)
	  throws IOException;

}
