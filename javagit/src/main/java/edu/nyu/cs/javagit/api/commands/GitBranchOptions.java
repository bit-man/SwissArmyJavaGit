package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;
/**
 * A class to manage passing branch options to the <code>GitBranch</code> command.
 */
public class GitBranchOptions {
  
  //Enum class for optionType. There are 4 categories.
  public static enum optionType {
    NO_ARG, BRANCHNAME_STARTPOINT, BRANCHLIST, OLDBRANCH_NEWBRANCH
  }
  
  /*
   * Generic Options.
   */
  //The -v or --verbose option
  private boolean optVerbose = false;
  
  //The --track option
  private boolean optTrack = false;
  
  //The --no-track option. Ignore the branch.autosetupmerge configuration variable.
  private boolean optNoTrack = false;
  
  //The color option. Color branches to highlight current, local, and remote branches
  private boolean optColor = false;
  
  /**The --no-color option. Turn off branch colors, even when the configuration file gives the
   *default to color output.
   */
  private boolean optNoColor = false;
  
  //The -r (remote-tracking) option
  private boolean optR = false;
  
  //The --contains option. Prints only branches that contain the commit
  private String optContains;
  
  //The --abbrev option. --abbrev to set with default <n>.
  private boolean optAbbrev = false;
  
  //The --abbrev option. --abbrev [<n>]   use <n> digits to display SHA-1s
  private String optAbbrevVal;
  
  //The -no-abbrev option. Displays the full sha1s in output listing rather than abbreviating them.
  private boolean optNoAbbrev = false;
  
  /*
   * Non-generic options.
   */
  //The -a option. List both remote-tracking and local branches.
  private boolean optA = false;
  
  //The -d option. delete fully merged branch.
  private boolean optSmallD = false;
  
  //The -D option. Delete branch (even if not merged).
  private boolean optBigD = false;
  
  //The -m option. move/rename a branch and its reflog.
  private boolean optSmallM = false;
  
  //The -M option. move/rename a branch, even if target exists.
  private boolean optBigM = false;
  
  //The -l option. create the branch's reflog.
  private boolean optL = false;
  
  //The -f option. force creation (when already exists).
  private boolean optF = false;
  
  //The --merged option. list only branches merged with HEAD.
  private boolean optMerged = false;
  
  //The --no-merged option. list only branches not merged with HEAD.
  private boolean optNoMerged = false;
  
  
  //Option class. One of the four enum values.
  private optionType optType;
  
  /**
   * @return the optType
   */
  public optionType getOptType() {
    return optType;
  }

