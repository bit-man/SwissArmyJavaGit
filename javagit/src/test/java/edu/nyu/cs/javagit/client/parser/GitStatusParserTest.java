package edu.nyu.cs.javagit.client.parser;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitStatusResponse;
import edu.nyu.cs.javagit.client.cli.PorcelainParseWrongFormatException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public class GitStatusParserTest
{

    private GitStatusParser parser;
    private Throwable throwable;
    private String repo;

    @Before
    public void setup()
            throws IOException, JavaGitException
    {
        repo = "";
        this.parser = new GitStatusParser();
        parser.setWorkingDir(repo);
    }

    @Test
    public void nullLineParsingThrowsException()
            throws JavaGitException
    {
        this.throwable = catchThrowable(getShouldRaiseThrowable(null));
        assertThat(throwable).isInstanceOf(JavaGitException.class).hasCauseExactlyInstanceOf
                (PorcelainParseWrongFormatException.class);
    }

    @Test
    public void emptyLineParsingThrowsException()
            throws JavaGitException
    {
        this.throwable = catchThrowable(getShouldRaiseThrowable(""));
        assertThat(throwable).isInstanceOf(JavaGitException.class).hasCauseExactlyInstanceOf
                (PorcelainParseWrongFormatException.class);
    }

    @Test
    public void deletedFilesNotUpdatedParsingAddsFileToResponse()
            throws JavaGitException
    {
        parser.parseLine(" D xxxx");
        parser.getResponse();
        assertThat(parser.getResponse().getDeletedFilesNotUpdated()).containsOnly(inRepoFile("xxxx"));
    }

    @Test
    public void testIgnoredFiles()
            throws JavaGitException
    {
        parser.parseLine("!! ignoredFile");
        GitStatusResponse response = parser.getResponse();
        assertThat(response.getIgnoredFiles()).containsOnly(inRepoFile("ignoredFile"));
        assertEquals(response.getIgnoredFilesSize(), 1);
        assertEquals(response.getUntrackedFilesSize(), 0);
        assertEquals(response.getDeletedFilesNotUpdatedSize(), 0);
        assertEquals(response.getDeletedFilesToCommitSize(), 0);
        assertEquals(response.getModifiedFilesNotUpdatedSize(), 0);
        assertEquals(response.getModifiedFilesToCommitSize(), 0);
        assertEquals(response.getNewFilesToCommitSize(), 0);
        assertEquals(response.getRenamedFilesToCommitSize(), 0);
        assertEquals(response.getErrorCount(), 0);
    }

    @Test
    public void testUnTrackedFiles()
            throws JavaGitException
    {
        parser.parseLine("?? untrackedFile");
        GitStatusResponse response = parser.getResponse();
        assertThat(response.getUntrackedFiles()).containsOnly(inRepoFile("untrackedFile"));
        assertEquals(response.getIgnoredFilesSize(), 0);
        assertEquals(response.getUntrackedFilesSize(), 1);
        assertEquals(response.getDeletedFilesNotUpdatedSize(), 0);
        assertEquals(response.getDeletedFilesToCommitSize(), 0);
        assertEquals(response.getModifiedFilesNotUpdatedSize(), 0);
        assertEquals(response.getModifiedFilesToCommitSize(), 0);
        assertEquals(response.getNewFilesToCommitSize(), 0);
        assertEquals(response.getRenamedFilesToCommitSize(), 0);
        assertEquals(response.getErrorCount(), 0);
    }

    private File inRepoFile(String path)
    {
        return new File(repo, path);
    }

    private ThrowableAssert.ThrowingCallable getShouldRaiseThrowable(final String line)
    {
        return new ThrowableAssert.ThrowingCallable()
        {
            @Override
            public void call()
                    throws Throwable
            {
                parser.parseLine(line);
            }
        };
    }

}
