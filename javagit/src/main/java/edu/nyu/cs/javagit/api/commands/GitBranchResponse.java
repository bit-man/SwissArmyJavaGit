package edu.nyu.cs.javagit.api.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.nyu.cs.javagit.api.Ref;

/**
 * A response data object for the git-branch command.
 */
public class GitBranchResponse implements CommandResponse {
  //The list of branches in the response of git-branch.
  private List<Ref> branchList;
  
  //The list of SHA refs in the response of git-branch.
  private List<Ref> shaList;
  
  //String Buffer to store the comment after execution of git-branch command with -v option.
  private StringBuffer comments = new StringBuffer();
  
  //String Buffer to store the message after execution of git-branch command.
  private StringBuffer messages = new StringBuffer();
  
  //Variable to store the current branch.
  private Ref currentBranch;
  
  /**
   * An enumeration of the types of response. In normal case a list of branches, otherwise 
   * some message such as "Deleted branch".
   */
  public static enum responseType {
    BRANCH_LIST, MESSAGE, EMPTY
  }
  
  //The type of this response.
  private responseType responseType;
  
  /**
   * Constructor. Sets the status as true if git-branch command executed successfully else false,
   * in the GitBranchResponse object.
   *  
   * @param status
   */
  public GitBranchResponse() {
    branchList = new ArrayList<Ref>();
    shaList = new ArrayList<Ref>();
  }
  
  /**
   * Get an <code>Iterator</code> with which to iterate over the branch list.
   * 
   * @return An <code>Iterator</code> with which to iterate over the branch list.
   */
  public Iterator<Ref> getBranchListIterator() {
    return branchList.iterator();
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
   * Get an <code>Iterator</code> with which to iterate over the Sha list.
   * 
   * @return An <code>Iterator</code> with which to iterate over the Sha list.
   */
  public Iterator<Ref> getShaListIterator() {
    return shaList.iterator();
  }
  
  /**
   * Add the Sha displayed by git-branch command into the list of Sha refs.
   * 
   * @return true after the sha ref gets added.
   */
  public boolean addIntoShaList(Ref sha) {
     return shaList.add(sha);
  }
  
  /**
   * @return the message
   */
  public String getComments() {
    return comments.toString();
  }

  /**
   * @param message the message to set
   */
  public void addComments(String comment) {
    comments.append(comment);
  }

  /**
   * @return the responseType
   */
  public responseType getResponseType() {
    return responseType;
  }

  /**
   * @param responseType the responseType to set
   */
  public void setResponseType(responseType responseType) {
    this.responseType = responseType;
  }

  /**
   * @return the message
   */
  public String getMessages() {
    return messages.toString();
  }

  /**
   * @param message the message to set
   */
  public void addMessages(String message) {
    messages.append(message);
  }

  /**
   * @return the currentBranch
   */
  public Ref getcurrentBranch() {
    return currentBranch;
  }

  /**
   * @param currentBranch the currentBranch to set
   */
  public void setCurrentBranch(Ref currentBranch) {
    this.currentBranch = currentBranch;
  }
}
