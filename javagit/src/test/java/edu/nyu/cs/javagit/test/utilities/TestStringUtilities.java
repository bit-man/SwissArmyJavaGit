package edu.nyu.cs.javagit.test.utilities;

import edu.nyu.cs.javagit.utilities.StringUtilities;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Description : StringUtilities testing
 * Date: 3/31/13
 * Time: 7:16 PM
 */
public class TestStringUtilities  {

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

    @Test
    public void testObtainElement() {
        String line = "aaa bbbb cccc dddd";
        assertEquals("aaa", StringUtilities.obtainElement(0, line, ' '));
        assertEquals("bbbb", StringUtilities.obtainElement(1, line, ' '));
        assertEquals("cccc", StringUtilities.obtainElement(2, line, ' '));
        assertEquals("dddd", StringUtilities.obtainElement(3, line, ' '));
     }

    @Test
    public void testIndexOfNChar() {
        String line = "aaaa bbbbb cccc dddd";
        assertEquals(4, StringUtilities.indexOfNChar(line, ' ', 1));
        assertEquals(10, StringUtilities.indexOfNChar(line, ' ', 2));
        assertEquals(15, StringUtilities.indexOfNChar(line, ' ', 3));

    }
}
