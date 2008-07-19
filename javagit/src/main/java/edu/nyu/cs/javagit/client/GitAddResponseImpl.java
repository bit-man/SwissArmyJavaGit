package edu.nyu.cs.javagit.client;

import java.io.File;

import edu.nyu.cs.javagit.api.commands.GitAddResponse;

/**
 * Class implementing <code>GitAddResponse</code> for setting values in it.
 */
public class GitAddResponseImpl extends GitAddResponse {
  
  /**
   * Sets the value of no output flag.
   * 
   * @param noOutput true if there was no output generated. This is a helper
   * flag that tells the consumer of <code>GitAddResponse</code> that there
   * was no resulting output for executed &lt;git-add&gt; command.
   */
  public void setNoOutput(boolean noOutput) {
    this.noOutput = noOutput;
  }
  
  /**
   * Adds the error string generated to <code>List</code> containing all the errors
   * that were generated at the output.
   * 
   * @param lineNumber Output line number at which error string appeared.
   * @param errorString <code>String</code> with error message.
   */
  public void setError(int lineNumber, String errorString) {
    ResponseString error = new ResponseString(lineNumber, errorString);
    errors.add(error);
  }

  /**
   * Sets the flag if dry run flag need to be used
   * 
   * @param dryRun true if dry run should be used, otherwise false.
   */
  public void setDryRun(boolean dryRun) {
    this.dryRun = dryRun;
  }
  
  /**
   * Sets the non-error message generated in the output of the &lt;git-add&gt; command.
   * 
   * @param lineNumber line number at which the message appeared in output.
   * @param commentString message itself.
   */
  public void setComment(int lineNumber, String commentString) {
    ResponseString comment = new ResponseString(lineNumber, commentString);
    comments.add(comment);
  }
  
  /**
   * Adds a file and action to the list.
   * 
   * @param action
   * @param filePath
   */
  public void add(File file) {
    filePathsList.add(file);
  }

}
