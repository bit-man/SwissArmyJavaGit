package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.api.JavaGitException;

import java.io.File;
import java.io.IOException;

public class CommandRunnerMock<T>
        implements ICommandRunner<T>
{
    @Override
    public T run()
            throws IOException, JavaGitException
    {
        return null;
    }

    @Override
    public void setWorkingDirectory(File repositoryPath)
    {
    }

    @Override
    public void setParser(IParser parser)
    {
    }

    @Override
    public void setProcessBuilder(IGitProcessBuilder processBuilder)
    {
    }
}
