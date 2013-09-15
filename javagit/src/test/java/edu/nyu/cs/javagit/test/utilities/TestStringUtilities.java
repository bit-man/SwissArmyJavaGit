package edu.nyu.cs.javagit.test.utilities;

import edu.nyu.cs.javagit.utilities.StringUtilities;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Description : StringUtilities testing
 * Date: 3/31/13
 * Time: 7:16 PM
 */
public class TestStringUtilities extends TestCase {
    @Test
    public void testFindChar() {
        String str = "aaaaaaa bbb cccc";
        assertEquals(11, StringUtilities.indexOfLeft(str, 13, ' '));
        assertEquals(7, StringUtilities.indexOfLeft(str, 10, ' '));
        assertEquals(-1, StringUtilities.indexOfLeft(str, 6, ' '));
    }

    @Test
    public void testgetNQuotedElement() {
        String line = "zzz 'a'  xxxx 'b' ";
        assertEquals("a", StringUtilities.getNQuotedElement(line, 1));
        assertEquals("b", StringUtilities.getNQuotedElement(line, 2));
    }

}
