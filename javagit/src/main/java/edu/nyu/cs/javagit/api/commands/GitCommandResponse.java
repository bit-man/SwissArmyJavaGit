package edu.nyu.cs.javagit.api.commands;

import java.util.List;

public interface GitCommandResponse
        extends CommandResponse
{
    int getExitCode();

    List<String> getOutput();
}
