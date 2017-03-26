package edu.nyu.cs.javagit.client.parser;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TestPorcelainField
{

    @Test
    public void testUnmodified()
    {
        assertEquals(GitStatusParser.PorcelainField.UNMODIFIED,
                GitStatusParser.PorcelainField.char2field(' '));
    }

    @Test
    public void testModified()
    {
        assertEquals(GitStatusParser.PorcelainField.MODIFIED,
                GitStatusParser.PorcelainField.char2field('M'));
    }

    @Test
    public void testAdded()
    {
        assertEquals(GitStatusParser.PorcelainField.ADDED,
                GitStatusParser.PorcelainField.char2field('A'));
    }

    @Test
    public void testDeleted()
    {
        assertEquals(GitStatusParser.PorcelainField.DELETED,
                GitStatusParser.PorcelainField.char2field('D'));
    }

    @Test
    public void testRenamed()
    {
        assertEquals(GitStatusParser.PorcelainField.RENAMED,
                GitStatusParser.PorcelainField.char2field('R'));
    }

    @Test
    public void testCopied()
    {
        assertEquals(GitStatusParser.PorcelainField.COPIED,
                GitStatusParser.PorcelainField.char2field('C'));
    }

    @Test
    public void testUpdatedButUnmerged()
    {
        assertEquals(GitStatusParser.PorcelainField.UPDATED_BUT_UNMERGED,
                GitStatusParser.PorcelainField.char2field('U'));
    }

    @Test
    public void testUntracked()
    {
        assertEquals(GitStatusParser.PorcelainField.UNTRACKED,
                GitStatusParser.PorcelainField.char2field('?'));
    }

    @Test
    public void testIgnored()
    {
        assertEquals(GitStatusParser.PorcelainField.IGNORED,
                GitStatusParser.PorcelainField.char2field('!'));
    }

    @Test
    public void testAnythingElse()
    {
        assertEquals(null,
                GitStatusParser.PorcelainField.char2field('Z'));
    }
}
