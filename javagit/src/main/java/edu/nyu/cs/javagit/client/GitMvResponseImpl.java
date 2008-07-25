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

import java.io.File;

import edu.nyu.cs.javagit.api.commands.GitMvResponse;

public class GitMvResponseImpl extends GitMvResponse {

  /**
   * Adds comments from each line of the message received upon successful execution of the git-mv
   * command, to the message buffer.
   * 
   * @param comment
   */
  public void addComment(String comment) {
    message.append(comment);
  }

  /**
   * Sets the destination file/folder/symlink in response to the destination
   * 
   * @param destination
   *          the destination to set
   */
  public void setDestination(File destination) {
    this.destination = destination;
  }

  /**
   * Sets the source file/folder/symlink in response object to the source string.
   * 
   * @param source
   *          the source to set
   */
  public void setSource(File source) {
    this.source = source;
  }

}
