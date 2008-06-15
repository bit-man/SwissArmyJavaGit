package edu.nyu.cs.javagit.api.commands;

import java.io.IOException;

import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitCommit;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitCommit</code> provides an interface to commit changes to a git
 * repository.
 */
public class GitCommit {

	/**
	 * Commits all staged changes (changes already defined in the index) into
	 * the specified repository.
	 * 
	 * @param repositoryPath
	 *            The path to the repository to commit against. A non-zero
	 *            length argument is required for this parameter, otherwise a
	 *            <code>NullPointerException</code> or
	 *            <code>IllegalArgumentException</code> will be thrown.
	 * @param message
	 *            The message to attach to the commit. A non-zero length
	 *            argument is required for this parameter, otherwise a
	 *            <code>NullPointerException</code> or
	 *            <code>IllegalArgumentException</code> will be thrown.
	 * @return The results from the commit.
	 * @exception IOException
	 *                There are many reasons for which an
	 *                <code>IOException</code> may be thrown. Examples
	 *                include:
	 *                <ul>
	 *                <li>a directory doesn't exist</li>
	 *                <li>access to a file is denied</li>
	 *                <li>a command is not found on the PATH</li>
	 *                </ul>
	 */
	public GitCommitResponse commit(String repositoryPath, String message)
			throws IOException {

		// TODO (jhl388): Change this method to take the JavaGitConfig object,
		// or whatever it ends up being called, once Ross and Max create it.

		CheckUtilities.checkStringArgument(repositoryPath, "repository path");
		CheckUtilities.checkStringArgument(message, "message");

		IClient client = ClientManager.getInstance().getPreferredClient();
		IGitCommit gitCommit = client.getGitCommitInstance();
		return gitCommit.commit(repositoryPath, message);
	}

}
