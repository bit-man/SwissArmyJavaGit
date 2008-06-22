package edu.nyu.cs.javagit.utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps exception error codes to exception messages. <br>
 * <br>
 * 
 * The code structure is as follows:
 * 
 * <ul>
 * <li>The error code is a six digit number</li>
 * <li>The first two digits represent the error type</li>
 * <li>The last four digits represent the specific error in that error type</li>
 * </ul>
 * 
 * Note: the last four digits may be divided into subtypes. In such case, the explanation of the
 * format will be included in the error type class descriptions below.<br>
 * <br>
 * 
 * The error types are:
 * 
 * <ul>
 * <li>00 - Standard Java exceptions caused by malformed or invalid method parameters</li>
 * <li>01 - All other standard Java exceptions</li>
 * <li></li>
 * </ul>
 * 
 * How to use the codes and messages:
 * 
 * <ul>
 * <li>All exceptions thrown from within javagit should contain a code and a message, even
 * exceptions thrown using standard Java exceptions.</li>
 * <li>When creating a new exception code and message, start the message with the code like so:
 * "000001: "</li>
 * <li>When throwing a <code>JavaGitException</code>, the code and the message must be supplied
 * to the constructor.</li>
 * <li>When throwing a standard Java exception (ex. <code>NullPointerException</code>), just
 * supply the message since the code is already included in the message.</li>
 * <li>The message retrieved from the <code>ExceptionMessageMap</code> can be treated like a base
 * message. If additional information is required in the message, append that information to the
 * base message.</li>
 * <li>If the value of a variable must be included in the message, list the variable names and
 * their values at the end of the message in the following format: "{ varName1=[varVal1],
 * varName2=[varVal2], ... }"</li>
 * </ul>
 * 
 * TODO (jhl388): Load the exception message mapping from a properties bundle.
 */
public class ExceptionMessageMap {

  private static Map<String, String> MESSAGE_MAP;

  {
    MESSAGE_MAP = new HashMap<String, String>();

    MESSAGE_MAP.put("000001", "000001: A String variable was not specified but is required.");
    MESSAGE_MAP.put("000002", "000002: A List<String> variable was not specified but is required.");

    MESSAGE_MAP.put("010001", "010001: File or path does not exist.");

    MESSAGE_MAP.put("010100", "010100: Unable to start sub-process.");
    MESSAGE_MAP.put("010101", "010101: Error reading input from the sub-process.");
  }

  /**
   * Gets the error message for the specified code.
   * 
   * @param code
   *          The error code for which to get the associated error message.
   * @return The error message for the specified code.
   */
  public static String getMessage(String code) {
    String str = MESSAGE_MAP.get(code);
    if (null == str) {
      return "NO MESSAGE FOR ERROR CODE. { code=[" + code + "] }";
    }
    return str;
  }

}
