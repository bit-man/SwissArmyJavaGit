package edu.nyu.cs.javagit.api.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * A response data object for the git-commit command.
 */
public class GitCommitResponse {

	// TODO (jhl388): Add methods to retrieve added, copied, deleted and renamed
	// file information.

	// The short hash name for the commit.
	private String commitShortHashName = "";

	// The short comment for the commit.
	private String commitShortComment = "";

	// Indicates how many files have changed in a commit.
	private int filesChanged = 0;

	// Indicates how many lines were inserted in a commit.
	private int linesInserted = 0;

	// Indicates how many lines were deleted in a commit.
	private int linesDeleted = 0;

	/**
	 * The list of the files added to the repository in this commit. The file
	 * name is the relative path to the file from the root of the repository
	 * tree.
	 */
	private List<AddedOrDeletedFile> addedFiles;

	/**
	 * A list of new files that were copied from existing files already tracked
	 * in the repository. The file names are relative paths to the files from
	 * the root of the repository tree.
	 */
	private List<CopiedOrMovedFile> copiedFiles;

	/**
	 * A list of the files deleted form the repository in this commit. The file
	 * name is the relative path to the file from the root of the repository
	 * tree.
	 */
	private List<AddedOrDeletedFile> deletedFiles;

	/**
	 * A list of files that were moved/renamed in this commit. The file name is
	 * the relative path to the file from the root of the repository tree.
	 */
	private List<CopiedOrMovedFile> renamedFiles;

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

		addedFiles = new ArrayList<AddedOrDeletedFile>();
		copiedFiles = new ArrayList<CopiedOrMovedFile>();
		deletedFiles = new ArrayList<AddedOrDeletedFile>();
		renamedFiles = new ArrayList<CopiedOrMovedFile>();
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
	 * Add the information about a newly added file in the repository for a
	 * given commit.
	 * 
	 * @param pathToFile
	 *            The path to the file from the root of the repository.
	 * @param mode
	 *            The mode of the file.
	 * @return False if the <code>pathToFile</code> is null or length zero.
	 *         True otherwise.
	 */
	public boolean addAddedFile(String pathToFile, String mode) {
		if (null == pathToFile || pathToFile.length() == 0) {
			return false;
		}
	
		return addedFiles.add(new AddedOrDeletedFile(pathToFile, mode));
	}

	/**
	 * Add the information about a file deleted from the repository for a given
	 * commit.
	 * 
	 * @param pathToFile
	 *            The path to the file from the root of the repository.
	 * @param mode
	 *            The mode of the file.
	 * @return False if the <code>pathToFile</code> is null or length zero.
	 *         True otherwise.
	 */
	public boolean addDeletedFile(String pathToFile, String mode) {
		if (null == pathToFile || pathToFile.length() == 0) {
			return false;
		}
	
		return deletedFiles.add(new AddedOrDeletedFile(pathToFile, mode));
	}

	/**
	 * Add the information about a moved/renamed file in the repository for a
	 * given commit.
	 * 
	 * @param sourceFilePath
	 *            The path to the source file.
	 * @param destinationFilePath
	 *            The path to the destination file.
	 * @param percentage
	 *            The percentage.
	 * @return False if <code>sourceFilePath</code> or
	 *         <code>destinationFilePath</code> is null or length zero. True
	 *         otherwise.
	 */
	public boolean addRenamedFile(String sourceFilePath,
			String destinationFilePath, int percentage) {
		if (null == sourceFilePath || sourceFilePath.length() == 0
				|| null == destinationFilePath
				|| destinationFilePath.length() == 0) {
			return false;
		}
		return renamedFiles.add(new CopiedOrMovedFile(sourceFilePath,
				destinationFilePath, percentage));
	}

	/**
	 * Add the information about a newly copied file in the repository for a
	 * given commit.
	 * 
	 * @param sourceFilePath
	 *            The path to the source file.
	 * @param destinationFilePath
	 *            The path to the destination file.
	 * @param percentage
	 *            The percentage.
	 * @return False if <code>sourceFilePath</code> or
	 *         <code>destinationFilePath</code> is null or length zero. True
	 *         otherwise.
	 */
	public boolean addCopiedFile(String sourceFilePath,
			String destinationFilePath, int percentage) {
		if (null == sourceFilePath || sourceFilePath.length() == 0
				|| null == destinationFilePath
				|| destinationFilePath.length() == 0) {
			return false;
		}
		return copiedFiles.add(new CopiedOrMovedFile(sourceFilePath,
				destinationFilePath, percentage));
	}

	/**
	 * Represents a file added to or deleted from the repository for a given
	 * commit.
	 */
	public class AddedOrDeletedFile {

		// TODO (jhl388): Add a method to get a javagit File instance for this
		// file.

		// The path to the file.
		private String pathTofile;

		// The mode the file was added/deleted with.
		private String mode;

		/**
		 * Constructor.
		 * 
		 * @param pathToFile
		 *            The path to the file.
		 * @param mode
		 *            The mode the file was added/deleted with.
		 */
		public AddedOrDeletedFile(String pathToFile, String mode) {
			this.pathTofile = pathToFile;
			this.mode = mode;
		}

		/**
		 * Gets the path to the file.
		 * 
		 * @return The path to the file.
		 */
		public String getPathTofile() {
			return pathTofile;
		}

		/**
		 * Gets the mode of the added/deleted file.
		 * 
		 * @return The mode of the added/deleted file.
		 */
		public String getMode() {
			return mode;
		}
	}

	/**
	 * Represents a file that was copied from an existing file already tracked
	 * in the repository or a tracked file that was moved from one name/place to
	 * another.
	 */
	public class CopiedOrMovedFile {

		// TODO (jhl388): Add methods to get a javagit File instance for these
		// files.

		// The path to the file that is the source of the copied/moved file.
		private String sourceFilePath;

		// The path to the new file/location.
		private String destinationFilePath;

		// The percentage. (not sure how to read this yet, -- jhl388 2008.06.15)
		private int percentage;

		/**
		 * Constructor.
		 * 
		 * @param sourceFilePath
		 *            The path to the source file.
		 * @param destinationFilePath
		 *            The path to the destination file.
		 * @param percentage
		 *            The percentage.
		 */
		public CopiedOrMovedFile(String sourceFilePath,
				String destinationFilePath, int percentage) {
			this.sourceFilePath = sourceFilePath;
			this.destinationFilePath = destinationFilePath;
			this.percentage = percentage;
		}

		/**
		 * Gets the path to the source file.
		 * 
		 * @return The path to the source file.
		 */
		public String getSourceFilePath() {
			return sourceFilePath;
		}

		/**
		 * Gets the path to the destination file.
		 * 
		 * @return The path to the destination file.
		 */
		public String getDestinationFilePath() {
			return destinationFilePath;
		}

		/**
		 * Gets the percentage.
		 * 
		 * @return The percentage.
		 */
		public int getPercentage() {
			return percentage;
		}
	}
}
