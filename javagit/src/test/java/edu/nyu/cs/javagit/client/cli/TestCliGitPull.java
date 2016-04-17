package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.TestBase;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitPullResponse;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCliGitPull extends TestBase
{
    @Test
    public void testGitPullParserExitCode0MakesResponseNotContainError()
            throws JavaGitException
    {
        CliGitPull.GitPullParser parser = new CliGitPull.GitPullParser();
        parser.parseLine("aaaaaa");
        parser.processExitCode(0);
        assertFalse(((GitPullResponse)parser.getResponse()).containsError());
    }

    @Test
    public void testGitPullParserExitCode1MakesResponseContainsError()
            throws JavaGitException
    {
        CliGitPull.GitPullParser parser = new CliGitPull.GitPullParser();
        parser.parseLine("aaaaaa");
        parser.processExitCode(1);
        GitPullResponse response = (GitPullResponse) parser.getResponse();
        assertTrue(response.containsError());
        assertThat(response.getOutput()).containsOnly("aaaaaa");
    }

    @Test
    public void testPullFailsOnInexistentRepoPath()
    {
        assertThatThrownBy(doGitPullAtRepo("/path/to/nonexistent/repo"))
                .isNotNull()
                .hasMessageStartingWith("Git pull error")
                .hasCauseInstanceOf(IOException.class);
    }

    @Test
    public void testPullFailsOnUninitRepo()
            throws IOException
    {
        File repo = FileUtilities.createTempDirectory(this.getClass().getSimpleName());
        assertThatThrownBy(doGitPullAtRepo(repo.getAbsolutePath()))
                .isNotNull()
                .hasMessageStartingWith("Git pull error")
                .hasNoCause();
    }

    private ThrowableAssert.ThrowingCallable doGitPullAtRepo(final String repoPath)
    {
        return new ThrowableAssert.ThrowingCallable()
        {
            @Override
            public void call()
                    throws Throwable
            {
                new CliGitPull().pull(new File(repoPath));
            }
        };
    }
}
