package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.client.GitStatusResponseImpl;
import edu.nyu.cs.javagit.test.utilities.FileUtilities;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CliGitStatusTest
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private CliGitStatus client;
    private IGitProcessBuilder processBuilder;
    private CommandRunnerMock<GitStatusResponseImpl> commandRuner;

    @Test
    public void nullRepositoryPathThrowsNPE()
            throws IOException, JavaGitException
    {
        givenNewClient();
        whenExpectedExceptionIs(NullPointerException.class);
        thenStatusShouldFail(null);
    }

    @Test
    public void nonExistentRepositoryPathThrowsIOException()
            throws IOException, JavaGitException
    {
        givenNewClient();
        whenExpectedExceptionIs(IOException.class);
        thenStatusShouldFail(FileUtilities.createNonExistingTempFolder());
    }

    @Test
    public void commandLineContainsStatusCommand()
            throws IOException, JavaGitException
    {
        givenNewClient();
        whenStatusIsQueried();
        thenCommandlineContains("status");
    }

    @Test
    public void commandLineContainsPorcelainSwitch()
            throws IOException, JavaGitException
    {
        givenNewClient();
        whenStatusIsQueried();
        thenCommandlineContains("--porcelain");
    }

    @Test
    public void commandLineContainsIgnoredSwitch()
            throws IOException, JavaGitException
    {
        givenNewClient();
        whenStatusIsQueried();
        thenCommandlineContains("--ignored");
    }

    private void thenCommandlineContains(String command)
    {
        assertThat(processBuilder.getCommand()).containsOnlyOnce(command);
    }

    private void whenStatusIsQueried()
            throws IOException, JavaGitException
    {
        File repositoryPath = FileUtilities.createTempFolder();
        client.status(repositoryPath);
    }


    private void whenExpectedExceptionIs(Class<? extends Throwable> exceptionClass)
    {
        exception.expect(exceptionClass);
    }

    private void thenStatusShouldFail(File repositoryPath)
            throws JavaGitException, IOException
    {
        client.status(repositoryPath);
    }

    private void givenNewClient()
            throws JavaGitException, IOException
    {
        this.processBuilder = new ProcessBuilderMock();
        commandRuner = new CommandRunnerMock<>();
        client = new CliGitStatus(processBuilder, new Validator(), commandRuner);
    }

}
