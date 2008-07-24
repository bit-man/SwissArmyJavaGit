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
