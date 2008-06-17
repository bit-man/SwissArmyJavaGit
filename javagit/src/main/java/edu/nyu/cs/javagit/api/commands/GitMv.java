/**
 * 
 */
package edu.nyu.cs.javagit.api.commands;

import java.io.IOException;

import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitMv;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitMv</code> provides an interface to move files, symlinks or directories to a 
 * different location or rename them.
 *
 */
public class GitMv {

	/**
	 * Moves the specified source file/symlink/directory to the destination 
	 * file/symlink/directory. If destination is non-existant then same as rename.
	 * 
	 * @param repositoryPath
	 *            The path to the repository to commit against. A non-zero
	 *            length argument is required for this parameter, otherwise a
	 *            <code>NullPointerException</code> or
	 *            <code>IllegalArgumentException</code> will be thrown.
	 * @param source
	 *            The file/symlink/directory to be moved. A non-zero length
	 *            argument is required for this parameter, otherwise a
	 *            <code>NullPointerException</code> or
	 *            <code>IllegalArgumentException</code> will be thrown.
	 * @param destination
	 *            The existant file/symlink/directory or a non-existant 
	 *            name(for renaming). A non-zero length argument is required
	 *            for this parameter, otherwise a
	 *            <code>NullPointerException</code> or
	 *            <code>IllegalArgumentException</code> will be thrown.
	 * @return The results from the mv.
	 * @exception IOException
	 *                There are many reasons for which an
	 *                <code>IOException</code> may be thrown. Examples
	 *                include:
	 *                <ul>
	 *                <li>sourcefile doesn't exist</li>
	 *                <li>moving a source file which is not added to the repository yet</li>
	 *                <li>source directory is empty</li>
	 *                <li>source directory present in destination directory>/li>
	 *                </ul>
	 */
	public GitMvResponse mv(String repositoryPath, String option, String source, String destination)
			throws IOException {

		// TODO : Change this method to take the JavaGitConfig object,
		// or whatever it ends up being called, once Ross and Max create it.

		CheckUtilities.checkStringArgument(source, "source");
		CheckUtilities.checkStringArgument(destination, "destination");

		IClient client = ClientManager.getInstance().getPreferredClient();
		IGitMv gitMv = client.getGitMvInstance();
		return gitMv.mv(repositoryPath, option, source, destination);
	}

}

