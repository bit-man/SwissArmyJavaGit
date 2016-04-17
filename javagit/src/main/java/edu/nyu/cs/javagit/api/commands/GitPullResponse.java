package edu.nyu.cs.javagit.api.commands;

import java.util.Collection;

public class GitPullResponse implements CommandResponse
{
    private final int exitCode;

    private final Collection<String> output;

    public GitPullResponse(int exitCode, Collection<String> output)
    {
        this.exitCode = exitCode;
        this.output = output;
    }

    public Collection<String> getOutput()
    {
        return output;
    }

    public boolean containsError()
    {
        return exitCode != 0;
    }
}
