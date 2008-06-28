package edu.nyu.cs.javagit.api.commands;

public class GitMvResponse {

  //Variable to store the status of execution.
  private boolean status;
 
  //private String errorMessage;
  
  //Variable to store the source file/folder/symlink of the response.
  private String source;
  
  //Variable to store the destination file/folder/symlink of the response.
  private String destination;
  
  //String Buffer to store the comment message after execution of git-mv.
  private StringBuffer message = new StringBuffer();

  /**
   * Constructor. Sets the status as true if command executed successfully else false, in the
   * GitMvResponse object.
   * 
   * @param status
   */
  public GitMvResponse(boolean status) {
	  this.status = status;
  }
  
  /**
   * Gets the status of the command (true or false) from GitMvResponse object.
   * 
   * @return status
   */
  public boolean IsSuccess() {
	  return status;
  }

  /**
   * Sets the status message as true or false.
   * 
   * @param status
   */
  public void setStatus(boolean status) {
    this.status = status;
  }
  /**
   * Adds comments from each line of the message received upon successful execution
   * of the git-mv command, to the message buffer.
   * 
   * @param comment
   */
  public void addComment(String comment) {
    message.append(comment);
  }

  /**
   * Gets the comments received upon successful execution of the git-mv command, from 
   * the message buffer.
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
  public String getDestination() {
    return destination;
  }

  /**
   * Sets the destination file/folder/symlink in response to the destination
   * 
   * @param destination the destination to set
   */
  public void setDestination(String destination) {
    this.destination = destination;
  }

  /**
   * Gets the source file/folder/symlink
   * 
   * @return the source
   */
  public String getSource() {
    return source;
  }

  /**
   * Sets the source file/folder/symlink in response object to the source string.
   * 
   * @param source the source to set
   */
  public void setSource(String source) {
    this.source = source;
  }
}
