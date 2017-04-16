package edu.nyu.cs.javagit.client;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitCommandResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IGitCommand
{
    GitCommandResponse run(File repository, List<String> gitArgs)
            throws IOException, JavaGitException;
}
