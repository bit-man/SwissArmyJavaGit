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
}
