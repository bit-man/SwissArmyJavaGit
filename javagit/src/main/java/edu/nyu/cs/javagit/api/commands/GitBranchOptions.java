package edu.nyu.cs.javagit.api.commands;

public class GitBranchOptions {
  //The -v or --verbose option
  private boolean optVerbose;
  
  //The --track option
  private boolean optTrack;
  
  //The --no-track option. Ignore the branch.autosetupmerge configuration variable.
  private boolean optNoTrack;
  
  //The color option. Color branches to highlight current, local, and remote branches
  private boolean optColor;
  
  /**The --no-color option. Turn off branch colors, even when the configuration file gives the
   *default to color output.
   */
  private boolean optNoColor;
  
  //The -r (remote-tracking) option
  private boolean optR;
  
  //The --contains option. Prints only branches that contain the commit
  private String optContains = null;
  
  //The --abbrev option. --abbrev [<n>]   use <n> digits to display SHA-1s
  private String optAbbrev = null;
  
  //The -no-abbrev option. Displays the full sha1s in output listing rather than abbreviating them.
  private boolean optNoAbbrev;

  //The -a option. List both remote-tracking and local branches.
  private boolean optA;
  
  //The -d option. delete fully merged branch.
  private boolean optSmallD;
  
  //The -D option. Delete branch (even if not merged).
  private boolean optBigD;
  
  //The -m option. move/rename a branch and its reflog.
  private boolean optSmallM;
  
  //The -M option. move/rename a branch, even if target exists.
  private boolean optBigM;
  
  //The -l option. create the branch's reflog.
  private boolean optL;
  
  //The -f option. force creation (when already exists).
  private boolean optF;
  
  //The --merged option. list only branches merged with HEAD.
  private boolean optMerged;
  
  //The --no-merged option. list only branches not merged with HEAD.
  private boolean optNoMerged;

  /**
   * @return the optVerbose
   */
  public boolean isOptVerbose() {
    return optVerbose;
  }

  /**
   * @param optVerbose the optVerbose to set
   */
  public void setOptVerbose(boolean optVerbose) {
    this.optVerbose = optVerbose;
  }

  /**
   * @return the optTrack
   */
  public boolean isOptTrack() {
    return optTrack;
  }

  /**
   * @param optTrack the optTrack to set
   */
  public void setOptTrack(boolean optTrack) {
    this.optTrack = optTrack;
    if (optTrack) {
      this.optNoTrack = false;
    }
  }

  /**
   * @return the optNoTrack
   */
  public boolean isOptNoTrack() {
    return optNoTrack;
  }

  /**
   * @param optNoTrack the optNoTrack to set
   */
  public void setOptNoTrack(boolean optNoTrack) {
    this.optNoTrack = optNoTrack;
    if (optNoTrack) {
      this.optTrack = false;
    }
  }

  /**
   * @return the optColor
   */
  public boolean isOptColor() {
    return optColor;
  }

  /**
   * @param optColor the optColor to set
   */
  public void setOptColor(boolean optColor) {
    this.optColor = optColor;
    if (optColor) {
      this.optNoColor = false;
    }
  }

  /**
   * @return the optNoColor
   */
  public boolean isOptNoColor() {
    return optNoColor;
  }

  /**
   * @param optNoColor the optNoColor to set
   */
  public void setOptNoColor(boolean optNoColor) {
    this.optNoColor = optNoColor;
    if (optNoColor) {
      this.optColor = false;
    }
  }

  /**
   * @return the optR
   */
  public boolean isOptR() {
    return optR;
  }

  /**
   * @param optR the optR to set
   */
  public void setOptR(boolean optR) {
    this.optR = optR;
  }

  /**
   * @return the optContains
   */
  public String getOptContains() {
    return optContains;
  }

  /**
   * @param optContains the optContains to set
   */
  public void setOptContains(String optContains) {
    this.optContains = optContains;
  }

  /**
   * @return the optAbbrev
   */
  public String getOptAbbrev() {
    return optAbbrev;
  }

  /**
   * @param optAbbrev the optAbbrev to set
   */
  public void setOptAbbrev(String optAbbrev) {
    this.optAbbrev = optAbbrev;
    if (null != optAbbrev && optAbbrev.length() > 0) {
      this.optNoAbbrev = false;
    }
  }

  /**
   * @return the optNoAbbrev
   */
  public boolean isOptNoAbbrev() {
    return optNoAbbrev;
  }

  /**
   * @param optNoAbbrev the optNoAbbrev to set
   */
  public void setOptNoAbbrev(boolean optNoAbbrev) {
    this.optNoAbbrev = optNoAbbrev;
    if (optNoAbbrev) {
      this.optAbbrev = null;
    }
  }

  /**
   * @return the optA
   */
  public boolean isOptA() {
    return optA;
  }

  /**
   * @param optA the optA to set
   */
  public void setOptA(boolean optA) {
    this.optA = optA;
  }

  /**
   * @return the optSmallD
   */
  public boolean isOptSmallD() {
    return optSmallD;
  }

  /**
   * @param optSmallD the optSmallD to set
   */
  public void setOptSmallD(boolean optSmallD) {
    this.optSmallD = optSmallD;
  }

  /**
   * @return the optBigD
   */
  public boolean isOptBigD() {
    return optBigD;
  }

  /**
   * @param optBigD the optBigD to set
   */
  public void setOptBigD(boolean optBigD) {
    this.optBigD = optBigD;
  }

  /**
   * @return the optSmallM
   */
  public boolean isOptSmallM() {
    return optSmallM;
  }

  /**
   * @param optSmallM the optSmallM to set
   */
  public void setOptSmallM(boolean optSmallM) {
    this.optSmallM = optSmallM;
  }

  /**
   * @return the optBigM
   */
  public boolean isOptBigM() {
    return optBigM;
  }

  /**
   * @param optBigM the optBigM to set
   */
  public void setOptBigM(boolean optBigM) {
    this.optBigM = optBigM;
  }

  /**
   * @return the optL
   */
  public boolean isOptL() {
    return optL;
  }

  /**
   * @param optL the optL to set
   */
  public void setOptL(boolean optL) {
    this.optL = optL;
  }

  /**
   * @return the optF
   */
  public boolean isOptF() {
    return optF;
  }

  /**
   * @param optF the optF to set
   */
  public void setOptF(boolean optF) {
    this.optF = optF;
  }

  /**
   * @return the optMerged
   */
  public boolean isOptMerged() {
    return optMerged;
  }

  /**
   * @param optMerged the optMerged to set
   */
  public void setOptMerged(boolean optMerged) {
    this.optMerged = optMerged;
    if(optMerged) {
      this.optNoMerged = false;
    }
  }

  /**
   * @return the optNoMerged
   */
  public boolean isOptNoMerged() {
    return optNoMerged;
  }

  /**
   * @param optNoMerged the optNoMerged to set
   */
  public void setOptNoMerged(boolean optNoMerged) {
    this.optNoMerged = optNoMerged;
    if(optNoMerged) {
      this.optMerged = false;
    }
  }
}
