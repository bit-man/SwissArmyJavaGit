package edu.nyu.cs.javagit.api.commands.internals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.nyu.cs.javagit.client.cli.CliGitStatus;

public class TestTuple {

    @Test
    public void testCreate() {
        CliGitStatus.GitStatusParser.Tuple<String, String> tuple = CliGitStatus.GitStatusParser.Tuple.create("a", "b");
        assertNotNull(tuple);
    }

    @Test
    public void testCreateWithNulls() {
        CliGitStatus.GitStatusParser.Tuple<String, String> tuple = CliGitStatus.GitStatusParser.Tuple.create(null, null);
        assertNotNull(tuple);
    }

    @Test
    public void testNull() {
        CliGitStatus.GitStatusParser.Tuple<String, String> tuple = CliGitStatus.GitStatusParser.Tuple.create(null, null);
        assertNull(tuple.getA());
        assertNull(tuple.getB());
    }

    @Test
    public void testNotNull() {
        CliGitStatus.GitStatusParser.Tuple<String, String> tuple = CliGitStatus.GitStatusParser.Tuple.create("a", "b");
        assertEquals("a", tuple.getA());
        assertEquals("b", tuple.getB());
    }

    @Test
    public void testEquals() {
        CliGitStatus.GitStatusParser.Tuple<String, String> tuple = CliGitStatus.GitStatusParser.Tuple.create("a", "b");
        CliGitStatus.GitStatusParser.Tuple<String, String> tupleExpected = CliGitStatus.GitStatusParser.Tuple.create("a", "b");
        assertTrue(tupleExpected.equals(tuple));
    }

    @Test
    public void testEqualsPorcelain() {
        CliGitStatus.GitStatusParser.Tuple<CliGitStatus.GitStatusParser.PorcelainField, CliGitStatus.GitStatusParser.PorcelainField> tupleExpected =
                CliGitStatus.GitStatusParser.Tuple.create(CliGitStatus.GitStatusParser.PorcelainField.UNTRACKED, CliGitStatus.GitStatusParser.PorcelainField.UNTRACKED);
        CliGitStatus.GitStatusParser.Tuple<CliGitStatus.GitStatusParser.PorcelainField, CliGitStatus.GitStatusParser.PorcelainField> tuple =
                CliGitStatus.GitStatusParser.Tuple.create(CliGitStatus.GitStatusParser.PorcelainField.UNTRACKED, CliGitStatus.GitStatusParser.PorcelainField.UNTRACKED);
        assertEquals(tupleExpected, tuple);
    }

    @Test
    public void testEqualsDifferent() {
        CliGitStatus.GitStatusParser.Tuple<String, String> tuple = CliGitStatus.GitStatusParser.Tuple.create("a", "X");
        CliGitStatus.GitStatusParser.Tuple<String, String> tupleExpected = CliGitStatus.GitStatusParser.Tuple.create("a", "b");
        assertFalse(tupleExpected.equals(tuple));
    }

    @Test
    public void testEqualsDifferentObjectType() {
        Integer i = 10;
        CliGitStatus.GitStatusParser.Tuple<String, String> tuple = CliGitStatus.GitStatusParser.Tuple.create("a", "b");
        assertFalse(tuple.equals(i));
    }

    @Test
    public void testHashCode() {
        CliGitStatus.GitStatusParser.Tuple<String, String> tuple = CliGitStatus.GitStatusParser.Tuple.create("a", "b");
        CliGitStatus.GitStatusParser.Tuple<String, String> tupleExpected = CliGitStatus.GitStatusParser.Tuple.create("a", "b");
        assertEquals(tupleExpected.hashCode(),tuple.hashCode());
    }

    @Test
    public void testHashCodePorcelain() {
        CliGitStatus.GitStatusParser.Tuple<CliGitStatus.GitStatusParser.PorcelainField, CliGitStatus.GitStatusParser.PorcelainField> tupleExpected =
                CliGitStatus.GitStatusParser.Tuple.create(CliGitStatus.GitStatusParser.PorcelainField.UNTRACKED, CliGitStatus.GitStatusParser.PorcelainField.UNTRACKED);
        CliGitStatus.GitStatusParser.Tuple<CliGitStatus.GitStatusParser.PorcelainField, CliGitStatus.GitStatusParser.PorcelainField> tuple =
                CliGitStatus.GitStatusParser.Tuple.create(CliGitStatus.GitStatusParser.PorcelainField.UNTRACKED, CliGitStatus.GitStatusParser.PorcelainField.UNTRACKED);
        assertEquals(tupleExpected.hashCode(),tuple.hashCode());
    }

}
