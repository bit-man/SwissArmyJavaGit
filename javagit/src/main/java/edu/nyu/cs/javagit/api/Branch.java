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
package edu.nyu.cs.javagit.api;

/**
 * <code>Branch</code> represents a branch in a git repository.
 * 
 * TODO: Build out the class
 */
public class Branch {
  private String branchName;

  /**
   * The constructor.
   * 
   * @param name
   *          The name of the branch
   */
  public Branch(String name) {
    this.branchName = name;
  }

  /**
   * Gets the name of the branch.
   * 
   * @return The name of the branch.
   */
  public String getBranchName() {
    return branchName;
  }

  /**
   * Gets the directory at the root of this branch.
   * 
   * @return The directory at the root of the branch.
   */
  public GitDirectory getBranchRoot() {
    return null;
  }

  /**
   * Moves the branch
   * 
   * @param newName
   *          The name of the new branch
   */
  public void mv(String newName) {
    // GitBranch.branch(-m);
  }
}
