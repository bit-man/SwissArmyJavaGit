package edu.nyu.cs.javagit.client.parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestTuple
{

    @Test
    public void testCreate()
    {
        GitStatusParser.Tuple<String, String> tuple = GitStatusParser.Tuple.create("a", "b");
        assertNotNull(tuple);
    }

    @Test
    public void testCreateWithNulls()
    {
        GitStatusParser.Tuple<String, String> tuple = GitStatusParser.Tuple.create(null, null);
        assertNotNull(tuple);
    }

    @Test
    public void testNull()
    {
        GitStatusParser.Tuple<String, String> tuple = GitStatusParser.Tuple.create(null, null);
        assertNull(tuple.getA());
        assertNull(tuple.getB());
    }

    @Test
    public void testNotNull()
    {
        GitStatusParser.Tuple<String, String> tuple = GitStatusParser.Tuple.create("a", "b");
        assertEquals("a", tuple.getA());
        assertEquals("b", tuple.getB());
    }

    @Test
    public void testEquals()
    {
        GitStatusParser.Tuple<String, String> tuple = GitStatusParser.Tuple.create("a", "b");
        GitStatusParser.Tuple<String, String> tupleExpected = GitStatusParser.Tuple.create("a", "b");
        assertTrue(tupleExpected.equals(tuple));
    }

    @Test
    public void testEqualsPorcelain()
    {
        GitStatusParser.Tuple<GitStatusParser.PorcelainField, GitStatusParser.PorcelainField> tupleExpected =
                GitStatusParser.Tuple.create(GitStatusParser.PorcelainField.UNTRACKED, GitStatusParser.PorcelainField
                        .UNTRACKED);
        GitStatusParser.Tuple<GitStatusParser.PorcelainField, GitStatusParser.PorcelainField> tuple =
                GitStatusParser.Tuple.create(GitStatusParser.PorcelainField.UNTRACKED, GitStatusParser.PorcelainField
                        .UNTRACKED);
        assertEquals(tupleExpected, tuple);
    }

    @Test
    public void testEqualsDifferent()
    {
        GitStatusParser.Tuple<String, String> tuple = GitStatusParser.Tuple.create("a", "X");
        GitStatusParser.Tuple<String, String> tupleExpected = GitStatusParser.Tuple.create("a", "b");
        assertFalse(tupleExpected.equals(tuple));
    }

    @Test
    public void testEqualsDifferentObjectType()
    {
        Integer i = 10;
        GitStatusParser.Tuple<String, String> tuple = GitStatusParser.Tuple.create("a", "b");
        assertFalse(tuple.equals(i));
    }

    @Test
    public void testHashCode()
    {
        GitStatusParser.Tuple<String, String> tuple = GitStatusParser.Tuple.create("a", "b");
        GitStatusParser.Tuple<String, String> tupleExpected = GitStatusParser.Tuple.create("a", "b");
        assertEquals(tupleExpected.hashCode(), tuple.hashCode());
    }

    @Test
    public void testHashCodePorcelain()
    {
        GitStatusParser.Tuple<GitStatusParser.PorcelainField, GitStatusParser.PorcelainField> tupleExpected =
                GitStatusParser.Tuple.create(GitStatusParser.PorcelainField.UNTRACKED, GitStatusParser.PorcelainField
                        .UNTRACKED);
        GitStatusParser.Tuple<GitStatusParser.PorcelainField, GitStatusParser.PorcelainField> tuple =
                GitStatusParser.Tuple.create(GitStatusParser.PorcelainField.UNTRACKED, GitStatusParser.PorcelainField
                        .UNTRACKED);
        assertEquals(tupleExpected.hashCode(), tuple.hashCode());
    }

}
