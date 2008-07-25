/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.api.Ref.RefType;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * Class for managing options for &lt;git-checkout&gt; command.
 */
public class GitCheckoutOptions {

  /**
   * Create a new branch named &lt;new_branch&gt; and start it at &lt;branch&gt;. if &lt;optB&gt; is
   * null then -b option is not specified.
   */
  Ref optB;

  /**
   * Quiet, suppress feedback messages.
   */
  boolean optQ;

  /**
   * Proceed even if the index or the working tree differs from HEAD. This is used to throw away
   * local changes.
   */
  boolean optF;

  /**
   * When creating a new branch, set up configuration so that <code>git-pull</code> will
   * automatically retrieve data from the start point, which must be a branch. Use this if you
   * always pull from the same upstream branch into the new branch, and if you don't want to use
   * "git pull &lt;repository&gt; &lt;refspec&gt;" explicitly. This behavior is the default when the
   * start point is a remote branch.
   */
  boolean optTrack;
  /**
   * Ignore the branch.autosetupmerge configuration variable.
   */
  boolean optNoTrack;
  /**
   * Create the new branch's reflog. This activates recording of all changes made to the branch ref,
   * enabling use of date based sha1 expressions such as "&lt;branchname&gt;@{yesterday}".
   */
  boolean optL;
  /**
   * If you have local modifications to one or more files that are different between the current
   * branch and the branch to which you are switching, the command refuses to switch branches in
   * order to preserve your modifications in context.However, with this option, a three-way merge
   * between the current branch, your working tree contents, and the new branch is done, and you
   * will be on the new branch.
   */
  boolean optM;

  /**
   * Sets the name of the new branch that need to be created from the base branch.
   * 
   * @param Name
   *          of the new branch.
   */
  public void setOptB(Ref newBranchName) {
    CheckUtilities.validateArgumentRefType(newBranchName, RefType.BRANCH, "New Branch Name");
    optB = newBranchName;
  }

  /**
   * @return Returns the name of the new branch.
   */
  public Ref getOptB() {
    return optB;
  }

  /**
   * Gets the value of Quiet option.
   * 
   * @return true if Quiet option is set, else false.
   */
  public boolean optQ() {
    return optQ;
  }

  /**
   * Sets the Quiet option
   * 
   * @param true
   *          if quiet option should be set.
   */
  public void setOptQ(boolean optQ) {
    this.optQ = optQ;
  }

  /**
   * Gets the value of force option.
   * 
   * @return true if force is set, or false.
   */
  public boolean optF() {
    return optF;
  }

  /**
   * Sets the value of force option
   * 
   * @param true
   *          if force should be set, else false.
   */
  public void setOptF(boolean optF) {
    this.optF = optF;
  }

  /**
   * Gets the value value of track option.
   * 
   * @return true if track option is set, else false.
   */
  public boolean optTrack() {
    return optTrack;
  }

  /**
   * Sets the track option.
   * 
   * @param true
   *          if the track option should be set, else false.
   */
  public void setOptTrack(boolean optTrack) {
    this.optTrack = optTrack;
  }

  /**
   * Gets the notrack option.
   * 
   * @return true if notrack option is set, else false.
   */
  public boolean optNoTrack() {
    return optNoTrack;
  }

  /**
   * Sets the noTrack option.
   * 
   * @param true
   *          if noTrack options need to be set, else false.
   */
  public void setOptNoTrack(boolean optNoTrack) {
    this.optNoTrack = optNoTrack;
  }

  /**
   * Gets the reflog option for the new branch.
   * 
   * @return
   */
  public boolean optL() {
    return optL;
  }

  /**
   * Sets the reflog option for the newbranch.
   * 
   * @param true
   *          if reflog option should be set, else false.
   */
  public void setOptL(boolean optL) {
    this.optL = optL;
  }

  /**
   * Gets the merge option
   * 
   * @return true if merge option to be used, else false
   */
  public boolean optM() {
    return optM;
  }

  /**
   * Sets the merge option.
   * 
   * @param true
   *          if merge need to be used, else false.
   */
  public void setOptM(boolean optM) {
    this.optM = optM;
  }

}
