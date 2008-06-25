package edu.nyu.cs.javagit.api.commands;

public class GitMvOptions {
  //boolean variable to set or reset -f option.
  private boolean optF = false;
  //boolean variable to set or reset -n option.
  private boolean optN = false;
  //boolean variable to set or reset -k option.
  private boolean optK = false;
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
   * @return the optK
   */
  public boolean isOptK() {
    return optK;
  }
  /**
   * @param optK the optK to set
   */
  public void setOptK(boolean optK) {
    this.optK = optK;
  }
  /**
   * @return the optN
   */
  public boolean isOptN() {
    return optN;
  }
  /**
   * @param optN the optN to set
   */
  public void setOptN(boolean optN) {
    this.optN = optN;
  }
}
