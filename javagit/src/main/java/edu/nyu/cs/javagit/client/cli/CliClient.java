package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitAdd;
import edu.nyu.cs.javagit.client.IGitBranch;
import edu.nyu.cs.javagit.client.IGitCommit;
import edu.nyu.cs.javagit.client.IGitDiff;
import edu.nyu.cs.javagit.client.IGitGrep;
import edu.nyu.cs.javagit.client.IGitLog;
import edu.nyu.cs.javagit.client.IGitMv;
import edu.nyu.cs.javagit.client.IGitReset;
import edu.nyu.cs.javagit.client.IGitRevert;
import edu.nyu.cs.javagit.client.IGitRm;
import edu.nyu.cs.javagit.client.IGitShow;
import edu.nyu.cs.javagit.client.IGitStatus;

/**
 * Command-line implementation of the <code>IClient</code> interface.
 */
public class CliClient implements IClient {

  public IGitAdd getGitAddInstance() {
    return new CliGitAdd();
  }

  public IGitCommit getGitCommitInstance() {
    return new CliGitCommit();
  }

  public IGitDiff getGitDiffInstance() {
    return new CliGitDiff();
  }

  public IGitGrep getGitGrepInstance() {
    return new CliGitGrep();
  }

  public IGitLog getGitLogInstance() {
    return new CliGitLog();
  }

  public IGitMv getGitMvInstance() {
    return new CliGitMv();
  }

  public IGitReset getGitResetInstance() {
    return new CliGitReset();
  }

  public IGitRevert getGitRevertInstance() {
    return new CliGitRevert();
  }

  public IGitRm getGitRmInstance() {
    return new CliGitRm();
  }

  public IGitShow getGitShowInstance() {
    return new CliGitShow();
  }

  public IGitStatus getGitStatusInstance() {
    return new CliGitStatus();
  }
  
  public IGitBranch getGitBranchInstance() {
    return new CliGitBranch();
  }

}
