package edu.nyu.cs.javagit.api.commands;

import java.io.File;

import edu.nyu.cs.javagit.utilities.CheckUtilities;

public class GitMvResponse implements CommandResponse {

  // Variable to store the source file/folder/symlink of the response.
  protected File source;

  // Variable to store the destination file/folder/symlink of the response.
  protected File destination;

  // String Buffer to store the comment message after execution of git-mv.
  protected StringBuffer message = new StringBuffer();

  /**
   * Gets the comments received upon successful execution of the git-mv command, from the message
   * buffer.
   * 
   * @return message
   */
  public String getComment() {
    return message.toString();
  }

  /**
   * Gets the destination file/folder
   * 
   * @return the destination
   */
  public File getDestination() {
    return destination;
  }

  /**
   * Gets the source file/folder/symlink
   * 
   * @return the source
   */
  public File getSource() {
    return source;
  }
  
  public boolean equals(Object o) {
    if (!(o instanceof GitMvResponse)) {
      return false;
    }

    GitMvResponse g = (GitMvResponse) o;

    if (!CheckUtilities.checkObjectsEqual(getSource(), g.getSource())) {
      return false;
    }

    if (!CheckUtilities.checkObjectsEqual(getDestination(), g.getDestination())) {
      return false;
    }

    if (!CheckUtilities.checkObjectsEqual(getComment(), g.getComment())) {
      return false;
    }
    
    return true;
  }
  
  public int hashCode() {
    return source.hashCode() + destination.hashCode() + message.hashCode();
  }
}
