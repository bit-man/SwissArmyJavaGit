package edu.nyu.cs.javagit.client;

import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.commands.GitRmResponse;

/**
 * An interface to represent the git-rm command.
 */
public interface IGitRm {

	/**
	 * Remove files relative to the path within the repository.
	 * 
	 * @param repoPath
	 *            A path within the repository. A non-zero length argument is
	 *            required for this parameter, otherwise a
	 *            <code>NullPointerException</code> or
	 *            <code>IllegalArgumentException</code> will be thrown.
	 * @param files
	 *            A list of files to remove. A non-zero length argument is
	 *            required for this parameter and its children, otherwise a
	 *            <code>NullPointerException</code> or
	 *            <code>IllegalArgumentException</code> will be thrown.
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
	public GitRmResponse rm(String repositoryPath, List<String> files)
			throws IOException;

}
