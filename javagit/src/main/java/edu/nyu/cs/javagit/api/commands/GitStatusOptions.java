package edu.nyu.cs.javagit.api.commands;

public class GitStatusOptions {
  /**
   * The git-status command takes the same set of options as 
   * git-commit; it shows what would be committed if 
   * the same options are given to git-commit.
   */

  protected boolean optQuite = false;
  protected boolean optVerbose = false;
  protected boolean optSignOff = false;
  protected boolean optEdit = false;
  protected boolean optAll = false;
  protected boolean optInclude = false;
  protected boolean optOnly = false;
  protected boolean optNoVerify = false;
  protected boolean optUntrackedFiles = false;
  protected boolean optAllowEmpty = false;
  protected boolean optAmmend = false;
  protected String optReadFromLogFile = null;
  protected String message = null;
  protected String reuseMessageFromCommit = null;
  protected String templateFile = null;
  protected String author = null;
  protected String reEditMsg = null;
  protected String reUseMsg = null;

  public boolean isOptQuite() {
    return optQuite;
  }

  public void setOptQuite(boolean optQuite) {
    this.optQuite = optQuite;
  }

  public boolean isOptVerbose() {
    return optVerbose;
  }

  public void setOptVerbose(boolean optVerbose) {
    this.optVerbose = optVerbose;
  }

  public boolean isOptReadFromLogFileNull() {
    return optReadFromLogFile == null;
  }

  public void setOptReadFromLogFile(String optReadFromLogFile) {
    this.optReadFromLogFile = optReadFromLogFile;
  }
  
  public String getOptReadFromLogFile() {
    return optReadFromLogFile;
  }

  public boolean isOptSignOff() {
    return optSignOff;
  }

  public void setOptSignOff(boolean signOff) {
  this.optSignOff = signOff;
  }

  public boolean isOptEdit() {
    return optEdit;
  }

  public void setOptEdit(boolean edit) {
    this.optEdit = edit;
  }

  public boolean isOptAll() {
    return optAll;
  }

  public void setOptAll(boolean all) {
    this.optAll = all;
  }

  public boolean isOptInclude() {
    return optInclude;
  }

  public void setOptInclude(boolean include) {
    this.optInclude = include;
  }

  public boolean isOptOnly() {
    return optOnly;
  }

  public void setOptOnly(boolean only) {
    this.optOnly = only;
  }

  public boolean isOptNoVerify() {
    return optNoVerify;
  }

  public void setOptNoVerify(boolean noVerify) {
    this.optNoVerify = noVerify;
  }

  public boolean isOptUntrackedFiles() {
    return optUntrackedFiles;
  }

  public void setOptUntrackedFiles(boolean untrackedFiles) {
    this.optUntrackedFiles = untrackedFiles;
  }

  public boolean isOptAllowEmpty() {
    return optAllowEmpty;
  }

  public void setOptAllowEmpty(boolean allowEmpty) {
    this.optAllowEmpty = allowEmpty;
  }

  public boolean isOptAmmend() {
    return optAmmend;
  }

  public void setOptAmmend(boolean ammend) {
    this.optAmmend = ammend;
  }

  public boolean isMessageNull()
  {
    return message == null;
  }
  
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
  public boolean isTemplateFileNull() {
    return templateFile == null;
  }

  public String getTemplateFile() {
    return templateFile;
  }

  public void setTemplateFile(String templateFile) {
    this.templateFile = templateFile;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public boolean isAuthorNull() {
    return author == null;
  }

  public boolean isReEditMsgNull() {
    return reEditMsg == null;
  }
  
  public String getReEditMsg() {
    return reEditMsg;
  }

  public void setReEditMsg(String reEditMsg) {
    this.reEditMsg = reEditMsg;
  }

  public boolean isReUseMsgNull() {
    return reUseMsg == null;
  }
  
  public String getReUseMsg() {
    return reUseMsg;
  }

  public void setReUseMsg(String reUseMsg) {
    this.reUseMsg = reUseMsg;
  }
  
  
}
