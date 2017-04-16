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
  IGitAdd getGitAddInstance();

  /**
   * Gets an instance of <code>IGitCommit</code>.
   * 
   * @return An instance of <code>IGitCommit</code>.
   */
  IGitCommit getGitCommitInstance();

  /**
   * Gets an instance of <code>IGitDiff</code>.
   * 
   * @return An instance of <code>IGitDiff</code>.
   */
  IGitDiff getGitDiffInstance();

  /**
   * Gets an instance of <code>IGitGrep</code>.
   * 
   * @return An instance of <code>IGitGrep</code>.
   */
  IGitGrep getGitGrepInstance();

  /**
   * Gets an instance of <code>IGitLog</code>.
   * 
   * @return An instance of <code>IGitLog</code>.
   */
  IGitLog getGitLogInstance();

  /**
   * Gets an instance of <code>IGitMv</code>.
   * 
   * @return An instance of <code>IGitMv</code>.
   */
  IGitMv getGitMvInstance();

  /**
   * Gets an instance if <code>IGitReset</code>.
   * 
   * @return An instance of <code>IGitReset</code>
   */
  IGitReset getGitResetInstance();

  /**
   * Gets an instance of <code>IGitRevert</code>.
   * 
   * @return An instance of <code>IGitRevert</code>.
   */
  IGitRevert getGitRevertInstance();

  /**
   * Gets an instance of <code>IGitRm</code>.
   * 
   * @return An instance of <code>IGitRm</code>.
   */
  IGitRm getGitRmInstance();

  /**
   * Gets an instance of <code>IGitShow</code>.
   * 
   * @return An instance of <code>IGitShow</code>.
   */
  IGitShow getGitShowInstance();

  /**
   * Gets an instance of <code>IGitStatus</code>.
   * 
   * @return An instance of <code>IGitStatus</code>.
   */
  IGitStatus getGitStatusInstance();

  /**
   * Gets an instance of <code>IGitBranch</code>
   * 
   * @return An instance of <code>IGitBranch</code>
   */
  IGitBranch getGitBranchInstance();

  /**
   * Gets an instance of <code>IGitCheckout</code>
   * 
   * @return An instance of <code>IGitCheckout</code>
   */
  IGitCheckout getGitCheckoutInstance();
  
  /**
   * Gets an instance of <code>IGitInit</code>
   * 
   * @return An instance of <code>IGitInit</code>
   */
  IGitInit getGitInitInstance();
  
  /**
   * Gets an instance of <code>IGitClone</code>
   * 
   * @return An instance of <code>IGitClone</code>
   */
  IGitClone getGitCloneInstance();

    /**
     * Gets an instance of <code>IGitFetch</code>
     *
     * @return An instance of <code>IGitFetch</code>
     */
  IGitFetch getGitFetchInstance();


  /**
   * Gets an instance of <code>IGitPull</code>
   *
   * @return An instance of <code>IGitPull</code>
   */
  IGitPull getGitPullInstance();

  /**
   * Returns instance for generic git command
   *
   * @param command
   * @return
   */
  IGitCommand getGitCommandInstance(String command);
}
