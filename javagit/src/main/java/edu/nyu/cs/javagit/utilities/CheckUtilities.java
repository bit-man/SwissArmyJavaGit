package edu.nyu.cs.javagit.utilities;

import java.io.File;
import java.io.IOException;

/**
 * This class provides utilities to check parameters for validity.
 */
public class CheckUtilities {

	/**
	 * Checks a <code>String[]</code> argument to make sure it is not null,
	 * none of its elements are null, and all its elements contain one or 
	 * more characters. If the <code>String[]</code> or any 
	 * <code>String</code> is null, a <code>NullPointerException</code> is 
	 * thrown. If a <code>String</code> has length zero, an 
	 * <code>IllegalArgumentException</code> is thrown.
	 * 
	 * @param str[]
	 *            The <code>String[]</code> to check.
	 * @param variableName
	 *            The name of the variable to use in throwing exceptions.
	 */
	public static void checkStringArrayArgument(String[] str, String variableName) {
		if (null == str) {
			throw new NullPointerException("No " + variableName
					+ " specified. A " + variableName + " is required.");
		}
		for(int i = 0; i < str.length; i++) {
			checkStringArgument(str[i],variableName);
		}
	}

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

	/**
	 * This assumes that the above check for string validity has already
	 * been run and the path/filename is neither null or of size 0.
	 * 
	 * @param filename
	 *              file or directory path
	 */
	public static void checkFileValidity(String filename) throws IOException
	{
		File file = new File(filename);
		if ( ! file.exists() ) {
			throw new IOException("file or path with name " + filename + " does not exist");
		}
	}
}
