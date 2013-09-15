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
     * @param line
     * @param n
     * @return The Nth quoted string
     */
    public static String getNQuotedElement(String line, int n){
        int firstQuote  = indexOfNQuote(line, 2 * n - 1);
        int secondQuote = line.indexOf("'", firstQuote + 1);
        String elem = line.substring(firstQuote + 1, secondQuote);
        return elem;

    }

    private static int indexOfNQuote(String line, int n) {
        int ix = 0;
        for ( int i = n; i > 0; i--)
             ix = line.indexOf("'", ix ) + 1;
        return ix - 1;
    }
}
