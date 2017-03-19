package edu.nyu.cs.javagit.client.cli;

import edu.nyu.cs.javagit.api.JavaGitException;

import java.io.File;
import java.io.IOException;

public interface ICommandRunner<T>
{
    T run()
            throws IOException, JavaGitException;

    void setWorkingDirectory(File repositoryPath);

    void setParser(IParser parser);

    void setProcessBuilder(IGitProcessBuilder processBuilder);
}
