package edu.nyu.cs.javagit.api;

import java.io.File;
import java.io.IOException;

import edu.nyu.cs.javagit.api.GitFileSystemObject.Status;
import edu.nyu.cs.javagit.api.commands.GitStatus;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.utilities.CheckUtilities;
/**
 * <code>GitFile</code> a file object in a git working tree.
 * 
 * TODO: Build out the class
 */
public class GitFile extends GitFileSystemObject {
  /**
   * The constructor.
   * 
   * @param file
   *          underlying <code>java.io.File</code> object
   */
  public GitFile(File file) throws JavaGitException {
    super(file);
  }

  
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GitFile)) {
      return false;
    }

    GitFileSystemObject gitObj = (GitFileSystemObject) obj;
    return super.equals(gitObj);
  }

  
  /**
   * Show object's status in the working directory
   * 
   * @return status (untracked, changed but not updated, etc)
   */
  public Status getStatus() throws IOException, JavaGitException {
    GitStatus gitStatus = new GitStatus();
    // run git-status command
    GitStatusResponse response = gitStatus.statusSingleFile(dotGit.getPath(), file);

    if (response.getUntrackedFilesSize() > 0) {
      return Status.UNTRACKED;
    }
    
    if (response.getNewFilesToCommitSize() > 0) {
      return Status.NEW_TO_COMMIT;
    }

    if (response.getDeletedFilesNotUpdatedSize() > 0) {
      return Status.DELETED;
    }

    if (response.getDeletedFilesToCommitSize() > 0) {
      return Status.DELETED_TO_COMMIT;
    }

    if (response.getModifiedFilesNotUpdatedSize() > 0) {
      return Status.MODIFIED;
    }

    if (response.getModifiedFilesToCommitSize() > 0) {
      return Status.MODIFIED_TO_COMMIT;
    }

    //default
    return Status.IN_REPOSITORY;
  }

}
