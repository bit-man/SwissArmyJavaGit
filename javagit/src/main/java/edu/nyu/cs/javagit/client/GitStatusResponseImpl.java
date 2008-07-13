package edu.nyu.cs.javagit.client;

import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * Implementation of a <code>GitStatusResponse</code>. This class adds functionality to set
 * values in a <code>GitStatusResponse</code>
 */
public class GitStatusResponseImpl extends GitStatusResponse {
  
  /**
   * Constructor
   */
  public GitStatusResponseImpl() {
    super();
  }
  
  /**
   * Sets the branch name in &lg;GitStatusResponse&gt;. NullPointerException is
   * thrown if branch is null;
   * @param branch
   */
  public void setBranch(String branch) {
    CheckUtilities.checkStringArgument(branch, "Branch name");
    this.branch = branch;
  }
  
  /**
   * Sets the <code>String</code> message in &lg;GitStatusResponse&gt;. <code>NullPointerException</code>
   * is thrown if message is null.
   * @param message
   */
  public void setMessage(String message) {
    CheckUtilities.checkStringArgument(message, "Commit Message");
    this.message = message;
  }
  
  /**
   * Sets the error message in the response object.
   * @param errorMsg
   */
  public void setError(String errorMsg ) {
    CheckUtilities.checkStringArgument(errorMsg, "Error Msg String");
    this.error = errorMsg;
  }
  
  /**
   * Adds a file to newFilesToCommit List.
   * @param filename
   */
  public void addToNewFilesToCommit(String filename) {
    newFilesToCommit.add(filename);
  }
  
  /**
   * Adds a file to deletedFilesToCommit list.
   * @param filename
   */
  public void addToDeletedFilesToCommit(String filename) {
    deletedFilesToCommit.add(filename);
  }
  
  /**
   * Adds a file to modifiedFilesToCommit list.
   * @param filename
   */
  public void addToModifiedFilesToCommit(String filename) {
    modifiedFilesToCommit.add(filename);
  }
  
  /**
   * Adds a file to deletedFilesNotUpdated list.
   * @param filename
   */
  public void addToDeletedFilesNotUpdated(String filename) {
    deletedFilesNotUpdated.add(filename);
  }
  
  /**
   * Adds a file to modifiedFilesNotUpdated list.
   * @param filename
   */
  public void addToModifiedFilesNotUpdated(String filename) {
    modifiedFilesNotUpdated.add(filename);
  }
  
  /**
   * Adds a file to untrackedFiles list.
   * @param filename
   */
  public void addToUntrackedFiles(String filename) {
    untrackedFiles.add(filename);
  }
  
}
