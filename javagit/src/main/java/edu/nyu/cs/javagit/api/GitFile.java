package edu.nyu.cs.javagit.api;

import java.io.File;
import java.io.IOException;

import edu.nyu.cs.javagit.api.commands.GitStatus;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;

/**
 * <code>GitFile</code> a file object in a git working tree.
 */
public class GitFile extends GitFileSystemObject {
  /**
   * The constructor. Both arguments are required (i.e. cannot be null).
   * 
<<<<<<< .working
   * @param file
   *          underlying <code>java.io.File</code> object
=======
   * @param dir
   *          The underlying {@link java.io.File} object that we want to augment with git
   *          functionality.
   * @param workingTree
   *          The <code>WorkingTree</code> that this file falls under.
   * 
>>>>>>> .merge-right.r482
   */
  protected GitFile(File file, WorkingTree workingTree) {
    super(file, workingTree);
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
   * @return {@link status} (untracked, changed but not updated, etc)
   */
  public Status getStatus() throws IOException, JavaGitException {
    GitStatus gitStatus = new GitStatus();
    // run git-status command
    GitStatusResponse response = gitStatus.getSingleFileStatus(workingTree.getPath(), file);

    /*
     * TODO: quote from Michael Schidlowsky: "this block of if statements is a little smelly... I'd
     * prefer to see something like return response.asStatus()...
     */
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

    // default
    return Status.IN_REPOSITORY;
  }
}
