package edu.nyu.cs.javagit.api.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GitBranchResponse implements CommandResponse {
  //The list of branches in the response of git-branch.
  protected List<ListOfBranches> branchList;
  
  //String Buffer to store the comment message after execution of git-branch command.
  private StringBuffer message = new StringBuffer();
  
  /**
   * Constructor. Sets the status as true if git-branch command executed successfully else false,
   * in the GitBranchResponse object.
   *  
   * @param status
   */
  public GitBranchResponse() {
    branchList = new ArrayList<ListOfBranches>();
  }
  
  /**
   * Get an <code>Iterator</code> with which to iterate over the branch list.
   * 
   * @return An <code>Iterator</code> with which to iterate over the branch list.
   */
  public Iterator<ListOfBranches> getBranchListIterator() {
    return branchList.iterator();
  }
  
  /**
   * Add the branch displayed by git-branch command into the list of branches.
   * 
   * @return true after the file gets added.
   */
  public boolean addIntoBranchList(String branchName, boolean isCurrentBranch) {
     return branchList.add(new ListOfBranches(branchName, isCurrentBranch));
  }
  /**
   * @return the message
   */
  public String getComment() {
    return message.toString();
  }

  /**
   * @param message the message to set
   */
  public void addComment(String comment) {
    message.append(comment);
  }
  
  public class ListOfBranches {
    /**A boolean variable <code>currentBranch</code> set to true if the branch in the list is the 
     *current branch.
     */
    private boolean currentBranch;
    
    //The name of the branch.
    private String branchName;
    
    /**
     * @param branchName the branchName to set
     * 
     * @param isCurrentBranch the currentBranch to set.
     */
    public ListOfBranches(String branchName, boolean isCurrentBranch) {
      this.branchName = branchName;
      this.currentBranch = isCurrentBranch;
    }

    /**
     * @return the currentBranch
     */
    public boolean isCurrentBranch() {
      return currentBranch;
    }

    /**
     * @return the branchName
     */
    public String getBranchName() {
      return branchName;
    }
  }
}
