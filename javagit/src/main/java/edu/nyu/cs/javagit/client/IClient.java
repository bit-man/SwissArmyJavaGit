package edu.nyu.cs.javagit.client;

/**
 * An interface to represent a git client type, such as a command-line client.
 */
public interface IClient {

	/**
	 * Gets an instance of <code>IGitAdd</code>.
	 * 
	 * @return An instance of <code>IGitAdd</code>.
	 */
	public IGitAdd getGitAddInstance();

	/**
	 * Gets an instance of <code>IGitCommit</code>.
	 * 
	 * @return An instance of <code>IGitCommit</code>.
	 */
	public IGitCommit getGitCommitInstance();

	/**
	 * Gets an instance of <code>IGitDiff</code>.
	 * 
	 * @return An instance of <code>IGitDiff</code>.
	 */
	public IGitDiff getGitDiffInstance();

	/**
	 * Gets an instance of <code>IGitGrep</code>.
	 * 
	 * @return An instance of <code>IGitGrep</code>.
	 */
	public IGitGrep getGitGrepInstance();

	/**
	 * Gets an instance of <code>IGitLog</code>.
	 * 
	 * @return An instance of <code>IGitLog</code>.
	 */
	public IGitLog getGitLogInstance();

	/**
	 * Gets an instance of <code>IGitMv</code>.
	 * 
	 * @return An instance of <code>IGitMv</code>.
	 */
	public IGitMv getGitMvInstance();

	/**
	 * Gets an instance of <code>IGitRevert</code>.
	 * 
	 * @return An instance of <code>IGitRevert</code>.
	 */
	public IGitRevert getGitRevertInstance();

	/**
	 * Gets an instance of <code>IGitRm</code>.
	 * 
	 * @return An instance of <code>IGitRm</code>.
	 */
	public IGitRm getGitRmInstance();

	/**
	 * Gets an instance of <code>IGitShow</code>.
	 * 
	 * @return An instance of <code>IGitShow</code>.
	 */
	public IGitShow getGitShowInstance();

	/**
	 * Gets an instance of <code>IGitStatus</code>.
	 * 
	 * @return An instance of <code>IGitStatus</code>.
	 */
	public IGitStatus getGitStatusInstance();

}
