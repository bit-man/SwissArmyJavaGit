package edu.nyu.cs.javagit.api.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.nyu.cs.javagit.api.Ref;

/**
 * A response data object for the git-branch command.
 */
public class GitBranchResponse implements CommandResponse {
  /**
   * An enumeration of the types of response. In normal case a list of branches, otherwise some
   * message such as "Deleted branch".
   */
  public static enum responseType {
    BRANCH_LIST, MESSAGE, EMPTY
  }
  
  // The list of branches in the response of git-branch.
  private List<Ref> branchList;
  
  private List<BranchRecord> listOfBranchRecord;

  // String Buffer to store the message after execution of git-branch command.
  private StringBuffer messages = new StringBuffer();

  // Variable to store the current branch.
  private Ref currentBranch;

  // The type of this response.
  private responseType responseType;

  /**
   * Constructor.
   */
  public GitBranchResponse() {
    branchList = new ArrayList<Ref>();
    listOfBranchRecord = new ArrayList<BranchRecord>();
  }

  /**
   * Get an <code>Iterator</code> with which to iterate over the branch list.
   * 
   * @return An <code>Iterator</code> with which to iterate over the branch list.
   */
  public Iterator<Ref> getBranchListIterator() {
    return (new ArrayList<Ref>(branchList).iterator());
  }

  /**
   * Add the branch displayed by git-branch command into the list of branches.
   * 
   * @return true after the file gets added.
   */
  public boolean addIntoBranchList(Ref branchName) {
    return branchList.add(branchName);
  }

  /**
   * Get an <code>Iterator</code> with which to iterate over the branch record list.
   * 
   * @return An <code>Iterator</code> with which to iterate over the branch record list.
   */
  public Iterator<BranchRecord> getListOfBranchRecordIterator() {
    return (new ArrayList<BranchRecord>(listOfBranchRecord).iterator());
  }

  /**
   * Add the record displayed by git-branch command with -v option into the list of records.
   * 
   * @return True after the record gets added.
   */
  public boolean addIntoListOfBranchRecord(BranchRecord record) {
    return listOfBranchRecord.add(record);
  }
  
  /**
   * Gets the type of the response. Branch list, message or empty.
   * 
   * @return The responseType.
   */
  public responseType getResponseType() {
    return responseType;
  }

  /**
   * Sets the type of the response.
   * 
   * @param responseType
   *          The responseType to set to one of the three types.
   */
  public void setResponseType(responseType responseType) {
    this.responseType = responseType;
  }

  /**
   * Gets a message about the git-branch operation that was run.
   *
   * @return A message about the git-branch operation that was run. 
   */
  public String getMessages() {
    return messages.toString();
  }

  /**
   * Sets a message about the git-branch operation that was run.
   *
   * @param message
   *          A message about the git-branch operation that was run.
   */
  public void addMessages(String message) {
    messages.append(message);
  }

  /**
   * Gets the current branch from the list of branches displayed by git-branch operation.
   * 
   * @return The current branch from the list of branches displayed by git-branch operation.
   */
  public Ref getcurrentBranch() {
    return currentBranch;
  }

  /**
   * Sets the current branch from the list of branches displayed by git-branch operation.
   * 
   * @param currentBranch
   *          The current branch from the list of branches displayed by git-branch operation.
   */
  public void setCurrentBranch(Ref currentBranch) {
    this.currentBranch = currentBranch;
  }
  
  public static class BranchRecord {
    private Ref branch;
    
    // The SHA Refs of a branch in the response of git-branch with -v option.
    private Ref sha1;
    
    // String Buffer to store the comment after execution of git-branch command with -v option.
    private String comment;
    
    // Variable to store the current branch.
    private boolean isCurrentBranch;

    /**
     * Gets the branch from the record.
     * 
     * @return The branch from the record.
     */
    public Ref getBranch() {
      return branch;
    }

    /**
     * Sets the branch in the record.
     * 
     * @param branch The branch to set in the record.
     */
    public void setBranch(Ref branch) {
      this.branch = branch;
    }

    /**
     * Gets the SHA1 from the record.
     * 
     * @return The SHA1 from the record.
     */
    public Ref getSha1() {
      return sha1;
    }

    /**
     * Sets the SHA1 in the record.
     * 
     * @param sha1 The SHA1 to set, in the record.
     */
    public void setSha1(Ref sha1) {
      this.sha1 = sha1;
    }

    /**
     * Gets the comment of the last commit on a branch or the last commit on the branch it has
     * originated from. Displayed when git-branch is run with -v option.
     * 
     * @return The comment of the recent commit on a branch.
     */
    public String getComment() {
      return comment;
    }

    /**
     * Sets the comment of the last commit on a branch or the last commit on the branch it has 
     * originated from. Displayed when git-branch is run with -v option.
     * 
     * @param The comment of the recent commit on a branch.
     */
    public void setComment(String comment) {
      this.comment = comment;
    }

    /**
     * Gets the current branch from the list of branches displayed by git-branch operation.
     * 
     * @return The current branch from the list of branches displayed by git-branch operation.
     */
    public boolean isCurrentBranch() {
      return isCurrentBranch;
    }

    /**
     * Sets the current branch from the list of branches displayed by git-branch operation.
     * 
     * @param currentBranch
     *          The current branch from the list of branches displayed by git-branch operation.
     */
    public void setCurrentBranch(boolean isCurrentBranch) {
      this.isCurrentBranch = isCurrentBranch;
    }
  }
}
