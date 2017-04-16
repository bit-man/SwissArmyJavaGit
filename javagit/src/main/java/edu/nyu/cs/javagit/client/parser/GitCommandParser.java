package edu.nyu.cs.javagit.client.parser;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.CommandResponse;
import edu.nyu.cs.javagit.client.GitCommandResponseImpl;
import edu.nyu.cs.javagit.client.cli.IParser;

import java.util.ArrayList;
import java.util.List;

public class GitCommandParser
        implements IParser
{
    private final List<String> output = new ArrayList<>();
    private int exitCode;

    @Override
    public void parseLine(String line)
            throws JavaGitException
    {
        this.output.add(line);
    }

    @Override
    public void processExitCode(int code)
    {
        this.exitCode = code;
    }

    @Override
    public CommandResponse getResponse()
            throws JavaGitException
    {
        return new GitCommandResponseImpl(exitCode, output);
    }

    @Override
    public void setWorkingDir(String workingDir)
    {

    }
}
