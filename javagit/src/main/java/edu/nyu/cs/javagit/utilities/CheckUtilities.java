package edu.nyu.cs.javagit.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class provides utilities to check parameters for validity.
 */
public class CheckUtilities {

  /**
   * Checks a <code>List&lt;String&gt;</code> argument to make sure it is not null, none of its
   * elements are null, and all its elements contain one or more characters. If the
   * <code>List&lt;String&gt;</code> or any <code>String</code> is null, a
   * <code>NullPointerException</code> is thrown. If a <code>String</code> has length zero, an
   * <code>IllegalArgumentException</code> is thrown.
   * 
   * @param str
   *          The <code>List&lt;String&gt;</code> to check.
   * @param variableName
   *          The name of the variable to use in throwing exceptions.
   */
  public static void checkStringListArgument(List<String> str, String variableName) {
    if (null == str) {
      throw new NullPointerException(ExceptionMessageMap.getMessage("000002")
          + "  { variableName=[" + variableName + "] }");
    }
    for (int i = 0; i < str.size(); i++) {
      checkStringArgument(str.get(i), variableName);
    }
  }

  /**
   * Checks a <code>String</code> argument to make sure it is not null and contains one or more
   * characters. If the <code>String</code> is null, a <code>NullPointerException</code> is
   * thrown. If the <code>String</code> has length zero, an <code>IllegalArgumentException</code>
   * is thrown.
   * 
   * @param str
   *          The string to check.
   * @param variableName
   *          The name of the variable to use in throwing exceptions.
   */
  public static void checkStringArgument(String str, String variableName) {
    if (null == str) {
      throw new NullPointerException(ExceptionMessageMap.getMessage("000001")
          + "  { variableName=[" + variableName + "] }");
    }
    if (str.length() == 0) {
      throw new IllegalArgumentException(ExceptionMessageMap.getMessage("000001")
          + "  { variableName=[" + variableName + "] }");
    }
  }

  /**
   * Checks that the specified filename exists. This assumes that the above check for string
   * validity has already been run and the path/filename is neither null or of size 0.
   * 
   * @param filename
   *          File or directory path
   */
  public static void checkFileValidity(String filename) throws IOException {
    File file = new File(filename);
    if (!file.exists()) {
      throw new IOException(ExceptionMessageMap.getMessage("010001") + "  { filename=[" + filename
          + "] }");
    }
  }

}
