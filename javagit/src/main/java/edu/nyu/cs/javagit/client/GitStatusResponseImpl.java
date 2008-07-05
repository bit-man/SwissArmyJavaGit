package edu.nyu.cs.javagit.client;

import edu.nyu.cs.javagit.api.commands.GitStatusResponse;

public class GitStatusResponseImpl extends GitStatusResponse {
  
  public GitStatusResponseImpl() {
    super();
  }
  
  public void setBranch(String branch) {
    this.branch = branch;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public void setError(String errorMsg ) {
    this.error = errorMsg;
  }
  
  public void addToNewFilesToCommit(String filename) {
    newFilesToCommit.add(filename);
  }
  
  public void addToDeletedFilesToCommit(String filename) {
    deletedFilesToCommit.add(filename);
  }
  
  public void addToModifiedFilesToCommit(String filename) {
    modifiedFilesToCommit.add(filename);
  }
  
  public void addToDeletedFilesNotUpdated(String filename) {
    deletedFilesNotUpdated.add(filename);
  }
  
  public void addToModifiedFilesNotUpdated(String filename) {
    modifiedFilesNotUpdated.add(filename);
  }
  
  public void addToUntrackedFiles(String filename) {
    untrackedFiles.add(filename);
  }
  
}
