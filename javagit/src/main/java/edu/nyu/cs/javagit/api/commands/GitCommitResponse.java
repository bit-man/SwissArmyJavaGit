package edu.nyu.cs.javagit.api.commands;

/**
 * A response data object for the git-commit command.
 */
public class GitCommitResponse {

	/** The short hash name for the commit. */
	private String commitShortHashName = "";

	/** The short comment for the commit. */
	private String commitShortComment = "";

	/** Indicates how many files have changed in a commit. */
	private int filesChanged = 0;

	/** Indicates how many lines were inserted in a commit. */
	private int linesInserted = 0;

	/** Indicates how many lines were deleted in a commit. */
	private int linesDeleted = 0;

	/**
	 * Constructor.
	 * 
	 * @param shortHashName
	 *            The short hash name
	 * @param shortComment
	 */
	public GitCommitResponse(String shortHashName, String shortComment) {
		this.commitShortHashName = shortHashName;
		this.commitShortComment = shortComment;
	}

	/**
	 * Gets the number of files changed in the commit.
	 * 
	 * @return The number of files changed in the commit.
	 */
	public int getFilesChanged() {
		return filesChanged;
	}

	/**
	 * Sets the number of files changed in the commit.
	 * 
	 * @param filesChanged
	 *            The number of files changed in the commit.
	 */
	public void setFilesChanged(int filesChanged) {
		this.filesChanged = filesChanged;
	}

	/**
	 * Sets the number of files changed during a commit.
	 * 
	 * @param filesChangedStr
	 *            The number of files changed in <code>String</code> format.
	 * @return True if the <code>filesChangedStr</code> parameter is a
	 *         <code>String</code> representing a number. False if the
	 *         <code>String</code> does not contain a parsable integer.
	 */
	public boolean setFilesChanged(String filesChangedStr) {
		try {
			this.filesChanged = Integer.parseInt(filesChangedStr);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Gets the number of lines inserted in the commit.
	 * 
	 * @return The number of lines inserted in the commit.
	 */
	public int getLinesInserted() {
		return linesInserted;
	}

	/**
	 * Sets the number of lines inserted in the commit.
	 * 
	 * @param linesInserted
	 *            The number of lines inserted in the commit.
	 */
	public void setLinesInserted(int linesInserted) {
		this.linesInserted = linesInserted;
	}

	/**
	 * Sets the number of lines inserted in a commit.
	 * 
	 * @param linesInsertedStr
	 *            The number of lines inserted in <code>String</code> format.
	 * @return True if the <code>linesInsertedStr</code> parameter is a
	 *         <code>String</code> representing a number. False if the
	 *         <code>String</code> does not contain a parsable integer.
	 */
	public boolean setLinesInserted(String linesInsertedStr) {
		try {
			this.linesInserted = Integer.parseInt(linesInsertedStr);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Gets the number of lines deleted in the commit.
	 * 
	 * @return The number of lines deleted in the commit.
	 */
	public int getLinesDeleted() {
		return linesDeleted;
	}

	/**
	 * Sets the number of lines deleted in the commit.
	 * 
	 * @param linesDeleted
	 *            The number of lines deleted in the commit.
	 */
	public void setLinesDeleted(int linesDeleted) {
		this.linesDeleted = linesDeleted;
	}

	/**
	 * Sets the number of lines deleted in a commit.
	 * 
	 * @param linesDeletedStr
	 *            The number of lines deleted in <code>String</code> format.
	 * @return True if the <code>linesDeletedStr</code> parameter is a
	 *         <code>String</code> representing a number. False if the
	 *         <code>String</code> does not contain a parsable integer.
	 */
	public boolean setLinesDeleted(String linesDeletedStr) {
		try {
			this.linesDeleted = Integer.parseInt(linesDeletedStr);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Gets the short hash name for the commit. This is the first seven
	 * characters of the SHA1 hash that represents the commit.
	 * 
	 * @return The short hash name for the commit.
	 */
	public String getCommitShortHashName() {
		return commitShortHashName;
	}

	/**
	 * Gets the short comment (description) for the commit. It is the first line
	 * of the commit message.
	 * 
	 * @return The short comment for the commit.
	 */
	public String getCommitShortComment() {
		return commitShortComment;
	}

}
