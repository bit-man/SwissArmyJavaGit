package edu.nyu.cs.javagit.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class provides utilities methods that perform various checks for validity.
 */
public class CheckUtilities {

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
      throw new IOException(ExceptionMessageMap.getMessage("020001") + "  { filename=[" + filename
          + "] }");
    }
  }

  /**
   * Performs a null check on the specified object. If the object is null, a
   * <code>NullPointerException</code> is thrown.
   * 
   * @param obj
   *          The object to check.
   * @param variableName
   *          The name of the variable to use in the exception message.
   */
  public static void checkNullArgument(Object obj, String variableName) {

    // TODO (jhl388): Add a unit test for this method (checkNullArgument()).

    if (null == obj) {
      throw new NullPointerException(ExceptionMessageMap.getMessage("000003")
          + "  { variableName=[" + variableName + "] }");
    }
  }

  /**
   * Checks to see if two objects are equal. The Object.equal() method is used to check for
   * equality.
   * 
   * @param o1
   *          The first object to check.
   * @param o2
   *          The second object to check.
   * @return True if the two objects are equal. False if the objects are not equal.
   */
  public static boolean checkObjectsEqual(Object o1, Object o2) {
    if (null != o1 && !o1.equals(o2)) {
      return false;
    }
  
    if (null == o1 && null != o2) {
      return false;
    }
  
    return true;
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
   * Checks if two unordered lists are equal.
   * 
   * @param l1
   *          The first list to test.
   * @param l2
   *          The second list to test.
   * @return True if:
   *         <ul>
   *         <li>both lists are null or</li>
   *         <li>both lists are the same length, there exists an equivalent object in l2 for all
   *         objects in l1, and there exists an equivalent object in l1 for all objects in l2</li>
   *         </ul>
   *         False otherwise.
   */
  public static boolean checkUnorderedListsEqual(List<?> l1, List<?> l2) {
    if (null == l1 && null != l2) {
      return false;
    }
  
    if (null != l1 && null == l2) {
      return false;
    }
  
    if (l1.size() != l2.size()) {
      return false;
    }
  
    for (Object o : l1) {
      if (!l2.contains(o)) {
        return false;
      }
    }
  
    for (Object o : l2) {
      if (!l1.contains(o)) {
        return false;
      }
    }
  
    return true;
  }

}
