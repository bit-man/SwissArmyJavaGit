package edu.nyu.cs.javagit.utilities;

/**
 * Description : String utilities
 * Date: 3/31/13
 * Time: 7:01 PM
 */
public final class StringUtilities {
    /**
     * Returns the position for char 'c' in string 'str' starting from position 'pos'
     * and searching towards the string beginning
     * @param str
     * @param from
     * @param c
     * @return char 'c' position or -1 if char is not found
     */
    public static int indexOfLeft(String str, int from, char c) {
        int pos = -1;
        int f = from;

        while(  f >= 0 && pos == -1 )
            if ( str.charAt(f--) == c )
                pos = f+1;

        return pos;
    }

    /**
     * Searches for quoted strings in a string
     * @param str string to searh into
     * @param n  nth quoted string to search for
     * @return The Nth quoted string
     */
    public static String getNQuotedElement(String str, int n){
        int firstQuote  = indexOfNQuote(str, 2 * n - 1);
        int secondQuote = str.indexOf("'", firstQuote + 1);
        return str.substring(firstQuote + 1, secondQuote);

    }

    /**
     * Returns the index of the nth quote from string
     * @param str string to search into
     * @param n nth quote to search for
     * @return  The nth quote position inside string
     */
    private static int indexOfNQuote(String str, int n) {
        char c = "'".toCharArray()[0];
        return indexOfNChar(str, c, n);
    }

    /**
     * Returns the index of the nth char occurrence inside string
     * @param str string to search into
     * @param c character to search for
     * @param n nth char occurrence  (starting from 1)
     * @return  The nth char occurence position inside string
     */
    public static int indexOfNChar(String str, char c,int n) {
        int ix = 0;
        for ( int i = n; i > 0; i--)
             ix = str.indexOf(c, ix ) + 1;
        return ix - 1;
    }

    /**
     * Retrieves the 'n'th element of the 'line' separated by char 'c'
     * @param n
     * @param line
     * @param c
     * @return  Nth element
     */
    public static String obtainElement(int n, String line, char c) {
        final String[] split = line.split( String.valueOf(c) );
        return split[n];
    }

    /**
     * Checks for string emptiness
     * @param str
     * @return
     */
    public static boolean isEmptyString(String str) {
        return (str == null || str.length() == 0 );
    }
}
