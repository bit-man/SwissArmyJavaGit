package edu.nyu.cs.javagit.api.commands;

/**
 * A class to manage options to the <code>GitMv</code> command.
 */
public class GitMvOptions {
  // boolean variable to set or reset -f option.
  private boolean optF = false;
  
  // boolean variable to set or reset -n option.
  private boolean optN = false;
  
  // boolean variable to set or reset -k option.
  private boolean optK = false;

  /**
   * Is the -f option set?
   * 
   * @return True if the -f option is set, false if it is not set.
   */
  public boolean isOptF() {
    return optF;
  }

  /**
   * Set the force option.
   * 
   * @param optF
   *          True to set the -f option, false to reset it.
   */
  public void setOptF(boolean optF) {
    this.optF = optF;
  }

  /**
   * Is the -k option set?
   * 
   * @return True if the -k option is set, false if it is not set.
   */
  public boolean isOptK() {
    return optK;
  }

  /**
   * Set the quiet option.
   * 
   * @param optK
   *          True to set the -k option, false to reset it.
   */
  public void setOptK(boolean optK) {
    this.optK = optK;
  }

  /**
   * Is the -n option set?
   * 
   * @return True if the -n option is set, false if it is not set.
   */
  public boolean isOptN() {
    return optN;
  }

  /**
   * Set the dry-run option.
   * 
   * @param optK
   *          True to set the -n option, false to reset it.
   */
  public void setOptN(boolean optN) {
    this.optN = optN;
  }
}
