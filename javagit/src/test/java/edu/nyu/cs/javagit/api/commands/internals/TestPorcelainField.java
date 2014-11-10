package edu.nyu.cs.javagit.api.commands.internals;

import edu.nyu.cs.javagit.client.cli.CliGitStatus;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TestPorcelainField {

    @Test
    public void testUnmodified() {
        assertEquals(CliGitStatus.GitStatusParser.PorcelainField.UNMODIFIED,
                CliGitStatus.GitStatusParser.PorcelainField.char2field(' ') );
    }

    @Test
    public void testModified() {
        assertEquals(CliGitStatus.GitStatusParser.PorcelainField.MODIFIED,
                CliGitStatus.GitStatusParser.PorcelainField.char2field('M') );
    }

    @Test
    public void testAdded() {
        assertEquals(CliGitStatus.GitStatusParser.PorcelainField.ADDED,
                CliGitStatus.GitStatusParser.PorcelainField.char2field('A') );
    }

    @Test
    public void testDeleted() {
        assertEquals(CliGitStatus.GitStatusParser.PorcelainField.DELETED,
                CliGitStatus.GitStatusParser.PorcelainField.char2field('D') );
    }

    @Test
    public void testRenamed() {
        assertEquals(CliGitStatus.GitStatusParser.PorcelainField.RENAMED,
                CliGitStatus.GitStatusParser.PorcelainField.char2field('R') );
    }

    @Test
    public void testCopied() {
        assertEquals(CliGitStatus.GitStatusParser.PorcelainField.COPIED,
                CliGitStatus.GitStatusParser.PorcelainField.char2field('C') );
    }

    @Test
    public void testUpdatedButUnmerged() {
        assertEquals(CliGitStatus.GitStatusParser.PorcelainField.UPDATED_BUT_UNMERGED,
                CliGitStatus.GitStatusParser.PorcelainField.char2field('U') );
    }

    @Test
    public void testUntracked() {
        assertEquals(CliGitStatus.GitStatusParser.PorcelainField.UNTRACKED,
                CliGitStatus.GitStatusParser.PorcelainField.char2field('?') );
    }

    @Test
    public void testIgnored() {
        assertEquals(CliGitStatus.GitStatusParser.PorcelainField.IGNORED,
                CliGitStatus.GitStatusParser.PorcelainField.char2field('!') );
    }
    @Test
    public void testAnythingElse() {
        assertEquals(null,
                CliGitStatus.GitStatusParser.PorcelainField.char2field('Z') );
    }
}