  /**
   * @param optType the optType to set
   */
  public void setOptType(optionType optType) {
    this.optType = optType;
  }

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
    if ((optionType.NO_ARG == optType) && optVerbose) {
      this.optVerbose = optVerbose;
    } else if (optVerbose) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000120")
          + "  --verbose should be used when there are no arguments.");
    } else {
      this.optVerbose = optVerbose;
    }
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
    if ((optionType.BRANCHNAME_STARTPOINT == optType) && optTrack) {
      this.optTrack = optTrack;
      this.optNoTrack = false;
    } else if (optTrack) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000120")
          + "  --track should be used when there are arguments, <branchname> and optional " +
          		"<starpoint>");
    } else {
      this.optTrack = optTrack;
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
    if ((optionType.BRANCHNAME_STARTPOINT == optType) && optNoTrack) {
      this.optNoTrack = optNoTrack;
      this.optTrack = false;
    } else if (optNoTrack) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000120")
          + "  --no-track should be used when there are arguments, <branchname> and optional " +
              "<starpoint>");
    } else {
      this.optNoTrack = optNoTrack;
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
    if ((optionType.NO_ARG == optType) && optColor) {
      this.optColor = optColor;
      this.optNoColor = false;
    } else if (optColor) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000120")
          + "  --color should be used when there are no arguments");
    } else {
      this.optColor = optColor;
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
    if ((optionType.NO_ARG == optType) && optNoColor) {
      this.optNoColor = optNoColor;
      this.optColor = false;
    } else if (optNoColor) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000120")
          + "  --no-color should be used when there are no arguments");
    } else {
      this.optNoColor = optNoColor;
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
    if (((optionType.NO_ARG == optType) || (optionType.BRANCHLIST == optType)) && optR) {
      this.optR = optR;
      this.optA = false;
    } else if (optR) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000120")
          + "  -r should be used when there are no arguments or list of branches.");
    } else {
      this.optR = optR;
    }
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
  public void setOptContains(String commit) {
    if ((optionType.NO_ARG == optType) && (null != commit)) {
      this.optContains = commit;
    } else if (null != commit) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000120")
          + "  --contains should be used when there are no arguments.");
    } else {
      this.optContains = commit;
    }
  }

  /**
   * @return the optAbbrev
   */
  public boolean isOptAbbrev() {
    return optAbbrev;
  }

  /**
   * @param optAbbrev the optAbbrev to set
   */
  public void setOptAbbrev(boolean optAbbrev) {
    if (optAbbrev) {
      if (optVerbose) {
        this.optAbbrev = optAbbrev;
        this.optNoAbbrev = false;
      }
    }
    else {
      this.optAbbrev = optAbbrev;
    }
  }

  /**
   * @return the optAbbrev
   */
  public String getOptAbbrevVal() {
    return optAbbrevVal;
  }

  /**
   * @param optAbbrev the optAbbrev to set
   */
  public void setOptAbbrevVal(String optAbbrevVal) {
    if (isOptAbbrev()) {
      this.optAbbrevVal = optAbbrevVal;
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
    if (optNoAbbrev) {
      if (optVerbose) {
        this.optNoAbbrev = optNoAbbrev;
        this.optAbbrev = false;
      }
    }
    else {
      this.optNoAbbrev = optNoAbbrev;
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
    if ((optionType.NO_ARG == optType) && optA) {
      this.optA = optA;
      this.optR = false;
    } else if (optA) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000120")
          + "  -a should be used when there are no arguments");
    } else {
      this.optA = optA;
    }
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
    if ((optionType.BRANCHLIST == optType) && optSmallD) {
      this.optSmallD = optSmallD;
    } else if (optSmallD) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000120")
          + "  -d should be used when there is a list of branches to be deleted.");
    } else {
      this.optSmallD = optSmallD;
    }
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
    if ((optionType.BRANCHLIST == optType) && optBigD) {
      this.optBigD = optBigD;
    } else if (optBigD) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000120")
          + "  -D should be used when there is a list of branches to be deleted.");
    } else {
      this.optBigD = optBigD;
    }
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
    if ((optionType.OLDBRANCH_NEWBRANCH == optType) && optSmallM) {
      this.optSmallM = optSmallM;
    } else if (optSmallM) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000121")
          + "  -m should be used when there is <oldbranch> <newbranch> in command");
    } else {
      this.optSmallM = optSmallM;
    }
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
    if ((optionType.OLDBRANCH_NEWBRANCH == optType) && optBigM) {
      this.optBigM = optBigM;
    } else if (optBigM) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000121")
          + "  -M should be used when there is <oldbranch> <newbranch> in command");
    } else {
      this.optBigM = optBigM;
    }
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
    if ((optionType.BRANCHNAME_STARTPOINT == optType) && optL) {
      this.optL = optL;
    } else if (optL) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000121")
          + "  -l should be used when there is <branchname> [<startpoint>] in command");
    } else {
      this.optL = optL;
    }
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
    if ((optionType.BRANCHNAME_STARTPOINT == optType) && optF) {
      this.optF = optF;
    } else if (optF) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000121")
          + "  -f should be used when there is <branchname> [<startpoint>] in command");
    } else {
      this.optF = optF;
    }
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
    if ((optionType.NO_ARG == optType) && optMerged) {
      this.optMerged = optMerged;
      this.optNoMerged = false;
    } else if (optMerged) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000121")
          + "  --merged should be used when there is no argument in command");
    } else {
      this.optMerged = optMerged;
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
    if ((optionType.NO_ARG == optType) && optNoMerged) {
      this.optNoMerged = optNoMerged;
      this.optMerged = false;
    } else if (optNoMerged) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000121")
          + "  --no-merged should be used when there is no argument in command");
    } else {
      this.optNoMerged = optNoMerged;
    }
  }
}
