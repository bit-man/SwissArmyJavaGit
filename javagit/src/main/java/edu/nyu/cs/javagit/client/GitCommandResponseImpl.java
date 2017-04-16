package edu.nyu.cs.javagit.client;

import edu.nyu.cs.javagit.api.commands.GitCommandResponse;

import java.util.List;

public class GitCommandResponseImpl
        implements GitCommandResponse
{

    private final int exitCode;
    private final List<String> output;

    public GitCommandResponseImpl(int exitCode, List<String> output)
    {
        this.exitCode = exitCode;
        this.output = output;
    }

    public int getExitCode()
    {
        return exitCode;
    }

    public List<String> getOutput()
    {
        return output;
    }
}
