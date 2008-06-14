package edu.nyu.cs.javagit.client;

/**
 * An interface to represent the git-commit command.
 */
public interface IGitCommit {

	/**
	 * Commit everything that is in the index.
	 * 
	 * @param repoPath
	 *            The path to the repository.
	 * @param message
	 *            The message to attach to the commit.
	 */
	public void commit(String repoPath, String message);

}
