package edu.nyu.cs.javagit.client;

import edu.nyu.cs.javagit.api.commands.GitCheckoutResponse;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * This class implements <code>GitCheckoutResponse</code> by setting values in
 * <code>GitCheckoutResponse</code>.
 */
public class GitCheckoutResponseImpl extends GitCheckoutResponse {

  /**
   * Sets the error parsed from the execution of &lt;git-checkout&gt; command.
   * 
   * @param error
   *          error string
   */
  public void setError(String error) {
    this.error = error;
  }

  /**
   * Sets the new branch name that is created by &lt;git-checkout&gt using -b option
   * 
   * @param newBranch
   *          Name of the new branch created
   */
  public void setNewBranch(String newBranch) {
    this.newBranch = newBranch;
  }

  /**
   * Sets the branch to the branch, to which the &lt;git-checkout&gt switched the repository to. This
   * branch should already be existing in the repository. To create a new branch and switch to it,
   * use the -b option while running &lt;git-checkout&gt.
   * 
   * @param branch
   */
  public void setBranch(String branch) {
    this.branch = branch;
  }

  /**
   * Adds the modified file to the list of modifiedFiles. When a file is modified locally but has
   * not been committed to the repository and if we try to switch the branch to another branch, the
   * &lt;git-checkout&gt fails and outputs the list of modified files that are not yet committed unless -f
   * option is used by &lt;git-checkout&gt.
   * 
   * @param file
   */
  public void addModifiedFile(String file) {
    CheckUtilities.checkStringArgument(file, "Modified File");
    modifiedFiles.add(file);
  }

  /**
   * Adds the newly added file to the list of addedFiles. A newly added file is the one that is
   * added by &lt;git-add&gt; command but had not been committed.
   * 
   * @param file
   */
  public void addAddedFile(String file) {
    CheckUtilities.checkStringArgument(file, "Newly Added file");
    addedFiles.add(file);
  }

  /**
   * Adds the locally deleted file to the list of deletedFiles. A locally deleted file is one that
   * has been removed but has not been removed from repository using &lt;git-rm&gt; command.
   * 
   * @param file
   */
  public void addDeletedFile(String file) {
    CheckUtilities.checkStringArgument(file, "Deleted File");
    deletedFiles.add(file);
  }

}
