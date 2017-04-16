package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.TestBase;
import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestGitCommand
        extends TestBase
{

    private GitCommand gitCommand;
    private GitCommandResponse response;
    private File repository;

    @Test
    public void statusAtNotGitRepositoryEndsInError()
            throws IOException, JavaGitException
    {
        givenGitStatus();
        givenPathToNonGitRepository();
        whenCommandIsRun();
        thenOutputIsError();

    }

    @Test
    public void statusAtGitRepositoryEndsOK()
            throws IOException, JavaGitException
    {
        givenGitStatus();
        givenPathToGitRepository();
        whenCommandIsRun();
        thenOutputIsNoError();
    }

    @Test
    public void statusAtGitRepositoryWithInvalidArgsEndsInError()
            throws IOException, JavaGitException
    {
        givenGitStatus();
        givenPathToGitRepository();
        whenCommandIsRunWithArgs(Lists.newArrayList("--invalid-argument-12345"));
        thenOutputIsError();
    }

    private void thenOutputIsError()
    {
        assertTrue(response.getExitCode() != 0);
        assertNotNull(response.getOutput());
    }

    private void thenOutputIsNoError()
    {
        assertEquals(0, response.getExitCode());
        assertNotNull(response.getOutput());
    }

    private void givenPathToNonGitRepository()
            throws IOException
    {
        this.repository = FileUtilities.createTempFolder();
    }

    private void givenPathToGitRepository()
            throws IOException, JavaGitException
    {
        this.repository = FileUtilities.createTempFolder();
        new GitInit().init(repository);
    }

    private void whenCommandIsRun()
            throws IOException, JavaGitException
    {
        this.response = gitCommand.run(repository, new ArrayList<String>());
    }

    private void whenCommandIsRunWithArgs(List<String> args)
            throws IOException, JavaGitException
    {
        this.response = gitCommand.run(repository, args);
    }

    private void givenGitStatus()
    {
        this.gitCommand = new GitCommand("status");
    }

}
