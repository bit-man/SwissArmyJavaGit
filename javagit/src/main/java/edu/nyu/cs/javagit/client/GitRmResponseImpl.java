package edu.nyu.cs.javagit.client;

import java.io.File;

import edu.nyu.cs.javagit.api.commands.GitRmResponse;

/**
 * Implementation of <code>GitRmResponse</code> that includes setter methods for all fields.
 */
public final class GitRmResponseImpl extends GitRmResponse {

  // TODO (jhl388): Add test cases for this class.

  /**
   * Adds the file to the removed files list.
   * 
   * @param file
   *          The file to add to the removed files list.
   */
  public void addFileToRemovedFilesList(File file) {
    removedFiles.add(file);
  }
  
}
