package edu.nyu.cs.javagit.client;

import java.io.File;

import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.api.commands.GitResetResponse;

/**
 * Implementation of a <code>GitResetResponse</code> abstract class. This class adds functionality
 * to set values in a <code>GitResetResponse</code>.
 */
public class GitResetResponseImpl extends GitResetResponse {

  // TODO (jhl388): Add test cases for this class.

  /**
   * Default constructor.
   */
  public GitResetResponseImpl() {
    super();
  }

  public GitResetResponseImpl(Ref newHeadSha1, String newHeadShortMessage) {
    super(newHeadSha1, newHeadShortMessage);
  }

  /**
   * Adds the file to the files needing update list.
   * 
   * @param file
   *          The file to add to the files needing update list.
   */
  public void addFileToFilesNeedingUpdateList(File file) {
    filesNeedingUpdate.add(file);
  }

}
