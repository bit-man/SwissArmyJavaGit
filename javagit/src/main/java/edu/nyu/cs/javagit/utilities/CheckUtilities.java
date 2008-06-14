package edu.nyu.cs.javagit.utilities;

/**
 * This class provides utilities to check parameters for validity.
 */
public class CheckUtilities {

	/**
	 * Checks a <code>String</code> argument to make sure it is not null and
	 * contains one or more characters. If the <code>String</code> is null, a
	 * <code>NullPointerException</code> is thrown. If the <code>String</code>
	 * has length zero, an <code>IllegalArgumentException</code> is thrown.
	 * 
	 * @param str
	 *            The string to check.
	 * @param variableName
	 *            The name of the variable to use in throwing exceptions.
	 */
	public static void checkStringArgument(String str, String variableName) {
		if (null == str) {
			throw new NullPointerException("No " + variableName
					+ " specified. A " + variableName + " is required.");
		}
		if (str.length() == 0) {
			throw new IllegalArgumentException("No " + variableName
					+ " specified. A " + variableName + " is required.");
		}
	}

}
